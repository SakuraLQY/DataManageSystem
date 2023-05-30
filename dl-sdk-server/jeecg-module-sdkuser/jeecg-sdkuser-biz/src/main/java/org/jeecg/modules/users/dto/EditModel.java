package org.jeecg.modules.users.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "前端返回对象", description = "前端返回对象")
public class EditModel {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "密码")
    private String userPassword;

    @ApiModelProperty(value = "平台币")
    private String platformCurrency;

}
