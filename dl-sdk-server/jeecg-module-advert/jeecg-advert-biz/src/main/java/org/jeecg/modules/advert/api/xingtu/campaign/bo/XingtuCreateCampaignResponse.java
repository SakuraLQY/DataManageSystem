package org.jeecg.modules.advert.api.xingtu.campaign.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 创建广告组返回参数
 * @date: 2023/3/7 14:00
 **/
@Data
public class XingtuCreateCampaignResponse {

    /**
     * 广告组ID
     **/
    @JSONField(name = "campaign_id")
    private String campaignId;
}
