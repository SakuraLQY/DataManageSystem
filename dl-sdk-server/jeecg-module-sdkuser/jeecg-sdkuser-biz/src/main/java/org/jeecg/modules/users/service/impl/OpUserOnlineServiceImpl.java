package org.jeecg.modules.users.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.IsConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.Md5Util;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.users.bo.CheckAdultData;
import org.jeecg.modules.users.dto.ReportErrResponse;
import org.jeecg.modules.users.dto.ReportRequestBody;
import org.jeecg.modules.users.entity.*;
import org.jeecg.modules.users.mapper.OpHolidayConfigMapper;
import org.jeecg.modules.users.mapper.OpUserOnlineMapper;
import org.jeecg.modules.users.service.IOpUserOnlineService;
import org.jeecg.modules.users.util.AntiAddiction;
import org.jeecg.modules.users.util.HexStringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.jeecg.modules.users.constant.OnlineBtConstant.OFF_LINE;
import static org.jeecg.modules.users.constant.OnlineBtConstant.ON_LINE;
import static org.jeecg.modules.users.util.AntiAddiction.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: op_user_online
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_gateway")
public class OpUserOnlineServiceImpl extends
    ServiceImpl<OpUserOnlineMapper, OpUserOnline> implements IOpUserOnlineService {

    @Resource
    OpHolidayConfigMapper holidayConfigMapper;

    @Resource
    OpUserOnlineMapper opUserOnlineMapper;

    /**
     * 反沉迷数据上报
     */
    @Scheduled(cron = "0/10 * * * * ?")
    @Override
    public void report() {
        //删除上报完成的记录或者重试次数大于等于三次
        LambdaQueryWrapper<OpUserOnline> deleteMapper = new LambdaQueryWrapper<>();
        deleteMapper.eq(OpUserOnline::getState, REPORT_SUCCESS).or().ge(OpUserOnline::getRetryTimes, REPORT_RETRY_TIMES);
        opUserOnlineMapper.delete(deleteMapper);
        //查询所有的biz_id
        QueryWrapper<OpUserOnline> bizIdQueryWrapper = new QueryWrapper<>();
        bizIdQueryWrapper.select("DISTINCT biz_id");
        List<String> bizIds = opUserOnlineMapper.selectObjs(bizIdQueryWrapper).stream()
            .map(o -> (String) o).collect(Collectors.toList());
        for (String bizId : bizIds) {
            //上报登录
            doReport(bizId, REPORT_LOGIN);
            //上报登出
            doReport(bizId, REPORT_LOGOUT);
        }
    }

    /**
     * @param bizId:游戏备案号
     * @param state:上报标识
     * @return QueryWrapper<OpUserOnline>
     * @author czb
     * @description 构造查询条件
     * @date 2022/12/19 10:13
     */
    private LambdaQueryWrapper<OpUserOnline> createQueryWrapper(String bizId, Integer state) {
        //过去三分钟未上报的登录信息
        LambdaQueryWrapper<OpUserOnline> queryWrapper = new LambdaQueryWrapper<>();
        LocalDateTime now = LocalDateTime.now();
        queryWrapper.eq(OpUserOnline::getBizId, bizId);
        queryWrapper.last("limit " + REPORT_MAX);
        queryWrapper.eq(OpUserOnline::getState, state);
        if (state.equals(REPORT_LOGIN)) {
            queryWrapper.lt(OpUserOnline::getLoginTime, now);
            queryWrapper.gt(OpUserOnline::getLoginTime, now.minusSeconds(EXPIRE_TIME));
        }else {
            queryWrapper.lt(OpUserOnline::getLogoutTime, now);
            queryWrapper.gt(OpUserOnline::getLogoutTime, now.minusSeconds(EXPIRE_TIME));
            queryWrapper.isNotNull(OpUserOnline::getLogoutTime);
        }
        return queryWrapper;
    }

    private void doReport(String bizId, Integer state) {
        LambdaQueryWrapper<OpUserOnline> queryWrapper = createQueryWrapper(bizId, state);
        List<OpUserOnline> records = list(queryWrapper);
        if (records.size() == 0) {
            return;
        }
        //数据库数据转换成上报格式规范数据
        HashMap<String, Object> requestBodies = toRequestBodies(records);
        JSONObject jsonObject = doPostLoginOut(bizId, requestBodies);
        Integer errcode = jsonObject.getObject("errcode", Integer.class);
        //上报成功
        if (errcode.equals(SUCCESS_CODE)) {
            updateBatch(records);
        } else if (errcode.equals(PARTIAL_FAILURE)) { //存入数据库的信息有错的导致的问题信息不再重试，直接删除
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray results = data.getJSONArray("results");
            LinkedList<OpUserOnline> errRecords = new LinkedList<>();
            for (int i = 0; i <= results.size(); i++) {
                ReportErrResponse reportErrResponse = results
                    .getObject(i, ReportErrResponse.class);
                errRecords.add(records.get(reportErrResponse.getNo()));
            }
            records.removeAll(errRecords);
            updateBatch(records);//更新上报信息
            removeBatch(errRecords); //删除问题数据
        } else { //其他原因（网络过慢等等）导致的给三次重试机会
            updateBatchRetryTime(records);
        }
    }

    /**
     * 重试次数 + 1
     *
     * @param records
     */
    private void updateBatchRetryTime(List<OpUserOnline> records) {
        if (records.size() == 0) {
            return;
        }
        List<OpUserOnline> updateList = records.stream()
            .map(obj -> obj.setRetryTimes(obj.getRetryTimes() + 1)).collect(Collectors.toList());
        updateBatchById(updateList);
    }

    /**
     * 批量删除
     *
     * @param errRecords 问题信息
     */
    private void removeBatch(LinkedList<OpUserOnline> errRecords) {
        if (errRecords.size() == 0) {
            return;
        }
        List<Integer> delList = errRecords.stream().map(OpUserOnline::getId)
            .collect(Collectors.toList());
        removeByIds(delList);
    }

    /**
     * 批量修改
     *
     * @param records 上报信息
     */
    private void updateBatch(List<OpUserOnline> records) {
        if (records.size() == 0) {
            return;
        }
        List<OpUserOnline> updateList = records.stream()
            .map(obj -> obj.setState(obj.getState() + 1)).collect(Collectors.toList());
        updateBatchById(updateList);
    }

    /**
     * @param records:数据库数据
     * @return HashMap<String, Object> 上报反沉迷接口规范数据
     * @author czb
     * @description 转换数据格式
     * @date 2022/12/15 17:56
     */
    private HashMap<String, Object> toRequestBodies(List<OpUserOnline> records) {
        HashMap<String, Object> map = new HashMap<>();
        LinkedList<ReportRequestBody> requestBodies = new LinkedList<>();
        for (OpUserOnline record : records) {
            ReportRequestBody reportRequestBody = new ReportRequestBody();
            BeanUtils.copyProperties(record, reportRequestBody);
            //上报类型
            Integer state = record.getState();
            //游客上报或实名玩家上报
            Integer ct = record.getPi() == null ? NOT_REAL_NAME : REAL_NAME;
            reportRequestBody.setCt(ct);
            if (state.equals(REPORT_LOGIN)) { //上报登录
                reportRequestBody.setBt(ON_LINE);
                reportRequestBody.setOt(record.getLoginTime().toEpochSecond(ZoneOffset.of("+8")));
            } else { //上报登出
                reportRequestBody.setBt(OFF_LINE);
                reportRequestBody.setOt(record.getLogoutTime().toEpochSecond(ZoneOffset.of("+8")));
            }
            reportRequestBody.setNo(requestBodies.size() + 1);
            requestBodies.add(reportRequestBody);
        }
        map.put("collections", requestBodies);
        return map;
    }

    /**
     * @param bizId: 游戏备案号
     * @param paramMap:  上报数据
     * @return JSONObject 接口返回
     * @author czb
     * @description 上报流程
     * @date 2022/12/19 11:07
     */
    private JSONObject doPostLoginOut(String bizId, Map<String, Object> paramMap) {
        long nowTime = new Date().getTime();
        //处理密钥（将16进制字符串密钥转换为byte数组）
        byte[] keyBytes = HexStringUtil.hexStringToByte(AntiAddiction.SECRET_KEY);
        String content = JSONObject.toJSONString(paramMap);
        //业务参数加密（AES-128/GCM + BASE64算法加密）计算
        String encryptStr = aesGcmEncrypt(content, keyBytes);
//        System.out.println("业务参数解密结果：" + aesGcmDecrypt(encryptStr, keyBytes));

        String str = "";
        TreeMap<String, String> signMap = new TreeMap<>();
        signMap.put("appId", AntiAddiction.APP_ID);
        signMap.put("bizId", bizId);
        signMap.put("timestamps", "" + nowTime);
        for (Map.Entry<String, String> entry : signMap.entrySet()) {
            str += entry.getKey() + entry.getValue();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(AntiAddiction.SECRET_KEY);
        sb.append(str);
        sb.append("{\"data\":\"" + encryptStr + "\"}");
        //待签名字符串
        String toBeSignStr = sb.toString();

        String sign;
        try {
            sign = sign(toBeSignStr);
            log.info("sign:{}", sign);
        } catch (NoSuchAlgorithmException e) {
            throw new JeecgBootException("签名失败" + e);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-type", "application/json;charset=utf-8");
        httpHeaders.set("appId", signMap.get("appId"));
        httpHeaders.set("bizId", bizId);
        httpHeaders.set("timestamps", signMap.get("timestamps"));
        httpHeaders.set("sign", sign);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", encryptStr);
        return RestUtil.post(AntiAddiction.URL_LOGIN_OUT, httpHeaders, null, jsonObject);
    }

    /**
     * 保存防沉迷信息
     *
     * @param subGameId 子游戏id
     * @param pkgId     一级游戏包id
     * @param userId    用户id
     * @param state     上报类型 0：下线，1：上线
     * @param bizId     游戏备案码
     * @param device    设备
     * @param pi        实名认证
     * @return
     */

    public boolean saveOpUserOnline(Integer subGameId, Integer pkgId, Integer userId, Integer state, String bizId,
        String device, String pi) {
        QueryWrapper<OpUserOnline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).ne("sub_game_id", subGameId).ne("pkg_id", pkgId).ne("state", SUCCESS_CODE);
        Long count = opUserOnlineMapper.selectCount(queryWrapper);
        if (count != 0) { //存在其他游戏未上报信息
            return false;
        }
        OpUserOnline opUserOnline = new OpUserOnline();
        opUserOnline.setSubGameId(subGameId);
        opUserOnline.setPkgId(pkgId);
        opUserOnline.setUserId(userId);
        opUserOnline.setPi(pi);
        opUserOnline.setSi(Md5Util.md5Encode(APP_ID + userId, "UTF-8"));
        opUserOnline.setBizId(bizId);
        opUserOnline.setDi(Md5Util.md5Encode(device, "UTF-8"));
        if (state.equals(ON_LINE)) {
            opUserOnline.setLoginTime(LocalDateTime.now());
            return save(opUserOnline);
        } else {
            opUserOnline.setLogoutTime(LocalDateTime.now());
            return updateById(opUserOnline);
        }
    }

    // 签名
    private String sign(String toBeSignStr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(toBeSignStr.getBytes(UTF_8));
        return HexStringUtil.byteToHexString(messageDigest.digest());
    }

    private String aesGcmEncrypt(String content, byte[] key) {
        try {
            // 根据指定算法ALGORITHM自成密码器
            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
            SecretKeySpec skey = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            // 获取向量
            byte[] ivb = cipher.getIV();
            byte[] encodedByteArray = cipher.doFinal(content.getBytes(UTF_8));
            byte[] message = new byte[ivb.length + encodedByteArray.length];
            System.arraycopy(ivb, 0, message, 0, ivb.length);
            System.arraycopy(encodedByteArray, 0, message, ivb.length, encodedByteArray.length);
            return Base64.getEncoder().encodeToString(message);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param idNum:  身份证
     * @return CheckAdultData 未成年返回剩余游戏时间
     * @author czb
     * @description 检查用户是否成年
     * @date 2022/12/19 11:01
     */
    public CheckAdultData checkAdult(String idNum) {
        //年
        String year = idNum.substring(6, 10);
        //月
        String month = idNum.substring(10, 12);
        //日
        String day = idNum.substring(12, 14);
        //出生日期
        LocalDateTime birthTime = LocalDateTime
            .of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
        //当前时间
        LocalDateTime nowTime = LocalDateTime.now();

        CheckAdultData checkAdultData = new CheckAdultData();
        //是否成年
        if (birthTime.plusYears(18).isBefore(nowTime)) {
            checkAdultData.setIsAdult(IsConstant.YES);
            return checkAdultData;
        }else{
            checkAdultData.setIsAdult(IsConstant.NO);
        }
        int hour = nowTime.getHour();
        //未成年，不是晚上8点到9点
        if (hour < 20 || hour > 21) {
            return checkAdultData;
        }
        DayOfWeek dayOfWeek = nowTime.getDayOfWeek();
        boolean isLogin = false;
        //周末
        if (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY
            || dayOfWeek == DayOfWeek.SUNDAY) {
            isLogin = true;
        } else { //不是周末，判断节假日
            QueryWrapper<OpHolidayConfig> opHolidayConfigQueryWrapper = new QueryWrapper<>();
            opHolidayConfigQueryWrapper.eq("holiday", new Date());
            if (holidayConfigMapper.exists(opHolidayConfigQueryWrapper)) {
                isLogin = true;
            }
        }
        //允许登录计算剩余时间
        if (isLogin) {
            LocalDateTime offTime = nowTime.withHour(21);
            long nowTimeSeconds = nowTime.toEpochSecond(ZoneOffset.of("+8"));
            long offTimeSeconds = offTime.toEpochSecond(ZoneOffset.of("+8"));
            long remainingTime = offTimeSeconds - nowTimeSeconds;
            if (remainingTime > 0) {
                checkAdultData.setRemainingTime(remainingTime);
            }
        }
        return checkAdultData;
    }

}
