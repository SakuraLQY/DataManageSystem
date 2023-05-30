package org.jeecg.modules.count.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_daily_loyal_dev
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@TableName("ct_daily_loyal_dev")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_daily_loyal_dev对象", description="ct_daily_loyal_dev")
public class CtDailyLoyalDev implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**统计表id*/
	@Excel(name = "统计表id", width = 15)
    @ApiModelProperty(value = "统计表id")
    private java.lang.Integer dailyId;
	/**留存-1天*/
	@Excel(name = "留存-1天", width = 15)
    @ApiModelProperty(value = "留存-1天")
    private java.lang.Integer day1;
	/**留存-2天*/
	@Excel(name = "留存-2天", width = 15)
    @ApiModelProperty(value = "留存-2天")
    private java.lang.Integer day2;
	/**留存-3天*/
	@Excel(name = "留存-3天", width = 15)
    @ApiModelProperty(value = "留存-3天")
    private java.lang.Integer day3;
	/**留存-4天*/
	@Excel(name = "留存-4天", width = 15)
    @ApiModelProperty(value = "留存-4天")
    private java.lang.Integer day4;
	/**留存-5天*/
	@Excel(name = "留存-5天", width = 15)
    @ApiModelProperty(value = "留存-5天")
    private java.lang.Integer day5;
	/**留存-6天*/
	@Excel(name = "留存-6天", width = 15)
    @ApiModelProperty(value = "留存-6天")
    private java.lang.Integer day6;
	/**留存-7天*/
	@Excel(name = "留存-7天", width = 15)
    @ApiModelProperty(value = "留存-7天")
    private java.lang.Integer day7;
	/**留存-8天*/
	@Excel(name = "留存-8天", width = 15)
    @ApiModelProperty(value = "留存-8天")
    private java.lang.Integer day8;
	/**留存-9天*/
	@Excel(name = "留存-9天", width = 15)
    @ApiModelProperty(value = "留存-9天")
    private java.lang.Integer day9;
	/**留存-10天*/
	@Excel(name = "留存-10天", width = 15)
    @ApiModelProperty(value = "留存-10天")
    private java.lang.Integer day10;
	/**留存-11天*/
	@Excel(name = "留存-11天", width = 15)
    @ApiModelProperty(value = "留存-11天")
    private java.lang.Integer day11;
	/**留存-12天*/
	@Excel(name = "留存-12天", width = 15)
    @ApiModelProperty(value = "留存-12天")
    private java.lang.Integer day12;
	/**留存-13天*/
	@Excel(name = "留存-13天", width = 15)
    @ApiModelProperty(value = "留存-13天")
    private java.lang.Integer day13;
	/**留存-14天*/
	@Excel(name = "留存-14天", width = 15)
    @ApiModelProperty(value = "留存-14天")
    private java.lang.Integer day14;
	/**留存-15天*/
	@Excel(name = "留存-15天", width = 15)
    @ApiModelProperty(value = "留存-15天")
    private java.lang.Integer day15;
	/**留存-16天*/
	@Excel(name = "留存-16天", width = 15)
    @ApiModelProperty(value = "留存-16天")
    private java.lang.Integer day16;
	/**留存-17天*/
	@Excel(name = "留存-17天", width = 15)
    @ApiModelProperty(value = "留存-17天")
    private java.lang.Integer day17;
	/**留存-18天*/
	@Excel(name = "留存-18天", width = 15)
    @ApiModelProperty(value = "留存-18天")
    private java.lang.Integer day18;
	/**留存-19天*/
	@Excel(name = "留存-19天", width = 15)
    @ApiModelProperty(value = "留存-19天")
    private java.lang.Integer day19;
	/**留存-20天*/
	@Excel(name = "留存-20天", width = 15)
    @ApiModelProperty(value = "留存-20天")
    private java.lang.Integer day20;
	/**留存-21天*/
	@Excel(name = "留存-21天", width = 15)
    @ApiModelProperty(value = "留存-21天")
    private java.lang.Integer day21;
	/**留存-22天*/
	@Excel(name = "留存-22天", width = 15)
    @ApiModelProperty(value = "留存-22天")
    private java.lang.Integer day22;
	/**留存-23天*/
	@Excel(name = "留存-23天", width = 15)
    @ApiModelProperty(value = "留存-23天")
    private java.lang.Integer day23;
	/**留存-24天*/
	@Excel(name = "留存-24天", width = 15)
    @ApiModelProperty(value = "留存-24天")
    private java.lang.Integer day24;
	/**留存-25天*/
	@Excel(name = "留存-25天", width = 15)
    @ApiModelProperty(value = "留存-25天")
    private java.lang.Integer day25;
	/**留存-26天*/
	@Excel(name = "留存-26天", width = 15)
    @ApiModelProperty(value = "留存-26天")
    private java.lang.Integer day26;
	/**留存-27天*/
	@Excel(name = "留存-27天", width = 15)
    @ApiModelProperty(value = "留存-27天")
    private java.lang.Integer day27;
	/**留存-28天*/
	@Excel(name = "留存-28天", width = 15)
    @ApiModelProperty(value = "留存-28天")
    private java.lang.Integer day28;
	/**留存-29天*/
	@Excel(name = "留存-29天", width = 15)
    @ApiModelProperty(value = "留存-29天")
    private java.lang.Integer day29;
	/**留存-30天*/
	@Excel(name = "留存-30天", width = 15)
    @ApiModelProperty(value = "留存-30天")
    private java.lang.Integer day30;
	/**留存-31天*/
	@Excel(name = "留存-31天", width = 15)
    @ApiModelProperty(value = "留存-31天")
    private java.lang.Integer day31;
	/**留存-32天*/
	@Excel(name = "留存-32天", width = 15)
    @ApiModelProperty(value = "留存-32天")
    private java.lang.Integer day32;
	/**留存-33天*/
	@Excel(name = "留存-33天", width = 15)
    @ApiModelProperty(value = "留存-33天")
    private java.lang.Integer day33;
	/**留存-34天*/
	@Excel(name = "留存-34天", width = 15)
    @ApiModelProperty(value = "留存-34天")
    private java.lang.Integer day34;
	/**留存-35天*/
	@Excel(name = "留存-35天", width = 15)
    @ApiModelProperty(value = "留存-35天")
    private java.lang.Integer day35;
	/**留存-36天*/
	@Excel(name = "留存-36天", width = 15)
    @ApiModelProperty(value = "留存-36天")
    private java.lang.Integer day36;
	/**留存-37天*/
	@Excel(name = "留存-37天", width = 15)
    @ApiModelProperty(value = "留存-37天")
    private java.lang.Integer day37;
	/**留存-38天*/
	@Excel(name = "留存-38天", width = 15)
    @ApiModelProperty(value = "留存-38天")
    private java.lang.Integer day38;
	/**留存-39天*/
	@Excel(name = "留存-39天", width = 15)
    @ApiModelProperty(value = "留存-39天")
    private java.lang.Integer day39;
	/**留存-40天*/
	@Excel(name = "留存-40天", width = 15)
    @ApiModelProperty(value = "留存-40天")
    private java.lang.Integer day40;
	/**留存-41天*/
	@Excel(name = "留存-41天", width = 15)
    @ApiModelProperty(value = "留存-41天")
    private java.lang.Integer day41;
	/**留存-42天*/
	@Excel(name = "留存-42天", width = 15)
    @ApiModelProperty(value = "留存-42天")
    private java.lang.Integer day42;
	/**留存-43天*/
	@Excel(name = "留存-43天", width = 15)
    @ApiModelProperty(value = "留存-43天")
    private java.lang.Integer day43;
	/**留存-44天*/
	@Excel(name = "留存-44天", width = 15)
    @ApiModelProperty(value = "留存-44天")
    private java.lang.Integer day44;
	/**留存-45天*/
	@Excel(name = "留存-45天", width = 15)
    @ApiModelProperty(value = "留存-45天")
    private java.lang.Integer day45;
	/**留存-46天*/
	@Excel(name = "留存-46天", width = 15)
    @ApiModelProperty(value = "留存-46天")
    private java.lang.Integer day46;
	/**留存-47天*/
	@Excel(name = "留存-47天", width = 15)
    @ApiModelProperty(value = "留存-47天")
    private java.lang.Integer day47;
	/**留存-48天*/
	@Excel(name = "留存-48天", width = 15)
    @ApiModelProperty(value = "留存-48天")
    private java.lang.Integer day48;
	/**留存-49天*/
	@Excel(name = "留存-49天", width = 15)
    @ApiModelProperty(value = "留存-49天")
    private java.lang.Integer day49;
	/**留存-50天*/
	@Excel(name = "留存-50天", width = 15)
    @ApiModelProperty(value = "留存-50天")
    private java.lang.Integer day50;
	/**留存-51天*/
	@Excel(name = "留存-51天", width = 15)
    @ApiModelProperty(value = "留存-51天")
    private java.lang.Integer day51;
	/**留存-52天*/
	@Excel(name = "留存-52天", width = 15)
    @ApiModelProperty(value = "留存-52天")
    private java.lang.Integer day52;
	/**留存-53天*/
	@Excel(name = "留存-53天", width = 15)
    @ApiModelProperty(value = "留存-53天")
    private java.lang.Integer day53;
	/**留存-54天*/
	@Excel(name = "留存-54天", width = 15)
    @ApiModelProperty(value = "留存-54天")
    private java.lang.Integer day54;
	/**留存-55天*/
	@Excel(name = "留存-55天", width = 15)
    @ApiModelProperty(value = "留存-55天")
    private java.lang.Integer day55;
	/**留存-56天*/
	@Excel(name = "留存-56天", width = 15)
    @ApiModelProperty(value = "留存-56天")
    private java.lang.Integer day56;
	/**留存-57天*/
	@Excel(name = "留存-57天", width = 15)
    @ApiModelProperty(value = "留存-57天")
    private java.lang.Integer day57;
	/**留存-58天*/
	@Excel(name = "留存-58天", width = 15)
    @ApiModelProperty(value = "留存-58天")
    private java.lang.Integer day58;
	/**留存-59天*/
	@Excel(name = "留存-59天", width = 15)
    @ApiModelProperty(value = "留存-59天")
    private java.lang.Integer day59;
	/**留存-60天*/
	@Excel(name = "留存-60天", width = 15)
    @ApiModelProperty(value = "留存-60天")
    private java.lang.Integer day60;
	/**留存-61天*/
	@Excel(name = "留存-61天", width = 15)
    @ApiModelProperty(value = "留存-61天")
    private java.lang.Integer day61;
	/**留存-62天*/
	@Excel(name = "留存-62天", width = 15)
    @ApiModelProperty(value = "留存-62天")
    private java.lang.Integer day62;
	/**留存-63天*/
	@Excel(name = "留存-63天", width = 15)
    @ApiModelProperty(value = "留存-63天")
    private java.lang.Integer day63;
	/**留存-64天*/
	@Excel(name = "留存-64天", width = 15)
    @ApiModelProperty(value = "留存-64天")
    private java.lang.Integer day64;
	/**留存-65天*/
	@Excel(name = "留存-65天", width = 15)
    @ApiModelProperty(value = "留存-65天")
    private java.lang.Integer day65;
	/**留存-66天*/
	@Excel(name = "留存-66天", width = 15)
    @ApiModelProperty(value = "留存-66天")
    private java.lang.Integer day66;
	/**留存-67天*/
	@Excel(name = "留存-67天", width = 15)
    @ApiModelProperty(value = "留存-67天")
    private java.lang.Integer day67;
	/**留存-68天*/
	@Excel(name = "留存-68天", width = 15)
    @ApiModelProperty(value = "留存-68天")
    private java.lang.Integer day68;
	/**留存-69天*/
	@Excel(name = "留存-69天", width = 15)
    @ApiModelProperty(value = "留存-69天")
    private java.lang.Integer day69;
	/**留存-70天*/
	@Excel(name = "留存-70天", width = 15)
    @ApiModelProperty(value = "留存-70天")
    private java.lang.Integer day70;
	/**留存-71天*/
	@Excel(name = "留存-71天", width = 15)
    @ApiModelProperty(value = "留存-71天")
    private java.lang.Integer day71;
	/**留存-72天*/
	@Excel(name = "留存-72天", width = 15)
    @ApiModelProperty(value = "留存-72天")
    private java.lang.Integer day72;
	/**留存-73天*/
	@Excel(name = "留存-73天", width = 15)
    @ApiModelProperty(value = "留存-73天")
    private java.lang.Integer day73;
	/**留存-74天*/
	@Excel(name = "留存-74天", width = 15)
    @ApiModelProperty(value = "留存-74天")
    private java.lang.Integer day74;
	/**留存-75天*/
	@Excel(name = "留存-75天", width = 15)
    @ApiModelProperty(value = "留存-75天")
    private java.lang.Integer day75;
	/**留存-76天*/
	@Excel(name = "留存-76天", width = 15)
    @ApiModelProperty(value = "留存-76天")
    private java.lang.Integer day76;
	/**留存-77天*/
	@Excel(name = "留存-77天", width = 15)
    @ApiModelProperty(value = "留存-77天")
    private java.lang.Integer day77;
	/**留存-78天*/
	@Excel(name = "留存-78天", width = 15)
    @ApiModelProperty(value = "留存-78天")
    private java.lang.Integer day78;
	/**留存-79天*/
	@Excel(name = "留存-79天", width = 15)
    @ApiModelProperty(value = "留存-79天")
    private java.lang.Integer day79;
	/**留存-80天*/
	@Excel(name = "留存-80天", width = 15)
    @ApiModelProperty(value = "留存-80天")
    private java.lang.Integer day80;
	/**留存-81天*/
	@Excel(name = "留存-81天", width = 15)
    @ApiModelProperty(value = "留存-81天")
    private java.lang.Integer day81;
	/**留存-82天*/
	@Excel(name = "留存-82天", width = 15)
    @ApiModelProperty(value = "留存-82天")
    private java.lang.Integer day82;
	/**留存-83天*/
	@Excel(name = "留存-83天", width = 15)
    @ApiModelProperty(value = "留存-83天")
    private java.lang.Integer day83;
	/**留存-84天*/
	@Excel(name = "留存-84天", width = 15)
    @ApiModelProperty(value = "留存-84天")
    private java.lang.Integer day84;
	/**留存-85天*/
	@Excel(name = "留存-85天", width = 15)
    @ApiModelProperty(value = "留存-85天")
    private java.lang.Integer day85;
	/**留存-86天*/
	@Excel(name = "留存-86天", width = 15)
    @ApiModelProperty(value = "留存-86天")
    private java.lang.Integer day86;
	/**留存-87天*/
	@Excel(name = "留存-87天", width = 15)
    @ApiModelProperty(value = "留存-87天")
    private java.lang.Integer day87;
	/**留存-88天*/
	@Excel(name = "留存-88天", width = 15)
    @ApiModelProperty(value = "留存-88天")
    private java.lang.Integer day88;
	/**留存-89天*/
	@Excel(name = "留存-89天", width = 15)
    @ApiModelProperty(value = "留存-89天")
    private java.lang.Integer day89;
	/**留存-90天*/
	@Excel(name = "留存-90天", width = 15)
    @ApiModelProperty(value = "留存-90天")
    private java.lang.Integer day90;
	/**留存-91天*/
	@Excel(name = "留存-91天", width = 15)
    @ApiModelProperty(value = "留存-91天")
    private java.lang.Integer day91;
	/**留存-92天*/
	@Excel(name = "留存-92天", width = 15)
    @ApiModelProperty(value = "留存-92天")
    private java.lang.Integer day92;
	/**留存-93天*/
	@Excel(name = "留存-93天", width = 15)
    @ApiModelProperty(value = "留存-93天")
    private java.lang.Integer day93;
	/**留存-94天*/
	@Excel(name = "留存-94天", width = 15)
    @ApiModelProperty(value = "留存-94天")
    private java.lang.Integer day94;
	/**留存-95天*/
	@Excel(name = "留存-95天", width = 15)
    @ApiModelProperty(value = "留存-95天")
    private java.lang.Integer day95;
	/**留存-96天*/
	@Excel(name = "留存-96天", width = 15)
    @ApiModelProperty(value = "留存-96天")
    private java.lang.Integer day96;
	/**留存-97天*/
	@Excel(name = "留存-97天", width = 15)
    @ApiModelProperty(value = "留存-97天")
    private java.lang.Integer day97;
	/**留存-98天*/
	@Excel(name = "留存-98天", width = 15)
    @ApiModelProperty(value = "留存-98天")
    private java.lang.Integer day98;
	/**留存-99天*/
	@Excel(name = "留存-99天", width = 15)
    @ApiModelProperty(value = "留存-99天")
    private java.lang.Integer day99;
	/**留存-100天*/
	@Excel(name = "留存-100天", width = 15)
    @ApiModelProperty(value = "留存-100天")
    private java.lang.Integer day100;
	/**留存-101天*/
	@Excel(name = "留存-101天", width = 15)
    @ApiModelProperty(value = "留存-101天")
    private java.lang.Integer day101;
	/**留存-102天*/
	@Excel(name = "留存-102天", width = 15)
    @ApiModelProperty(value = "留存-102天")
    private java.lang.Integer day102;
	/**留存-103天*/
	@Excel(name = "留存-103天", width = 15)
    @ApiModelProperty(value = "留存-103天")
    private java.lang.Integer day103;
	/**留存-104天*/
	@Excel(name = "留存-104天", width = 15)
    @ApiModelProperty(value = "留存-104天")
    private java.lang.Integer day104;
	/**留存-105天*/
	@Excel(name = "留存-105天", width = 15)
    @ApiModelProperty(value = "留存-105天")
    private java.lang.Integer day105;
	/**留存-106天*/
	@Excel(name = "留存-106天", width = 15)
    @ApiModelProperty(value = "留存-106天")
    private java.lang.Integer day106;
	/**留存-107天*/
	@Excel(name = "留存-107天", width = 15)
    @ApiModelProperty(value = "留存-107天")
    private java.lang.Integer day107;
	/**留存-108天*/
	@Excel(name = "留存-108天", width = 15)
    @ApiModelProperty(value = "留存-108天")
    private java.lang.Integer day108;
	/**留存-109天*/
	@Excel(name = "留存-109天", width = 15)
    @ApiModelProperty(value = "留存-109天")
    private java.lang.Integer day109;
	/**留存-110天*/
	@Excel(name = "留存-110天", width = 15)
    @ApiModelProperty(value = "留存-110天")
    private java.lang.Integer day110;
	/**留存-111天*/
	@Excel(name = "留存-111天", width = 15)
    @ApiModelProperty(value = "留存-111天")
    private java.lang.Integer day111;
	/**留存-112天*/
	@Excel(name = "留存-112天", width = 15)
    @ApiModelProperty(value = "留存-112天")
    private java.lang.Integer day112;
	/**留存-113天*/
	@Excel(name = "留存-113天", width = 15)
    @ApiModelProperty(value = "留存-113天")
    private java.lang.Integer day113;
	/**留存-114天*/
	@Excel(name = "留存-114天", width = 15)
    @ApiModelProperty(value = "留存-114天")
    private java.lang.Integer day114;
	/**留存-115天*/
	@Excel(name = "留存-115天", width = 15)
    @ApiModelProperty(value = "留存-115天")
    private java.lang.Integer day115;
	/**留存-116天*/
	@Excel(name = "留存-116天", width = 15)
    @ApiModelProperty(value = "留存-116天")
    private java.lang.Integer day116;
	/**留存-117天*/
	@Excel(name = "留存-117天", width = 15)
    @ApiModelProperty(value = "留存-117天")
    private java.lang.Integer day117;
	/**留存-118天*/
	@Excel(name = "留存-118天", width = 15)
    @ApiModelProperty(value = "留存-118天")
    private java.lang.Integer day118;
	/**留存-119天*/
	@Excel(name = "留存-119天", width = 15)
    @ApiModelProperty(value = "留存-119天")
    private java.lang.Integer day119;
	/**留存-120天*/
	@Excel(name = "留存-120天", width = 15)
    @ApiModelProperty(value = "留存-120天")
    private java.lang.Integer day120;
	/**留存-121天*/
	@Excel(name = "留存-121天", width = 15)
    @ApiModelProperty(value = "留存-121天")
    private java.lang.Integer day121;
	/**留存-122天*/
	@Excel(name = "留存-122天", width = 15)
    @ApiModelProperty(value = "留存-122天")
    private java.lang.Integer day122;
	/**留存-123天*/
	@Excel(name = "留存-123天", width = 15)
    @ApiModelProperty(value = "留存-123天")
    private java.lang.Integer day123;
	/**留存-124天*/
	@Excel(name = "留存-124天", width = 15)
    @ApiModelProperty(value = "留存-124天")
    private java.lang.Integer day124;
	/**留存-125天*/
	@Excel(name = "留存-125天", width = 15)
    @ApiModelProperty(value = "留存-125天")
    private java.lang.Integer day125;
	/**留存-126天*/
	@Excel(name = "留存-126天", width = 15)
    @ApiModelProperty(value = "留存-126天")
    private java.lang.Integer day126;
	/**留存-127天*/
	@Excel(name = "留存-127天", width = 15)
    @ApiModelProperty(value = "留存-127天")
    private java.lang.Integer day127;
	/**留存-128天*/
	@Excel(name = "留存-128天", width = 15)
    @ApiModelProperty(value = "留存-128天")
    private java.lang.Integer day128;
	/**留存-129天*/
	@Excel(name = "留存-129天", width = 15)
    @ApiModelProperty(value = "留存-129天")
    private java.lang.Integer day129;
	/**留存-130天*/
	@Excel(name = "留存-130天", width = 15)
    @ApiModelProperty(value = "留存-130天")
    private java.lang.Integer day130;
	/**留存-131天*/
	@Excel(name = "留存-131天", width = 15)
    @ApiModelProperty(value = "留存-131天")
    private java.lang.Integer day131;
	/**留存-132天*/
	@Excel(name = "留存-132天", width = 15)
    @ApiModelProperty(value = "留存-132天")
    private java.lang.Integer day132;
	/**留存-133天*/
	@Excel(name = "留存-133天", width = 15)
    @ApiModelProperty(value = "留存-133天")
    private java.lang.Integer day133;
	/**留存-134天*/
	@Excel(name = "留存-134天", width = 15)
    @ApiModelProperty(value = "留存-134天")
    private java.lang.Integer day134;
	/**留存-135天*/
	@Excel(name = "留存-135天", width = 15)
    @ApiModelProperty(value = "留存-135天")
    private java.lang.Integer day135;
	/**留存-136天*/
	@Excel(name = "留存-136天", width = 15)
    @ApiModelProperty(value = "留存-136天")
    private java.lang.Integer day136;
	/**留存-137天*/
	@Excel(name = "留存-137天", width = 15)
    @ApiModelProperty(value = "留存-137天")
    private java.lang.Integer day137;
	/**留存-138天*/
	@Excel(name = "留存-138天", width = 15)
    @ApiModelProperty(value = "留存-138天")
    private java.lang.Integer day138;
	/**留存-139天*/
	@Excel(name = "留存-139天", width = 15)
    @ApiModelProperty(value = "留存-139天")
    private java.lang.Integer day139;
	/**留存-140天*/
	@Excel(name = "留存-140天", width = 15)
    @ApiModelProperty(value = "留存-140天")
    private java.lang.Integer day140;
	/**留存-141天*/
	@Excel(name = "留存-141天", width = 15)
    @ApiModelProperty(value = "留存-141天")
    private java.lang.Integer day141;
	/**留存-142天*/
	@Excel(name = "留存-142天", width = 15)
    @ApiModelProperty(value = "留存-142天")
    private java.lang.Integer day142;
	/**留存-143天*/
	@Excel(name = "留存-143天", width = 15)
    @ApiModelProperty(value = "留存-143天")
    private java.lang.Integer day143;
	/**留存-144天*/
	@Excel(name = "留存-144天", width = 15)
    @ApiModelProperty(value = "留存-144天")
    private java.lang.Integer day144;
	/**留存-145天*/
	@Excel(name = "留存-145天", width = 15)
    @ApiModelProperty(value = "留存-145天")
    private java.lang.Integer day145;
	/**留存-146天*/
	@Excel(name = "留存-146天", width = 15)
    @ApiModelProperty(value = "留存-146天")
    private java.lang.Integer day146;
	/**留存-147天*/
	@Excel(name = "留存-147天", width = 15)
    @ApiModelProperty(value = "留存-147天")
    private java.lang.Integer day147;
	/**留存-148天*/
	@Excel(name = "留存-148天", width = 15)
    @ApiModelProperty(value = "留存-148天")
    private java.lang.Integer day148;
	/**留存-149天*/
	@Excel(name = "留存-149天", width = 15)
    @ApiModelProperty(value = "留存-149天")
    private java.lang.Integer day149;
	/**留存-150天*/
	@Excel(name = "留存-150天", width = 15)
    @ApiModelProperty(value = "留存-150天")
    private java.lang.Integer day150;
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
