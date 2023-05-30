package org.jeecg.modules.count.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 渠道明细表数据
 * @Author: jeecg-boot
 * @Date:   2023-05-11
 * @Version: V1.0
 */
@Data
@TableName("channel_details")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="channel_details对象", description="渠道明细表数据")
public class ChannelDetails implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**游戏项目*/
	@Excel(name = "游戏项目", width = 15)
    @ApiModelProperty(value = "游戏项目")
    private java.lang.Integer gameId;
	/**游戏名称*/
	@Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private java.lang.Integer subGameId;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private java.lang.Integer channelTypeId;
	/**渠道名称*/
	@Excel(name = "渠道名称", width = 15)
    @ApiModelProperty(value = "渠道名称")
    private java.lang.Integer channelId;
	/**起始日期*/
	@Excel(name = "起始日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "起始日期")
    private java.util.Date startTime;
	/**结束日期*/
	@Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private java.util.Date endTime;
	/**展示方式*/
	@Excel(name = "展示方式", width = 15)
    @ApiModelProperty(value = "展示方式")
    private java.lang.String showType;
	/**日期*/
	@Excel(name = "日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "日期")
    private java.util.Date dateTime;
	/**子游戏*/
	@Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private java.lang.String subGameName;
	/**渠道*/
	@Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private java.lang.String channel;
	/**激活*/
	@Excel(name = "激活", width = 15)
    @ApiModelProperty(value = "激活")
    private java.lang.Integer countActive;
	/**激活设备数*/
	@Excel(name = "激活设备数", width = 15)
    @ApiModelProperty(value = "激活设备数")
    private java.lang.Integer countActiveDev;
	/**注册数*/
	@Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private java.lang.Integer countUser;
	/**注册设备数*/
	@Excel(name = "注册设备数", width = 15)
    @ApiModelProperty(value = "注册设备数")
    private java.lang.Integer countUserDev;
	/**激活注册率*/
	@Excel(name = "激活注册率", width = 15)
    @ApiModelProperty(value = "激活注册率")
    private java.math.BigDecimal activeRate;
	/**有效注册数*/
	@Excel(name = "有效注册数", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private java.lang.Integer countValidUser;
	/**新增付费人数*/
	@Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private java.lang.Integer firstPayuser;
	/**新增付费金额*/
	@Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private java.lang.Integer firstMoney;
	/**新增付费率*/
	@Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private java.math.BigDecimal firstPayRate;
	/**新增ARPU*/
	@Excel(name = "新增ARPU", width = 15)
    @ApiModelProperty(value = "新增ARPU")
    private java.math.BigDecimal firstArpu;
	/**新增ARPPU*/
	@Excel(name = "新增ARPPU", width = 15)
    @ApiModelProperty(value = "新增ARPPU")
    private java.lang.Integer firstArppu;
	/**老用户付费数*/
	@Excel(name = "老用户付费数", width = 15)
    @ApiModelProperty(value = "老用户付费数")
    private java.lang.Integer oldPayuser;
	/**老用户付费金额*/
	@Excel(name = "老用户付费金额", width = 15)
    @ApiModelProperty(value = "老用户付费金额")
    private java.lang.Integer oldMoney;
	/**老用户付费率*/
	@Excel(name = "老用户付费率", width = 15)
    @ApiModelProperty(value = "老用户付费率")
    private java.lang.Integer odPayRate;
	/**老用户ARPU*/
	@Excel(name = "老用户ARPU", width = 15)
    @ApiModelProperty(value = "老用户ARPU")
    private java.lang.Integer oldArpu;
	/**老用户ARPPU*/
	@Excel(name = "老用户ARPPU", width = 15)
    @ApiModelProperty(value = "老用户ARPPU")
    private java.lang.Integer oldArppu;
	/**累计付费金额*/
	@Excel(name = "累计付费金额", width = 15)
    @ApiModelProperty(value = "累计付费金额")
    private java.lang.Integer totalMoney;
	/**DAU*/
	@Excel(name = "DAU", width = 15)
    @ApiModelProperty(value = "DAU")
    private java.lang.Integer dau;
	/**活跃付费人数*/
	@Excel(name = "活跃付费人数", width = 15)
    @ApiModelProperty(value = "活跃付费人数")
    private java.lang.Integer alivePayuser;
	/**活跃付费金额*/
	@Excel(name = "活跃付费金额", width = 15)
    @ApiModelProperty(value = "活跃付费金额")
    private java.lang.Integer aliveMoney;
	/**ARPU*/
	@Excel(name = "ARPU", width = 15)
    @ApiModelProperty(value = "ARPU")
    private java.math.BigDecimal arpu;
	/**ARPPU*/
	@Excel(name = "ARPPU", width = 15)
    @ApiModelProperty(value = "ARPPU")
    private java.math.BigDecimal arppu;
	/**总付费率*/
	@Excel(name = "总付费率", width = 15)
    @ApiModelProperty(value = "总付费率")
    private java.math.BigDecimal totalPayRate;
	/**次留*/
	@Excel(name = "次留", width = 15)
    @ApiModelProperty(value = "次留")
    private java.lang.Integer loyal;
	/**3日留存*/
	@Excel(name = "3日留存", width = 15)
    @ApiModelProperty(value = "3日留存")
    private java.lang.Integer loyal3;
	/**4日留存*/
	@Excel(name = "4日留存", width = 15)
    @ApiModelProperty(value = "4日留存")
    private java.lang.Integer loyal4;
	/**5日留存*/
	@Excel(name = "5日留存", width = 15)
    @ApiModelProperty(value = "5日留存")
    private java.lang.Integer loyal5;
	/**6日留存*/
	@Excel(name = "6日留存", width = 15)
    @ApiModelProperty(value = "6日留存")
    private java.lang.Integer loyal6;
	/**7日留存*/
	@Excel(name = "7日留存", width = 15)
    @ApiModelProperty(value = "7日留存")
    private java.lang.Integer loyal7;
	/**LTV1*/
	@Excel(name = "LTV1", width = 15)
    @ApiModelProperty(value = "LTV1")
    private java.lang.Integer ltv1;
	/**LTV2*/
	@Excel(name = "LTV2", width = 15)
    @ApiModelProperty(value = "LTV2")
    private java.lang.Integer ltv2;
	/**LTV3*/
	@Excel(name = "LTV3", width = 15)
    @ApiModelProperty(value = "LTV3")
    private java.lang.Integer ltv3;
	/**LTV4*/
	@Excel(name = "LTV4", width = 15)
    @ApiModelProperty(value = "LTV4")
    private java.lang.Integer ltv4;
	/**LTV5*/
	@Excel(name = "LTV5", width = 15)
    @ApiModelProperty(value = "LTV5")
    private java.lang.Integer ltv5;
	/**LTV6*/
	@Excel(name = "LTV6", width = 15)
    @ApiModelProperty(value = "LTV6")
    private java.lang.Integer ltv6;
	/**LTV7*/
	@Excel(name = "LTV7", width = 15)
    @ApiModelProperty(value = "LTV7")
    private java.lang.Integer ltv7;
}
