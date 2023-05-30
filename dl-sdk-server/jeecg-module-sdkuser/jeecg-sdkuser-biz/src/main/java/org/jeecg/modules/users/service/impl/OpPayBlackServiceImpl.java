package org.jeecg.modules.users.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.users.common.ResultPayBlackList;
import org.jeecg.modules.users.entity.OpPayBlack;
import org.jeecg.modules.users.entity.OpRegisterLoginSwitch;
import org.jeecg.modules.users.mapper.OpPayBlackMapper;
import org.jeecg.modules.users.service.IOpPayBlackService;
import org.jeecg.modules.users.vo.OpPayBlackVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_pay_black
 * @Author: jeecg-boot
 * @Date: 2022-12-19
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpPayBlackServiceImpl extends ServiceImpl<OpPayBlackMapper, OpPayBlack> implements
    IOpPayBlackService {

    @Resource
    private OpPayBlackMapper opPayBlackMapper;

    @Override
    public IPage<OpPayBlackVo> getPayBlackList(Page<T> page, OpPayBlack opPayBlack) {
        QueryWrapper<OpPayBlackVo> q = new QueryWrapper<>();
        if (null != opPayBlack.getRuleId()) {
            q.eq("a.rule_id", opPayBlack.getRuleId());
        }
        if (null != opPayBlack.getRuleType()) {
            q.eq("a.rule_type", opPayBlack.getRuleType());
        }
        IPage<OpPayBlackVo> pageList = baseMapper.getPayBlackList(page, q);
        return pageList;
    }

    @Override
    public List<OpPayBlackVo> getOptionList(OpPayBlack opPayBlack) {
        if (opPayBlack.getRuleType() != 0) {
            return opPayBlackMapper.getSubGameOptionList();
        }
        return opPayBlackMapper.getGameOptionList();
    }

    @Override
    public Boolean checkUser(Integer userId, Integer gameId, Integer subGameId, String ip,
        String device) {
        //检查是否存在此游戏拉黑名单
        LambdaQueryWrapper<OpPayBlack> q = new LambdaQueryWrapper<>();
        if (null != gameId) {
            q.eq(OpPayBlack::getRuleId, gameId);
        }
        OpPayBlack opPayBlack = opPayBlackMapper.selectOne(q);
        //没有返回false
        if (oConvertUtils.isEmpty(opPayBlack)) {
            return false;
        }
        String payLimitUserId = opPayBlack.getPayLimitUserId();
        String[] strList = payLimitUserId.split("\\r?\\n");
        if (Arrays.asList(strList).contains(String.valueOf(userId))) {
            return true;
        }
        String payLimitIp = opPayBlack.getPayLimitIp();
        strList = payLimitIp.split("\\r?\\n");
        if (Arrays.asList(strList).contains(ip)) {
            return true;
        }
        String payLimitDevice = opPayBlack.getPayLimitDevice();
        strList = payLimitDevice.split("\\r?\\n");
        if (Arrays.asList(strList).contains(device)) {
            return true;
        }
        //检查是否存在此子游戏拉黑名单
        q = new LambdaQueryWrapper<>();
        if (null != subGameId) {
            q.eq(OpPayBlack::getRuleId, subGameId);
        }
        OpPayBlack opPayBlackSub = opPayBlackMapper.selectOne(q);
        //没有返回false
        if (oConvertUtils.isEmpty(opPayBlackSub)) {
            return false;
        }
        payLimitUserId = opPayBlackSub.getPayLimitUserId();
        strList = payLimitUserId.split("\\r?\\n");
        if (Arrays.asList(strList).contains(String.valueOf(userId))) {
            return true;
        }
        payLimitIp = opPayBlackSub.getPayLimitIp();
        strList = payLimitIp.split("\\r?\\n");
        if (Arrays.asList(strList).contains(ip)) {
            return true;
        }
        payLimitDevice = opPayBlackSub.getPayLimitDevice();
        strList = payLimitDevice.split("\\r?\\n");
        if (Arrays.asList(strList).contains(device)) {
            return true;
        }
        return false;
    }

    @Override
    public void checkRuleId(OpPayBlack opPayBlack) {
        LambdaQueryWrapper<OpPayBlack> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpPayBlack::getRuleType, opPayBlack.getRuleType());
        wrapper.eq(OpPayBlack::getRuleId, opPayBlack.getRuleId());
        OpPayBlack opb = opPayBlackMapper.selectOne(wrapper);
        if (opPayBlack.getId() != null) {
            OpPayBlack s = opPayBlackMapper.selectById(opPayBlack.getId());
            if (!Objects.equals(s.getRuleId(), opPayBlack.getRuleId()) || !Objects.equals(
                s.getRuleType(), opPayBlack.getRuleType())) {
                if (oConvertUtils.isNotEmpty(opb)) {
                    throw new IdeaRunTimeException(ErrorCode.RULE_NOT_REPEATING);
                }
            }
        } else {
            if (oConvertUtils.isNotEmpty(opb)) {
                throw new IdeaRunTimeException(ErrorCode.RULE_NOT_REPEATING);
            }
        }
    }

    @Override
    public void add(OpPayBlack opPayBlack) {
        checkRuleId(opPayBlack);
        save(opPayBlack);
    }

    @Override
    public void update(OpPayBlack opPayBlack) {
        checkRuleId(opPayBlack);
        updateById(opPayBlack);
    }
}
