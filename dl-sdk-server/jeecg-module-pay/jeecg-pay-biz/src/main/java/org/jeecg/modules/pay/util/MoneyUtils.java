package org.jeecg.modules.pay.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.jeecg.modules.pay.constant.OpenConstant;

/**
 * @author xmh
 * @version V1.0
 * @description: 金额工具类
 * @date: 2023/1/5 9:59
 **/
public class MoneyUtils {

    // 元与分比例
    private static final BigDecimal CHANGE = BigDecimal.valueOf(100);
    
    /**
     * @param amount: 金额
     * @return Integer
     * @author xmh
     * @description 将单位为元的金额转换为单位为分
     * @date 2023/1/3 14:49
     */
    public static Integer changeY2F(BigDecimal amount) {
        return Integer.valueOf(
            amount.multiply(CHANGE).stripTrailingZeros().toPlainString());
    }

    /**
     * @author xmh
     * @description 将单位为分的金额转换为单位为元
     * @date 2023/1/6 11:12
     * @param amount: 金额
     * @return BigDecimal
     */
    public static BigDecimal changeF2Y(int amount) {
        BigDecimal fen = BigDecimal.valueOf(amount);
        return fen.divide(CHANGE, 2, RoundingMode.DOWN);
    }
}
