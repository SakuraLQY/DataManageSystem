package org.jeecg.modules.count.vo;

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
 * @Description: ct_dayanalyze
 * @Author: jeecg-boot
 * @Date:   2023-04-28
 * @Version: V1.0
 */
@Data
@TableName("ct_hour")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_dayanalyze对象", description="ct_dayanalyze")
public class CtDayAnalyze implements Serializable {
    private static final long serialVersionUID = 1L;

	/**维度*/
    @ApiModelProperty(value = "维度")
    private java.lang.String level;
	/**0时*/
	@Excel(name = "0时", width = 15)
    @ApiModelProperty(value = "0时")
    private java.lang.Integer zeroHour;
	/**1时*/
	@Excel(name = "1时", width = 15)
    @ApiModelProperty(value = "1时")
    private java.lang.Integer oneHour;
	/**2时*/
	@Excel(name = "2时", width = 15)
    @ApiModelProperty(value = "2时")
    private java.lang.Integer twoHour;
	/**3时*/
	@Excel(name = "3时", width = 15)
    @ApiModelProperty(value = "3时")
    private java.lang.Integer threeHour;
	/**4时*/
	@Excel(name = "4时", width = 15)
    @ApiModelProperty(value = "4时")
    private java.lang.Integer fourHour;
	/**5时*/
	@Excel(name = "5时", width = 15)
    @ApiModelProperty(value = "5时")
    private java.lang.Integer fiveHour;
	/**6时*/
	@Excel(name = "6时", width = 15)
    @ApiModelProperty(value = "6时")
    private java.lang.Integer sixHour;
	/**7时*/
	@Excel(name = "7时", width = 15)
    @ApiModelProperty(value = "7时")
    private java.lang.Integer sevenHour;
	/**8时*/
	@Excel(name = "8时", width = 15)
    @ApiModelProperty(value = "8时")
    private java.lang.Integer eightHour;
	/**9时*/
	@Excel(name = "9时", width = 15)
    @ApiModelProperty(value = "9时")
    private java.lang.Integer nineHour;
	/**10时*/
	@Excel(name = "10时", width = 15)
    @ApiModelProperty(value = "10时")
    private java.lang.Integer tenHour;
	/**11时*/
	@Excel(name = "11时", width = 15)
    @ApiModelProperty(value = "11时")
    private java.lang.Integer elevenHour;
	/**12时*/
	@Excel(name = "12时", width = 15)
    @ApiModelProperty(value = "12时")
    private java.lang.Integer twelveHour;
	/**13时*/
	@Excel(name = "13时", width = 15)
    @ApiModelProperty(value = "13时")
    private java.lang.Integer thirteenHour;
	/**14时*/
	@Excel(name = "14时", width = 15)
    @ApiModelProperty(value = "14时")
    private java.lang.Integer fourteenHour;
	/**15时*/
	@Excel(name = "15时", width = 15)
    @ApiModelProperty(value = "15时")
    private java.lang.Integer fifteenHour;
	/**16时*/
	@Excel(name = "16时", width = 15)
    @ApiModelProperty(value = "16时")
    private java.lang.Integer sixteenHour;
	/**17时*/
	@Excel(name = "17时", width = 15)
    @ApiModelProperty(value = "17时")
    private java.lang.Integer seventeenHour;
	/**18时*/
	@Excel(name = "18时", width = 15)
    @ApiModelProperty(value = "18时")
    private java.lang.Integer eighteenHour;
	/**19时*/
	@Excel(name = "19时", width = 15)
    @ApiModelProperty(value = "19时")
    private java.lang.Integer nineteenHour;
	/**20时*/
	@Excel(name = "20时", width = 15)
    @ApiModelProperty(value = "20时")
    private java.lang.Integer twentyHour;
	/**21时*/
	@Excel(name = "21时", width = 15)
    @ApiModelProperty(value = "21时")
    private java.lang.Integer twentyoneHour;
	/**22时*/
	@Excel(name = "22时", width = 15)
    @ApiModelProperty(value = "22时")
    private java.lang.Integer twentytwoHour;
	/**23时*/
	@Excel(name = "23时", width = 15)
    @ApiModelProperty(value = "23时")
    private java.lang.Integer twentythreeHour;
	/**汇总*/
	@Excel(name = "汇总", width = 15)
    @ApiModelProperty(value = "汇总")
    private java.lang.Integer total;
}
