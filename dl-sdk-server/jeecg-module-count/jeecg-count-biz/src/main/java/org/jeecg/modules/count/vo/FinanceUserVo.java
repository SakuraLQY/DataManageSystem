package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class FinanceUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**日期*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String roiDate;
	/**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private String gameName;
	/**渠道*/
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private String channelName;
    /**广告*/
    @ApiModelProperty(value = "广告")
    private String dealName;
    /**推广费用*/
    @Excel(name = "推广费用", width = 15)
    @ApiModelProperty(value = "推广费用")
    private BigDecimal promotionCost;
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
    /**注册单价*/
    @Excel(name = "注册单价", width = 15)
    @ApiModelProperty(value = "注册单价")
    private BigDecimal regUnitPrice;
    /**付费用户数*/
    @Excel(name = "付费用户数", width = 15)
    @ApiModelProperty(value = "付费用户数")
    private Integer userPayCount;
    /**付费单价*/
    @Excel(name = "付费单价", width = 15)
    @ApiModelProperty(value = "付费单价")
    private BigDecimal costUnitPrice;
    /**总留存*/
    @Excel(name = "总留存", width = 15)
    @ApiModelProperty(value = "总留存")
    private Integer totalRetention;
    /**付费留存*/
    @Excel(name = "付费留存", width = 15)
    @ApiModelProperty(value = "付费留存")
    private Integer payRetention;
    /**利润*/
    @Excel(name = "利润", width = 15)
    @ApiModelProperty(value = "利润")
    private BigDecimal profit;


}
