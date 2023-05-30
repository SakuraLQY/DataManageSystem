package org.jeecg.modules.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description: ios订单信息
 * @date: 2022/1/14
 **/
@Data
@ApiModel(value="ios订单信息", description="ios订单信息")
public class IosPayInfoVo {

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String productId;

}
