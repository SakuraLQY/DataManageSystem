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
import org.jeecg.modules.count.entity.CtDailyLoyalDev;
import org.jeecg.modules.count.service.ICtDailyLoyalDevService;

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
 * @Description: ct_daily_loyal_dev
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="ct_daily_loyal_dev")
@RestController
@RequestMapping("/count/ctDailyLoyalDev")
@Slf4j
public class CtDailyLoyalDevController extends JeecgController<CtDailyLoyalDev, ICtDailyLoyalDevService> {
	@Autowired
	private ICtDailyLoyalDevService ctDailyLoyalDevService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ctDailyLoyalDev
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_daily_loyal_dev-分页列表查询")
	@ApiOperation(value="ct_daily_loyal_dev-分页列表查询", notes="ct_daily_loyal_dev-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CtDailyLoyalDev>> queryPageList(CtDailyLoyalDev ctDailyLoyalDev,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CtDailyLoyalDev> queryWrapper = QueryGenerator.initQueryWrapper(ctDailyLoyalDev, req.getParameterMap());
		Page<CtDailyLoyalDev> page = new Page<CtDailyLoyalDev>(pageNo, pageSize);
		IPage<CtDailyLoyalDev> pageList = ctDailyLoyalDevService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ctDailyLoyalDev
	 * @return
	 */
	@AutoLog(value = "ct_daily_loyal_dev-添加")
	@ApiOperation(value="ct_daily_loyal_dev-添加", notes="ct_daily_loyal_dev-添加")
	//@RequiresPermissions("count:ct_daily_loyal_dev:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtDailyLoyalDev ctDailyLoyalDev) {
		ctDailyLoyalDevService.save(ctDailyLoyalDev);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ctDailyLoyalDev
	 * @return
	 */
	@AutoLog(value = "ct_daily_loyal_dev-编辑")
	@ApiOperation(value="ct_daily_loyal_dev-编辑", notes="ct_daily_loyal_dev-编辑")
	//@RequiresPermissions("count:ct_daily_loyal_dev:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtDailyLoyalDev ctDailyLoyalDev) {
		ctDailyLoyalDevService.updateById(ctDailyLoyalDev);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_daily_loyal_dev-通过id删除")
	@ApiOperation(value="ct_daily_loyal_dev-通过id删除", notes="ct_daily_loyal_dev-通过id删除")
	//@RequiresPermissions("count:ct_daily_loyal_dev:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctDailyLoyalDevService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_daily_loyal_dev-批量删除")
	@ApiOperation(value="ct_daily_loyal_dev-批量删除", notes="ct_daily_loyal_dev-批量删除")
	//@RequiresPermissions("count:ct_daily_loyal_dev:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctDailyLoyalDevService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_daily_loyal_dev-通过id查询")
	@ApiOperation(value="ct_daily_loyal_dev-通过id查询", notes="ct_daily_loyal_dev-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtDailyLoyalDev> queryById(@RequestParam(name="id",required=true) String id) {
		CtDailyLoyalDev ctDailyLoyalDev = ctDailyLoyalDevService.getById(id);
		if(ctDailyLoyalDev==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctDailyLoyalDev);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ctDailyLoyalDev
    */
    //@RequiresPermissions("count:ct_daily_loyal_dev:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDailyLoyalDev ctDailyLoyalDev) {
        return super.exportXls(request, ctDailyLoyalDev, CtDailyLoyalDev.class, "ct_daily_loyal_dev");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_daily_loyal_dev:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDailyLoyalDev.class);
    }

}
