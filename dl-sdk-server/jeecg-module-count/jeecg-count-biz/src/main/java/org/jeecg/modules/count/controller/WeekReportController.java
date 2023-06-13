package org.jeecg.modules.count.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.count.dto.SummaryDealInfoDataDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.modules.count.service.ISummaryService;
import org.jeecg.modules.count.service.IWeekReportService;
import org.jeecg.modules.count.vo.SummaryDealInfoDataVo;
import org.jeecg.modules.count.vo.SummaryVo;
import org.jeecg.modules.count.vo.WeekReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 数据周报
 * @Author: jeecg-boot
 * @Date: 2023-04-19
 * @Version: V1.0
 */
@Api(tags = "summary")
@RestController
@RequestMapping("/count/weekReport")
@Slf4j
public class WeekReportController {

    @Autowired
    private IWeekReportService weekReportService;

    @ApiOperation(value = "数据周报", notes = "获取数据周报")
    @GetMapping(value = "/list")
    public Result<WeekReportVo> queryPageList(String startDate, String endDate) {
        WeekReportVo weekReportData = weekReportService.getWeekReportData(startDate, endDate);
        return Result.OK(weekReportData);
    }

}
