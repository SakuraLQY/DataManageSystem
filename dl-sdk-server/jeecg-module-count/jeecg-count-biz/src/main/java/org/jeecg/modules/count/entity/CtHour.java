package org.jeecg.modules.count.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: ct_hour
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@TableName("ct_hour")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ct_hour对象", description="ct_hour")
public class CtHour implements Serializable {
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
	/**统计时间(精确到小时)*/
	@Excel(name = "统计时间(精确到小时)", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "统计时间(精确到小时)")
    private java.util.Date timeHour;
	/**新增-激活数*/
	@Excel(name = "新增-激活数", width = 15)
    @ApiModelProperty(value = "新增-激活数")
    private java.lang.Integer countActive;
    /**
     * 新增-激活数（按设备）
     */
    @Excel(name = "按设备统计激活数",width = 15)
    @ApiModelProperty(value = "按设备统计激活数")
    private java.lang.Integer countActiveDev;
	/**新增-注册数*/
	@Excel(name = "新增-注册数", width = 15)
    @ApiModelProperty(value = "新增-注册数")
    private java.lang.Integer countUser;
	/**新增-注册人数-按设备*/
	@Excel(name = "新增-注册人数-按设备", width = 15)
    @ApiModelProperty(value = "新增-注册人数-按设备")
    private java.lang.Integer countUserDev;
	/**新增-创角数*/
	@Excel(name = "新增-有效注册数", width = 15)
    @ApiModelProperty(value = "新增-有效注册数")
    private java.lang.Integer countValidUser;
	/**新增-创角数-按设备*/
	@Excel(name = "新增-有效注册数-按设备", width = 15)
    @ApiModelProperty(value = "新增-有效注册数-按设备")
    private java.lang.Integer countValidUserDev;
	/**新增-活跃用户*/
	@Excel(name = "新增-活跃用户", width = 15)
    @ApiModelProperty(value = "新增-活跃用户")
    private java.lang.Integer countDau;
	/**新增-活跃用户-按设备*/
	@Excel(name = "新增-活跃用户-按设备", width = 15)
    @ApiModelProperty(value = "新增-活跃用户-按设备")
    private java.lang.Integer countDauDev;
	/**首日-付费人数*/
	@Excel(name = "首日-付费人数", width = 15)
    @ApiModelProperty(value = "首日-付费人数")
    private java.lang.Integer firstPayuser;
	/**首日-付费人数-按设备*/
	@Excel(name = "首日-付费人数-按设备", width = 15)
    @ApiModelProperty(value = "首日-付费人数-按设备")
    private java.lang.Integer firstPayuserDev;
	/**首日-付费订单*/
	@Excel(name = "首日-付费订单", width = 15)
    @ApiModelProperty(value = "首日-付费订单")
    private java.lang.Integer firstOrder;
	/**首日-付费订单-按设备*/
	@Excel(name = "首日-付费订单-按设备", width = 15)
    @ApiModelProperty(value = "首日-付费订单-按设备")
    private java.lang.Integer firstOrderDev;
	/**首日-付费金额*/
	@Excel(name = "首日-付费金额", width = 15)
    @ApiModelProperty(value = "首日-付费金额")
    private java.math.BigDecimal firstMoney;
	/**首日-付费金额-按设备*/
	@Excel(name = "首日-付费金额-按设备", width = 15)
    @ApiModelProperty(value = "首日-付费金额-按设备")
    private java.math.BigDecimal firstMoneyDev;
	/**首日-付费优惠*/
	@Excel(name = "首日-付费优惠", width = 15)
    @ApiModelProperty(value = "首日-付费优惠")
    private java.math.BigDecimal firstMoneyDiscount;
	/**首日-付费优惠-按设备*/
	@Excel(name = "首日-付费优惠-按设备", width = 15)
    @ApiModelProperty(value = "首日-付费优惠-按设备")
    private java.math.BigDecimal firstMoneyDiscountDev;
	/**活跃-付费人数*/
	@Excel(name = "活跃-付费人数", width = 15)
    @ApiModelProperty(value = "活跃-付费人数")
    private java.lang.Integer alivePayuser;
	/**活跃-付费人数-按设备*/
	@Excel(name = "活跃-付费人数-按设备", width = 15)
    @ApiModelProperty(value = "活跃-付费人数-按设备")
    private java.lang.Integer alivePayuserDev;
	/**活跃-付费订单数*/
	@Excel(name = "活跃-付费订单数", width = 15)
    @ApiModelProperty(value = "活跃-付费订单数")
    private java.lang.Integer aliveOrder;
	/**活跃-付费订单数-按设备*/
	@Excel(name = "活跃-付费订单数-按设备", width = 15)
    @ApiModelProperty(value = "活跃-付费订单数-按设备")
    private java.lang.Integer aliveOrderDev;
	/**活跃-付费金额*/
	@Excel(name = "活跃-付费金额", width = 15)
    @ApiModelProperty(value = "活跃-付费金额")
    private java.math.BigDecimal aliveMoney;
	/**活跃-付费金额-按设备*/
	@Excel(name = "活跃-付费金额-按设备", width = 15)
    @ApiModelProperty(value = "活跃-付费金额-按设备")
    private java.math.BigDecimal aliveMoneyDev;
	/**活跃-付费优惠*/
	@Excel(name = "活跃-付费优惠", width = 15)
    @ApiModelProperty(value = "活跃-付费优惠")
    private java.math.BigDecimal aliveMoneyDiscount;
	/**活跃-付费优惠-按设备*/
	@Excel(name = "活跃-付费优惠-按设备", width = 15)
    @ApiModelProperty(value = "活跃-付费优惠-按设备")
    private java.math.BigDecimal aliveMoneyDiscountDev;
	/**累积-付费人数*/
	@Excel(name = "累积-付费人数", width = 15)
    @ApiModelProperty(value = "累积-付费人数")
    private java.lang.Integer totalPayuser;
	/**累积-付费人数-按设备*/
	@Excel(name = "累积-付费人数-按设备", width = 15)
    @ApiModelProperty(value = "累积-付费人数-按设备")
    private java.lang.Integer totalPayuserDev;
	/**累积-付费订单数*/
	@Excel(name = "累积-付费订单数", width = 15)
    @ApiModelProperty(value = "累积-付费订单数")
    private java.lang.Integer totalOrder;
	/**累积-付费订单数-按设备*/
	@Excel(name = "累积-付费订单数-按设备", width = 15)
    @ApiModelProperty(value = "累积-付费订单数-按设备")
    private java.lang.Integer totalOrderDev;
	/**累积-付费金额*/
	@Excel(name = "累积-付费金额", width = 15)
    @ApiModelProperty(value = "累积-付费金额")
    private java.math.BigDecimal totalMoney;
	/**累积-付费金额-按设备*/
	@Excel(name = "累积-付费金额-按设备", width = 15)
    @ApiModelProperty(value = "累积-付费金额-按设备")
    private java.math.BigDecimal totalMoneyDev;
	/**累计付费优惠*/
	@Excel(name = "累计付费优惠", width = 15)
    @ApiModelProperty(value = "累计付费优惠")
    private java.math.BigDecimal totalMoneyDiscount;
	/**累计付费优惠-按设备*/
	@Excel(name = "累计付费优惠-按设备", width = 15)
    @ApiModelProperty(value = "累计付费优惠-按设备")
    private java.math.BigDecimal totalMoneyDiscountDev;
	/**次日留存*/
	@Excel(name = "次日留存", width = 15)
    @ApiModelProperty(value = "次日留存")
    private java.lang.Integer loyal2;
	/**3日留存*/
	@Excel(name = "3日留存", width = 15)
    @ApiModelProperty(value = "3日留存")
    private java.lang.Integer loyal3;
	/**4日留存*/
	@Excel(name = "4日留存", width = 15)
    @ApiModelProperty(value = "4日留存")
    private java.lang.Integer loyal4;
	/**5日留存*/
	@Excel(name = "5日留存", width = 15)
    @ApiModelProperty(value = "5日留存")
    private java.lang.Integer loyal5;
	/**6日留存*/
	@Excel(name = "6日留存", width = 15)
    @ApiModelProperty(value = "6日留存")
    private java.lang.Integer loyal6;
	/**7日留存*/
	@Excel(name = "7日留存", width = 15)
    @ApiModelProperty(value = "7日留存")
    private java.lang.Integer loyal7;
	/**15日留存*/
	@Excel(name = "15日留存", width = 15)
    @ApiModelProperty(value = "15日留存")
    private java.lang.Integer loyal15;
	/**30日留存*/
	@Excel(name = "30日留存", width = 15)
    @ApiModelProperty(value = "30日留存")
    private java.lang.Integer loyal30;
	/**45日留存*/
	@Excel(name = "45日留存", width = 15)
    @ApiModelProperty(value = "45日留存")
    private java.lang.Integer loyal45;
	/**60日留存*/
	@Excel(name = "60日留存", width = 15)
    @ApiModelProperty(value = "60日留存")
    private java.lang.Integer loyal60;
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
}
