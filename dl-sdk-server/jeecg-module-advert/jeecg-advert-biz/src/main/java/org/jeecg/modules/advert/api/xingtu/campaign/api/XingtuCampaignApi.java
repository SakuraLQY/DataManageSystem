package org.jeecg.modules.advert.api.xingtu.campaign.api;

import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.xingtu.campaign.bo.XingtuCreateCampaignRequest;
import org.jeecg.modules.advert.api.xingtu.campaign.bo.XingtuCreateCampaignResponse;
import org.jeecg.modules.advert.constant.xingtu.XingtuUrlConstant;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/3/7 14:02
 **/
public class XingtuCampaignApi extends JrttBaseApi {

    /**
     * @author xmh
     * @description 创建广告组
     * @date 2023/3/7 14:06
     * @param accessToken: token
     * @param xingtuCreateCampaignRequest: 请求参数
     * @return JrttBaseResponse<XingtuCreateCampaignResponse>
     */
    public static JrttBaseResponse<XingtuCreateCampaignResponse> createCampaign(String accessToken, XingtuCreateCampaignRequest xingtuCreateCampaignRequest) {
        JrttBaseRequest<XingtuCreateCampaignRequest> jrttBaseRequest = new JrttBaseRequest<>(accessToken,
            XingtuUrlConstant.CREATE_CAMPAIGN_URL, xingtuCreateCampaignRequest);
        return JrttBaseApi.post(jrttBaseRequest, XingtuCreateCampaignResponse.class);
    }
}
