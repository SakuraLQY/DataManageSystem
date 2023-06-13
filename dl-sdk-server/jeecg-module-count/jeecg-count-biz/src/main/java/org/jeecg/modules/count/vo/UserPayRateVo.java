package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: UserPayRateVo
 * @Author: jeecg-boot
 * @Date: 2023-05-17
 * @Version: V1.0
 */
@Data
public class UserPayRateVo {

    /**
     * 日期
     **/
    private String day;

    /**
     * 游戏名
     **/
    private String gameName;

    /**
     * 渠道名
     **/
    private String channelName;

    /**
     * 广告名
     **/
    private String dealName;

    /**
     * 注册数
     **/
    private Integer countUser = 0;

    /**
     * 首日付费率
     **/
    private BigDecimal userPayRate1 = BigDecimal.ZERO;

    /**
     * 首日arppu
     **/
    private BigDecimal arppu1 = BigDecimal.ZERO;

    /**
     * 次日付费率
     **/
    private BigDecimal userPayRate2 = BigDecimal.ZERO;

    /**
     * 次日arppu
     **/
    private BigDecimal arppu2 = BigDecimal.ZERO;

    /**
     * 3日付费率
     **/
    private BigDecimal userPayRate3 = BigDecimal.ZERO;

    /**
     * 3日arppu
     **/
    private BigDecimal arppu3 = BigDecimal.ZERO;

    /**
     * 4日付费率
     **/
    private BigDecimal userPayRate4 = BigDecimal.ZERO;

    /**
     * 4日arppu
     **/
    private BigDecimal arppu4 = BigDecimal.ZERO;

    /**
     * 5日付费率
     **/
    private BigDecimal userPayRate5 = BigDecimal.ZERO;

    /**
     * 5日arppu
     **/
    private BigDecimal arppu5 = BigDecimal.ZERO;

    /**
     * 6日付费率
     **/
    private BigDecimal userPayRate6 = BigDecimal.ZERO;

    /**
     * 6日arppu
     **/
    private BigDecimal arppu6 = BigDecimal.ZERO;

    /**
     * 7日付费率
     **/
    private BigDecimal userPayRate7 = BigDecimal.ZERO;

    /**
     * 7日arppu
     **/
    private BigDecimal arppu7 = BigDecimal.ZERO;

    /**
     * 15日付费率
     **/
    private BigDecimal userPayRate15 = BigDecimal.ZERO;

    /**
     * 15日arppu
     **/
    private BigDecimal arppu15 = BigDecimal.ZERO;

    /**
     * 30日付费率
     **/
    private BigDecimal userPayRate30 = BigDecimal.ZERO;

    /**
     * 30日arppu
     **/
    private BigDecimal arppu30 = BigDecimal.ZERO;

    /**
     * 45日付费率
     **/
    private BigDecimal userPayRate45 = BigDecimal.ZERO;

    /**
     * 45日arppu
     **/
    private BigDecimal arppu45 = BigDecimal.ZERO;

    /**
     * 60日付费率
     **/
    private BigDecimal userPayRate60 = BigDecimal.ZERO;

    /**
     * 60日arppu
     **/
    private BigDecimal arppu60 = BigDecimal.ZERO;

}
