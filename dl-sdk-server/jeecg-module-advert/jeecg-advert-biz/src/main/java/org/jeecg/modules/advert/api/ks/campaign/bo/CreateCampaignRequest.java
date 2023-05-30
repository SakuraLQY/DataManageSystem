package org.jeecg.modules.advert.api.ks.campaign.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 创建广告计划请求参数
 * @date: 2023/3/11 11:41
 **/
@Data
public class CreateCampaignRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 广告计划名称
     **/
    @JSONField(name = "campaign_name")
    private String campaignName;

    /**
     * 计划类型
     **/
    @JSONField(name = "type")
    private Integer type;

    /**
     * 单日预算金额
     **/
    @JSONField(name = "day_budget")
    private Long dayBudget;

    /**
     * 分日预算
     **/
    @JSONField(name = "day_budget_schedule")
    private Long[] dayBudgetSchedule;

    /**
     * 出价类型
     **/
    @JSONField(name = "bid_type")
    private Integer bidType;
}
