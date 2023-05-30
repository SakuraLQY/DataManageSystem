package org.jeecg.modules.advert.api.jrtt.account.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取Access Token 响应参数
 * @Author: chenyw
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttAccessTokenResponse {

    /**
     * 用于验证权限的token
     **/
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * access_token剩余有效时间,单位(秒)
     **/
    @JSONField(name = "expires_in")
    private Long expiresIn;

    /**
     * 刷新access_token,用于获取新的access_token和refresh_token，并且刷新过期时间
     **/
    @JSONField(name = "refresh_token")
    private String refreshToken;

    /**
     * refresh_token剩余有效时间,单位(秒)
     **/
    @JSONField(name = "refresh_token_expires_in")
    private Long refreshTokenExpiresIn;

    /**
     * 授权的账户id列表
     **/
    @JSONField(name = "advertiser_ids")
    private List<String> advertiserIds;

}
