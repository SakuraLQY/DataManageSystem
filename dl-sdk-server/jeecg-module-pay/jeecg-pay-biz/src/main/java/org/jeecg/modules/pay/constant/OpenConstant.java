package org.jeecg.modules.pay.constant;

import java.math.BigDecimal;

/**
 * @author xmh
 * @version V1.0
 * @description: 统一下单时用到的常量
 * @date: 2022/12/21
 **/
public class OpenConstant {

    // 支付金额最小值
    public static final BigDecimal MONEY_MIN = BigDecimal.ZERO;
    // 支付金额最大值
    public static final BigDecimal MONEY_MAX = BigDecimal.valueOf(100000);
    // 使用平台币抵扣
    public static final int USE_PC = 1;
    // 统计数据分隔符号
    public static final String EXTRA_SYMBOL = "\t";
    // 统计数据支付标识
    public static final String EXTRA_PAY = "pay";
    // 创建订单状态
    public static final Integer CREATE_ORDER_STATUS = 1;
    // 平台币状态, 暂时抵扣
    public static final int PC_FOR_NOW = 1;
    // 平台币状态，已使用
    public static final int PC_USE = 2;
    // 平台币状态, 已返回
    public static final int PC_RETURN = 3;
    // 平台币兑换比
    public static final BigDecimal MONEY_COMPARE_PC = BigDecimal.valueOf(100);
    // 平台币抵扣比例 100 = 100%
    public static final double PC_DISCOUNT = 100.00;
    // BigDecimal 保留小数
    public static final int SAVE_POINT_NUM = 3;
}
