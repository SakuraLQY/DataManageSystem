package org.jeecg.modules.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 统一下单接口接收参数
 * @date: 2022/12/19
 **/
@Data
public class OpenDto {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**
     * 子游戏ID
     */
    @NotNull(message = "子游戏ID不能为空")
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;
    /**
     * 子游戏ID
     */
    @NotNull(message = "一级游戏包ID不能为空")
    @ApiModelProperty(value = "一级游戏包ID")
    private Integer pkgId;
    /**
     * 广告ID
     */
    @ApiModelProperty(value = "广告ID")
    private Integer dealId = 0;
    /**
     * 充值类型
     */
    @NotNull(message = "充值类型不能为空")
    @ApiModelProperty(value = "充值类型")
    private Integer orderType;
    /**
     * 游戏订单ID
     */
    @ApiModelProperty(value = "游戏订单ID")
    private String subGameOrderId;
    /**
     * 设备
     */
    @NotEmpty(message = "设备不能为空")
    @ApiModelProperty(value = "设备")
    private String device;
    /**
     * IP
     */
    @ApiModelProperty(value = "IP地址")
    private String clientIp = "";
    /**
     * 支付金额
     */
    @NotNull(message = "支付金额不能为空")
    @ApiModelProperty(value = "支付金额")
    private BigDecimal mmm;
    /**
     * 优惠金额
     */
    @ApiModelProperty(value = "优惠金额")
    private BigDecimal couponPrice = BigDecimal.ZERO;
    /**
     * 订单备注
     */
    @NotEmpty(message = "订单备注不能为空")
    @ApiModelProperty(value = "订单备注")
    private String desc;
    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码")
    private String userPassword;
    /**
     * 是否使用平台币抵扣, 默认不使用
     */
    @ApiModelProperty(value = "是否使用平台币抵扣")
    private Integer usePlatformCurrency = 0;
    /**
     * 服务器ID
     */
    @ApiModelProperty(value = "服务器ID")
    private Integer serverId = 0;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private String roleId = "0";
    /**
     * 角色等级
     */
    @ApiModelProperty(value = "角色等级")
    private Integer roleLevel = 0;
    /**
     * 透传参数
     */
    @ApiModelProperty(value = "透传参数")
    private String subGameData;
    /**
     * 微信跳转url
     */
    @ApiModelProperty(value = "微信跳转url")
    private String wechatRedirectUrl;
}
