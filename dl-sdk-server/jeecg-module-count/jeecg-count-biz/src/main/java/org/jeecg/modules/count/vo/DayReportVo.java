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
public class DayReportVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**日期*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String roiDate;
	/**产品*/
    @Excel(name = "产品", width = 15)
    @ApiModelProperty(value = "产品")
    private String gameName;
	/**渠道*/
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private String channelName;
    /**渠道游戏包id*/
    @ApiModelProperty(value = "渠道游戏包id")
    private java.lang.Integer pkgId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
    /**曝光*/
    @ApiModelProperty(value = "曝光")
    private Integer exhibition;
    /**下载数*/
    @ApiModelProperty(value = "下载数")
    private Integer download;
    /**下载率*/
    @ApiModelProperty(value = "下载率")
    private BigDecimal downloadRate;
    /**下载单价*/
    @ApiModelProperty(value = "下载单价")
    private BigDecimal downloadUnitPrice;
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    /**注册率*/
    @Excel(name = "注册率", width = 15)
    @ApiModelProperty(value = "注册率")
    private BigDecimal regProbability;
    /**注册单价*/
    @ApiModelProperty(value = "注册单价")
    private BigDecimal regUnitPrice;
    /**付费单价*/
    @Excel(name = "付费单价", width = 15)
    @ApiModelProperty(value = "付费单价")
    private BigDecimal costUnitPrice;
//    /**消耗*/
//    @ApiModelProperty(value = "消耗")
//    private BigDecimal consume;
//    /**现金消耗*/
//    @ApiModelProperty(value = "现金消耗")
//    private BigDecimal moneyConsume;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstUser;
    /**新增付费率*/
    @Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private BigDecimal addCostProbability;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private Integer firstMoney;
    /**新增ARPU*/
    @Excel(name = "新增ARPU", width = 15)
    @ApiModelProperty(value = "新增ARPU")
    private BigDecimal firstArpu;
    /**活跃付费金额*/
    @Excel(name = "活跃付费金额", width = 15)
    @ApiModelProperty(value = "活跃付费金额")
    private Integer aliveMoney;
    /**首日ROI*/
    @Excel(name = "首日ROI", width = 15)
    @ApiModelProperty(value = "首日ROI")
    private BigDecimal firstROI;
    /**投产比*/
    @Excel(name = "投产比", width = 15)
    @ApiModelProperty(value = "投产比")
    private BigDecimal productionRatio;

    /**现金消耗*/
    @ApiModelProperty(value = "现金消耗")
    private BigDecimal realCostMoney;
    /**成本金额*/
    @ApiModelProperty(value = "成本金额")
    private BigDecimal costMoney;
    /**新增分成所得*/
    @ApiModelProperty(value = "新增分成所得")
    private BigDecimal profit;
    /**活跃付费分成所得*/
    @ApiModelProperty(value = "活跃付费分成所得")
    private BigDecimal aliveProfit;
//    /**新增付费*/
//    @ApiModelProperty(value = "新增付费")
//    private Integer firstMoney;
//    /**活跃付费*/
//    @ApiModelProperty(value = "活跃付费")
//    private Integer aliveMoney;
//    /**注册数*/
//    @ApiModelProperty(value = "注册数")
//    private Integer countUser;
//    /**新增付费人数*/
//    @ApiModelProperty(value = "新增付费人数")
//    private Integer firstUser;
//    /**下载*/
//    @ApiModelProperty(value = "下载")
//    private Integer download;
//    /**展示*/
//    @ApiModelProperty(value = "展示")
//    private Integer exhibition;


}
