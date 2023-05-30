package org.jeecg.modules.users.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
 * @Description: op_user_online
 * @Author: jeecg-boot
 * @Date:   2022-12-02
 * @Version: V1.0
 */
@Data
@TableName("op_user_online")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_user_online对象", description="op_user_online")
public class OpUserOnline implements Serializable {
    private static final long serialVersionUID = 1L;

    /**id*/
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**子游戏id*/
    @Excel(name = "子游戏id", width = 15)
    @ApiModelProperty(value = "子游戏id")
    private java.lang.Integer subGameId;
    /**一级游戏包id*/
    @Excel(name = "一级游戏包id", width = 15)
    @ApiModelProperty(value = "一级游戏包id")
    private java.lang.Integer pkgId;
    /**用户id*/
    @Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private java.lang.Integer userId;
    /**游戏备案标识码*/
    @Excel(name = "游戏备案标识码", width = 15)
    @ApiModelProperty(value = "游戏备案标识码")
    private java.lang.String bizId;
    /**登录时间*/
    @Excel(name = "时间戳", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登出时间")
    private LocalDateTime loginTime;
    /**游戏内部会话标识*/
    @Excel(name = "游戏内部会话标识", width = 15)
    @ApiModelProperty(value = "游戏内部会话标识")
    private java.lang.String si;
    /**游客设备标识*/
    @Excel(name = "游客设备标识", width = 15)
    @ApiModelProperty(value = "游客设备标识")
    private java.lang.String di;
    /**实名认证*/
    @Excel(name = "实名认证", width = 15)
    @ApiModelProperty(value = "实名认证")
    private java.lang.String pi;

    /**登出时间*/
    @Excel(name = "时间戳", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登出时间")
    private LocalDateTime logoutTime;

    /**上报状态，0上报登录，1上报登出，2上报完成*/
    @Excel(name = "0上报登录，1上报登出，2上报完成", width = 15)
    @ApiModelProperty(value = "0上报登录，1上报登出，2上报完成")
    private java.lang.Integer state;

    /**重试次数*/
    @Excel(name = "重试次数", width = 15)
    @ApiModelProperty(value = "重试次数")
    private java.lang.Integer retryTimes;
}
