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
import org.jeecg.modules.count.dto.LtvAnalysisDto;
import org.jeecg.modules.count.entity.LtvAnalysis;
import org.jeecg.modules.count.service.ILtvAnalysisService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.LtvAnalysisVo;
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
 * @Description: ltv_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Api(tags="ltv_analysis")
@RestController
@RequestMapping("/count/ltvAnalysis")
@Slf4j
public class LtvAnalysisController extends JeecgController<LtvAnalysis, ILtvAnalysisService> {
	@Autowired
	private ILtvAnalysisService ltvAnalysisService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ltvAnalysis
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ltv_analysis-分页列表查询")
	@ApiOperation(value="ltv_analysis-分页列表查询", notes="ltv_analysis-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LtvAnalysis>> queryPageList(LtvAnalysis ltvAnalysis,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<LtvAnalysis> queryWrapper = QueryGenerator.initQueryWrapper(ltvAnalysis, req.getParameterMap());
		Page<LtvAnalysis> page = new Page<LtvAnalysis>(pageNo, pageSize);
		IPage<LtvAnalysis> pageList = ltvAnalysisService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	/**@param ltvAnalysisDto
	 * @author chenglin
	 * @description 查询LTV数据统计的列表
	 * @date 18:39 2023/5/24
	 **/
	@ApiOperation(value="ltv_analysis查询", notes="ltv_analysis查询")
	@GetMapping(value = "/queryList")
	public Result<List<LtvAnalysisVo>>queryList(LtvAnalysisDto ltvAnalysisDto){
		return Result.OK(ltvAnalysisService.queryList(ltvAnalysisDto));
	}
	/**
	 *   添加
	 *
	 * @param ltvAnalysis
	 * @return
	 */
	@AutoLog(value = "ltv_analysis-添加")
	@ApiOperation(value="ltv_analysis-添加", notes="ltv_analysis-添加")
	//@RequiresPermissions("count:ltv_analysis:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LtvAnalysis ltvAnalysis) {
		ltvAnalysisService.save(ltvAnalysis);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ltvAnalysis
	 * @return
	 */
	@AutoLog(value = "ltv_analysis-编辑")
	@ApiOperation(value="ltv_analysis-编辑", notes="ltv_analysis-编辑")
	//@RequiresPermissions("count:ltv_analysis:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LtvAnalysis ltvAnalysis) {
		ltvAnalysisService.updateById(ltvAnalysis);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ltv_analysis-通过id删除")
	@ApiOperation(value="ltv_analysis-通过id删除", notes="ltv_analysis-通过id删除")
	//@RequiresPermissions("count:ltv_analysis:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ltvAnalysisService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ltv_analysis-批量删除")
	@ApiOperation(value="ltv_analysis-批量删除", notes="ltv_analysis-批量删除")
	//@RequiresPermissions("count:ltv_analysis:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ltvAnalysisService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ltv_analysis-通过id查询")
	@ApiOperation(value="ltv_analysis-通过id查询", notes="ltv_analysis-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LtvAnalysis> queryById(@RequestParam(name="id",required=true) String id) {
		LtvAnalysis ltvAnalysis = ltvAnalysisService.getById(id);
		if(ltvAnalysis==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ltvAnalysis);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ltvAnalysis
    */
    //@RequiresPermissions("count:ltv_analysis:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LtvAnalysis ltvAnalysis) {
        return super.exportXls(request, ltvAnalysis, LtvAnalysis.class, "ltv_analysis");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ltv_analysis:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LtvAnalysis.class);
    }

}
