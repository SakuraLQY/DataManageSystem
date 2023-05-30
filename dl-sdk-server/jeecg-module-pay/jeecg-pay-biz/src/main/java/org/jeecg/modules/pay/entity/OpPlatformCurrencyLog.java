package org.jeecg.modules.pay.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_platform_currency_log
 * @Author: jeecg-boot
 * @Date:   2022-12-21
 * @Version: V1.0
 */
@Data
@TableName("op_platform_currency_log")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_platform_currency_log对象", description="op_platform_currency_log")
public class OpPlatformCurrencyLog implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**游戏ID*/
	@Excel(name = "游戏ID", width = 15)
    @ApiModelProperty(value = "游戏ID")
    private java.lang.Integer GameId;
    /**子游戏ID*/
    @Excel(name = "子游戏ID", width = 15)
    @ApiModelProperty(value = "子游戏ID")
    private java.lang.Integer subGameId;
    /**一级游戏包ID*/
    @Excel(name = "一级游戏包ID", width = 15)
    @ApiModelProperty(value = "一级游戏包ID")
    private java.lang.Integer pkgId;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private java.lang.Integer userId;
	/**订单ID*/
	@Excel(name = "订单ID", width = 15)
    @ApiModelProperty(value = "订单ID")
    private java.lang.String orderId;
	/**充值金额*/
	@Excel(name = "充值金额", width = 15)
    @ApiModelProperty(value = "充值金额")
    private java.math.BigDecimal money;
	/**平台币*/
	@Excel(name = "平台币", width = 15)
    @ApiModelProperty(value = "平台币")
    private java.math.BigDecimal couponPrice;
	/**平台币使用状态：1为暂时抵扣，2为成功消耗，3为返回*/
	@Excel(name = "平台币使用状态：1为暂时抵扣，2为成功消耗，3为返回", width = 15)
    @ApiModelProperty(value = "平台币使用状态：1为暂时抵扣，2为成功消耗，3为返回")
    private java.lang.Integer status;
	/**创建时间*/
    @Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**使用时间*/
    @Excel(name = "使用时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "使用时间")
    private java.util.Date useTime;
	/**返还时间戳*/
    @Excel(name = "返还时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "返还时间")
    private java.util.Date returnTime;
}
