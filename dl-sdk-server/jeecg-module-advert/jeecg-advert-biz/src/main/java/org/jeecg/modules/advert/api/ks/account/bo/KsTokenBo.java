package org.jeecg.modules.advert.api.ks.account.bo;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.bo
 * @className: JrttTokenBo
 * @author:
 * @description: 头条token信息bo
 * @date: 2023/1/13 11:21
 * @version: 1.0
 */
@Data
public class KsTokenBo {

    /**
     * token
     **/
    private String accessToken;

    /**
     * 广告组id
     **/
    private Long advertiserId;


}
