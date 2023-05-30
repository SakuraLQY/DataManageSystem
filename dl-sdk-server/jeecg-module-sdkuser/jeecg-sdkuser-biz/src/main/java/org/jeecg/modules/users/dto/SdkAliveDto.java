package org.jeecg.modules.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "SDK角色上报对象", description = "SDK角色上报对象")
public class SdkAliveDto {

    @NotNull(message = "子游戏id不能为空")
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @NotNull(message = "一级游戏包ID不能为空")
    @ApiModelProperty(value = "一级游戏包ID")
    private Integer pkgId;

    @NotNull(message = "游戏id不能为空")
    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;

    @NotNull(message = "渠道id不能为空")
    @ApiModelProperty(value = "渠道id")
    private Integer channelId;

    @ApiModelProperty(value = "子渠道ID")
    private Integer subChannelId;

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @NotBlank(message = "角色id不能为空")
    @ApiModelProperty(value = "角色id")
    private String roleId;

    @NotBlank(message = "角色名不能为空")
    @ApiModelProperty(value = "角色名")
    private String roleName;

    @NotNull(message = "角色名等级不能为空")
    @ApiModelProperty(value = "角色等级")
    private Integer roleLevel;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String device;

    @NotBlank(message = "区服id不能为空")
    @ApiModelProperty(value = "区服id")
    private Integer serverId;

    @NotBlank(message = "区服名不能为空")
    @ApiModelProperty(value = "区服名")
    private String serverName;

    @ApiModelProperty(value = "广告id")
    private Integer dealId;

    @ApiModelProperty(value = "设备ip")
    private String clientIp;

    @NotBlank(message = "签名不能为空")
    @ApiModelProperty(value = "签名")
    private String sign;

}
