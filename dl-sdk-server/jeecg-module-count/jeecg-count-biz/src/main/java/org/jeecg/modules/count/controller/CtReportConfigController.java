package org.jeecg.modules.count.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.jeecg.modules.count.entity.CtReportConfig;
import org.jeecg.modules.count.service.ICtReportConfigService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: ct_report_config
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Api(tags = "ct_report_config")
@RestController
@RequestMapping("/count/ctReportConfig")
@Slf4j
public class CtReportConfigController extends
    JeecgController<CtReportConfig, ICtReportConfigService> {

    @Autowired
    private ICtReportConfigService ctReportConfigService;

    /**
     * 分页列表查询
     *
     * @param ctReportConfig
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "ct_report_config-分页列表查询")
    @ApiOperation(value = "ct_report_config-分页列表查询", notes = "ct_report_config-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<CtReportConfig>> queryPageList(CtReportConfig ctReportConfig,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<CtReportConfig> queryWrapper = QueryGenerator.initQueryWrapper(ctReportConfig,
            req.getParameterMap());
        Page<CtReportConfig> page = new Page<CtReportConfig>(pageNo, pageSize);
        IPage<CtReportConfig> pageList = ctReportConfigService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param ctReportConfig
     * @return
     */
    @AutoLog(value = "ct_report_config-添加")
    @ApiOperation(value = "ct_report_config-添加", notes = "ct_report_config-添加")
    //@RequiresPermissions("count:ct_report_config:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CtReportConfig ctReportConfig) {
        ctReportConfigService.save(ctReportConfig);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param ctReportConfig
     * @return
     */
    @AutoLog(value = "ct_report_config-编辑")
    @ApiOperation(value = "ct_report_config-编辑", notes = "ct_report_config-编辑")
    //@RequiresPermissions("count:ct_report_config:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody CtReportConfig ctReportConfig) {
        LambdaQueryWrapper<CtReportConfig> lambdaQueryWrapper = new LambdaQueryWrapper<CtReportConfig>().eq(
            CtReportConfig::getConfigName, ctReportConfig.getConfigName());
        ctReportConfigService.update(ctReportConfig, lambdaQueryWrapper);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "ct_report_config-通过id删除")
    @ApiOperation(value = "ct_report_config-通过id删除", notes = "ct_report_config-通过id删除")
    //@RequiresPermissions("count:ct_report_config:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        ctReportConfigService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "ct_report_config-批量删除")
    @ApiOperation(value = "ct_report_config-批量删除", notes = "ct_report_config-批量删除")
    //@RequiresPermissions("count:ct_report_config:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ctReportConfigService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "ct_report_config-通过id查询")
    @ApiOperation(value = "ct_report_config-通过id查询", notes = "ct_report_config-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<CtReportConfig> queryById(@RequestParam(name = "id", required = true) String id) {
        CtReportConfig ctReportConfig = ctReportConfigService.getById(id);
        if (ctReportConfig == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ctReportConfig);
    }

    /**
     * 通过configName查询
     *
     * @param configName
     * @return
     */
    @ApiOperation(value = "ct_report_config-通过id查询", notes = "ct_report_config-通过id查询")
    @GetMapping(value = "/queryByConfigName")
    public Result<List<JSONObject>> queryByConfigName(
        @RequestParam(name = "configName", required = true) String configName) {
        List<JSONObject> res = service.getByConfigName(configName);
        return Result.OK(res);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ctReportConfig
     */
    //@RequiresPermissions("count:ct_report_config:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtReportConfig ctReportConfig) {
        return super.exportXls(request, ctReportConfig, CtReportConfig.class, "ct_report_config");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("count:ct_report_config:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtReportConfig.class);
    }

}
