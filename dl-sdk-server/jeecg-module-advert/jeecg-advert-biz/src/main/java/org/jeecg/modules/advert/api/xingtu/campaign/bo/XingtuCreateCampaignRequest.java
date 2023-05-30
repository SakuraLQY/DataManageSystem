package org.jeecg.modules.advert.api.xingtu.campaign.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 创建广告组请求参数
 * @date: 2023/3/7 13:54
 **/
@Data
public class XingtuCreateCampaignRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 广告组名称
     **/
    @JSONField(name = "campaign_name")
    private String campaignName;

    /**
     * 广告组状态
     **/
    @JSONField(name = "operation")
    private String operation;

    /**
     * 预算类型
     **/
    @JSONField(name = "budget_mode")
    private String budgetMode;

    /**
     * 预算
     **/
    @JSONField(name = "budget")
    private Double budget;

    /**
     * 推广目的
     **/
    @JSONField(name = "landing_type")
    private String landingType;

    /**
     * 营销目的
     **/
    @JSONField(name = "marketing_purpose")
    private String marketingPurpose;

    /**
     * 预算择优分配
     **/
    @JSONField(name = "campaign_budget_optimization")
    private String campaignBudgetOptimization;

    /**
     * 出价方式
     **/
    @JSONField(name = "smart_bid_type")
    private String smartBidType;
}
