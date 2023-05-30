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
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.dto.RetainAnalyzeDto;
import org.jeecg.modules.count.entity.RetainAnalyze;
import org.jeecg.modules.count.service.IRetainAnalyzeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.RetainLoyalVo;
import org.jeecg.modules.count.vo.RetainNewLoyalVo;
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
 * @Description: 留存分析
 * @Author: jeecg-boot
 * @Date:   2023-05-17
 * @Version: V1.0
 */
@Api(tags="留存分析")
@RestController
@RequestMapping("/count/retainAnalyze")
@Slf4j
public class RetainAnalyzeController extends JeecgController<RetainAnalyze, IRetainAnalyzeService> {

	private static final String LOYAL_RETAIN = "loyal";
	private static final String LOYAL_PAY_RETAIN = "newLoyal";
	@Autowired
	private IRetainAnalyzeService retainAnalyzeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param retainAnalyze
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "留存分析-分页列表查询")
	@ApiOperation(value="留存分析-分页列表查询", notes="留存分析-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<RetainAnalyze>> queryPageList(RetainAnalyze retainAnalyze,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RetainAnalyze> queryWrapper = QueryGenerator.initQueryWrapper(retainAnalyze, req.getParameterMap());
		Page<RetainAnalyze> page = new Page<RetainAnalyze>(pageNo, pageSize);
		IPage<RetainAnalyze> pageList = retainAnalyzeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**@param retainAnalyzeDto
	 * @author chenglin
	 * @description
	 * @date 11:53 2023/5/18
	 **/
	 @ApiOperation(value="自定义分页列表查询", notes="留存分析-查询")
	 @GetMapping(value = "/queryList")
	public Result<?>queryList(RetainAnalyzeDto retainAnalyzeDto){
		if(retainAnalyzeDto.getRetainType().equals(LOYAL_RETAIN)){
			List<RetainLoyalVo>retainList = retainAnalyzeService.queryRetainList(retainAnalyzeDto);
			return Result.OK(retainList);
		}else{
			List<RetainNewLoyalVo>retainNewLoyalVoList = retainAnalyzeService.queryRetainNewLoyalList(retainAnalyzeDto);
			return Result.OK(retainNewLoyalVoList);
		}
	}

	/**
	 *   添加
	 *
	 * @param retainAnalyze
	 * @return
	 */
	@AutoLog(value = "留存分析-添加")
	@ApiOperation(value="留存分析-添加", notes="留存分析-添加")
	//@RequiresPermissions("count:ct_daily_retain:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody RetainAnalyze retainAnalyze) {
		retainAnalyzeService.save(retainAnalyze);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param retainAnalyze
	 * @return
	 */
	@AutoLog(value = "留存分析-编辑")
	@ApiOperation(value="留存分析-编辑", notes="留存分析-编辑")
	//@RequiresPermissions("count:ct_daily_retain:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody RetainAnalyze retainAnalyze) {
		retainAnalyzeService.updateById(retainAnalyze);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "留存分析-通过id删除")
	@ApiOperation(value="留存分析-通过id删除", notes="留存分析-通过id删除")
	//@RequiresPermissions("count:ct_daily_retain:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		retainAnalyzeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "留存分析-批量删除")
	@ApiOperation(value="留存分析-批量删除", notes="留存分析-批量删除")
	//@RequiresPermissions("count:ct_daily_retain:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.retainAnalyzeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "留存分析-通过id查询")
	@ApiOperation(value="留存分析-通过id查询", notes="留存分析-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<RetainAnalyze> queryById(@RequestParam(name="id",required=true) String id) {
		RetainAnalyze retainAnalyze = retainAnalyzeService.getById(id);
		if(retainAnalyze==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(retainAnalyze);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param retainAnalyze
    */
    //@RequiresPermissions("count:ct_daily_retain:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RetainAnalyze retainAnalyze) {
        return super.exportXls(request, retainAnalyze, RetainAnalyze.class, "留存分析");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_daily_retain:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, RetainAnalyze.class);
    }

}
