package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.bo.GetOrderRateBo;
import org.jeecg.modules.count.vo.OrderDateGroupRateVo;
import org.jeecg.modules.count.vo.OrderMoneyGroupRateVo;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.modules.count.dto.OrderDetailDto;
import org.jeecg.modules.count.bo.summary.SummaryOrderBo;
import org.jeecg.modules.count.bo.summary.SummaryOrderDevBo;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.count.entity.CtRole;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.modal.OrderDetailModal;

/**
 * @Description: ct_order
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
public interface ICtOrderService extends IService<CtOrder> {

    /**
     * @param orderDetailDto
     * @return org.jeecg.modules.count.modal.OrderDetailModal
     * @Author lili
     * @Description 查询用户充值数据
     * @Date 13:58 2023/5/6
     **/
    OrderDetailModal getOrderDetail(OrderDetailDto orderDetailDto);

    /**
     * @param dealId
     * @param orderId
     * @return org.jeecg.modules.count.entity.CtOrder
     * @author chenyw
     * @description 获取订单
     * @date 20:18 2023/4/19/019
     **/
    CtOrder getCtOrder(Integer dealId, String orderId);

    /**
     * @param parsePayDto
     * @param ctRole
     * @param ctDevice
     * @param ctUser
     * @author chenyw
     * @description 支付解析支付信息
     * @date 14:21 2023/4/21/021
     **/
    void parsePayOrder(ParsePayDto parsePayDto, CtRole ctRole, CtDevice ctDevice, CtUser ctUser);

    /**
     * @param summaryDto
     * @return java.util.List<org.jeecg.modules.count.bo.summary.SummaryOrderBo>
     * @author chenyw
     * @description 获取汇总表 周期订单数据 按设备
     * @date 17:27 2023/5/5/005
     **/
    List<SummaryOrderDevBo> getSummaryOrderDev(SummaryDto summaryDto);

    /**
     * @param summaryDto
     * @return java.util.List<org.jeecg.modules.count.bo.summary.SummaryOrderBo>
     * @author chenyw
     * @description 获取汇总表 周期订单数据
     * @date 17:27 2023/5/5/005
     **/
    List<SummaryOrderBo> getSummaryOrder(SummaryDto summaryDto);

    /**
     * @param getOrderRateBo
     * @return java.util.List<org.jeecg.modules.count.bo.OrderMoneyGroupBo>
     * @author chenyw
     * @description  新增充值比例
     * @date 21:13 2023/5/10/010
     **/
    List<OrderMoneyGroupRateVo> getFirstMoneyGroup(GetOrderRateBo getOrderRateBo);

    /**
     * @param getOrderRateBo
     * @return java.util.List<org.jeecg.modules.count.vo.OrderMoneyGroupRateVo>
     * @author chenyw
     * @description 活跃充值比例
     * @date 10:25 2023/5/11/011
     **/
    List<OrderMoneyGroupRateVo> getAliveMoneyGroup(GetOrderRateBo getOrderRateBo);

    /**
     * @param getOrderRateBo
     * @return java.util.List<org.jeecg.modules.count.vo.OrderDateGroupRateVo>
     * @author chenyw
     * @description 获取用户注册日期比例
     * @date 18:46 2023/5/12/012
     **/
    List<OrderDateGroupRateVo> getRegDateGroup(GetOrderRateBo getOrderRateBo);

}
