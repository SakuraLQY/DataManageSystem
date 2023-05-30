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
 * @Description: data_tool
 * @Author: jeecg-boot
 * @Date:   2023-05-23
 * @Version: V1.0
 */
@Data
@TableName("data_tool")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="data_tool对象", description="data_tool")
public class ComputeMoney implements Serializable {
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
    private java.lang.String subGameId;
	/**子游戏包*/
	@Excel(name = "子游戏包", width = 15)
    @ApiModelProperty(value = "子游戏包")
    private java.lang.String pkgId;
	/**渠道*/
	@Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private java.lang.String channelId;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private java.lang.String channelTypeId;
	/**子渠道*/
	@Excel(name = "子渠道", width = 15)
    @ApiModelProperty(value = "子渠道")
    private java.lang.String channelSubAccountId;
	/**注册时间*/
	@Excel(name = "注册时间", width = 15)
    @ApiModelProperty(value = "注册时间")
    private java.lang.String startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15)
    @ApiModelProperty(value = "结束时间")
    private java.lang.String endTime;
	/**付费日期*/
	@Excel(name = "付费日期", width = 15)
    @ApiModelProperty(value = "付费日期")
    private java.lang.String payStart;
	/**结束日期*/
	@Excel(name = "结束日期", width = 15)
    @ApiModelProperty(value = "结束日期")
    private java.lang.String payEnd;
	/**注册日期*/
	@Excel(name = "注册日期", width = 15)
    @ApiModelProperty(value = "注册日期")
    private java.lang.String regTime;
	/**注册数*/
	@Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private java.lang.String regCount;
	/**新增付费人数*/
	@Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private java.lang.String firstPayUser;
	/**新增付费*/
	@Excel(name = "新增付费", width = 15)
    @ApiModelProperty(value = "新增付费")
    private java.lang.String firstPay;
	/**期间付费人数*/
	@Excel(name = "期间付费人数", width = 15)
    @ApiModelProperty(value = "期间付费人数")
    private java.lang.String periodUser;
	/**期间付费*/
	@Excel(name = "期间付费", width = 15)
    @ApiModelProperty(value = "期间付费")
    private java.lang.String periodMoney;
}
