package org.jeecg.modules.advert.api.ks.base.api;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseRequest;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse;
import org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponseList;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.ks.KsCodeConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 * @Description: 快手接统一接口口请求
 * @Author: lili
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Slf4j
public class KsBaseApi {

    /**
     * @param ksBaseRequest
     * @param clazz
     * @return org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse<T>
     * @Author lili
     * @Description 快手接口get请求
     * @Date 14:52 2023/3/10
     **/
    public static <T> KsBaseResponse<T> get(KsBaseRequest ksBaseRequest, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Token", ksBaseRequest.getAccessToken());
        httpHeaders.set("Content-Type", "application/json");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(ksBaseRequest.getData());
        JSONObject res = RestUtil.get(ksBaseRequest.getUrl(), httpHeaders, jsonObject, null);
        KsBaseResponse<T> ksBaseResponse = res.toJavaObject(KsBaseResponse.class);
        if (JrttCodeConstant.OK.equals(ksBaseResponse.getCode())) {
            ksBaseResponse.setData(res.getJSONObject("data").toJavaObject(clazz));
        }else{
            log.error("快手接口响应异常,请求:{},响应:{}", ksBaseRequest, ksBaseResponse);
        }
        return ksBaseResponse;
    }

    /**
     * @param ksBaseRequest
     * @param clazz
     * @return org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponseList<T>
     * @Author lili
     * @Description 快手接口get请求
     * @Date 14:52 2023/3/10
     **/
    public static <T> KsBaseResponseList<T> getArray(KsBaseRequest ksBaseRequest, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Token", ksBaseRequest.getAccessToken());
        httpHeaders.set("Content-Type", "application/json");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(ksBaseRequest.getData());
        JSONObject res = RestUtil.get(ksBaseRequest.getUrl(), httpHeaders, jsonObject, null);
        KsBaseResponseList<T> ksBaseResponse = res.toJavaObject(KsBaseResponseList.class);
        if (KsCodeConstant.OK.equals(ksBaseResponse.getCode())) {
            List<T> data = res.getJSONArray("data").toJavaList(clazz);
            ksBaseResponse.setData(data);
        }else{
            log.error("快手接口响应异常,请求:{},响应:{}", ksBaseRequest, ksBaseResponse);
        }
        return ksBaseResponse;
    }

    /**
     * @param ksBaseRequest
     * @param clazz
     * @return org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse<T>
     * @Author lili
     * @Description 快手接口post请求
     * @Date 14:53 2023/3/10
     **/
    public static <T> KsBaseResponse<T> post(KsBaseRequest ksBaseRequest, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Token", ksBaseRequest.getAccessToken());
        httpHeaders.set("Content-Type", "application/json");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(ksBaseRequest.getData());
        JSONObject res = RestUtil.post(ksBaseRequest.getUrl(), httpHeaders, null, jsonObject);
        KsBaseResponse<T> ksBaseResponse = res.toJavaObject(KsBaseResponse.class);
        if (KsCodeConstant.OK.equals(ksBaseResponse.getCode())) {
            ksBaseResponse.setData(res.getJSONObject("data").toJavaObject(clazz));
        }else{
            log.error("快手接口响应异常,请求:{},响应:{}", ksBaseRequest, ksBaseResponse);
        }
        return ksBaseResponse;
    }

    /**
     * @param ksBaseRequest
     * @param clazz
     * @return org.jeecg.modules.advert.api.ks.base.bo.KsBaseResponse<T>
     * @Author lili
     * @Description 快手接口post请求
     * @Date 14:50 2023/3/10
     **/
    public static <T> KsBaseResponse<T> postMultipart(KsBaseRequest ksBaseRequest, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Token", ksBaseRequest.getAccessToken());
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        JSONObject res = RestUtil.request(ksBaseRequest.getUrl(), HttpMethod.POST, httpHeaders,
            null, ksBaseRequest.getData(), JSONObject.class).getBody();
        KsBaseResponse<T> ksBaseResponse = res.toJavaObject(KsBaseResponse.class);
        if (KsCodeConstant.OK.equals(ksBaseResponse.getCode())) {
            ksBaseResponse.setData(res.getJSONObject("data").toJavaObject(clazz));
        }else{
            log.error("快手接口响应异常,请求:{},响应:{}", ksBaseRequest, ksBaseResponse);
        }
        return ksBaseResponse;
    }

}
