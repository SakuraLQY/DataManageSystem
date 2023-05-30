package org.jeecg.modules.advert.api.jrtt.promotion.api;

import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionCreateRequest;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionCreateResponse;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionUpdateRequest;
import org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionUpdateResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;

/**
 * @Description: 今日头条 创建广告api
 * @Author: chenyw
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class JrttPromotionApi extends JrttBaseApi {

    /**
     * @param jrttPromotionCreateRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionCreateResponse
     * @author chenyw
     * @description 创建广告
     * @date 16:03 2023/2/25/025
     **/
    public static JrttPromotionCreateResponse promotionCreate(
        JrttPromotionCreateRequest jrttPromotionCreateRequest, String accessToken) {
        JrttBaseRequest<JrttPromotionCreateRequest> jrttBaseRequest = new JrttBaseRequest<JrttPromotionCreateRequest>(
            accessToken, JrttUrlConstant.PROMOTION_CREATE, jrttPromotionCreateRequest);
        JrttBaseResponse<JrttPromotionCreateResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttPromotionCreateResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse.getData();
    }


    /**
     * @param jrttPromotionUpdateRequest
     * @param accessToken 
     * @return org.jeecg.modules.advert.api.jrtt.promotion.bo.JrttPromotionUpdateResponse
     * @author chenyw
     * @description 修改广告
     * @date 17:54 2023/3/14/014
     **/
    public static JrttPromotionUpdateResponse promotionUpdate(
        JrttPromotionUpdateRequest jrttPromotionUpdateRequest, String accessToken) {
        JrttBaseRequest<JrttPromotionUpdateRequest> jrttBaseRequest = new JrttBaseRequest<JrttPromotionUpdateRequest>(
            accessToken, JrttUrlConstant.PROMOTION_UPDATE, jrttPromotionUpdateRequest);
        JrttBaseResponse<JrttPromotionUpdateResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttPromotionUpdateResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse.getData();
    }

}
