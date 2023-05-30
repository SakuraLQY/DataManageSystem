package org.jeecg.modules.advert.api.ks.material.api;

import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.ks.base.api.KsBaseApi;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseRequest;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse;
import org.jeecg.modules.advert.api.ks.material.bo.KsImageAdRequest;
import org.jeecg.modules.advert.api.ks.material.bo.KsImageAdResponse;
import org.jeecg.modules.advert.api.ks.material.bo.KsVideoAdRequest;
import org.jeecg.modules.advert.api.ks.material.bo.KsVideoAdResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;
import org.jeecg.modules.advert.constant.ks.KsUrlConstant;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @Description: 快手上传素材接口
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class KsMaterialApi extends KsBaseApi {

    /**
     * @param ksImageAdRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse<org.jeecg.modules.advert.api.ks.material.bo.KsImageAdResponse>
     * @Author lili
     * @Description 上传图片
     * @Date 15:30 2023/3/10
     **/
    public static KsBaseResponse<KsImageAdResponse> imageAd(
        KsImageAdRequest ksImageAdRequest, String accessToken) {
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
        linkedMultiValueMap.set("advertiser_id" , ksImageAdRequest.getAdvertiserId());
        linkedMultiValueMap.set("upload_type" , ksImageAdRequest.getUploadType());
        linkedMultiValueMap.set("signature" , ksImageAdRequest.getSignature());
        linkedMultiValueMap.set("file" , ksImageAdRequest.getFile());
        KsBaseRequest<LinkedMultiValueMap> ksBaseRequest = new KsBaseRequest<>(
            accessToken, KsUrlConstant.IMAGE_AD, linkedMultiValueMap);
        KsBaseResponse<KsImageAdResponse> ksBaseResponse = KsBaseApi.postMultipart(
            ksBaseRequest, KsImageAdResponse.class);
        return ksBaseResponse;
    }

    /**
     * @param ksVideoAdRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse<org.jeecg.modules.advert.api.ks.material.bo.KsVideoAdResponse>
     * @Author lili
     * @Description 上传视频
     * @Date 15:42 2023/3/10
     **/
    public static KsBaseResponse<KsVideoAdResponse> videoAd(
        KsVideoAdRequest ksVideoAdRequest, String accessToken) {
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
        linkedMultiValueMap.set("advertiser_id" , ksVideoAdRequest.getAdvertiserId());
        linkedMultiValueMap.set("signature" , ksVideoAdRequest.getSignature());
        linkedMultiValueMap.set("file" , ksVideoAdRequest.getFile());
        linkedMultiValueMap.set("photo_name" , ksVideoAdRequest.getPhotoName());
        KsBaseRequest<LinkedMultiValueMap> ksBaseRequest = new KsBaseRequest<>(
            accessToken, KsUrlConstant.VIDEO_AD, linkedMultiValueMap);
        KsBaseResponse<KsVideoAdResponse> ksBaseResponse = KsBaseApi.postMultipart(
            ksBaseRequest, KsVideoAdResponse.class);
        return ksBaseResponse;
    }

}
