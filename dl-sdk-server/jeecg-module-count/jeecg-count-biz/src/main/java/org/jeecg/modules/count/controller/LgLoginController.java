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
import org.jeecg.modules.count.entity.LgLogin;
import org.jeecg.modules.count.service.ILgLoginService;

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
 * @Description: lg_login
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
@Api(tags="lg_login")
@RestController
@RequestMapping("/count/lgLogin")
@Slf4j
public class LgLoginController extends JeecgController<LgLogin, ILgLoginService> {
	@Autowired
	private ILgLoginService lgLoginService;
	
	/**
	 * 分页列表查询
	 *
	 * @param lgLogin
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "lg_login-分页列表查询")
	@ApiOperation(value="lg_login-分页列表查询", notes="lg_login-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LgLogin>> queryPageList(LgLogin lgLogin,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<LgLogin> queryWrapper = QueryGenerator.initQueryWrapper(lgLogin, req.getParameterMap());
		Page<LgLogin> page = new Page<LgLogin>(pageNo, pageSize);
		IPage<LgLogin> pageList = lgLoginService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param lgLogin
	 * @return
	 */
	@AutoLog(value = "lg_login-添加")
	@ApiOperation(value="lg_login-添加", notes="lg_login-添加")
	//@RequiresPermissions("count:lg_login:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LgLogin lgLogin) {
		lgLoginService.save(lgLogin);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param lgLogin
	 * @return
	 */
	@AutoLog(value = "lg_login-编辑")
	@ApiOperation(value="lg_login-编辑", notes="lg_login-编辑")
	//@RequiresPermissions("count:lg_login:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LgLogin lgLogin) {
		lgLoginService.updateById(lgLogin);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "lg_login-通过id删除")
	@ApiOperation(value="lg_login-通过id删除", notes="lg_login-通过id删除")
	//@RequiresPermissions("count:lg_login:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		lgLoginService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "lg_login-批量删除")
	@ApiOperation(value="lg_login-批量删除", notes="lg_login-批量删除")
	//@RequiresPermissions("count:lg_login:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.lgLoginService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "lg_login-通过id查询")
	@ApiOperation(value="lg_login-通过id查询", notes="lg_login-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LgLogin> queryById(@RequestParam(name="id",required=true) String id) {
		LgLogin lgLogin = lgLoginService.getById(id);
		if(lgLogin==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(lgLogin);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param lgLogin
    */
    //@RequiresPermissions("count:lg_login:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LgLogin lgLogin) {
        return super.exportXls(request, lgLogin, LgLogin.class, "lg_login");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:lg_login:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LgLogin.class);
    }

}
