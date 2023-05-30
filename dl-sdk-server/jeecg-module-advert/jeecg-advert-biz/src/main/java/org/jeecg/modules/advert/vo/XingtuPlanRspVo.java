package org.jeecg.modules.advert.vo;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: XingtuPlanRspVo
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/10 20:20
 * @version: 1.0
 */
@Data
public class XingtuPlanRspVo {
    private Integer code;
    private String message;
    private Integer dealId;
    private String dealName;
}
