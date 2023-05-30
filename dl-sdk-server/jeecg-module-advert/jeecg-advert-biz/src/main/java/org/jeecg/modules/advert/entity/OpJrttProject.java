package org.jeecg.modules.advert.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @Description: op_jrtt_project
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_project")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_jrtt_project对象", description="op_jrtt_project")
public class OpJrttProject implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**广告总表Id*/
    @ApiModelProperty(value = "广告总表Id")
    private java.lang.Integer dealId;
	/**项目ID*/
	@Excel(name = "项目ID ", width = 15)
    @ApiModelProperty(value = "项目ID")
    private java.lang.Long projectId;
	/**广告主ID*/
	@Excel(name = "广告主ID", width = 15, dictTable = "open_gateway.op_jrtt_put_account", dicText = "advertiser_id", dicCode = "account_id")
    @Dict(dictTable = "open_gateway.op_put_account", dicText = "nick_name", dicCode = "id")
    @ApiModelProperty(value = "广告主ID")
    @JSONField(name = "advertiser_id")
    private java.lang.Long advertiserId;
	/**计划状态*/
	@Excel(name = "计划状态", width = 15)
    @ApiModelProperty(value = "计划状态")
    private java.lang.String operation;
	/**推广目的*/
	@Excel(name = "推广目的", width = 15)
    @ApiModelProperty(value = "推广目的")
    @JSONField(name = "landing_type")
    private java.lang.String landingType;
	/**子目标*/
	@Excel(name = "子目标", width = 15)
    @ApiModelProperty(value = "子目标")
    @JSONField(name = "app_promotion_type")
    private java.lang.String appPromotionType;
	/**营销场景*/
	@Excel(name = "营销场景", width = 15)
    @ApiModelProperty(value = "营销场景")
    @JSONField(name = "marketing_goal")
    private java.lang.String marketingGoal;
	/**广告类型*/
	@Excel(name = "广告类型", width = 15)
    @ApiModelProperty(value = "广告类型")
    @JSONField(name = "ad_type")
    private java.lang.String adType;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目名称")
    @JSONField(name = "name")
    private java.lang.String name;
	/**出价系数*/
	@Excel(name = "出价系数", width = 15)
    @ApiModelProperty(value = "出价系数")
    @JSONField(name = "search_bid_ratio")
    private java.lang.Double searchBidRatio;
	/**定向拓展*/
	@Excel(name = "定向拓展", width = 15)
    @ApiModelProperty(value = "定向拓展")
    @JSONField(name = "audience_extend")
    private java.lang.String audienceExtend;
	/**待添加搜索关键词列表*/
	@Excel(name = "待添加搜索关键词列表", width = 15)
    @ApiModelProperty(value = "待添加搜索关键词列表")
    private java.lang.String keywords;
	/**下载链接*/
	@Excel(name = "下载链接", width = 15)
    @ApiModelProperty(value = "下载链接")
    @JSONField(name = "download_url")
    private java.lang.String downloadUrl;
	/**下载方式*/
	@Excel(name = "下载方式", width = 15)
    @ApiModelProperty(value = "下载方式")
    @JSONField(name = "download_type")
    private java.lang.String downloadType;
	/**优先从系统应用商店下载（下载模式）*/
	@Excel(name = "优先从系统应用商店下载（下载模式）", width = 15)
    @ApiModelProperty(value = "优先从系统应用商店下载（下载模式）")
    @JSONField(name = "download_mode")
    private java.lang.String downloadMode;
	/**资产类型*/
	@Excel(name = "资产类型", width = 15)
    @ApiModelProperty(value = "资产类型")
    @JSONField(name = "asset_type")
    private java.lang.String assetType;
	/**优化目标*/
	@Excel(name = "优化目标", width = 15)
    @ApiModelProperty(value = "优化目标")
    private java.lang.String optimizeGoal;
	/**广告版位*/
	@Excel(name = "广告版位", width = 15)
    @ApiModelProperty(value = "广告版位")
    private java.lang.String deliveryRange;
	/**人群定向*/
	@Excel(name = "人群定向", width = 15)
    @ApiModelProperty(value = "人群定向")
    private java.lang.String audience;
	/**投放设置*/
	@Excel(name = "投放设置", width = 15)
    @ApiModelProperty(value = "投放设置")
    private java.lang.String deliverySetting;
	/**监测链接设置*/
	@Excel(name = "监测链接设置", width = 15)
    @ApiModelProperty(value = "监测链接设置")
    private java.lang.String trackUrlSetting;
    /**站点ID*/
    @Excel(name = "站点ID", width = 15)
    @ApiModelProperty(value = "站点ID")
    private java.lang.Integer siteId;
    /**头条站点ID*/
    @Excel(name = "头条站点ID", width = 15)
    @ApiModelProperty(value = "头条站点ID")
    private java.lang.String jrttSiteId;
}
