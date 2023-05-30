package org.jeecg.modules.users.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="SDK注册返回对象", description="SDK注册响应对象")
@Data
public class SdkUserRegisterRes {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}
