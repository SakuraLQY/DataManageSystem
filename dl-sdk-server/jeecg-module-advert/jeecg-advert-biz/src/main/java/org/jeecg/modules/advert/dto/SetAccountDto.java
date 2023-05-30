package org.jeecg.modules.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: op_material
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
@Data
@ApiModel(value = "前端传来对象", description = "前端传来对象")
public class SetAccountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "头条/广点通/快手  1/2/3")
    private Integer type;
    @ApiModelProperty(value = "账号")
    private Integer accountId;
}
