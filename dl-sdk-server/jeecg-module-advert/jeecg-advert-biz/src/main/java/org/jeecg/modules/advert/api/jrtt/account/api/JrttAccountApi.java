package org.jeecg.modules.advert.api.jrtt.account.api;

import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectResponse;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenResponse;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;
import org.springframework.util.CollectionUtils;

/**
 * @Description: 今日头条账号api
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class JrttAccountApi extends JrttBaseApi {

    /**
     * @param jrttAccessTokenRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccessTokenResponse>
     * @author chenyw
     * @description 获取access_token
     * @date 13:44 2023/2/17/017
     **/
    public static JrttBaseResponse<JrttAccessTokenResponse> getAccessToken(
        JrttAccessTokenRequest jrttAccessTokenRequest) {
        JrttBaseRequest jrttBaseRequest = new JrttBaseRequest(null, JrttUrlConstant.ACCESS_TOKEN,
            jrttAccessTokenRequest);
        JrttBaseResponse<JrttAccessTokenResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttAccessTokenResponse.class);
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttAdvertiserSelectRequest
     * @return org.jeecg.modules.advert.api.jrtt.account.bo.JrttAdvertiserSelectResponse
     * @author chenyw
     * @description 获取纵横组织下资产账户列表
     * @date 13:44 2023/2/17/017
     **/
    public static JrttAdvertiserSelectResponse advertiserSelect(
        String accessToken, JrttAdvertiserSelectRequest jrttAdvertiserSelectRequest) {
        JrttBaseRequest<JrttAdvertiserSelectRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.ADVERTISER_SELECT, jrttAdvertiserSelectRequest);
        JrttBaseResponse<JrttAdvertiserSelectResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttAdvertiserSelectResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        if (CollectionUtils.isEmpty(jrttBaseResponse.getData().getList())) {
            throw new JeecgBootException("头条接口返回账户列表为空");
        }
        return jrttBaseResponse.getData();
    }

    /**
     * @param jrttRefreshTokenRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.account.bo.JrttRefreshTokenResponse>
     * @author chenyw
     * @description 刷新access_token
     * @date 13:45 2023/2/17/017
     **/
    public static JrttBaseResponse<JrttRefreshTokenResponse> refreshToken(
        JrttRefreshTokenRequest jrttRefreshTokenRequest) {
        JrttBaseRequest<JrttRefreshTokenRequest> jrttBaseRequest = new JrttBaseRequest(null,
            JrttUrlConstant.REFRESH_TOKEN, jrttRefreshTokenRequest);
        JrttBaseResponse<JrttRefreshTokenResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttRefreshTokenResponse.class);
        return jrttBaseResponse;
    }

}
