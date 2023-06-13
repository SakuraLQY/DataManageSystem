package org.jeecg.modules.game.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "返回SDK对象", description = "返回SDK对象")
public class PrivacyVo {

    @ApiModelProperty(value = "隐私协议开关 0关闭 1开启")
    private Integer privacySwitch;

}
