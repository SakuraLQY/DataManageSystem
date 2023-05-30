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
 * @Description: publish_conversion
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Data
@TableName("publish_conversion")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="publish_conversion对象", description="publish_conversion")
public class PublishConversion implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**起始时间*/
	@Excel(name = "起始时间", width = 15)
    @ApiModelProperty(value = "起始时间")
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
	/**点击*/
	@Excel(name = "点击", width = 15)
    @ApiModelProperty(value = "点击")
    private java.lang.String click;
	/**点击设备数*/
	@Excel(name = "点击设备数", width = 15)
    @ApiModelProperty(value = "点击设备数")
    private java.lang.String clickDev;
	/**下载数*/
	@Excel(name = "下载数", width = 15)
    @ApiModelProperty(value = "下载数")
    private java.lang.String download;
	/**下载设备数*/
	@Excel(name = "下载设备数", width = 15)
    @ApiModelProperty(value = "下载设备数")
    private java.lang.String downloadDev;
	/**激活*/
	@Excel(name = "激活", width = 15)
    @ApiModelProperty(value = "激活")
    private java.lang.String active;
	/**激活设备数*/
	@Excel(name = "激活设备数", width = 15)
    @ApiModelProperty(value = "激活设备数")
    private java.lang.String activeDev;
	/**注册数*/
	@Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private java.lang.String regCount;
	/**注册设备数*/
	@Excel(name = "注册设备数", width = 15)
    @ApiModelProperty(value = "注册设备数")
    private java.lang.String regCountDev;
	/**点击下载率*/
	@Excel(name = "点击下载率", width = 15)
    @ApiModelProperty(value = "点击下载率")
    private java.lang.String clickRate;
	/**点击激活率*/
	@Excel(name = "点击激活率", width = 15)
    @ApiModelProperty(value = "点击激活率")
    private java.lang.String clickActiveRate;
	/**点击注册率*/
	@Excel(name = "点击注册率", width = 15)
    @ApiModelProperty(value = "点击注册率")
    private java.lang.String clickRegRate;
	/**激活注册率*/
	@Excel(name = "激活注册率", width = 15)
    @ApiModelProperty(value = "激活注册率")
    private java.lang.String activeRegRate;
	/**新增付费人数*/
	@Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private java.lang.String firstPayUser;
	/**有效注册数*/
	@Excel(name = "有效注册数", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private java.lang.String validUser;
}
