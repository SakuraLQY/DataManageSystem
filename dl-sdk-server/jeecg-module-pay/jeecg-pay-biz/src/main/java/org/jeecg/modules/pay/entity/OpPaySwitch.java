package org.jeecg.modules.pay.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: op_pay_switch
 * @Author: jeecg-boot
 * @Date:   2023-01-03
 * @Version: V1.0
 */
@Data
@TableName("op_pay_switch")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_pay_switch对象", description="op_pay_switch")
public class OpPaySwitch implements Serializable {
	private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**游戏*/
	@ApiModelProperty(value = "游戏")
	@Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
	private Integer gameId;
	/**子游戏*/
	@Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
	@ApiModelProperty(value = "子游戏")
	private Integer subGameId;
	/**渠道游戏包*/
	@Dict(dictTable = "open_gateway.op_pkg", dicText = "pkg_name", dicCode = "id")
	@ApiModelProperty(value = "渠道游戏包")
	private Integer pkgId;
//	/**游戏id*/
//	@Excel(name = "游戏id", width = 15, dictTable = "op_sub_game", dicText = "game_name", dicCode = "id")
//	@Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
//	@ApiModelProperty(value = "游戏id")
//	private java.lang.Integer gameId;
	/**游戏版本*/
	@Excel(name = "游戏版本", width = 15)
	@ApiModelProperty(value = "游戏版本")
	private java.lang.String gameVersion;
	/**游戏构建*/
	@Excel(name = "游戏构建", width = 15)
	@ApiModelProperty(value = "游戏构建")
	private java.lang.String gameBuild;
	/**默认支付*/
	@Excel(name = "默认支付", width = 15)
	@ApiModelProperty(value = "默认支付")
	private java.lang.String defaultPay;
	/**非默认支付*/
	@Excel(name = "非默认支付", width = 15)
	@ApiModelProperty(value = "非默认支付")
	private java.lang.String noDefaultPay;
	/**单笔订单金额*/
	@Excel(name = "单笔订单金额", width = 15)
	@ApiModelProperty(value = "单笔订单金额")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private java.math.BigDecimal orderMoney;
	/**充值次数*/
	@Excel(name = "充值次数", width = 15)
	@ApiModelProperty(value = "充值次数")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private java.lang.Integer rechargeTimes;
	/**累计充值金额*/
	@Excel(name = "累计充值金额", width = 15)
	@ApiModelProperty(value = "累计充值金额")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private java.math.BigDecimal totalMoney;
	/**时间范围*/
	@Excel(name = "时间范围", width = 15)
	@ApiModelProperty(value = "时间范围")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private java.lang.String rangeTime;

	/**时间范围*/
	@Excel(name = "白名单", width = 15)
	@ApiModelProperty(value = "白名单")
	private java.lang.String whiteList;

	/**时间范围*/
	@Excel(name = "黑名单", width = 15)
	@ApiModelProperty(value = "黑名单")
	private java.lang.String blackList;

	/**时间范围*/
	@Excel(name = "地区", width = 15)
	@ApiModelProperty(value = "地区")
	private java.lang.String regions;
}
