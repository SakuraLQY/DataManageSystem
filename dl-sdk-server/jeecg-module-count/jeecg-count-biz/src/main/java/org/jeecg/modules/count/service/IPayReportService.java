package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.count.dto.DayPayReportDto;
import org.jeecg.modules.count.dto.MonthPayReportDto;
import org.jeecg.modules.count.dto.OrderPurchaseVolumeDto;
import org.jeecg.modules.count.dto.PayOrderDto;
import org.jeecg.modules.count.dto.SupplementaryOrderDto;
import org.jeecg.modules.count.dto.UserOrderDto;
import org.jeecg.modules.count.vo.PayOrderTotalVo;
import org.springframework.web.servlet.ModelAndView;

public interface IPayReportService {

    /**
     * @param page:
     * @param dayPayReportDto:
     * @return IPage
     * @author Fkh
     * @description 获取日充值汇总数据
     * @date 2023/5/13 14:47
     */
    IPage pageDay(Page page, DayPayReportDto dayPayReportDto);

    ModelAndView exportXls(HttpServletRequest request, DayPayReportDto dayPayReportDto, String title);

    /**
     * @param page:
     * @param monthPayReportDto:
     * @return IPage
     * @author Fkh
     * @description 获取月充值汇总数据
     * @date 2023/5/13 14:47
     */
    IPage pageMonth(Page page, MonthPayReportDto monthPayReportDto);

    ModelAndView monthExportXls(HttpServletRequest request, MonthPayReportDto monthPayReportDto, String title);

    /**
     * @param page:
     * @param payOrderDto:
     * @return IPage
     * @author Fkh
     * @description 获取订单充值数据
     * @date 2023/5/13 14:48
     */
    IPage queryPayOrderList(Page page, PayOrderDto payOrderDto);

    ModelAndView payOrderExportXls(HttpServletRequest request, PayOrderDto payOrderDto, String title);

    /**
     * @param supplementaryOrderDto:
     * @return void
     * @author Fkh
     * @description 订单充值手动补单接口
     * @date 2023/5/13 14:48
     */
    void check(SupplementaryOrderDto supplementaryOrderDto);

    /**
     * @param payOrderDto:
     * @return PayOrderTotalVo
     * @author Fkh
     * @description 获取充值订单总数据
     * @date 2023/5/13 14:49
     */
    PayOrderTotalVo getSumPayOrder(PayOrderDto payOrderDto);

    /**
     * @param page:
     * @param orderPurchaseVolumeDto:
     * @return IPage
     * @author Fkh
     * @description 充值订单[买量]
     * @date 2023/5/13 14:49
     */
    IPage orderPurchaseVolumeList(Page page,OrderPurchaseVolumeDto orderPurchaseVolumeDto);

    ModelAndView orderPurchaseVolumeExportXls(HttpServletRequest request, OrderPurchaseVolumeDto orderPurchaseVolumeDto, String title);

    /**
     * @param page:
     * @param userOrderDto:
     * @return IPage
     * @author Fkh
     * @description 用户充值订单查询
     * @date 2023/5/13 14:49
     */
    IPage userOrderSearch(Page page, UserOrderDto userOrderDto);

    /**
     * @param userOrderDto:
     * @return BigDecimal
     * @author Fkh
     * @description 获取用户充值订单总金额
     * @date 2023/5/13 14:50
     */
    BigDecimal getUserOrderMoney(UserOrderDto userOrderDto);
}
