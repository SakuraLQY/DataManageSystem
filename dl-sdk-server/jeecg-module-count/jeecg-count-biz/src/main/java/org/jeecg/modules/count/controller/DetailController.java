package org.jeecg.modules.count.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.count.bo.GetOrderRateBo;
import org.jeecg.modules.count.dto.DetailDto;
import org.jeecg.modules.count.service.ICtOrderService;
import org.jeecg.modules.count.service.IDetailService;
import org.jeecg.modules.count.vo.DetailVo;
import org.jeecg.modules.count.vo.OrderMoneyGroupRateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 详细分析
 * @Author: jeecg-boot
 * @Date: 2023-04-19
 * @Version: V1.0
 */
@Api(tags = "detail")
@RestController
@RequestMapping("/count/detail")
@Slf4j
public class DetailController {

    @Autowired
    private IDetailService detailService;
    @Autowired
    private ICtOrderService ctOrderService;

    @ApiOperation(value = "获取数据汇总", notes = "获取数据汇总")
    @GetMapping(value = "/list")
    public Result<List<DetailVo>> queryPageList(DetailDto detailDto) {
        List<DetailVo> detailVoList = detailService.getDaily(detailDto);
        return Result.OK(detailVoList);
    }

    @ApiOperation(value = "首次付费比率", notes = "首次付费比率")
    @GetMapping(value = "/firstMoneyRate")
    public Result<List<OrderMoneyGroupRateVo>> firstMoneyRate(DetailDto detailDto) {
        GetOrderRateBo getOrderRateBo = new GetOrderRateBo();
        BeanUtils.copyProperties(detailDto, getOrderRateBo);
        List<OrderMoneyGroupRateVo> orderMoneyGroupRateVos = ctOrderService.getFirstMoneyGroup(
            getOrderRateBo);
        return Result.ok(orderMoneyGroupRateVos);
    }

    @ApiOperation(value = "活跃付费比率", notes = "活跃付费比率")
    @GetMapping(value = "/aliveMoneyRate")
    public Result<List<OrderMoneyGroupRateVo>> aliveMoneyRate(DetailDto detailDto) {
        GetOrderRateBo getOrderRateBo = new GetOrderRateBo();
        BeanUtils.copyProperties(detailDto, getOrderRateBo);
        List<OrderMoneyGroupRateVo> orderMoneyGroupRateVos = ctOrderService.getAliveMoneyGroup(
            getOrderRateBo);
        return Result.ok(orderMoneyGroupRateVos);
    }
}
