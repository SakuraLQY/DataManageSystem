package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 接受单价1-150天的留存单价
 * @author: chenglin
 * @date: 2023年05月18日 15:24
 */
@Data
public class RetainPriceBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**留存单价-2天*/
    @Excel(name = "留存单价-2天", width = 15)
    @ApiModelProperty(value = "次日留存单价")
    private BigDecimal price2=BigDecimal.ZERO;
    /**留存单价-3天*/
    @Excel(name = "留存单价-3天", width = 15)
    @ApiModelProperty(value = "留存单价-3天")
    private BigDecimal price3=BigDecimal.ZERO;
    /**留存单价-4天*/
    @Excel(name = "留存单价-4天", width = 15)
    @ApiModelProperty(value = "留存单价-4天")
    private BigDecimal price4=BigDecimal.ZERO;
    /**留存单价-5天*/
    @Excel(name = "留存单价-5天", width = 15)
    @ApiModelProperty(value = "留存单价-5天")
    private BigDecimal price5=BigDecimal.ZERO;
    /**留存单价-6天*/
    @Excel(name = "留存单价-6天", width = 15)
    @ApiModelProperty(value = "留存单价-6天")
    private BigDecimal price6=BigDecimal.ZERO;
    /**留存单价-7天*/
    @Excel(name = "留存单价-7天", width = 15)
    @ApiModelProperty(value = "留存单价-7天")
    private BigDecimal price7= BigDecimal.ZERO;
    /**留存单价-8天*/
    @Excel(name = "留存单价-8天", width = 15)
    @ApiModelProperty(value = "留存单价-8天")
    private BigDecimal price8=BigDecimal.ZERO;
    /**留存单价-9天*/
    @Excel(name = "留存单价-9天", width = 15)
    @ApiModelProperty(value = "留存单价-9天")
    private BigDecimal price9=BigDecimal.ZERO;
    /**留存单价-10天*/
    @Excel(name = "留存单价-10天", width = 15)
    @ApiModelProperty(value = "留存单价-10天")
    private BigDecimal price10=BigDecimal.ZERO;
    /**留存单价-11天*/
    @Excel(name = "留存单价-11天", width = 15)
    @ApiModelProperty(value = "留存单价-11天")
    private BigDecimal price11=BigDecimal.ZERO;
    /**留存单价-12天*/
    @Excel(name = "留存单价-12天", width = 15)
    @ApiModelProperty(value = "留存单价-12天")
    private BigDecimal price12=BigDecimal.ZERO;
    /**留存单价-13天*/
    @Excel(name = "留存单价-13天", width = 15)
    @ApiModelProperty(value = "留存单价-13天")
    private BigDecimal price13=BigDecimal.ZERO;
    /**留存单价-14天*/
    @Excel(name = "留存单价-14天", width = 15)
    @ApiModelProperty(value = "留存单价-14天")
    private BigDecimal price14=BigDecimal.ZERO;
    /**留存单价-15天*/
    @Excel(name = "留存单价-15天", width = 15)
    @ApiModelProperty(value = "留存单价-15天")
    private BigDecimal price15=BigDecimal.ZERO;
    /**留存单价-16天*/
    @Excel(name = "留存单价-16天", width = 15)
    @ApiModelProperty(value = "留存单价-16天")
    private BigDecimal price16=BigDecimal.ZERO;
    /**留存单价-17天*/
    @Excel(name = "留存单价-17天", width = 15)
    @ApiModelProperty(value = "留存单价-17天")
    private BigDecimal price17=BigDecimal.ZERO;
    /**留存单价-18天*/
    @Excel(name = "留存单价-18天", width = 15)
    @ApiModelProperty(value = "留存单价-18天")
    private BigDecimal price18=BigDecimal.ZERO;
    /**留存单价-19天*/
    @Excel(name = "留存单价-19天", width = 15)
    @ApiModelProperty(value = "留存单价-19天")
    private BigDecimal price19=BigDecimal.ZERO;
    /**留存单价-20天*/
    @Excel(name = "留存单价-20天", width = 15)
    @ApiModelProperty(value = "留存单价-20天")
    private BigDecimal price20=BigDecimal.ZERO;
    /**留存单价-21天*/
    @Excel(name = "留存单价-21天", width = 15)
    @ApiModelProperty(value = "留存单价-21天")
    private BigDecimal price21=BigDecimal.ZERO;
    /**留存单价-22天*/
    @Excel(name = "留存单价-22天", width = 15)
    @ApiModelProperty(value = "留存单价-22天")
    private BigDecimal price22=BigDecimal.ZERO;
    /**留存单价-23天*/
    @Excel(name = "留存单价-23天", width = 15)
    @ApiModelProperty(value = "留存单价-23天")
    private BigDecimal price23=BigDecimal.ZERO;
    /**留存单价-24天*/
    @Excel(name = "留存单价-24天", width = 15)
    @ApiModelProperty(value = "留存单价-24天")
    private BigDecimal price24=BigDecimal.ZERO;
    /**留存单价-25天*/
    @Excel(name = "留存单价-25天", width = 15)
    @ApiModelProperty(value = "留存单价-25天")
    private BigDecimal price25=BigDecimal.ZERO;
    /**留存单价-26天*/
    @Excel(name = "留存单价-26天", width = 15)
    @ApiModelProperty(value = "留存单价-26天")
    private BigDecimal price26=BigDecimal.ZERO;
    /**留存单价-27天*/
    @Excel(name = "留存单价-27天", width = 15)
    @ApiModelProperty(value = "留存单价-27天")
    private BigDecimal price27=BigDecimal.ZERO;
    /**留存单价-28天*/
    @Excel(name = "留存单价-28天", width = 15)
    @ApiModelProperty(value = "留存单价-28天")
    private BigDecimal price28=BigDecimal.ZERO;
    /**留存单价-29天*/
    @Excel(name = "留存单价-29天", width = 15)
    @ApiModelProperty(value = "留存单价-29天")
    private BigDecimal price29=BigDecimal.ZERO;
    /**留存单价-30天*/
    @Excel(name = "留存单价-30天", width = 15)
    @ApiModelProperty(value = "留存单价-30天")
    private BigDecimal price30=BigDecimal.ZERO;
    /**留存单价-31天*/
    @Excel(name = "留存单价-31天", width = 15)
    @ApiModelProperty(value = "留存单价-31天")
    private BigDecimal price31=BigDecimal.ZERO;
    /**留存单价-32天*/
    @Excel(name = "留存单价-32天", width = 15)
    @ApiModelProperty(value = "留存单价-32天")
    private BigDecimal price32=BigDecimal.ZERO;
    /**留存单价-33天*/
    @Excel(name = "留存单价-33天", width = 15)
    @ApiModelProperty(value = "留存单价-33天")
    private BigDecimal price33=BigDecimal.ZERO;
    /**留存单价-34天*/
    @Excel(name = "留存单价-34天", width = 15)
    @ApiModelProperty(value = "留存单价-34天")
    private BigDecimal price34=BigDecimal.ZERO;
    /**留存单价-35天*/
    @Excel(name = "留存单价-35天", width = 15)
    @ApiModelProperty(value = "留存单价-35天")
    private BigDecimal price35=BigDecimal.ZERO;
    /**留存单价-36天*/
    @Excel(name = "留存单价-36天", width = 15)
    @ApiModelProperty(value = "留存单价-36天")
    private BigDecimal price36=BigDecimal.ZERO;
    /**留存单价-37天*/
    @Excel(name = "留存单价-37天", width = 15)
    @ApiModelProperty(value = "留存单价-37天")
    private BigDecimal price37=BigDecimal.ZERO;
    /**留存单价-38天*/
    @Excel(name = "留存单价-38天", width = 15)
    @ApiModelProperty(value = "留存单价-38天")
    private BigDecimal price38=BigDecimal.ZERO;
    /**留存单价-39天*/
    @Excel(name = "留存单价-39天", width = 15)
    @ApiModelProperty(value = "留存单价-39天")
    private BigDecimal price39=BigDecimal.ZERO;
    /**留存单价-40天*/
    @Excel(name = "留存单价-40天", width = 15)
    @ApiModelProperty(value = "留存单价-40天")
    private BigDecimal price40=BigDecimal.ZERO;
    /**留存单价-41天*/
    @Excel(name = "留存单价-41天", width = 15)
    @ApiModelProperty(value = "留存单价-41天")
    private BigDecimal price41=BigDecimal.ZERO;
    /**留存单价-42天*/
    @Excel(name = "留存单价-42天", width = 15)
    @ApiModelProperty(value = "留存单价-42天")
    private BigDecimal price42=BigDecimal.ZERO;
    /**留存单价-43天*/
    @Excel(name = "留存单价-43天", width = 15)
    @ApiModelProperty(value = "留存单价-43天")
    private BigDecimal price43=BigDecimal.ZERO;
    /**留存单价-44天*/
    @Excel(name = "留存单价-44天", width = 15)
    @ApiModelProperty(value = "留存单价-44天")
    private BigDecimal price44=BigDecimal.ZERO;
    /**留存单价-45天*/
    @Excel(name = "留存单价-45天", width = 15)
    @ApiModelProperty(value = "留存单价-45天")
    private BigDecimal price45=BigDecimal.ZERO;
    /**留存单价-46天*/
    @Excel(name = "留存单价-46天", width = 15)
    @ApiModelProperty(value = "留存单价-46天")
    private BigDecimal price46=BigDecimal.ZERO;
    /**留存单价-47天*/
    @Excel(name = "留存单价-47天", width = 15)
    @ApiModelProperty(value = "留存单价-47天")
    private BigDecimal price47=BigDecimal.ZERO;
    /**留存单价-48天*/
    @Excel(name = "留存单价-48天", width = 15)
    @ApiModelProperty(value = "留存单价-48天")
    private BigDecimal price48=BigDecimal.ZERO;
    /**留存单价-49天*/
    @Excel(name = "留存单价-49天", width = 15)
    @ApiModelProperty(value = "留存单价-49天")
    private BigDecimal price49=BigDecimal.ZERO;
    /**留存单价-50天*/
    @Excel(name = "留存单价-50天", width = 15)
    @ApiModelProperty(value = "留存单价-50天")
    private BigDecimal price50=BigDecimal.ZERO;
    /**留存单价-51天*/
    @Excel(name = "留存单价-51天", width = 15)
    @ApiModelProperty(value = "留存单价-51天")
    private BigDecimal price51=BigDecimal.ZERO;
    /**留存单价-52天*/
    @Excel(name = "留存单价-52天", width = 15)
    @ApiModelProperty(value = "留存单价-52天")
    private BigDecimal price52=BigDecimal.ZERO;
    /**留存单价-53天*/
    @Excel(name = "留存单价-53天", width = 15)
    @ApiModelProperty(value = "留存单价-53天")
    private BigDecimal price53=BigDecimal.ZERO;
    /**留存单价-54天*/
    @Excel(name = "留存单价-54天", width = 15)
    @ApiModelProperty(value = "留存单价-54天")
    private BigDecimal price54=BigDecimal.ZERO;
    /**留存单价-55天*/
    @Excel(name = "留存单价-55天", width = 15)
    @ApiModelProperty(value = "留存单价-55天")
    private BigDecimal price55=BigDecimal.ZERO;
    /**留存单价-56天*/
    @Excel(name = "留存单价-56天", width = 15)
    @ApiModelProperty(value = "留存单价-56天")
    private BigDecimal price56=BigDecimal.ZERO;
    /**留存单价-57天*/
    @Excel(name = "留存单价-57天", width = 15)
    @ApiModelProperty(value = "留存单价-57天")
    private BigDecimal price57=BigDecimal.ZERO;
    /**留存单价-58天*/
    @Excel(name = "留存单价-58天", width = 15)
    @ApiModelProperty(value = "留存单价-58天")
    private BigDecimal price58=BigDecimal.ZERO;
    /**留存单价-59天*/
    @Excel(name = "留存单价-59天", width = 15)
    @ApiModelProperty(value = "留存单价-59天")
    private BigDecimal price59=BigDecimal.ZERO;
    /**留存单价-60天*/
    @Excel(name = "留存单价-60天", width = 15)
    @ApiModelProperty(value = "留存单价-60天")
    private BigDecimal price60=BigDecimal.ZERO;
    /**留存单价-61天*/
    @Excel(name = "留存单价-61天", width = 15)
    @ApiModelProperty(value = "留存单价-61天")
    private BigDecimal price61=BigDecimal.ZERO;
    /**留存单价-62天*/
    @Excel(name = "留存单价-62天", width = 15)
    @ApiModelProperty(value = "留存单价-62天")
    private BigDecimal price62=BigDecimal.ZERO;
    /**留存单价-63天*/
    @Excel(name = "留存单价-63天", width = 15)
    @ApiModelProperty(value = "留存单价-63天")
    private BigDecimal price63=BigDecimal.ZERO;
    /**留存单价-64天*/
    @Excel(name = "留存单价-64天", width = 15)
    @ApiModelProperty(value = "留存单价-64天")
    private BigDecimal price64=BigDecimal.ZERO;
    /**留存单价-65天*/
    @Excel(name = "留存单价-65天", width = 15)
    @ApiModelProperty(value = "留存单价-65天")
    private BigDecimal price65=BigDecimal.ZERO;
    /**留存单价-66天*/
    @Excel(name = "留存单价-66天", width = 15)
    @ApiModelProperty(value = "留存单价-66天")
    private BigDecimal price66=BigDecimal.ZERO;
    /**留存单价-67天*/
    @Excel(name = "留存单价-67天", width = 15)
    @ApiModelProperty(value = "留存单价-67天")
    private BigDecimal price67=BigDecimal.ZERO;
    /**留存单价-68天*/
    @Excel(name = "留存单价-68天", width = 15)
    @ApiModelProperty(value = "留存单价-68天")
    private BigDecimal price68=BigDecimal.ZERO;
    /**留存单价-69天*/
    @Excel(name = "留存单价-69天", width = 15)
    @ApiModelProperty(value = "留存单价-69天")
    private BigDecimal price69=BigDecimal.ZERO;
    /**留存单价-70天*/
    @Excel(name = "留存单价-70天", width = 15)
    @ApiModelProperty(value = "留存单价-70天")
    private BigDecimal price70=BigDecimal.ZERO;
    /**留存单价-71天*/
    @Excel(name = "留存单价-71天", width = 15)
    @ApiModelProperty(value = "留存单价-71天")
    private BigDecimal price71=BigDecimal.ZERO;
    /**留存单价-72天*/
    @Excel(name = "留存单价-72天", width = 15)
    @ApiModelProperty(value = "留存单价-72天")
    private BigDecimal price72=BigDecimal.ZERO;
    /**留存单价-73天*/
    @Excel(name = "留存单价-73天", width = 15)
    @ApiModelProperty(value = "留存单价-73天")
    private BigDecimal price73=BigDecimal.ZERO;
    /**留存单价-74天*/
    @Excel(name = "留存单价-74天", width = 15)
    @ApiModelProperty(value = "留存单价-74天")
    private BigDecimal price74=BigDecimal.ZERO;
    /**留存单价-75天*/
    @Excel(name = "留存单价-75天", width = 15)
    @ApiModelProperty(value = "留存单价-75天")
    private BigDecimal price75=BigDecimal.ZERO;
    /**留存单价-76天*/
    @Excel(name = "留存单价-76天", width = 15)
    @ApiModelProperty(value = "留存单价-76天")
    private BigDecimal price76=BigDecimal.ZERO;
    /**留存单价-77天*/
    @Excel(name = "留存单价-77天", width = 15)
    @ApiModelProperty(value = "留存单价-77天")
    private BigDecimal price77=BigDecimal.ZERO;
    /**留存单价-78天*/
    @Excel(name = "留存单价-78天", width = 15)
    @ApiModelProperty(value = "留存单价-78天")
    private BigDecimal price78=BigDecimal.ZERO;
    /**留存单价-79天*/
    @Excel(name = "留存单价-79天", width = 15)
    @ApiModelProperty(value = "留存单价-79天")
    private BigDecimal price79=BigDecimal.ZERO;
    /**留存单价-80天*/
    @Excel(name = "留存单价-80天", width = 15)
    @ApiModelProperty(value = "留存单价-80天")
    private BigDecimal price80=BigDecimal.ZERO;
    /**留存单价-81天*/
    @Excel(name = "留存单价-81天", width = 15)
    @ApiModelProperty(value = "留存单价-81天")
    private BigDecimal price81=BigDecimal.ZERO;
    /**留存单价-82天*/
    @Excel(name = "留存单价-82天", width = 15)
    @ApiModelProperty(value = "留存单价-82天")
    private BigDecimal price82=BigDecimal.ZERO;
    /**留存单价-83天*/
    @Excel(name = "留存单价-83天", width = 15)
    @ApiModelProperty(value = "留存单价-83天")
    private BigDecimal price83=BigDecimal.ZERO;
    /**留存单价-84天*/
    @Excel(name = "留存单价-84天", width = 15)
    @ApiModelProperty(value = "留存单价-84天")
    private BigDecimal price84=BigDecimal.ZERO;
    /**留存单价-85天*/
    @Excel(name = "留存单价-85天", width = 15)
    @ApiModelProperty(value = "留存单价-85天")
    private BigDecimal price85=BigDecimal.ZERO;
    /**留存单价-86天*/
    @Excel(name = "留存单价-86天", width = 15)
    @ApiModelProperty(value = "留存单价-86天")
    private BigDecimal price86=BigDecimal.ZERO;
    /**留存单价-87天*/
    @Excel(name = "留存单价-87天", width = 15)
    @ApiModelProperty(value = "留存单价-87天")
    private BigDecimal price87=BigDecimal.ZERO;
    /**留存单价-88天*/
    @Excel(name = "留存单价-88天", width = 15)
    @ApiModelProperty(value = "留存单价-88天")
    private BigDecimal price88=BigDecimal.ZERO;
    /**留存单价-89天*/
    @Excel(name = "留存单价-89天", width = 15)
    @ApiModelProperty(value = "留存单价-89天")
    private BigDecimal price89=BigDecimal.ZERO;
    /**留存单价-90天*/
    @Excel(name = "留存单价-90天", width = 15)
    @ApiModelProperty(value = "留存单价-90天")
    private BigDecimal price90=BigDecimal.ZERO;
    /**留存单价-91天*/
    @Excel(name = "留存单价-91天", width = 15)
    @ApiModelProperty(value = "留存单价-91天")
    private BigDecimal price91=BigDecimal.ZERO;
    /**留存单价-92天*/
    @Excel(name = "留存单价-92天", width = 15)
    @ApiModelProperty(value = "留存单价-92天")
    private BigDecimal price92=BigDecimal.ZERO;
    /**留存单价-93天*/
    @Excel(name = "留存单价-93天", width = 15)
    @ApiModelProperty(value = "留存单价-93天")
    private BigDecimal price93=BigDecimal.ZERO;
    /**留存单价-94天*/
    @Excel(name = "留存单价-94天", width = 15)
    @ApiModelProperty(value = "留存单价-94天")
    private BigDecimal price94=BigDecimal.ZERO;
    /**留存单价-95天*/
    @Excel(name = "留存单价-95天", width = 15)
    @ApiModelProperty(value = "留存单价-95天")
    private BigDecimal price95=BigDecimal.ZERO;
    /**留存单价-96天*/
    @Excel(name = "留存单价-96天", width = 15)
    @ApiModelProperty(value = "留存单价-96天")
    private BigDecimal price96=BigDecimal.ZERO;
    /**留存单价-97天*/
    @Excel(name = "留存单价-97天", width = 15)
    @ApiModelProperty(value = "留存单价-97天")
    private BigDecimal price97=BigDecimal.ZERO;
    /**留存单价-98天*/
    @Excel(name = "留存单价-98天", width = 15)
    @ApiModelProperty(value = "留存单价-98天")
    private BigDecimal price98=BigDecimal.ZERO;
    /**留存单价-99天*/
    @Excel(name = "留存单价-99天", width = 15)
    @ApiModelProperty(value = "留存单价-99天")
    private BigDecimal price99=BigDecimal.ZERO;
    /**留存单价-100天*/
    @Excel(name = "留存单价-100天", width = 15)
    @ApiModelProperty(value = "留存单价-100天")
    private BigDecimal price100=BigDecimal.ZERO;
    /**留存单价-101天*/
    @Excel(name = "留存单价-101天", width = 15)
    @ApiModelProperty(value = "留存单价-101天")
    private BigDecimal price101=BigDecimal.ZERO;
    /**留存单价-102天*/
    @Excel(name = "留存单价-102天", width = 15)
    @ApiModelProperty(value = "留存单价-102天")
    private BigDecimal price102=BigDecimal.ZERO;
    /**留存单价-103天*/
    @Excel(name = "留存单价-103天", width = 15)
    @ApiModelProperty(value = "留存单价-103天")
    private BigDecimal price103=BigDecimal.ZERO;
    /**留存单价-104天*/
    @Excel(name = "留存单价-104天", width = 15)
    @ApiModelProperty(value = "留存单价-104天")
    private BigDecimal price104=BigDecimal.ZERO;
    /**留存单价-105天*/
    @Excel(name = "留存单价-105天", width = 15)
    @ApiModelProperty(value = "留存单价-105天")
    private BigDecimal price105=BigDecimal.ZERO;
    /**留存单价-106天*/
    @Excel(name = "留存单价-106天", width = 15)
    @ApiModelProperty(value = "留存单价-106天")
    private BigDecimal price106=BigDecimal.ZERO;
    /**留存单价-107天*/
    @Excel(name = "留存单价-107天", width = 15)
    @ApiModelProperty(value = "留存单价-107天")
    private BigDecimal price107=BigDecimal.ZERO;
    /**留存单价-108天*/
    @Excel(name = "留存单价-108天", width = 15)
    @ApiModelProperty(value = "留存单价-108天")
    private BigDecimal price108=BigDecimal.ZERO;
    /**留存单价-109天*/
    @Excel(name = "留存单价-109天", width = 15)
    @ApiModelProperty(value = "留存单价-109天")
    private BigDecimal price109=BigDecimal.ZERO;
    /**留存单价-110天*/
    @Excel(name = "留存单价-110天", width = 15)
    @ApiModelProperty(value = "留存单价-110天")
    private BigDecimal price110=BigDecimal.ZERO;
    /**留存单价-111天*/
    @Excel(name = "留存单价-111天", width = 15)
    @ApiModelProperty(value = "留存单价-111天")
    private BigDecimal price111=BigDecimal.ZERO;
    /**留存单价-112天*/
    @Excel(name = "留存单价-112天", width = 15)
    @ApiModelProperty(value = "留存单价-112天")
    private BigDecimal price112=BigDecimal.ZERO;
    /**留存单价-113天*/
    @Excel(name = "留存单价-113天", width = 15)
    @ApiModelProperty(value = "留存单价-113天")
    private BigDecimal price113=BigDecimal.ZERO;
    /**留存单价-114天*/
    @Excel(name = "留存单价-114天", width = 15)
    @ApiModelProperty(value = "留存单价-114天")
    private BigDecimal price114=BigDecimal.ZERO;
    /**留存单价-115天*/
    @Excel(name = "留存单价-115天", width = 15)
    @ApiModelProperty(value = "留存单价-115天")
    private BigDecimal price115=BigDecimal.ZERO;
    /**留存单价-116天*/
    @Excel(name = "留存单价-116天", width = 15)
    @ApiModelProperty(value = "留存单价-116天")
    private BigDecimal price116=BigDecimal.ZERO;
    /**留存单价-117天*/
    @Excel(name = "留存单价-117天", width = 15)
    @ApiModelProperty(value = "留存单价-117天")
    private BigDecimal price117=BigDecimal.ZERO;
    /**留存单价-118天*/
    @Excel(name = "留存单价-118天", width = 15)
    @ApiModelProperty(value = "留存单价-118天")
    private BigDecimal price118=BigDecimal.ZERO;
    /**留存单价-119天*/
    @Excel(name = "留存单价-119天", width = 15)
    @ApiModelProperty(value = "留存单价-119天")
    private BigDecimal price119=BigDecimal.ZERO;
    /**留存单价-120天*/
    @Excel(name = "留存单价-120天", width = 15)
    @ApiModelProperty(value = "留存单价-120天")
    private BigDecimal price120=BigDecimal.ZERO;
    /**留存单价-121天*/
    @Excel(name = "留存单价-121天", width = 15)
    @ApiModelProperty(value = "留存单价-121天")
    private BigDecimal price121=BigDecimal.ZERO;
    /**留存单价-122天*/
    @Excel(name = "留存单价-122天", width = 15)
    @ApiModelProperty(value = "留存单价-122天")
    private BigDecimal price122=BigDecimal.ZERO;
    /**留存单价-123天*/
    @Excel(name = "留存单价-123天", width = 15)
    @ApiModelProperty(value = "留存单价-123天")
    private BigDecimal price123=BigDecimal.ZERO;
    /**留存单价-124天*/
    @Excel(name = "留存单价-124天", width = 15)
    @ApiModelProperty(value = "留存单价-124天")
    private BigDecimal price124=BigDecimal.ZERO;
    /**留存单价-125天*/
    @Excel(name = "留存单价-125天", width = 15)
    @ApiModelProperty(value = "留存单价-125天")
    private BigDecimal price125=BigDecimal.ZERO;
    /**留存单价-126天*/
    @Excel(name = "留存单价-126天", width = 15)
    @ApiModelProperty(value = "留存单价-126天")
    private BigDecimal price126=BigDecimal.ZERO;
    /**留存单价-127天*/
    @Excel(name = "留存单价-127天", width = 15)
    @ApiModelProperty(value = "留存单价-127天")
    private BigDecimal price127=BigDecimal.ZERO;
    /**留存单价-128天*/
    @Excel(name = "留存单价-128天", width = 15)
    @ApiModelProperty(value = "留存单价-128天")
    private BigDecimal price128=BigDecimal.ZERO;
    /**留存单价-129天*/
    @Excel(name = "留存单价-129天", width = 15)
    @ApiModelProperty(value = "留存单价-129天")
    private BigDecimal price129=BigDecimal.ZERO;
    /**留存单价-130天*/
    @Excel(name = "留存单价-130天", width = 15)
    @ApiModelProperty(value = "留存单价-130天")
    private BigDecimal price130=BigDecimal.ZERO;
    /**留存单价-131天*/
    @Excel(name = "留存单价-131天", width = 15)
    @ApiModelProperty(value = "留存单价-131天")
    private BigDecimal price131=BigDecimal.ZERO;
    /**留存单价-132天*/
    @Excel(name = "留存单价-132天", width = 15)
    @ApiModelProperty(value = "留存单价-132天")
    private BigDecimal price132=BigDecimal.ZERO;
    /**留存单价-133天*/
    @Excel(name = "留存单价-133天", width = 15)
    @ApiModelProperty(value = "留存单价-133天")
    private BigDecimal price133=BigDecimal.ZERO;
    /**留存单价-134天*/
    @Excel(name = "留存单价-134天", width = 15)
    @ApiModelProperty(value = "留存单价-134天")
    private BigDecimal price134=BigDecimal.ZERO;
    /**留存单价-135天*/
    @Excel(name = "留存单价-135天", width = 15)
    @ApiModelProperty(value = "留存单价-135天")
    private BigDecimal price135=BigDecimal.ZERO;
    /**留存单价-136天*/
    @Excel(name = "留存单价-136天", width = 15)
    @ApiModelProperty(value = "留存单价-136天")
    private BigDecimal price136=BigDecimal.ZERO;
    /**留存单价-137天*/
    @Excel(name = "留存单价-137天", width = 15)
    @ApiModelProperty(value = "留存单价-137天")
    private BigDecimal price137=BigDecimal.ZERO;
    /**留存单价-138天*/
    @Excel(name = "留存单价-138天", width = 15)
    @ApiModelProperty(value = "留存单价-138天")
    private BigDecimal price138=BigDecimal.ZERO;
    /**留存单价-139天*/
    @Excel(name = "留存单价-139天", width = 15)
    @ApiModelProperty(value = "留存单价-139天")
    private BigDecimal price139=BigDecimal.ZERO;
    /**留存单价-140天*/
    @Excel(name = "留存单价-140天", width = 15)
    @ApiModelProperty(value = "留存单价-140天")
    private BigDecimal price140=BigDecimal.ZERO;
    /**留存单价-141天*/
    @Excel(name = "留存单价-141天", width = 15)
    @ApiModelProperty(value = "留存单价-141天")
    private BigDecimal price141=BigDecimal.ZERO;
    /**留存单价-142天*/
    @Excel(name = "留存单价-142天", width = 15)
    @ApiModelProperty(value = "留存单价-142天")
    private BigDecimal price142=BigDecimal.ZERO;
    /**留存单价-143天*/
    @Excel(name = "留存单价-143天", width = 15)
    @ApiModelProperty(value = "留存单价-143天")
    private BigDecimal price143=BigDecimal.ZERO;
    /**留存单价-144天*/
    @Excel(name = "留存单价-144天", width = 15)
    @ApiModelProperty(value = "留存单价-144天")
    private BigDecimal price144=BigDecimal.ZERO;
    /**留存单价-145天*/
    @Excel(name = "留存单价-145天", width = 15)
    @ApiModelProperty(value = "留存单价-145天")
    private BigDecimal price145=BigDecimal.ZERO;
    /**留存单价-146天*/
    @Excel(name = "留存单价-146天", width = 15)
    @ApiModelProperty(value = "留存单价-146天")
    private BigDecimal price146=BigDecimal.ZERO;
    /**留存单价-147天*/
    @Excel(name = "留存单价-147天", width = 15)
    @ApiModelProperty(value = "留存单价-147天")
    private BigDecimal price147=BigDecimal.ZERO;
    /**留存单价-148天*/
    @Excel(name = "留存单价-148天", width = 15)
    @ApiModelProperty(value = "留存单价-148天")
    private BigDecimal price148=BigDecimal.ZERO;
    /**留存单价-149天*/
    @Excel(name = "留存单价-149天", width = 15)
    @ApiModelProperty(value = "留存单价-149天")
    private BigDecimal price149=BigDecimal.ZERO;
    /**留存单价-150天*/
    @Excel(name = "留存单价-150天", width = 15)
    @ApiModelProperty(value = "留存单价-150天")
    private BigDecimal price150= BigDecimal.ZERO;
}
