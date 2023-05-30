package org.jeecg.modules.advert.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: op_xingtu_deal_plan
 * @Author: jeecg-boot
 * @Date:   2023-03-08
 * @Version: V1.0
 */
@Data
@ApiModel(value="返回前端对象", description="返回前端对象")
public class XingtuDealPlanVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**广告ID*/
	@Excel(name = "广告ID", width = 15)
    @ApiModelProperty(value = "广告ID")
    private Integer dealId;
	/**广告主ID*/
	@Excel(name = "广告主ID", width = 15)
    @ApiModelProperty(value = "广告主ID")
    private Long advertiserId;
	/**广告组ID*/
	@Excel(name = "广告组ID", width = 15)
    @ApiModelProperty(value = "广告组ID")
    private String campaignId;
    /**广告计划id*/
    @Excel(name = "广告计划ID", width = 15)
    @ApiModelProperty(value = "广告计划ID")
	private Long adId;
	/**广告计划名称*/
	@Excel(name = "广告计划名称", width = 15)
    @ApiModelProperty(value = "广告计划名称")
    private String name;
	/**计划状态*/
	@Excel(name = "计划状态", width = 15)
    @ApiModelProperty(value = "计划状态")
    private String operation;
	/**抖音号*/
	@Excel(name = "抖音号", width = 15)
    @ApiModelProperty(value = "抖音号")
    private String awemeAccount;
	/**投放范围*/
	@Excel(name = "投放范围", width = 15)
    @ApiModelProperty(value = "投放范围")
    private String deliverRange;
	/**用户定向*/
	@Excel(name = "用户定向", width = 15)
    @ApiModelProperty(value = "用户定向")
    private String audience;
	/**优化目标*/
	@Excel(name = "优化目标", width = 15)
    @ApiModelProperty(value = "优化目标")
    private String optimizeGoal;
	/**搜索快投*/
	@Excel(name = "搜索快投", width = 15)
    @ApiModelProperty(value = "搜索快投")
    private String deliverySearch;
	/**预算与出价*/
	@Excel(name = "预算与出价", width = 15)
    @ApiModelProperty(value = "预算与出价")
    private String deliverySetting;

    @ApiModelProperty(value = "创意id")
    private Integer creativeId;
//	/**监测链接*/
//	@Excel(name = "监测链接", width = 15)
//    @ApiModelProperty(value = "监测链接")
//    private java.lang.String trackUrlSetting;
}
