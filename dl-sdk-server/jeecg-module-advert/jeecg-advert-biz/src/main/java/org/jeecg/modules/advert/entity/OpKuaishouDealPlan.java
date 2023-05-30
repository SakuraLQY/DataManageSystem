package org.jeecg.modules.advert.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_kuaishou_deal
 * @Author: jeecg-boot
 * @Date:   2023-03-09
 * @Version: V1.0
 */
@Data
@TableName("op_kuaishou_deal_plan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_kuaishou_deal对象", description="op_kuaishou_deal")
public class OpKuaishouDealPlan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**广告ID*/
	@Excel(name = "广告ID", width = 15)
    @ApiModelProperty(value = "广告ID")
    private java.lang.Integer dealId;
	/**广告计划ID*/
	@Excel(name = "广告计划ID", width = 15)
    @ApiModelProperty(value = "广告计划ID")
    private java.lang.String campaignId;
	/**预算类型: 0不限, 1日预算, 2分日预算*/
	@Excel(name = "预算类型: 0不限, 1日预算, 2分日预算", width = 15)
    @ApiModelProperty(value = "预算类型: 0不限, 1日预算, 2分日预算")
    private java.lang.Integer budgetMode;
	/**推广计划预算*/
	@Excel(name = "推广计划预算", width = 15)
    @ApiModelProperty(value = "推广计划预算")
    private java.lang.String budget;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
}
