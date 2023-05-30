package org.jeecg.modules.users.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SDK实名认证返回对象", description = "SDK实名认证返回对象")
@Data
public class SdkUserIdAuthRes {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "是否成年 0否 1是")
    private Integer isAdult;

    @ApiModelProperty(value = "剩余时间")
    private Long remainingTime;

}
