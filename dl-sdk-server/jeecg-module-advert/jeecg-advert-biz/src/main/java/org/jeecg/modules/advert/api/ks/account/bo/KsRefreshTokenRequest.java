package org.jeecg.modules.advert.api.ks.account.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 刷新Refresh Token 请求参数
 * @Author: lili
 * @Date:   2023-03-07
 * @Version: V1.0
 */
@Data
public class KsRefreshTokenRequest {

    /**
     * 申请应用后快手返回的 app_id
     **/
    @JSONField(name = "app_id")
    private String appId;

    /**
     * 申请应用后快手返回的 secret
     **/
    @JSONField(name = "secret")
    private String secret;

    /**
     * 最近一次快手返回的 refresh_token
     **/
    @JSONField(name = "refresh_token")
    private String refreshToken;

}
