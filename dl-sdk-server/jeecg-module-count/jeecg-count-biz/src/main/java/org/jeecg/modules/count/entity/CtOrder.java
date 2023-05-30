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
 * @Description: ct_order
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
@Data
@TableName("ct_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_order对象", description="ct_order")
public class CtOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**游戏id*/
	@Excel(name = "游戏id", width = 15)
    @ApiModelProperty(value = "游戏id")
    private java.lang.Integer gameId;
	/**子游戏id*/
	@Excel(name = "子游戏id", width = 15)
    @ApiModelProperty(value = "子游戏id")
    private java.lang.Integer subGameId;
	/**渠道游戏包id*/
	@Excel(name = "渠道游戏包id", width = 15)
    @ApiModelProperty(value = "渠道游戏包id")
    private java.lang.Integer pkgId;
	/**渠道类型id*/
	@Excel(name = "渠道类型id", width = 15)
    @ApiModelProperty(value = "渠道类型id")
    private java.lang.Integer channelTypeId;
	/**渠道id*/
	@Excel(name = "渠道id", width = 15)
    @ApiModelProperty(value = "渠道id")
    private java.lang.Integer channelId;
	/**渠道子账号id*/
	@Excel(name = "渠道子账号id", width = 15)
    @ApiModelProperty(value = "渠道子账号id")
    private java.lang.Integer channelSubAccountId;
	/**广告-id*/
	@Excel(name = "广告-id", width = 15)
    @ApiModelProperty(value = "广告-id")
    private java.lang.Integer dealId;
	/**设备id*/
	@Excel(name = "设备id", width = 15)
    @ApiModelProperty(value = "设备id")
    private java.lang.String uniqueId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private java.lang.Integer userId;
	/**首服id*/
	@Excel(name = "首服id", width = 15)
    @ApiModelProperty(value = "首服id")
    private java.lang.Integer serverInit;
	/**区服id*/
	@Excel(name = "区服id", width = 15)
    @ApiModelProperty(value = "区服id")
    private java.lang.Integer serverId;
	/**充值类型*/
	@Excel(name = "充值类型", width = 15)
    @ApiModelProperty(value = "充值类型")
    private java.lang.Integer payType;
	/**充值金额*/
	@Excel(name = "充值金额", width = 15)
    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;
	/**订单id*/
	@Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private java.lang.String orderId;
	/**ip地址*/
	@Excel(name = "ip地址", width = 15)
    @ApiModelProperty(value = "ip地址")
    private java.lang.String clientIp;
	/**日志时间*/
	@Excel(name = "日志时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "日志时间")
    private java.util.Date logTime;
	/**时间-创建*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间-创建")
    private java.util.Date createTime;
	/**时间-更新*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "时间-更新")
    private java.util.Date updateTime;
	/**折扣*/
	@Excel(name = "折扣", width = 15)
    @ApiModelProperty(value = "折扣")
    private BigDecimal couponPrice;
	/**订单类型*/
	@Excel(name = "订单类型", width = 15)
    @ApiModelProperty(value = "订单类型")
    private java.lang.Integer orderType;
	/**角色id*/
	@Excel(name = "角色id", width = 15)
    @ApiModelProperty(value = "角色id")
    private java.lang.String roleId;
	/**充值角色等级*/
	@Excel(name = "充值角色等级", width = 15)
    @ApiModelProperty(value = "充值角色等级")
    private java.lang.Integer roleLevel;
	/**充值时角色在线时长*/
	@Excel(name = "充值时角色在线时长", width = 15)
    @ApiModelProperty(value = "充值时角色在线时长")
    private java.lang.Integer onlineTime;
	/**支付渠道id*/
	@Excel(name = "支付渠道id", width = 15)
    @ApiModelProperty(value = "支付渠道id")
    private java.lang.Integer payVendorId;
	/**区域*/
	@Excel(name = "区域", width = 15)
    @ApiModelProperty(value = "区域")
    private java.lang.String region;
    /**时间-更新*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "设备创建日期")
    private java.util.Date deviceCreateTime;
    /**时间-更新*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "用户创建日期")
    private java.util.Date userCreateTime;

}
