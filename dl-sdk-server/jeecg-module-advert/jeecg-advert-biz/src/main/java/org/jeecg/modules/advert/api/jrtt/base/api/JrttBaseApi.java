package org.jeecg.modules.advert.api.jrtt.base.api;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseRequest;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponseList;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @Description: 今日头条接统一接口口请求
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Slf4j
public class JrttBaseApi {

    /**
     * @param jrttBaseRequest
     * @param clazz
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse
     * @author chenyw
     * @description 今日头条接口get请求
     * @date 11:18 2023/2/9/009
     **/
    public static <T> JrttBaseResponse<T> get(JrttBaseRequest jrttBaseRequest, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Token", jrttBaseRequest.getAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = null;
        if (jrttBaseRequest.getData() != null) {
            jsonObject = (JSONObject) JSONObject.toJSON(jrttBaseRequest.getData());
        }
        JSONObject res = RestUtil.get(jrttBaseRequest.getUrl(), httpHeaders, jsonObject, null);
        JrttBaseResponse<T> jrttBaseResponse = res.toJavaObject(JrttBaseResponse.class);
        if (JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            jrttBaseResponse.setData(res.getJSONObject("data").toJavaObject(clazz));
        }else{
            log.error("今日头条接口响应异常,请求:{},响应:{}", JSONObject.toJSONString(jrttBaseRequest), jrttBaseResponse);
        }
        return jrttBaseResponse;
    }

    /**
     * @param jrttBaseRequest
     * @param clazz
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse
     * @author chenyw
     * @description 今日头条接口get请求
     * @date 11:18 2023/2/9/009
     **/
    public static <T>  JrttBaseResponseList<T> getArray(JrttBaseRequest jrttBaseRequest, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Token", jrttBaseRequest.getAccessToken());
        httpHeaders.set("Content-Type", "application/json");
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(jrttBaseRequest.getData());
        JSONObject res = RestUtil.get(jrttBaseRequest.getUrl(), httpHeaders, jsonObject, null);
        JrttBaseResponseList<T> jrttBaseResponse = res.toJavaObject(JrttBaseResponseList.class);
        if (JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            List<T> data = res.getJSONArray("data").toJavaList(clazz);
            jrttBaseResponse.setData(data);
        }else{
            log.error("今日头条接口响应异常,请求:{},响应:{}", JSONObject.toJSONString(jrttBaseRequest), jrttBaseResponse);
        }
        return jrttBaseResponse;
    }


    /**
     * @param jrttBaseRequest
     * @param clazz
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.Jrt tBaseResponse
     * @author chenyw
     * @description 今日头条接口post请求
     * @date 11:18 2023/2/9/009
     **/
    public static <T> JrttBaseResponse<T> post(JrttBaseRequest jrttBaseRequest, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Token", jrttBaseRequest.getAccessToken());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(jrttBaseRequest.getData());
        JSONObject res = RestUtil.post(jrttBaseRequest.getUrl(), httpHeaders, null, jsonObject);
        JrttBaseResponse<T> jrttBaseResponse = res.toJavaObject(JrttBaseResponse.class);

        if (JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            jrttBaseResponse.setData(res.getJSONObject("data").toJavaObject(clazz));
        }else{
            log.error("今日头条接口响应异常,请求:{},响应:{}", JSONObject.toJSONString(jrttBaseRequest), jrttBaseResponse);
        }
        return jrttBaseResponse;
    }

    /**
     * @param jrttBaseRequest
     * @param clazz
     * @return org.jeecg.modules.advert.api.jrtt.base.bo.Jrt tBaseResponse
     * @author chenyw
     * @description 今日头条接口post请求
     * @date 11:18 2023/2/9/009
     **/
    public static <T> JrttBaseResponse<T> postMultipart(JrttBaseRequest jrttBaseRequest, Class<T> clazz) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Access-Token", jrttBaseRequest.getAccessToken());
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        JSONObject res = RestUtil.request(jrttBaseRequest.getUrl(), HttpMethod.POST, httpHeaders,
            null, jrttBaseRequest.getData(), JSONObject.class).getBody();
        JrttBaseResponse<T> jrttBaseResponse = res.toJavaObject(JrttBaseResponse.class);
        if (JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            jrttBaseResponse.setData(res.getJSONObject("data").toJavaObject(clazz));
        }else{
            log.error("今日头条接口响应异常,请求:{},响应:{}", JSONObject.toJSONString(jrttBaseRequest), jrttBaseResponse);
        }
        return jrttBaseResponse;
    }

}
