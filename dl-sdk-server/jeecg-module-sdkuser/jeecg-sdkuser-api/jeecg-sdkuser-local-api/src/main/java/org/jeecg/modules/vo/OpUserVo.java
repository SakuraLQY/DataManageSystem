package org.jeecg.modules.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2022/12/23
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户信息", description="用户信息")
public class OpUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**用户ID*/
    private java.lang.Integer id;
    /**用户名*/
    private java.lang.String userName;
    /**用户密码*/
    private java.lang.String userPassword;
    /**用户类型*/
    private java.lang.Integer userType;
    /**用户信息-手机*/
    private java.lang.String userPhone;
    /**用户信息-标签-广告*/
    private java.lang.Integer userTagDeal;
    /**用户信息-标签-子游戏*/
    private java.lang.Integer userTagSubGame;
    /**注册-时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date signupTime;
    /**注册-IP*/
    private java.lang.String signupIp;
    /**注册-设备*/
    private java.lang.String signupDevice;
    /**注册-来源*/
    private java.lang.String signupSource;
    /**登录-时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date signinTime;
    /**登录-IP*/
    private java.lang.String signinIp;
    /**登录-设备*/
    private java.lang.String signinDevice;
    /**登录-来源*/
    private java.lang.String signinSource;
    /**充值-时间*/
    private java.util.Date chargeTime;
    /**平台币*/
    private java.math.BigDecimal platformCurrency;
    /**微信用户ID*/
    private java.lang.String extendedField;
}
