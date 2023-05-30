package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @description: 返回新增付费留存的信息
 * @author: chenglin
 * @date: 2023年05月17日 15:42
 */
@Data
public class RetainNewLoyalVo implements Serializable {
    /**日期*/
    @ApiModelProperty(value = "日期")
    private String dateTime;
    /**游戏*/
    @ApiModelProperty(value = "游戏名")
    private String gameName;
    /**渠道*/
    @ApiModelProperty(value = "渠道名")
    private String channelName;
    /**广告*/
    @ApiModelProperty(value = "广告名")
    private String dealName;
    /**新增注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
    @ApiModelProperty(value = "推广费用")
    private BigDecimal costPay;
    @ApiModelProperty(value = "新增付费用户")
    private Integer firstPayUser;
    @ApiModelProperty(value = "新增付费次留")
    private String firstPayLoyal;
    @ApiModelProperty(value = "新增付费3留")
    private String firstPayLoyal3;
    @ApiModelProperty(value = "新增付费7留")
    private String firstPayLoyal7;
    @ApiModelProperty(value = "新增付费15留")
    private String firstPayLoyal15;
    @ApiModelProperty(value = "新增付费30留")
    private String firstPayLoyal30;
    @ApiModelProperty(value = "新增付费45留")
    private String firstPayLoyal45;
    @ApiModelProperty(value = "新增付费60留")
    private String firstPayLoyal60;
    @ApiModelProperty(value = "新增付费90留")
    private String firstPayLoyal90;
    @ApiModelProperty(value = "新增付费次留单价")
    private BigDecimal firstPayPrice;
    @ApiModelProperty(value = "新增付费次留3单价")
    private BigDecimal firstPayPrice3;
    @ApiModelProperty(value = "新增付费次留7单价")
    private BigDecimal firstPayPrice7;
    @ApiModelProperty(value = "新增付费次留15单价")
    private BigDecimal firstPayPrice15;
    @ApiModelProperty(value = "新增付费次留30单价")
    private BigDecimal firstPayPrice30;
    @ApiModelProperty(value = "新增付费次留45单价")
    private BigDecimal firstPayPrice45;
    @ApiModelProperty(value = "新增付费次留60单价")
    private BigDecimal firstPayPrice60;
    @ApiModelProperty(value = "新增付费次留90单价")
    private BigDecimal firstPayPrice90;



}
