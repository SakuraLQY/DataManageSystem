package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.time.DateFormatUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.Md5Util;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.dto.UserLogDto;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.entity.LgLogin;
import org.jeecg.modules.count.mapper.LgLoginMapper;
import org.jeecg.modules.count.service.ILgLoginService;
import org.jeecg.modules.count.vo.UserLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: lg_login
 * @Author: jeecg-boot
 * @Date: 2023-04-19
 * @Version: V1.0
 */
@Service
@DS("sharding")
public class LgLoginServiceImpl extends ServiceImpl<LgLoginMapper, LgLogin> implements
    ILgLoginService {

    @Autowired
    private LgLoginMapper lgLoginMapper;

    @Override
    public void insertLoginLog(ParseLoginDto parseLoginDto, CtUser ctUser, CtDevice ctDevice) {
        LgLogin lgLogin = new LgLogin();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(parseLoginDto.getTime());
        Date time = DateUtils.str2Date(date, simpleDateFormat);

        lgLogin.setGameId(parseLoginDto.getGameId());
        lgLogin.setPkgId(parseLoginDto.getPkgId());
        lgLogin.setSubGameId(parseLoginDto.getSubGameId());
        lgLogin.setChannelId(parseLoginDto.getChannelId());
        lgLogin.setChannelTypeId(parseLoginDto.getChannelTypeId());
        lgLogin.setChannelSubAccountId(parseLoginDto.getChannelSubAccountId());
        lgLogin.setDealId(parseLoginDto.getDealId());

        lgLogin.setRegisterTime(ctUser.getCreateTime());
        lgLogin.setUniqueId(ctDevice.getUniqueId());
        lgLogin.setUserId(ctUser.getUserId());
        lgLogin.setLoginTime(time);
        lgLogin.setLoginIp(parseLoginDto.getClientIp());

        lgLoginMapper.insert(lgLogin);
    }

    @Override
    public List<String> getUseLoginLog(UserLogDto userLogDto) {
        if (null == userLogDto.getUserId()) {
            throw new JeecgBootException("用户为空");
        }
        Date startTime = null;
        Date endTime = null;
        try {
            if (null != userLogDto.getStartTime()) {
                startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(userLogDto.getStartTime(), "yyyy-MM-dd 00:00:00"));
            }
            if (null != userLogDto.getEndTime()) {
                endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                    DateFormatUtils.format(userLogDto.getEndTime(), "yyyy-MM-dd 23:59:59"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LambdaQueryWrapper<LgLogin> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LgLogin::getUserId, userLogDto.getUserId());
        if (null != startTime) {
            wrapper.ge(LgLogin::getLoginTime, startTime);
        }
        if (null != endTime) {
            wrapper.le(LgLogin::getLoginTime, endTime);
        }
        List<LgLogin> list = lgLoginMapper.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        }
        Map<String, UserLogVo> map = new LinkedHashMap<>();
        for (LgLogin lgLogin : list) {
            String key = Md5Util.md5Encode(
                String.valueOf(lgLogin.getDealId()) + lgLogin.getLoginTime(), "utf-8");
            if (oConvertUtils.isNotEmpty(map.get(key))) {
                map.get(key).setNum(map.get(key).getNum() + 1);
            } else {
                UserLogVo userLogVo = new UserLogVo();
                userLogVo.setLoginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lgLogin.getLoginTime()));
                userLogVo.setLoginIp(lgLogin.getLoginIp());
                userLogVo.setDealId(lgLogin.getDealId());
                userLogVo.setNum(1);
                map.put(key, userLogVo);
            }
        }
        List<String> resList = new ArrayList<>();
        for (String key : map.keySet()) {
            String msg = "";
            if (map.get(key).getNum() != 1) {
                msg += "时间：" + map.get(key).getLoginTime() + " 广告id：" + map.get(key).getDealId()
                    + " 登录IP：" + map.get(key).getLoginIp() + " 次数：" + map.get(key).getNum();
            }else {
                msg += "时间：" + map.get(key).getLoginTime() + " 广告id：" + map.get(key).getDealId()
                    + " 登录IP：" + map.get(key).getLoginIp();
            }
            resList.add(msg);
        }
        return resList;
    }
}
