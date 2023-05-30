package org.jeecg.modules.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="SDK短信验证码", description="SDK短信验证码")
public class SdkSendCodeDto {

    @NotNull(message = "子游戏id不能为空")
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String device;

    @ApiModelProperty(value = "设备ip")
    private String clientIp;

    @NotNull(message = "签名不能为空")
    @ApiModelProperty(value = "签名")
    private String sign;

}
