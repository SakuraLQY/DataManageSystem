package org.jeecg.modules.users.util;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.jeecg.common.util.RestUtil;
import org.springframework.http.HttpHeaders;

import java.util.*;

/**
 * @version V1.0
 * @description: 实名认证
 * @author: xmh
 * @date: 2022/12/2 15:58
 */
@Data
public class AntiAddiction {

    // 接口请求成功返回码
    public static final Integer CODE_SUCCESS = 0;
    // 实名验证错误时返回码
    public static final Integer CODE_AUTH_ERR = 2;
    // 实名验证验证中时返回码
    public static final Integer CODE_AUTH_WAIT = 1;
    // 实名验证成功时返回码
    public static final Integer CODE_AUTH_SUCCESS = 0;
    // 实名认证是否需要第三方api验证
    public static final Boolean NEED_API = true;
    public static final Boolean NO_NEED_API = false;
    // 是否需要第三方验证，中宣部验证不通过时
    public static final Boolean USE_OFFICIAL = false;
    public static final Boolean UN_USE_OFFICIAL = true;
    // appId
    public static final String APP_ID = "c7219f35f26a4b0ca1a4bf9aac3c853b";
    // 密钥
    public static final String SECRET_KEY = "800879e423d1c43b24b95da2231f07a4";
    // 实名认证请求接口
    private static final String CHECK_URL = "https://api.wlc.nppa.gov.cn/idcard/authentication/check";
    // 实名认证结果请求接口
    private static final String QUERY_URL = "http://api2.wlc.nppa.gov.cn/idcard/authentication/query";
    // 游戏上报请求接口
    public static final String URL_LOGIN_OUT = "http://api2.wlc.nppa.gov.cn/behavior/collection/loginout";
    // 成年需要的时间
    public static final long ADULT_TIME =  567648000;
    // 上报反沉迷数据成功响应码
    public static final Integer SUCCESS_CODE = 0;
    // 部分失败响应码
    public static final Integer PARTIAL_FAILURE = 3001;
    // 最多上报数量
    public static final Integer REPORT_MAX = 128;
    // 数据过期时间
    public static final Integer EXPIRE_TIME = 500;
    // 已实名
    public static final Integer REAL_NAME = 0;
    //未实名
    public static final Integer NOT_REAL_NAME = 2;
    //上报登录
    public static final Integer REPORT_LOGIN = 0;
    //上报登出
    public static final Integer REPORT_LOGOUT = 1;
    //上报完成
    public static final  Integer REPORT_SUCCESS = 2;
    //上报重试次数
    public static final  Integer REPORT_RETRY_TIMES = 3;
    /**
     * @param ai:       唯一标识
     * @param realName: 姓名
     * @param idNumber: 身份证号
     * @param bizId:    备案号
     * @return JSONObject
     * @author xmh
     * @description 执行实名认证
     * @date 2022/12/5 11:23
     */
    public static JSONObject checkIdCard(String ai, String realName, String idNumber,
        String bizId) {
        JSONObject params = new JSONObject();
        params.put("ai", ai);
        params.put("name", realName);
        params.put("idNum", idNumber);
        long time = new Date().getTime();
        JSONObject data = getDataEncrypt(params.toString());
        String sign = getSignSha(data.toString(), "", bizId, time);
        HttpHeaders headers = getHeaders(sign, bizId, time);
        return RestUtil.post(CHECK_URL, headers, null, data);
    }

    /**
     * @param ai:    唯一标识
     * @param bizId: 备案号
     * @return JSONObject
     * @author xmh
     * @description 查询实名认证结果
     * @date 2022/12/5 11:24
     */
    public static JSONObject queryIdCard(String ai, String bizId) {
        JSONObject params = new JSONObject();
        params.put("ai", ai);
        long time = new Date().getTime();
        String sign = getSignSha("", "ai" + ai, bizId, time);
        HttpHeaders headers = getHeaders(sign, bizId, time);
        return RestUtil.get(QUERY_URL, headers, params, null);
    }

    /**
     * @param sign:       加密签名
     * @param bizId:      备案号
     * @param timestamps: 时间戳
     * @return HttpHeaders
     * @author xmh
     * @description 构造请求头
     * @date 2022/12/9 10:30
     */
    private static HttpHeaders getHeaders(String sign, String bizId, long timestamps) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");
        headers.set("appId", APP_ID);
        headers.set("bizId", bizId);
        headers.set("timestamps", timestamps + "");
        headers.set("sign", sign);
        return headers;
    }

    /**
     * @param data:  data数据
     * @param query: ai参数
     * @param bizId: 备案号
     * @param time:  时间戳
     * @return String
     * @author xmh
     * @description 获取经过 sha256 加密后的sign
     * @date 2022/12/5 11:21
     */
    private static String getSignSha(String data, String query, String bizId, long time) {
        String sb = SECRET_KEY +
            query +
            "appId" +
            APP_ID +
            "bizId" +
            bizId +
            "timestamps" +
            time +
            data;
        return DigestUtil.sha256Hex(sb);
    }

    /**
     * @param params: data数据
     * @return JSONObject
     * @author xmh
     * @description 获取经过 aes 加密后的 data
     * @date 2022/12/5 11:04
     */
    private static JSONObject getDataEncrypt(String params) {
        String dataAes = AESUtils.Encrypt(params, SECRET_KEY);
        JSONObject data = new JSONObject();
        data.put("data", dataAes);
        return data;
    }
}
