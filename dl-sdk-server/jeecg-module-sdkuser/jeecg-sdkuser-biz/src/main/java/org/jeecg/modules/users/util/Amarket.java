package org.jeecg.modules.users.util;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.http.HttpHeaders;

/**
 * @version V1.0
 * @description: amarket 第三方api验证
 * @author: xmh
 * @date: 2022/12/5 11:42
 */
public class Amarket {

    // 实名验证错误时返回码
    public static final Integer CODE_ERR = -9;
    // 身份不匹配时返回码
    public static final Integer CODE_MIS_MATCH = -10;
    // 成功时返回码
    public static final Integer CODE_SUCCESS = 0;
    // 身份验证请求地址
    private static final String url = "https://lfeid.market.alicloudapi.com/idcheck/life";
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
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPCODE " + aurhCode);
        // 构造参数
        JSONObject params = new JSONObject();
        params.put("cardNo", idNumber);
        params.put("realName", realName);
        // 请求身份认证
        JSONObject result = RestUtil.get(url, headers, params, null);
        // 判断是否出错
        Integer errorCode = result.getInteger("error_code");
        if (oConvertUtils.isEmpty(result) || oConvertUtils.isEmpty(errorCode) || (errorCode != 0)) {
            return CODE_ERR;
        }
        // 判断身份证号与名字是否匹配
        JSONObject resultJSON = result.getJSONObject("result");
        Boolean isOk = resultJSON.getBoolean("isok");
        if (oConvertUtils.isEmpty(isOk) || !isOk) {
            return CODE_MIS_MATCH;
        }
        return CODE_SUCCESS;
    }
}
