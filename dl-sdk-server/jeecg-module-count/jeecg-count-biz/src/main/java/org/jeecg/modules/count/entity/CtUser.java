package org.jeecg.modules.count.entity;

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
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@TableName("ct_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_user对象", description="ct_user")
public class CtUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
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
	/**广告ID*/
	@Excel(name = "广告ID", width = 15)
    @ApiModelProperty(value = "广告ID")
    @Dict(dictTable = "open_gateway.op_deal", dicText = "deal_name", dicCode = "id")
    private java.lang.Integer dealId;
	/**设备ID*/
	@Excel(name = "设备ID", width = 15)
    @ApiModelProperty(value = "设备ID")
    private java.lang.String uniqueId;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private java.lang.Integer userId;
	/**首服ID*/
	@Excel(name = "首服ID", width = 15)
    @ApiModelProperty(value = "首服ID")
    private java.lang.Integer serverInit;
	/**新服ID*/
	@Excel(name = "新服ID", width = 15)
    @ApiModelProperty(value = "新服ID")
    private java.lang.Integer serverLast;
	/**在线时长*/
	@Excel(name = "在线时长", width = 15)
    @ApiModelProperty(value = "在线时长")
    private java.lang.Integer onlineTime;
	/**注册时间*/
	@Excel(name = "注册时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册时间")
    private java.util.Date registerTime;
	/**最近登录时间*/
	@Excel(name = "最近登录时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最近登录时间")
    private java.util.Date loginTime;
	/**最新登录IP*/
	@Excel(name = "最新登录IP", width = 15)
    @ApiModelProperty(value = "最新登录IP")
    private java.lang.String clientIp;
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
	/**当天在线时长*/
	@Excel(name = "当天在线时长", width = 15)
    @ApiModelProperty(value = "当天在线时长")
    private java.lang.Integer dayOnlineTime;
	/**登陆标记*/
	@Excel(name = "登陆标记", width = 15)
    @ApiModelProperty(value = "登陆标记")
    private java.lang.Long loginMask;
	/**充值时间*/
	@Excel(name = "充值时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "充值时间")
    private java.util.Date payTime;
	/**上报时间*/
	@Excel(name = "上报时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "上报时间")
    private java.util.Date aliveTime;
    /**游戏时间*/
    @Excel(name = "游戏时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "游戏时间")
    private java.util.Date playTime;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;
	/**首次支付时间*/
	@Excel(name = "首次支付时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "首次支付时间")
    private java.util.Date firstPayTime;
	/**用户支付留存*/
	@Excel(name = "用户支付留存", width = 15)
    @ApiModelProperty(value = "用户支付留存")
    private java.lang.Long payUserLoginMask;
}
