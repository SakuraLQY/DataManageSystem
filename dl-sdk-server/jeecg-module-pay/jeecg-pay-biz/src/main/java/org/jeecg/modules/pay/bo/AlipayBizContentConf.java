package org.jeecg.modules.pay.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lili
 * @Description 调用支付宝下单参数
 * @Date 2022-12-24
 **/
@Data
@ApiModel(value = "调用支付宝下单参数", description = "调用支付宝下单参数")
public class AlipayBizContentConf {

    @ApiModelProperty(value = "订单标题")
    private String subject;
    @ApiModelProperty(value = "订单ID")
    @JSONField(name = "out_trade_no")
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    @ApiModelProperty(value = "接口")
    @JSONField(name = "product_code")
    @JsonProperty("product_code")
    private String productCode;
    @ApiModelProperty(value = "订单标题")
    @JSONField(name = "timeout_express")
    @JsonProperty("timeout_express")
    private String timeoutExpress;
    @ApiModelProperty(value = "绝对超时时间")
    @JSONField(name = "time_expire")
    @JsonProperty("time_expire")
    private String timeExpire;
    @ApiModelProperty(value = "最终金额")
    @JSONField(name = "total_amount")
    @JsonProperty("total_amount")
    private String totalAmount;

}
