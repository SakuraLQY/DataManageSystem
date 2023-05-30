package org.jeecg.modules.game.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "返回SDK对象", description = "返回SDK对象")
public class PrivacyAndUserProtocolVo {

    @ApiModelProperty(value = "隐私政策地址")
    private String privacyPolicyUrl;

    @ApiModelProperty(value = "用户协议地址")
    private String userProtocolUrl;

}
