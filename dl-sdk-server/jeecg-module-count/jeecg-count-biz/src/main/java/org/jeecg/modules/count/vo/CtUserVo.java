package org.jeecg.modules.count.vo;

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
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class CtUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
	/**游戏*/
    @ApiModelProperty(value = "游戏")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private Integer gameId;
	/**子游戏*/
    @ApiModelProperty(value = "子游戏")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private Integer subGameId;
	/**渠道游戏包*/
    @ApiModelProperty(value = "渠道游戏包")
    @Dict(dictTable = "open_gateway.op_pkg", dicText = "pkg_name", dicCode = "id")
    private Integer pkgId;
	/**渠道ID*/
    @ApiModelProperty(value = "渠道ID")
    @Dict(dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    private Integer channelId;
	/**广告ID*/
    @ApiModelProperty(value = "广告ID")
    @Dict(dictTable = "open_gateway.op_deal", dicText = "deal_name", dicCode = "id")
    private Integer dealId;
    /**游戏*/
    @Excel(name = "游戏", width = 15)
    @ApiModelProperty(value = "游戏")
    private java.lang.String gameName;
    /**广告名*/
    @Excel(name = "广告名", width = 15)
    @ApiModelProperty(value = "广告名")
    private java.lang.String dealName;
    /**子游戏*/
    @Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private java.lang.String subGameName;
    /**渠道游戏包*/
    @Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private java.lang.String pkgName;
    /**渠道ID*/
    @Excel(name = "渠道ID", width = 15)
    @ApiModelProperty(value = "渠道ID")
    private java.lang.String channelName;
	/**设备ID*/
    @ApiModelProperty(value = "设备ID")
    private String uniqueId;
	/**用户ID*/
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**账号*/
    @ApiModelProperty(value = "账号")
    private String userName;
	/**首服ID*/
    @ApiModelProperty(value = "首服ID")
    private Integer serverInit;
	/**新服ID*/
    @ApiModelProperty(value = "新服ID")
    private Integer serverLast;
	/**在线时长*/
    @ApiModelProperty(value = "在线时长")
    private Integer onlineTime;
    /**当天在线时长*/
    @ApiModelProperty(value = "当天在线时长")
    private String onlineTimeStr;
	/**注册时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册时间")
    private Date registerTime;
	/**最近登录时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最近登录时间")
    private Date loginTime;
	/**最新登录IP*/
    @ApiModelProperty(value = "最新登录IP")
    private String clientIp;
    /**IP归属地*/
    @ApiModelProperty(value = "IP归属地")
    private String ipPlace;
	/**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    @Dict(dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
    private Integer channelTypeId;
	/**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    @Dict(dictTable = "open_gateway.op_channel_sub_account", dicText = "sub_account_name", dicCode = "id")
    private Integer channelSubAccountId;
	/**当天在线时长*/
    @ApiModelProperty(value = "当天在线时长")
    private Integer dayOnlineTime;
	/**登陆标记*/
    @ApiModelProperty(value = "登陆标记")
    private Long loginMask;
	/**充值时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "充值时间")
    private Date payTime;
	/**上报时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上报时间")
    private Date aliveTime;
    /**游戏时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "游戏时间")
    private Date playTime;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**首次支付时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "首次支付时间")
    private Date firstPayTime;
	/**用户支付留存*/
	@Excel(name = "用户支付留存", width = 15)
    @ApiModelProperty(value = "用户支付留存")
    private Long payUserLoginMask;
    /**封号状态*/
    @Excel(name = "封号状态", width = 15)
    @ApiModelProperty(value = "封号状态")
    private Integer userState;
    /**封IP状态*/
    @Excel(name = "封IP状态", width = 15)
    @ApiModelProperty(value = "封IP状态")
    private Integer ipState;
    /**手机号*/
    @Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private String userPhone;
}
