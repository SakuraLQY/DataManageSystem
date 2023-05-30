package org.jeecg.modules.pay.dto;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: op_pay_switch
 * @Author: jeecg-boot
 * @Date:   2023-01-03
 * @Version: V1.0
 */
@Data
@ApiModel(value="op_pay_switch对象", description="op_pay_switch")
public class OpPaySwitchDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**id*/
	@ApiModelProperty(value = "id")
	private Integer id;
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
	@ApiModelProperty(value = "渠道游戏包")
	private List<String> pkgIdList;
	/**游戏版本*/
	@ApiModelProperty(value = "游戏版本")
	private String gameVersion;
	/**游戏构建*/
	@ApiModelProperty(value = "游戏构建")
	private String gameBuild;
	/**默认支付*/
	@ApiModelProperty(value = "默认支付")
	private String defaultPay;
	/**非默认支付*/
	@ApiModelProperty(value = "非默认支付")
	private String noDefaultPay;
	/**单笔订单金额*/
	@ApiModelProperty(value = "单笔订单金额")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private BigDecimal orderMoney;
	/**充值次数*/
	@ApiModelProperty(value = "充值次数")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer rechargeTimes;
	/**累计充值金额*/
	@ApiModelProperty(value = "累计充值金额")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private BigDecimal totalMoney;
	/**时间范围*/
	@ApiModelProperty(value = "时间范围")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String rangeTime;

	/**时间范围*/
	@ApiModelProperty(value = "白名单")
	private String whiteList;

	/**时间范围*/
	@ApiModelProperty(value = "黑名单")
	private String blackList;

	/**时间范围*/
	@ApiModelProperty(value = "地区")
	private String regions;
}
