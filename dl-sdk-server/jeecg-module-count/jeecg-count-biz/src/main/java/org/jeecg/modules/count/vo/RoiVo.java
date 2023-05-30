package org.jeecg.modules.count.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class RoiVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**日期*/
    @ApiModelProperty(value = "日期")
    private String roiDate;
	/**游戏*/
    @ApiModelProperty(value = "游戏")
    private String gameName;
	/**渠道*/
    @ApiModelProperty(value = "渠道")
    private String channelName;
	/**广告*/
    @ApiModelProperty(value = "广告")
    private String dealName;
    /**推广费用*/
    @Excel(name = "推广费用", width = 15)
    @ApiModelProperty(value = "推广费用")
    private BigDecimal promotionCost;
    /**新增注册数*/
    @Excel(name = "新增注册数", width = 15)
    @ApiModelProperty(value = "新增注册数")
    private Integer regCount;
    /**剩余DAU*/
    @Excel(name = "剩余DAU", width = 15)
    @ApiModelProperty(value = "剩余DAU")
    private Integer remainDAU;
    /**付费DAU*/
    @Excel(name = "付费DAU", width = 15)
    @ApiModelProperty(value = "付费DAU")
    private BigDecimal costDAU;
	/**注册单价*/
    @Excel(name = "注册单价", width = 15)
    @ApiModelProperty(value = "注册单价")
    private BigDecimal regUnitPrice;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer costCount;
    /**付费率*/
    @Excel(name = "付费率", width = 15)
    @ApiModelProperty(value = "付费率")
    private BigDecimal costProbability;
    /**付费单价*/
    @Excel(name = "付费单价", width = 15)
    @ApiModelProperty(value = "付费单价")
    private BigDecimal costUnitPrice;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal addCostPrice;
    /**累计付费金额*/
    @Excel(name = "累计付费金额", width = 15)
    @ApiModelProperty(value = "累计付费金额")
    private BigDecimal allCostPrice;
    /**sdk分成所得*/
    @Excel(name = "sdk分成所得", width = 15)
    @ApiModelProperty(value = "sdk分成所得")
    private BigDecimal sdkShare;
    /**利润*/
    @Excel(name = "利润", width = 15)
    @ApiModelProperty(value = "利润")
    private BigDecimal profit;
    /**预估ROI*/
    @Excel(name = "预估ROI", width = 15)
    @ApiModelProperty(value = "预估ROI")
    private BigDecimal estimatedROI;
    /**累计ROI*/
    @Excel(name = "累计ROI", width = 15)
    @ApiModelProperty(value = "累计ROI")
    private BigDecimal allROI;
    /**首日ROI*/
    @Excel(name = "首日ROI", width = 15)
    @ApiModelProperty(value = "首日ROI")
    private BigDecimal firstROI;
    /**剩余ROI*/
    @Excel(name = "剩余ROI", width = 15)
    @ApiModelProperty(value = "剩余ROI")
    private Map<String, BigDecimal> remainROI;
    /**真实ROI*/
    @Excel(name = "真实ROI", width = 15)
    @ApiModelProperty(value = "真实ROI")
    private BigDecimal realROI;



}
