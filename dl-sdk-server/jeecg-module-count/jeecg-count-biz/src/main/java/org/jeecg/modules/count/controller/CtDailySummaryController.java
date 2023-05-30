package org.jeecg.modules.count.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.count.dto.DailySummaryDto;
import org.jeecg.modules.count.vo.CtDailySummary;
import org.jeecg.modules.count.service.ICtDailySummaryService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: ct_daily_summary
 * @Author: jeecg-boot
 * @Date: 2023-04-28
 * @Version: V1.0
 */
@Api(tags = "ct_daily_summary")
@RestController
@RequestMapping("/count/ctDailySummary")
@Slf4j
public class CtDailySummaryController extends
    JeecgController<CtDailySummary, ICtDailySummaryService> {

    @Autowired
    private ICtDailySummaryService ctDailySummaryService;

    /**
     * 分页列表查询
     *
     * @param ctDailySummary
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */

    //@AutoLog(value = "ct_daily_summary-分页列表查询")
    @ApiOperation(value = "ct_daily_summary-分页列表查询", notes = "ct_daily_summary-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<CtDailySummary>> queryPageList(CtDailySummary ctDailySummary,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<CtDailySummary> queryWrapper = QueryGenerator.initQueryWrapper(ctDailySummary,
            req.getParameterMap());
        Page<CtDailySummary> page = new Page<CtDailySummary>(pageNo, pageSize);
        IPage<CtDailySummary> pageList = ctDailySummaryService.page(page, queryWrapper);
        return Result.OK(pageList);
    }


    @ApiOperation(value = "dailySummary-查询今日汇总数据")
    @GetMapping(value = "queryList")
    public Result<List<List<DailySummaryDto>>> queryTodayData(CtDailySummary ctDailySummary) {
        return Result.OK(ctDailySummaryService.queryList(ctDailySummary));
    }

    /**
     * 添加
     *
     * @param ctDailySummary
     * @return
     */
    @AutoLog(value = "ct_daily_summary-添加")
    @ApiOperation(value = "ct_daily_summary-添加", notes = "ct_daily_summary-添加")
    //@RequiresPermissions("count:ct_daily_summary:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CtDailySummary ctDailySummary) {
        ctDailySummaryService.save(ctDailySummary);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param ctDailySummary
     * @return
     */
    @AutoLog(value = "ct_daily_summary-编辑")
    @ApiOperation(value = "ct_daily_summary-编辑", notes = "ct_daily_summary-编辑")
    //@RequiresPermissions("count:ct_daily_summary:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody CtDailySummary ctDailySummary) {
        ctDailySummaryService.updateById(ctDailySummary);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "ct_daily_summary-通过id删除")
    @ApiOperation(value = "ct_daily_summary-通过id删除", notes = "ct_daily_summary-通过id删除")
    //@RequiresPermissions("count:ct_daily_summary:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        ctDailySummaryService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "ct_daily_summary-批量删除")
    @ApiOperation(value = "ct_daily_summary-批量删除", notes = "ct_daily_summary-批量删除")
    //@RequiresPermissions("count:ct_daily_summary:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ctDailySummaryService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "ct_daily_summary-通过id查询")
    @ApiOperation(value = "ct_daily_summary-通过id查询", notes = "ct_daily_summary-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<CtDailySummary> queryById(@RequestParam(name = "id", required = true) String id) {
        CtDailySummary ctDailySummary = ctDailySummaryService.getById(id);
        if (ctDailySummary == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ctDailySummary);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ctDailySummary
     */
    //@RequiresPermissions("count:ct_daily_summary:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDailySummary ctDailySummary) {
        return super.exportXls(request, ctDailySummary, CtDailySummary.class, "ct_daily_summary");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("count:ct_daily_summary:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDailySummary.class);
    }

}
