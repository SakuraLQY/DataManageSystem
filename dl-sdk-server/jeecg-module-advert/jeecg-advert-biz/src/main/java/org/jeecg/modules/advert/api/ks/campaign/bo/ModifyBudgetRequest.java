package org.jeecg.modules.advert.api.ks.campaign.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 修改计划预算请求参数
 * @date: 2023/3/16 13:46
 **/
@Data
public class ModifyBudgetRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 广告计划ID
     **/
    @JSONField(name = "campaign_id")
    private Long campaignId;

    /**
     * 单日预算金额
     **/
    @JSONField(name = "day_budget")
    private Long dayBudget;

    /**
     * 分日预算金额
     **/
    @JSONField(name = "day_budget_schedule")
    private Long[] dayBudgetSchedule;
}
