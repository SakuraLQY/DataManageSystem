package org.jeecg.modules.advert.api.ks.campaign.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 创建广告计划返回参数
 * @date: 2023/3/11 11:45
 **/
@Data
public class CreateCampaignResponse {

    /**
     * 广告计划ID
     **/
    @JSONField(name = "campaign_id")
    private String campaignId;
}
