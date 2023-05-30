package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 计算付费返回给前端的数据信息
 * @author: chenglin
 * @date: 2023年05月23日 14:52
 */
@Data
public class ComputePayVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**注册日期*/
    @Excel(name = "注册日期", width = 15)
    @ApiModelProperty(value = "注册日期")
    private java.lang.String regTime;
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private java.lang.Integer regCount;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private java.lang.Integer firstPayUser;
    /**新增付费*/
    @Excel(name = "新增付费", width = 15)
    @ApiModelProperty(value = "新增付费")
    private BigDecimal firstPay;
    /**期间付费人数*/
    @Excel(name = "期间付费人数", width = 15)
    @ApiModelProperty(value = "期间付费人数")
    private java.lang.Integer periodUser;
    /**期间付费*/
    @Excel(name = "期间付费", width = 15)
    @ApiModelProperty(value = "期间付费")
    private BigDecimal periodMoney;
}
