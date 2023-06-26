package org.jeecg.modules.count.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.modules.count.dto.SummaryDealInfoDataDto;
import org.jeecg.modules.count.service.ISummaryService;
import org.jeecg.modules.count.vo.SummaryDealInfoDataVo;
import org.jeecg.modules.count.vo.SummaryVo;
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
@Api(tags = "summary")
@RestController
@RequestMapping("/count/summary")
@Slf4j
public class SummaryController {

    @Autowired
    private ISummaryService summaryService;

    @ApiOperation(value = "获取数据汇总", notes = "获取数据汇总")
    @GetMapping(value = "/list")
    @UserPermissionData(alias = "a")
    public Result<List<SummaryVo>> queryPageList(SummaryDto summaryDto) {
        List<SummaryVo> res = summaryService.getSummary(summaryDto);
        return Result.OK(res);
    }

    @ApiOperation(value = "获取广告数据", notes = "获取广告数据")
    @GetMapping(value = "/getDealInfoData")
    public Result<SummaryDealInfoDataVo> getDealInfoData(
        SummaryDealInfoDataDto summaryDealInfoDataDto) {
        SummaryDealInfoDataVo summaryDealInfoDataVo = summaryService.getDealInfoData(
            summaryDealInfoDataDto);
        return Result.OK(summaryDealInfoDataVo);
    }
}
