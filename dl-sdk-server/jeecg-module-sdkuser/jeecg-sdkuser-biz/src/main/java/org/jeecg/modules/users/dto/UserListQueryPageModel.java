package org.jeecg.modules.users.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "前端返回对象", description = "前端返回对象")
public class UserListQueryPageModel {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @ApiModelProperty(value = "当前页")
    private Integer page;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;
}
