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
import org.jeecg.modules.advert.entity.OpJrttSite;
import org.jeecg.modules.advert.service.IOpJrttSiteService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.service.IOpMaterialService;
import org.jeecg.modules.advert.vo.JrttSiteListVo;
import org.jeecg.modules.advert.vo.QuerySiteMaterialVo;
import org.jeecg.modules.advert.vo.ResponseSiteMaterialVo;
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
 * @Description: op_jrtt_site
 * @Author: jeecg-boot
 * @Date:   2023-02-16
 * @Version: V1.0
 */
@Api(tags="op_jrtt_site")
@RestController
@RequestMapping("/advert/opJrttSite")
@Slf4j
public class OpJrttSiteController extends JeecgController<OpJrttSite, IOpJrttSiteService> {
	@Autowired
	private IOpJrttSiteService opJrttSiteService;

	@Autowired
	private IOpMaterialService materialService;

	/**
	 * 分页列表查询
	 *
	 * @param opJrttSite
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_site-分页列表查询")
	@ApiOperation(value="op_jrtt_site-分页列表查询", notes="op_jrtt_site-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<JrttSiteListVo>> queryPageList(OpJrttSite opJrttSite,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="8") Integer pageSize,
								   HttpServletRequest req) {
		Page<JrttSiteListVo> page = new Page<>(pageNo, pageSize);
		IPage<JrttSiteListVo> sites = opJrttSiteService.getPage(page,opJrttSite);
		return Result.ok(sites);
	}
	@ApiOperation("分页查询落地页素材")
	@GetMapping("/listMaterial")
	public Result<IPage<ResponseSiteMaterialVo>> querySiteMaterials(
		@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
		@RequestParam(name="pageSize", defaultValue="8") Integer pageSize, QuerySiteMaterialVo params){
		Page<ResponseSiteMaterialVo> page = new Page<>(pageNo, pageSize);
		IPage<ResponseSiteMaterialVo> siteMaterialPage = materialService
			.getSiteMaterialPage(page, params);
		return Result.OK(siteMaterialPage);
	}

	/**
	 *   添加
	 *
	 * @param opJrttSite
	 * @return
	 */
	@AutoLog(value = "op_jrtt_site-添加")
	@ApiOperation(value="op_jrtt_site-添加", notes="op_jrtt_site-添加")
	//@RequiresPermissions("advert:op_jrtt_site:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpJrttSite opJrttSite) {
		opJrttSiteService.saveSite(opJrttSite);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param opJrttSite
	 * @return
	 */
	@AutoLog(value = "op_jrtt_site-编辑")
	@ApiOperation(value="op_jrtt_site-编辑", notes="op_jrtt_site-编辑")
	//@RequiresPermissions("advert:op_jrtt_site:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpJrttSite opJrttSite) {
		opJrttSiteService.updateById(opJrttSite);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_jrtt_site-通过id删除")
	@ApiOperation(value="op_jrtt_site-通过id删除", notes="op_jrtt_site-通过id删除")
	//@RequiresPermissions("advert:op_jrtt_site:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opJrttSiteService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_jrtt_site-批量删除")
	@ApiOperation(value="op_jrtt_site-批量删除", notes="op_jrtt_site-批量删除")
	//@RequiresPermissions("advert:op_jrtt_site:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opJrttSiteService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_site-通过id查询")
	@ApiOperation(value="op_jrtt_site-通过id查询", notes="op_jrtt_site-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpJrttSite> queryById(@RequestParam(name="id",required=true) String id) {
		OpJrttSite opJrttSite = opJrttSiteService.getById(id);
		if(opJrttSite==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opJrttSite);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opJrttSite
    */
    //@RequiresPermissions("advert:op_jrtt_site:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpJrttSite opJrttSite) {
        return super.exportXls(request, opJrttSite, OpJrttSite.class, "op_jrtt_site");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_jrtt_site:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpJrttSite.class);
    }
}
