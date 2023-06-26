package org.jeecg.modules.count.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.modules.count.modal.SummaryLaunchModal;
import org.jeecg.modules.count.vo.SummaryLaunchVo;
import org.jeecg.modules.count.service.ISummaryLaunchService;


import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.count.dto.SummaryLaunchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: 数据投放
 * @Author: jeecg-boot
 * @Date: 2023-05-10
 * @Version: V1.0
 */
@Api(tags = "数据投放")
@RestController
@RequestMapping("/count/summaryLaunch")
@Slf4j
public class SummaryLaunchController extends
    JeecgController<SummaryLaunchDto, ISummaryLaunchService> {

    @Autowired
    private ISummaryLaunchService summaryLaunchService;

    /**
     * 分页列表查询
     *
     * @param summaryLaunch
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "数据投放-分页列表查询")


    /**
     * 自定义查询投放数据
     *
     * @param
     * @param req
     * @return
     */
    @ApiOperation(value = "数据投放数据展示", notes = "数据投放-列表查询")
    @GetMapping(value = "queryList")
    @UserPermissionData(alias = "a")
    public Result<List<SummaryLaunchVo>> queryList(SummaryLaunchDto summaryLaunchDto,
        HttpServletRequest req) {
        List<SummaryLaunchVo> list = summaryLaunchService.queryList(summaryLaunchDto);
        return Result.OK(list);
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "数据投放-通过id删除")
    @ApiOperation(value = "数据投放-通过id删除", notes = "数据投放-通过id删除")
    //@RequiresPermissions("count:summary_launch:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        summaryLaunchService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "数据投放-批量删除")
    @ApiOperation(value = "数据投放-批量删除", notes = "数据投放-批量删除")
    //@RequiresPermissions("count:summary_launch:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.summaryLaunchService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**@param summaryLaunchDto
     * @author chenglin
     * @description 投放数据导出
     * @date 14:06 2023/6/13
     **/
    @ApiOperation(value = "数据投放数据展示", notes = "数据投放-列表查询")
    @GetMapping(value = "exportExcel")
    public ModelAndView exportExcel(HttpServletRequest request,SummaryLaunchDto summaryLaunchDto){
        return summaryLaunchService.exportExcel(request,summaryLaunchDto, SummaryLaunchModal.class,"数据投放表");
    }

}
