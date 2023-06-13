package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: WeekReportVo 数据周报-环比详细数据
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Data
public class WeekReportDetailVo {

    /**
     * 一级分组
     **/
    private String firstGroup = "";

    /**
     * 渠道 (二级分组)
     **/
    private String secondGroup = "";

    /**
     * 时间
     **/
    private String day = "";

    /**
     * 消耗
     **/
    private BigDecimal costMoney = BigDecimal.ZERO;

    /**
     * 注册数
     **/
    private Integer countUser = 0;

    /**
     * 单价
     **/
    private BigDecimal regPrice = BigDecimal.ZERO;

    /**
     * 首日roi
     **/
    private BigDecimal firstDayRoi = BigDecimal.ZERO;

    /**
     * 首周roi
     **/
    private BigDecimal firstWeekRoi = BigDecimal.ZERO;

    /**
     * 累计roi
     **/
    private BigDecimal totalRoi = BigDecimal.ZERO;

    /**
     * 首次付费
     **/
    private BigDecimal firstMoney = BigDecimal.ZERO;

    /**
     * 活跃付费
     **/
    private BigDecimal aliveMoney = BigDecimal.ZERO;

    /**
     * 累计付费
     **/
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 首次付费-ios
     **/
    private BigDecimal firstMoneyIos = BigDecimal.ZERO;

    /**
     * 累计付费-ios
     **/
    private BigDecimal totalMoneyIos = BigDecimal.ZERO;

    /**
     * 活跃付费ios
     **/
    private BigDecimal aliveMoneyIos = BigDecimal.ZERO;

    /**
     * 首日
     **/
    private BigDecimal payback1 = BigDecimal.ZERO;

    /**
     * 2日
     **/
    private BigDecimal payback2 = BigDecimal.ZERO;

    /**
     * 3日
     **/
    private BigDecimal payback3 = BigDecimal.ZERO;

    /**
     * 4日
     **/
    private BigDecimal payback4 = BigDecimal.ZERO;

    /**
     * 5日
     **/
    private BigDecimal payback5 = BigDecimal.ZERO;

    /**
     * 6日
     **/
    private BigDecimal payback6 = BigDecimal.ZERO;

    /**
     * 7日
     **/
    private BigDecimal payback7 = BigDecimal.ZERO;

    /**
     * 15日
     **/
    private BigDecimal payback15 = BigDecimal.ZERO;

    /**
     * 首日
     **/
    private BigDecimal ltv1 = BigDecimal.ZERO;

    /**
     * 2日
     **/
    private BigDecimal ltv2 = BigDecimal.ZERO;

    /**
     * 3日
     **/
    private BigDecimal ltv3 = BigDecimal.ZERO;

    /**
     * 4日
     **/
    private BigDecimal ltv4 = BigDecimal.ZERO;

    /**
     * 5日
     **/
    private BigDecimal ltv5 = BigDecimal.ZERO;

    /**
     * 6日
     **/
    private BigDecimal ltv6 = BigDecimal.ZERO;

    /**
     * 7日
     **/
    private BigDecimal ltv7 = BigDecimal.ZERO;

    /**
     * 15日
     **/
    private BigDecimal ltv15 = BigDecimal.ZERO;

    /**
     * 首周付费
     **/
    private BigDecimal weekMoney = BigDecimal.ZERO;

}
