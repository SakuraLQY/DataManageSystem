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
 * @Description: ltv_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Data
@TableName("ltv_analysis")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ltv_analysis对象", description="ltv_analysis")
public class LtvAnalysis implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**游戏*/
	@Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private java.lang.String gameId;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date endTime;
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
	/**LTV1*/
	@Excel(name = "LTV1", width = 15)
    @ApiModelProperty(value = "LTV1")
    private java.lang.String ltv1;
	/**LTV2*/
	@Excel(name = "LTV2", width = 15)
    @ApiModelProperty(value = "LTV2")
    private java.lang.String ltv2;
	/**LTV3*/
	@Excel(name = "LTV3", width = 15)
    @ApiModelProperty(value = "LTV3")
    private java.lang.String ltv3;
	/**LTV4*/
	@Excel(name = "LTV4", width = 15)
    @ApiModelProperty(value = "LTV4")
    private java.lang.String ltv4;
	/**LTV5*/
	@Excel(name = "LTV5", width = 15)
    @ApiModelProperty(value = "LTV5")
    private java.lang.String ltv5;
	/**LTV6*/
	@Excel(name = "LTV6", width = 15)
    @ApiModelProperty(value = "LTV6")
    private java.lang.String ltv6;
	/**LTV7*/
	@Excel(name = "LTV7", width = 15)
    @ApiModelProperty(value = "LTV7")
    private java.lang.String ltv7;
	/**LTV15*/
	@Excel(name = "LTV15", width = 15)
    @ApiModelProperty(value = "LTV15")
    private java.lang.String ltv15;
	/**LTV30*/
	@Excel(name = "LTV30", width = 15)
    @ApiModelProperty(value = "LTV30")
    private java.lang.String ltv30;
	/**LTV45*/
	@Excel(name = "LTV45", width = 15)
    @ApiModelProperty(value = "LTV45")
    private java.lang.String ltv45;
	/**LTV60*/
	@Excel(name = "LTV60", width = 15)
    @ApiModelProperty(value = "LTV60")
    private java.lang.String ltv60;
	/**LTV90*/
	@Excel(name = "LTV90", width = 15)
    @ApiModelProperty(value = "LTV90")
    private java.lang.String ltv90;
	/**LTV120*/
	@Excel(name = "LTV120", width = 15)
    @ApiModelProperty(value = "LTV120")
    private java.lang.String ltv120;
	/**LTV150*/
	@Excel(name = "LTV150", width = 15)
    @ApiModelProperty(value = "LTV150")
    private java.lang.String ltv150;
}
