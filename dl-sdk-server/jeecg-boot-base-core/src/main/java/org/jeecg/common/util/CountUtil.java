package org.jeecg.common.util;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author chenyw
 * @Description 计算工具
 * @Date 2023-04-23
 **/
public class CountUtil {

    /**
     * @param i
     * @return java.lang.Integer
     * @author chenyw
     * @description Integer类型加1 防空指针
     * @date 18:30 2023/4/21/021
     **/
    public static Integer increaseInt(Integer i) {
        return i == null ? 1 : (i + 1);
    }

    /**
     * @param a 参数a
     * @param b 参数b
     * @return java.math.BigDecimal
     * @author chenyw
     * @description 金额类型相加 防空指针
     * @date 18:30 2023/4/21/021
     **/
    public static BigDecimal addDec(BigDecimal a, BigDecimal b) {
        if (a == null) {
            a = BigDecimal.ZERO;
        }
        if (b == null) {
            b = BigDecimal.ZERO;
        }
        return a.add(b);
    }

}
