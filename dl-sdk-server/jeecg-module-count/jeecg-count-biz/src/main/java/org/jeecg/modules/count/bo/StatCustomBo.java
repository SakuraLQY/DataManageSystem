package org.jeecg.modules.count.bo;

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
public class StatCustomBo implements Serializable {
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
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer countUser=0;
    /**有效注册数*/
    @Excel(name = "有效注册数", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser=0;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayuser=0;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstMoney=BigDecimal.ZERO;
    /**新增付费率*/
    @Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private BigDecimal addCostRate=BigDecimal.ZERO;
    /**活跃人数*/
    @Excel(name = "活跃人数", width = 15)
    @ApiModelProperty(value = "活跃人数")
    private Integer countDau=0;
    /**活跃付费人数*/
    @Excel(name = "活跃付费人数", width = 15)
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayuser=0;
    /**付费总额*/
    @Excel(name = "付费总额", width = 15)
    @ApiModelProperty(value = "付费总额")
    private BigDecimal aliveMoney=BigDecimal.ZERO;
    /**活跃付费率*/
    @Excel(name = "活跃付费率", width = 15)
    @ApiModelProperty(value = "活跃付费率")
    private BigDecimal aliveMoneyRate=BigDecimal.ZERO;
    /**首日ARPU*/
    @Excel(name = "首日ARPU", width = 15)
    @ApiModelProperty(value = "首日ARPU")
    private BigDecimal firstArpu=BigDecimal.ZERO;
    /**总ARPU*/
    @Excel(name = "总ARPU", width = 15)
    @ApiModelProperty(value = "总ARPU")
    private BigDecimal allArpu=BigDecimal.ZERO;
    /**注册设备数*/
    @Excel(name = "注册设备数", width = 15)
    @ApiModelProperty(value = "注册设备数")
    private Integer countUserDev=0;
    /**次留*/
    @Excel(name = "次留", width = 15)
    @ApiModelProperty(value = "次留")
    private Integer loyal2=0;
    /**3日留存*/
    @Excel(name = "3日留存", width = 15)
    @ApiModelProperty(value = "3日留存")
    private Integer loyal3=0;
    /**7日留存*/
    @Excel(name = "7日留存", width = 15)
    @ApiModelProperty(value = "7日留存")
    private Integer loyal7=0;
    /**15日留存*/
    @Excel(name = "15日留存", width = 15)
    @ApiModelProperty(value = "15日留存")
    private Integer loyal15=0;
    /**30日留存*/
    @Excel(name = "30日留存", width = 15)
    @ApiModelProperty(value = "30日留存")
    private Integer loyal30=0;
    /**回本-1天*/
    @Excel(name = "回本-1天", width = 15)
    @ApiModelProperty(value = "回本-1天")
    private java.math.BigDecimal payback1=BigDecimal.ZERO;
    /**回本-2天*/
    @Excel(name = "回本-2天", width = 15)
    @ApiModelProperty(value = "回本-2天")
    private java.math.BigDecimal payback2=BigDecimal.ZERO;
    /**回本-3天*/
    @Excel(name = "回本-3天", width = 15)
    @ApiModelProperty(value = "回本-3天")
    private java.math.BigDecimal payback3=BigDecimal.ZERO;
    /**回本-4天*/
    @Excel(name = "回本-4天", width = 15)
    @ApiModelProperty(value = "回本-4天")
    private java.math.BigDecimal payback4=BigDecimal.ZERO;
    /**回本-5天*/
    @Excel(name = "回本-5天", width = 15)
    @ApiModelProperty(value = "回本-5天")
    private java.math.BigDecimal payback5=BigDecimal.ZERO;
    /**回本-6天*/
    @Excel(name = "回本-6天", width = 15)
    @ApiModelProperty(value = "回本-6天")
    private java.math.BigDecimal payback6=BigDecimal.ZERO;
    /**回本-7天*/
    @Excel(name = "回本-7天", width = 15)
    @ApiModelProperty(value = "回本-7天")
    private java.math.BigDecimal payback7= BigDecimal.ZERO;
    /**回本-8天*/
    @Excel(name = "回本-8天", width = 15)
    @ApiModelProperty(value = "回本-8天")
    private java.math.BigDecimal payback8=BigDecimal.ZERO;
    /**回本-9天*/
    @Excel(name = "回本-9天", width = 15)
    @ApiModelProperty(value = "回本-9天")
    private java.math.BigDecimal payback9=BigDecimal.ZERO;
    /**回本-10天*/
    @Excel(name = "回本-10天", width = 15)
    @ApiModelProperty(value = "回本-10天")
    private java.math.BigDecimal payback10=BigDecimal.ZERO;
    /**回本-11天*/
    @Excel(name = "回本-11天", width = 15)
    @ApiModelProperty(value = "回本-11天")
    private java.math.BigDecimal payback11=BigDecimal.ZERO;
    /**回本-12天*/
    @Excel(name = "回本-12天", width = 15)
    @ApiModelProperty(value = "回本-12天")
    private java.math.BigDecimal payback12=BigDecimal.ZERO;
    /**回本-13天*/
    @Excel(name = "回本-13天", width = 15)
    @ApiModelProperty(value = "回本-13天")
    private java.math.BigDecimal payback13=BigDecimal.ZERO;
    /**回本-14天*/
    @Excel(name = "回本-14天", width = 15)
    @ApiModelProperty(value = "回本-14天")
    private java.math.BigDecimal payback14=BigDecimal.ZERO;
    /**回本-15天*/
    @Excel(name = "回本-15天", width = 15)
    @ApiModelProperty(value = "回本-15天")
    private java.math.BigDecimal payback15=BigDecimal.ZERO;
    /**回本-16天*/
    @Excel(name = "回本-16天", width = 15)
    @ApiModelProperty(value = "回本-16天")
    private java.math.BigDecimal payback16=BigDecimal.ZERO;
    /**回本-17天*/
    @Excel(name = "回本-17天", width = 15)
    @ApiModelProperty(value = "回本-17天")
    private java.math.BigDecimal payback17=BigDecimal.ZERO;
    /**回本-18天*/
    @Excel(name = "回本-18天", width = 15)
    @ApiModelProperty(value = "回本-18天")
    private java.math.BigDecimal payback18=BigDecimal.ZERO;
    /**回本-19天*/
    @Excel(name = "回本-19天", width = 15)
    @ApiModelProperty(value = "回本-19天")
    private java.math.BigDecimal payback19=BigDecimal.ZERO;
    /**回本-20天*/
    @Excel(name = "回本-20天", width = 15)
    @ApiModelProperty(value = "回本-20天")
    private java.math.BigDecimal payback20=BigDecimal.ZERO;
    /**回本-21天*/
    @Excel(name = "回本-21天", width = 15)
    @ApiModelProperty(value = "回本-21天")
    private java.math.BigDecimal payback21=BigDecimal.ZERO;
    /**回本-22天*/
    @Excel(name = "回本-22天", width = 15)
    @ApiModelProperty(value = "回本-22天")
    private java.math.BigDecimal payback22=BigDecimal.ZERO;
    /**回本-23天*/
    @Excel(name = "回本-23天", width = 15)
    @ApiModelProperty(value = "回本-23天")
    private java.math.BigDecimal payback23=BigDecimal.ZERO;
    /**回本-24天*/
    @Excel(name = "回本-24天", width = 15)
    @ApiModelProperty(value = "回本-24天")
    private java.math.BigDecimal payback24=BigDecimal.ZERO;
    /**回本-25天*/
    @Excel(name = "回本-25天", width = 15)
    @ApiModelProperty(value = "回本-25天")
    private java.math.BigDecimal payback25=BigDecimal.ZERO;
    /**回本-26天*/
    @Excel(name = "回本-26天", width = 15)
    @ApiModelProperty(value = "回本-26天")
    private java.math.BigDecimal payback26=BigDecimal.ZERO;
    /**回本-27天*/
    @Excel(name = "回本-27天", width = 15)
    @ApiModelProperty(value = "回本-27天")
    private java.math.BigDecimal payback27=BigDecimal.ZERO;
    /**回本-28天*/
    @Excel(name = "回本-28天", width = 15)
    @ApiModelProperty(value = "回本-28天")
    private java.math.BigDecimal payback28=BigDecimal.ZERO;
    /**回本-29天*/
    @Excel(name = "回本-29天", width = 15)
    @ApiModelProperty(value = "回本-29天")
    private java.math.BigDecimal payback29=BigDecimal.ZERO;
    /**回本-30天*/
    @Excel(name = "回本-30天", width = 15)
    @ApiModelProperty(value = "回本-30天")
    private java.math.BigDecimal payback30=BigDecimal.ZERO;
    /**回本-31天*/
    @Excel(name = "回本-31天", width = 15)
    @ApiModelProperty(value = "回本-31天")
    private java.math.BigDecimal payback31=BigDecimal.ZERO;
    /**回本-32天*/
    @Excel(name = "回本-32天", width = 15)
    @ApiModelProperty(value = "回本-32天")
    private java.math.BigDecimal payback32=BigDecimal.ZERO;
    /**回本-33天*/
    @Excel(name = "回本-33天", width = 15)
    @ApiModelProperty(value = "回本-33天")
    private java.math.BigDecimal payback33=BigDecimal.ZERO;
    /**回本-34天*/
    @Excel(name = "回本-34天", width = 15)
    @ApiModelProperty(value = "回本-34天")
    private java.math.BigDecimal payback34=BigDecimal.ZERO;
    /**回本-35天*/
    @Excel(name = "回本-35天", width = 15)
    @ApiModelProperty(value = "回本-35天")
    private java.math.BigDecimal payback35=BigDecimal.ZERO;
    /**回本-36天*/
    @Excel(name = "回本-36天", width = 15)
    @ApiModelProperty(value = "回本-36天")
    private java.math.BigDecimal payback36=BigDecimal.ZERO;
    /**回本-37天*/
    @Excel(name = "回本-37天", width = 15)
    @ApiModelProperty(value = "回本-37天")
    private java.math.BigDecimal payback37=BigDecimal.ZERO;
    /**回本-38天*/
    @Excel(name = "回本-38天", width = 15)
    @ApiModelProperty(value = "回本-38天")
    private java.math.BigDecimal payback38=BigDecimal.ZERO;
    /**回本-39天*/
    @Excel(name = "回本-39天", width = 15)
    @ApiModelProperty(value = "回本-39天")
    private java.math.BigDecimal payback39=BigDecimal.ZERO;
    /**回本-40天*/
    @Excel(name = "回本-40天", width = 15)
    @ApiModelProperty(value = "回本-40天")
    private java.math.BigDecimal payback40=BigDecimal.ZERO;
    /**回本-41天*/
    @Excel(name = "回本-41天", width = 15)
    @ApiModelProperty(value = "回本-41天")
    private java.math.BigDecimal payback41=BigDecimal.ZERO;
    /**回本-42天*/
    @Excel(name = "回本-42天", width = 15)
    @ApiModelProperty(value = "回本-42天")
    private java.math.BigDecimal payback42=BigDecimal.ZERO;
    /**回本-43天*/
    @Excel(name = "回本-43天", width = 15)
    @ApiModelProperty(value = "回本-43天")
    private java.math.BigDecimal payback43=BigDecimal.ZERO;
    /**回本-44天*/
    @Excel(name = "回本-44天", width = 15)
    @ApiModelProperty(value = "回本-44天")
    private java.math.BigDecimal payback44=BigDecimal.ZERO;
    /**回本-45天*/
    @Excel(name = "回本-45天", width = 15)
    @ApiModelProperty(value = "回本-45天")
    private java.math.BigDecimal payback45=BigDecimal.ZERO;
    /**回本-46天*/
    @Excel(name = "回本-46天", width = 15)
    @ApiModelProperty(value = "回本-46天")
    private java.math.BigDecimal payback46=BigDecimal.ZERO;
    /**回本-47天*/
    @Excel(name = "回本-47天", width = 15)
    @ApiModelProperty(value = "回本-47天")
    private java.math.BigDecimal payback47=BigDecimal.ZERO;
    /**回本-48天*/
    @Excel(name = "回本-48天", width = 15)
    @ApiModelProperty(value = "回本-48天")
    private java.math.BigDecimal payback48=BigDecimal.ZERO;
    /**回本-49天*/
    @Excel(name = "回本-49天", width = 15)
    @ApiModelProperty(value = "回本-49天")
    private java.math.BigDecimal payback49=BigDecimal.ZERO;
    /**回本-50天*/
    @Excel(name = "回本-50天", width = 15)
    @ApiModelProperty(value = "回本-50天")
    private java.math.BigDecimal payback50=BigDecimal.ZERO;
    /**回本-51天*/
    @Excel(name = "回本-51天", width = 15)
    @ApiModelProperty(value = "回本-51天")
    private java.math.BigDecimal payback51=BigDecimal.ZERO;
    /**回本-52天*/
    @Excel(name = "回本-52天", width = 15)
    @ApiModelProperty(value = "回本-52天")
    private java.math.BigDecimal payback52=BigDecimal.ZERO;
    /**回本-53天*/
    @Excel(name = "回本-53天", width = 15)
    @ApiModelProperty(value = "回本-53天")
    private java.math.BigDecimal payback53=BigDecimal.ZERO;
    /**回本-54天*/
    @Excel(name = "回本-54天", width = 15)
    @ApiModelProperty(value = "回本-54天")
    private java.math.BigDecimal payback54=BigDecimal.ZERO;
    /**回本-55天*/
    @Excel(name = "回本-55天", width = 15)
    @ApiModelProperty(value = "回本-55天")
    private java.math.BigDecimal payback55=BigDecimal.ZERO;
    /**回本-56天*/
    @Excel(name = "回本-56天", width = 15)
    @ApiModelProperty(value = "回本-56天")
    private java.math.BigDecimal payback56=BigDecimal.ZERO;
    /**回本-57天*/
    @Excel(name = "回本-57天", width = 15)
    @ApiModelProperty(value = "回本-57天")
    private java.math.BigDecimal payback57=BigDecimal.ZERO;
    /**回本-58天*/
    @Excel(name = "回本-58天", width = 15)
    @ApiModelProperty(value = "回本-58天")
    private java.math.BigDecimal payback58=BigDecimal.ZERO;
    /**回本-59天*/
    @Excel(name = "回本-59天", width = 15)
    @ApiModelProperty(value = "回本-59天")
    private java.math.BigDecimal payback59=BigDecimal.ZERO;
    /**回本-60天*/
    @Excel(name = "回本-60天", width = 15)
    @ApiModelProperty(value = "回本-60天")
    private java.math.BigDecimal payback60=BigDecimal.ZERO;
    /**回本-61天*/
    @Excel(name = "回本-61天", width = 15)
    @ApiModelProperty(value = "回本-61天")
    private java.math.BigDecimal payback61=BigDecimal.ZERO;
    /**回本-62天*/
    @Excel(name = "回本-62天", width = 15)
    @ApiModelProperty(value = "回本-62天")
    private java.math.BigDecimal payback62=BigDecimal.ZERO;
    /**回本-63天*/
    @Excel(name = "回本-63天", width = 15)
    @ApiModelProperty(value = "回本-63天")
    private java.math.BigDecimal payback63=BigDecimal.ZERO;
    /**回本-64天*/
    @Excel(name = "回本-64天", width = 15)
    @ApiModelProperty(value = "回本-64天")
    private java.math.BigDecimal payback64=BigDecimal.ZERO;
    /**回本-65天*/
    @Excel(name = "回本-65天", width = 15)
    @ApiModelProperty(value = "回本-65天")
    private java.math.BigDecimal payback65=BigDecimal.ZERO;
    /**回本-66天*/
    @Excel(name = "回本-66天", width = 15)
    @ApiModelProperty(value = "回本-66天")
    private java.math.BigDecimal payback66=BigDecimal.ZERO;
    /**回本-67天*/
    @Excel(name = "回本-67天", width = 15)
    @ApiModelProperty(value = "回本-67天")
    private java.math.BigDecimal payback67=BigDecimal.ZERO;
    /**回本-68天*/
    @Excel(name = "回本-68天", width = 15)
    @ApiModelProperty(value = "回本-68天")
    private java.math.BigDecimal payback68=BigDecimal.ZERO;
    /**回本-69天*/
    @Excel(name = "回本-69天", width = 15)
    @ApiModelProperty(value = "回本-69天")
    private java.math.BigDecimal payback69=BigDecimal.ZERO;
    /**回本-70天*/
    @Excel(name = "回本-70天", width = 15)
    @ApiModelProperty(value = "回本-70天")
    private java.math.BigDecimal payback70=BigDecimal.ZERO;
    /**回本-71天*/
    @Excel(name = "回本-71天", width = 15)
    @ApiModelProperty(value = "回本-71天")
    private java.math.BigDecimal payback71=BigDecimal.ZERO;
    /**回本-72天*/
    @Excel(name = "回本-72天", width = 15)
    @ApiModelProperty(value = "回本-72天")
    private java.math.BigDecimal payback72=BigDecimal.ZERO;
    /**回本-73天*/
    @Excel(name = "回本-73天", width = 15)
    @ApiModelProperty(value = "回本-73天")
    private java.math.BigDecimal payback73=BigDecimal.ZERO;
    /**回本-74天*/
    @Excel(name = "回本-74天", width = 15)
    @ApiModelProperty(value = "回本-74天")
    private java.math.BigDecimal payback74=BigDecimal.ZERO;
    /**回本-75天*/
    @Excel(name = "回本-75天", width = 15)
    @ApiModelProperty(value = "回本-75天")
    private java.math.BigDecimal payback75=BigDecimal.ZERO;
    /**回本-76天*/
    @Excel(name = "回本-76天", width = 15)
    @ApiModelProperty(value = "回本-76天")
    private java.math.BigDecimal payback76=BigDecimal.ZERO;
    /**回本-77天*/
    @Excel(name = "回本-77天", width = 15)
    @ApiModelProperty(value = "回本-77天")
    private java.math.BigDecimal payback77=BigDecimal.ZERO;
    /**回本-78天*/
    @Excel(name = "回本-78天", width = 15)
    @ApiModelProperty(value = "回本-78天")
    private java.math.BigDecimal payback78=BigDecimal.ZERO;
    /**回本-79天*/
    @Excel(name = "回本-79天", width = 15)
    @ApiModelProperty(value = "回本-79天")
    private java.math.BigDecimal payback79=BigDecimal.ZERO;
    /**回本-80天*/
    @Excel(name = "回本-80天", width = 15)
    @ApiModelProperty(value = "回本-80天")
    private java.math.BigDecimal payback80=BigDecimal.ZERO;
    /**回本-81天*/
    @Excel(name = "回本-81天", width = 15)
    @ApiModelProperty(value = "回本-81天")
    private java.math.BigDecimal payback81=BigDecimal.ZERO;
    /**回本-82天*/
    @Excel(name = "回本-82天", width = 15)
    @ApiModelProperty(value = "回本-82天")
    private java.math.BigDecimal payback82=BigDecimal.ZERO;
    /**回本-83天*/
    @Excel(name = "回本-83天", width = 15)
    @ApiModelProperty(value = "回本-83天")
    private java.math.BigDecimal payback83=BigDecimal.ZERO;
    /**回本-84天*/
    @Excel(name = "回本-84天", width = 15)
    @ApiModelProperty(value = "回本-84天")
    private java.math.BigDecimal payback84=BigDecimal.ZERO;
    /**回本-85天*/
    @Excel(name = "回本-85天", width = 15)
    @ApiModelProperty(value = "回本-85天")
    private java.math.BigDecimal payback85=BigDecimal.ZERO;
    /**回本-86天*/
    @Excel(name = "回本-86天", width = 15)
    @ApiModelProperty(value = "回本-86天")
    private java.math.BigDecimal payback86=BigDecimal.ZERO;
    /**回本-87天*/
    @Excel(name = "回本-87天", width = 15)
    @ApiModelProperty(value = "回本-87天")
    private java.math.BigDecimal payback87=BigDecimal.ZERO;
    /**回本-88天*/
    @Excel(name = "回本-88天", width = 15)
    @ApiModelProperty(value = "回本-88天")
    private java.math.BigDecimal payback88=BigDecimal.ZERO;
    /**回本-89天*/
    @Excel(name = "回本-89天", width = 15)
    @ApiModelProperty(value = "回本-89天")
    private java.math.BigDecimal payback89=BigDecimal.ZERO;
    /**回本-90天*/
    @Excel(name = "回本-90天", width = 15)
    @ApiModelProperty(value = "回本-90天")
    private java.math.BigDecimal payback90=BigDecimal.ZERO;

}
