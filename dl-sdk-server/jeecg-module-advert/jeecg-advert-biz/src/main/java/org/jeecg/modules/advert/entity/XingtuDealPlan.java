package org.jeecg.modules.advert.entity;

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
 * @Description: op_xingtu_deal_plan
 * @Author: jeecg-boot
 * @Date:   2023-03-08
 * @Version: V1.0
 */
@Data
@TableName("op_xingtu_deal_plan")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_xingtu_deal_plan对象", description="op_xingtu_deal_plan")
public class XingtuDealPlan implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**广告ID*/
	@Excel(name = "广告ID", width = 15)
    @ApiModelProperty(value = "广告ID")
    private java.lang.Integer dealId;
	/**广告主ID*/
	@Excel(name = "广告主ID", width = 15)
    @ApiModelProperty(value = "广告主ID")
    private java.lang.Long advertiserId;
	/**广告组ID*/
	@Excel(name = "广告组ID", width = 15)
    @ApiModelProperty(value = "广告组ID")
    private java.lang.Long campaignId;
    /**广告计划id*/
    @Excel(name = "广告计划ID", width = 15)
    @ApiModelProperty(value = "广告计划ID")
	private Long adId;
	/**广告计划名称*/
	@Excel(name = "广告计划名称", width = 15)
    @ApiModelProperty(value = "广告计划名称")
    private java.lang.String name;
	/**计划状态*/
	@Excel(name = "计划状态", width = 15)
    @ApiModelProperty(value = "计划状态")
    private java.lang.String operation;
	/**抖音号*/
	@Excel(name = "抖音号", width = 15)
    @ApiModelProperty(value = "抖音号")
    private java.lang.String awemeAccount;
	/**投放范围*/
	@Excel(name = "投放范围", width = 15)
    @ApiModelProperty(value = "投放范围")
    private java.lang.String deliverRange;
	/**用户定向*/
	@Excel(name = "用户定向", width = 15)
    @ApiModelProperty(value = "用户定向")
    private java.lang.String audience;
	/**优化目标*/
	@Excel(name = "优化目标", width = 15)
    @ApiModelProperty(value = "优化目标")
    private java.lang.String optimizeGoal;
	/**搜索快投*/
	@Excel(name = "搜索快投", width = 15)
    @ApiModelProperty(value = "搜索快投")
    private java.lang.String deliverySearch;
	/**预算与出价*/
	@Excel(name = "预算与出价", width = 15)
    @ApiModelProperty(value = "预算与出价")
    private java.lang.String deliverySetting;
//	/**监测链接*/
//	@Excel(name = "监测链接", width = 15)
//    @ApiModelProperty(value = "监测链接")
//    private java.lang.String trackUrlSetting;
}
