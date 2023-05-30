package org.jeecg.modules.advert.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.advert.bo.opXingtuCreative.CreativeVideoMeterials;
import org.jeecg.modules.advert.bo.opXingtuCreative.TitleList;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_xingtu_creative
 * @Author: jeecg-boot
 * @Date:   2023-03-20
 * @Version: V1.0
 */
@Data
@ApiModel(value="前端传来对象", description="前端传来对象")
public class OpXingtuCreativeDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**广告主ID*/
	@Excel(name = "广告主ID", width = 15)
    @ApiModelProperty(value = "广告主ID")
    @Dict(dictTable = "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2' ", dicText = "nick_name", dicCode = "id")
    private Integer accountId;
	/**广告计划主键*/
	@Excel(name = "广告计划ID", width = 15)
    @ApiModelProperty(value = "广告计划ID")
    @Dict(dictTable = "open_gateway.op_xingtu_deal_plan ", dicText = "name", dicCode = "id")
    private Integer adId;
    /**广告计划ID*/
    @Excel(name = "广告计划ID", width = 15)
    @ApiModelProperty(value = "广告计划ID")
    private Long adIdLong;
    /**推广抖音号*/
    @Excel(name = "推广抖音号", width = 15)
    @ApiModelProperty(value = "推广抖音号")
    private String trillId;
	/**广告创意ID*/
	@Excel(name = "广告创意ID", width = 15)
    @ApiModelProperty(value = "广告创意ID")
    private Integer creativeId;
    /**创意方式*/
    @Excel(name = "创意方式", width = 15)
    @ApiModelProperty(value = "创意方式")
    private String creativeMaterialMode;
//    /**自动生成视频素材*/
//    @Excel(name = "自动生成视频素材", width = 15)
//    @ApiModelProperty(value = "自动生成视频素材")
//    private Integer isPresentedVideo;
    /**广告来源*/
    @Excel(name = "广告来源", width = 15)
    @ApiModelProperty(value = "广告来源")
    private String source;
    /**是否开启来源智能生成*/
    @Excel(name = "是否开启来源智能生成，允许值：ON 开启，OFF 关闭", width = 15)
    @ApiModelProperty(value = "是否开启来源智能生成，允许值：ON 开启，OFF 关闭")
    private String enableSmartSource;
    /**是否开启自动生成素材*/
    @Excel(name = "是否开启自动生成素材，大通投时可填，默认值: 1允许值: 0(不启用), 1(启用)", width = 15)
    @ApiModelProperty(value = "是否开启自动生成素材，大通投时可填，默认值: 1允许值: 0(不启用), 1(启用)")
    private Integer creativeAutoGenerateSwitch;
    /**创意标签*/
    @Excel(name = "创意标签", width = 15)
    @ApiModelProperty(value = "创意标签")
    private List<String> adKeywords;
    /**主页作品列表隐藏广告内容*/
    @Excel(name = "主页作品列表隐藏广告内容，默认值：0允选值：0（不隐藏），1（隐藏）", width = 15)
    @ApiModelProperty(value = "主页作品列表隐藏广告内容，默认值：0允选值：0（不隐藏），1（隐藏）")
    private Integer isFeedAndFavSee;
    /**三级行业ID*/
    @Excel(name = "三级行业ID", width = 15)
    @ApiModelProperty(value = "三级行业ID")
    private List<Integer> thirdIndustryId;
	/**素材类型*/
	@Excel(name = "素材类型", width = 15)
    @ApiModelProperty(value = "素材类型")
    private String imageMode;
	/**标题信息*/
	@Excel(name = "标题信息", width = 15)
    @ApiModelProperty(value = "标题信息")
    private List<TitleList> titleList;
	/**视频素材*/
	@Excel(name = "视频素材", width = 15)
    @ApiModelProperty(value = "视频素材")
    private List<CreativeVideoMeterials> videoMaterials;
	/**创意素材状态*/
	@Excel(name = "创意素材状态", width = 15)
    @ApiModelProperty(value = "创意素材状态")
    private String status;
	/**创意素材操作状态*/
	@Excel(name = "创意素材操作状态", width = 15)
    @ApiModelProperty(value = "创意素材操作状态")
    private String optStatus;
//	/**抖音视频ID*/
	@Excel(name = "抖音视频ID", width = 15)
    @ApiModelProperty(value = "抖音视频ID")
    private String awemeItemId;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新用户*/
    @ApiModelProperty(value = "更新用户")
    private String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
