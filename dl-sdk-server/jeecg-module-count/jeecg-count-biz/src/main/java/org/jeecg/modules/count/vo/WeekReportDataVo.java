package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: WeekReportVo 数据周报- 数据报表
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Data
public class WeekReportDataVo {

    /**
     * 游戏名 （一级分组）
     **/
    private String firstGroup = "";

    /**
     * 渠道1 （游戏名称）
     **/
    private String gameNickName = "";

    /**
     * 渠道2 （渠道名称）
     **/
    private String channelNickName = "";

    /**
     * 现金消耗
     **/
    private BigDecimal costMoney = BigDecimal.ZERO;

    /**
     * 流水
     **/
    private BigDecimal aliveMoney = BigDecimal.ZERO;

    /**
     * 注册
     **/
    private Integer countUser = 0;

    /**
     * 首日付费人数
     **/
    private Integer firstPayuser = 0;

    /**
     * DAU
     **/
    private Integer countDau = 0;

    /**
     * 单价
     **/
    private BigDecimal regPrice = BigDecimal.ZERO;

    /**
     * 首日付费
     **/
    private BigDecimal firstMoney = BigDecimal.ZERO;

    /**
     * 新增付费率
     **/
    private BigDecimal firstPayRate = BigDecimal.ZERO;

    /**
     * 首日ROI
     **/
    private BigDecimal firstRoi = BigDecimal.ZERO;

    /**
     * 全部金额
     **/
    private BigDecimal totalMoney = BigDecimal.ZERO;

    /**
     * 累计ROI
     **/
    private BigDecimal totalRoi = BigDecimal.ZERO;

    /**
     * 活跃ROI
     **/
    private BigDecimal aliveRoi = BigDecimal.ZERO;

    /**
     * 首次付费ROI
     **/
    private BigDecimal firstMoneyIos = BigDecimal.ZERO;

    /**
     * 累计付费ROI
     **/
    private BigDecimal totalMoneyIos = BigDecimal.ZERO;

    /**
     * ios活跃金额
     **/
    private BigDecimal aliveMoneyIos = BigDecimal.ZERO;


    /**
     * 累亏
     **/
    private BigDecimal loss = BigDecimal.ZERO;

    /**
     * 利润
     **/
    private BigDecimal profit = BigDecimal.ZERO;

}
