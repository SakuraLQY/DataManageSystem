package org.jeecg.modules.users.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpGameModel;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.users.constant.RuleTypeConstant;
import org.jeecg.modules.users.entity.OpRegisterLoginSwitch;
import org.jeecg.modules.users.mapper.OpRegisterLoginSwitchMapper;
import org.jeecg.modules.users.service.IOpRegisterLoginSwitchService;
import org.jeecg.modules.users.vo.OpRegisterLoginSwitchVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;

/**
 * @Description: op_register_login_switch
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpRegisterLoginSwitchServiceImpl extends
    ServiceImpl<OpRegisterLoginSwitchMapper, OpRegisterLoginSwitch> implements
    IOpRegisterLoginSwitchService {

    @Autowired
    private OpRegisterLoginSwitchMapper opRegisterLoginSwitchMapper;
    @Autowired
    private IGameApi gameApi;
    @Autowired
    private IAdvertApi advertApi;

    @Override
    public IPage<OpRegisterLoginSwitchVo> selectList(Page<OpRegisterLoginSwitch> page,
        OpRegisterLoginSwitch opRegisterLoginSwitch) {
        LambdaQueryWrapper<OpRegisterLoginSwitch> wrapper = new LambdaQueryWrapper<>();
        if (null != opRegisterLoginSwitch.getRuleType()) {
            wrapper.eq(OpRegisterLoginSwitch::getRuleType, opRegisterLoginSwitch.getRuleType());
        }
        if (null != opRegisterLoginSwitch.getRuleId()) {
            wrapper.eq(OpRegisterLoginSwitch::getRuleId, opRegisterLoginSwitch.getRuleId());
        }
        List<OpRegisterLoginSwitch> list = opRegisterLoginSwitchMapper.selectList(wrapper);
        List<OpRegisterLoginSwitchVo> opRegisterLoginSwitchVoList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (OpRegisterLoginSwitch obj : list) {
                OpRegisterLoginSwitchVo opRegisterLoginSwitchVo = new OpRegisterLoginSwitchVo();
                BeanUtils.copyProperties(obj, opRegisterLoginSwitchVo);
                if (opRegisterLoginSwitchVo.getRuleType() == 1) {
                    OpGameModel opGameModel = gameApi.getOpGame(opRegisterLoginSwitchVo.getRuleId());
                    opRegisterLoginSwitchVo.setRuleName(opGameModel.getGameName());
                }else if (opRegisterLoginSwitchVo.getRuleType() == 2) {
                    OpSubGameModel opSubGameModel = gameApi.getOpSubGame(opRegisterLoginSwitchVo.getRuleId());
                    opRegisterLoginSwitchVo.setRuleName(opSubGameModel.getGameName());
                }else if (opRegisterLoginSwitchVo.getRuleType() == 3){
                    OpDealModel opDealModel = advertApi.getOpDeal(opRegisterLoginSwitchVo.getRuleId());
                    opRegisterLoginSwitchVo.setRuleName(opDealModel.getDealName());
                }else {
                    OpPkgModel opPkgModel = gameApi.getOpPkgById(opRegisterLoginSwitchVo.getRuleId());
                    opRegisterLoginSwitchVo.setRuleName(opPkgModel.getPkgName());
                }
                opRegisterLoginSwitchVoList.add(opRegisterLoginSwitchVo);
            }
        }
        IPage<OpRegisterLoginSwitchVo> pageList = new Page<>(page.getCurrent(), page.getSize());
        pageList.setRecords(Lists.reverse(opRegisterLoginSwitchVoList));
        pageList.setTotal(opRegisterLoginSwitchVoList.size());
        return pageList;
    }

    @Override
    public void checkRuleId(OpRegisterLoginSwitch opRegisterLoginSwitch) {
        LambdaQueryWrapper<OpRegisterLoginSwitch> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpRegisterLoginSwitch::getRuleType, opRegisterLoginSwitch.getRuleType());
        wrapper.eq(OpRegisterLoginSwitch::getRuleId, opRegisterLoginSwitch.getRuleId());
        OpRegisterLoginSwitch orls = opRegisterLoginSwitchMapper.selectOne(wrapper);
        if (opRegisterLoginSwitch.getId() != null) {
            OpRegisterLoginSwitch s = opRegisterLoginSwitchMapper.selectById(
                opRegisterLoginSwitch.getId());
            if (!Objects.equals(s.getRuleId(), opRegisterLoginSwitch.getRuleId())
                || !Objects.equals(
                s.getRuleType(), opRegisterLoginSwitch.getRuleType())) {
                if (oConvertUtils.isNotEmpty(orls)) {
                    throw new IdeaRunTimeException(ErrorCode.RULE_NOT_REPEATING);
                }
            }
        } else {
            if (oConvertUtils.isNotEmpty(orls)) {
                throw new IdeaRunTimeException(ErrorCode.RULE_NOT_REPEATING);
            }
        }
    }

    @Override
    public void add(OpRegisterLoginSwitch opRegisterLoginSwitch) {
        checkRuleId(opRegisterLoginSwitch);
        save(opRegisterLoginSwitch);
    }

    @Override
    public void update(OpRegisterLoginSwitch opRegisterLoginSwitch) {
        checkRuleId(opRegisterLoginSwitch);
        updateById(opRegisterLoginSwitch);
    }

    public List<OpRegisterLoginSwitch> getAllSwitchList() {
        List<OpRegisterLoginSwitch> opRegisterLoginSwitchList = list();
        return opRegisterLoginSwitchList;
    }

}
