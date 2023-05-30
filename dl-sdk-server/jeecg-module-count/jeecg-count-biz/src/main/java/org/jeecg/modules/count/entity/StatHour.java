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
 * @Description: cooperator_stat
 * @Author: jeecg-boot
 * @Date:   2023-05-29
 * @Version: V1.0
 */
@Data
@TableName("cooperator_stat")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cooperator_stat对象", description="cooperator_stat")
public class StatHour implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**广告类型*/
	@Excel(name = "广告类型", width = 15)
    @ApiModelProperty(value = "广告类型")
    private java.lang.String dealId;
	/**起始日期*/
	@Excel(name = "起始日期", width = 15)
    @ApiModelProperty(value = "起始日期")
    private java.lang.String startTime;
	/**结束日期*/
	@Excel(name = "结束日期", width = 15)
    @ApiModelProperty(value = "结束日期")
    private java.lang.String endTime;
	/**归类方式*/
	@Excel(name = "归类方式", width = 15)
    @ApiModelProperty(value = "归类方式")
    private java.lang.String type;
	/**游戏*/
	@Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private java.lang.String gameId;
	/**子游戏*/
	@Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private java.lang.String subGameId;
	/**渠道子账号*/
	@Excel(name = "渠道子账号", width = 15)
    @ApiModelProperty(value = "渠道子账号")
    private java.lang.String channelSubAccountId;
	/**渠道*/
	@Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private java.lang.String channelId;
	/**渠道类型*/
	@Excel(name = "渠道类型", width = 15)
    @ApiModelProperty(value = "渠道类型")
    private java.lang.String channelTypeId;
	/**渠道游戏包*/
	@Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private java.lang.String pkgId;
	/**日期*/
	@Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private java.lang.String date;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
}
