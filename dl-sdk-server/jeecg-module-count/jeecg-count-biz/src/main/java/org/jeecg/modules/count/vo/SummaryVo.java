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
 * @Description: SummaryVo
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class SummaryVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private String id;
	/**游戏*/
    @ApiModelProperty(value = "名称")
    private String name;
	/**成本*/
    @ApiModelProperty(value = "成本")
    private BigDecimal costMoney = BigDecimal.ZERO;
    /**激活数*/
    @ApiModelProperty(value = "激活数")
    private Integer countActive = 0;
    /**激活设备数*/
    @ApiModelProperty(value = "激活设备数")
    private Integer countActiveDev = 0;
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer countUser = 0;
    /**注册设备数*/
    @ApiModelProperty(value = "注册设备数")
    private Integer countUserDev = 0;
    /**有效注册数*/
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser = 0;
    /**有效注册数-按设备*/
    @ApiModelProperty(value = "有效注册数-按设备")
    private Integer countValidUserDev = 0;
    /**激活注册率 注册数-an/激活数 */
    @ApiModelProperty(value = "激活注册率")
    private BigDecimal countActiveUserRate = BigDecimal.ZERO;
    /**激活注册率-按设备 注册数-按设备/激活数-按设备*/
    @ApiModelProperty(value = "激活注册率-按设备")
    private BigDecimal countActiveUserRateDev = BigDecimal.ZERO;
    /**注册单价 成本/新用户数*/
    @ApiModelProperty(value = "注册单价")
    private BigDecimal costCountUser = BigDecimal.ZERO;
    /**注册单价-按设备 成本/新用户数-按设备*/
    @ApiModelProperty(value = "注册单价-按设备")
    private BigDecimal costCountUserDev = BigDecimal.ZERO;
    /**首日付费额*/
    @ApiModelProperty(value = "首日付费额")
    private BigDecimal firstMoney = BigDecimal.ZERO;
    /**首日付费额-按设备*/
    @ApiModelProperty(value = "首日付费额-按设备")
    private BigDecimal firstMoneyDev = BigDecimal.ZERO;
    /**首日回款率  首日付费额/成本 firstMoney/costMoney */
    @ApiModelProperty(value = "首日回款率")
    private BigDecimal firstMoneyCostMoneyRate = BigDecimal.ZERO;
    /**首日回款率-按设备 首日付费额-按设备/成本  firstMoneyDev/costMoney */
    @ApiModelProperty(value = "首日回款率-按设备")
    private BigDecimal firstMoneyCostMoneyRateDev = BigDecimal.ZERO;
    /**首日付费人数 */
    @ApiModelProperty(value = "首日付费人数")
    private Integer firstPayuser = 0;
    /**首日付费人数-按设备*/
    @ApiModelProperty(value = "首日付费人数-按设备")
    private Integer firstPayuserDev = 0;
    /**首日付费单价 首日付费成本/首日付费人数 costMoney/firstPayUser */
    @ApiModelProperty(value = "首日付费单价")
    private BigDecimal firstPayuserCostMoney = BigDecimal.ZERO;
    /**首日付费单价 首日付费成本-按设备/首日付费人数-按设备 costMoney/firstPayUserDev */
    @ApiModelProperty(value = "首日付费单价-按设备")
    private BigDecimal firstPayuserCostMoneyDev = BigDecimal.ZERO;
    /**arpu 首日付费额/注册数 firstMoney/countUser*/
    @ApiModelProperty(value = "首日arpu")
    private BigDecimal firstArpu = BigDecimal.ZERO;
    /**arpu 首日付费额-按设备/注册数-按设备 firstMoneyDev/countUserDev*/
    @ApiModelProperty(value = "首日arpu-按设备")
    private BigDecimal firstArpuDev = BigDecimal.ZERO;
    /**arppu 首日付费额/首日回款率 firstMoney/firstPayuser */
    @ApiModelProperty(value = "首日arppu")
    private BigDecimal firstArppu = BigDecimal.ZERO;
    /**arppu 首日付费额-按设备/首日回款率-按设备 firstMoneyDev/firstPayUserDev */
    @ApiModelProperty(value = "首日arppu-按设备")
    private BigDecimal firstArppuDev = BigDecimal.ZERO;
    /**首日ROI 首日回本/成本 day1/cost */
    @ApiModelProperty(value = "首日ROI")
    private BigDecimal firstRoi = BigDecimal.ZERO;
    /**首日ROI-按设备 首日回本/成本 day1_dev/cost */
    @ApiModelProperty(value = "首日ROI-按设备")
    private BigDecimal firstRoiDev = BigDecimal.ZERO;
    /** 首日付费次数 */
    @ApiModelProperty(value = "首日付费次数")
    private Integer firstOrder = 0;
    /** 首日付费次数-按设备 */
    @ApiModelProperty(value = "首日付费次数-按设备")
    private Integer firstOrderDev = 0;
    /** 首日订单均值 首日付费额/首日付费次数  firstMoney/firstOrder */
    @ApiModelProperty(value = "首日订单均值")
    private BigDecimal firstMoneyFirstOrder = BigDecimal.ZERO;
    /** 首日订单均值 首日付费额/首日付费次数  firstMoneyDev/firstOrderDev */
    @ApiModelProperty(value = "首日订单均值-按设备")
    private BigDecimal firstMoneyFirstOrderDev = BigDecimal.ZERO;
    /** 首日付费次数单价 成本/首日付费次数  cost/firstOrder */
    @ApiModelProperty(value = "首日付费次数单价")
    private BigDecimal firstOrderCost = BigDecimal.ZERO;
    /** 首日付费次数单价 成本/首日付费次数  cost/firstOrderDev */
    @ApiModelProperty(value = "首日付费次数单价-按设备")
    private BigDecimal firstOrderCostDev = BigDecimal.ZERO;
    /** 老用户付费率 当日老用户付费人数 / 当日老用户总数 (alivePayUser-firstPayUser)/(countDau-countUser)*100*/
    @ApiModelProperty(value = "老用户付费率")
    private BigDecimal oldUserPayRate = BigDecimal.ZERO;
    /** 老用户付费率-按设备 (alivePayUserDev-firstPayUserDev)/(countDauDev-countUserDev)*100 */
    @ApiModelProperty(value = "老用户付费率-按设备")
    private BigDecimal oldUserPayRateDev = BigDecimal.ZERO;
    /** 老用户arpu (aliveMoney-firstMoney)/(countDau-countUser) */
    @ApiModelProperty(value = "老用户Arpu")
    private BigDecimal oldUserArpu = BigDecimal.ZERO;
    /** 老用户arpu-按设备 (aliveMoneyDev-firstMoneyDev)/(countDauDev-countUserDev)*/
    @ApiModelProperty(value = "老用户Arpu-按设备")
    private BigDecimal oldUserArpuDev = BigDecimal.ZERO;
    /** 老用户arppu (aliveMoney-firstMoney)/(alivePayuser-firstPayuser) */
    @ApiModelProperty(value = "老用户Arppu")
    private BigDecimal oldUserArppu = BigDecimal.ZERO;
    /** 老用户arppu-按设备 (aliveMoneyDev-firstMoneyDev)/(alivePayuserDev-firstPayuserDev)*/
    @ApiModelProperty(value = "老用户Arppu-按设备")
    private BigDecimal oldUserArppuDev = BigDecimal.ZERO;
    /** 周期付费额 选择付费日期的情况下 sum(ct_order.money)*/
    @ApiModelProperty(value = "周期付费额")
    private BigDecimal cycleMoney = BigDecimal.ZERO;
    /** 周期付费额 -按设备选择付费日期的情况下 sum(ct_order.money)*/
    @ApiModelProperty(value = "周期付费额-按设备")
    private BigDecimal cycleMoneyDev = BigDecimal.ZERO;
    /** 周期回本率 选择付费日期的情况下 (cycleMoney-cost)/cost */
    @ApiModelProperty(value = "周期回本率")
    private BigDecimal cycleMoneyCost = BigDecimal.ZERO;
    /** 周期回本率-按设备 选择付费日期的情况下 (cycleMoney-cost)/cost */
    @ApiModelProperty(value = "周期回本率-按设备")
    private BigDecimal cycleMoneyCostDev = BigDecimal.ZERO;
    /** 周期付费人数 选择付费日期的情况下 count(ct_order.user_id) */
    @ApiModelProperty(value = "周期付费人数")
    private Integer cycleUser = 0;
    /** 周期付费人数 选择付费日期的情况下 count(ct_order.unique_id) */
    @ApiModelProperty(value = "周期付费人数-按设备")
    private Integer cycleUserDev = 0;
    /** 周期付费率 选择付费日期的情况下 cycleUser/countUser */
    @ApiModelProperty(value = "周期付费率")
    private BigDecimal cycleUserCountUserRate = BigDecimal.ZERO;
    /** 周期付费率-按设备 选择付费日期的情况下 cycleUserDev/countUserDev */
    @ApiModelProperty(value = "周期付费率-按设备")
    private BigDecimal cycleUserCountUserRateDev = BigDecimal.ZERO;
    /** ARPU(周期) 选择付费日期的情况下 cycleMoney/countUser */
    @ApiModelProperty(value = "ARPU(周期)")
    private BigDecimal cycleArpu = BigDecimal.ZERO;
    /** ARPU(周期)-设备 选择付费日期的情况下 cycleMoneyDev/countUserDev */
    @ApiModelProperty(value = "ARPU(周期)-设备")
    private BigDecimal cycleArpuDev = BigDecimal.ZERO;
    /** ARPPU(周期) 选择付费日期的情况下 sum(ct_order.money)/count(ct_order.user_id) */
    @ApiModelProperty(value = "ARPPU(周期)")
    private BigDecimal cycleArppu = BigDecimal.ZERO;
    /** ARPPU(周期)-按设备 选择付费日期的情况下 sum(ct_order.money)/count(ct_order.uniqueId) */
    @ApiModelProperty(value = "ARPPU(周期)-设备")
    private BigDecimal cycleArppuDev = BigDecimal.ZERO;
    /** 累积付费额 */
    @ApiModelProperty(value = "累积付费额")
    private BigDecimal totalMoney = BigDecimal.ZERO;
    /** 累积付费额-按设备 */
    @ApiModelProperty(value = "累积付费额-按设备")
    private BigDecimal totalMoneyDev = BigDecimal.ZERO;
    /** 累积回本率 累积付费额/成本 totalMoney/costMoney */
    @ApiModelProperty(value = "累积回本率")
    private BigDecimal totalMoneyCostMoneyRate = BigDecimal.ZERO;
    /** 累积回本率 累积付费额/成本 totalMoney/costMoney */
    @ApiModelProperty(value = "累积回本率-dev")
    private BigDecimal totalMoneyCostMoneyRateDev = BigDecimal.ZERO;
    /** 累积付费人数 */
    @ApiModelProperty(value = "累积付费人数")
    private Integer totalPayuser = 0;
    /** 累积付费人数-按设备 */
    @ApiModelProperty(value = "累积付费人数-按设备")
    private Integer totalPayuserDev = 0;
    /** ARPU(累积) 累计付费额/日活 tatalMoney/countDau*/
    @ApiModelProperty(value = "ARPU(累积)")
    private BigDecimal totalArpu;
    /** ARPU(累积)-按设备 累计付费额-按设备/日活-按设备 tatalMoneyDev/countDauDev*/
    @ApiModelProperty(value = "ARPU(累积)-按设备")
    private BigDecimal totalArpuDev = BigDecimal.ZERO;
    /** ARPPU(累积) 累计付费额/累计付费人数 tatalMoney/totalPayuser*/
    @ApiModelProperty(value = "ARPPU(累积)")
    private BigDecimal totalArppu = BigDecimal.ZERO;
    /** ARPPU(累积) 累计付费额/累计付费人数 tatalMoneyDev/totalPayuserDev*/
    @ApiModelProperty(value = "ARPPU(累积)-按设备")
    private BigDecimal totalArppuDev = BigDecimal.ZERO;
    /** DAU 日活*/
    @ApiModelProperty(value = "DAU")
    private Integer countDau = 0;
    /** DAUDEV 日活-按设备*/
    @ApiModelProperty(value = "DAU-设备")
    private Integer countDauDev = 0;
    /** 活跃付费人数*/
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayuser = 0;
    /** 活跃付费人数-按设备*/
    @ApiModelProperty(value = "活跃付费人数-按设备")
    private Integer alivePayuserDev = 0;
    /** 活跃付费金额*/
    @ApiModelProperty(value = "活跃付费金额")
    private BigDecimal aliveMoney = BigDecimal.ZERO;
    /** 活跃付费金额-按设备*/
    @ApiModelProperty(value = "活跃付费金额-按设备")
    private BigDecimal aliveMoneyDev = BigDecimal.ZERO;
    /** 活跃付费次数*/
    @ApiModelProperty(value = "活跃付费次数")
    private Integer aliveOrder = 0;
    /** 活跃付费次数-按设备*/
    @ApiModelProperty(value = "活跃付费次数-按设备")
    private Integer aliveOrderDev = 0;
    /** 活跃订单均值 活跃付费金额/活跃付费次数*/
    @ApiModelProperty(value = "活跃订单均值")
    private BigDecimal aliveMoneyAliveOrderAlive = BigDecimal.ZERO;
    /** 活跃订单均值 活跃付费金额/活跃付费次数*/
    @ApiModelProperty(value = "活跃订单均值-按设备")
    private BigDecimal aliveMoneyAliveOrderAliveDev = BigDecimal.ZERO;
    /** 次日留存*/
    @ApiModelProperty(value = "次日留存")
    private BigDecimal retention2 = BigDecimal.ZERO;
    /** 3日留存*/
    @ApiModelProperty(value = "3日留存")
    private BigDecimal retention3 = BigDecimal.ZERO;
    /** 4日留存*/
    @ApiModelProperty(value = "4日留存")
    private BigDecimal retention4 = BigDecimal.ZERO;
    /** 5日留存*/
    @ApiModelProperty(value = "5日留存")
    private BigDecimal retention5 = BigDecimal.ZERO;
    /** 6日留存*/
    @ApiModelProperty(value = "6日留存")
    private BigDecimal retention6 = BigDecimal.ZERO;
    /** 7日留存*/
    @ApiModelProperty(value = "7日留存")
    private BigDecimal retention7 = BigDecimal.ZERO;
    /** 次日留存-按设备*/
    @ApiModelProperty(value = "次日留存-按设备")
    private BigDecimal retention2Dev = BigDecimal.ZERO;
    /** 3日留存-按设备*/
    @ApiModelProperty(value = "3日留存-按设备")
    private BigDecimal retention3Dev = BigDecimal.ZERO;
    /** 4日留存-按设备*/
    @ApiModelProperty(value = "4日留存-按设备")
    private BigDecimal retention4Dev = BigDecimal.ZERO;
    /** 5日留存-按设备*/
    @ApiModelProperty(value = "5日留存-按设备")
    private BigDecimal retention5Dev = BigDecimal.ZERO;
    /** 6日留存-按设备*/
    @ApiModelProperty(value = "6日留存-按设备")
    private BigDecimal retention6Dev = BigDecimal.ZERO;
    /** 7日留存-按设备*/
    @ApiModelProperty(value = "7日留存-按设备")
    private BigDecimal retention7Dev = BigDecimal.ZERO;
    /** ltv1*/
    @ApiModelProperty(value = "ltv1")
    private BigDecimal ltv1 = BigDecimal.ZERO;
    /** ltv2*/
    @ApiModelProperty(value = "ltv2")
    private BigDecimal ltv2 = BigDecimal.ZERO;
    /** ltv3*/
    @ApiModelProperty(value = "ltv3")
    private BigDecimal ltv3 = BigDecimal.ZERO;
    /** ltv4*/
    @ApiModelProperty(value = "ltv4")
    private BigDecimal ltv4 = BigDecimal.ZERO;
    /** ltv5*/
    @ApiModelProperty(value = "ltv5")
    private BigDecimal ltv5 = BigDecimal.ZERO;
    /** ltv6*/
    @ApiModelProperty(value = "ltv6")
    private BigDecimal ltv6 = BigDecimal.ZERO;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv7 = BigDecimal.ZERO;
    /** ltv1-按设备*/
    @ApiModelProperty(value = "ltv1-按设备")
    private BigDecimal ltv1Dev = BigDecimal.ZERO;
    /** ltv2-按设备*/
    @ApiModelProperty(value = "ltv2-按设备")
    private BigDecimal ltv2Dev = BigDecimal.ZERO;
    /** ltv3-按设备*/
    @ApiModelProperty(value = "ltv3-按设备")
    private BigDecimal ltv3Dev = BigDecimal.ZERO;
    /** ltv4-按设备*/
    @ApiModelProperty(value = "ltv4-按设备")
    private BigDecimal ltv4Dev = BigDecimal.ZERO;
    /** ltv5-按设备*/
    @ApiModelProperty(value = "ltv5-按设备")
    private BigDecimal ltv5Dev = BigDecimal.ZERO;
    /** ltv6-按设备*/
    @ApiModelProperty(value = "ltv6-按设备")
    private BigDecimal ltv6Dev = BigDecimal.ZERO;
    /** ltv7-按设备*/
    @ApiModelProperty(value = "ltv7-按设备")
    private BigDecimal ltv7Dev = BigDecimal.ZERO;
}
