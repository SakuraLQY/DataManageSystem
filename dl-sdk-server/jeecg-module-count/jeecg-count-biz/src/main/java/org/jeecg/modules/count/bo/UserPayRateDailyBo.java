package org.jeecg.modules.count.bo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: UserPayRateDailyBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class UserPayRateDailyBo {

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
    private String gameName;
    /**
     * 广告名
     */
    private String dealName;
    /**
     * 渠道名
     */
    private String channelName;
    /**
     * 注册数
     */
    private Integer countUser = 0;

    /**
     * 新增付费人数
     */
    private Integer firstPayuser = 0;
    /**
     * 新增付费额
     */
    private BigDecimal firstMoney = BigDecimal.ZERO;
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
     * 15日留存
     */
    private Integer loyal15 = 0;
    /**
     * 30日留存
     */
    private Integer loyal30 = 0;
    /**
     * 45日留存
     */
    private Integer loyal45 = 0;
    /**
     * 60日留存
     */
    private Integer loyal60 = 0;

}
