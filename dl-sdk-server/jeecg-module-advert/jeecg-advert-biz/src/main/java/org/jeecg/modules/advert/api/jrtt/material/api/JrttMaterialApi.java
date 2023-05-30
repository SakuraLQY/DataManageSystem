package org.jeecg.modules.advert.api.jrtt.material.api;

import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.modules.advert.api.jrtt.base.api.JrttBaseApi;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdResponse;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdRequest;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttVideoAdRequest;
import org.jeecg.modules.advert.api.jrtt.material.bo.JrttVideoAdResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttUrlConstant;
import org.springframework.util.LinkedMultiValueMap;

/**
 * @Description: 今日头条上传素材接口
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
public class JrttMaterialApi extends JrttBaseApi {


    /**
     * @param jrttImageAdRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.material.bo.JrttImageAdResponse
     * @author chenyw
     * @description 上传广告图片
     * @date 17:55 2023/2/27/027
     **/
    public static JrttBaseResponse<JrttImageAdResponse> imageAd(
        JrttImageAdRequest jrttImageAdRequest, String accessToken) {
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
        linkedMultiValueMap.set("advertiser_id" , jrttImageAdRequest.getAdvertiserId());
        linkedMultiValueMap.set("upload_type" , jrttImageAdRequest.getUploadType());
        linkedMultiValueMap.set("image_signature" , jrttImageAdRequest.getImageSignature());
        linkedMultiValueMap.set("image_file" , jrttImageAdRequest.getImageFile());
        linkedMultiValueMap.set("filename" , jrttImageAdRequest.getFilename());
        JrttBaseRequest<LinkedMultiValueMap> jrttBaseRequest = new JrttBaseRequest<>(
            accessToken, JrttUrlConstant.IMAGE_AD, linkedMultiValueMap);
        JrttBaseResponse<JrttImageAdResponse> jrttBaseResponse = JrttBaseApi.postMultipart(
            jrttBaseRequest, JrttImageAdResponse.class);
        return jrttBaseResponse;
    }

    /**
     * @param jrttVideoAdRequest
     * @param accessToken
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse<org.jeecg.modules.advert.api.jrtt.material.bo.JrttVideoAdResponse>
     * @Author lili
     * @Description 上传视频
     * @Date 11:07 2023/3/10
     **/
    public static JrttBaseResponse<JrttVideoAdResponse> videoAd(
        JrttVideoAdRequest jrttVideoAdRequest, String accessToken) {
        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
        linkedMultiValueMap.set("advertiser_id" , jrttVideoAdRequest.getAdvertiserId());
        linkedMultiValueMap.set("upload_type" , jrttVideoAdRequest.getUploadType());
        linkedMultiValueMap.set("video_signature" , jrttVideoAdRequest.getVideoSignature());
        linkedMultiValueMap.set("video_file" , jrttVideoAdRequest.getVideoFile());
        linkedMultiValueMap.set("filename" , jrttVideoAdRequest.getFilename());
        JrttBaseRequest<LinkedMultiValueMap> jrttBaseRequest = new JrttBaseRequest<>(
            accessToken, JrttUrlConstant.VIDEO_AD, linkedMultiValueMap);
        JrttBaseResponse<JrttVideoAdResponse> jrttBaseResponse = JrttBaseApi.postMultipart(
            jrttBaseRequest, JrttVideoAdResponse.class);
        return jrttBaseResponse;
    }

}
