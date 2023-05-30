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
import org.jeecg.modules.count.entity.CtDailyPaybackDev;
import org.jeecg.modules.count.service.ICtDailyPaybackDevService;

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
 * @Description: ct_daily_payback_dev
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="ct_daily_payback_dev")
@RestController
@RequestMapping("/count/ctDailyPaybackDev")
@Slf4j
public class CtDailyPaybackDevController extends JeecgController<CtDailyPaybackDev, ICtDailyPaybackDevService> {
	@Autowired
	private ICtDailyPaybackDevService ctDailyPaybackDevService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ctDailyPaybackDev
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_daily_payback_dev-分页列表查询")
	@ApiOperation(value="ct_daily_payback_dev-分页列表查询", notes="ct_daily_payback_dev-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CtDailyPaybackDev>> queryPageList(CtDailyPaybackDev ctDailyPaybackDev,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CtDailyPaybackDev> queryWrapper = QueryGenerator.initQueryWrapper(ctDailyPaybackDev, req.getParameterMap());
		Page<CtDailyPaybackDev> page = new Page<CtDailyPaybackDev>(pageNo, pageSize);
		IPage<CtDailyPaybackDev> pageList = ctDailyPaybackDevService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ctDailyPaybackDev
	 * @return
	 */
	@AutoLog(value = "ct_daily_payback_dev-添加")
	@ApiOperation(value="ct_daily_payback_dev-添加", notes="ct_daily_payback_dev-添加")
	//@RequiresPermissions("count:ct_daily_payback_dev:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtDailyPaybackDev ctDailyPaybackDev) {
		ctDailyPaybackDevService.save(ctDailyPaybackDev);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ctDailyPaybackDev
	 * @return
	 */
	@AutoLog(value = "ct_daily_payback_dev-编辑")
	@ApiOperation(value="ct_daily_payback_dev-编辑", notes="ct_daily_payback_dev-编辑")
	//@RequiresPermissions("count:ct_daily_payback_dev:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtDailyPaybackDev ctDailyPaybackDev) {
		ctDailyPaybackDevService.updateById(ctDailyPaybackDev);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_daily_payback_dev-通过id删除")
	@ApiOperation(value="ct_daily_payback_dev-通过id删除", notes="ct_daily_payback_dev-通过id删除")
	//@RequiresPermissions("count:ct_daily_payback_dev:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctDailyPaybackDevService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_daily_payback_dev-批量删除")
	@ApiOperation(value="ct_daily_payback_dev-批量删除", notes="ct_daily_payback_dev-批量删除")
	//@RequiresPermissions("count:ct_daily_payback_dev:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctDailyPaybackDevService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_daily_payback_dev-通过id查询")
	@ApiOperation(value="ct_daily_payback_dev-通过id查询", notes="ct_daily_payback_dev-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtDailyPaybackDev> queryById(@RequestParam(name="id",required=true) String id) {
		CtDailyPaybackDev ctDailyPaybackDev = ctDailyPaybackDevService.getById(id);
		if(ctDailyPaybackDev==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctDailyPaybackDev);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ctDailyPaybackDev
    */
    //@RequiresPermissions("count:ct_daily_payback_dev:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDailyPaybackDev ctDailyPaybackDev) {
        return super.exportXls(request, ctDailyPaybackDev, CtDailyPaybackDev.class, "ct_daily_payback_dev");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_daily_payback_dev:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDailyPaybackDev.class);
    }

}
