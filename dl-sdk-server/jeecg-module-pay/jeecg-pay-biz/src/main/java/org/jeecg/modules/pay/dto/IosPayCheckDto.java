package org.jeecg.modules.pay.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description: ios校验订单请求参数
 * @date: 2023/1/4
 **/
@Data
@ApiModel(value="ios校验订单请求参数", description="ios校验订单请求参数")
public class IosPayCheckDto {

    /**
     * 子游戏id
     */
    @ApiModelProperty(value = "子游戏id")
    private Integer subGameId;

    /**
     * 渠道游戏包ID
     */
    @ApiModelProperty(value = "渠道游戏包ID")
    private Integer pkgId;

    /**
     * 设备号
     */
    @ApiModelProperty(value = "设备号")
    private String device;

    /**
     * 支付类型
     */
    @ApiModelProperty(value = "支付类型")
    private String mmmType;

    /**
     * 订单id
     */
    @NotBlank(message = "订单id不能为空")
    @ApiModelProperty(value = "订单id")
    private String orderId;

    /**
     * ios包版本
     */
    @ApiModelProperty(value = "ios包版本")
    private String pkgVer;

    /**
     * ios支付收条
     */
    @NotBlank(message = "ios支付收条不能为空")
    @ApiModelProperty(value = "ios支付收条")
    private String receipt;

    /**
     * 交易id 相当于order表的bank_order_id
     */
    @NotBlank(message = "交易id不能为空")
    @ApiModelProperty(value = "交易id")
    private String transactionId;

    /**
     * 签名
     */
    @ApiModelProperty(value = "签名")
    private String sign;
}
