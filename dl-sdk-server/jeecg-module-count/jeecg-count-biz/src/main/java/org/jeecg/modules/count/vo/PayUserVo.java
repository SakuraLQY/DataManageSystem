package org.jeecg.modules.count.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
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
@ApiModel(value="传给前端对象-付费用户数据", description="传给前端对象-付费用户数据")
public class PayUserVo implements Serializable {
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
    private String gameName;
    /**广告名*/
    @Excel(name = "广告名", width = 15)
    @ApiModelProperty(value = "广告名")
    private String dealName;
    /**子游戏*/
    @Excel(name = "子游戏", width = 15)
    @ApiModelProperty(value = "子游戏")
    private String subGameName;
    /**渠道游戏包*/
    @Excel(name = "渠道游戏包", width = 15)
    @ApiModelProperty(value = "渠道游戏包")
    private String pkgName;
    /**渠道ID*/
    @Excel(name = "渠道ID", width = 15)
    @ApiModelProperty(value = "渠道ID")
    private String channelName;
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
	/**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    @Dict(dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
    private Integer channelTypeId;
	/**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    @Dict(dictTable = "open_gateway.op_channel_sub_account", dicText = "sub_account_name", dicCode = "id")
    private Integer channelSubAccountId;
	/**充值时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "充值时间")
    private Date payTime;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**首次支付时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "首次支付时间")
    private Date firstPayTime;
    /**手机号*/
    @Excel(name = "手机号", width = 15)
    @ApiModelProperty(value = "手机号")
    private String userPhone;
    /**在线时长*/
    @ApiModelProperty(value = "在线时长")
    private String onlineTimeStr;
    /**充值总额*/
    @Excel(name = "充值总额", width = 15)
    @ApiModelProperty(value = "充值总额")
    private BigDecimal totalMoney;

}
