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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.entity.OpJrttAweme;
import org.jeecg.modules.advert.service.IOpJrttAwemeService;
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
 * @Description: op_jrtt_aweme
 * @Author: jeecg-boot
 * @Date:   2023-02-22
 * @Version: V1.0
 */
@Api(tags="op_jrtt_aweme")
@RestController
@RequestMapping("/aweme/opJrttAweme")
@Slf4j
public class OpJrttAwemeController extends JeecgController<OpJrttAweme, IOpJrttAwemeService> {
	@Autowired
	private IOpJrttAwemeService opJrttAwemeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opJrttAweme
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_aweme-分页列表查询")
	@ApiOperation(value="op_jrtt_aweme-分页列表查询", notes="op_jrtt_aweme-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpJrttAweme>> queryPageList(OpJrttAweme opJrttAweme,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="100") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpJrttAweme> queryWrapper = QueryGenerator.initQueryWrapper(opJrttAweme, req.getParameterMap());
		Page<OpJrttAweme> page = new Page<OpJrttAweme>(pageNo, pageSize);
		IPage<OpJrttAweme> pageList = opJrttAwemeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opJrttAweme
	 * @return
	 */
	@AutoLog(value = "op_jrtt_aweme-添加")
	@ApiOperation(value="op_jrtt_aweme-添加", notes="op_jrtt_aweme-添加")
	//@RequiresPermissions("aweme:op_jrtt_aweme:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpJrttAweme opJrttAweme) {
		opJrttAwemeService.save(opJrttAweme);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param opJrttAweme
	 * @return
	 */
	@AutoLog(value = "op_jrtt_aweme-编辑")
	@ApiOperation(value="op_jrtt_aweme-编辑", notes="op_jrtt_aweme-编辑")
	//@RequiresPermissions("aweme:op_jrtt_aweme:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpJrttAweme opJrttAweme) {
		opJrttAwemeService.updateById(opJrttAweme);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_jrtt_aweme-通过id删除")
	@ApiOperation(value="op_jrtt_aweme-通过id删除", notes="op_jrtt_aweme-通过id删除")
	//@RequiresPermissions("aweme:op_jrtt_aweme:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opJrttAwemeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_jrtt_aweme-批量删除")
	@ApiOperation(value="op_jrtt_aweme-批量删除", notes="op_jrtt_aweme-批量删除")
	//@RequiresPermissions("aweme:op_jrtt_aweme:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opJrttAwemeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_aweme-通过id查询")
	@ApiOperation(value="op_jrtt_aweme-通过id查询", notes="op_jrtt_aweme-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpJrttAweme> queryById(@RequestParam(name="id",required=true) String id) {
		OpJrttAweme opJrttAweme = opJrttAwemeService.getById(id);
		if(opJrttAweme==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opJrttAweme);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opJrttAweme
    */
    //@RequiresPermissions("aweme:op_jrtt_aweme:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpJrttAweme opJrttAweme) {
        return super.exportXls(request, opJrttAweme, OpJrttAweme.class, "op_jrtt_aweme");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("aweme:op_jrtt_aweme:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpJrttAweme.class);
    }

	 /**
	  * 根据accountId同步
	  *
	  * @param accountId
	  * @return
	  */
	 @ApiOperation(value="op_jrtt_aweme-根据accountId同步", notes="op_jrtt_aweme-根据accountId同步")
	 @PostMapping(value = "/syncAwemeByAccountId")
	 public Result<List<OpJrttAweme>> syncAwemeByAccountId(@RequestParam(name="accountId",required=true) Integer accountId) {
		 return Result.OK(opJrttAwemeService.syncAwemeByAccountId(accountId));
	 }

}
