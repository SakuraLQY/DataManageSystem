package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.count.bo.OrderDateGroupBo;
import org.jeecg.modules.count.bo.OrderMoneyGroupBo;
import org.jeecg.modules.count.bo.summary.SummaryOrderBo;
import org.jeecg.modules.count.bo.summary.SummaryOrderDevBo;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.entity.CtOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.count.vo.CtUserVo;
import org.jeecg.modules.count.vo.PayUserAndOrderVo;

/**
 * @Description: ct_order
 * @Author: jeecg-boot
 * @Date: 2023-04-19
 * @Version: V1.0
 */
public interface CtOrderMapper extends BaseMapper<CtOrder> {

    List<PayUserAndOrderVo> selectOrderByDealIdAndUserId(
        @Param(Constants.WRAPPER) QueryWrapper<CtUserVo> wrapper);

    /**
     * @param groupBy
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.summary.SummaryOrderBo>
     * @author chenyw
     * @description 获取数据汇总 周期订单
     * @date 18:02 2023/5/5/005
     **/
    List<SummaryOrderBo> getSummaryOrder(@Param("groupBy") String groupBy,
        @Param(Constants.WRAPPER) Wrapper<CtOrder> queryWrapper);

    /**
     * @param groupBy
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.summary.SummaryOrderBo>
     * @author chenyw
     * @description 获取数据汇总 周期订单 按设备
     * @date 18:02 2023/5/5/005
     **/
    List<SummaryOrderDevBo> getSummaryOrderDev(@Param("groupBy") String groupBy,
        @Param(Constants.WRAPPER) Wrapper<CtOrder> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.OrderMoneyGroupBo>
     * @author chenyw
     * @description 获取 金额归类
     * @date 21:06 2023/5/10/010
     **/
    List<OrderMoneyGroupBo> selectMoneyGroup(
        @Param(Constants.WRAPPER) Wrapper<CtOrder> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.modules.count.bo.OrderDateGroupBo>
     * @author chenyw
     * @description 获取 注册日期归类
     * @date 19:26 2023/5/12/012
     **/
    List<OrderDateGroupBo> selectDateGroup(
        @Param(Constants.WRAPPER) Wrapper<CtOrder> queryWrapper);

}
