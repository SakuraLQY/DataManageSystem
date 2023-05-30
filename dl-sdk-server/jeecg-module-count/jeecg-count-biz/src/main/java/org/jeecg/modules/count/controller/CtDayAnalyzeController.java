package org.jeecg.modules.count.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.count.dto.DailyAnalyzeDto;
import org.jeecg.modules.count.vo.CtDayAnalyze;
import org.jeecg.modules.count.service.ICtDayAnalyzeService;

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
 * @Description: ct_dayanalyze
 * @Author: jeecg-boot
 * @Date: 2023-04-28
 * @Version: V1.0
 */
@Api(tags = "ct_dayanalyze")
@RestController
@RequestMapping("/count/ctDayAnalyze")
@Slf4j
public class CtDayAnalyzeController extends JeecgController<CtDayAnalyze, ICtDayAnalyzeService> {

    @Autowired
    private ICtDayAnalyzeService ctDayAnalyzeService;

    /**
     * 分页列表查询
     *
     * @param ctDayAnalyze
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "ct_dayanalyze-分页列表查询")
    @ApiOperation(value = "ct_dayanalyze-分页列表查询", notes = "ct_dayanalyze-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<CtDayAnalyze>> queryPageList(CtDayAnalyze ctDayAnalyze,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<CtDayAnalyze> queryWrapper = QueryGenerator.initQueryWrapper(ctDayAnalyze,
            req.getParameterMap());
        Page<CtDayAnalyze> page = new Page<CtDayAnalyze>(pageNo, pageSize);
        IPage<CtDayAnalyze> pageList = ctDayAnalyzeService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**@param dailyAnalyzeDto
     * @author chenglin
     * @description 自定义返回查询的数据
     * @date 18:53 2023/05/11
     **/
    @ApiOperation(value = "多日分时分析数据", notes = "列表信息查询展示")
    @GetMapping(value = "/queryList")
    public Result<List<CtDayAnalyze>> queryList(DailyAnalyzeDto dailyAnalyzeDto,
        HttpServletRequest req) {
        return Result.OK(ctDayAnalyzeService.queryList(dailyAnalyzeDto));
    }


    /**
     * 添加
     *
     * @param ctDayAnalyze
     * @return
     */
    @AutoLog(value = "ct_dayanalyze-添加")
    @ApiOperation(value = "ct_dayanalyze-添加", notes = "ct_dayanalyze-添加")
    //@RequiresPermissions("count:ct_dayanalyze:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CtDayAnalyze ctDayAnalyze) {
        ctDayAnalyzeService.save(ctDayAnalyze);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param ctDayAnalyze
     * @return
     */
    @AutoLog(value = "ct_dayanalyze-编辑")
    @ApiOperation(value = "ct_dayanalyze-编辑", notes = "ct_dayanalyze-编辑")
    //@RequiresPermissions("count:ct_dayanalyze:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody CtDayAnalyze ctDayAnalyze) {
        ctDayAnalyzeService.updateById(ctDayAnalyze);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "ct_dayanalyze-通过id删除")
    @ApiOperation(value = "ct_dayanalyze-通过id删除", notes = "ct_dayanalyze-通过id删除")
    //@RequiresPermissions("count:ct_dayanalyze:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        ctDayAnalyzeService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "ct_dayanalyze-批量删除")
    @ApiOperation(value = "ct_dayanalyze-批量删除", notes = "ct_dayanalyze-批量删除")
    //@RequiresPermissions("count:ct_dayanalyze:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ctDayAnalyzeService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "ct_dayanalyze-通过id查询")
    @ApiOperation(value = "ct_dayanalyze-通过id查询", notes = "ct_dayanalyze-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<CtDayAnalyze> queryById(@RequestParam(name = "id", required = true) String id) {
        CtDayAnalyze ctDayAnalyze = ctDayAnalyzeService.getById(id);
        if (ctDayAnalyze == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ctDayAnalyze);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ctDayAnalyze
     */
    //@RequiresPermissions("count:ct_dayanalyze:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDayAnalyze ctDayAnalyze) {
        return super.exportXls(request, ctDayAnalyze, CtDayAnalyze.class, "ct_dayanalyze");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("count:ct_dayanalyze:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDayAnalyze.class);
    }

}
