package org.jeecg.modules.advert.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_jrtt_audience
 * @Author: jeecg-boot
 * @Date:   2023-02-20
 * @Version: V1.0
 */
@Data
@ApiModel(value="返回前端对象", description="返回前端对象")
public class OpJrttAudienceVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
	/**定向包ID*/
    @ApiModelProperty(value = "定向包ID")
    private Long audiencePackageId;
	/**定向包名称*/
    @ApiModelProperty(value = "定向包名称")
    private String name;
	/**定向包描述*/
    @ApiModelProperty(value = "定向包描述")
    private String description;
	/**定向包类型*/
    @ApiModelProperty(value = "定向包类型")
    @Dict(dicCode = "landing_type")
    private String landingType;
	/**投放账号*/
	@Dict(dictTable = "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2'", dicText = "nick_name", dicCode = "id")
    @ApiModelProperty(value = "投放账号")
    private Integer accountId;
	/**广告投放范围*/
    @ApiModelProperty(value = "广告投放范围")
    private String deliveryRange;
	/**地域*/
    @ApiModelProperty(value = "地域")
    private String district;
	/**性别*/
    @ApiModelProperty(value = "性别")
    private String gender;
	/**年龄*/
    @ApiModelProperty(value = "年龄")
    private List<String> age;
	/**自定义人群*/
    @ApiModelProperty(value = "自定义人群")
    private Object retargetingTagsConf;
	/**穿山甲媒体定向*/
    @ApiModelProperty(value = "穿山甲媒体定向")
    private String superiorPopularityType;
	/**行为兴趣*/
    @ApiModelProperty(value = "行为兴趣")
    private String interestActionMode;
	/**抖音达人*/
    @ApiModelProperty(value = "抖音达人")
    private Object awemeFanConf;
	/**过滤高活跃用户*/
    @ApiModelProperty(value = "过滤高活跃用户")
    private Integer filterAwemeAbnormalActive;
	/**过滤自己的粉丝*/
    @ApiModelProperty(value = "过滤自己的粉丝")
    private Integer filterOwnAwemeFans;
	/**过滤高关注数用户*/
    @ApiModelProperty(value = "过滤高关注数用户")
    private Integer filterAwemeFansCount;
	/**平台*/
    @ApiModelProperty(value = "平台")
    private List<String> platform;
	/**设备类型*/
    @ApiModelProperty(value = "设备类型")
    private List<String> deviceType;
	/**网络*/
    @ApiModelProperty(value = "网络")
    private List<String> ac;
	/**已安装用户*/
    @ApiModelProperty(value = "已安装用户")
    private Integer hideIfExists;
	/**手机品牌*/
    @ApiModelProperty(value = "手机品牌")
    private List<String> deviceBrand;
	/**手机价格*/
    @ApiModelProperty(value = "手机价格")
    private List<String> launchPrice;
	/**智能放量*/
    @ApiModelProperty(value = "智能放量")
    private List<String> autoExtendTargets;
	/**受众最低ios版本(当推广应用下载iOS时选填,其余情况不填)		*/
    @ApiModelProperty(value = "受众最低ios版本(当推广应用下载iOS时选填,其余情况不填)		")
    private String iosOsv;
	/**受众运营商, 仅通信行业可用*/
    @ApiModelProperty(value = "受众运营商, 仅通信行业可用")
    private List<String> carrier;
	/**受众最低android版本(当推广应用下载Android时选填,其余情况不填)*/
    @ApiModelProperty(value = "受众最低android版本(当推广应用下载Android时选填,其余情况不填)")
    private String androidOsv;
	/**受众文章分类*/
    @ApiModelProperty(value = "受众文章分类")
    private List<String> articleCategory;
	/**行为和兴趣参数。当interest_action_mode传CUSTOM时有效*/
    @ApiModelProperty(value = "行为和兴趣参数。当interest_action_mode传CUSTOM时有效")
    private Object interestActionConf;
	/**districtConf*/
    @ApiModelProperty(value = "districtConf")
    private Object districtConf;
	/**定向逻辑和排除定向逻辑*/
    @ApiModelProperty(value = "定向逻辑和排除定向逻辑")
    private Object superiorPopularityTypeConf;
	/**过滤已转化用户*/
    @ApiModelProperty(value = "过滤已转化用户")
    private String hideIfConverted;
	/**过滤时间范围。当 hide_if_converted = APP或CUSTOMER或ORGANIZATION时必填，默认为THREE_MONTH*/
    @ApiModelProperty(value = "过滤时间范围。当 hide_if_converted = APP或CUSTOMER或ORGANIZATION时必填，默认为THREE_MONTH")
    private String convertedTimeDuration;
	/**地图位置.district为BUSINESS_DISTRICT才有效*/
    @ApiModelProperty(value = "地图位置.district为BUSINESS_DISTRICT才有效")
    private String geolocation;
	/**（抖音推广特有）账号粉丝相似人群（添加抖音账号，会将广告投放给对应账号的相似人群粉丝）*/
    @ApiModelProperty(value = "（抖音推广特有）账号粉丝相似人群（添加抖音账号，会将广告投放给对应账号的相似人群粉丝）")
    private List<String> awemeFansNumbers;
    /**创建用户*/
    @ApiModelProperty(value = "创建用户")
    @Dict(dictTable = "sys_user", dicText = "username", dicCode = "username")
    private String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
