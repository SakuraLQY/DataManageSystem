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
 * @Description: 留存分析
 * @Author: jeecg-boot
 * @Date:   2023-05-17
 * @Version: V1.0
 */
@Data
@TableName("ct_daily_retain")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_daily_retain对象", description="留存分析")
public class RetainAnalyze implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**游戏名*/
	@Excel(name = "游戏名", width = 15)
    @ApiModelProperty(value = "游戏名")
    private java.lang.String gameId;
	/**游戏名称*/
	@Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private java.lang.String subGameId;
	/**游戏包名*/
	@Excel(name = "游戏包名", width = 15)
    @ApiModelProperty(value = "游戏包名")
    private java.lang.String pkgId;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private java.lang.String channelTypeId;
	/**渠道名称*/
	@Excel(name = "渠道名称", width = 15)
    @ApiModelProperty(value = "渠道名称")
    private java.lang.String channelId;
	/**二级渠道*/
	@Excel(name = "二级渠道", width = 15)
    @ApiModelProperty(value = "二级渠道")
    private java.lang.String subChannelAccountId;
	/**广告列表*/
	@Excel(name = "广告列表", width = 15)
    @ApiModelProperty(value = "广告列表")
    private java.lang.String dealId;
	/**留存类型*/
	@Excel(name = "留存类型", width = 15)
    @ApiModelProperty(value = "留存类型")
    private java.lang.String retainType;
	/**充值区间*/
	@Excel(name = "充值区间", width = 15)
    @ApiModelProperty(value = "充值区间")
    private java.lang.String begSection;
	/**区间结束*/
	@Excel(name = "区间结束", width = 15)
    @ApiModelProperty(value = "区间结束")
    private java.lang.String endSection;
	/**起始时间*/
	@Excel(name = "起始时间", width = 15)
    @ApiModelProperty(value = "起始时间")
    private java.lang.String startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15)
    @ApiModelProperty(value = "结束时间")
    private java.lang.String endTime;
	/**充值限期*/
	@Excel(name = "充值限期", width = 15)
    @ApiModelProperty(value = "充值限期")
    private java.lang.String costTime;
	/**账号人员*/
	@Excel(name = "账号人员", width = 15)
    @ApiModelProperty(value = "账号人员")
    private java.lang.String createUser;
	/**日期*/
	@Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private java.lang.String dateTime;
	/**游戏名*/
	@Excel(name = "游戏名", width = 15)
    @ApiModelProperty(value = "游戏名")
    private java.lang.String gameName;
	/**渠道名*/
	@Excel(name = "渠道名", width = 15)
    @ApiModelProperty(value = "渠道名")
    private java.lang.String channelName;
	/**广告名*/
	@Excel(name = "广告名", width = 15)
    @ApiModelProperty(value = "广告名")
    private java.lang.String dealName;
	/**注册数*/
	@Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private java.lang.String regCount;
	/**推广费用*/
	@Excel(name = "推广费用", width = 15)
    @ApiModelProperty(value = "推广费用")
    private java.lang.String costMoney;
}
