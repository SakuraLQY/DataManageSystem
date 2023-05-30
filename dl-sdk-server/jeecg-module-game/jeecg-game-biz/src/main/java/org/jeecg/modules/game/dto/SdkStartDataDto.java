package org.jeecg.modules.game.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="SDK激活", description="SDK安卓激活")
public class SdkStartDataDto {

    @NotNull(message="子游戏id不能为空")
    @ApiModelProperty(value = "子游戏Id")
    private Integer subGameId;

    @NotNull(message="广告id不能为空")
    @ApiModelProperty(value = "广告id")
    private Integer dealId;

    @NotNull(message="一级游戏包id不能为空")
    @ApiModelProperty(value = "一级游戏包ID")
    private Integer pkgId;

    @NotNull(message="唯一设备标识不能为空")
    @ApiModelProperty(value = "唯一设备标识")
    private String uniqueId;

    @NotNull(message="设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String deviceId;

    @ApiModelProperty(value = "安卓id")
    private String androidId;

    @ApiModelProperty(value = "序列号")
    private String serialId;

    @ApiModelProperty(value = "操作系统")
    private String devOs;

    @ApiModelProperty(value = "操作系统版本")
    private String devOsVer;

    @ApiModelProperty(value = "设备型号")
    private String devModel;

    @ApiModelProperty(value = "游戏应用包名")
    private String pkgName;

    @ApiModelProperty(value = "游戏应用包版本号")
    private String pkgVersionCode;

    @ApiModelProperty(value = "游戏应用包版本名")
    private String pkgVersionName;

    @ApiModelProperty(value = "sdk版本")
    private String sdkVer;

    @NotNull(message="时间戳不能为空")
    @ApiModelProperty(value = "时间戳(毫秒)")
    private Long time;

    @ApiModelProperty(value = "ip")
    private String clientIp;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "是否第一次激活")
    private Integer firstActive;

}
