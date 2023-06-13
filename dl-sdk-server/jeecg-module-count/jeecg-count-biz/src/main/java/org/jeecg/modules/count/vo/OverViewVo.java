package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
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
public class OverViewVo implements Serializable {
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
    /**老用户付费金额*/
    @Excel(name = "老用户付费金额", width = 15)
    @ApiModelProperty(value = "老用户付费金额")
    private BigDecimal oldUserPay;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal addCostPrice;
    /**活跃付费金额*/
    @Excel(name = "活跃付费金额", width = 15)
    @ApiModelProperty(value = "活跃付费金额")
    private BigDecimal activeCostPrice;
    /**活跃费率*/
    @Excel(name = "活跃费率", width = 15)
    @ApiModelProperty(value = "活跃费率")
    private BigDecimal activeProbability;
    /**新增付费率*/
    @Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private BigDecimal addCostProbability;
    /**老用户付费率*/
    @Excel(name = "老用户付费率", width = 15)
    @ApiModelProperty(value = "老用户付费率")
    private BigDecimal oldUserPayProbability;
    /**ARPPU*/
    @Excel(name = "ARPPU", width = 15)
    @ApiModelProperty(value = "ARPPU")
    private BigDecimal arppu;
    /**新增ARPPU*/
    @Excel(name = "新增ARPPU", width = 15)
    @ApiModelProperty(value = "新增ARPPU")
    private BigDecimal addArppu;
    /**老用户付费金额 arppu*/
    @Excel(name = "老用户付费金额 arppu", width = 15)
    @ApiModelProperty(value = "老用户付费金额 arppu")
    private BigDecimal oldUserPayArppu;
    /**DAU*/
    @Excel(name = "DAU", width = 15)
    @ApiModelProperty(value = "DAU")
    private Integer dau;
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
    /**活跃付费人数*/
    @Excel(name = "活跃付费人数", width = 15)
    @ApiModelProperty(value = "活跃付费人数")
    private Integer activeCostCount;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer addCostCount;

}
