package org.jeecg.modules.users.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_user
 * @Author: jeecg-boot
 * @Date:   2022-11-30
 * @Version: V1.0
 */
@Data
@TableName("op_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_user对象", description="op_user")
public class OpUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**用户ID*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID")
    private java.lang.Integer id;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private java.lang.String userName;
	/**用户密码*/
	@Excel(name = "用户密码", width = 15)
    @ApiModelProperty(value = "用户密码")
    private java.lang.String userPassword;
	/**用户类型*/
	@Excel(name = "用户类型", width = 15)
    @ApiModelProperty(value = "用户类型")
    private java.lang.Integer userType;
	/**用户信息-手机*/
	@Excel(name = "用户信息-手机", width = 15)
    @ApiModelProperty(value = "用户信息-手机")
    private java.lang.String userPhone;
	/**用户信息-标签-广告*/
	@Excel(name = "用户信息-标签-广告", width = 15)
    @ApiModelProperty(value = "用户信息-标签-广告")
    private java.lang.Integer userTagDeal;
	/**用户信息-标签-子游戏*/
	@Excel(name = "用户信息-标签-子游戏", width = 15)
    @ApiModelProperty(value = "用户信息-标签-子游戏")
    private java.lang.Integer userTagSubGame;
    /**用户信息-标签-一级游戏包*/
    @Excel(name = "用户信息-标签-一级游戏包", width = 15)
    @ApiModelProperty(value = "用户信息-标签-一级游戏包")
    private java.lang.Integer userTagPkg;
	/**注册-时间*/
	@Excel(name = "注册-时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册-时间")
    private java.util.Date signupTime;
	/**注册-IP*/
	@Excel(name = "注册-IP", width = 15)
    @ApiModelProperty(value = "注册-IP")
    private java.lang.String signupIp;
	/**注册-设备*/
	@Excel(name = "注册-设备", width = 15)
    @ApiModelProperty(value = "注册-设备")
    private java.lang.String signupDevice;
	/**注册-来源*/
	@Excel(name = "注册-来源", width = 15)
    @ApiModelProperty(value = "注册-来源")
    private java.lang.String signupSource;
	/**登录-时间*/
	@Excel(name = "登录-时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登录-时间")
    private java.util.Date signinTime;
	/**登录-IP*/
	@Excel(name = "登录-IP", width = 15)
    @ApiModelProperty(value = "登录-IP")
    private java.lang.String signinIp;
	/**登录-设备*/
	@Excel(name = "登录-设备", width = 15)
    @ApiModelProperty(value = "登录-设备")
    private java.lang.String signinDevice;
	/**登录-来源*/
	@Excel(name = "登录-来源", width = 15)
    @ApiModelProperty(value = "登录-来源")
    private java.lang.String signinSource;
	/**充值-时间*/
    @Excel(name = "充值-时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "充值-时间")
    private java.util.Date chargeTime;
	/**平台币*/
	@Excel(name = "平台币", width = 15)
    @ApiModelProperty(value = "平台币")
    private java.math.BigDecimal platformCurrency;
	/**微信用户ID*/
	@Excel(name = "微信用户ID", width = 15)
    @ApiModelProperty(value = "微信用户ID")
    private java.lang.String extendedField;
}
