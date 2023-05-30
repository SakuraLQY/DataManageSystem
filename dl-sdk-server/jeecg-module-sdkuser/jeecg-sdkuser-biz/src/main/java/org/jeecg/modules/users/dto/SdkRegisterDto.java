package org.jeecg.modules.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="SDK注册对象", description="SDK注册对象")
public class SdkRegisterDto {

    @NotNull(message = "子游戏id不能为空")
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @NotNull(message = "一级游戏包id不能为空")
    @ApiModelProperty(value = "一级游戏包id")
    private Integer pkgId;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码 ")
    private String userPassword;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String device;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @ApiModelProperty(value = "广告id")
    private Integer dealId;

    @ApiModelProperty(value = "设备ip")
    private String clientIp;

    @NotNull(message = "注册类型不能为空")
    @ApiModelProperty(value = "注册类型 2用户名注册 3手机号注册")
    private Integer registerType;

    @NotNull(message = "签名不能为空")
    @ApiModelProperty(value = "签名")
    private String sign;
}
