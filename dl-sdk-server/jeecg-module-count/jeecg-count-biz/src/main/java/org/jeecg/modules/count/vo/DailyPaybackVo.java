package org.jeecg.modules.count.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_daily_payback_dev
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="ct_daily_payback对象", description="ct_daily_payback")
public class DailyPaybackVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**注册时间*/
	@ApiModelProperty(value = "注册时间")
	private String registerTime;
	/**游戏*/
	@ApiModelProperty(value = "游戏")
	private Integer gameId;
	/**子游戏*/
	@ApiModelProperty(value = "子游戏")
	private Integer subGameId;
	/**广告ID*/
	@ApiModelProperty(value = "广告ID")
	private Integer dealId;
	/**渠道ID*/
	@ApiModelProperty(value = "渠道ID")
	private Integer channelId;
	/**渠道子账号id*/
	@ApiModelProperty(value = "渠道子账号id")
	private Integer channelSubAccountId;
	/**累积-付费金额*/
	@ApiModelProperty(value = "累积-付费金额")
	private BigDecimal totalMoney;
	/**首日-付费金额*/
	@ApiModelProperty(value = "首日-付费金额")
	private BigDecimal firstMoney;
	/**新增-注册数*/
	@ApiModelProperty(value = "新增-注册数")
	private Integer countUser;
	/**首日-付费人数*/
	@ApiModelProperty(value = "首日-付费人数")
	private Integer firstPayuser;
	/**累计付费ios*/
	@ApiModelProperty(value = "累计付费ios")
	private BigDecimal totalMoneyIos;
	/**成本*/
	@ApiModelProperty(value = "成本")
	private BigDecimal remainTotalMoney;
	/**回本-1天*/
	@Excel(name = "回本-1天", width = 15)
	@ApiModelProperty(value = "回本-1天")
	private BigDecimal day1=BigDecimal.ZERO;
	/**回本-2天*/
	@Excel(name = "回本-2天", width = 15)
	@ApiModelProperty(value = "回本-2天")
	private BigDecimal day2=BigDecimal.ZERO;
	/**回本-3天*/
	@Excel(name = "回本-3天", width = 15)
	@ApiModelProperty(value = "回本-3天")
	private BigDecimal day3=BigDecimal.ZERO;
	/**回本-4天*/
	@Excel(name = "回本-4天", width = 15)
	@ApiModelProperty(value = "回本-4天")
	private BigDecimal day4=BigDecimal.ZERO;
	/**回本-5天*/
	@Excel(name = "回本-5天", width = 15)
	@ApiModelProperty(value = "回本-5天")
	private BigDecimal day5=BigDecimal.ZERO;
	/**回本-6天*/
	@Excel(name = "回本-6天", width = 15)
	@ApiModelProperty(value = "回本-6天")
	private BigDecimal day6=BigDecimal.ZERO;
	/**回本-7天*/
	@Excel(name = "回本-7天", width = 15)
	@ApiModelProperty(value = "回本-7天")
	private BigDecimal day7= BigDecimal.ZERO;
	/**回本-8天*/
	@Excel(name = "回本-8天", width = 15)
	@ApiModelProperty(value = "回本-8天")
	private BigDecimal day8=BigDecimal.ZERO;
	/**回本-9天*/
	@Excel(name = "回本-9天", width = 15)
	@ApiModelProperty(value = "回本-9天")
	private BigDecimal day9=BigDecimal.ZERO;
	/**回本-10天*/
	@Excel(name = "回本-10天", width = 15)
	@ApiModelProperty(value = "回本-10天")
	private BigDecimal day10=BigDecimal.ZERO;
	/**回本-11天*/
	@Excel(name = "回本-11天", width = 15)
	@ApiModelProperty(value = "回本-11天")
	private BigDecimal day11=BigDecimal.ZERO;
	/**回本-12天*/
	@Excel(name = "回本-12天", width = 15)
	@ApiModelProperty(value = "回本-12天")
	private BigDecimal day12=BigDecimal.ZERO;
	/**回本-13天*/
	@Excel(name = "回本-13天", width = 15)
	@ApiModelProperty(value = "回本-13天")
	private BigDecimal day13=BigDecimal.ZERO;
	/**回本-14天*/
	@Excel(name = "回本-14天", width = 15)
	@ApiModelProperty(value = "回本-14天")
	private BigDecimal day14=BigDecimal.ZERO;
	/**回本-15天*/
	@Excel(name = "回本-15天", width = 15)
	@ApiModelProperty(value = "回本-15天")
	private BigDecimal day15=BigDecimal.ZERO;
	/**回本-16天*/
	@Excel(name = "回本-16天", width = 15)
	@ApiModelProperty(value = "回本-16天")
	private BigDecimal day16=BigDecimal.ZERO;
	/**回本-17天*/
	@Excel(name = "回本-17天", width = 15)
	@ApiModelProperty(value = "回本-17天")
	private BigDecimal day17=BigDecimal.ZERO;
	/**回本-18天*/
	@Excel(name = "回本-18天", width = 15)
	@ApiModelProperty(value = "回本-18天")
	private BigDecimal day18=BigDecimal.ZERO;
	/**回本-19天*/
	@Excel(name = "回本-19天", width = 15)
	@ApiModelProperty(value = "回本-19天")
	private BigDecimal day19=BigDecimal.ZERO;
	/**回本-20天*/
	@Excel(name = "回本-20天", width = 15)
	@ApiModelProperty(value = "回本-20天")
	private BigDecimal day20=BigDecimal.ZERO;
	/**回本-21天*/
	@Excel(name = "回本-21天", width = 15)
	@ApiModelProperty(value = "回本-21天")
	private BigDecimal day21=BigDecimal.ZERO;
	/**回本-22天*/
	@Excel(name = "回本-22天", width = 15)
	@ApiModelProperty(value = "回本-22天")
	private BigDecimal day22=BigDecimal.ZERO;
	/**回本-23天*/
	@Excel(name = "回本-23天", width = 15)
	@ApiModelProperty(value = "回本-23天")
	private BigDecimal day23=BigDecimal.ZERO;
	/**回本-24天*/
	@Excel(name = "回本-24天", width = 15)
	@ApiModelProperty(value = "回本-24天")
	private BigDecimal day24=BigDecimal.ZERO;
	/**回本-25天*/
	@Excel(name = "回本-25天", width = 15)
	@ApiModelProperty(value = "回本-25天")
	private BigDecimal day25=BigDecimal.ZERO;
	/**回本-26天*/
	@Excel(name = "回本-26天", width = 15)
	@ApiModelProperty(value = "回本-26天")
	private BigDecimal day26=BigDecimal.ZERO;
	/**回本-27天*/
	@Excel(name = "回本-27天", width = 15)
	@ApiModelProperty(value = "回本-27天")
	private BigDecimal day27=BigDecimal.ZERO;
	/**回本-28天*/
	@Excel(name = "回本-28天", width = 15)
	@ApiModelProperty(value = "回本-28天")
	private BigDecimal day28=BigDecimal.ZERO;
	/**回本-29天*/
	@Excel(name = "回本-29天", width = 15)
	@ApiModelProperty(value = "回本-29天")
	private BigDecimal day29=BigDecimal.ZERO;
	/**回本-30天*/
	@Excel(name = "回本-30天", width = 15)
	@ApiModelProperty(value = "回本-30天")
	private BigDecimal day30=BigDecimal.ZERO;
	/**回本-31天*/
	@Excel(name = "回本-31天", width = 15)
	@ApiModelProperty(value = "回本-31天")
	private BigDecimal day31=BigDecimal.ZERO;
	/**回本-32天*/
	@Excel(name = "回本-32天", width = 15)
	@ApiModelProperty(value = "回本-32天")
	private BigDecimal day32=BigDecimal.ZERO;
	/**回本-33天*/
	@Excel(name = "回本-33天", width = 15)
	@ApiModelProperty(value = "回本-33天")
	private BigDecimal day33=BigDecimal.ZERO;
	/**回本-34天*/
	@Excel(name = "回本-34天", width = 15)
	@ApiModelProperty(value = "回本-34天")
	private BigDecimal day34=BigDecimal.ZERO;
	/**回本-35天*/
	@Excel(name = "回本-35天", width = 15)
	@ApiModelProperty(value = "回本-35天")
	private BigDecimal day35=BigDecimal.ZERO;
	/**回本-36天*/
	@Excel(name = "回本-36天", width = 15)
	@ApiModelProperty(value = "回本-36天")
	private BigDecimal day36=BigDecimal.ZERO;
	/**回本-37天*/
	@Excel(name = "回本-37天", width = 15)
	@ApiModelProperty(value = "回本-37天")
	private BigDecimal day37=BigDecimal.ZERO;
	/**回本-38天*/
	@Excel(name = "回本-38天", width = 15)
	@ApiModelProperty(value = "回本-38天")
	private BigDecimal day38=BigDecimal.ZERO;
	/**回本-39天*/
	@Excel(name = "回本-39天", width = 15)
	@ApiModelProperty(value = "回本-39天")
	private BigDecimal day39=BigDecimal.ZERO;
	/**回本-40天*/
	@Excel(name = "回本-40天", width = 15)
	@ApiModelProperty(value = "回本-40天")
	private BigDecimal day40=BigDecimal.ZERO;
	/**回本-41天*/
	@Excel(name = "回本-41天", width = 15)
	@ApiModelProperty(value = "回本-41天")
	private BigDecimal day41=BigDecimal.ZERO;
	/**回本-42天*/
	@Excel(name = "回本-42天", width = 15)
	@ApiModelProperty(value = "回本-42天")
	private BigDecimal day42=BigDecimal.ZERO;
	/**回本-43天*/
	@Excel(name = "回本-43天", width = 15)
	@ApiModelProperty(value = "回本-43天")
	private BigDecimal day43=BigDecimal.ZERO;
	/**回本-44天*/
	@Excel(name = "回本-44天", width = 15)
	@ApiModelProperty(value = "回本-44天")
	private BigDecimal day44=BigDecimal.ZERO;
	/**回本-45天*/
	@Excel(name = "回本-45天", width = 15)
	@ApiModelProperty(value = "回本-45天")
	private BigDecimal day45=BigDecimal.ZERO;
	/**回本-46天*/
	@Excel(name = "回本-46天", width = 15)
	@ApiModelProperty(value = "回本-46天")
	private BigDecimal day46=BigDecimal.ZERO;
	/**回本-47天*/
	@Excel(name = "回本-47天", width = 15)
	@ApiModelProperty(value = "回本-47天")
	private BigDecimal day47=BigDecimal.ZERO;
	/**回本-48天*/
	@Excel(name = "回本-48天", width = 15)
	@ApiModelProperty(value = "回本-48天")
	private BigDecimal day48=BigDecimal.ZERO;
	/**回本-49天*/
	@Excel(name = "回本-49天", width = 15)
	@ApiModelProperty(value = "回本-49天")
	private BigDecimal day49=BigDecimal.ZERO;
	/**回本-50天*/
	@Excel(name = "回本-50天", width = 15)
	@ApiModelProperty(value = "回本-50天")
	private BigDecimal day50=BigDecimal.ZERO;
	/**回本-51天*/
	@Excel(name = "回本-51天", width = 15)
	@ApiModelProperty(value = "回本-51天")
	private BigDecimal day51=BigDecimal.ZERO;
	/**回本-52天*/
	@Excel(name = "回本-52天", width = 15)
	@ApiModelProperty(value = "回本-52天")
	private BigDecimal day52=BigDecimal.ZERO;
	/**回本-53天*/
	@Excel(name = "回本-53天", width = 15)
	@ApiModelProperty(value = "回本-53天")
	private BigDecimal day53=BigDecimal.ZERO;
	/**回本-54天*/
	@Excel(name = "回本-54天", width = 15)
	@ApiModelProperty(value = "回本-54天")
	private BigDecimal day54=BigDecimal.ZERO;
	/**回本-55天*/
	@Excel(name = "回本-55天", width = 15)
	@ApiModelProperty(value = "回本-55天")
	private BigDecimal day55=BigDecimal.ZERO;
	/**回本-56天*/
	@Excel(name = "回本-56天", width = 15)
	@ApiModelProperty(value = "回本-56天")
	private BigDecimal day56=BigDecimal.ZERO;
	/**回本-57天*/
	@Excel(name = "回本-57天", width = 15)
	@ApiModelProperty(value = "回本-57天")
	private BigDecimal day57=BigDecimal.ZERO;
	/**回本-58天*/
	@Excel(name = "回本-58天", width = 15)
	@ApiModelProperty(value = "回本-58天")
	private BigDecimal day58=BigDecimal.ZERO;
	/**回本-59天*/
	@Excel(name = "回本-59天", width = 15)
	@ApiModelProperty(value = "回本-59天")
	private BigDecimal day59=BigDecimal.ZERO;
	/**回本-60天*/
	@Excel(name = "回本-60天", width = 15)
	@ApiModelProperty(value = "回本-60天")
	private BigDecimal day60=BigDecimal.ZERO;
	/**回本-61天*/
	@Excel(name = "回本-61天", width = 15)
	@ApiModelProperty(value = "回本-61天")
	private BigDecimal day61=BigDecimal.ZERO;
	/**回本-62天*/
	@Excel(name = "回本-62天", width = 15)
	@ApiModelProperty(value = "回本-62天")
	private BigDecimal day62=BigDecimal.ZERO;
	/**回本-63天*/
	@Excel(name = "回本-63天", width = 15)
	@ApiModelProperty(value = "回本-63天")
	private BigDecimal day63=BigDecimal.ZERO;
	/**回本-64天*/
	@Excel(name = "回本-64天", width = 15)
	@ApiModelProperty(value = "回本-64天")
	private BigDecimal day64=BigDecimal.ZERO;
	/**回本-65天*/
	@Excel(name = "回本-65天", width = 15)
	@ApiModelProperty(value = "回本-65天")
	private BigDecimal day65=BigDecimal.ZERO;
	/**回本-66天*/
	@Excel(name = "回本-66天", width = 15)
	@ApiModelProperty(value = "回本-66天")
	private BigDecimal day66=BigDecimal.ZERO;
	/**回本-67天*/
	@Excel(name = "回本-67天", width = 15)
	@ApiModelProperty(value = "回本-67天")
	private BigDecimal day67=BigDecimal.ZERO;
	/**回本-68天*/
	@Excel(name = "回本-68天", width = 15)
	@ApiModelProperty(value = "回本-68天")
	private BigDecimal day68=BigDecimal.ZERO;
	/**回本-69天*/
	@Excel(name = "回本-69天", width = 15)
	@ApiModelProperty(value = "回本-69天")
	private BigDecimal day69=BigDecimal.ZERO;
	/**回本-70天*/
	@Excel(name = "回本-70天", width = 15)
	@ApiModelProperty(value = "回本-70天")
	private BigDecimal day70=BigDecimal.ZERO;
	/**回本-71天*/
	@Excel(name = "回本-71天", width = 15)
	@ApiModelProperty(value = "回本-71天")
	private BigDecimal day71=BigDecimal.ZERO;
	/**回本-72天*/
	@Excel(name = "回本-72天", width = 15)
	@ApiModelProperty(value = "回本-72天")
	private BigDecimal day72=BigDecimal.ZERO;
	/**回本-73天*/
	@Excel(name = "回本-73天", width = 15)
	@ApiModelProperty(value = "回本-73天")
	private BigDecimal day73=BigDecimal.ZERO;
	/**回本-74天*/
	@Excel(name = "回本-74天", width = 15)
	@ApiModelProperty(value = "回本-74天")
	private BigDecimal day74=BigDecimal.ZERO;
	/**回本-75天*/
	@Excel(name = "回本-75天", width = 15)
	@ApiModelProperty(value = "回本-75天")
	private BigDecimal day75=BigDecimal.ZERO;
	/**回本-76天*/
	@Excel(name = "回本-76天", width = 15)
	@ApiModelProperty(value = "回本-76天")
	private BigDecimal day76=BigDecimal.ZERO;
	/**回本-77天*/
	@Excel(name = "回本-77天", width = 15)
	@ApiModelProperty(value = "回本-77天")
	private BigDecimal day77=BigDecimal.ZERO;
	/**回本-78天*/
	@Excel(name = "回本-78天", width = 15)
	@ApiModelProperty(value = "回本-78天")
	private BigDecimal day78=BigDecimal.ZERO;
	/**回本-79天*/
	@Excel(name = "回本-79天", width = 15)
	@ApiModelProperty(value = "回本-79天")
	private BigDecimal day79=BigDecimal.ZERO;
	/**回本-80天*/
	@Excel(name = "回本-80天", width = 15)
	@ApiModelProperty(value = "回本-80天")
	private BigDecimal day80=BigDecimal.ZERO;
	/**回本-81天*/
	@Excel(name = "回本-81天", width = 15)
	@ApiModelProperty(value = "回本-81天")
	private BigDecimal day81=BigDecimal.ZERO;
	/**回本-82天*/
	@Excel(name = "回本-82天", width = 15)
	@ApiModelProperty(value = "回本-82天")
	private BigDecimal day82=BigDecimal.ZERO;
	/**回本-83天*/
	@Excel(name = "回本-83天", width = 15)
	@ApiModelProperty(value = "回本-83天")
	private BigDecimal day83=BigDecimal.ZERO;
	/**回本-84天*/
	@Excel(name = "回本-84天", width = 15)
	@ApiModelProperty(value = "回本-84天")
	private BigDecimal day84=BigDecimal.ZERO;
	/**回本-85天*/
	@Excel(name = "回本-85天", width = 15)
	@ApiModelProperty(value = "回本-85天")
	private BigDecimal day85=BigDecimal.ZERO;
	/**回本-86天*/
	@Excel(name = "回本-86天", width = 15)
	@ApiModelProperty(value = "回本-86天")
	private BigDecimal day86=BigDecimal.ZERO;
	/**回本-87天*/
	@Excel(name = "回本-87天", width = 15)
	@ApiModelProperty(value = "回本-87天")
	private BigDecimal day87=BigDecimal.ZERO;
	/**回本-88天*/
	@Excel(name = "回本-88天", width = 15)
	@ApiModelProperty(value = "回本-88天")
	private BigDecimal day88=BigDecimal.ZERO;
	/**回本-89天*/
	@Excel(name = "回本-89天", width = 15)
	@ApiModelProperty(value = "回本-89天")
	private BigDecimal day89=BigDecimal.ZERO;
	/**回本-90天*/
	@Excel(name = "回本-90天", width = 15)
	@ApiModelProperty(value = "回本-90天")
	private BigDecimal day90=BigDecimal.ZERO;
	/**回本-91天*/
	@Excel(name = "回本-91天", width = 15)
	@ApiModelProperty(value = "回本-91天")
	private BigDecimal day91=BigDecimal.ZERO;
	/**回本-92天*/
	@Excel(name = "回本-92天", width = 15)
	@ApiModelProperty(value = "回本-92天")
	private BigDecimal day92=BigDecimal.ZERO;
	/**回本-93天*/
	@Excel(name = "回本-93天", width = 15)
	@ApiModelProperty(value = "回本-93天")
	private BigDecimal day93=BigDecimal.ZERO;
	/**回本-94天*/
	@Excel(name = "回本-94天", width = 15)
	@ApiModelProperty(value = "回本-94天")
	private BigDecimal day94=BigDecimal.ZERO;
	/**回本-95天*/
	@Excel(name = "回本-95天", width = 15)
	@ApiModelProperty(value = "回本-95天")
	private BigDecimal day95=BigDecimal.ZERO;
	/**回本-96天*/
	@Excel(name = "回本-96天", width = 15)
	@ApiModelProperty(value = "回本-96天")
	private BigDecimal day96=BigDecimal.ZERO;
	/**回本-97天*/
	@Excel(name = "回本-97天", width = 15)
	@ApiModelProperty(value = "回本-97天")
	private BigDecimal day97=BigDecimal.ZERO;
	/**回本-98天*/
	@Excel(name = "回本-98天", width = 15)
	@ApiModelProperty(value = "回本-98天")
	private BigDecimal day98=BigDecimal.ZERO;
	/**回本-99天*/
	@Excel(name = "回本-99天", width = 15)
	@ApiModelProperty(value = "回本-99天")
	private BigDecimal day99=BigDecimal.ZERO;
	/**回本-100天*/
	@Excel(name = "回本-100天", width = 15)
	@ApiModelProperty(value = "回本-100天")
	private BigDecimal day100=BigDecimal.ZERO;
	/**回本-101天*/
	@Excel(name = "回本-101天", width = 15)
	@ApiModelProperty(value = "回本-101天")
	private BigDecimal day101=BigDecimal.ZERO;
	/**回本-102天*/
	@Excel(name = "回本-102天", width = 15)
	@ApiModelProperty(value = "回本-102天")
	private BigDecimal day102=BigDecimal.ZERO;
	/**回本-103天*/
	@Excel(name = "回本-103天", width = 15)
	@ApiModelProperty(value = "回本-103天")
	private BigDecimal day103=BigDecimal.ZERO;
	/**回本-104天*/
	@Excel(name = "回本-104天", width = 15)
	@ApiModelProperty(value = "回本-104天")
	private BigDecimal day104=BigDecimal.ZERO;
	/**回本-105天*/
	@Excel(name = "回本-105天", width = 15)
	@ApiModelProperty(value = "回本-105天")
	private BigDecimal day105=BigDecimal.ZERO;
	/**回本-106天*/
	@Excel(name = "回本-106天", width = 15)
	@ApiModelProperty(value = "回本-106天")
	private BigDecimal day106=BigDecimal.ZERO;
	/**回本-107天*/
	@Excel(name = "回本-107天", width = 15)
	@ApiModelProperty(value = "回本-107天")
	private BigDecimal day107=BigDecimal.ZERO;
	/**回本-108天*/
	@Excel(name = "回本-108天", width = 15)
	@ApiModelProperty(value = "回本-108天")
	private BigDecimal day108=BigDecimal.ZERO;
	/**回本-109天*/
	@Excel(name = "回本-109天", width = 15)
	@ApiModelProperty(value = "回本-109天")
	private BigDecimal day109=BigDecimal.ZERO;
	/**回本-110天*/
	@Excel(name = "回本-110天", width = 15)
	@ApiModelProperty(value = "回本-110天")
	private BigDecimal day110=BigDecimal.ZERO;
	/**回本-111天*/
	@Excel(name = "回本-111天", width = 15)
	@ApiModelProperty(value = "回本-111天")
	private BigDecimal day111=BigDecimal.ZERO;
	/**回本-112天*/
	@Excel(name = "回本-112天", width = 15)
	@ApiModelProperty(value = "回本-112天")
	private BigDecimal day112=BigDecimal.ZERO;
	/**回本-113天*/
	@Excel(name = "回本-113天", width = 15)
	@ApiModelProperty(value = "回本-113天")
	private BigDecimal day113=BigDecimal.ZERO;
	/**回本-114天*/
	@Excel(name = "回本-114天", width = 15)
	@ApiModelProperty(value = "回本-114天")
	private BigDecimal day114=BigDecimal.ZERO;
	/**回本-115天*/
	@Excel(name = "回本-115天", width = 15)
	@ApiModelProperty(value = "回本-115天")
	private BigDecimal day115=BigDecimal.ZERO;
	/**回本-116天*/
	@Excel(name = "回本-116天", width = 15)
	@ApiModelProperty(value = "回本-116天")
	private BigDecimal day116=BigDecimal.ZERO;
	/**回本-117天*/
	@Excel(name = "回本-117天", width = 15)
	@ApiModelProperty(value = "回本-117天")
	private BigDecimal day117=BigDecimal.ZERO;
	/**回本-118天*/
	@Excel(name = "回本-118天", width = 15)
	@ApiModelProperty(value = "回本-118天")
	private BigDecimal day118=BigDecimal.ZERO;
	/**回本-119天*/
	@Excel(name = "回本-119天", width = 15)
	@ApiModelProperty(value = "回本-119天")
	private BigDecimal day119=BigDecimal.ZERO;
	/**回本-120天*/
	@Excel(name = "回本-120天", width = 15)
	@ApiModelProperty(value = "回本-120天")
	private BigDecimal day120=BigDecimal.ZERO;
	/**回本-121天*/
	@Excel(name = "回本-121天", width = 15)
	@ApiModelProperty(value = "回本-121天")
	private BigDecimal day121=BigDecimal.ZERO;
	/**回本-122天*/
	@Excel(name = "回本-122天", width = 15)
	@ApiModelProperty(value = "回本-122天")
	private BigDecimal day122=BigDecimal.ZERO;
	/**回本-123天*/
	@Excel(name = "回本-123天", width = 15)
	@ApiModelProperty(value = "回本-123天")
	private BigDecimal day123=BigDecimal.ZERO;
	/**回本-124天*/
	@Excel(name = "回本-124天", width = 15)
	@ApiModelProperty(value = "回本-124天")
	private BigDecimal day124=BigDecimal.ZERO;
	/**回本-125天*/
	@Excel(name = "回本-125天", width = 15)
	@ApiModelProperty(value = "回本-125天")
	private BigDecimal day125=BigDecimal.ZERO;
	/**回本-126天*/
	@Excel(name = "回本-126天", width = 15)
	@ApiModelProperty(value = "回本-126天")
	private BigDecimal day126=BigDecimal.ZERO;
	/**回本-127天*/
	@Excel(name = "回本-127天", width = 15)
	@ApiModelProperty(value = "回本-127天")
	private BigDecimal day127=BigDecimal.ZERO;
	/**回本-128天*/
	@Excel(name = "回本-128天", width = 15)
	@ApiModelProperty(value = "回本-128天")
	private BigDecimal day128=BigDecimal.ZERO;
	/**回本-129天*/
	@Excel(name = "回本-129天", width = 15)
	@ApiModelProperty(value = "回本-129天")
	private BigDecimal day129=BigDecimal.ZERO;
	/**回本-130天*/
	@Excel(name = "回本-130天", width = 15)
	@ApiModelProperty(value = "回本-130天")
	private BigDecimal day130=BigDecimal.ZERO;
	/**回本-131天*/
	@Excel(name = "回本-131天", width = 15)
	@ApiModelProperty(value = "回本-131天")
	private BigDecimal day131=BigDecimal.ZERO;
	/**回本-132天*/
	@Excel(name = "回本-132天", width = 15)
	@ApiModelProperty(value = "回本-132天")
	private BigDecimal day132=BigDecimal.ZERO;
	/**回本-133天*/
	@Excel(name = "回本-133天", width = 15)
	@ApiModelProperty(value = "回本-133天")
	private BigDecimal day133=BigDecimal.ZERO;
	/**回本-134天*/
	@Excel(name = "回本-134天", width = 15)
	@ApiModelProperty(value = "回本-134天")
	private BigDecimal day134=BigDecimal.ZERO;
	/**回本-135天*/
	@Excel(name = "回本-135天", width = 15)
	@ApiModelProperty(value = "回本-135天")
	private BigDecimal day135=BigDecimal.ZERO;
	/**回本-136天*/
	@Excel(name = "回本-136天", width = 15)
	@ApiModelProperty(value = "回本-136天")
	private BigDecimal day136=BigDecimal.ZERO;
	/**回本-137天*/
	@Excel(name = "回本-137天", width = 15)
	@ApiModelProperty(value = "回本-137天")
	private BigDecimal day137=BigDecimal.ZERO;
	/**回本-138天*/
	@Excel(name = "回本-138天", width = 15)
	@ApiModelProperty(value = "回本-138天")
	private BigDecimal day138=BigDecimal.ZERO;
	/**回本-139天*/
	@Excel(name = "回本-139天", width = 15)
	@ApiModelProperty(value = "回本-139天")
	private BigDecimal day139=BigDecimal.ZERO;
	/**回本-140天*/
	@Excel(name = "回本-140天", width = 15)
	@ApiModelProperty(value = "回本-140天")
	private BigDecimal day140=BigDecimal.ZERO;
	/**回本-141天*/
	@Excel(name = "回本-141天", width = 15)
	@ApiModelProperty(value = "回本-141天")
	private BigDecimal day141=BigDecimal.ZERO;
	/**回本-142天*/
	@Excel(name = "回本-142天", width = 15)
	@ApiModelProperty(value = "回本-142天")
	private BigDecimal day142=BigDecimal.ZERO;
	/**回本-143天*/
	@Excel(name = "回本-143天", width = 15)
	@ApiModelProperty(value = "回本-143天")
	private BigDecimal day143=BigDecimal.ZERO;
	/**回本-144天*/
	@Excel(name = "回本-144天", width = 15)
	@ApiModelProperty(value = "回本-144天")
	private BigDecimal day144=BigDecimal.ZERO;
	/**回本-145天*/
	@Excel(name = "回本-145天", width = 15)
	@ApiModelProperty(value = "回本-145天")
	private BigDecimal day145=BigDecimal.ZERO;
	/**回本-146天*/
	@Excel(name = "回本-146天", width = 15)
	@ApiModelProperty(value = "回本-146天")
	private BigDecimal day146=BigDecimal.ZERO;
	/**回本-147天*/
	@Excel(name = "回本-147天", width = 15)
	@ApiModelProperty(value = "回本-147天")
	private BigDecimal day147=BigDecimal.ZERO;
	/**回本-148天*/
	@Excel(name = "回本-148天", width = 15)
	@ApiModelProperty(value = "回本-148天")
	private BigDecimal day148=BigDecimal.ZERO;
	/**回本-149天*/
	@Excel(name = "回本-149天", width = 15)
	@ApiModelProperty(value = "回本-149天")
	private BigDecimal day149=BigDecimal.ZERO;
	/**回本-150天*/
	@Excel(name = "回本-150天", width = 15)
	@ApiModelProperty(value = "回本-150天")
	private BigDecimal day150= BigDecimal.ZERO;

}
