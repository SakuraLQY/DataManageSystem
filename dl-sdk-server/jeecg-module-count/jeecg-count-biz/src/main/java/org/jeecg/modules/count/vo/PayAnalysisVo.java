package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 转换数据的展示
 * @author: chenglin
 * @date: 2023年05月24日 10:48
 */
@Data
public class PayAnalysisVo implements Serializable {
    /**ID*/
    @Excel(name = "ID", width = 15)
    @ApiModelProperty(value = "ID")
    private String typeId;
    /**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser;
    /**老用户付费人数*/
    @Excel(name = "老用户付费人数", width = 15)
    @ApiModelProperty(value = "老用户付费人数")
    private Integer oldPayUser;
    /**总付费人数*/
    @Excel(name = "总付费人数", width = 15)
    @ApiModelProperty(value = "总付费人数")
    private Integer totalPayUser;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstPayMoney;
    /**老用户付费金额*/
    @Excel(name = "老用户付费金额", width = 15)
    @ApiModelProperty(value = "老用户付费金额")
    private BigDecimal oldPayMoney;
    /**总付费金额*/
    @Excel(name = "总付费金额", width = 15)
    @ApiModelProperty(value = "总付费金额")
    private BigDecimal totalPayMoney;
    /**累计付费金额*/
    @Excel(name = "累计付费金额", width = 15)
    @ApiModelProperty(value = "累计付费金额")
    private BigDecimal aliveTotalMoney;
    /**老用户ARPU*/
    @Excel(name = "老用户ARPU", width = 15)
    @ApiModelProperty(value = "老用户ARPU")
    private BigDecimal oldArpu;
    /**老用户ARPPU*/
    @Excel(name = "老用户ARPPU", width = 15)
    @ApiModelProperty(value = "老用户ARPPU")
    private BigDecimal oldArppu;
    /**ARPU*/
    @Excel(name = "ARPU", width = 15)
    @ApiModelProperty(value = "ARPU")
    private BigDecimal arpu;
    /**ARPPU*/
    @Excel(name = "ARPPU", width = 15)
    @ApiModelProperty(value = "ARPPU")
    private BigDecimal arppu;
    /**新增付费率*/
    @Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private String firstPayRate;
    /**总付费率*/
    @Excel(name = "总付费率", width = 15)
    @ApiModelProperty(value = "总付费率")
    private String totalRate;
}
