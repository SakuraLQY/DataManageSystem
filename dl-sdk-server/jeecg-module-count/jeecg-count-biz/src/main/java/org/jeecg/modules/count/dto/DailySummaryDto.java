package org.jeecg.modules.count.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @description: TODO
 * @author: chenglin
 * @date: 2023年05月04日 17:17
 */
@Data
@TableName("ct_hour")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_hour对象", description="返回给前端的对象")
public class DailySummaryDto implements Serializable {
    /**维度*/
    @ApiModelProperty(value = "维度")
    private java.lang.String level;
    /**0时*/
    @ApiModelProperty(value = "0时")
    private java.lang.Integer zeroHour;
    /**1时*/
    @ApiModelProperty(value = "1时")
    private java.lang.Integer oneHour;
    /**2时*/
    @ApiModelProperty(value = "2时")
    private java.lang.Integer twoHour;
    /**3时*/
    @ApiModelProperty(value = "3时")
    private java.lang.Integer threeHour;
    /**4时*/
    @ApiModelProperty(value = "4时")
    private java.lang.Integer fourHour;
    /**5时*/
    @ApiModelProperty(value = "5时")
    private java.lang.Integer fiveHour;
    /**6时*/
    @ApiModelProperty(value = "6时")
    private java.lang.Integer sixHour;
    /**7时*/
    @ApiModelProperty(value = "7时")
    private java.lang.Integer sevenHour;
    /**8时*/
    @ApiModelProperty(value = "8时")
    private java.lang.Integer eightHour;
    /**9时*/
    @ApiModelProperty(value = "9时")
    private java.lang.Integer nineHour;
    /**10时*/
    @ApiModelProperty(value = "10时")
    private java.lang.Integer tenHour;
    /**11时*/
    @ApiModelProperty(value = "11时")
    private java.lang.Integer elevenHour;
    /**12时*/
    @ApiModelProperty(value = "12时")
    private java.lang.Integer twelveHour;
    /**13时*/
    @ApiModelProperty(value = "13时")
    private java.lang.Integer thirteenHour;
    /**14时*/
    @ApiModelProperty(value = "14时")
    private java.lang.Integer fourteenHour;
    /**15时*/
    @ApiModelProperty(value = "15时")
    private java.lang.Integer fifteenHour;
    /**16时*/
    @ApiModelProperty(value = "16时")
    private java.lang.Integer sixteenHour;
    /**17时*/
    @ApiModelProperty(value = "17时")
    private java.lang.Integer seventeenHour;
    /**18时*/
    @ApiModelProperty(value = "18时")
    private java.lang.Integer eighteenHour;
    /**19时*/
    @ApiModelProperty(value = "19时")
    private java.lang.Integer nineteenHour;
    /**20时*/
    @ApiModelProperty(value = "20时")
    private java.lang.Integer twentyHour;
    /**21时*/
    @ApiModelProperty(value = "21时")
    private java.lang.Integer twentyoneHour;
    /**22时*/
    @ApiModelProperty(value = "22时")
    private java.lang.Integer twentytwoHour;
    /**23时*/
    @ApiModelProperty(value = "23时")
    private java.lang.Integer twentythreeHour;
    /**汇总*/
    @ApiModelProperty(value = "汇总")
    private java.lang.Integer total;
}
