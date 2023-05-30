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
 * @Description: op_xingtu_creative
 * @Author: jeecg-boot
 * @Date:   2023-03-20
 * @Version: V1.0
 */
@Data
@TableName("op_xingtu_creative")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_xingtu_creative对象", description="op_xingtu_creative")
public class OpXingtuCreative implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private java.lang.Integer id;
	/**广告主ID*/
	@Excel(name = "广告主ID", width = 15)
    @ApiModelProperty(value = "广告主ID")
    @Dict(dictTable = "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2' ", dicText = "nick_name", dicCode = "id")
    private java.lang.Integer accountId;
	/**广告计划ID*/
	@Excel(name = "广告计划ID", width = 15)
    @ApiModelProperty(value = "广告计划ID")
    @Dict(dictTable = "open_gateway.op_xingtu_deal_plan ", dicText = "name", dicCode = "id")
    private java.lang.Integer adId;
	/**广告创意ID*/
	@Excel(name = "广告创意ID", width = 15)
    @ApiModelProperty(value = "广告创意ID")
    private java.lang.Long creativeId;
    /**创意方式*/
    @Excel(name = "创意方式", width = 15)
    @ApiModelProperty(value = "创意方式")
    private java.lang.String creativeMaterialMode;
//    /**自动生成视频素材*/
//    @Excel(name = "自动生成视频素材", width = 15)
//    @ApiModelProperty(value = "自动生成视频素材")
//    private java.lang.Integer isPresentedVideo;
    /**广告来源*/
    @Excel(name = "广告来源", width = 15)
    @ApiModelProperty(value = "广告来源")
    private java.lang.String source;
    /**是否开启来源智能生成*/
    @Excel(name = "是否开启来源智能生成，允许值：ON 开启，OFF 关闭", width = 15)
    @ApiModelProperty(value = "是否开启来源智能生成，允许值：ON 开启，OFF 关闭")
    private java.lang.String enableSmartSource;
    /**是否开启自动生成素材*/
    @Excel(name = "是否开启自动生成素材，大通投时可填，默认值: 1允许值: 0(不启用), 1(启用)", width = 15)
    @ApiModelProperty(value = "是否开启自动生成素材，大通投时可填，默认值: 1允许值: 0(不启用), 1(启用)")
    private java.lang.Integer creativeAutoGenerateSwitch;
    /**创意标签*/
    @Excel(name = "创意标签", width = 15)
    @ApiModelProperty(value = "创意标签")
    private java.lang.String adKeywords;
    /**主页作品列表隐藏广告内容*/
    @Excel(name = "主页作品列表隐藏广告内容，默认值：0允选值：0（不隐藏），1（隐藏）", width = 15)
    @ApiModelProperty(value = "主页作品列表隐藏广告内容，默认值：0允选值：0（不隐藏），1（隐藏）")
    private java.lang.Integer isFeedAndFavSee;
    /**三级行业ID*/
    @Excel(name = "三级行业ID", width = 15)
    @ApiModelProperty(value = "三级行业ID")
    private java.lang.String thirdIndustryId;
	/**素材类型*/
	@Excel(name = "素材类型", width = 15)
    @ApiModelProperty(value = "素材类型")
    private java.lang.String imageMode;
	/**标题信息*/
	@Excel(name = "标题信息", width = 15)
    @ApiModelProperty(value = "标题信息")
    private java.lang.String titleList;
	/**视频素材*/
	@Excel(name = "视频素材", width = 15)
    @ApiModelProperty(value = "视频素材")
    private java.lang.String videoMaterials;
	/**创意素材状态*/
	@Excel(name = "创意素材状态", width = 15)
    @ApiModelProperty(value = "创意素材状态")
    private java.lang.String status;
	/**创意素材操作状态*/
	@Excel(name = "创意素材操作状态", width = 15)
    @ApiModelProperty(value = "创意素材操作状态")
    private java.lang.String optStatus;
//	/**视频素材，封面图片ID*/
//	@Excel(name = "视频素材，封面图片ID", width = 15)
//    @ApiModelProperty(value = "视频素材，封面图片ID")
//    private java.lang.String imageId;
//	/**视频素材，视频ID*/
//	@Excel(name = "视频素材，视频ID", width = 15)
//    @ApiModelProperty(value = "视频素材，视频ID")
//    private java.lang.String videoId;
//	/**抖音视频ID*/
	@Excel(name = "抖音视频ID", width = 15)
    @ApiModelProperty(value = "抖音视频ID")
    private java.lang.String awemeItemId;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新用户*/
    @ApiModelProperty(value = "更新用户")
    private java.lang.String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
}
