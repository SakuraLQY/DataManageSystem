package org.jeecg.modules.count.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.count.dto.DayPayReportDto;
import org.jeecg.modules.count.dto.MonthPayReportDto;
import org.jeecg.modules.count.dto.OrderPurchaseVolumeDto;
import org.jeecg.modules.count.dto.PayOrderDto;
import org.jeecg.modules.count.dto.SupplementaryOrderDto;
import org.jeecg.modules.count.dto.UserOrderDto;
import org.jeecg.modules.count.service.IPayReportService;
import org.jeecg.modules.count.vo.DayPayReportVo;
import org.jeecg.modules.count.vo.MonthPayReportVo;
import org.jeecg.modules.count.vo.OrderPurchaseVolumeVo;
import org.jeecg.modules.count.vo.PayOrderTotalVo;
import org.jeecg.modules.count.vo.PayOrderVo;
import org.jeecg.modules.count.vo.UserOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.controller
 * @className: PayReportController
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/26 17:02
 */
@Api(tags="pay_report")
@RestController
@RequestMapping("/count/pay")
@Slf4j
public class PayReportController {

    @Autowired
    private IPayReportService payReportService;

    @ApiOperation(value="日充值汇总")
    @GetMapping(value = "/dayList")
    public Result<IPage<DayPayReportVo>> queryDayPageList(DayPayReportDto dayPayReportDto,
        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
        HttpServletRequest req) {
        Page<DayPayReportVo> objectPage = new Page<DayPayReportVo>(pageNo, pageSize);
        IPage<DayPayReportVo> pageList=payReportService.pageDay(objectPage, dayPayReportDto);
        return Result.OK(pageList);
    }

    /**
     * 日充值导出excel
     *
     * @param request
     * @param
     */
    @RequestMapping(value = "/dayExportXls")
    public ModelAndView exportXls(HttpServletRequest request, DayPayReportDto dayPayReportDto) {
        return payReportService.exportXls(request, dayPayReportDto, "日充值汇总");
    }

    @ApiOperation(value="月充值汇总")
    @GetMapping(value = "/monthList")
    public Result<IPage<MonthPayReportVo>> queryMonthPageList(
        MonthPayReportDto monthPayReportController,
        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
        HttpServletRequest req) {
        Page<MonthPayReportVo> objectPage = new Page<MonthPayReportVo>(pageNo, pageSize);
        IPage<MonthPayReportVo> pageList=payReportService.pageMonth(objectPage, monthPayReportController);
        return Result.OK(pageList);
    }

    /**
     * 月充值导出excel
     *
     * @param request
     * @param
     */
    @RequestMapping(value = "/monthExportXls")
    public ModelAndView monthExportXls(HttpServletRequest request, MonthPayReportDto monthPayReportDto) {
        return payReportService.monthExportXls(request, monthPayReportDto, "月充值汇总");
    }

    @ApiOperation(value="充值订单")
    @GetMapping(value = "/payOrderList")
    public Result<IPage<PayOrderVo>> queryPayOrderList(
        PayOrderDto payOrderDto,
        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
        HttpServletRequest req) {
        Page<PayOrderVo> objectPage = new Page<PayOrderVo>(pageNo, pageSize);
        IPage<PayOrderVo> pageList=payReportService.queryPayOrderList(objectPage, payOrderDto);
        return Result.OK(pageList);
    }

    /**
     * 充值订单导出excel
     *
     * @param request
     * @param
     */
    @RequestMapping(value = "/payOrderExportXls")
    public ModelAndView payOrderExportXls(HttpServletRequest request, PayOrderDto payOrderDto) {
        return payReportService.payOrderExportXls(request, payOrderDto, "充值订单");
    }

    @ApiOperation(value="手动补单")
    @GetMapping(value = "/supplementaryOrder")
    public Result<String> supplementaryOrder(
        SupplementaryOrderDto supplementaryOrderDto) {
        payReportService.check(supplementaryOrderDto);
        return Result.OK("补单成功");
    }

    @ApiOperation(value="获取充值订单总数据")
    @GetMapping(value = "/getSumPayOrder")
    public Result<PayOrderTotalVo> getSumPayOrder(
        PayOrderDto payOrderDto) {
        PayOrderTotalVo sumPayOrder = payReportService.getSumPayOrder(payOrderDto);
        return Result.OK(sumPayOrder);
    }

    @ApiOperation(value="充值订单[买量]")
    @GetMapping(value = "/orderPurchaseVolumeList")
    public Result<IPage<OrderPurchaseVolumeVo>> orderPurchaseVolumeList(
        OrderPurchaseVolumeDto orderPurchaseVolumeDto,
        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<OrderPurchaseVolumeVo> objectPage = new Page<OrderPurchaseVolumeVo>(pageNo, pageSize);
        IPage<OrderPurchaseVolumeVo> pageList=payReportService.orderPurchaseVolumeList(objectPage, orderPurchaseVolumeDto);
        return Result.OK(pageList);
    }

    /**
     * 充值订单[买量]导出excel
     *
     * @param request
     * @param
     */
    @RequestMapping(value = "/orderPurchaseVolumeExportXls")
    public ModelAndView orderPurchaseVolumeExportXls(HttpServletRequest request, OrderPurchaseVolumeDto orderPurchaseVolumeDto) {
        return payReportService.orderPurchaseVolumeExportXls(request, orderPurchaseVolumeDto, "充值订单[买量]");
    }

    @ApiOperation(value="用户充值订单查询")
    @GetMapping(value = "/userOrderSearch")
    public Result<IPage<UserOrderVo>> userOrderSearch(
        UserOrderDto userOrderDto,
        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<UserOrderVo> objectPage = new Page<UserOrderVo>(pageNo, pageSize);
        IPage<UserOrderVo> pageList=payReportService.userOrderSearch(objectPage, userOrderDto);
        return Result.OK(pageList);
    }

    @ApiOperation(value="获取用户充值订单总金额")
    @GetMapping(value = "/getUserOrderMoney")
    public Result<BigDecimal> getUserOrderMoney(
        UserOrderDto userOrderDto) {
        BigDecimal totalMoney = payReportService.getUserOrderMoney(userOrderDto);
        return Result.OK(totalMoney);
    }
}
