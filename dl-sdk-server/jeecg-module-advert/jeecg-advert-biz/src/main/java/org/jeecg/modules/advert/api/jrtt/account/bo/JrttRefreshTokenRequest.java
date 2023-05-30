package org.jeecg.modules.advert.api.jrtt.account.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 刷新Refresh Token 请求参数
 * @Author: chenyw
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttRefreshTokenRequest {

    /**
     * 开发者申请的应用APP_ID
     **/
    @JSONField(name = "app_id")
    private String appId;

    /**
     * 开发者应用的私钥Secret
     **/
    @JSONField(name = "secret")
    private String secret;

    /**
     * 授权类型
     **/
    @JSONField(name = "grant_type")
    private String grantType;

    /**
     * 刷新token
     **/
    @JSONField(name = "refresh_token")
    private String refreshToken;

}
