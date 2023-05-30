package org.jeecg.modules.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 功能描述
 *
 * @author: xmh
 * @date: 2022/12/1 16:55
 */
@Data
@ApiModel(value="SDK接口返回对象", description="SDK接口返回对象")
public class IdAuthDTO {

    @ApiModelProperty(value = "游戏ID")
    private Integer app_id;

    @ApiModelProperty(value = "唯一设备标识")
    private String device;

    @ApiModelProperty(value = "广告ID")
    private Long tag1;

    @ApiModelProperty(value = "游戏ID")
    private Integer tag2;

    @ApiModelProperty(value = "渠道ID")
    private Integer tag3;

    @ApiModelProperty(value = "子渠道ID")
    private Long tag4;

    @ApiModelProperty(value = "接口版本")
    private Integer ver;

    @ApiModelProperty(value = "time")
    private Long time;

    @ApiModelProperty(value = "加密参数")
    private String sign;

    @ApiModelProperty(value = "身份证号码")
    private String id_number;

   @ApiModelProperty(value = "真实姓名")
    private String real_name;

   @ApiModelProperty(value = "用户ID")
    private String user_id;
}
