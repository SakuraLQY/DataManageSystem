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
import org.jeecg.modules.count.dto.RoiDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.modal.ROIListResult;
import org.jeecg.modules.count.modal.RoiModal;
import org.jeecg.modules.count.service.ICtDailyService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.RoiVo;
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
 * @Description: ct_daily
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="ct_daily")
@RestController
@RequestMapping("/count/ctDaily")
@Slf4j
public class CtDailyController extends JeecgController<CtDaily, ICtDailyService> {
	@Autowired
	private ICtDailyService ctDailyService;

	/**
	 * 分页列表查询
	 *
	 * @param ctDaily
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_daily-分页列表查询")
	@ApiOperation(value="ct_daily-分页列表查询", notes="ct_daily-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CtDaily>> queryPageList(CtDaily ctDaily,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CtDaily> queryWrapper = QueryGenerator.initQueryWrapper(ctDaily, req.getParameterMap());
		Page<CtDaily> page = new Page<CtDaily>(pageNo, pageSize);
		IPage<CtDaily> pageList = ctDailyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 * @param roiDto
	 * @return org.jeecg.common.api.vo.Result<java.util.List < org.jeecg.modules.count.vo.RoiVo>>
	 * @Author lili
	 * @Description ROI查询
	 * @Date 11:36 2023/5/11
	 **/
	 @ApiOperation(value="ct_daily-ROI查询", notes="ct_daily-ROI查询")
	 @GetMapping(value = "/queryROIList")
	 public Result<List<RoiVo>> queryROIList(RoiDto roiDto) {
		 return Result.OK(ctDailyService.queryROIList(roiDto));
	 }

	 /**
	  * 导出excel
	  *
	  * @param request
	  * @param ctDaily
	  */
	 //@RequiresPermissions("count:ct_daily:exportXls")
	 @RequestMapping(value = "/roiExportXls")
	 public ModelAndView roiExportXls(RoiDto roiDto) {
		 return ctDailyService.exportXls(roiDto, RoiModal.class, "ROI数据表");
	 }

	/**
	 *   添加
	 *
	 * @param ctDaily
	 * @return
	 */
	@AutoLog(value = "ct_daily-添加")
	@ApiOperation(value="ct_daily-添加", notes="ct_daily-添加")
	//@RequiresPermissions("count:ct_daily:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtDaily ctDaily) {
		ctDailyService.save(ctDaily);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param ctDaily
	 * @return
	 */
	@AutoLog(value = "ct_daily-编辑")
	@ApiOperation(value="ct_daily-编辑", notes="ct_daily-编辑")
	//@RequiresPermissions("count:ct_daily:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtDaily ctDaily) {
		ctDailyService.updateById(ctDaily);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_daily-通过id删除")
	@ApiOperation(value="ct_daily-通过id删除", notes="ct_daily-通过id删除")
	//@RequiresPermissions("count:ct_daily:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctDailyService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_daily-批量删除")
	@ApiOperation(value="ct_daily-批量删除", notes="ct_daily-批量删除")
	//@RequiresPermissions("count:ct_daily:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctDailyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_daily-通过id查询")
	@ApiOperation(value="ct_daily-通过id查询", notes="ct_daily-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtDaily> queryById(@RequestParam(name="id",required=true) String id) {
		CtDaily ctDaily = ctDailyService.getById(id);
		if(ctDaily==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctDaily);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ctDaily
    */
    //@RequiresPermissions("count:ct_daily:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDaily ctDaily) {
        return super.exportXls(request, ctDaily, CtDaily.class, "ct_daily");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_daily:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDaily.class);
    }

}
