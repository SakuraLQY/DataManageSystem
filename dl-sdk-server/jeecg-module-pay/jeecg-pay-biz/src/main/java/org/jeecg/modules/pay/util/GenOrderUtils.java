package org.jeecg.modules.pay.util;

import cn.hutool.core.util.IdUtil;

/**
 * @author xmh
 * @version V1.0
 * @description: 生成订单ID工具类
 * @date: 2022/12/21
 **/
public class GenOrderUtils {

    /**
     * @return String
     * @author xmh
     * @description 生成订单ID
     * @date 2022/12/21 11:50
     */
    public static String genOrderId() {
        return IdUtil.simpleUUID();
    }

    /**
     * @return String
     * @author xmh
     * @description 生成平台币订单ID
     * @date 2022/12/21 11:50
     */
    public static String genPcOrderId() {
        String orderId = genOrderId();
        return "pc" + orderId;
    }
}
