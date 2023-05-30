
package org.jeecg.modules.count.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@ApiModel(value="传给后端对象", description="传给后端对象")
public class PayUserDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
	/**游戏*/
    @ApiModelProperty(value = "游戏")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private List<Integer> gameId;
	/**子游戏*/
    @ApiModelProperty(value = "子游戏")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private List<Integer> subGameId;
	/**渠道游戏包*/
    @ApiModelProperty(value = "渠道游戏包")
    @Dict(dictTable = "open_gateway.op_pkg", dicText = "pkg_name", dicCode = "id")
    private List<Integer> pkgId;
    @ApiModelProperty(value = "渠道游戏包")
    private List<String> pkgIdList;
	/**渠道ID*/
    @ApiModelProperty(value = "渠道ID")
    @Dict(dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    private List<Integer> channelId;
	/**广告ID*/
    @ApiModelProperty(value = "广告ID")
    @Dict(dictTable = "open_gateway.op_deal", dicText = "deal_name", dicCode = "id")
    private Integer dealId;
	/**用户ID*/
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    /**账号*/
    @ApiModelProperty(value = "账号")
    private String userName;
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
    private List<Integer> channelTypeId;
	/**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    @Dict(dictTable = "open_gateway.op_channel_sub_account", dicText = "sub_account_name", dicCode = "id")
    private List<Integer> channelSubAccountId;
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
	/**用户支付留存*/
	@Excel(name = "用户支付留存", width = 15)
    @ApiModelProperty(value = "用户支付留存")
    private Long payUserLoginMask;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册开始时间")
    private Date regStartTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "注册结束时间")
    private Date regEndTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "登录开始时间")
    private Date logStartTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "登录结束时间")
    private Date logEndTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "充值开始时间")
    private Date payStartTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "充值结束时间")
    private Date payEndTime;
    /**查询类型*/
    @ApiModelProperty(value = "查询类型")
    private Integer type;

}
