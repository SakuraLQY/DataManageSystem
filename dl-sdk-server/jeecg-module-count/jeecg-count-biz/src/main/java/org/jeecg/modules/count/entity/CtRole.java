package org.jeecg.modules.count.entity;

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
@TableName("ct_role")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_role对象", description="ct_role")
public class CtRole implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**广告ID*/
    @Excel(name = "广告ID", width = 15)
    @ApiModelProperty(value = "广告ID")
    @Dict(dictTable = "open_gateway.op_deal", dicText = "deal_name", dicCode = "id")
    private java.lang.Integer dealId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private java.lang.Integer userId;
    /**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private java.lang.Integer gameId;
    /**子游戏*/
    @Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private java.lang.Integer subGameId;
    /**渠道游戏包*/
    @Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    @Dict(dictTable = "open_gateway.op_pkg", dicText = "pkg_name", dicCode = "id")
    private java.lang.Integer pkgId;
    /**渠道ID*/
    @Excel(name = "渠道ID", width = 15)
    @ApiModelProperty(value = "渠道ID")
    @Dict(dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    private java.lang.Integer channelId;
    /**渠道类型id*/
    @Excel(name = "渠道类型id", width = 15)
    @ApiModelProperty(value = "渠道类型id")
    @Dict(dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
    private java.lang.Integer channelTypeId;
    /**渠道子账号id*/
    @Excel(name = "渠道子账号id", width = 15)
    @ApiModelProperty(value = "渠道子账号id")
    @Dict(dictTable = "open_gateway.op_channel_sub_account", dicText = "sub_account_name", dicCode = "id")
    private java.lang.Integer channelSubAccountId;
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
	/**激活时间*/
	@Excel(name = "激活时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "激活时间")
    private java.util.Date aliveTime;
	/**在线总时长*/
	@Excel(name = "在线总时长", width = 15)
    @ApiModelProperty(value = "在线总时长")
    private java.lang.Integer onlineTime;
}
