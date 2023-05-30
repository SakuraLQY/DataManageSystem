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
import org.jeecg.modules.count.entity.CtDailyPayback;
import org.jeecg.modules.count.service.ICtDailyPaybackService;

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
 * @Description: ct_daily_payback
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="ct_daily_payback")
@RestController
@RequestMapping("/count/ctDailyPayback")
@Slf4j
public class CtDailyPaybackController extends JeecgController<CtDailyPayback, ICtDailyPaybackService> {
	@Autowired
	private ICtDailyPaybackService ctDailyPaybackService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ctDailyPayback
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_daily_payback-分页列表查询")
	@ApiOperation(value="ct_daily_payback-分页列表查询", notes="ct_daily_payback-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CtDailyPayback>> queryPageList(CtDailyPayback ctDailyPayback,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CtDailyPayback> queryWrapper = QueryGenerator.initQueryWrapper(ctDailyPayback, req.getParameterMap());
		Page<CtDailyPayback> page = new Page<CtDailyPayback>(pageNo, pageSize);
		IPage<CtDailyPayback> pageList = ctDailyPaybackService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ctDailyPayback
	 * @return
	 */
	@AutoLog(value = "ct_daily_payback-添加")
	@ApiOperation(value="ct_daily_payback-添加", notes="ct_daily_payback-添加")
	//@RequiresPermissions("count:ct_daily_payback:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtDailyPayback ctDailyPayback) {
		ctDailyPaybackService.save(ctDailyPayback);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ctDailyPayback
	 * @return
	 */
	@AutoLog(value = "ct_daily_payback-编辑")
	@ApiOperation(value="ct_daily_payback-编辑", notes="ct_daily_payback-编辑")
	//@RequiresPermissions("count:ct_daily_payback:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtDailyPayback ctDailyPayback) {
		ctDailyPaybackService.updateById(ctDailyPayback);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_daily_payback-通过id删除")
	@ApiOperation(value="ct_daily_payback-通过id删除", notes="ct_daily_payback-通过id删除")
	//@RequiresPermissions("count:ct_daily_payback:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctDailyPaybackService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_daily_payback-批量删除")
	@ApiOperation(value="ct_daily_payback-批量删除", notes="ct_daily_payback-批量删除")
	//@RequiresPermissions("count:ct_daily_payback:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctDailyPaybackService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_daily_payback-通过id查询")
	@ApiOperation(value="ct_daily_payback-通过id查询", notes="ct_daily_payback-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtDailyPayback> queryById(@RequestParam(name="id",required=true) String id) {
		CtDailyPayback ctDailyPayback = ctDailyPaybackService.getById(id);
		if(ctDailyPayback==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctDailyPayback);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ctDailyPayback
    */
    //@RequiresPermissions("count:ct_daily_payback:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDailyPayback ctDailyPayback) {
        return super.exportXls(request, ctDailyPayback, CtDailyPayback.class, "ct_daily_payback");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_daily_payback:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDailyPayback.class);
    }

}
