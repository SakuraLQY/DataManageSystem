package org.jeecg.modules.users.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "SDK登录返回对象", description = "SDK登录响应对象")
@Data
public class SdkUserLoginRes {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "sessionId")
    private String sessionId;

    @ApiModelProperty(value = "是否绑定手机 1已绑定 0未绑定")
    private Integer isBindPhone;

    @ApiModelProperty(value = "是否已实名 1已实名 0未实名")
    private Integer isIdAuth;

    @ApiModelProperty(value = "实名认证开关 1打开 0关闭")
    private Integer idAuthSwitch;

    @ApiModelProperty(value = "是否成年")
    private Integer isAdult;

    @ApiModelProperty(value = "未成年情况下剩余游戏时间")
    private Long remainingTime;

    @ApiModelProperty(value = "ios审核版本")
    private String iosAuditVersion;

}
