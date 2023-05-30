package org.jeecg.modules.pay.bo.wx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xmh
 * @version V1.0
 * @description: 订单金额信息
 * @date: 2022/12/31
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ApiModel(value="订单金额信息", description="订单金额信息")
public class Amount implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单总金额，单位为分")
    private int total;
    @ApiModelProperty(value = "货币类型")
    private String currency;
}
