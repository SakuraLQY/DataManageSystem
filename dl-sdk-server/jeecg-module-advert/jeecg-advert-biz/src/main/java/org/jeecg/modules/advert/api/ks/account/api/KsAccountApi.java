package org.jeecg.modules.advert.api.ks.account.api;

import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenResponse;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.ks.account.bo.KsAccessTokenRequest;
import org.jeecg.modules.advert.api.ks.account.bo.KsAccessTokenResponse;
import org.jeecg.modules.advert.api.ks.account.bo.KsRefreshTokenRequest;
import org.jeecg.modules.advert.api.ks.account.bo.KsRefreshTokenResponse;
import org.jeecg.modules.advert.api.ks.base.api.KsBaseApi;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseRequest;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;
import org.jeecg.modules.advert.constant.ks.KsUrlConstant;
import org.springframework.util.CollectionUtils;

/**
 * @Description: 快手账号api
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class KsAccountApi extends KsBaseApi {

    /**
     * @param ksAccessTokenRequest
     * @return org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse<org.jeecg.modules.advert.api.ks.account.bo.KsAccessTokenResponse>
     * @Author lili
     * @Description 获取access_token
     * @Date 19:03 2023/3/7
     **/
    public static KsBaseResponse<KsAccessTokenResponse> getAccessToken(
        KsAccessTokenRequest ksAccessTokenRequest) {
        KsBaseRequest ksBaseRequest = new KsBaseRequest(null, KsUrlConstant.ACCESS_TOKEN,
            ksAccessTokenRequest);
        KsBaseResponse<KsAccessTokenResponse> KsBaseResponse = KsBaseApi.post(
            ksBaseRequest, KsAccessTokenResponse.class);
        return KsBaseResponse;
    }

    /**
     * @param ksRefreshTokenRequest
     * @return org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse<org.jeecg.modules.advert.api.ks.account.bo.KsRefreshTokenResponse>
     * @Author lili
     * @Description 刷新access_token
     * @Date 19:12 2023/3/7
     **/
    public static KsBaseResponse<KsRefreshTokenResponse> refreshToken(
        KsRefreshTokenRequest ksRefreshTokenRequest) {
        KsBaseRequest<KsRefreshTokenRequest> ksBaseRequest = new KsBaseRequest(null,
            KsUrlConstant.REFRESH_TOKEN, ksRefreshTokenRequest);
        KsBaseResponse<KsRefreshTokenResponse> ksBaseResponse = KsBaseApi.post(
            ksBaseRequest, KsRefreshTokenResponse.class);
        return ksBaseResponse;
    }

}
