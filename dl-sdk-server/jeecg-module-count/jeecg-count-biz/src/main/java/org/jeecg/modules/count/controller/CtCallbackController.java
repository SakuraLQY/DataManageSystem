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
import org.jeecg.modules.count.entity.CtCallback;
import org.jeecg.modules.count.service.ICtCallbackService;

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
 * @Description: ct_callback
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="ct_callback")
@RestController
@RequestMapping("/count/ctCallback")
@Slf4j
public class CtCallbackController extends JeecgController<CtCallback, ICtCallbackService> {
	@Autowired
	private ICtCallbackService ctCallbackService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ctCallback
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_callback-分页列表查询")
	@ApiOperation(value="ct_callback-分页列表查询", notes="ct_callback-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CtCallback>> queryPageList(CtCallback ctCallback,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CtCallback> queryWrapper = QueryGenerator.initQueryWrapper(ctCallback, req.getParameterMap());
		Page<CtCallback> page = new Page<CtCallback>(pageNo, pageSize);
		IPage<CtCallback> pageList = ctCallbackService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ctCallback
	 * @return
	 */
	@AutoLog(value = "ct_callback-添加")
	@ApiOperation(value="ct_callback-添加", notes="ct_callback-添加")
	//@RequiresPermissions("count:ct_callback:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtCallback ctCallback) {
		ctCallbackService.save(ctCallback);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ctCallback
	 * @return
	 */
	@AutoLog(value = "ct_callback-编辑")
	@ApiOperation(value="ct_callback-编辑", notes="ct_callback-编辑")
	//@RequiresPermissions("count:ct_callback:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtCallback ctCallback) {
		ctCallbackService.updateById(ctCallback);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_callback-通过id删除")
	@ApiOperation(value="ct_callback-通过id删除", notes="ct_callback-通过id删除")
	//@RequiresPermissions("count:ct_callback:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctCallbackService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_callback-批量删除")
	@ApiOperation(value="ct_callback-批量删除", notes="ct_callback-批量删除")
	//@RequiresPermissions("count:ct_callback:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctCallbackService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_callback-通过id查询")
	@ApiOperation(value="ct_callback-通过id查询", notes="ct_callback-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtCallback> queryById(@RequestParam(name="id",required=true) String id) {
		CtCallback ctCallback = ctCallbackService.getById(id);
		if(ctCallback==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctCallback);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ctCallback
    */
    //@RequiresPermissions("count:ct_callback:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtCallback ctCallback) {
        return super.exportXls(request, ctCallback, CtCallback.class, "ct_callback");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_callback:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtCallback.class);
    }

}
