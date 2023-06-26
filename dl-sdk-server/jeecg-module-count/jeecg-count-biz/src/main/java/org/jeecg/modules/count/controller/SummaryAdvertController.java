package org.jeecg.modules.count.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.modules.count.dto.SummaryAdvertDto;
import org.jeecg.modules.count.service.ISummaryAdvertService;
import org.jeecg.modules.count.vo.SummaryAdvertBarVo;
import org.jeecg.modules.count.vo.SummaryAdvertVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 数据汇总
 * @Author: jeecg-boot
 * @Date: 2023-04-19
 * @Version: V1.0
 */
@Api(tags = "summaryAdvert")
@RestController
@RequestMapping("/count/summaryAdvert")
@Slf4j
public class SummaryAdvertController {

    @Autowired
    private ISummaryAdvertService summaryAdvertService;

    @ApiOperation(value = "获取广告数据明细", notes = "获取广告数据明细")
    @GetMapping(value = "/list")
    @UserPermissionData(alias = "a")
    public Result<List<SummaryAdvertVo>> queryPageList(SummaryAdvertDto summaryAdvertDto) {
        List<SummaryAdvertVo> res = summaryAdvertService.getSummaryAdvert(summaryAdvertDto);
        return Result.OK(res);
    }

    @ApiOperation(value = "获取饼图", notes = "获取饼图")
    @GetMapping(value = "/getSummaryAdvertEChart")
    public Result<SummaryAdvertBarVo> getSummaryAdvertEChartVo(SummaryAdvertDto summaryAdvertDto) {
        SummaryAdvertBarVo summaryAdvertBarVo = summaryAdvertService.getSummaryAdvertEChart(
            summaryAdvertDto);
        return Result.OK(summaryAdvertBarVo);
    }

    @ApiOperation(value = "获取折线图", notes = "获取折线图")
    @GetMapping(value = "/getSummaryAdvertLine")
    public Result<Map<String, Map<String, BigDecimal>>> getSummaryAdvertLine(
        SummaryAdvertDto summaryAdvertDto) {
        Map<String, Map<String, BigDecimal>> result = summaryAdvertService.getSummaryAdvertLine(
            summaryAdvertDto);
        return Result.OK(result);
    }

}
