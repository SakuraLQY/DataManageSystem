package org.jeecg.modules.count.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.modules.count.dto.LogDeviceDataDto;
import org.jeecg.modules.count.service.ILogManageService;
import org.jeecg.modules.count.vo.DayPayReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.controller
 * @className: LogManageController
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 10:00
 */
@Api(tags = "logManage")
@RestController
@RequestMapping("/log/manage")
@Slf4j
public class LogManageController {

    @Autowired
    private ILogManageService iLogManageService;

    @ApiOperation(value="设备数据")
    @GetMapping(value = "/getDeviceData")
    @UserPermissionData(alias = "cd")
    public Result<IPage> getDeviceData(
        LogDeviceDataDto logDeviceDataDto,
        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {

        Page<DayPayReportVo> objectPage = new Page<DayPayReportVo>(pageNo, pageSize);
        IPage deviceData = iLogManageService.getDeviceData(objectPage, logDeviceDataDto);
        return Result.OK(deviceData);
    }

    /**
     * 设备数据导出excel
     *
     * @param request
     * @param
     */
    @UserPermissionData(alias = "cd")
    @RequestMapping(value = "/deviceDataExportXls")
    public ModelAndView deviceDataExportXls(HttpServletRequest request, LogDeviceDataDto logDeviceDataDto) {
        return iLogManageService.deviceDataExportXls(request, logDeviceDataDto, "设备数据");
    }

}
