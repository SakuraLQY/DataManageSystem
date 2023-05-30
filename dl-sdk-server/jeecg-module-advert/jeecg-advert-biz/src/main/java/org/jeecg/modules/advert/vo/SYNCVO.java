package org.jeecg.modules.advert.vo;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: SYNCVO
 * @author: Eric
 * @description: TODO
 * @date: 2023/1/12 17:42
 * @version: 1.0
 */
@Data
public class SYNCVO {

    public static final Integer SUCCESS = 0;
    public static final Integer FAIL = 1;
    public static final Integer AUTHED = 2;

    private Long advertiserId;
    private Integer flag;

}
