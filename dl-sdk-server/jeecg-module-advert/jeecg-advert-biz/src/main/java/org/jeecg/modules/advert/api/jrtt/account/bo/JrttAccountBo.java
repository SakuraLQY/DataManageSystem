package org.jeecg.modules.advert.api.jrtt.account.bo;

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
public class JrttAccountBo {

    /**
     * 账号id
     **/
    private Integer accountId;

    /**
     * 广告组id
     **/
    private Long advertiserId;

    /**
     * accessToken
     **/
    private String accessToken;

}
