package org.jeecg.modules.count.controller;

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
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtHour;
import org.jeecg.modules.count.service.ICtDeviceService;

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
 * @Description: ct_device
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Api(tags = "ct_device")
@RestController
@RequestMapping("/count/ctDevice")
@Slf4j
public class CtDeviceController extends JeecgController<CtDevice, ICtDeviceService> {

    @Autowired
    private ICtDeviceService ctDeviceService;

    /**
     * 分页列表查询
     *
     * @param ctDevice
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "ct_device-分页列表查询")
    @ApiOperation(value = "ct_device-分页列表查询", notes = "ct_device-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<CtDevice>> queryPageList(CtDevice ctDevice,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<CtDevice> queryWrapper = QueryGenerator.initQueryWrapper(ctDevice,
            req.getParameterMap());
        Page<CtDevice> page = new Page<CtDevice>(pageNo, pageSize);
        IPage<CtDevice> pageList = ctDeviceService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param ctDevice
     * @return
     */
    @AutoLog(value = "ct_device-添加")
    @ApiOperation(value = "ct_device-添加", notes = "ct_device-添加")
    //@RequiresPermissions("count:ct_device:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CtDevice ctDevice) {
        ctDeviceService.save(ctDevice);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param ctDevice
     * @return
     */
    @AutoLog(value = "ct_device-编辑")
    @ApiOperation(value = "ct_device-编辑", notes = "ct_device-编辑")
    //@RequiresPermissions("count:ct_device:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody CtDevice ctDevice) {
        ctDeviceService.updateById(ctDevice);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "ct_device-通过id删除")
    @ApiOperation(value = "ct_device-通过id删除", notes = "ct_device-通过id删除")
    //@RequiresPermissions("count:ct_device:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        ctDeviceService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "ct_device-批量删除")
    @ApiOperation(value = "ct_device-批量删除", notes = "ct_device-批量删除")
    //@RequiresPermissions("count:ct_device:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ctDeviceService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "ct_device-通过id查询")
    @ApiOperation(value = "ct_device-通过id查询", notes = "ct_device-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<CtDevice> queryById(@RequestParam(name = "id", required = true) String id) {
        CtDevice ctDevice = ctDeviceService.getById(id);
        if (ctDevice == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ctDevice);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ctDevice
     */
    //@RequiresPermissions("count:ct_device:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDevice ctDevice) {
        return super.exportXls(request, ctDevice, CtDevice.class, "ct_device");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("count:ct_device:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDevice.class);
    }

}
