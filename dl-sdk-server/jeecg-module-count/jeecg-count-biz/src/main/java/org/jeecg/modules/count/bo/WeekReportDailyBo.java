package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: WeekReportDailyBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class WeekReportDailyBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 游戏
     **/
    private Integer gameId;

    /**
     * 子游戏
     **/
    private Integer subGameId;

    /**
     * 一级游戏包
     **/
    private Integer pkgId;

    /**
     * 渠道类型id
     **/
    private Integer channelTypeId;

    /**
     * 渠道id
     **/
    private Integer channelId;

    /**
     * 渠道子账号id
     **/
    private Integer channelSubAccountId;

    /**
     * 统计时间
     **/
    private String timeDaily;

    /**
     * 注册用户
     **/
    private Integer countUser;

    /**
     * DAU
     **/
    private Integer countDau;

    /**
     * 新增注册人数
     **/
    private Integer firstpayuser;

    /**
     * 首日付费金额
     **/
    private BigDecimal firstMoney;

    /**
     * 首日付费金额-ios
     **/
    private BigDecimal firstMoneyIos;

    /**
     * 活跃付费
     **/
    private BigDecimal aliveMoney;

    /**
     * 活跃付费金额-ios
     **/
    private BigDecimal aliveMoneyIos;

    /**
     * 累计付费
     **/
    private BigDecimal totalMoney;

    /**
     * 累计付费金额-ios
     **/
    private BigDecimal totalMoneyIos;

    /**
     * 回本天数1
     */
    private BigDecimal payback1;
    /**
     * 回本天数2
     */
    private BigDecimal payback2;
    /**
     * 回本天数3
     */
    private BigDecimal payback3;
    /**
     * 回本天数4
     */
    private BigDecimal payback4;
    /**
     * 回本天数5
     */
    private BigDecimal payback5;
    /**
     * 回本天数6
     */
    private BigDecimal payback6;
    /**
     * 回本天数7
     */
    private BigDecimal payback7;
    /**
     * 回本天数8
     */
    private BigDecimal payback8;
    /**
     * 回本天数9
     */
    private BigDecimal payback9;
    /**
     * 回本天数10
     */
    private BigDecimal payback10;
    /**
     * 回本天数11
     */
    private BigDecimal payback11;
    /**
     * 回本天数12
     */
    private BigDecimal payback12;
    /**
     * 回本天数13
     */
    private BigDecimal payback13;
    /**
     * 回本天数14
     */
    private BigDecimal payback14;
    /**
     * 回本天数15
     */
    private BigDecimal payback15;

}
