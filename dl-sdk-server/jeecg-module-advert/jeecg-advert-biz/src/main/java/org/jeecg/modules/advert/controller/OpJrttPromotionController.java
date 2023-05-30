package org.jeecg.modules.advert.controller;

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
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListListResponse;
import org.jeecg.modules.advert.dto.OpJrttPromotionDto;
import org.jeecg.modules.advert.entity.OpJrttPromotion;
import org.jeecg.modules.advert.service.IOpJrttPromotionService;

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
 * @Description: op_jrtt_promotion
 * @Author: jeecg-boot
 * @Date:   2023-02-22
 * @Version: V1.0
 */
@Api(tags="op_jrtt_promotion")
@RestController
@RequestMapping("/advert/opJrttPromotion")
@Slf4j
public class OpJrttPromotionController extends JeecgController<OpJrttPromotion, IOpJrttPromotionService> {
	@Autowired
	private IOpJrttPromotionService opJrttPromotionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opJrttPromotion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_promotion-分页列表查询")
	@ApiOperation(value="op_jrtt_promotion-分页列表查询", notes="op_jrtt_promotion-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpJrttPromotion>> queryPageList(OpJrttPromotion opJrttPromotion,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpJrttPromotion> queryWrapper = QueryGenerator.initQueryWrapper(opJrttPromotion, req.getParameterMap());
		Page<OpJrttPromotion> page = new Page<OpJrttPromotion>(pageNo, pageSize);
		IPage<OpJrttPromotion> pageList = opJrttPromotionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opJrttPromotionDto
	 * @return
	 */
	@AutoLog(value = "op_jrtt_promotion-添加")
	@ApiOperation(value="op_jrtt_promotion-添加", notes="op_jrtt_promotion-添加")
	//@RequiresPermissions("advert:op_jrtt_promotion:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpJrttPromotionDto opJrttPromotionDto) {
		opJrttPromotionService.saveOpJrttPromotion(opJrttPromotionDto);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param opJrttPromotion
	 * @return
	 */
	@AutoLog(value = "op_jrtt_promotion-编辑")
	@ApiOperation(value="op_jrtt_promotion-编辑", notes="op_jrtt_promotion-编辑")
	//@RequiresPermissions("advert:op_jrtt_promotion:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpJrttPromotionDto opJrttPromotion) {
		opJrttPromotionService.updateOpJrttPromotion(opJrttPromotion);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_jrtt_promotion-通过id删除")
	@ApiOperation(value="op_jrtt_promotion-通过id删除", notes="op_jrtt_promotion-通过id删除")
	//@RequiresPermissions("advert:op_jrtt_promotion:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opJrttPromotionService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_jrtt_promotion-批量删除")
	@ApiOperation(value="op_jrtt_promotion-批量删除", notes="op_jrtt_promotion-批量删除")
	//@RequiresPermissions("advert:op_jrtt_promotion:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opJrttPromotionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_promotion-通过id查询")
	@ApiOperation(value="op_jrtt_promotion-通过id查询", notes="op_jrtt_promotion-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpJrttPromotion> queryById(@RequestParam(name="id",required=true) String id) {
		OpJrttPromotion opJrttPromotion = opJrttPromotionService.getById(id);
		if(opJrttPromotion==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opJrttPromotion);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opJrttPromotion
    */
    //@RequiresPermissions("advert:op_jrtt_promotion:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpJrttPromotion opJrttPromotion) {
        return super.exportXls(request, opJrttPromotion, OpJrttPromotion.class, "op_jrtt_promotion");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_jrtt_promotion:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpJrttPromotion.class);
    }

}
