package org.jeecg.common.constant;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.common.constant
 * @className: BankStatus
 * @author: Eric
 * @description: TODO
 * @date: 2022/12/21 18:49
 * @version: 1.0
 */
public class BankStatus {

    // 初始状态
    public static final Integer INIT = 0;
    // 正常
    public static final Integer NORMAL = 1000;
    // 金额不相等
    public static final Integer MONEY_NE = -2;
    // 未支付成功
    public static final Integer TRADE_FAIL = -3;
    // ios商品不存在
    public static final Integer COMMODITY_NO_FOUND = -4;

}
