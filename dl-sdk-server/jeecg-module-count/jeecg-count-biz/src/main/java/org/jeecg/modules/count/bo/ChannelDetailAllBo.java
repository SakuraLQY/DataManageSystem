package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @description: 补充渠道明细合计
 * @author: chenglin
 * @date: 2023年06月15日 11:45
 */
@Data
public class ChannelDetailAllBo implements Serializable {
    @ApiModelProperty(value = "激活")
    private Integer countActive;
    /**
     * 激活设备数
     */
    @ApiModelProperty(value = "激活设备数")
    private Integer countActiveDev;
    /**
     * 注册数
     */
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    /**
     * 注册设备数
     */
    @ApiModelProperty(value = "注册设备数")
    private Integer countUserDev;
    /**
     * 有效注册数
     */
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser;
    /**
     * 新增付费人数
     */
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser;
    /**
     * 新增付费金额
     */
    @ApiModelProperty(value = "新增付费金额")
    private Integer firstMoney;
    /**
     * 累计付费金额
     */
    @ApiModelProperty(value = "累计付费金额")
    private Integer totalMoney;
    /**
     * DAU
     */
    @ApiModelProperty(value = "DAU")
    private Integer dau;
    /**
     * 活跃付费人数
     */
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayUser;
    /**
     * 活跃付费金额
     */
    @ApiModelProperty(value = "活跃付费金额")
    private Integer aliveMoney;
    /**
     * 次留
     */
    @ApiModelProperty(value = "次留")
    private Integer loyal;
    /**
     * 3日留存
     */
    @ApiModelProperty(value = "3日留存")
    private Integer loyal3;
    /**
     * 4日留存
     */
    @ApiModelProperty(value = "4日留存")
    private Integer loyal4;
    /**
     * 5日留存
     */
    @ApiModelProperty(value = "5日留存")
    private Integer loyal5;
    /**
     * 6日留存
     */
    @ApiModelProperty(value = "6日留存")
    private Integer loyal6;
    /**
     * 7日留存
     */
    @ApiModelProperty(value = "7日留存")
    private Integer loyal7;
    /**
     * LTV1
     */

    @ApiModelProperty(value = "LTV1")
    private BigDecimal ltv1;
    /**
     * LTV2
     */

    @ApiModelProperty(value = "LTV2")
    private BigDecimal ltv2;
    /**
     * LTV3
     */

    @ApiModelProperty(value = "LTV3")
    private BigDecimal ltv3;
    /**
     * LTV4
     */

    @ApiModelProperty(value = "LTV4")
    private BigDecimal ltv4;
    /**
     * LTV5
     */
    @ApiModelProperty(value = "LTV5")
    private BigDecimal ltv5;
    /**
     * LTV6
     */
    @ApiModelProperty(value = "LTV6")
    private BigDecimal ltv6;
    /**
     * LTV7
     */
    @ApiModelProperty(value = "LTV7")
    private BigDecimal ltv7;
}
