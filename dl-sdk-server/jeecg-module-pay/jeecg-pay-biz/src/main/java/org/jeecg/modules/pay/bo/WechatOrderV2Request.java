package org.jeecg.modules.pay.bo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.pay.bo.wx.Amount;
import org.jeecg.modules.pay.bo.wx.Payer;
import org.jeecg.modules.pay.bo.wx.SceneInfo;
import org.jeecg.modules.pay.bo.wx.SceneInfoH5V2;

/**
 * @author lili
 * @version V1.0
 * @description: 微信下单对象
 * @date: 2022/12/31
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="微信V2下单对象", description="微信V2下单对象")
public class WechatOrderV2Request implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JSONField(name = "mch_id")
    private String mchId;

    @ApiModelProperty(value = "商户订单号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "应用ID")
    @JSONField(name = "appid")
    private String appId;

    @ApiModelProperty(value = "商品描述")
    private String body;

    @ApiModelProperty(value = "随机字符串")
    @JSONField(name = "nonce_str")
    private String nonceStr;

    @ApiModelProperty(value = "订单总金额，单位为分")
    @JSONField(name = "total_fee")
    private Integer totalFee;

    @ApiModelProperty(value = "终端IP")
    @JSONField(name = "spbill_create_ip")
    private String spbillCreateIp;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "通知地址")
    @JSONField(name = "notify_url")
    private String notifyUrl;

    @ApiModelProperty(value = "附加数据")
    @JSONField(name = "trade_type")
    private String tradeType;

    @ApiModelProperty(value = "场景信息")
    @JSONField(name = "scene_info")
    private SceneInfoH5V2 sceneInfo;


}
