package org.jeecg.modules.count.bo.summary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: SummaryDailyBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class SummaryDailyBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;
    /**
     * name
     */
    private String name;
    /**
     * 激活数
     */
    private Integer countActive = 0;
    /**
     * 激活设备数
     */
    private Integer countActiveDev = 0;
    /**
     * 注册数
     */
    private Integer countUser = 0;
    /**
     * 注册设备数
     */
    private Integer countUserDev = 0;
    /**
     * 有效注册数
     */
    private Integer countValidUser = 0;
    /**
     * 有效注册数-按设备
     */
    private Integer countValidUserDev = 0;
    /**
     * 首日付费额
     */
    private BigDecimal firstMoney = BigDecimal.ZERO;
    /**
     * 首日付费额-按设备
     */
    private BigDecimal firstMoneyDev = BigDecimal.ZERO;
    /**
     * 首日付费人数
     */
    private Integer firstPayuser = 0;
    /**
     * 首日付费人数-按设备
     */
    private Integer firstPayuserDev = 0;
    /**
     * 首日付费次数
     */
    private Integer firstOrder = 0;
    /**
     * 首日付费次数-按设备
     */
    private Integer firstOrderDev = 0;
    /**
     * 累积付费额
     */
    private BigDecimal totalMoney = BigDecimal.ZERO;
    /**
     * 累积付费额-按设备
     */
    private BigDecimal totalMoneyDev = BigDecimal.ZERO;
    /**
     * 累积付费人数
     */
    private Integer totalPayuser = 0;
    /**
     * 累积付费人数
     */
    private Integer totalPayuserDev = 0;
    /**
     * DAU 日活
     */
    private Integer countDau = 0;
    /**
     * DAUDEV 日活-按设备
     */
    private Integer countDauDev = 0;
    /**
     * 活跃付费人数
     */
    private Integer alivePayuser = 0;
    /**
     * 活跃付费人数-按设备
     */
    private Integer alivePayuserDev = 0;
    /**
     * 活跃付费金额
     */
    private BigDecimal aliveMoney = BigDecimal.ZERO;
    /**
     * 活跃付费金额-按设备
     */
    private BigDecimal aliveMoneyDev = BigDecimal.ZERO;
    /**
     * 活跃付费次数
     */
    private Integer aliveOrder = 0;
    /**
     * 活跃付费次数-按设备
     */
    private Integer aliveOrderDev = 0;
    /**
     * 次日留存
     */
    private Integer loyal2 = 0;
    /**
     * 3日留存
     */
    private Integer loyal3 = 0;
    /**
     * 4日留存
     */
    private Integer loyal4 = 0;
    /**
     * 5日留存
     */
    private Integer loyal5 = 0;
    /**
     * 6日留存
     */
    private Integer loyal6 = 0;
    /**
     * 7日留存
     */
    private Integer loyal7 = 0;
    /**
     * 次日留存-按设备
     */
    private Integer loyal2Dev = 0;
    /**
     * 3日留存-按设备
     */
    private Integer loyal3Dev = 0;
    /**
     * 4日留存-按设备
     */
    private Integer loyal4Dev = 0;
    /**
     * 5日留存-按设备
     */
    private Integer loyal5Dev = 0;
    /**
     * 6日留存-按设备
     */
    private Integer loyal6Dev = 0;
    /**
     * 7日留存-按设备
     */
    private Integer loyal7Dev = 0;
    /**
     * 回本天数1
     */
    private BigDecimal payback1 = BigDecimal.ZERO;
    /**
     * 回本天数2
     */
    private BigDecimal payback2 = BigDecimal.ZERO;
    /**
     * 回本天数3
     */
    private BigDecimal payback3 = BigDecimal.ZERO;
    /**
     * 回本天数4
     */
    private BigDecimal payback4 = BigDecimal.ZERO;
    /**
     * 回本天数5
     */
    private BigDecimal payback5 = BigDecimal.ZERO;
    /**
     * 回本天数6
     */
    private BigDecimal payback6 = BigDecimal.ZERO;
    /**
     * 回本天数7
     */
    private BigDecimal payback7 = BigDecimal.ZERO;
    /**
     * 回本天数1-按设备
     */
    private BigDecimal payback1Dev = BigDecimal.ZERO;
    /**
     * 回本天数2-按设备
     */
    private BigDecimal payback2Dev = BigDecimal.ZERO;
    /**
     * 回本天数3-按设备
     */
    private BigDecimal payback3Dev = BigDecimal.ZERO;
    /**
     * 回本天数4-按设备
     */
    private BigDecimal payback4Dev = BigDecimal.ZERO;
    /**
     * 回本天数5-按设备
     */
    private BigDecimal payback5Dev = BigDecimal.ZERO;
    /**
     * 回本天数6-按设备
     */
    private BigDecimal payback6Dev = BigDecimal.ZERO;
    /**
     * 回本天数7-按设备
     */
    private BigDecimal payback7Dev = BigDecimal.ZERO;
}
