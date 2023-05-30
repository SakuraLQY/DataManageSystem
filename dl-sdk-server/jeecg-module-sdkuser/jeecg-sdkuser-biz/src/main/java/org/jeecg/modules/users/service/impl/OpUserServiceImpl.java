package org.jeecg.modules.users.service.impl;

import cn.hutool.core.util.IdcardUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.constant.IsConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.config.SdkConfig;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.modules.users.bo.CheckAdultData;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.modules.users.dto.SdkIdAuthDto;
import org.jeecg.modules.users.service.IOpUserOnlineService;
import org.jeecg.common.util.Md5Util;
import org.jeecg.common.util.oConvertUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.users.entity.OpUser;
import org.jeecg.modules.users.entity.OpUserRealnameInfo;
import org.jeecg.modules.users.entity.OpUserRealnamedGame;
import org.jeecg.modules.users.mapper.OpUserMapper;
import org.jeecg.modules.users.mapper.OpUserRealnameInfoMapper;
import org.jeecg.modules.users.mapper.OpUserRealnamedGameMapper;
import org.jeecg.modules.users.service.IOpUserRealnameInfoService;
import org.jeecg.modules.users.service.IOpUserRealnamedGameService;
import org.jeecg.modules.users.service.IOpUserService;
import org.jeecg.modules.users.util.Amarket;
import org.jeecg.modules.users.util.AntiAddiction;
import org.jeecg.modules.users.util.RedisUtils;
import org.jeecg.modules.users.util.RegularUtils;
import org.jeecg.modules.users.vo.OpUserVo;
import org.jeecg.modules.users.vo.SdkUserIdAuthRes;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: op_user
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_gateway")
public class OpUserServiceImpl extends ServiceImpl<OpUserMapper, OpUser> implements IOpUserService {

    @Resource
    private SdkConfig sdkConfig;
    @Resource
    private OpUserRealnameInfoMapper opUserRealnameInfoMapper;
    @Resource
    private OpUserRealnamedGameMapper opUserRealnamedGameMapper;
    @Resource
    private IOpUserRealnameInfoService opUserRealnameInfoService;
    @Resource
    private IOpUserRealnamedGameService opUserRealnamedGameService;
    @Resource
    private IOpUserOnlineService opUserOnlineService;

    @Override
    @CacheEvict(cacheNames = RedisUtils.OP_USER, key = "#id")
    public void editPass(Integer id, String userPassword) {
        if (id == null || oConvertUtils.isEmpty(userPassword)) {
            throw new JeecgBootException("密码未填写！");
        }
        String regex = "^[\\w]{6,16}$";//正则表达式
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userPassword);
        if (!m.matches()) {
            throw new JeecgBootException("密码格式有误！");
        }
        userPassword += sdkConfig.getLoginSessionSalt();
        userPassword = Md5Util.md5Encode(userPassword, "utf-8");
        UpdateWrapper<OpUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("user_password", userPassword);
        update(null, wrapper);
    }

    @Override
    @CacheEvict(cacheNames = RedisUtils.OP_USER, key = "#id")
    public void editPlatformCurrency(Integer id, String platformCurrency) {
        if (id == null || oConvertUtils.isEmpty(platformCurrency)) {
            throw new JeecgBootException("平台币未填写！");
        }
        String regex = "^\\d+$";//正则表达式
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(platformCurrency);
        if (!m.matches()) {
            throw new JeecgBootException("平台币格式有误！");
        }
        UpdateWrapper<OpUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("platform_currency", platformCurrency);
        update(null, wrapper);
    }

    @Override
    @CacheEvict(cacheNames = RedisUtils.OP_USER, key = "#id")
    public void removePhone(Integer id) {
        if (id == null) {
            throw new JeecgBootException("参数未传！");
        }
        UpdateWrapper<OpUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("user_phone", "");
        update(null, wrapper);
    }

    @Override
    public SdkUserIdAuthRes idAuth(SdkIdAuthDto sdkIdAuthDto) {
        log.info("sdk 实名认证参数:{}", sdkIdAuthDto);
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        // 前置判断
        if (!IdcardUtil.isValidCard(sdkIdAuthDto.getIdNumber())) {
            throw new IdeaRunTimeException(ErrorCode.WRONG_ID_NUMBER);
        }
        if (!RegularUtils.checkRealName(sdkIdAuthDto.getRealName())) {
            throw new IdeaRunTimeException(ErrorCode.ILLEGAL_NAME);
        }
        OpUser opUser = getOpUserById(sdkIdAuthDto.getUserId());
        if (oConvertUtils.isEmpty(opUser)) {
            throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SDK_USER);
        }
        if (oConvertUtils.isNotEmpty(sdkInfo.getOpPkgModel())) {
            return singleGameIdAuth(sdkIdAuthDto.getSubGameId(), sdkIdAuthDto.getPkgId(), sdkIdAuthDto.getUserId(),
                sdkIdAuthDto.getIdNumber(), sdkIdAuthDto.getRealName(),
                sdkInfo.getOpPkgModel().getIdAuthApi(),
                sdkInfo.getOpPkgModel().getOfficialAntiIndulgeSwitch(),
                sdkInfo.getOpPkgModel().getOfficialBizId());
        }else {
            return singleGameIdAuth(sdkIdAuthDto.getSubGameId(), sdkIdAuthDto.getPkgId(), sdkIdAuthDto.getUserId(),
                sdkIdAuthDto.getIdNumber(), sdkIdAuthDto.getRealName(),
                sdkInfo.getOpSubGameModel().getIdAuthApi(),
                sdkInfo.getOpSubGameModel().getOfficialAntiIndulgeSwitch(),
                sdkInfo.getOpSubGameModel().getOfficialBizId());
        }

    }

    @Override
    public IPage<OpUserVo> SysUserPage(Page<T> page, OpUser opUser) {
        QueryWrapper<OpUserVo> q = new QueryWrapper<>();
        if (null != opUser.getUserName() && opUser.getUserName().trim().length() > 0) {
            q.like("a.user_name", opUser.getUserName());
        }
        if (null != opUser.getUserPhone() && opUser.getUserPhone().trim().length() > 0) {
            q.like("a.user_phone", opUser.getUserPhone());
        }
        if (null != opUser.getId()) {
            q.eq("a.id", opUser.getId());
        }
        return baseMapper.getUserList(page, q);
    }


    @Override
    @Cacheable(cacheNames = RedisUtils.OP_USER, key = "#id")
    public OpUser getOpUserById(Integer id) {
        return getById(id);
    }

    @Override
    public OpUser getOpUserByUserName(String userName) {
        return getOne(new QueryWrapper<OpUser>().lambda().eq(OpUser::getUserName, userName));
    }

    @Override
    public OpUser getOpUserByUserPhone(String userPhone) {
        return getOne(new QueryWrapper<OpUser>().lambda().eq(OpUser::getUserPhone, userPhone));
    }

    @Override
    @CacheEvict(cacheNames = RedisUtils.OP_USER, key = "#opUser.getId()")
    public void updateOpUserById(OpUser opUser) {
        updateById(opUser);
    }

    /**
     * @param subGameId:    子游戏ID
     * @param userId:   用户ID
     * @param idNumber: 身份证号
     * @param realName: 姓名
     * @return: JSONObject
     * @description: 单独游戏实名认证
     * @author: xmh
     * @date: 2022/12/6 14:41
     */
    private SdkUserIdAuthRes singleGameIdAuth(Integer subGameId, Integer pkgId, Integer userId, String idNumber,
        String realName, int idAuthApi, int officialAntiIndulge, String officialBizId) {
        // 判断该身份信息在该游戏中是否被绑定
        LambdaQueryWrapper<OpUserRealnamedGame> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpUserRealnamedGame::getPkgId, pkgId);
        wrapper.eq(OpUserRealnamedGame::getRealNumber, idNumber);
        OpUserRealnamedGame repeatIdNumber = opUserRealnamedGameMapper.selectOne(wrapper);
        if (oConvertUtils.isNotEmpty(repeatIdNumber)) {
            throw new IdeaRunTimeException(ErrorCode.ID_NUMBER_IS_BIND);
        }
        // 初始化
        OpUserRealnameInfo opUserRealnameInfo = new OpUserRealnameInfo();
        opUserRealnameInfo.setUserId(userId);
        opUserRealnameInfo.setRealName(realName);
        opUserRealnameInfo.setRealNumber(idNumber);

        OpUserRealnamedGame gameUser = new OpUserRealnamedGame();
        gameUser.setUserId(userId);
        gameUser.setSubGameId(subGameId);
        gameUser.setPkgId(pkgId);
        gameUser.setRealName(realName);
        gameUser.setRealNumber(idNumber);
        // 本账户实名信息
        OpUserRealnameInfo realnameInfo = opUserRealnameInfoService.getOpUserRealnameInfoByUserId(
            userId);
        // 该身份证号实名信息
        LambdaQueryWrapper<OpUserRealnameInfo> infoWrapper = new LambdaQueryWrapper<>();
        infoWrapper.eq(OpUserRealnameInfo::getRealNumber, idNumber);
        infoWrapper.ne(OpUserRealnameInfo::getRealPi, "");
        OpUserRealnameInfo idNumberRealnameInfo = opUserRealnameInfoMapper.selectOne(infoWrapper);
        // 身份证未被验证过
        if (oConvertUtils.isEmpty(idNumberRealnameInfo)) {
            // 直接更新实名相关信息
            String pi = doIdAuth(userId, realName, idNumber, idAuthApi, officialAntiIndulge,
                officialBizId);
            // 保存实名信息
            opUserRealnameInfo.setRealPi(pi);
            opUserRealnameInfoService.editUserRealnameInfo(opUserRealnameInfo);
            // 同步
            opUserRealnamedGameService.editUserRealnamedGame(gameUser);
        } else {
            // 身份证被占用
            // 在开始时第三方api检验，被占用时无论真实名假实名都不用走中宣部检验了
            if (idAuthApi == 1) {
                amarketCheckId(realName, idNumber);
            }
            // 未实名
            if (oConvertUtils.isEmpty(realnameInfo) || oConvertUtils.isEmpty(
                realnameInfo.getRealNumber())) {
                // 保存为假实名
                opUserRealnameInfo.setRealPi("");
                opUserRealnameInfoService.editUserRealnameInfo(opUserRealnameInfo);
            }
            // 未实名 / 假实名 / 正常实名 最终都同步到实名游戏表中
            opUserRealnamedGameService.editUserRealnamedGame(gameUser);
        }

        // 防沉迷功能
        CheckAdultData checkAdultData = opUserOnlineService.checkAdult(idNumber);

        SdkUserIdAuthRes sdkUserIdAuthRes = new SdkUserIdAuthRes();
        sdkUserIdAuthRes.setUserId(userId);
        sdkUserIdAuthRes.setIsAdult(checkAdultData.getIsAdult());
        sdkUserIdAuthRes.setRemainingTime(checkAdultData.getRemainingTime());
        return sdkUserIdAuthRes;
    }

    /**
     * @param userId:              用户ID
     * @param realName:            姓名
     * @param idNumber:            身份证号
     * @param idAuthApi:           是否开启第三方api验证
     * @param officialAntiIndulge: 是否接入中宣部
     * @param officialBizId:       中宣部备案号
     * @return: String
     * @description: 执行实名认证
     * @author: xmh
     * @date: 2022/12/15 10:10
     */
    private String doIdAuth(Integer userId, String realName, String idNumber, int idAuthApi,
        int officialAntiIndulge, String officialBizId) {
        // 真实实名认证pi
        String pi = "";
        // 实名认证状态
        boolean realnameStatus = AntiAddiction.NO_NEED_API;
        // 是否需要第三方验证
        boolean isOfficial = AntiAddiction.UN_USE_OFFICIAL;
        // 接入中宣部并且备案号不为空
        if (officialAntiIndulge == 1 && oConvertUtils.isNotEmpty(officialBizId)) {
            // 不需要第三方验证
            isOfficial = AntiAddiction.USE_OFFICIAL;
            // 构造 ai
            String user = userId + "" + new Date().getTime();
            String ai = Md5Util.md5Encode(user, "utf-8");
            // 执行实名认证
            JSONObject response = AntiAddiction.checkIdCard(ai, realName, idNumber, officialBizId);
            // 判断状态
            JSONObject result = getResponseStatus(response);
            Integer status = result.getInteger("status");
            if (status.equals(AntiAddiction.CODE_AUTH_ERR)) {
                throw new IdeaRunTimeException(ErrorCode.WRONG_AUTH_INFO);
            } else if (status.equals(AntiAddiction.CODE_AUTH_SUCCESS)) {
                pi = result.getString("pi");
            } else {
                // 验证中，调用查询接口
                JSONObject queryResponse = AntiAddiction.queryIdCard(ai, officialBizId);
                // 判断状态
                JSONObject queryResult = getResponseStatus(queryResponse);
                Integer queryStatus = queryResult.getInteger("status");
                if (queryStatus.equals(AntiAddiction.CODE_AUTH_SUCCESS)) {
                    pi = queryResult.getString("pi");
                } else if (queryStatus.equals(AntiAddiction.CODE_AUTH_WAIT)) {
                    // 验证中，走第三方api验证
                    isOfficial = AntiAddiction.UN_USE_OFFICIAL;
                    realnameStatus = AntiAddiction.NEED_API;
                } else {
                    throw new IdeaRunTimeException(ErrorCode.WRONG_AUTH_INFO);
                }
            }
        }

        if ((idAuthApi == 1 && isOfficial) || realnameStatus) {
            amarketCheckId(realName, idNumber);
        }
        return pi;
    }

    /**
     * 获取接口响应状态
     *
     * @param response:
     * @return: JSONObject
     * @description:
     * @author: xmh
     * @date: 2022/12/5 16:09
     */
    private JSONObject getResponseStatus(JSONObject response) {
        // 判断接口是否正常
        Integer errCode = response.getInteger("errcode");
        if (!errCode.equals(AntiAddiction.CODE_SUCCESS)) {
            // 异常时返回状态为验证中，将会进入第三方api验证
            JSONObject result = new JSONObject();
            result.put("status", AntiAddiction.CODE_AUTH_WAIT);
            log.error("接口状态异常:" + response);
            return result;
        }
        JSONObject data = response.getJSONObject("data");
        return data.getJSONObject("result");
    }

    /**
     * 第三方api验证
     *
     * @param realName:
     * @param idNumber:
     * @description:
     * @author: xmh
     * @date: 2022/12/5 15:33
     */
    private void amarketCheckId(String realName, String idNumber) {
        int result = Amarket.checkId(realName, idNumber);

        if (result == Amarket.CODE_ERR) {
            throw new IdeaRunTimeException(ErrorCode.AMARKET_ERR);
        } else if (result == Amarket.CODE_MIS_MATCH) {
            throw new IdeaRunTimeException(ErrorCode.AUTH_MIS_MATCH);
        }
    }
}
