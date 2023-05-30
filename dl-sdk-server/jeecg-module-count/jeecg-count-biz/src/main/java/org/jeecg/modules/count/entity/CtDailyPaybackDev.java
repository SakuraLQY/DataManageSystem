package org.jeecg.modules.count.entity;

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
@TableName("ct_daily_payback_dev")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_daily_payback_dev对象", description="ct_daily_payback_dev")
public class CtDailyPaybackDev implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**统计表id*/
	@Excel(name = "统计表id", width = 15)
    @ApiModelProperty(value = "统计表id")
    private java.lang.Integer dailyId;
	/**回本-1天*/
	@Excel(name = "回本-1天", width = 15)
    @ApiModelProperty(value = "回本-1天")
    private java.math.BigDecimal day1;
	/**回本-2天*/
	@Excel(name = "回本-2天", width = 15)
    @ApiModelProperty(value = "回本-2天")
    private java.math.BigDecimal day2;
	/**回本-3天*/
	@Excel(name = "回本-3天", width = 15)
    @ApiModelProperty(value = "回本-3天")
    private java.math.BigDecimal day3;
	/**回本-4天*/
	@Excel(name = "回本-4天", width = 15)
    @ApiModelProperty(value = "回本-4天")
    private java.math.BigDecimal day4;
	/**回本-5天*/
	@Excel(name = "回本-5天", width = 15)
    @ApiModelProperty(value = "回本-5天")
    private java.math.BigDecimal day5;
	/**回本-6天*/
	@Excel(name = "回本-6天", width = 15)
    @ApiModelProperty(value = "回本-6天")
    private java.math.BigDecimal day6;
	/**回本-7天*/
	@Excel(name = "回本-7天", width = 15)
    @ApiModelProperty(value = "回本-7天")
    private java.math.BigDecimal day7= BigDecimal.ZERO;
	/**回本-8天*/
	@Excel(name = "回本-8天", width = 15)
    @ApiModelProperty(value = "回本-8天")
    private java.math.BigDecimal day8;
	/**回本-9天*/
	@Excel(name = "回本-9天", width = 15)
    @ApiModelProperty(value = "回本-9天")
    private java.math.BigDecimal day9;
	/**回本-10天*/
	@Excel(name = "回本-10天", width = 15)
    @ApiModelProperty(value = "回本-10天")
    private java.math.BigDecimal day10;
	/**回本-11天*/
	@Excel(name = "回本-11天", width = 15)
    @ApiModelProperty(value = "回本-11天")
    private java.math.BigDecimal day11;
	/**回本-12天*/
	@Excel(name = "回本-12天", width = 15)
    @ApiModelProperty(value = "回本-12天")
    private java.math.BigDecimal day12;
	/**回本-13天*/
	@Excel(name = "回本-13天", width = 15)
    @ApiModelProperty(value = "回本-13天")
    private java.math.BigDecimal day13;
	/**回本-14天*/
	@Excel(name = "回本-14天", width = 15)
    @ApiModelProperty(value = "回本-14天")
    private java.math.BigDecimal day14;
	/**回本-15天*/
	@Excel(name = "回本-15天", width = 15)
    @ApiModelProperty(value = "回本-15天")
    private java.math.BigDecimal day15;
	/**回本-16天*/
	@Excel(name = "回本-16天", width = 15)
    @ApiModelProperty(value = "回本-16天")
    private java.math.BigDecimal day16;
	/**回本-17天*/
	@Excel(name = "回本-17天", width = 15)
    @ApiModelProperty(value = "回本-17天")
    private java.math.BigDecimal day17;
	/**回本-18天*/
	@Excel(name = "回本-18天", width = 15)
    @ApiModelProperty(value = "回本-18天")
    private java.math.BigDecimal day18;
	/**回本-19天*/
	@Excel(name = "回本-19天", width = 15)
    @ApiModelProperty(value = "回本-19天")
    private java.math.BigDecimal day19;
	/**回本-20天*/
	@Excel(name = "回本-20天", width = 15)
    @ApiModelProperty(value = "回本-20天")
    private java.math.BigDecimal day20;
	/**回本-21天*/
	@Excel(name = "回本-21天", width = 15)
    @ApiModelProperty(value = "回本-21天")
    private java.math.BigDecimal day21;
	/**回本-22天*/
	@Excel(name = "回本-22天", width = 15)
    @ApiModelProperty(value = "回本-22天")
    private java.math.BigDecimal day22;
	/**回本-23天*/
	@Excel(name = "回本-23天", width = 15)
    @ApiModelProperty(value = "回本-23天")
    private java.math.BigDecimal day23;
	/**回本-24天*/
	@Excel(name = "回本-24天", width = 15)
    @ApiModelProperty(value = "回本-24天")
    private java.math.BigDecimal day24;
	/**回本-25天*/
	@Excel(name = "回本-25天", width = 15)
    @ApiModelProperty(value = "回本-25天")
    private java.math.BigDecimal day25;
	/**回本-26天*/
	@Excel(name = "回本-26天", width = 15)
    @ApiModelProperty(value = "回本-26天")
    private java.math.BigDecimal day26;
	/**回本-27天*/
	@Excel(name = "回本-27天", width = 15)
    @ApiModelProperty(value = "回本-27天")
    private java.math.BigDecimal day27;
	/**回本-28天*/
	@Excel(name = "回本-28天", width = 15)
    @ApiModelProperty(value = "回本-28天")
    private java.math.BigDecimal day28;
	/**回本-29天*/
	@Excel(name = "回本-29天", width = 15)
    @ApiModelProperty(value = "回本-29天")
    private java.math.BigDecimal day29;
	/**回本-30天*/
	@Excel(name = "回本-30天", width = 15)
    @ApiModelProperty(value = "回本-30天")
    private java.math.BigDecimal day30;
	/**回本-31天*/
	@Excel(name = "回本-31天", width = 15)
    @ApiModelProperty(value = "回本-31天")
    private java.math.BigDecimal day31;
	/**回本-32天*/
	@Excel(name = "回本-32天", width = 15)
    @ApiModelProperty(value = "回本-32天")
    private java.math.BigDecimal day32;
	/**回本-33天*/
	@Excel(name = "回本-33天", width = 15)
    @ApiModelProperty(value = "回本-33天")
    private java.math.BigDecimal day33;
	/**回本-34天*/
	@Excel(name = "回本-34天", width = 15)
    @ApiModelProperty(value = "回本-34天")
    private java.math.BigDecimal day34;
	/**回本-35天*/
	@Excel(name = "回本-35天", width = 15)
    @ApiModelProperty(value = "回本-35天")
    private java.math.BigDecimal day35;
	/**回本-36天*/
	@Excel(name = "回本-36天", width = 15)
    @ApiModelProperty(value = "回本-36天")
    private java.math.BigDecimal day36;
	/**回本-37天*/
	@Excel(name = "回本-37天", width = 15)
    @ApiModelProperty(value = "回本-37天")
    private java.math.BigDecimal day37;
	/**回本-38天*/
	@Excel(name = "回本-38天", width = 15)
    @ApiModelProperty(value = "回本-38天")
    private java.math.BigDecimal day38;
	/**回本-39天*/
	@Excel(name = "回本-39天", width = 15)
    @ApiModelProperty(value = "回本-39天")
    private java.math.BigDecimal day39;
	/**回本-40天*/
	@Excel(name = "回本-40天", width = 15)
    @ApiModelProperty(value = "回本-40天")
    private java.math.BigDecimal day40;
	/**回本-41天*/
	@Excel(name = "回本-41天", width = 15)
    @ApiModelProperty(value = "回本-41天")
    private java.math.BigDecimal day41;
	/**回本-42天*/
	@Excel(name = "回本-42天", width = 15)
    @ApiModelProperty(value = "回本-42天")
    private java.math.BigDecimal day42;
	/**回本-43天*/
	@Excel(name = "回本-43天", width = 15)
    @ApiModelProperty(value = "回本-43天")
    private java.math.BigDecimal day43;
	/**回本-44天*/
	@Excel(name = "回本-44天", width = 15)
    @ApiModelProperty(value = "回本-44天")
    private java.math.BigDecimal day44;
	/**回本-45天*/
	@Excel(name = "回本-45天", width = 15)
    @ApiModelProperty(value = "回本-45天")
    private java.math.BigDecimal day45;
	/**回本-46天*/
	@Excel(name = "回本-46天", width = 15)
    @ApiModelProperty(value = "回本-46天")
    private java.math.BigDecimal day46;
	/**回本-47天*/
	@Excel(name = "回本-47天", width = 15)
    @ApiModelProperty(value = "回本-47天")
    private java.math.BigDecimal day47;
	/**回本-48天*/
	@Excel(name = "回本-48天", width = 15)
    @ApiModelProperty(value = "回本-48天")
    private java.math.BigDecimal day48;
	/**回本-49天*/
	@Excel(name = "回本-49天", width = 15)
    @ApiModelProperty(value = "回本-49天")
    private java.math.BigDecimal day49;
	/**回本-50天*/
	@Excel(name = "回本-50天", width = 15)
    @ApiModelProperty(value = "回本-50天")
    private java.math.BigDecimal day50;
	/**回本-51天*/
	@Excel(name = "回本-51天", width = 15)
    @ApiModelProperty(value = "回本-51天")
    private java.math.BigDecimal day51;
	/**回本-52天*/
	@Excel(name = "回本-52天", width = 15)
    @ApiModelProperty(value = "回本-52天")
    private java.math.BigDecimal day52;
	/**回本-53天*/
	@Excel(name = "回本-53天", width = 15)
    @ApiModelProperty(value = "回本-53天")
    private java.math.BigDecimal day53;
	/**回本-54天*/
	@Excel(name = "回本-54天", width = 15)
    @ApiModelProperty(value = "回本-54天")
    private java.math.BigDecimal day54;
	/**回本-55天*/
	@Excel(name = "回本-55天", width = 15)
    @ApiModelProperty(value = "回本-55天")
    private java.math.BigDecimal day55;
	/**回本-56天*/
	@Excel(name = "回本-56天", width = 15)
    @ApiModelProperty(value = "回本-56天")
    private java.math.BigDecimal day56;
	/**回本-57天*/
	@Excel(name = "回本-57天", width = 15)
    @ApiModelProperty(value = "回本-57天")
    private java.math.BigDecimal day57;
	/**回本-58天*/
	@Excel(name = "回本-58天", width = 15)
    @ApiModelProperty(value = "回本-58天")
    private java.math.BigDecimal day58;
	/**回本-59天*/
	@Excel(name = "回本-59天", width = 15)
    @ApiModelProperty(value = "回本-59天")
    private java.math.BigDecimal day59;
	/**回本-60天*/
	@Excel(name = "回本-60天", width = 15)
    @ApiModelProperty(value = "回本-60天")
    private java.math.BigDecimal day60;
	/**回本-61天*/
	@Excel(name = "回本-61天", width = 15)
    @ApiModelProperty(value = "回本-61天")
    private java.math.BigDecimal day61;
	/**回本-62天*/
	@Excel(name = "回本-62天", width = 15)
    @ApiModelProperty(value = "回本-62天")
    private java.math.BigDecimal day62;
	/**回本-63天*/
	@Excel(name = "回本-63天", width = 15)
    @ApiModelProperty(value = "回本-63天")
    private java.math.BigDecimal day63;
	/**回本-64天*/
	@Excel(name = "回本-64天", width = 15)
    @ApiModelProperty(value = "回本-64天")
    private java.math.BigDecimal day64;
	/**回本-65天*/
	@Excel(name = "回本-65天", width = 15)
    @ApiModelProperty(value = "回本-65天")
    private java.math.BigDecimal day65;
	/**回本-66天*/
	@Excel(name = "回本-66天", width = 15)
    @ApiModelProperty(value = "回本-66天")
    private java.math.BigDecimal day66;
	/**回本-67天*/
	@Excel(name = "回本-67天", width = 15)
    @ApiModelProperty(value = "回本-67天")
    private java.math.BigDecimal day67;
	/**回本-68天*/
	@Excel(name = "回本-68天", width = 15)
    @ApiModelProperty(value = "回本-68天")
    private java.math.BigDecimal day68;
	/**回本-69天*/
	@Excel(name = "回本-69天", width = 15)
    @ApiModelProperty(value = "回本-69天")
    private java.math.BigDecimal day69;
	/**回本-70天*/
	@Excel(name = "回本-70天", width = 15)
    @ApiModelProperty(value = "回本-70天")
    private java.math.BigDecimal day70;
	/**回本-71天*/
	@Excel(name = "回本-71天", width = 15)
    @ApiModelProperty(value = "回本-71天")
    private java.math.BigDecimal day71;
	/**回本-72天*/
	@Excel(name = "回本-72天", width = 15)
    @ApiModelProperty(value = "回本-72天")
    private java.math.BigDecimal day72;
	/**回本-73天*/
	@Excel(name = "回本-73天", width = 15)
    @ApiModelProperty(value = "回本-73天")
    private java.math.BigDecimal day73;
	/**回本-74天*/
	@Excel(name = "回本-74天", width = 15)
    @ApiModelProperty(value = "回本-74天")
    private java.math.BigDecimal day74;
	/**回本-75天*/
	@Excel(name = "回本-75天", width = 15)
    @ApiModelProperty(value = "回本-75天")
    private java.math.BigDecimal day75;
	/**回本-76天*/
	@Excel(name = "回本-76天", width = 15)
    @ApiModelProperty(value = "回本-76天")
    private java.math.BigDecimal day76;
	/**回本-77天*/
	@Excel(name = "回本-77天", width = 15)
    @ApiModelProperty(value = "回本-77天")
    private java.math.BigDecimal day77;
	/**回本-78天*/
	@Excel(name = "回本-78天", width = 15)
    @ApiModelProperty(value = "回本-78天")
    private java.math.BigDecimal day78;
	/**回本-79天*/
	@Excel(name = "回本-79天", width = 15)
    @ApiModelProperty(value = "回本-79天")
    private java.math.BigDecimal day79;
	/**回本-80天*/
	@Excel(name = "回本-80天", width = 15)
    @ApiModelProperty(value = "回本-80天")
    private java.math.BigDecimal day80;
	/**回本-81天*/
	@Excel(name = "回本-81天", width = 15)
    @ApiModelProperty(value = "回本-81天")
    private java.math.BigDecimal day81;
	/**回本-82天*/
	@Excel(name = "回本-82天", width = 15)
    @ApiModelProperty(value = "回本-82天")
    private java.math.BigDecimal day82;
	/**回本-83天*/
	@Excel(name = "回本-83天", width = 15)
    @ApiModelProperty(value = "回本-83天")
    private java.math.BigDecimal day83;
	/**回本-84天*/
	@Excel(name = "回本-84天", width = 15)
    @ApiModelProperty(value = "回本-84天")
    private java.math.BigDecimal day84;
	/**回本-85天*/
	@Excel(name = "回本-85天", width = 15)
    @ApiModelProperty(value = "回本-85天")
    private java.math.BigDecimal day85;
	/**回本-86天*/
	@Excel(name = "回本-86天", width = 15)
    @ApiModelProperty(value = "回本-86天")
    private java.math.BigDecimal day86;
	/**回本-87天*/
	@Excel(name = "回本-87天", width = 15)
    @ApiModelProperty(value = "回本-87天")
    private java.math.BigDecimal day87;
	/**回本-88天*/
	@Excel(name = "回本-88天", width = 15)
    @ApiModelProperty(value = "回本-88天")
    private java.math.BigDecimal day88;
	/**回本-89天*/
	@Excel(name = "回本-89天", width = 15)
    @ApiModelProperty(value = "回本-89天")
    private java.math.BigDecimal day89;
	/**回本-90天*/
	@Excel(name = "回本-90天", width = 15)
    @ApiModelProperty(value = "回本-90天")
    private java.math.BigDecimal day90;
	/**回本-91天*/
	@Excel(name = "回本-91天", width = 15)
    @ApiModelProperty(value = "回本-91天")
    private java.math.BigDecimal day91;
	/**回本-92天*/
	@Excel(name = "回本-92天", width = 15)
    @ApiModelProperty(value = "回本-92天")
    private java.math.BigDecimal day92;
	/**回本-93天*/
	@Excel(name = "回本-93天", width = 15)
    @ApiModelProperty(value = "回本-93天")
    private java.math.BigDecimal day93;
	/**回本-94天*/
	@Excel(name = "回本-94天", width = 15)
    @ApiModelProperty(value = "回本-94天")
    private java.math.BigDecimal day94;
	/**回本-95天*/
	@Excel(name = "回本-95天", width = 15)
    @ApiModelProperty(value = "回本-95天")
    private java.math.BigDecimal day95;
	/**回本-96天*/
	@Excel(name = "回本-96天", width = 15)
    @ApiModelProperty(value = "回本-96天")
    private java.math.BigDecimal day96;
	/**回本-97天*/
	@Excel(name = "回本-97天", width = 15)
    @ApiModelProperty(value = "回本-97天")
    private java.math.BigDecimal day97;
	/**回本-98天*/
	@Excel(name = "回本-98天", width = 15)
    @ApiModelProperty(value = "回本-98天")
    private java.math.BigDecimal day98;
	/**回本-99天*/
	@Excel(name = "回本-99天", width = 15)
    @ApiModelProperty(value = "回本-99天")
    private java.math.BigDecimal day99;
	/**回本-100天*/
	@Excel(name = "回本-100天", width = 15)
    @ApiModelProperty(value = "回本-100天")
    private java.math.BigDecimal day100;
	/**回本-101天*/
	@Excel(name = "回本-101天", width = 15)
    @ApiModelProperty(value = "回本-101天")
    private java.math.BigDecimal day101;
	/**回本-102天*/
	@Excel(name = "回本-102天", width = 15)
    @ApiModelProperty(value = "回本-102天")
    private java.math.BigDecimal day102;
	/**回本-103天*/
	@Excel(name = "回本-103天", width = 15)
    @ApiModelProperty(value = "回本-103天")
    private java.math.BigDecimal day103;
	/**回本-104天*/
	@Excel(name = "回本-104天", width = 15)
    @ApiModelProperty(value = "回本-104天")
    private java.math.BigDecimal day104;
	/**回本-105天*/
	@Excel(name = "回本-105天", width = 15)
    @ApiModelProperty(value = "回本-105天")
    private java.math.BigDecimal day105;
	/**回本-106天*/
	@Excel(name = "回本-106天", width = 15)
    @ApiModelProperty(value = "回本-106天")
    private java.math.BigDecimal day106;
	/**回本-107天*/
	@Excel(name = "回本-107天", width = 15)
    @ApiModelProperty(value = "回本-107天")
    private java.math.BigDecimal day107;
	/**回本-108天*/
	@Excel(name = "回本-108天", width = 15)
    @ApiModelProperty(value = "回本-108天")
    private java.math.BigDecimal day108;
	/**回本-109天*/
	@Excel(name = "回本-109天", width = 15)
    @ApiModelProperty(value = "回本-109天")
    private java.math.BigDecimal day109;
	/**回本-110天*/
	@Excel(name = "回本-110天", width = 15)
    @ApiModelProperty(value = "回本-110天")
    private java.math.BigDecimal day110;
	/**回本-111天*/
	@Excel(name = "回本-111天", width = 15)
    @ApiModelProperty(value = "回本-111天")
    private java.math.BigDecimal day111;
	/**回本-112天*/
	@Excel(name = "回本-112天", width = 15)
    @ApiModelProperty(value = "回本-112天")
    private java.math.BigDecimal day112;
	/**回本-113天*/
	@Excel(name = "回本-113天", width = 15)
    @ApiModelProperty(value = "回本-113天")
    private java.math.BigDecimal day113;
	/**回本-114天*/
	@Excel(name = "回本-114天", width = 15)
    @ApiModelProperty(value = "回本-114天")
    private java.math.BigDecimal day114;
	/**回本-115天*/
	@Excel(name = "回本-115天", width = 15)
    @ApiModelProperty(value = "回本-115天")
    private java.math.BigDecimal day115;
	/**回本-116天*/
	@Excel(name = "回本-116天", width = 15)
    @ApiModelProperty(value = "回本-116天")
    private java.math.BigDecimal day116;
	/**回本-117天*/
	@Excel(name = "回本-117天", width = 15)
    @ApiModelProperty(value = "回本-117天")
    private java.math.BigDecimal day117;
	/**回本-118天*/
	@Excel(name = "回本-118天", width = 15)
    @ApiModelProperty(value = "回本-118天")
    private java.math.BigDecimal day118;
	/**回本-119天*/
	@Excel(name = "回本-119天", width = 15)
    @ApiModelProperty(value = "回本-119天")
    private java.math.BigDecimal day119;
	/**回本-120天*/
	@Excel(name = "回本-120天", width = 15)
    @ApiModelProperty(value = "回本-120天")
    private java.math.BigDecimal day120;
	/**回本-121天*/
	@Excel(name = "回本-121天", width = 15)
    @ApiModelProperty(value = "回本-121天")
    private java.math.BigDecimal day121;
	/**回本-122天*/
	@Excel(name = "回本-122天", width = 15)
    @ApiModelProperty(value = "回本-122天")
    private java.math.BigDecimal day122;
	/**回本-123天*/
	@Excel(name = "回本-123天", width = 15)
    @ApiModelProperty(value = "回本-123天")
    private java.math.BigDecimal day123;
	/**回本-124天*/
	@Excel(name = "回本-124天", width = 15)
    @ApiModelProperty(value = "回本-124天")
    private java.math.BigDecimal day124;
	/**回本-125天*/
	@Excel(name = "回本-125天", width = 15)
    @ApiModelProperty(value = "回本-125天")
    private java.math.BigDecimal day125;
	/**回本-126天*/
	@Excel(name = "回本-126天", width = 15)
    @ApiModelProperty(value = "回本-126天")
    private java.math.BigDecimal day126;
	/**回本-127天*/
	@Excel(name = "回本-127天", width = 15)
    @ApiModelProperty(value = "回本-127天")
    private java.math.BigDecimal day127;
	/**回本-128天*/
	@Excel(name = "回本-128天", width = 15)
    @ApiModelProperty(value = "回本-128天")
    private java.math.BigDecimal day128;
	/**回本-129天*/
	@Excel(name = "回本-129天", width = 15)
    @ApiModelProperty(value = "回本-129天")
    private java.math.BigDecimal day129;
	/**回本-130天*/
	@Excel(name = "回本-130天", width = 15)
    @ApiModelProperty(value = "回本-130天")
    private java.math.BigDecimal day130;
	/**回本-131天*/
	@Excel(name = "回本-131天", width = 15)
    @ApiModelProperty(value = "回本-131天")
    private java.math.BigDecimal day131;
	/**回本-132天*/
	@Excel(name = "回本-132天", width = 15)
    @ApiModelProperty(value = "回本-132天")
    private java.math.BigDecimal day132;
	/**回本-133天*/
	@Excel(name = "回本-133天", width = 15)
    @ApiModelProperty(value = "回本-133天")
    private java.math.BigDecimal day133;
	/**回本-134天*/
	@Excel(name = "回本-134天", width = 15)
    @ApiModelProperty(value = "回本-134天")
    private java.math.BigDecimal day134;
	/**回本-135天*/
	@Excel(name = "回本-135天", width = 15)
    @ApiModelProperty(value = "回本-135天")
    private java.math.BigDecimal day135;
	/**回本-136天*/
	@Excel(name = "回本-136天", width = 15)
    @ApiModelProperty(value = "回本-136天")
    private java.math.BigDecimal day136;
	/**回本-137天*/
	@Excel(name = "回本-137天", width = 15)
    @ApiModelProperty(value = "回本-137天")
    private java.math.BigDecimal day137;
	/**回本-138天*/
	@Excel(name = "回本-138天", width = 15)
    @ApiModelProperty(value = "回本-138天")
    private java.math.BigDecimal day138;
	/**回本-139天*/
	@Excel(name = "回本-139天", width = 15)
    @ApiModelProperty(value = "回本-139天")
    private java.math.BigDecimal day139;
	/**回本-140天*/
	@Excel(name = "回本-140天", width = 15)
    @ApiModelProperty(value = "回本-140天")
    private java.math.BigDecimal day140;
	/**回本-141天*/
	@Excel(name = "回本-141天", width = 15)
    @ApiModelProperty(value = "回本-141天")
    private java.math.BigDecimal day141;
	/**回本-142天*/
	@Excel(name = "回本-142天", width = 15)
    @ApiModelProperty(value = "回本-142天")
    private java.math.BigDecimal day142;
	/**回本-143天*/
	@Excel(name = "回本-143天", width = 15)
    @ApiModelProperty(value = "回本-143天")
    private java.math.BigDecimal day143;
	/**回本-144天*/
	@Excel(name = "回本-144天", width = 15)
    @ApiModelProperty(value = "回本-144天")
    private java.math.BigDecimal day144;
	/**回本-145天*/
	@Excel(name = "回本-145天", width = 15)
    @ApiModelProperty(value = "回本-145天")
    private java.math.BigDecimal day145;
	/**回本-146天*/
	@Excel(name = "回本-146天", width = 15)
    @ApiModelProperty(value = "回本-146天")
    private java.math.BigDecimal day146;
	/**回本-147天*/
	@Excel(name = "回本-147天", width = 15)
    @ApiModelProperty(value = "回本-147天")
    private java.math.BigDecimal day147;
	/**回本-148天*/
	@Excel(name = "回本-148天", width = 15)
    @ApiModelProperty(value = "回本-148天")
    private java.math.BigDecimal day148;
	/**回本-149天*/
	@Excel(name = "回本-149天", width = 15)
    @ApiModelProperty(value = "回本-149天")
    private java.math.BigDecimal day149;
	/**回本-150天*/
	@Excel(name = "回本-150天", width = 15)
    @ApiModelProperty(value = "回本-150天")
    private java.math.BigDecimal day150= BigDecimal.ZERO;
	/**时间-创建*/
	@Excel(name = "时间-创建", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间-创建")
    private java.util.Date createTime;
	/**时间-更新*/
	@Excel(name = "时间-更新", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间-更新")
    private java.util.Date updateTime;
}
