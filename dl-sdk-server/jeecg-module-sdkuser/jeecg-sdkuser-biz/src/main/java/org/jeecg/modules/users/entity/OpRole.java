package org.jeecg.modules.users.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Description: op_role
 * @Author: jeecg-boot
 * @Date:   2022-11-30
 * @Version: V1.0
 */
@Data
@TableName("op_role")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_role对象", description="op_role")
public class OpRole implements Serializable {
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
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private java.lang.Integer userId;
	/**服务器id*/
	@Excel(name = "服务器id", width = 15)
    @ApiModelProperty(value = "服务器id")
    private java.lang.Integer serverId;
	/**服务器名*/
	@Excel(name = "服务器名", width = 15)
    @ApiModelProperty(value = "服务器名")
    private java.lang.String serverName;
	/**角色id*/
	@Excel(name = "角色id", width = 15)
    @ApiModelProperty(value = "角色id")
    private java.lang.String roleId;
	/**角色名*/
	@Excel(name = "角色名", width = 15)
    @ApiModelProperty(value = "角色名")
    private java.lang.String roleName;
	/**角色等级*/
	@Excel(name = "角色等级", width = 15)
    @ApiModelProperty(value = "角色等级")
    private java.lang.Integer roleLevel;
	/**注册-IP*/
	@Excel(name = "注册-IP", width = 15)
    @ApiModelProperty(value = "注册-IP")
    private java.lang.String createIp;
	/**注册-设备*/
	@Excel(name = "注册-设备", width = 15)
    @ApiModelProperty(value = "注册-设备")
    private java.lang.String createDevice;
	/**注册-时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册-时间")
    private java.util.Date createTime;
	/**更新-时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新-时间")
    private java.util.Date updateTime;
}
