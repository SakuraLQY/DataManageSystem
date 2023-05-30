package org.jeecg.modules.advert.api.ks.account.bo;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: TOFreashAcccount
 * @author: Eric
 * @description: 要刷新token的账号
 * @date: 2023/1/13 11:21
 * @version: 1.0
 */
@Data
public class KsToRefreshAccountBo {

    /**
     * 账号id
     **/
    private Integer accountId;

    /**
     * accessToken
     **/
    private String accessToken;

    /**
     * accessToken过期时间
     **/
    private LocalDateTime accessTokenTime;

    /**
     * refresh_token
     **/
    private String refreshToken;

    /**
     * refresh_token过期时间
     **/
    private LocalDateTime refreshTokenTime;

    /**
     * 应用appId
     **/
    private String appId;

    /**
     * 应用secret
     **/
    private String appSecret;
}
