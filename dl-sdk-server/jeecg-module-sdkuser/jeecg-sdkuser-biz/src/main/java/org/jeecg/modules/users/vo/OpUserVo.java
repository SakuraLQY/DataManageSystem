package org.jeecg.modules.users.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Author lili
 * @Description 用户列表
 * @Date 9:43 2022/12/16
 **/
@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class OpUserVo implements Serializable {

    private static final long serialVersionUID = -7710899965534481357L;

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "身份证ID")
    private java.lang.String realNumber;

    @ApiModelProperty(value = "用户信息-手机")
    private String userPhone;

    @ApiModelProperty(value = "注册-时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date signupTime;

    @ApiModelProperty(value = "注册-IP")
    private String signupIp;

    @ApiModelProperty(value = "注册-设备")
    private String signupDevice;

    @ApiModelProperty(value = "登录-时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date signinTime;

    @ApiModelProperty(value = "登录-IP")
    private String signinIp;

    @ApiModelProperty(value = "登录-设备")
    private String signinDevice;

    @ApiModelProperty(value = "充值-时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date chargeTime;

    @ApiModelProperty(value = "平台币")
    private BigDecimal platformCurrency;

}
