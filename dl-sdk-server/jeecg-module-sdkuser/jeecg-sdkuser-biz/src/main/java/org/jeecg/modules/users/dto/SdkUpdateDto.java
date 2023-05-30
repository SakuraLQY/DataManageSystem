package org.jeecg.modules.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "SDK更新对象", description = "SDK找回密码、修改密码、绑定手机、解绑手机对象")
public class SdkUpdateDto {

    @NotNull(message = "子游戏id不能为空")
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @ApiModelProperty(value = "验证码")
    private String code;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String device;

    @NotBlank(message = "令牌 updateType=1时必填")
    @ApiModelProperty(value = "令牌,规则：md5(subGameId+gameKey+password+time)")
    private String token;

    @ApiModelProperty(value = "手机号 updateType=2,4时必填")
    private String userPhone;

    @ApiModelProperty(value = "密码 updateType=1,2时必填")
    private String userPassword;

    @ApiModelProperty(value = "用户名 updateType=1,3时必填")
    private String userName;

    @ApiModelProperty(value = "广告id")
    private Integer dealId;

    @ApiModelProperty(value = "设备ip")
    private String clientIp;

    @NotBlank(message = "签名不能为空")
    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "时间")
    private Long time;

    @NotNull(message = "更新类型不能为空")
    @ApiModelProperty(value = "1修改密码 2重置密码 3绑定手机 4解绑手机")
    private Integer updateType;

}
