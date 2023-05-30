package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.entity.OpCost;
import org.jeecg.modules.advert.service.IOpCostService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.vo.OpCostTotalVo;
import org.jeecg.modules.advert.vo.OpCostVo;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: op_cost
 * @Author: jeecg-boot
 * @Date: 2023-04-27
 * @Version: V1.0
 */
@Api(tags = "op_cost")
@RestController
@RequestMapping("/advert/opCost")
@Slf4j
public class OpCostController extends JeecgController<OpCost, IOpCostService> {

    @Autowired
    private IOpCostService opCostService;

    /**
     * 分页列表查询
     *
     * @param opCost
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_cost-分页列表查询")
    @ApiOperation(value = "op_cost-分页列表查询", notes = "op_cost-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpCostVo>> queryPageList(OpCost opCost,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        String startDate, String endDate) {
        Page<OpCostVo> page = new Page<>(pageNo, pageSize);
        IPage<OpCostVo> pageList = opCostService.getByPage(page, opCost, startDate, endDate);
        return Result.OK(pageList);
    }

    @ApiOperation(value = "op_cost-合计", notes = "op_cost-合计")
    @GetMapping(value = "/total")
    public Result<OpCostTotalVo> total(OpCost opCost, String startDate, String endDate) {
        OpCostTotalVo opCostTotalVo = opCostService.getTotalCost(opCost, startDate, endDate);
        return Result.ok(opCostTotalVo);
    }

    /**
     * 添加
     *
     * @param opCost
     * @return
     */
    @AutoLog(value = "op_cost-添加")
    @ApiOperation(value = "op_cost-添加", notes = "op_cost-添加")
    //@RequiresPermissions("advert:op_cost:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpCost opCost) {
        opCostService.save(opCost);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param opCost
     * @return
     */
    @AutoLog(value = "op_cost-编辑")
    @ApiOperation(value = "op_cost-编辑", notes = "op_cost-编辑")
    //@RequiresPermissions("advert:op_cost:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpCost opCost) {
        opCostService.updateById(opCost);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_cost-通过id删除")
    @ApiOperation(value = "op_cost-通过id删除", notes = "op_cost-通过id删除")
    //@RequiresPermissions("advert:op_cost:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opCostService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_cost-批量删除")
    @ApiOperation(value = "op_cost-批量删除", notes = "op_cost-批量删除")
    //@RequiresPermissions("advert:op_cost:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opCostService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_cost-通过id查询")
    @ApiOperation(value = "op_cost-通过id查询", notes = "op_cost-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpCost> queryById(@RequestParam(name = "id", required = true) String id) {
        OpCost opCost = opCostService.getById(id);
        if (opCost == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opCost);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opCost
     */
    //@RequiresPermissions("advert:op_cost:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpCost opCost) {
        return super.exportXls(request, opCost, OpCost.class, "op_cost");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("advert:op_cost:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpCost.class);
    }

}
