package org.jeecg.modules.pay.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_order_count
 * @Author: jeecg-boot
 * @Date:   2022-12-21
 * @Version: V1.0
 */
@Data
@TableName("op_order_count")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ApiModel(value="op_order_count对象", description="op_order_count")
public class OpOrderCount implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**游戏id*/
	@Excel(name = "游戏id", width = 15)
    @ApiModelProperty(value = "游戏id")
    private java.lang.Integer gameId;
	/**玩家id*/
	@Excel(name = "玩家id", width = 15)
    @ApiModelProperty(value = "玩家id")
    private java.lang.Integer userId;
	/**订单数量*/
	@Excel(name = "订单数量", width = 15)
    @ApiModelProperty(value = "订单数量")
    private java.lang.Integer number;
	/**累计金额*/
	@Excel(name = "累计金额", width = 15)
    @ApiModelProperty(value = "累计金额")
    private BigDecimal totalMoney;

	public OpOrderCount (Integer number,BigDecimal totalMoney){
	    this.number = number;
	    this.totalMoney = totalMoney;
    }
}
