package org.jeecg.modules.users.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SDK注册返回对象", description = "SDK用户修改响应对象")
@Data
public class SdkVerifyRes {

    @ApiModelProperty(value = "身份证号")
    private String realNumber;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

}
