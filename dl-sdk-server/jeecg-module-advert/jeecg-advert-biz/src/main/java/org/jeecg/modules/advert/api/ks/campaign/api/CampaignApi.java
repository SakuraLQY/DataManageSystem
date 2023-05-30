package org.jeecg.modules.advert.api.ks.campaign.api;

import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.advert.api.ks.base.api.KsBaseApi;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseRequest;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse;
import org.jeecg.modules.advert.api.ks.campaign.bo.CreateCampaignRequest;
import org.jeecg.modules.advert.api.ks.campaign.bo.CreateCampaignResponse;
import org.jeecg.modules.advert.api.ks.campaign.bo.ModifyBudgetRequest;
import org.jeecg.modules.advert.constant.ks.KsUrlConstant;

/**
 * @author xmh
 * @version V1.0
 * @description: 广告计划api
 * @date: 2023/3/11 11:46
 **/
public class CampaignApi extends KsBaseApi {

    /**
     * @param accessToken:           token
     * @param createCampaignRequest: 请求参数
     * @return KsBaseResponse<CreateCampaignResponse>
     * @author xmh
     * @description 创建广告计划
     * @date 2023/3/11 11:50
     */
    public static KsBaseResponse<CreateCampaignResponse> createCampaign(String accessToken,
        CreateCampaignRequest createCampaignRequest) {
        KsBaseRequest<CreateCampaignRequest> baseRequest = new KsBaseRequest<>(accessToken,
            KsUrlConstant.CREATE_CAMPAIGN_URL, createCampaignRequest);
        return KsBaseApi.post(baseRequest, CreateCampaignResponse.class);
    }

    /**
     * @param accessToken:         token
     * @param modifyBudgetRequest: 请求参数
     * @return KsBaseResponse<T>
     * @author xmh
     * @description 修改广告计划预算
     * @date 2023/3/16 13:50
     */
    public static KsBaseResponse<T> modifyBudget(String accessToken,
        ModifyBudgetRequest modifyBudgetRequest) {
        KsBaseRequest<ModifyBudgetRequest> request = new KsBaseRequest<>(accessToken,
            KsUrlConstant.MODIFY_BUDGET_URL, modifyBudgetRequest);
        return KsBaseApi.post(request, T.class);
    }
}
