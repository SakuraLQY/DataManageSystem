package org.jeecg.modules.advert.api.ks.app.api;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.advert.api.ks.app.bo.KsAppQueryReponse;
import org.jeecg.modules.advert.api.ks.app.bo.KsAppQueryRequset;
import org.jeecg.modules.advert.api.ks.app.bo.KsAppRequest;
import org.jeecg.modules.advert.api.ks.base.api.KsBaseApi;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseRequest;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse;
import org.jeecg.modules.advert.constant.ks.KsUrlConstant;
import org.springframework.util.LinkedMultiValueMap;

public class KsAppApi {


    public static KsBaseResponse<JSONObject> createApp(String accessToken, KsAppRequest ksAppRequest){

        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.set("advertiser_id",ksAppRequest.getAdvertiserId());
        linkedMultiValueMap.set("file",ksAppRequest.getFile());
        linkedMultiValueMap.set("app_version",ksAppRequest.getAppVersion());
        linkedMultiValueMap.set("app_name",ksAppRequest.getAppName());
        linkedMultiValueMap.set("image_token",ksAppRequest.getImageToken());
        if (ksAppRequest.getPackageName()!=null){
            linkedMultiValueMap.set("package_name",ksAppRequest.getPackageName());
        }
        linkedMultiValueMap.set("platform",ksAppRequest.getPlatform());
        linkedMultiValueMap.set("app_privacy_url",ksAppRequest.getAppPrivacyUrl());
        KsBaseRequest<LinkedMultiValueMap> ksAppRequestKsBaseRequest = new KsBaseRequest<>(accessToken,
            KsUrlConstant.CREATE_APP_URL, linkedMultiValueMap);
        KsBaseResponse<JSONObject> jsonObjectKsBaseResponse = KsBaseApi
            .postMultipart(ksAppRequestKsBaseRequest, JSONObject.class);

        return jsonObjectKsBaseResponse;
    }

    public static KsBaseResponse<KsAppQueryReponse> queryAppList(String accessToken,
        KsAppQueryRequset ksAppQueryRequset){
        KsBaseRequest<KsAppQueryRequset> ksAppQueryRequsetKsBaseRequest = new KsBaseRequest<>(
            accessToken, KsUrlConstant.QUERY_APP_LIST_URL, ksAppQueryRequset);
        KsBaseResponse<KsAppQueryReponse> ksAppQueryReponseKsBaseResponse = KsBaseApi
            .post(ksAppQueryRequsetKsBaseRequest, KsAppQueryReponse.class);

        return ksAppQueryReponseKsBaseResponse;
    }
}
