package org.jeecg.modules.count.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 自定义投放数据模型类
 * @author: chenglin
 * @date: 2023年05月10日 15:42
 */
@Data
@TableName("op_cost")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_cost对象", description="返回给前端的对象")
public class SummaryLaunchVo implements Serializable {
    /**ID*/
    @ApiModelProperty(value = "ID")
    private String lanuchId;
    /**名称*/
    @ApiModelProperty(value = "名称")
    private String name;
    /**成本*/
    @ApiModelProperty(value = "成本")
    private BigDecimal cost;
    /**激活数*/
    @ApiModelProperty(value = "激活数")
    private Integer countActive;
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    /**DAU*/
    @ApiModelProperty(value = "DAU")
    private Integer countDau;
    /**有效注册数*/
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser;
    /**激活注册率*/
    @ApiModelProperty(value = "激活注册率")
    private String registrationRate;
    /**注册单价*/
    @ApiModelProperty(value = "注册单价")
    private BigDecimal registryPrice;
    /**首日付费额*/
    @ApiModelProperty(value = "首日付费额")
    private BigDecimal firstMoney;
    /**首日付费人数*/
    @ApiModelProperty(value = "首日付费人数")
    private Integer firstPayuser;
    /**首日付费率*/
    @ApiModelProperty(value = "首日付费率")
    private String firstPayrate;
    /**首日付费单价*/
    @ApiModelProperty(value = "首日付费单价")
    private BigDecimal firstPayprice;
    /**首日arpu*/
    @ApiModelProperty(value = "首日arpu")
    private BigDecimal firstArpu;
    /**首日arppu*/
    @ApiModelProperty(value = "首日arppu")
    private BigDecimal firstArppu;
    /**首日ROI*/
    @ApiModelProperty(value = "首日ROI")
    private String firstRoi;
}
