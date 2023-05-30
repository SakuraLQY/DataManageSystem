package org.jeecg.modules.game.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="SDK初始化", description="SDK-ios初始化")
public class SdkIosConfDto {

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @ApiModelProperty(value = "一级游戏包ID")
    private Integer pkgId;

    @ApiModelProperty(value = "唯一设备标识")
    private String device;

    @ApiModelProperty(value = "游戏应用包名")
    private String pkgName;

    @ApiModelProperty(value = "游戏应用包版本")
    private String pkgVer;

    @ApiModelProperty(value = "系统")
    private String os;

    @ApiModelProperty(value = "sdk版本")
    private String sdkVer;

    @ApiModelProperty(value = "時間")
    private String time;

    @ApiModelProperty(value = "广告id")
    private String dealId;

    @ApiModelProperty(value = "广告标识")
    private String idfa;

    @ApiModelProperty(value = "是否为首次激活 0否 1是")
    private Integer firstActive;

    @ApiModelProperty(value = "设备ip")
    private String clientIp;

}
