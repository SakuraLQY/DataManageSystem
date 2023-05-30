package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 临时存储下成本和ID数据
 * @author: chenglin
 * @date: 2023年05月10日 16:44
 */
@Data
@Accessors(chain = true)
public class LaunchTempBo<T> implements Serializable {
    @ApiModelProperty(value = "ID")
    private T ID;
    @ApiModelProperty(value = "成本")
    private BigDecimal cost;
}
