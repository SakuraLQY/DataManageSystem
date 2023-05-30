package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import java.util.Date;
import lombok.Data;

/**
 * @description: 作为渠道信息查询出来的临时对象
 * @author: chenglin
 * @date: 2023年05月12日 10:23
 */
@Data
public class ChannelDetailBo<T> implements Serializable {
    @ApiModelProperty(value = "日期")
    private Date timeDaily;
    @ApiModelProperty(value = "游戏id")
    private T gameName;
    @ApiModelProperty(value = "子游戏Id")
    private T subGameName;
    @ApiModelProperty(value = "渠道Id")
    private T channel;
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
    private Integer ltv1;
    /**
     * LTV2
     */

    @ApiModelProperty(value = "LTV2")
    private Integer ltv2;
    /**
     * LTV3
     */

    @ApiModelProperty(value = "LTV3")
    private Integer ltv3;
    /**
     * LTV4
     */

    @ApiModelProperty(value = "LTV4")
    private Integer ltv4;
    /**
     * LTV5
     */
    @ApiModelProperty(value = "LTV5")
    private Integer ltv5;
    /**
     * LTV6
     */
    @ApiModelProperty(value = "LTV6")
    private Integer ltv6;
    /**
     * LTV7
     */
    @ApiModelProperty(value = "LTV7")
    private Integer ltv7;
}
