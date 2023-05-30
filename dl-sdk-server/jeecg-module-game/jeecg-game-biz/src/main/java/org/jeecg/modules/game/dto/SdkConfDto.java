package org.jeecg.modules.game.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="SDK初始化", description="SDK安卓初始化")
public class SdkConfDto {

    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @ApiModelProperty(value = "一级游戏包ID")
    private Integer pkgId;

    @ApiModelProperty(value = "接口版本")
    private String ver;

    @ApiModelProperty(value = "唯一设备标识")
    private String device;

    @ApiModelProperty(value = "游戏应用包名")
    @JSONField(name="pkg_name")
    private String pkgName;

    @ApiModelProperty(value = "游戏应用包版本")
    @JSONField(name="pkg_ver")
    private String pkgVer;

    @ApiModelProperty(value = "用戶ID")
    @JSONField(name="user_id")
    private Integer userId;

    @ApiModelProperty(value = "系统")
    private String os;

    @ApiModelProperty(value = "sdk版本")
    @JSONField(name="sdk_ver")
    private String sdkVer;

    @ApiModelProperty(value = "時間")
    private String time;

    @ApiModelProperty(value = "广告id")
    private String dealId;

    @ApiModelProperty(value = "游戏id")
    private String gameId;

    @ApiModelProperty(value = "渠道id")
    private String channelId;

    @ApiModelProperty(value = "子渠道id")
    private String subChannelId;

}
