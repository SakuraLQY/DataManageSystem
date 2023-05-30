package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.advert.entity.OpJrttAction;
import org.jeecg.modules.advert.mapper.OpDealMapper;
import org.jeecg.modules.advert.mapper.OpJrttActionMapper;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IOpJrttActionService;
import org.springframework.stereotype.Service;

/**
 * @Description: ad_deal
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class OpJrttActionServiceImpl extends
    ServiceImpl<OpJrttActionMapper, OpJrttAction> implements
    IOpJrttActionService {

    @Override
    public List<OpJrttAction> queryByActionType() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        LambdaQueryWrapper<OpJrttAction> lambdaQueryWrapper = new LambdaQueryWrapper<OpJrttAction>()
            .eq(OpJrttAction::getCreateBy, loginUser.getUsername());
        List<OpJrttAction> list = list(lambdaQueryWrapper);
        return list;
    }

    @Override
    public void saveAction(OpJrttAction opJrttAction) {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        LambdaQueryWrapper<OpJrttAction> lambdaQueryWrapper = new LambdaQueryWrapper<OpJrttAction>()
            .eq(OpJrttAction::getCreateBy, loginUser.getUsername())
            .eq(OpJrttAction::getActionType, opJrttAction.getActionType())
            .eq(OpJrttAction::getActionContent, opJrttAction.getActionContent());
        if (count(lambdaQueryWrapper) < 1) {
            save(opJrttAction);
        }
    }
}
