package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: SummaryAdvertDailyBo 广告数据
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class SummaryAdvertDailyBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    private String day;
    /**
     * 游戏
     */
    private String dealId;
    /**
     * 渠道游戏包名
     */
    private String pkgName;
    /**
     * 广告名
     */
    private String dealName;
    /**
     * 渠道名
     */
    private String channelName;
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
     * 新增付费人数
     */
    private Integer firstPayuser = 0;
    /**
     * 新增付费额
     */
    private BigDecimal firstMoney = BigDecimal.ZERO;
    /**
     * 累积付费额
     */
    private BigDecimal totalMoney = BigDecimal.ZERO;
    /**
     * DAU 日活
     */
    private Integer countDau = 0;
    /**
     * 活跃付费人数
     */
    private Integer alivePayuser = 0;
    /**
     * 活跃付费金额
     */
    private BigDecimal aliveMoney = BigDecimal.ZERO;
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
}
