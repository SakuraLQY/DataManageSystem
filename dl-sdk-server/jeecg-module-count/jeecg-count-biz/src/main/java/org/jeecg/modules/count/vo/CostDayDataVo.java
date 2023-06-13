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
@ApiModel(value="查询投入成本", description="查询投入成本")
public class CostDayDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**日期*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String roiDate;
    /**游戏id*/
    @ApiModelProperty(value = "游戏id")
    private java.lang.Integer gameId;
    /**子游戏id*/
    @ApiModelProperty(value = "子游戏id")
    private java.lang.Integer subGameId;
    /**渠道游戏包id*/
    @ApiModelProperty(value = "渠道游戏包id")
    private java.lang.Integer pkgId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private java.lang.Integer channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
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
    /**新增付费*/
    @ApiModelProperty(value = "新增付费")
    private Integer firstMoney;
    /**活跃付费*/
    @ApiModelProperty(value = "活跃付费")
    private Integer aliveMoney;
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    /**新增付费人数*/
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstUser;
    /**下载*/
    @ApiModelProperty(value = "下载")
    private Integer download;
    /**展示*/
    @ApiModelProperty(value = "展示")
    private Integer exhibition;

}
