package org.jeecg.modules.advert.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: CreateProjectRspVo
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/17 15:28
 * @version: 1.0
 */

@Data
@AllArgsConstructor
public class CreateProjectRspVo {

    private Integer dealId;

    private Integer code;

    private String message;
}
