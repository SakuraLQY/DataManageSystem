package org.jeecg.modules.users.util;

import com.alibaba.fastjson.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jeecg.common.util.oConvertUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import java.security.cert.X509Certificate;
import org.apache.http.conn.scheme.Scheme;
import org.apache.commons.lang.StringUtils;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @version V1.0
 * @description: amarket 第三方api验证
 * @author: xmh
 * @date: 2022/12/5 11:42
 */
@Slf4j
public class Amarket {

    // 实名验证错误时返回码
    public static final Integer CODE_ERR = -9;
    // 身份不匹配时返回码
    public static final Integer CODE_MIS_MATCH = -10;
    // 成功时返回码
    public static final Integer CODE_SUCCESS = 0;
    // 身份验证请求地址
    private static final String host = "https://lfeid.market.alicloudapi.com";
    private static final String path = "/idcheck/life";
    // 密钥
    private static final String aurhCode = "abca9eb2833e48f485852802d0f38d27";

    /**
     * @param realName: 姓名
     * @param idNumber: 身份证号
     * @return int
     * @author xmh
     * @description 执行身份认证
     * @date 2022/12/5 14:50
     */
    public static int checkId(String realName, String idNumber) {
        Map<String, String> headers = new HashMap<>();
        // 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + aurhCode);
        Map<String, String> querys = new HashMap<>();
        querys.put("cardNo", idNumber);
        querys.put("realName", realName);

        try {
            HttpResponse response = doGet(host, path, headers, querys);
            System.out.println(response.toString());
            // 获取response的body
            JSONObject result = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
            // 判断是否出错
            Integer errorCode = result.getInteger("error_code");
            if (oConvertUtils.isEmpty(result) || oConvertUtils.isEmpty(errorCode) || (errorCode
                != 0)) {
                return CODE_ERR;
            }
            // 判断身份证号与名字是否匹配
            JSONObject resultJSON = result.getJSONObject("result");
            Boolean isOk = resultJSON.getBoolean("isok");
            if (oConvertUtils.isEmpty(isOk) || !isOk) {
                return CODE_MIS_MATCH;
            }
            return CODE_SUCCESS;
        } catch (Exception e) {
            log.error("实名第三方接口状态异常:" + e);
            return CODE_ERR;
        }
    }

    /**
     * @param host:    路径
     * @param path:    地址
     * @param headers: 请求头
     * @param querys:  请求参数
     * @return HttpResponse
     * @author xmh
     * @description
     * @date 2023/6/12 11:14
     */
    public static HttpResponse doGet(String host, String path,
        Map<String, String> headers,
        Map<String, String> querys)
        throws Exception {
        HttpClient httpClient = wrapClient(host);

        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        return httpClient.execute(request);
    }

    private static String buildUrl(String host, String path, Map<String, String> querys)
        throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }
}
