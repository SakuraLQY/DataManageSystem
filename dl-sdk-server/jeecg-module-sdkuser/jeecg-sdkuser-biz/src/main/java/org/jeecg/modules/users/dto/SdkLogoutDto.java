package org.jeecg.modules.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "SDK登出对象", description = "SDK登出对象")
public class SdkLogoutDto {

    @NotNull(message = "子游戏id不能为空")
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @NotNull(message = "一级游戏包id不能为空")
    @ApiModelProperty(value = "一级游戏包id")
    private Integer pkgId;

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String device;

    @ApiModelProperty(value = "广告id")
    private Integer dealId;

    @ApiModelProperty(value = "设备ip")
    private String clientIp;

    @NotBlank(message = "签名不能为空")
    @ApiModelProperty(value = "签名")
    private String sign;

}
