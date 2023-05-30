package org.jeecg.modules.pay.api;

import java.util.List;
import org.jeecg.modules.pay.entity.OpVendor;

/**
 * @version V1.0
 * @description: 对外暴露接口
 * @author: xmh
 * @date: 2022/12/19 9:05
 */
public interface IPayBaseAPI {

    /**
     * @return List<OpVendor>
     * @author xmh
     * @description 获取所有支付厂商
     * @date 2022/12/19 9:05
     */
    List<OpVendor> getAllVendor();

    Boolean updateOrderStatus(String orderId, Integer bankStatus);

    void deliverOnce(String orderId);
}
