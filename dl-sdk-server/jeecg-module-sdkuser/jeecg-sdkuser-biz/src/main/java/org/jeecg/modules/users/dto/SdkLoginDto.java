package org.jeecg.modules.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "SDK登录对象", description = "SDK登录对象")
public class SdkLoginDto {

    @NotNull(message = "子游戏id不能为空")
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @NotNull(message = "一级游戏包id不能为空")
    @ApiModelProperty(value = "一级游戏包id")
    private Integer pkgId;

    @ApiModelProperty(value = "用户名/手机号")
    private String name;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String device;

    @NotBlank(message = "令牌不能为空")
    @ApiModelProperty(value = "令牌,规则：md5(subGameId+gameKey+password+time)")
    private String token;

    @ApiModelProperty(value = "广告id")
    private Integer dealId;

    @ApiModelProperty(value = "设备ip")
    private String clientIp;

    @NotBlank(message = "签名不能为空")
    @ApiModelProperty(value = "签名")
    private String sign;

    @NotNull(message = "时间不能为空")
    @ApiModelProperty(value = "时间")
    private Long time;

}
