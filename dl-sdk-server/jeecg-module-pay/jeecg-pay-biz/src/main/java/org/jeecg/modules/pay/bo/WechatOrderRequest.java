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

/**
 * @author xmh
 * @version V1.0
 * @description: 微信下单对象
 * @date: 2022/12/31
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="微信下单对象", description="微信下单对象")
public class WechatOrderRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JSONField(name = "mchid")
    private String mchId;

    @ApiModelProperty(value = "商户订单号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "应用ID")
    @JSONField(name = "appid")
    private String appId;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "通知地址")
    @JSONField(name = "notify_url")
    private String notifyUrl;

    @ApiModelProperty(value = "订单金额信息")
    private Amount amount;

    @ApiModelProperty(value = "支付者信息")
    private Payer payer;

    @ApiModelProperty(value = "场景信息")
    @JSONField(name = "scene_info")
    private SceneInfo sceneInfo;
}
