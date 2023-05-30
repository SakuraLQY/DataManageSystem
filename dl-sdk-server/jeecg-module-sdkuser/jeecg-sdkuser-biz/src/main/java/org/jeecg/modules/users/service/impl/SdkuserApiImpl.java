package org.jeecg.modules.users.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.api.ISdkuserApi;
import org.jeecg.modules.users.constant.RuleTypeConstant;
import org.jeecg.modules.users.entity.OpRegisterLoginSwitch;
import org.jeecg.modules.users.entity.OpUser;
import org.jeecg.modules.users.mapper.OpRegisterLoginSwitchMapper;
import org.jeecg.modules.users.service.IOpPayBlackService;
import org.jeecg.modules.users.service.IOpRegisterLoginSwitchService;
import org.jeecg.modules.users.service.IOpUserService;
import org.jeecg.modules.users.util.RedisUtils;
import org.jeecg.modules.vo.OpRegisterLoginSwitchVo;
import org.jeecg.modules.vo.OpUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@DS("open_gateway")
public class SdkuserApiImpl implements ISdkuserApi {

    @Resource
    private IOpPayBlackService opPayBlackService;
    @Resource
    private IOpUserService opUserService;
    @Resource
    private IOpRegisterLoginSwitchService opRegisterLoginSwitchService;

    public Boolean checkBlackUser(Integer userId, Integer gameId, Integer subGameId, String ip, String device){
        return opPayBlackService.checkUser(userId, gameId, subGameId, ip, device);
    }

    @Override
    public OpUserVo getOpUserVoById(Integer id) {
        OpUser opUser = opUserService.getOpUserById(id);
        if(null == opUser){
            return null;
        }
        OpUserVo opUserVo = new OpUserVo();
        BeanUtils.copyProperties(opUser, opUserVo);
        return opUserVo;
    }

    @Override
    @CacheEvict(cacheNames = RedisUtils.OP_USER, key = "#userId")
    public boolean updateUserPC(Integer userId, Integer platformCurrent, boolean isAdd) {
        UpdateWrapper<OpUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", userId);
        if(isAdd){
            wrapper.setSql("`platform_currency` = `platform_currency` + " + platformCurrent);
        }else{
            wrapper.setSql("`platform_currency` = `platform_currency` - " + platformCurrent);
        }
        wrapper.set("charge_time", new Date());
        return opUserService.update(wrapper);
    }

    @Override
    public List<OpRegisterLoginSwitchVo> getListByDealIdList(List<Integer> dealIdList) {
        LambdaQueryWrapper<OpRegisterLoginSwitch> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpRegisterLoginSwitch::getRuleType, RuleTypeConstant.DEAL);
        wrapper.in(OpRegisterLoginSwitch::getRuleId, dealIdList);
        List<OpRegisterLoginSwitch> list = opRegisterLoginSwitchService.list(wrapper);
        List<OpRegisterLoginSwitchVo> resList = new ArrayList<>();
        for (OpRegisterLoginSwitch opRegisterLoginSwitch : list) {
            OpRegisterLoginSwitchVo opRegisterLoginSwitchVo = new OpRegisterLoginSwitchVo();
            BeanUtils.copyProperties(opRegisterLoginSwitch, opRegisterLoginSwitchVo);
            resList.add(opRegisterLoginSwitchVo);
        }
        return resList;
    }

    @Override
    public Boolean saveOpRegisterLoginSwitch(OpRegisterLoginSwitchVo opRegisterLoginSwitchVo) {
        OpRegisterLoginSwitch opRegisterLoginSwitch = new OpRegisterLoginSwitch();
        BeanUtils.copyProperties(opRegisterLoginSwitchVo, opRegisterLoginSwitch);
        if (null != opRegisterLoginSwitch.getId()) {
            return opRegisterLoginSwitchService.updateById(opRegisterLoginSwitch);
        }
        return opRegisterLoginSwitchService.save(opRegisterLoginSwitch);
    }
}
