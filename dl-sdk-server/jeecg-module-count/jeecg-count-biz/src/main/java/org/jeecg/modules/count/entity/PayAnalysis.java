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
 * @Description: pay_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Data
@TableName("pay_analysis")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pay_analysis对象", description="pay_analysis")
public class PayAnalysis implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15)
    @ApiModelProperty(value = "开始时间")
    private java.lang.String startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15)
    @ApiModelProperty(value = "结束时间")
    private java.lang.String endTime;
	/**归类方式*/
	@Excel(name = "归类方式", width = 15)
    @ApiModelProperty(value = "归类方式")
    private java.lang.String type;
	/**ID*/
	@Excel(name = "ID", width = 15)
    @ApiModelProperty(value = "ID")
    private java.lang.String typeId;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**新增付费人数*/
	@Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private java.lang.String firstPayUser;
	/**老用户付费人数*/
	@Excel(name = "老用户付费人数", width = 15)
    @ApiModelProperty(value = "老用户付费人数")
    private java.lang.String oldPayUser;
	/**总付费人数*/
	@Excel(name = "总付费人数", width = 15)
    @ApiModelProperty(value = "总付费人数")
    private java.lang.String totalPayUser;
	/**新增付费金额*/
	@Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private java.lang.String firstPayMoney;
	/**老用户付费金额*/
	@Excel(name = "老用户付费金额", width = 15)
    @ApiModelProperty(value = "老用户付费金额")
    private java.lang.String oldPayMoney;
	/**总付费金额*/
	@Excel(name = "总付费金额", width = 15)
    @ApiModelProperty(value = "总付费金额")
    private java.lang.String totalPayMoney;
	/**累计付费金额*/
	@Excel(name = "累计付费金额", width = 15)
    @ApiModelProperty(value = "累计付费金额")
    private java.lang.String aliveTotalMoney;
	/**老用户ARPU*/
	@Excel(name = "老用户ARPU", width = 15)
    @ApiModelProperty(value = "老用户ARPU")
    private java.lang.String oldArpu;
	/**老用户ARPPU*/
	@Excel(name = "老用户ARPPU", width = 15)
    @ApiModelProperty(value = "老用户ARPPU")
    private java.lang.String oldArppu;
	/**ARPU*/
	@Excel(name = "ARPU", width = 15)
    @ApiModelProperty(value = "ARPU")
    private java.lang.String arpu;
	/**ARPPU*/
	@Excel(name = "ARPPU", width = 15)
    @ApiModelProperty(value = "ARPPU")
    private java.lang.String arppu;
	/**新增付费率*/
	@Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private java.lang.String firstPayRate;
	/**总付费率*/
	@Excel(name = "总付费率", width = 15)
    @ApiModelProperty(value = "总付费率")
    private java.lang.String totalRate;
}
