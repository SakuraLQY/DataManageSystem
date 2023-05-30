package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 留存150天数据
 * @author: chenglin
 * @date: 2023年05月18日 15:24
 */
@Data
public class RetainLoyalBo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**注册时间*/
    @ApiModelProperty(value = "注册时间")
    private Date dateTime;
    /**新增-注册数*/
    @ApiModelProperty(value = "新增-注册数")
    private Integer regCount;
    /**首日-付费人数*/
    @ApiModelProperty(value = "首日-付费人数")
    private Integer firstPayUser;
    /**留存-2天*/
    @Excel(name = "留存-2天", width = 15)
    @ApiModelProperty(value = "次日留存")
    private BigDecimal loyal2=BigDecimal.ZERO;
    /**留存-3天*/
    @Excel(name = "留存-3天", width = 15)
    @ApiModelProperty(value = "留存-3天")
    private BigDecimal loyal3=BigDecimal.ZERO;
    /**留存-4天*/
    @Excel(name = "留存-4天", width = 15)
    @ApiModelProperty(value = "留存-4天")
    private BigDecimal loyal4=BigDecimal.ZERO;
    /**留存-5天*/
    @Excel(name = "留存-5天", width = 15)
    @ApiModelProperty(value = "留存-5天")
    private BigDecimal loyal5=BigDecimal.ZERO;
    /**留存-6天*/
    @Excel(name = "留存-6天", width = 15)
    @ApiModelProperty(value = "留存-6天")
    private BigDecimal loyal6=BigDecimal.ZERO;
    /**留存-7天*/
    @Excel(name = "留存-7天", width = 15)
    @ApiModelProperty(value = "留存-7天")
    private BigDecimal loyal7= BigDecimal.ZERO;
    /**留存-8天*/
    @Excel(name = "留存-8天", width = 15)
    @ApiModelProperty(value = "留存-8天")
    private BigDecimal loyal8=BigDecimal.ZERO;
    /**留存-9天*/
    @Excel(name = "留存-9天", width = 15)
    @ApiModelProperty(value = "留存-9天")
    private BigDecimal loyal9=BigDecimal.ZERO;
    /**留存-10天*/
    @Excel(name = "留存-10天", width = 15)
    @ApiModelProperty(value = "留存-10天")
    private BigDecimal loyal10=BigDecimal.ZERO;
    /**留存-11天*/
    @Excel(name = "留存-11天", width = 15)
    @ApiModelProperty(value = "留存-11天")
    private BigDecimal loyal11=BigDecimal.ZERO;
    /**留存-12天*/
    @Excel(name = "留存-12天", width = 15)
    @ApiModelProperty(value = "留存-12天")
    private BigDecimal loyal12=BigDecimal.ZERO;
    /**留存-13天*/
    @Excel(name = "留存-13天", width = 15)
    @ApiModelProperty(value = "留存-13天")
    private BigDecimal loyal13=BigDecimal.ZERO;
    /**留存-14天*/
    @Excel(name = "留存-14天", width = 15)
    @ApiModelProperty(value = "留存-14天")
    private BigDecimal loyal14=BigDecimal.ZERO;
    /**留存-15天*/
    @Excel(name = "留存-15天", width = 15)
    @ApiModelProperty(value = "留存-15天")
    private BigDecimal loyal15=BigDecimal.ZERO;
    /**留存-16天*/
    @Excel(name = "留存-16天", width = 15)
    @ApiModelProperty(value = "留存-16天")
    private BigDecimal loyal16=BigDecimal.ZERO;
    /**留存-17天*/
    @Excel(name = "留存-17天", width = 15)
    @ApiModelProperty(value = "留存-17天")
    private BigDecimal loyal17=BigDecimal.ZERO;
    /**留存-18天*/
    @Excel(name = "留存-18天", width = 15)
    @ApiModelProperty(value = "留存-18天")
    private BigDecimal loyal18=BigDecimal.ZERO;
    /**留存-19天*/
    @Excel(name = "留存-19天", width = 15)
    @ApiModelProperty(value = "留存-19天")
    private BigDecimal loyal19=BigDecimal.ZERO;
    /**留存-20天*/
    @Excel(name = "留存-20天", width = 15)
    @ApiModelProperty(value = "留存-20天")
    private BigDecimal loyal20=BigDecimal.ZERO;
    /**留存-21天*/
    @Excel(name = "留存-21天", width = 15)
    @ApiModelProperty(value = "留存-21天")
    private BigDecimal loyal21=BigDecimal.ZERO;
    /**留存-22天*/
    @Excel(name = "留存-22天", width = 15)
    @ApiModelProperty(value = "留存-22天")
    private BigDecimal loyal22=BigDecimal.ZERO;
    /**留存-23天*/
    @Excel(name = "留存-23天", width = 15)
    @ApiModelProperty(value = "留存-23天")
    private BigDecimal loyal23=BigDecimal.ZERO;
    /**留存-24天*/
    @Excel(name = "留存-24天", width = 15)
    @ApiModelProperty(value = "留存-24天")
    private BigDecimal loyal24=BigDecimal.ZERO;
    /**留存-25天*/
    @Excel(name = "留存-25天", width = 15)
    @ApiModelProperty(value = "留存-25天")
    private BigDecimal loyal25=BigDecimal.ZERO;
    /**留存-26天*/
    @Excel(name = "留存-26天", width = 15)
    @ApiModelProperty(value = "留存-26天")
    private BigDecimal loyal26=BigDecimal.ZERO;
    /**留存-27天*/
    @Excel(name = "留存-27天", width = 15)
    @ApiModelProperty(value = "留存-27天")
    private BigDecimal loyal27=BigDecimal.ZERO;
    /**留存-28天*/
    @Excel(name = "留存-28天", width = 15)
    @ApiModelProperty(value = "留存-28天")
    private BigDecimal loyal28=BigDecimal.ZERO;
    /**留存-29天*/
    @Excel(name = "留存-29天", width = 15)
    @ApiModelProperty(value = "留存-29天")
    private BigDecimal loyal29=BigDecimal.ZERO;
    /**留存-30天*/
    @Excel(name = "留存-30天", width = 15)
    @ApiModelProperty(value = "留存-30天")
    private BigDecimal loyal30=BigDecimal.ZERO;
    /**留存-31天*/
    @Excel(name = "留存-31天", width = 15)
    @ApiModelProperty(value = "留存-31天")
    private BigDecimal loyal31=BigDecimal.ZERO;
    /**留存-32天*/
    @Excel(name = "留存-32天", width = 15)
    @ApiModelProperty(value = "留存-32天")
    private BigDecimal loyal32=BigDecimal.ZERO;
    /**留存-33天*/
    @Excel(name = "留存-33天", width = 15)
    @ApiModelProperty(value = "留存-33天")
    private BigDecimal loyal33=BigDecimal.ZERO;
    /**留存-34天*/
    @Excel(name = "留存-34天", width = 15)
    @ApiModelProperty(value = "留存-34天")
    private BigDecimal loyal34=BigDecimal.ZERO;
    /**留存-35天*/
    @Excel(name = "留存-35天", width = 15)
    @ApiModelProperty(value = "留存-35天")
    private BigDecimal loyal35=BigDecimal.ZERO;
    /**留存-36天*/
    @Excel(name = "留存-36天", width = 15)
    @ApiModelProperty(value = "留存-36天")
    private BigDecimal loyal36=BigDecimal.ZERO;
    /**留存-37天*/
    @Excel(name = "留存-37天", width = 15)
    @ApiModelProperty(value = "留存-37天")
    private BigDecimal loyal37=BigDecimal.ZERO;
    /**留存-38天*/
    @Excel(name = "留存-38天", width = 15)
    @ApiModelProperty(value = "留存-38天")
    private BigDecimal loyal38=BigDecimal.ZERO;
    /**留存-39天*/
    @Excel(name = "留存-39天", width = 15)
    @ApiModelProperty(value = "留存-39天")
    private BigDecimal loyal39=BigDecimal.ZERO;
    /**留存-40天*/
    @Excel(name = "留存-40天", width = 15)
    @ApiModelProperty(value = "留存-40天")
    private BigDecimal loyal40=BigDecimal.ZERO;
    /**留存-41天*/
    @Excel(name = "留存-41天", width = 15)
    @ApiModelProperty(value = "留存-41天")
    private BigDecimal loyal41=BigDecimal.ZERO;
    /**留存-42天*/
    @Excel(name = "留存-42天", width = 15)
    @ApiModelProperty(value = "留存-42天")
    private BigDecimal loyal42=BigDecimal.ZERO;
    /**留存-43天*/
    @Excel(name = "留存-43天", width = 15)
    @ApiModelProperty(value = "留存-43天")
    private BigDecimal loyal43=BigDecimal.ZERO;
    /**留存-44天*/
    @Excel(name = "留存-44天", width = 15)
    @ApiModelProperty(value = "留存-44天")
    private BigDecimal loyal44=BigDecimal.ZERO;
    /**留存-45天*/
    @Excel(name = "留存-45天", width = 15)
    @ApiModelProperty(value = "留存-45天")
    private BigDecimal loyal45=BigDecimal.ZERO;
    /**留存-46天*/
    @Excel(name = "留存-46天", width = 15)
    @ApiModelProperty(value = "留存-46天")
    private BigDecimal loyal46=BigDecimal.ZERO;
    /**留存-47天*/
    @Excel(name = "留存-47天", width = 15)
    @ApiModelProperty(value = "留存-47天")
    private BigDecimal loyal47=BigDecimal.ZERO;
    /**留存-48天*/
    @Excel(name = "留存-48天", width = 15)
    @ApiModelProperty(value = "留存-48天")
    private BigDecimal loyal48=BigDecimal.ZERO;
    /**留存-49天*/
    @Excel(name = "留存-49天", width = 15)
    @ApiModelProperty(value = "留存-49天")
    private BigDecimal loyal49=BigDecimal.ZERO;
    /**留存-50天*/
    @Excel(name = "留存-50天", width = 15)
    @ApiModelProperty(value = "留存-50天")
    private BigDecimal loyal50=BigDecimal.ZERO;
    /**留存-51天*/
    @Excel(name = "留存-51天", width = 15)
    @ApiModelProperty(value = "留存-51天")
    private BigDecimal loyal51=BigDecimal.ZERO;
    /**留存-52天*/
    @Excel(name = "留存-52天", width = 15)
    @ApiModelProperty(value = "留存-52天")
    private BigDecimal loyal52=BigDecimal.ZERO;
    /**留存-53天*/
    @Excel(name = "留存-53天", width = 15)
    @ApiModelProperty(value = "留存-53天")
    private BigDecimal loyal53=BigDecimal.ZERO;
    /**留存-54天*/
    @Excel(name = "留存-54天", width = 15)
    @ApiModelProperty(value = "留存-54天")
    private BigDecimal loyal54=BigDecimal.ZERO;
    /**留存-55天*/
    @Excel(name = "留存-55天", width = 15)
    @ApiModelProperty(value = "留存-55天")
    private BigDecimal loyal55=BigDecimal.ZERO;
    /**留存-56天*/
    @Excel(name = "留存-56天", width = 15)
    @ApiModelProperty(value = "留存-56天")
    private BigDecimal loyal56=BigDecimal.ZERO;
    /**留存-57天*/
    @Excel(name = "留存-57天", width = 15)
    @ApiModelProperty(value = "留存-57天")
    private BigDecimal loyal57=BigDecimal.ZERO;
    /**留存-58天*/
    @Excel(name = "留存-58天", width = 15)
    @ApiModelProperty(value = "留存-58天")
    private BigDecimal loyal58=BigDecimal.ZERO;
    /**留存-59天*/
    @Excel(name = "留存-59天", width = 15)
    @ApiModelProperty(value = "留存-59天")
    private BigDecimal loyal59=BigDecimal.ZERO;
    /**留存-60天*/
    @Excel(name = "留存-60天", width = 15)
    @ApiModelProperty(value = "留存-60天")
    private BigDecimal loyal60=BigDecimal.ZERO;
    /**留存-61天*/
    @Excel(name = "留存-61天", width = 15)
    @ApiModelProperty(value = "留存-61天")
    private BigDecimal loyal61=BigDecimal.ZERO;
    /**留存-62天*/
    @Excel(name = "留存-62天", width = 15)
    @ApiModelProperty(value = "留存-62天")
    private BigDecimal loyal62=BigDecimal.ZERO;
    /**留存-63天*/
    @Excel(name = "留存-63天", width = 15)
    @ApiModelProperty(value = "留存-63天")
    private BigDecimal loyal63=BigDecimal.ZERO;
    /**留存-64天*/
    @Excel(name = "留存-64天", width = 15)
    @ApiModelProperty(value = "留存-64天")
    private BigDecimal loyal64=BigDecimal.ZERO;
    /**留存-65天*/
    @Excel(name = "留存-65天", width = 15)
    @ApiModelProperty(value = "留存-65天")
    private BigDecimal loyal65=BigDecimal.ZERO;
    /**留存-66天*/
    @Excel(name = "留存-66天", width = 15)
    @ApiModelProperty(value = "留存-66天")
    private BigDecimal loyal66=BigDecimal.ZERO;
    /**留存-67天*/
    @Excel(name = "留存-67天", width = 15)
    @ApiModelProperty(value = "留存-67天")
    private BigDecimal loyal67=BigDecimal.ZERO;
    /**留存-68天*/
    @Excel(name = "留存-68天", width = 15)
    @ApiModelProperty(value = "留存-68天")
    private BigDecimal loyal68=BigDecimal.ZERO;
    /**留存-69天*/
    @Excel(name = "留存-69天", width = 15)
    @ApiModelProperty(value = "留存-69天")
    private BigDecimal loyal69=BigDecimal.ZERO;
    /**留存-70天*/
    @Excel(name = "留存-70天", width = 15)
    @ApiModelProperty(value = "留存-70天")
    private BigDecimal loyal70=BigDecimal.ZERO;
    /**留存-71天*/
    @Excel(name = "留存-71天", width = 15)
    @ApiModelProperty(value = "留存-71天")
    private BigDecimal loyal71=BigDecimal.ZERO;
    /**留存-72天*/
    @Excel(name = "留存-72天", width = 15)
    @ApiModelProperty(value = "留存-72天")
    private BigDecimal loyal72=BigDecimal.ZERO;
    /**留存-73天*/
    @Excel(name = "留存-73天", width = 15)
    @ApiModelProperty(value = "留存-73天")
    private BigDecimal loyal73=BigDecimal.ZERO;
    /**留存-74天*/
    @Excel(name = "留存-74天", width = 15)
    @ApiModelProperty(value = "留存-74天")
    private BigDecimal loyal74=BigDecimal.ZERO;
    /**留存-75天*/
    @Excel(name = "留存-75天", width = 15)
    @ApiModelProperty(value = "留存-75天")
    private BigDecimal loyal75=BigDecimal.ZERO;
    /**留存-76天*/
    @Excel(name = "留存-76天", width = 15)
    @ApiModelProperty(value = "留存-76天")
    private BigDecimal loyal76=BigDecimal.ZERO;
    /**留存-77天*/
    @Excel(name = "留存-77天", width = 15)
    @ApiModelProperty(value = "留存-77天")
    private BigDecimal loyal77=BigDecimal.ZERO;
    /**留存-78天*/
    @Excel(name = "留存-78天", width = 15)
    @ApiModelProperty(value = "留存-78天")
    private BigDecimal loyal78=BigDecimal.ZERO;
    /**留存-79天*/
    @Excel(name = "留存-79天", width = 15)
    @ApiModelProperty(value = "留存-79天")
    private BigDecimal loyal79=BigDecimal.ZERO;
    /**留存-80天*/
    @Excel(name = "留存-80天", width = 15)
    @ApiModelProperty(value = "留存-80天")
    private BigDecimal loyal80=BigDecimal.ZERO;
    /**留存-81天*/
    @Excel(name = "留存-81天", width = 15)
    @ApiModelProperty(value = "留存-81天")
    private BigDecimal loyal81=BigDecimal.ZERO;
    /**留存-82天*/
    @Excel(name = "留存-82天", width = 15)
    @ApiModelProperty(value = "留存-82天")
    private BigDecimal loyal82=BigDecimal.ZERO;
    /**留存-83天*/
    @Excel(name = "留存-83天", width = 15)
    @ApiModelProperty(value = "留存-83天")
    private BigDecimal loyal83=BigDecimal.ZERO;
    /**留存-84天*/
    @Excel(name = "留存-84天", width = 15)
    @ApiModelProperty(value = "留存-84天")
    private BigDecimal loyal84=BigDecimal.ZERO;
    /**留存-85天*/
    @Excel(name = "留存-85天", width = 15)
    @ApiModelProperty(value = "留存-85天")
    private BigDecimal loyal85=BigDecimal.ZERO;
    /**留存-86天*/
    @Excel(name = "留存-86天", width = 15)
    @ApiModelProperty(value = "留存-86天")
    private BigDecimal loyal86=BigDecimal.ZERO;
    /**留存-87天*/
    @Excel(name = "留存-87天", width = 15)
    @ApiModelProperty(value = "留存-87天")
    private BigDecimal loyal87=BigDecimal.ZERO;
    /**留存-88天*/
    @Excel(name = "留存-88天", width = 15)
    @ApiModelProperty(value = "留存-88天")
    private BigDecimal loyal88=BigDecimal.ZERO;
    /**留存-89天*/
    @Excel(name = "留存-89天", width = 15)
    @ApiModelProperty(value = "留存-89天")
    private BigDecimal loyal89=BigDecimal.ZERO;
    /**留存-90天*/
    @Excel(name = "留存-90天", width = 15)
    @ApiModelProperty(value = "留存-90天")
    private BigDecimal loyal90=BigDecimal.ZERO;
    /**留存-91天*/
    @Excel(name = "留存-91天", width = 15)
    @ApiModelProperty(value = "留存-91天")
    private BigDecimal loyal91=BigDecimal.ZERO;
    /**留存-92天*/
    @Excel(name = "留存-92天", width = 15)
    @ApiModelProperty(value = "留存-92天")
    private BigDecimal loyal92=BigDecimal.ZERO;
    /**留存-93天*/
    @Excel(name = "留存-93天", width = 15)
    @ApiModelProperty(value = "留存-93天")
    private BigDecimal loyal93=BigDecimal.ZERO;
    /**留存-94天*/
    @Excel(name = "留存-94天", width = 15)
    @ApiModelProperty(value = "留存-94天")
    private BigDecimal loyal94=BigDecimal.ZERO;
    /**留存-95天*/
    @Excel(name = "留存-95天", width = 15)
    @ApiModelProperty(value = "留存-95天")
    private BigDecimal loyal95=BigDecimal.ZERO;
    /**留存-96天*/
    @Excel(name = "留存-96天", width = 15)
    @ApiModelProperty(value = "留存-96天")
    private BigDecimal loyal96=BigDecimal.ZERO;
    /**留存-97天*/
    @Excel(name = "留存-97天", width = 15)
    @ApiModelProperty(value = "留存-97天")
    private BigDecimal loyal97=BigDecimal.ZERO;
    /**留存-98天*/
    @Excel(name = "留存-98天", width = 15)
    @ApiModelProperty(value = "留存-98天")
    private BigDecimal loyal98=BigDecimal.ZERO;
    /**留存-99天*/
    @Excel(name = "留存-99天", width = 15)
    @ApiModelProperty(value = "留存-99天")
    private BigDecimal loyal99=BigDecimal.ZERO;
    /**留存-100天*/
    @Excel(name = "留存-100天", width = 15)
    @ApiModelProperty(value = "留存-100天")
    private BigDecimal loyal100=BigDecimal.ZERO;
    /**留存-101天*/
    @Excel(name = "留存-101天", width = 15)
    @ApiModelProperty(value = "留存-101天")
    private BigDecimal loyal101=BigDecimal.ZERO;
    /**留存-102天*/
    @Excel(name = "留存-102天", width = 15)
    @ApiModelProperty(value = "留存-102天")
    private BigDecimal loyal102=BigDecimal.ZERO;
    /**留存-103天*/
    @Excel(name = "留存-103天", width = 15)
    @ApiModelProperty(value = "留存-103天")
    private BigDecimal loyal103=BigDecimal.ZERO;
    /**留存-104天*/
    @Excel(name = "留存-104天", width = 15)
    @ApiModelProperty(value = "留存-104天")
    private BigDecimal loyal104=BigDecimal.ZERO;
    /**留存-105天*/
    @Excel(name = "留存-105天", width = 15)
    @ApiModelProperty(value = "留存-105天")
    private BigDecimal loyal105=BigDecimal.ZERO;
    /**留存-106天*/
    @Excel(name = "留存-106天", width = 15)
    @ApiModelProperty(value = "留存-106天")
    private BigDecimal loyal106=BigDecimal.ZERO;
    /**留存-107天*/
    @Excel(name = "留存-107天", width = 15)
    @ApiModelProperty(value = "留存-107天")
    private BigDecimal loyal107=BigDecimal.ZERO;
    /**留存-108天*/
    @Excel(name = "留存-108天", width = 15)
    @ApiModelProperty(value = "留存-108天")
    private BigDecimal loyal108=BigDecimal.ZERO;
    /**留存-109天*/
    @Excel(name = "留存-109天", width = 15)
    @ApiModelProperty(value = "留存-109天")
    private BigDecimal loyal109=BigDecimal.ZERO;
    /**留存-110天*/
    @Excel(name = "留存-110天", width = 15)
    @ApiModelProperty(value = "留存-110天")
    private BigDecimal loyal110=BigDecimal.ZERO;
    /**留存-111天*/
    @Excel(name = "留存-111天", width = 15)
    @ApiModelProperty(value = "留存-111天")
    private BigDecimal loyal111=BigDecimal.ZERO;
    /**留存-112天*/
    @Excel(name = "留存-112天", width = 15)
    @ApiModelProperty(value = "留存-112天")
    private BigDecimal loyal112=BigDecimal.ZERO;
    /**留存-113天*/
    @Excel(name = "留存-113天", width = 15)
    @ApiModelProperty(value = "留存-113天")
    private BigDecimal loyal113=BigDecimal.ZERO;
    /**留存-114天*/
    @Excel(name = "留存-114天", width = 15)
    @ApiModelProperty(value = "留存-114天")
    private BigDecimal loyal114=BigDecimal.ZERO;
    /**留存-115天*/
    @Excel(name = "留存-115天", width = 15)
    @ApiModelProperty(value = "留存-115天")
    private BigDecimal loyal115=BigDecimal.ZERO;
    /**留存-116天*/
    @Excel(name = "留存-116天", width = 15)
    @ApiModelProperty(value = "留存-116天")
    private BigDecimal loyal116=BigDecimal.ZERO;
    /**留存-117天*/
    @Excel(name = "留存-117天", width = 15)
    @ApiModelProperty(value = "留存-117天")
    private BigDecimal loyal117=BigDecimal.ZERO;
    /**留存-118天*/
    @Excel(name = "留存-118天", width = 15)
    @ApiModelProperty(value = "留存-118天")
    private BigDecimal loyal118=BigDecimal.ZERO;
    /**留存-119天*/
    @Excel(name = "留存-119天", width = 15)
    @ApiModelProperty(value = "留存-119天")
    private BigDecimal loyal119=BigDecimal.ZERO;
    /**留存-120天*/
    @Excel(name = "留存-120天", width = 15)
    @ApiModelProperty(value = "留存-120天")
    private BigDecimal loyal120=BigDecimal.ZERO;
    /**留存-121天*/
    @Excel(name = "留存-121天", width = 15)
    @ApiModelProperty(value = "留存-121天")
    private BigDecimal loyal121=BigDecimal.ZERO;
    /**留存-122天*/
    @Excel(name = "留存-122天", width = 15)
    @ApiModelProperty(value = "留存-122天")
    private BigDecimal loyal122=BigDecimal.ZERO;
    /**留存-123天*/
    @Excel(name = "留存-123天", width = 15)
    @ApiModelProperty(value = "留存-123天")
    private BigDecimal loyal123=BigDecimal.ZERO;
    /**留存-124天*/
    @Excel(name = "留存-124天", width = 15)
    @ApiModelProperty(value = "留存-124天")
    private BigDecimal loyal124=BigDecimal.ZERO;
    /**留存-125天*/
    @Excel(name = "留存-125天", width = 15)
    @ApiModelProperty(value = "留存-125天")
    private BigDecimal loyal125=BigDecimal.ZERO;
    /**留存-126天*/
    @Excel(name = "留存-126天", width = 15)
    @ApiModelProperty(value = "留存-126天")
    private BigDecimal loyal126=BigDecimal.ZERO;
    /**留存-127天*/
    @Excel(name = "留存-127天", width = 15)
    @ApiModelProperty(value = "留存-127天")
    private BigDecimal loyal127=BigDecimal.ZERO;
    /**留存-128天*/
    @Excel(name = "留存-128天", width = 15)
    @ApiModelProperty(value = "留存-128天")
    private BigDecimal loyal128=BigDecimal.ZERO;
    /**留存-129天*/
    @Excel(name = "留存-129天", width = 15)
    @ApiModelProperty(value = "留存-129天")
    private BigDecimal loyal129=BigDecimal.ZERO;
    /**留存-130天*/
    @Excel(name = "留存-130天", width = 15)
    @ApiModelProperty(value = "留存-130天")
    private BigDecimal loyal130=BigDecimal.ZERO;
    /**留存-131天*/
    @Excel(name = "留存-131天", width = 15)
    @ApiModelProperty(value = "留存-131天")
    private BigDecimal loyal131=BigDecimal.ZERO;
    /**留存-132天*/
    @Excel(name = "留存-132天", width = 15)
    @ApiModelProperty(value = "留存-132天")
    private BigDecimal loyal132=BigDecimal.ZERO;
    /**留存-133天*/
    @Excel(name = "留存-133天", width = 15)
    @ApiModelProperty(value = "留存-133天")
    private BigDecimal loyal133=BigDecimal.ZERO;
    /**留存-134天*/
    @Excel(name = "留存-134天", width = 15)
    @ApiModelProperty(value = "留存-134天")
    private BigDecimal loyal134=BigDecimal.ZERO;
    /**留存-135天*/
    @Excel(name = "留存-135天", width = 15)
    @ApiModelProperty(value = "留存-135天")
    private BigDecimal loyal135=BigDecimal.ZERO;
    /**留存-136天*/
    @Excel(name = "留存-136天", width = 15)
    @ApiModelProperty(value = "留存-136天")
    private BigDecimal loyal136=BigDecimal.ZERO;
    /**留存-137天*/
    @Excel(name = "留存-137天", width = 15)
    @ApiModelProperty(value = "留存-137天")
    private BigDecimal loyal137=BigDecimal.ZERO;
    /**留存-138天*/
    @Excel(name = "留存-138天", width = 15)
    @ApiModelProperty(value = "留存-138天")
    private BigDecimal loyal138=BigDecimal.ZERO;
    /**留存-139天*/
    @Excel(name = "留存-139天", width = 15)
    @ApiModelProperty(value = "留存-139天")
    private BigDecimal loyal139=BigDecimal.ZERO;
    /**留存-140天*/
    @Excel(name = "留存-140天", width = 15)
    @ApiModelProperty(value = "留存-140天")
    private BigDecimal loyal140=BigDecimal.ZERO;
    /**留存-141天*/
    @Excel(name = "留存-141天", width = 15)
    @ApiModelProperty(value = "留存-141天")
    private BigDecimal loyal141=BigDecimal.ZERO;
    /**留存-142天*/
    @Excel(name = "留存-142天", width = 15)
    @ApiModelProperty(value = "留存-142天")
    private BigDecimal loyal142=BigDecimal.ZERO;
    /**留存-143天*/
    @Excel(name = "留存-143天", width = 15)
    @ApiModelProperty(value = "留存-143天")
    private BigDecimal loyal143=BigDecimal.ZERO;
    /**留存-144天*/
    @Excel(name = "留存-144天", width = 15)
    @ApiModelProperty(value = "留存-144天")
    private BigDecimal loyal144=BigDecimal.ZERO;
    /**留存-145天*/
    @Excel(name = "留存-145天", width = 15)
    @ApiModelProperty(value = "留存-145天")
    private BigDecimal loyal145=BigDecimal.ZERO;
    /**留存-146天*/
    @Excel(name = "留存-146天", width = 15)
    @ApiModelProperty(value = "留存-146天")
    private BigDecimal loyal146=BigDecimal.ZERO;
    /**留存-147天*/
    @Excel(name = "留存-147天", width = 15)
    @ApiModelProperty(value = "留存-147天")
    private BigDecimal loyal147=BigDecimal.ZERO;
    /**留存-148天*/
    @Excel(name = "留存-148天", width = 15)
    @ApiModelProperty(value = "留存-148天")
    private BigDecimal loyal148=BigDecimal.ZERO;
    /**留存-149天*/
    @Excel(name = "留存-149天", width = 15)
    @ApiModelProperty(value = "留存-149天")
    private BigDecimal loyal149=BigDecimal.ZERO;
    /**留存-150天*/
    @Excel(name = "留存-150天", width = 15)
    @ApiModelProperty(value = "留存-150天")
    private BigDecimal loyal150= BigDecimal.ZERO;
}
