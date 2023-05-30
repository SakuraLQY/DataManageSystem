package org.jeecg.modules.pay.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
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
 * @Description: op_order
 * @Author: jeecg-boot
 * @Date:   2022-12-21
 * @Version: V1.0
 */
@Data
@TableName("op_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_order对象", description="op_order")
public class OpOrder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**游戏ID*/
	@Excel(name = "游戏ID", width = 15)
    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;
    /**子游戏ID*/
    @Excel(name = "子游戏ID", width = 15)
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;
    /**子游戏ID*/
    @Excel(name = "一级游戏包ID", width = 15)
    @ApiModelProperty(value = "一级游戏包ID")
    private Integer pkgId;
	/**用户ID*/
	@Excel(name = "用户ID", width = 15)
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
	/**订单ID*/
	@Excel(name = "订单ID", width = 15)
    @ApiModelProperty(value = "订单ID")
    private String orderId;
	/**充值金额*/
	@Excel(name = "充值金额", width = 15)
    @ApiModelProperty(value = "充值金额")
    private BigDecimal money;
	/**交易状态*/
	@Excel(name = "交易状态", width = 15)
    @ApiModelProperty(value = "交易状态")
    private Integer status;
	/**测试标记*/
	@Excel(name = "测试标记", width = 15)
    @ApiModelProperty(value = "测试标记")
    private Integer isTest;
	/**开始时间戳*/
	@Excel(name = "开始时间戳", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间戳")
    private LocalDateTime openTime;
	/**状态时间戳*/
	@Excel(name = "状态时间戳", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "状态时间戳")
    private LocalDateTime statusTime;
	/**银行-类型*/
	@Excel(name = "银行-类型", width = 15)
    @ApiModelProperty(value = "银行-类型")
    private Integer bankType;
	/**实际金额*/
	@Excel(name = "实际金额", width = 15)
    @ApiModelProperty(value = "实际金额")
    private BigDecimal bankMoney;
	/**银行-货币类型*/
	@Excel(name = "银行-货币类型", width = 15)
    @ApiModelProperty(value = "银行-货币类型")
    private String bankMoneyType;
	/**银行-订单ID*/
	@Excel(name = "银行-订单ID", width = 15)
    @ApiModelProperty(value = "银行-订单ID")
    private String bankOrderId;
	/**银行-状态*/
	@Excel(name = "银行-状态", width = 15)
    @ApiModelProperty(value = "银行-状态")
    private Integer bankStatus;
	/**银行-状态时间戳*/
	@Excel(name = "银行-状态时间戳", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "银行-状态时间戳")
    private LocalDateTime bankStatusTime;
	/**游戏-订单ID*/
	@Excel(name = "游戏-订单ID", width = 15)
    @ApiModelProperty(value = "游戏-订单ID")
    private String gameOrderId;
	/**游戏-自定义参数*/
	@Excel(name = "游戏-自定义参数", width = 15)
    @ApiModelProperty(value = "游戏-自定义参数")
    private String gameData;
	/**游戏-状态*/
	@Excel(name = "游戏-状态", width = 15)
    @ApiModelProperty(value = "游戏-状态")
    private Integer gameStatus;
	/**游戏-状态时间戳*/
	@Excel(name = "游戏-状态时间戳", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "游戏-状态时间戳")
    private LocalDateTime gameStatusTime;
	/**游戏-发货重试次数*/
	@Excel(name = "游戏-发货重试次数", width = 15)
    @ApiModelProperty(value = "游戏-发货重试次数")
    private Integer gameDeliverRetry;
	/**游戏-发货重试时间戳*/
	@Excel(name = "游戏-发货重试时间戳", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "游戏-发货重试时间戳")
    private LocalDateTime gameDeliverTime;
	/**extraData1*/
	@Excel(name = "extraData1", width = 15)
    @ApiModelProperty(value = "extraData1")
    private String extraData1;
	/**orderType*/
	@Excel(name = "orderType", width = 15)
    @ApiModelProperty(value = "orderType")
    private Integer orderType;
	/**couponPrice*/
	@Excel(name = "couponPrice", width = 15)
    @ApiModelProperty(value = "couponPrice")
    private BigDecimal couponPrice;
	/**iosPkgVer*/
	@Excel(name = "iosPkgVer", width = 15)
    @ApiModelProperty(value = "iosPkgVer")
    private String iosPkgVer;
    /**ip*/
    @ApiModelProperty(value = "client_ip")
    private String clientIp;
    /**地区*/
    @ApiModelProperty(value = "地区")
    private String region;
    /**地区*/
    @ApiModelProperty(value = "支付厂商id")
    private Integer payVendorId;
}
