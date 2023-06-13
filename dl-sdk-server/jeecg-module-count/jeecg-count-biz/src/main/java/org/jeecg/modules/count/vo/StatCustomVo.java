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
public class StatCustomVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**ID*/
    @Excel(name = "ID", width = 15)
    @ApiModelProperty(value = "ID")
    private String id;
	/**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
    /**激活数*/
    @Excel(name = "激活数", width = 15)
    @ApiModelProperty(value = "激活数")
    private Integer countActive=0;
//    /**全部激活数*/
//    @Excel(name = "全部激活数", width = 15)
//    @ApiModelProperty(value = "全部激活数")
//    private Integer allCountActive=0;
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer countUser=0;
//    /**全部注册数*/
//    @Excel(name = "全部注册数", width = 15)
//    @ApiModelProperty(value = "全部注册数")
//    private Integer allCountUser=0;
    /**有效注册数*/
    @Excel(name = "有效注册数", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser=0;
//    /**全部有效注册数*/
//    @Excel(name = "全部有效注册数", width = 15)
//    @ApiModelProperty(value = "全部有效注册数")
//    private Integer allCountValidUser=0;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayuser=0;
//    /**全部新增付费人数*/
//    @Excel(name = "全部新增付费人数", width = 15)
//    @ApiModelProperty(value = "全部新增付费人数")
//    private Integer allFirstPayuser=0;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstMoney=BigDecimal.ZERO;
//    /**全部新增付费金额*/
//    @Excel(name = "全部新增付费金额", width = 15)
//    @ApiModelProperty(value = "全部新增付费金额")
//    private BigDecimal allFirstMoney=BigDecimal.ZERO;
    /**新增付费率*/
    @Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private BigDecimal addCostRate=BigDecimal.ZERO;
//    /**全部新增付费率*/
//    @Excel(name = "全部新增付费率", width = 15)
//    @ApiModelProperty(value = "全部新增付费率")
//    private BigDecimal allAddCostRate=BigDecimal.ZERO;
    /**活跃人数*/
    @Excel(name = "活跃人数", width = 15)
    @ApiModelProperty(value = "活跃人数")
    private Integer countDau=0;
//    /**全部活跃人数*/
//    @Excel(name = "全部活跃人数", width = 15)
//    @ApiModelProperty(value = "全部活跃人数")
//    private Integer allCountDau=0;
    /**活跃付费人数*/
    @Excel(name = "活跃付费人数", width = 15)
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayuser=0;
//    /**全部活跃付费人数*/
//    @Excel(name = "全部活跃付费人数", width = 15)
//    @ApiModelProperty(value = "全部活跃付费人数")
//    private Integer allAlivePayuser=0;
    /**付费总额*/
    @Excel(name = "付费总额", width = 15)
    @ApiModelProperty(value = "付费总额")
    private BigDecimal aliveMoney=BigDecimal.ZERO;
//    /**全部付费总额*/
//    @Excel(name = "全部付费总额", width = 15)
//    @ApiModelProperty(value = "全部付费总额")
//    private BigDecimal allAliveMoney=BigDecimal.ZERO;
    /**活跃付费率*/
    @Excel(name = "活跃付费率", width = 15)
    @ApiModelProperty(value = "活跃付费率")
    private BigDecimal aliveMoneyRate=BigDecimal.ZERO;
//    /**全部活跃付费率*/
//    @Excel(name = "全部活跃付费率", width = 15)
//    @ApiModelProperty(value = "全部活跃付费率")
//    private BigDecimal allAliveMoneyRate=BigDecimal.ZERO;
    /**首日ARPU*/
    @Excel(name = "首日ARPU", width = 15)
    @ApiModelProperty(value = "首日ARPU")
    private BigDecimal firstArpu=BigDecimal.ZERO;
//    /**全部首日ARPU*/
//    @Excel(name = "全部首日ARPU", width = 15)
//    @ApiModelProperty(value = "全部首日ARPU")
//    private BigDecimal allFirstArpu=BigDecimal.ZERO;
    /**总ARPU*/
    @Excel(name = "总ARPU", width = 15)
    @ApiModelProperty(value = "总ARPU")
    private BigDecimal totalArpu=BigDecimal.ZERO;
    /**注册设备数*/
    @Excel(name = "注册设备数", width = 15)
    @ApiModelProperty(value = "注册设备数")
    private Integer countUserDev;
//    /**全部总ARPU*/
//    @Excel(name = "全部总ARPU", width = 15)
//    @ApiModelProperty(value = "全部总ARPU")
//    private BigDecimal allAllArpu=BigDecimal.ZERO;
    /**次留*/
    @Excel(name = "次留", width = 15)
    @ApiModelProperty(value = "次留")
    private BigDecimal day2=BigDecimal.ZERO;
    /**3日留存*/
    @Excel(name = "3日留存", width = 15)
    @ApiModelProperty(value = "3日留存")
    private BigDecimal day3=BigDecimal.ZERO;
    /**7日留存*/
    @Excel(name = "7日留存", width = 15)
    @ApiModelProperty(value = "7日留存")
    private BigDecimal day7=BigDecimal.ZERO;
    /**15日留存*/
    @Excel(name = "15日留存", width = 15)
    @ApiModelProperty(value = "15日留存")
    private BigDecimal day15=BigDecimal.ZERO;
    /**30日留存*/
    @Excel(name = "30日留存", width = 15)
    @ApiModelProperty(value = "30日留存")
    private BigDecimal day30=BigDecimal.ZERO;
    /**LTV1*/
    @Excel(name = "LTV1", width = 15)
    @ApiModelProperty(value = "LTV1")
    private BigDecimal ltv1=BigDecimal.ZERO;
    /**LTV3*/
    @Excel(name = "LTV3", width = 15)
    @ApiModelProperty(value = "LTV3")
    private BigDecimal ltv3=BigDecimal.ZERO;
    /**LTV7*/
    @Excel(name = "LTV7", width = 15)
    @ApiModelProperty(value = "LTV7")
    private BigDecimal ltv7=BigDecimal.ZERO;
    /**LTV30*/
    @Excel(name = "LTV30", width = 15)
    @ApiModelProperty(value = "LTV30")
    private BigDecimal ltv30=BigDecimal.ZERO;
    /**LTV60*/
    @Excel(name = "LTV60", width = 15)
    @ApiModelProperty(value = "LTV60")
    private BigDecimal ltv60=BigDecimal.ZERO;
    /**LTV90*/
    @Excel(name = "LTV90", width = 15)
    @ApiModelProperty(value = "LTV90")
    private BigDecimal ltv90=BigDecimal.ZERO;



}
