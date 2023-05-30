package org.jeecg.modules.advert.api.jrtt.report.api;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionCreateRequest;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionCreateResponse;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionUpdateRequest;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionUpdateResponse;
import org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCampaignRequest;
import org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCampaignResponse;
import org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCustomRequest;
import org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCustomResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;

/**
 * @Description: 今日头条 创建广告api
 * @Author: chenyw
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Slf4j
public class JrttReportApi extends JrttBaseApi {

    /**
     * @param jrttReportCustomRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCustomResponse
     * @author chenyw
     * @description 获取自定义报表（体验版获取消耗）
     * @date 20:35 2023/4/26/026
     **/
    public static JrttBaseResponse<JrttReportCustomResponse> getReportCustom(
        JrttReportCustomRequest jrttReportCustomRequest, String accessToken) {
        JrttBaseRequest<JrttReportCustomRequest> jrttBaseRequest = new JrttBaseRequest<JrttReportCustomRequest>(
            accessToken, JrttUrlConstant.GET_REPORT_CUSTOM, jrttReportCustomRequest);
        JrttBaseResponse<JrttReportCustomResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttReportCustomResponse.class);
        return jrttBaseResponse;
    }

    /**
     * @param jrttReportCampaignRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCampaignResponse
     * @author chenyw
     * @description 获取广告组数据（旧版获取消耗）
     * @date 14:29 2023/4/27/027
     **/
    public static JrttBaseResponse<JrttReportCampaignResponse> getReportCampaign(
        JrttReportCampaignRequest jrttReportCampaignRequest, String accessToken) {
        JrttBaseRequest<JrttReportCampaignRequest> jrttBaseRequest = new JrttBaseRequest<JrttReportCampaignRequest>(
            accessToken, JrttUrlConstant.GET_REPORT_CAMPAIGN, jrttReportCampaignRequest);
        JrttBaseResponse<JrttReportCampaignResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttReportCampaignResponse.class);
        return jrttBaseResponse;
    }
}
