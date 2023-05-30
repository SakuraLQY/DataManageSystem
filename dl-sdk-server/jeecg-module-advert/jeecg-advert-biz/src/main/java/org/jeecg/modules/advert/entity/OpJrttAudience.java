package org.jeecg.modules.advert.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: op_jrtt_audience
 * @Author: jeecg-boot
 * @Date:   2023-02-20
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_audience")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_jrtt_audience对象", description="op_jrtt_audience")
public class OpJrttAudience implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**定向包ID*/
	@Excel(name = "定向包ID", width = 15)
    @ApiModelProperty(value = "定向包ID")
    private java.lang.Long audiencePackageId;
	/**定向包名称*/
	@Excel(name = "定向包名称", width = 15)
    @ApiModelProperty(value = "定向包名称")
    private java.lang.String name;
	/**定向包描述*/
	@Excel(name = "定向包描述", width = 15)
    @ApiModelProperty(value = "定向包描述")
    private java.lang.String description;
	/**定向包类型*/
	@Excel(name = "定向包类型", width = 15)
    @Dict(dicCode = "landing_type")
    @ApiModelProperty(value = "定向包类型")
    private java.lang.String landingType;
	/**投放账号*/
	@Excel(name = "投放账号", width = 15, dictTable = "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2'", dicText = "nick_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2'", dicText = "nick_name", dicCode = "id")
    @ApiModelProperty(value = "投放账号")
    private java.lang.Integer accountId;
	/**广告投放范围*/
	@Excel(name = "广告投放范围", width = 15)
    @ApiModelProperty(value = "广告投放范围")
    private java.lang.String deliveryRange;
	/**地域*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "地域", width = 15)
    @ApiModelProperty(value = "地域")
    private java.lang.String district;
	/**性别*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "性别", width = 15)
    @ApiModelProperty(value = "性别")
    private java.lang.String gender;
	/**年龄*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "年龄", width = 15)
    @ApiModelProperty(value = "年龄")
    private java.lang.String age;
	/**自定义人群*/
	@Excel(name = "自定义人群", width = 15)
    @ApiModelProperty(value = "自定义人群")
    private java.lang.String retargetingTagsConf;
	/**穿山甲媒体定向*/
	@Excel(name = "穿山甲媒体定向", width = 15)
    @ApiModelProperty(value = "穿山甲媒体定向")
    private java.lang.String superiorPopularityType;
	/**行为兴趣*/
	@Excel(name = "行为兴趣", width = 15)
    @ApiModelProperty(value = "行为兴趣")
    private java.lang.String interestActionMode;
	/**抖音达人*/
	@Excel(name = "抖音达人", width = 15)
    @ApiModelProperty(value = "抖音达人")
    private java.lang.String awemeFanConf;
	/**过滤高活跃用户*/
	@Excel(name = "过滤高活跃用户", width = 15)
    @ApiModelProperty(value = "过滤高活跃用户")
    private java.lang.Integer filterAwemeAbnormalActive;
	/**过滤自己的粉丝*/
	@Excel(name = "过滤自己的粉丝", width = 15)
    @ApiModelProperty(value = "过滤自己的粉丝")
    private java.lang.Integer filterOwnAwemeFans;
	/**过滤高关注数用户*/
	@Excel(name = "过滤高关注数用户", width = 15)
    @ApiModelProperty(value = "过滤高关注数用户")
    private java.lang.Integer filterAwemeFansCount;
	/**平台*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "平台", width = 15)
    @ApiModelProperty(value = "平台")
    private java.lang.String platform;
	/**设备类型*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "设备类型", width = 15)
    @ApiModelProperty(value = "设备类型")
    private java.lang.String deviceType;
	/**网络*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "网络", width = 15)
    @ApiModelProperty(value = "网络")
    private java.lang.String ac;
	/**已安装用户*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "已安装用户", width = 15)
    @ApiModelProperty(value = "已安装用户")
    private java.lang.Integer hideIfExists;
	/**手机品牌*/
	@Excel(name = "手机品牌", width = 15)
    @ApiModelProperty(value = "手机品牌")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private java.lang.String deviceBrand;
	/**手机价格*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "手机价格", width = 15)
    @ApiModelProperty(value = "手机价格")
    private java.lang.String launchPrice;
	/**智能放量*/
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	@Excel(name = "智能放量", width = 15)
    @ApiModelProperty(value = "智能放量")
    private java.lang.String autoExtendTargets;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	/**受众最低ios版本(当推广应用下载iOS时选填,其余情况不填)		*/
	@Excel(name = "受众最低ios版本(当推广应用下载iOS时选填,其余情况不填)		", width = 15)
    @ApiModelProperty(value = "受众最低ios版本(当推广应用下载iOS时选填,其余情况不填)		")
    private java.lang.String iosOsv;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	/**受众运营商, 仅通信行业可用*/
	@Excel(name = "受众运营商, 仅通信行业可用", width = 15)
    @ApiModelProperty(value = "受众运营商, 仅通信行业可用")
    private java.lang.String carrier;
	/**受众最低android版本(当推广应用下载Android时选填,其余情况不填)*/
	@Excel(name = "受众最低android版本(当推广应用下载Android时选填,其余情况不填)", width = 15)
    @ApiModelProperty(value = "受众最低android版本(当推广应用下载Android时选填,其余情况不填)")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private java.lang.String androidOsv;
	/**受众文章分类*/
	@Excel(name = "受众文章分类", width = 15)
    @ApiModelProperty(value = "受众文章分类")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private java.lang.String articleCategory;
	/**行为和兴趣参数。当interest_action_mode传CUSTOM时有效*/
	@Excel(name = "行为和兴趣参数。当interest_action_mode传CUSTOM时有效", width = 15)
    @ApiModelProperty(value = "行为和兴趣参数。当interest_action_mode传CUSTOM时有效")
    private java.lang.String interestActionConf;
	/**districtConf*/
	@Excel(name = "districtConf", width = 15)
    @ApiModelProperty(value = "districtConf")
    private java.lang.String districtConf;
	/**定向逻辑和排除定向逻辑*/
	@Excel(name = "定向逻辑和排除定向逻辑", width = 15)
    @ApiModelProperty(value = "定向逻辑和排除定向逻辑")
    private java.lang.String superiorPopularityTypeConf;
	/**过滤已转化用户*/
	@Excel(name = "过滤已转化用户", width = 15)
    @ApiModelProperty(value = "过滤已转化用户")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private java.lang.String hideIfConverted;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
	/**过滤时间范围。当 hide_if_converted = APP或CUSTOMER或ORGANIZATION时必填，默认为THREE_MONTH*/
	@Excel(name = "过滤时间范围。当 hide_if_converted = APP或CUSTOMER或ORGANIZATION时必填，默认为THREE_MONTH", width = 15)
    @ApiModelProperty(value = "过滤时间范围。当 hide_if_converted = APP或CUSTOMER或ORGANIZATION时必填，默认为THREE_MONTH")
    private java.lang.String convertedTimeDuration;
	/**地图位置.district为BUSINESS_DISTRICT才有效*/
	@Excel(name = "地图位置.district为BUSINESS_DISTRICT才有效", width = 15)
    @ApiModelProperty(value = "地图位置.district为BUSINESS_DISTRICT才有效")
    private java.lang.String geolocation;
	/**（抖音推广特有）账号粉丝相似人群（添加抖音账号，会将广告投放给对应账号的相似人群粉丝）*/
	@Excel(name = "（抖音推广特有）账号粉丝相似人群（添加抖音账号，会将广告投放给对应账号的相似人群粉丝）", width = 15)
    @ApiModelProperty(value = "（抖音推广特有）账号粉丝相似人群（添加抖音账号，会将广告投放给对应账号的相似人群粉丝）")
    private java.lang.String awemeFansNumbers;
    /**创建用户*/
    @ApiModelProperty(value = "创建用户")
    @Dict(dictTable = "sys_user", dicText = "username", dicCode = "username")
    private java.lang.String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
