package org.jeecg.modules.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description: ios切支付列表
 * @date: 2022/1/14
 **/
@Data
@ApiModel(value = "ios切支付对象", description = "ios切支付对象")
public class IosCheckPayModelVo {

    /**
     * 是否是ios默认支付
     */
    @ApiModelProperty(value = "是否是ios默认支付 1为是 0为否")
    private Integer state;

    /**
     * 苹果订单信息 对象名去掉了pay关键字
     */
    @ApiModelProperty(value = "苹果订单信息")
    private IosPayInfoVo iosInfo;

    /**
     * 苹果支付列表 对象名去掉了pay关键字
     */
    @ApiModelProperty(value = "苹果第三方支付列表")
    private IosPayMenuVo thirdMenu;
}
