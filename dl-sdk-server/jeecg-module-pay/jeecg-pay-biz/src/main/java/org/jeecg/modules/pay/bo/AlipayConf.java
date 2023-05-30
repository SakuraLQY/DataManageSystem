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
public class AlipayConf {

    @ApiModelProperty(value = "应用ID")
    @JSONField(name = "app_id")
    private String appId;
    @ApiModelProperty(value = "合作者ID")
    @JSONField(name = "seller_id")
    private String sellerId;
    @ApiModelProperty(value = "方法")
    private String method;
    @ApiModelProperty(value = "编码")
    private String charset;
    @ApiModelProperty(value = "签名类型")
    @JSONField(name = "sign_type")
    private String signType;
    @ApiModelProperty(value = "时间")
    private String timestamp;
    @ApiModelProperty(value = "版本")
    private String version;
//    @ApiModelProperty(value = "合作者私钥")
//    private String sellerPrikey;
//    @ApiModelProperty(value = "支付宝公钥")
//    private String alipayPubkey;
    @ApiModelProperty(value = "异步通知地址")
    @JSONField(name = "notify_url")
    @JsonProperty("notify_url")
    private String notifyUrl;
    @ApiModelProperty(value = "签名")
    private String sign;
    @ApiModelProperty(value = "商品参数")
    @JSONField(name = "biz_content")
    @JsonProperty("biz_content")
    private AlipayBizContentConf  bizContent;

}
