package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.vo.DayPayReportVo;
import org.jeecg.modules.count.vo.MonthPayReportVo;
import org.jeecg.modules.count.vo.OrderPurchaseVolumeVo;
import org.jeecg.modules.count.vo.PayOrderTotalVo;
import org.jeecg.modules.count.vo.PayOrderVo;
import org.jeecg.modules.count.vo.UserOrderVo;
import org.springframework.stereotype.Repository;

@Repository
public interface PayReportMapper  {

    /**
     * @param page: 分页
     * @param queryWrapper: 查询条件
     * @return List<DayPayReportVo> 返回的封装类
     * @author Fkh
     * @description 获取日充值汇总数据
     * @date 2023/5/13 14:53
     */
    List<DayPayReportVo> queryNewList(Page page,@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param page: 分页
     * @param queryWrapper: 查询条件
     * @return List<MonthPayReportVo> 返回的封装类
     * @author Fkh
     * @description 获取月充值汇总数据
     * @date 2023/5/13 14:53
     */
    List<MonthPayReportVo> queryMonthList(Page page,@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param page: 分页
     * @param queryWrapper: 查询条件
     * @return List<PayOrderVo> 返回的封装类
     * @author Fkh
     * @description 获取订单充值数据
     * @date 2023/5/13 14:54
     */
    List<PayOrderVo> queryPayOrderList(Page page,@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return PayOrderTotalVo 返回的封装类
     * @author Fkh
     * @description 获取充值订单总数据
     * @date 2023/5/13 14:54
     */
    PayOrderTotalVo getSumPayOrder(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param page: 分页
     * @param queryWrapper: 查询条件
     * @return List<OrderPurchaseVolumeVo> 返回的封装类
     * @author Fkh
     * @description 充值订单[买量]
     * @date 2023/5/13 14:54
     */
    List<OrderPurchaseVolumeVo>  orderPurchaseVolumeList(Page page,@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param page: 分页
     * @param queryWrapper: 查询条件
     * @return List<UserOrderVo> 返回的封装类
     * @author Fkh
     * @description 根据用户ID和用户名查询用户数据
     * @date 2023/5/13 14:54
     */
    List<UserOrderVo> userOrderSearch(Page page,@Param(Constants.WRAPPER)QueryWrapper queryWrapper);

    /**
     * @param queryWrapper: 查询条件
     * @return BigDecimal
     * @author Fkh
     * @description 根据用户ID和用户名查询用户消费总金额
     * @date 2023/5/13 14:56
     */
    BigDecimal getUserOrderMoney(@Param(Constants.WRAPPER)QueryWrapper queryWrapper);
}
