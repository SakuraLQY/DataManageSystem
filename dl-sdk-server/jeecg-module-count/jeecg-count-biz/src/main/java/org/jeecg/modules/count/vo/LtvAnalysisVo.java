package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: Ltv数据统计显示字段
 * @author: chenglin
 * @date: 2023年05月24日 18:02
 */
@Data
public class LtvAnalysisVo implements Serializable {
    /**ID*/
    @Excel(name = "ID", width = 15)
    @ApiModelProperty(value = "ID")
    private String typeId;
    /**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
    /**LTV1*/
    @Excel(name = "LTV1", width = 15)
    @ApiModelProperty(value = "LTV1")
    private BigDecimal ltv1;
    /**LTV2*/
    @Excel(name = "LTV2", width = 15)
    @ApiModelProperty(value = "LTV2")
    private BigDecimal ltv2;
    /**LTV3*/
    @Excel(name = "LTV3", width = 15)
    @ApiModelProperty(value = "LTV3")
    private BigDecimal ltv3;
    /**LTV4*/
    @Excel(name = "LTV4", width = 15)
    @ApiModelProperty(value = "LTV4")
    private BigDecimal ltv4;
    /**LTV5*/
    @Excel(name = "LTV5", width = 15)
    @ApiModelProperty(value = "LTV5")
    private BigDecimal ltv5;
    /**LTV6*/
    @Excel(name = "LTV6", width = 15)
    @ApiModelProperty(value = "LTV6")
    private BigDecimal ltv6;
    /**LTV7*/
    @Excel(name = "LTV7", width = 15)
    @ApiModelProperty(value = "LTV7")
    private BigDecimal ltv7;
    /**LTV15*/
    @Excel(name = "LTV15", width = 15)
    @ApiModelProperty(value = "LTV15")
    private BigDecimal ltv15;
    /**LTV30*/
    @Excel(name = "LTV30", width = 15)
    @ApiModelProperty(value = "LTV30")
    private BigDecimal ltv30;
    /**LTV45*/
    @Excel(name = "LTV45", width = 15)
    @ApiModelProperty(value = "LTV45")
    private BigDecimal ltv45;
    /**LTV60*/
    @Excel(name = "LTV60", width = 15)
    @ApiModelProperty(value = "LTV60")
    private BigDecimal ltv60;
    /**LTV90*/
    @Excel(name = "LTV90", width = 15)
    @ApiModelProperty(value = "LTV90")
    private BigDecimal ltv90;
    /**LTV120*/
    @Excel(name = "LTV120", width = 15)
    @ApiModelProperty(value = "LTV120")
    private BigDecimal ltv120;
    /**LTV150*/
    @Excel(name = "LTV150", width = 15)
    @ApiModelProperty(value = "LTV150")
    private BigDecimal ltv150;
}
