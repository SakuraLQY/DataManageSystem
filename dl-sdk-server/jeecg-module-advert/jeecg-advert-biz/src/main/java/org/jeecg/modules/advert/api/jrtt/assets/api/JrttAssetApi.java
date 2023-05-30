package org.jeecg.modules.advert.api.jrtt.assets.api;

import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttAddAssetsRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttAddAssetsResponse;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttCreatEventsRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttCreatTrackUrlRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetAssetsRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetAssetsResponse;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetEventsDataResponse;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetEventsRequest;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;
import org.jeecg.modules.advert.service.IOpJrttAssetsService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 今日头条资产api
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class JrttAssetApi extends JrttBaseApi {

    /**
     * @param accessToken
     * @param jrttAddAssetsRequest
     * @return org.jeecg.modules.advert.api.jrtt.assets.bo.JrttAddAssetsResponse
     * @author chenyw
     * @description 新增资产
     * @date 13:59 2023/2/17/017
     **/
    public static JrttBaseResponse<JrttAddAssetsResponse> addAssets(String accessToken,
        JrttAddAssetsRequest jrttAddAssetsRequest) {
        JrttBaseRequest<JrttAddAssetsRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.ADD_ASSETS, jrttAddAssetsRequest);
        JrttBaseResponse<JrttAddAssetsResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttAddAssetsResponse.class);
        return jrttBaseResponse;
    }

    /**
     * @param accessToken
     * @param jrttGetAssetsRequest
     * @return org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetAssetsResponse
     * @author chenyw
     * @description 获取已创建资产
     * @date 13:59 2023/2/17/017
     **/
    public static JrttGetAssetsResponse getAssets(String accessToken,
        JrttGetAssetsRequest jrttGetAssetsRequest) {
        JrttBaseRequest<JrttGetAssetsRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_ASSETS, jrttGetAssetsRequest);
        JrttBaseResponse<JrttGetAssetsResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetAssetsResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse.getData();
    }

    /**
     * @param accessToken
     * @param jrttGetEventsRequest
     * @return org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetEventsDataResponse
     * @author chenyw
     * @description 获取已创建事件
     * @date 13:59 2023/2/17/017
     **/
    public static JrttGetEventsDataResponse getEvents(String accessToken,
        JrttGetEventsRequest jrttGetEventsRequest) {
        JrttBaseRequest<JrttGetEventsRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_EVENTS, jrttGetEventsRequest);
        JrttBaseResponse<JrttGetEventsDataResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, JrttGetEventsDataResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse.getData();
    }

    /**
     * @param accessToken
     * @param jrttCreatEventsRequest
     * @return org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetEventsDataResponse
     * @author lili
     * @description 创建事件
     * @date 13:59 2023/2/17
     **/
    public static JrttBaseResponse creatEvents(String accessToken,
        JrttCreatEventsRequest jrttCreatEventsRequest) {
        JrttBaseRequest<JrttGetEventsRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.CREAT_EVENTS, jrttCreatEventsRequest);
        JrttBaseResponse jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, Object.class);
        return jrttBaseResponse;
    }


    public static JrttBaseResponse creatTrackUrl(String accessToken,
        JrttCreatTrackUrlRequest jrttCreatTrackUrlRequest) {
        JrttBaseRequest<JrttGetEventsRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.CREAT_TRACK_URL, jrttCreatTrackUrlRequest);
        JrttBaseResponse jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, null);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

}
