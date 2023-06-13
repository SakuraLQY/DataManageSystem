package org.jeecg.modules.count.modal;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 补充数据投放导出表
 * @author: chenglin
 * @date: 2023年06月13日 13:46
 */
@Data
public class SummaryLaunchModal implements Serializable {
    private static final long serialVersionUID = 1L;
    /**ID*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "ID")
    private String lanuchId;
    /**名称*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
    /**成本*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "成本")
    private BigDecimal cost;
    /**激活数*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "激活数")
    private Integer countActive;
    /**注册数*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    /**DAU*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "DAU")
    private Integer countDau;
    /**有效注册数*/
    @Excel(name = "账号昵称", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser;
    /**激活注册率*/
    @Excel(name = "激活注册率", width = 15)
    @ApiModelProperty(value = "激活注册率")
    private String registrationRate;
    /**注册单价*/
    @Excel(name = "注册单价", width = 15)
    @ApiModelProperty(value = "注册单价")
    private BigDecimal registryPrice;
    /**首日付费额*/
    @Excel(name = "首日付费额", width = 15)
    @ApiModelProperty(value = "首日付费额")
    private BigDecimal firstMoney;
    /**首日付费人数*/
    @Excel(name = "首日付费人数", width = 15)
    @ApiModelProperty(value = "首日付费人数")
    private Integer firstPayuser;
    /**首日付费率*/
    @Excel(name = "首日付费率", width = 15)
    @ApiModelProperty(value = "首日付费率")
    private String firstPayrate;
    /**首日付费单价*/
    @Excel(name = "首日付费单价", width = 15)
    @ApiModelProperty(value = "首日付费单价")
    private BigDecimal firstPayprice;
    /**首日arpu*/
    @Excel(name = "首日arpu", width = 15)
    @ApiModelProperty(value = "首日arpu")
    private BigDecimal firstArpu;
    /**首日arppu*/
    @Excel(name = "首日arppu", width = 15)
    @ApiModelProperty(value = "首日arppu")
    private BigDecimal firstArppu;
    /**首日ROI*/
    @Excel(name = "首日ROI", width = 15)
    @ApiModelProperty(value = "首日ROI")
    private String firstRoi;
}
