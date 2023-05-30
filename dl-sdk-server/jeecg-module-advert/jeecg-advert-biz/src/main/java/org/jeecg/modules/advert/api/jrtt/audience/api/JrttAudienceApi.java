package org.jeecg.modules.advert.api.jrtt.audience.api;

import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttAddAudienceRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttAddAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAudienceRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeAuthorInfoRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeAuthorInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanAccountsRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanAccountsResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanCategoriesRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanCategoriesResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSimilarAccountsRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSimilarAccountsResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCustomAudienceRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCustomAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetLiveAuthorizeRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetLiveAuthorizeResponse;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttUpdateAudienceRequest;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttUpdateAudienceResponse;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponseList;
import org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordOrSuggestRsp;
import org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordReq;
import org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestSuggestReq;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionCategoryReq;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionInterestCategory;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionKeywordsReq;
import org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionKeywordsRsp;
import org.jeecg.modules.advert.api.jrtt.audience.constant.JrttDealInterestActionConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;

/**
 * @Description: 今日头条定向包api
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class JrttAudienceApi extends JrttBaseApi {

    /**
     * @param accessToken
     * @param jrttAddAudienceRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttAddAudienceResponse>
     * @Author lili
     * @Description 添加定向包
     * @Date 14:07 2023/2/22
     **/
    public static JrttBaseResponse<JrttAddAudienceResponse> addAudience(String accessToken,
        JrttAddAudienceRequest jrttAddAudienceRequest) {
        JrttBaseRequest<JrttAddAudienceRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.CREAT_AUDIENCE_PACKAGE_URL, jrttAddAudienceRequest);
        JrttBaseResponse<JrttAddAudienceResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttAddAudienceResponse.class);
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetAudienceRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttAddAudienceResponse>
     * @Author lili
     * @Description 获取定向包
     * @Date 14:07 2023/2/22
     **/
    public static JrttBaseResponse<JrttGetAudienceResponse> getAudience(String accessToken,
        JrttGetAudienceRequest jrttGetAudienceRequest) {
        JrttBaseRequest<JrttGetAudienceRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_AUDIENCE_PACKAGE_URL, jrttGetAudienceRequest);
        JrttBaseResponse<JrttGetAudienceResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetAudienceResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttUpdateAudienceRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAudienceResponse>
     * @Author lili
     * @Description 更新定向包
     * @Date 10:15 2023/2/23
     **/
    public static JrttBaseResponse<JrttUpdateAudienceResponse> updateAudience(String accessToken,
        JrttUpdateAudienceRequest jrttUpdateAudienceRequest) {
        JrttBaseRequest<JrttUpdateAudienceRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.UPDATE_AUDIENCE_PACKAGE_URL, jrttUpdateAudienceRequest);
        JrttBaseResponse<JrttUpdateAudienceResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttUpdateAudienceResponse.class);
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetCustomAudienceRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCustomAudienceResponse>
     * @Author lili
     * @Description 人群包列表
     * @Date 18:02 2023/2/23
     **/
    public static JrttBaseResponse<JrttGetCustomAudienceResponse> getCustomAudience(
        String accessToken,
        JrttGetCustomAudienceRequest jrttGetCustomAudienceRequest) {
        JrttBaseRequest<JrttGetCustomAudienceRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_CUSTOM_AUDIENCE_URL, jrttGetCustomAudienceRequest);
        JrttBaseResponse<JrttGetCustomAudienceResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetCustomAudienceResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetCountryInfoRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetCountryInfoResponse>
     * @Author lili
     * @Description 获取行政信息
     * @Date 17:45 2023/2/25
     **/
    public static JrttBaseResponse<JrttGetCountryInfoResponse> getCountryInfo(String accessToken,
        JrttGetCountryInfoRequest jrttGetCountryInfoRequest) {
        JrttBaseRequest<JrttGetCountryInfoRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_COUNTRY_INFO_URL, jrttGetCountryInfoRequest);
        JrttBaseResponse<JrttGetCountryInfoResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetCountryInfoResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetAwemeFanCategoriesRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanCategoriesResponse>
     * @Author lili
     * @Description 获取抖音类目列表
     * @Date 11:31 2023/2/27
     **/
    public static JrttBaseResponse<JrttGetAwemeFanCategoriesResponse> getAwemeFanCategories(
        String accessToken,
        JrttGetAwemeFanCategoriesRequest jrttGetAwemeFanCategoriesRequest) {
        JrttBaseRequest<JrttGetAwemeFanCategoriesRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_AWEME_FAN_CATEGORIES_URL,
            jrttGetAwemeFanCategoriesRequest);
        JrttBaseResponse<JrttGetAwemeFanCategoriesResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetAwemeFanCategoriesResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetAwemeAuthorInfoRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeAuthorInfoResponse>
     * @Author lili
     * @Description 查询抖音号id对应的达人信息
     * @Date 13:55 2023/2/27
     **/
    public static JrttBaseResponse<JrttGetAwemeAuthorInfoResponse> getAwemeAuthorInfo(
        String accessToken,
        JrttGetAwemeAuthorInfoRequest jrttGetAwemeAuthorInfoRequest) {
        JrttBaseRequest<JrttGetAwemeAuthorInfoRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_AWEME_AUTHOR_INFO_URL,
            jrttGetAwemeAuthorInfoRequest);
        JrttBaseResponse<JrttGetAwemeAuthorInfoResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetAwemeAuthorInfoResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetAwemeSearchInfoRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSearchInfoResponse>
     * @Author lili
     * @Description 查询抖音帐号和类目信息
     * @Date 14:21 2023/2/27
     **/
    public static JrttBaseResponse<JrttGetAwemeSearchInfoResponse> getAwemeSearchInfo(
        String accessToken,
        JrttGetAwemeSearchInfoRequest jrttGetAwemeSearchInfoRequest) {
        JrttBaseRequest<JrttGetAwemeAuthorInfoRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_AWEME_INFO_SEARCH_URL,
            jrttGetAwemeSearchInfoRequest);
        JrttBaseResponse<JrttGetAwemeSearchInfoResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetAwemeSearchInfoResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetAwemeSimilarAccountsRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeSimilarAccountsResponse>
     * @Author lili
     * @Description 查询抖音类似帐号
     * @Date 14:31 2023/2/27
     **/
    public static JrttBaseResponse<JrttGetAwemeSimilarAccountsResponse> getAwemeSimilarAccounts(
        String accessToken,
        JrttGetAwemeSimilarAccountsRequest jrttGetAwemeSimilarAccountsRequest) {
        JrttBaseRequest<JrttGetAwemeSimilarAccountsRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_AWEME_SIMILAR_AUTHOR_SEARCH_URL,
            jrttGetAwemeSimilarAccountsRequest);
        JrttBaseResponse<JrttGetAwemeSimilarAccountsResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetAwemeSimilarAccountsResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetAwemeFanAccountsRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetAwemeFanAccountsResponse>
     * @Author lili
     * @Description 查询抖音类目下的推荐达人
     * @Date 14:41 2023/2/27
     **/
    public static JrttBaseResponse<JrttGetAwemeFanAccountsResponse> getAwemeFanAccounts(
        String accessToken,
        JrttGetAwemeFanAccountsRequest jrttGetAwemeFanAccountsRequest) {
        JrttBaseRequest<JrttGetAwemeFanAccountsRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_AWEME_CATEGORY_TOP_AUTHOR_URL,
            jrttGetAwemeFanAccountsRequest);
        JrttBaseResponse<JrttGetAwemeFanAccountsResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetAwemeFanAccountsResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetLiveAuthorizeRequest
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttGetLiveAuthorizeResponse>
     * @Author lili
     * @Description 查询授权直播抖音达人列表
     * @Date 14:57 2023/2/27
     **/
    public static JrttBaseResponse<JrttGetLiveAuthorizeResponse> getLiveAuthorize(
        String accessToken,
        JrttGetLiveAuthorizeRequest jrttGetLiveAuthorizeRequest) {
        JrttBaseRequest<JrttGetLiveAuthorizeRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_LIVE_AUTHORIZE_URL,
            jrttGetLiveAuthorizeRequest);
        JrttBaseResponse<JrttGetLiveAuthorizeResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetLiveAuthorizeResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param type
     * @param token
     * @param jrttDealActionCategoryReq
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponseList<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionInterestCategory>
     * @Author lili
     * @Description 行为类目查询、兴趣类目查询
     * @Date 10:03 2023/2/28
     **/
    public static JrttBaseResponseList<JrttDealActionInterestCategory> getActionOrInterestCategories(
        Integer type, String token, JrttDealActionCategoryReq jrttDealActionCategoryReq) {
        String url = type.equals(JrttDealInterestActionConstant.TYPE_ACTION)
            ? JrttUrlConstant.SEARCH_ACTION_CATEGORIES_URL
            : JrttUrlConstant.SEARCH_INTEREST_CATEGORIES_URL;
        JrttBaseRequest baseRequest = new JrttBaseRequest(token, url, jrttDealActionCategoryReq);
        JrttBaseResponseList<JrttDealActionInterestCategory> response = JrttBaseApi.getArray(
            baseRequest,
            JrttDealActionInterestCategory.class);
        if (!response.getCode().equals(JrttCodeConstant.OK)) {
            throw new JeecgBootException("搜索行为类目失败");
        }
        return response;
    }

    /**
     * @param type
     * @param token
     * @param jrttDealActionKeywordsReq
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.JrttDealActionKeywordsRsp>
     * @Author lili
     * @Description 行为关键字查询、兴趣关键字查询
     * @Date 10:03 2023/2/28
     **/
    public static JrttBaseResponse<JrttDealActionKeywordsRsp> getActionOrInterestKeywords(
        Integer type, String token, JrttDealActionKeywordsReq jrttDealActionKeywordsReq) {
        String url = type.equals(JrttDealInterestActionConstant.TYPE_ACTION)
            ? JrttUrlConstant.SEARCH_ACTION_KEYWORD_URL
            : JrttUrlConstant.SEARCH_INTEREST_KEYWORD_URL;
        JrttBaseRequest baseRequest = new JrttBaseRequest(token, url, jrttDealActionKeywordsReq);
        JrttBaseResponse<JrttDealActionKeywordsRsp> response = JrttBaseApi
            .get(baseRequest, JrttDealActionKeywordsRsp.class);
        if (!response.getCode().equals(JrttCodeConstant.OK)) {
            if (type.equals(JrttDealInterestActionConstant.TYPE_ACTION)) {
                throw new JeecgBootException("搜索行为关键字失败");
            }
            throw new JeecgBootException("搜索兴趣关键字失败");
        }
        return response;
    }

    /**
     * @param accessToken
     * @param req
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordOrSuggestRsp>
     * @Author lili
     * @Description 兴趣行为类目关键词id转词
     * @Date 10:04 2023/2/28
     **/
    public static JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> getInterestActionInfoByIs(
        String accessToken, ActionInterestId2WordReq req) {
        JrttBaseRequest baseRequest = new JrttBaseRequest(accessToken,
            JrttUrlConstant.SEARCH_ACTION_INTEREST_ID2WORD_URL, req);
        JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> responseList = JrttBaseApi
            .get(baseRequest, ActionInterestId2WordOrSuggestRsp.class);
        if (!responseList.getCode().equals(JrttCodeConstant.OK)) {
            throw new JeecgBootException("搜索兴趣行为类目关键词id转词失败");
        }
        return responseList;
    }

    /**
     * @param accessToken
     * @param req
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.audience.bo.ActionInterestId2WordOrSuggestRsp>
     * @Author lili
     * @Description 获取行为兴趣推荐关键词
     * @Date 10:04 2023/2/28
     **/
    public static JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> getActionInterestKeywordSuggest(
        String accessToken, ActionInterestSuggestReq req) {
        JrttBaseRequest baseRequest = new JrttBaseRequest(accessToken,
            JrttUrlConstant.SEARCH_ACTION_INTEREST_SUGGEST_URL, req);
        JrttBaseResponse<ActionInterestId2WordOrSuggestRsp> response = JrttBaseApi
            .get(baseRequest, ActionInterestId2WordOrSuggestRsp.class);
        if (!response.getCode().equals(JrttCodeConstant.OK)) {
            throw new JeecgBootException("搜索兴趣行为类目关键词id转词失败");
        }
        return response;
    }
}
