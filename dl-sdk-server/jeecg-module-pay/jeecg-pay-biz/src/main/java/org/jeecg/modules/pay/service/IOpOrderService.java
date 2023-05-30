package org.jeecg.modules.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.math.BigDecimal;
import java.util.LinkedList;
import org.jeecg.modules.pay.entity.OpOrder;
import org.jeecg.modules.pay.entity.OpOrderCount;

/**
 * @Description: op_order
 * @Author: jeecg-boot
 * @Date: 2022-12-21
 * @Version: V1.0
 */
public interface IOpOrderService extends IService<OpOrder> {

    /**
     * @param pkgId:   一级游戏包ID
     * @param gameOrderId: 游戏订单
     * @return OpOrder
     * @author xmh
     * @description 获取游戏订单信息
     * @date 2022/12/23 20:19
     */
    OpOrder getPkgIdOrder(Integer pkgId, String gameOrderId);

    /**
     * @param orderId:    订单编号
     * @param bankStatus: 状态
     * @return boolean
     * @author xmh
     * @description 更新订单游戏支付状态
     * @date 2022/12/24 16:07
     */
    boolean updateOrderStatus(String orderId, int bankStatus);

    /**
     * @author xmh
     * @description 根据订单号获取订单信息
     * @date 2023/1/6 9:43
     * @param orderId: 订单号
     * @return OpOrder
     */
    OpOrder getOrderByOrderId(String orderId);

    /**
     * @author xmh
     * @description 支付回调时更新订单信息
     * @date 2023/1/6 10:58
     * @param orderId: 订单号
     * @param transactionId: 支付交易订单号
     * @param bankStatus: 银行状态
     * @param bankMoney: 实际金额
     * @return boolean
     */
    boolean updateOrderByPay(String orderId, String transactionId, int bankStatus, BigDecimal bankMoney);

    /**
     * @param orderId: 订单id
     * @return void
     * @author czb
     * @description 单笔订单发货
     * @date 2022/12/22 14:34
     */
    void deliverOnce(String orderId);

    /**
     * @param opOrders: 订单
     * @param opOrderCounts: 订单统计
     * @return void
     * @author czb
     * @description 批量更新操作，不允许手动调，为了AOP代理对象使用
     * @date 2022/12/24 11:26
     */
    void updateDBBatch(LinkedList<OpOrder> opOrders,LinkedList<OpOrderCount> opOrderCounts);
    /**
     * @param order: 订单
     * @param orderCount: 订单统计
     * @return void
     * @author czb
     * @description 单次更新操作，不允许手动调，为了AOP代理对象使用
     * @date 2022/12/24 11:27
     */
    void updateDB(OpOrder order,OpOrderCount orderCount);
}
