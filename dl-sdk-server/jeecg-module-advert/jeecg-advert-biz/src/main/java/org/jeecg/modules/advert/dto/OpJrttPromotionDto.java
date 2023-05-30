package org.jeecg.modules.advert.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

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
public class OpJrttPromotionDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
	/**广告总表ID*/
    @ApiModelProperty(value = "广告总表ID")
    private Integer dealId;
	/**项目ID*/
    @ApiModelProperty(value = "项目ID")
    private Integer projectId;
	/**头条广告id*/
    @ApiModelProperty(value = "头条广告id")
    private Long jrttPromotionId;
	/**广告主ID*/
    @ApiModelProperty(value = "广告主ID")
    private Long advertiserId;
	/**广告状态*/
    @ApiModelProperty(value = "广告状态")
    private String operation;
	/**广告名称*/
    @ApiModelProperty(value = "广告名称")
    private String name;
	/**视频素材信息*/
    @ApiModelProperty(value = "视频素材id")
    private Integer videoMaterialId;
    /**视频素材信息*/
    @ApiModelProperty(value = "视频素材帧")
    private Integer videoMaterialFrameRate;
    /**视频素材信息*/
    @ApiModelProperty(value = "视频素材封面")
    private String videoMaterialScreenShot;
	/**创意图片素材*/
    @ApiModelProperty(value = "创意图片素材")
    private String imageMaterialList;
	/**标题素材*/
    @ApiModelProperty(value = "标题素材")
    private List<String> titleMaterialList;
	/**普通落地页链接素材*/
    @ApiModelProperty(value = "普通落地页链接素材")
    private String externalUrlMaterialList;
	/**产品名称*/
    @ApiModelProperty(value = "产品名称")
    private String productTitle;
	/**产品主图*/
    @ApiModelProperty(value = "产品主图Id")
    private Integer productImageId;
	/**产品卖点*/
    @ApiModelProperty(value = "产品卖点")
    private List<String> sellingPoints;
    /**行动号召文案*/
    @ApiModelProperty(value = "行动号召文案")
    private List<String> callToActionButtons;
    /**promotionIdentity*/
    @ApiModelProperty(value = "推广身份")
    private String promotionIdentity;
	/**授权抖音号id*/
    @ApiModelProperty(value = "授权抖音号id")
    private String awemeId;
	/**主页作品列表隐藏广告内容允选值：OFF（不隐藏），ON（隐藏）默认值：OFF*/
    @ApiModelProperty(value = "主页作品列表隐藏广告内容允选值：OFF（不隐藏），ON（隐藏）默认值：OFF")
    private String isFeedAndFavSee;
	/**原生锚点启用开关，允许值:不启用 OFF（默认值），自动生成 AUTO，手动选择 SELECT*/
    @ApiModelProperty(value = "原生锚点启用开关，允许值:不启用 OFF（默认值），自动生成 AUTO，手动选择 SELECT")
    private String anchorRelatedType;
	/**预算*/
    @ApiModelProperty(value = "预算")
    private BigDecimal budget;
	/**点击出价/展示出价*/
    @ApiModelProperty(value = "点击出价/展示出价")
    private BigDecimal bid;
	/**目标转化出价/预期成本*/
    @ApiModelProperty(value = "目标转化出价/预期成本")
    private BigDecimal cpaBid;
	/**深度优化出价*/
    @ApiModelProperty(value = "深度优化出价")
    private BigDecimal deepCpabid;
	/**深度转化ROI系数*/
    @ApiModelProperty(value = "深度转化ROI系数")
    private BigDecimal roiGoal;
}
