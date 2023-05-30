package org.jeecg.modules.advert.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.advert.dto.LogCallbackDataDto;
import org.jeecg.modules.advert.service.IAtVisitService;
import org.jeecg.modules.advert.vo.LogCallbackDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.controller
 * @className: AtVisitController
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 16:43
 */
@Api(tags="at_visit")
@RestController
@RequestMapping("/advert/atVisit")
@Slf4j
public class AtVisitController {

    @Autowired
    private IAtVisitService iAtVisitService;

    @ApiOperation(value="回调数据")
    @GetMapping(value = "/getCallbackData")
    public Result<IPage> getCallbackData(
        LogCallbackDataDto logCallbackDataDto,
        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {

        Page<LogCallbackDataVo> objectPage = new Page<LogCallbackDataVo>(pageNo, pageSize);
        IPage callbackData = iAtVisitService.getCallbackData(objectPage, logCallbackDataDto);
        return Result.OK(callbackData);
    }

    /**
     * 导出excel
     *
     * @param request
     */
    //@RequiresPermissions("advert:at_unique:exportXls")
    @RequestMapping(value = "/callbackDataExportXls")
    public ModelAndView callbackDataExportXls(HttpServletRequest request, LogCallbackDataDto logCallbackDataDto) {
        return iAtVisitService.callbackDataExportXls(request, logCallbackDataDto, "回调数据");
    }
}
