package org.jeecg.common.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description:
 * @date: 2023/2/14 11:22
 **/
@Data
@ApiModel(value = "今日头条点击广告", description = "今日头条点击广告")
public class JrttVisitDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "巨量广告体验版的广告项目id")
    private Long projectId;

    @ApiModelProperty(value = "巨量广告体验版的广告id")
    private Long promotionId;

    @ApiModelProperty(value = "广告组id")
    private Long campaignId;

    @ApiModelProperty(value = "广告计划id")
    private Long aid;

    @ApiModelProperty(value = "广告创意id")
    private Long cid;

    @ApiModelProperty(value = "IOS 6+的设备id字段，32位")
    private String idfa;

    @ApiModelProperty(value = "安卓id原值的md5，32位")
    private String androidId;

    @ApiModelProperty(value = "Android Q及更高版本的设备号，32位")
    private String oaid;

    @ApiModelProperty(value = "安卓的设备 ID 的 md5 摘要，32位")
    private String imei;

    @ApiModelProperty(value = "移动设备mac地址,转换成大写字母,去掉“:”，并且取md5摘要后的结果")
    private String mac;

    @ApiModelProperty(value = "操作系统平台 安卓：0，IOS：1，其他：3")
    private Integer os;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "客户端发生广告点击事件的时间")
    private Long time;

    @ApiModelProperty(value = "直接把调用事件回传接口的url生成出来，广告主可以直接使用")
    private String callbackUrl;

    @ApiModelProperty(value = "广告id")
    private Integer dealId;

}
