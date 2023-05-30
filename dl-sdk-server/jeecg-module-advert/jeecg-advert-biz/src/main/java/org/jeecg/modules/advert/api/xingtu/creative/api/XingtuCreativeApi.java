package org.jeecg.modules.advert.api.xingtu.creative.api;

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
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAddCustomCreativeRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAddCustomCreativeResponse;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAddProceduralCreativeRequest;
import org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuGetIndustryResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;

/**
 * @Description: 今日头条创意api
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class XingtuCreativeApi extends JrttBaseApi {

    /**
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuGetIndustryResponse>
     * @Author lili
     * @Description 获取行业列表
     * @Date 14:27 2023/3/22
     **/
    public static JrttBaseResponse<XingtuGetIndustryResponse> getIndustryList(String accessToken) {
        JrttBaseRequest jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.GET_INDUSTRY, null);
        JrttBaseResponse<XingtuGetIndustryResponse> jrttBaseResponse = JrttBaseApi.get(
            jrttBaseRequest, XingtuGetIndustryResponse.class);
        return jrttBaseResponse;
    }

    /**
     * @param xingtuAddProceduralCreativeRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse
     * @Author lili
     * @Description 创建程序化创意（营销链路）
     * @Date 18:39 2023/3/22
     **/
    public static JrttBaseResponse createProceduralCreative(
        XingtuAddProceduralCreativeRequest xingtuAddProceduralCreativeRequest, String accessToken) {
        JrttBaseRequest<XingtuAddProceduralCreativeRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.CREATE_PROCEDURAL_CREATIVE, xingtuAddProceduralCreativeRequest);
        JrttBaseResponse jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttBaseResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param xingtuAddProceduralCreativeRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse
     * @Author lili
     * @Description 修改程序化创意（营销链路）
     * @Date 11:17 2023/3/24
     **/
    public static JrttBaseResponse updateProceduralCreative(
        XingtuAddProceduralCreativeRequest xingtuAddProceduralCreativeRequest, String accessToken) {
        JrttBaseRequest<XingtuAddProceduralCreativeRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.CREATE_PROCEDURAL_UPDATE, xingtuAddProceduralCreativeRequest);
        JrttBaseResponse jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, JrttBaseResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param xingtuAddCustomCreativeRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAddCustomCreativeResponse>
     * @Author lili
     * @Description 创建自定义创意（营销链路）
     * @Date 19:05 2023/3/22
     **/
    public static JrttBaseResponse<XingtuAddCustomCreativeResponse> createCustomCreative(
        XingtuAddCustomCreativeRequest xingtuAddCustomCreativeRequest, String accessToken) {
        JrttBaseRequest<XingtuAddCustomCreativeRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.CREATE_CUSTOM_CREATIVE, xingtuAddCustomCreativeRequest);
        JrttBaseResponse<XingtuAddCustomCreativeResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, XingtuAddCustomCreativeResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }

    /**
     * @param xingtuAddCustomCreativeRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.xingtu.creative.bo.XingtuAddCustomCreativeResponse>
     * @Author lili
     * @Description 修改自定义创意（营销链路）
     * @Date 15:59 2023/3/24
     **/
    public static JrttBaseResponse<XingtuAddCustomCreativeResponse> updateCustomCreative(
        XingtuAddCustomCreativeRequest xingtuAddCustomCreativeRequest, String accessToken) {
        JrttBaseRequest<XingtuAddCustomCreativeRequest> jrttBaseRequest = new JrttBaseRequest(
            accessToken, JrttUrlConstant.CREATE_CUSTOM_UPDATE, xingtuAddCustomCreativeRequest);
        JrttBaseResponse<XingtuAddCustomCreativeResponse> jrttBaseResponse = JrttBaseApi.post(
            jrttBaseRequest, XingtuAddCustomCreativeResponse.class);
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            throw new JeecgBootException(jrttBaseResponse.getMessage());
        }
        return jrttBaseResponse;
    }




}
