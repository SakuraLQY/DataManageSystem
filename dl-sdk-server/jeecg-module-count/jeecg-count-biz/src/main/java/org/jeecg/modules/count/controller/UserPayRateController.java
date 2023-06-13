package org.jeecg.modules.count.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.count.dto.SummaryDealInfoDataDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.modules.count.dto.UserPayRateDto;
import org.jeecg.modules.count.service.ISummaryService;
import org.jeecg.modules.count.service.IUserPayRateService;
import org.jeecg.modules.count.vo.SummaryDealInfoDataVo;
import org.jeecg.modules.count.vo.SummaryVo;
import org.jeecg.modules.count.vo.UserPayRateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 用户付费率
 * @Author: jeecg-boot
 * @Date: 2023-04-19
 * @Version: V1.0
 */
@Api(tags = "summary")
@RestController
@RequestMapping("/count/userPayRate")
@Slf4j
public class UserPayRateController {

    @Autowired
    private IUserPayRateService userPayRateService;

    @ApiOperation(value = "获取用户付费率", notes = "获取用户付费率")
    @GetMapping(value = "/list")
    public Result<List<UserPayRateVo>> getPayRate(UserPayRateDto userPayRateDto) {
        List<UserPayRateVo> res = userPayRateService.getPayRate(userPayRateDto);
        return Result.OK(res);
    }

}
