package org.jeecg.modules.count.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_role
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="导出表格对象", description="导出表格对象")
public class CtRoleModal implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
    /**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private java.lang.String gameName;
    /**子游戏*/
    @Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private java.lang.String subGameName;
    /**渠道游戏包*/
    @Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private java.lang.String pkgName;
    /**渠道ID*/
    @Excel(name = "渠道", width = 15)
    @ApiModelProperty(value = "渠道")
    private java.lang.String channelName;
    /**广告ID*/
    @Excel(name = "广告ID", width = 15)
    @ApiModelProperty(value = "广告ID")
    private Integer dealId;
    /**广告名*/
    @Excel(name = "广告名", width = 15)
    @ApiModelProperty(value = "广告名")
    private java.lang.String dealName;
	/**用户id*/
    @Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**账号*/
    @Excel(name = "账号", width = 15)
    @ApiModelProperty(value = "账号")
    private String userName;
    /**角色id*/
    @Excel(name = "角色ID", width = 15)
    @ApiModelProperty(value = "角色ID")
    private String roleId;
    /**角色名*/
    @Excel(name = "角色名", width = 15)
    @ApiModelProperty(value = "角色名")
    private String roleName;
    /**角色等级*/
    @Excel(name = "角色等级", width = 15)
    @ApiModelProperty(value = "角色等级")
    private Integer roleLevel;
	/**服务器id*/
    @Excel(name = "服务器ID", width = 15)
    @ApiModelProperty(value = "服务器ID")
    private Integer serverId;
	/**服务器名*/
    @Excel(name = "服务器名", width = 15)
    @ApiModelProperty(value = "服务器名")
    private String serverName;
    /**在线总时长*/
    @Excel(name = "在线总时长", width = 15)
    @ApiModelProperty(value = "在线总时长")
    private Integer onlineTime;
    /**注册-时间*/
    @Excel(name = "注册时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册时间")
    private java.util.Date createTime;
    /**最新登录时间*/
    @Excel(name = "最新登录时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最新登录时间")
    private java.util.Date aliveTime;
}
