package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.util.List;
import org.jeecg.modules.advert.entity.OpAnchorPlan;
import org.jeecg.modules.advert.mapper.OpAnchorPlanMapper;
import org.jeecg.modules.advert.service.IOpAnchorPlanService;
import org.jeecg.modules.advert.vo.CampaignAnchorVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_anchor_plan
 * @Author: jeecg-boot
 * @Date:   2023-02-28
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpAnchorPlanServiceImpl extends ServiceImpl<OpAnchorPlanMapper, OpAnchorPlan> implements
    IOpAnchorPlanService {

    @Override
    public void bindDeal(Integer dealId, Integer anchorId) {
        UpdateWrapper<OpAnchorPlan> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", anchorId);
        wrapper.set("deal_id", dealId);
        update(wrapper);
    }

    @Override
    public List<CampaignAnchorVo> anchorDealInfo() {
        return baseMapper.anchorDealInfo();
    }
}
