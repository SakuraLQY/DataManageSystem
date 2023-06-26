package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: DetailDailyBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class DetailDailyBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    private String day;
    /**
     * 激活数
     */
    private Integer countActive = 0;
    /**
     * 注册数
     */
    private Integer countUser = 0;
    /**
     * 有效注册数
     */
    private Integer countValidUserDev = 0;
    /**
     * 新增付费人数
     */
    private Integer firstPayuser = 0;
    /**
     * 新增付费金额
     */
    private BigDecimal firstMoney = BigDecimal.ZERO;
    /**
     * 累计付费金额
     */
    private BigDecimal totalMoney = BigDecimal.ZERO;
    /**
     * 累计付费人数
     */
    private Integer totalPayuser = 0;
    /**
     * DAU
     */
    private Integer countDau = 0;
    /**
     *  活跃付费人数
     */
    private Integer alivePayuser = 0;
    /**
     *  活跃金额
     */
    private BigDecimal aliveMoney = BigDecimal.ZERO;
    /**
     *  活跃付费金额-ios
     */
    private BigDecimal aliveMoneyIos = BigDecimal.ZERO;
}
