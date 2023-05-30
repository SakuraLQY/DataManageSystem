package org.jeecg.modules.advert.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * @Description: op_jrtt_promotion
 * @Author: jeecg-boot
 * @Date:   2023-02-22
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_promotion")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_jrtt_promotion对象", description="op_jrtt_promotion")
public class OpJrttPromotion implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**广告总表ID*/
	@Excel(name = "广告总表ID", width = 15)
    @ApiModelProperty(value = "广告总表ID")
    private java.lang.Integer dealId;
	/**项目ID*/
	@Excel(name = "项目ID", width = 15)
    @ApiModelProperty(value = "项目ID")
    private java.lang.Integer projectId;
	/**头条广告id*/
	@Excel(name = "头条广告id", width = 15)
    @JsonSerialize(using= ToStringSerializer.class)
    @ApiModelProperty(value = "头条广告id")
    private java.lang.Long jrttPromotionId;
	/**广告主ID*/
	@Excel(name = "广告主ID", width = 15)
    @ApiModelProperty(value = "广告主ID")
    private java.lang.Long advertiserId;
	/**广告状态*/
	@Excel(name = "广告状态", width = 15)
    @ApiModelProperty(value = "广告状态")
    private java.lang.String operation;
	/**广告名称*/
	@Excel(name = "广告名称", width = 15)
    @ApiModelProperty(value = "广告名称")
    private java.lang.String name;
	/**视频素材信息*/
	@Excel(name = "视频素材信息", width = 15)
    @ApiModelProperty(value = "视频素材信息")
    private java.lang.String videoMaterialList;
	/**创意图片素材*/
	@Excel(name = "创意图片素材", width = 15)
    @ApiModelProperty(value = "创意图片素材")
    private java.lang.String imageMaterialList;
	/**标题素材*/
	@Excel(name = "标题素材", width = 15)
    @ApiModelProperty(value = "标题素材")
    private java.lang.String titleMaterialList;
	/**产品名称*/
	@Excel(name = "产品名称", width = 15)
    @ApiModelProperty(value = "产品名称")
    private java.lang.String productTitle;
	/**产品主图*/
	@Excel(name = "产品主图", width = 15)
    @ApiModelProperty(value = "产品主图")
    private java.lang.String imageIds;
	/**产品卖点*/
	@Excel(name = "产品卖点", width = 15)
    @ApiModelProperty(value = "产品卖点")
    private java.lang.String sellingPoints;
    /**产品卖点*/
    @Excel(name = "行动号召文案", width = 15)
    @ApiModelProperty(value = "行动号召文案")
    private java.lang.String callToActionButtons;
	/**授权抖音号id*/
	@Excel(name = "授权抖音号id", width = 15)
    @ApiModelProperty(value = "授权抖音号id")
    private java.lang.String awemeId;
	/**主页作品列表隐藏广告内容允选值：OFF（不隐藏），ON（隐藏）默认值：OFF*/
	@Excel(name = "主页作品列表隐藏广告内容允选值：OFF（不隐藏），ON（隐藏）默认值：OFF", width = 15)
    @ApiModelProperty(value = "主页作品列表隐藏广告内容允选值：OFF（不隐藏），ON（隐藏）默认值：OFF")
    private java.lang.String isFeedAndFavSee;
	/**原生锚点启用开关，允许值:不启用 OFF（默认值），自动生成 AUTO，手动选择 SELECT*/
	@Excel(name = "原生锚点启用开关，允许值:不启用 OFF（默认值），自动生成 AUTO，手动选择 SELECT", width = 15)
    @ApiModelProperty(value = "原生锚点启用开关，允许值:不启用 OFF（默认值），自动生成 AUTO，手动选择 SELECT")
    private java.lang.String anchorRelatedType;
	/**预算*/
	@Excel(name = "预算", width = 15)
    @ApiModelProperty(value = "预算")
    private java.math.BigDecimal budget;
	/**点击出价/展示出价*/
	@Excel(name = "点击出价/展示出价", width = 15)
    @ApiModelProperty(value = "点击出价/展示出价")
    private java.math.BigDecimal bid;
	/**目标转化出价/预期成本*/
	@Excel(name = "目标转化出价/预期成本", width = 15)
    @ApiModelProperty(value = "目标转化出价/预期成本")
    private java.math.BigDecimal cpaBid;
	/**深度优化出价*/
	@Excel(name = "深度优化出价", width = 15)
    @ApiModelProperty(value = "深度优化出价")
    private java.math.BigDecimal deepCpabid;
	/**深度转化ROI系数*/
	@Excel(name = "深度转化ROI系数", width = 15)
    @ApiModelProperty(value = "深度转化ROI系数")
    private java.math.BigDecimal roiGoal;
    /**视频素材id*/
    @Excel(name = "视频素材id", width = 15)
    @ApiModelProperty(value = "视频素材id")
    private java.lang.Integer videoMaterialId;
    /**视频素材帧*/
    @Excel(name = "视频素材帧", width = 15)
    @ApiModelProperty(value = "视频素材帧")
    private java.lang.Integer videoMaterialFrameRate;
}
