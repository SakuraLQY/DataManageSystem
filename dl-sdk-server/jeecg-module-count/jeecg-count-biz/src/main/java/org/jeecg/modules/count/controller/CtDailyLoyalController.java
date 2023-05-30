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
import org.jeecg.modules.count.entity.CtDailyLoyal;
import org.jeecg.modules.count.service.ICtDailyLoyalService;

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
 * @Description: ct_daily_loyal
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="ct_daily_loyal")
@RestController
@RequestMapping("/count/ctDailyLoyal")
@Slf4j
public class CtDailyLoyalController extends JeecgController<CtDailyLoyal, ICtDailyLoyalService> {
	@Autowired
	private ICtDailyLoyalService ctDailyLoyalService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ctDailyLoyal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_daily_loyal-分页列表查询")
	@ApiOperation(value="ct_daily_loyal-分页列表查询", notes="ct_daily_loyal-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CtDailyLoyal>> queryPageList(CtDailyLoyal ctDailyLoyal,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CtDailyLoyal> queryWrapper = QueryGenerator.initQueryWrapper(ctDailyLoyal, req.getParameterMap());
		Page<CtDailyLoyal> page = new Page<CtDailyLoyal>(pageNo, pageSize);
		IPage<CtDailyLoyal> pageList = ctDailyLoyalService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ctDailyLoyal
	 * @return
	 */
	@AutoLog(value = "ct_daily_loyal-添加")
	@ApiOperation(value="ct_daily_loyal-添加", notes="ct_daily_loyal-添加")
	//@RequiresPermissions("count:ct_daily_loyal:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtDailyLoyal ctDailyLoyal) {
		ctDailyLoyalService.save(ctDailyLoyal);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ctDailyLoyal
	 * @return
	 */
	@AutoLog(value = "ct_daily_loyal-编辑")
	@ApiOperation(value="ct_daily_loyal-编辑", notes="ct_daily_loyal-编辑")
	//@RequiresPermissions("count:ct_daily_loyal:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtDailyLoyal ctDailyLoyal) {
		ctDailyLoyalService.updateById(ctDailyLoyal);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_daily_loyal-通过id删除")
	@ApiOperation(value="ct_daily_loyal-通过id删除", notes="ct_daily_loyal-通过id删除")
	//@RequiresPermissions("count:ct_daily_loyal:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctDailyLoyalService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_daily_loyal-批量删除")
	@ApiOperation(value="ct_daily_loyal-批量删除", notes="ct_daily_loyal-批量删除")
	//@RequiresPermissions("count:ct_daily_loyal:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctDailyLoyalService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_daily_loyal-通过id查询")
	@ApiOperation(value="ct_daily_loyal-通过id查询", notes="ct_daily_loyal-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtDailyLoyal> queryById(@RequestParam(name="id",required=true) String id) {
		CtDailyLoyal ctDailyLoyal = ctDailyLoyalService.getById(id);
		if(ctDailyLoyal==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctDailyLoyal);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ctDailyLoyal
    */
    //@RequiresPermissions("count:ct_daily_loyal:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDailyLoyal ctDailyLoyal) {
        return super.exportXls(request, ctDailyLoyal, CtDailyLoyal.class, "ct_daily_loyal");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_daily_loyal:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDailyLoyal.class);
    }

}
