package org.jeecg.modules.advert.api.jrtt.account.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 刷新Refresh Token 响应参数
 * @Author: chenyw
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttRefreshTokenResponse {

    /**
     * 开发者申请的应用APP_ID
     **/
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * 开发者应用的私钥Secret
     **/
    @JSONField(name = "expires_in")
    private Long expiresIn;

    /**
     * 授权类型
     **/
    @JSONField(name = "refresh_token")
    private String refreshToken;

    /**
     * 刷新token
     **/
    @JSONField(name = "refresh_token_expires_in")
    private Long refreshTokenExpiresIn;

}
