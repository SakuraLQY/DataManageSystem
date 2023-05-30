package org.jeecg.modules.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_register_login_switch
 * @Author: jeecg-boot
 * @Date:   2022-11-30
 * @Version: V1.0
 */
@Data
@TableName("op_register_login_switch")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_register_login_switch对象", description="op_register_login_switch")
public class OpRegisterLoginSwitchVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
//    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer  id;
    /**类型*/
    @Excel(name = "类型", width = 15, dicCode = "rule_type")
    @Dict(dicCode = "rule_type")
    @ApiModelProperty(value = "类型")
    private Integer ruleType;
    /**规则ID*/
    @Excel(name = "规则ID", width = 15)
    @ApiModelProperty(value = "规则ID")
    private Integer ruleId;
    /**注册关闭类型*/
    @Excel(name = "注册关闭类型", width = 15, dicCode = "close_type")
    @Dict(dicCode = "close_type")
    @ApiModelProperty(value = "注册关闭类型")
    private Integer registerCloseType;
    /**注册规则类型*/
    @Excel(name = "注册规则类型", width = 15, dicCode = "register_rule_type")
    @Dict(dicCode = "register_rule_type")
    @ApiModelProperty(value = "注册规则类型")
    private Integer registerLimitSwitch;
    /**注册限制IP*/
    @Excel(name = "注册限制IP", width = 15)
    @ApiModelProperty(value = "注册限制IP")
    private String registerLimitIp;
    /**注册限制设备*/
    @Excel(name = "注册限制设备", width = 15)
    @ApiModelProperty(value = "注册限制设备")
    private String registerLimitDevice;
    /**登录关闭类型*/
    @Excel(name = "登录关闭类型", width = 15, dicCode = "close_type")
    @Dict(dicCode = "close_type")
    @ApiModelProperty(value = "登录关闭类型")
    private Integer loginCloseType;
    /**登录限制IP*/
    @Excel(name = "登录限制IP", width = 15)
    @ApiModelProperty(value = "登录限制IP")
    private String loginLimitIp;
    /**登录限制用户ID*/
    @Excel(name = "登录限制用户ID", width = 15)
    @ApiModelProperty(value = "登录限制用户ID")
    private String loginLimitUserId;
    /**登录限制设备*/
    @Excel(name = "登录限制设备", width = 15)
    @ApiModelProperty(value = "登录限制设备")
    private String loginLimitDevice;
//    /**登录限制开关*/
//    @Excel(name = "登录限制开关", width = 15, dicCode = "is_open")
//    @Dict(dicCode = "is_open")
//    @ApiModelProperty(value = "登录限制开关")
//    private java.lang.Integer loginLimitSwitch;
    /**限制跨登录开关*/
    @Excel(name = "限制跨登录开关", width = 15, dicCode = "is_open")
    @Dict(dicCode = "is_open")
    @ApiModelProperty(value = "限制跨登录开关")
    private Integer limitCrossLoginSwitch;
    /**备注*/
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String descs;
    /**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**更新用户*/
    @ApiModelProperty(value = "更新用户")
    private String updateBy;
    /**更新时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
