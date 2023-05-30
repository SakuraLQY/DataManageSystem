package org.jeecg.modules.users.controller;

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
import org.jeecg.modules.users.entity.OpDevice;
import org.jeecg.modules.users.service.IOpDeviceService;

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
 * @Description: op_device
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="op_device")
@RestController
@RequestMapping("/users/opDevice")
@Slf4j
public class OpDeviceController extends JeecgController<OpDevice, IOpDeviceService> {
	@Autowired
	private IOpDeviceService opDeviceService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opDevice
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_device-分页列表查询")
	@ApiOperation(value="op_device-分页列表查询", notes="op_device-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpDevice>> queryPageList(OpDevice opDevice,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpDevice> queryWrapper = QueryGenerator.initQueryWrapper(opDevice, req.getParameterMap());
		Page<OpDevice> page = new Page<OpDevice>(pageNo, pageSize);
		IPage<OpDevice> pageList = opDeviceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opDevice
	 * @return
	 */
	@AutoLog(value = "op_device-添加")
	@ApiOperation(value="op_device-添加", notes="op_device-添加")
	//@RequiresPermissions("users:op_device:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpDevice opDevice) {
		opDeviceService.save(opDevice);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param opDevice
	 * @return
	 */
	@AutoLog(value = "op_device-编辑")
	@ApiOperation(value="op_device-编辑", notes="op_device-编辑")
	//@RequiresPermissions("users:op_device:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpDevice opDevice) {
		opDeviceService.updateById(opDevice);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_device-通过id删除")
	@ApiOperation(value="op_device-通过id删除", notes="op_device-通过id删除")
	//@RequiresPermissions("users:op_device:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opDeviceService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_device-批量删除")
	@ApiOperation(value="op_device-批量删除", notes="op_device-批量删除")
	//@RequiresPermissions("users:op_device:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opDeviceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_device-通过id查询")
	@ApiOperation(value="op_device-通过id查询", notes="op_device-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpDevice> queryById(@RequestParam(name="id",required=true) String id) {
		OpDevice opDevice = opDeviceService.getById(id);
		if(opDevice==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opDevice);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opDevice
    */
    //@RequiresPermissions("users:op_device:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpDevice opDevice) {
        return super.exportXls(request, opDevice, OpDevice.class, "op_device");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("users:op_device:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpDevice.class);
    }

}
