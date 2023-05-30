package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.entity.OpJrttPutAccountApp;
import org.jeecg.modules.advert.service.IOpJrttPutAccountAppService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_jrtt_put_account_app
 * @Author: jeecg-boot
 * @Date:   2023-01-09
 * @Version: V1.0
 */
@Api(tags="op_jrtt_put_account_app")
@RestController
@RequestMapping("/opputaccountapp/opJrttPutAccountApp")
@Slf4j
public class OpJrttPutAccountAppController extends JeecgController<OpJrttPutAccountApp, IOpJrttPutAccountAppService> {
	@Autowired
	private IOpJrttPutAccountAppService opJrttPutAccountAppService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opJrttPutAccountApp
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_put_account_app-分页列表查询")
	@ApiOperation(value="op_jrtt_put_account_app-分页列表查询", notes="op_jrtt_put_account_app-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpJrttPutAccountApp>> queryPageList(OpJrttPutAccountApp opJrttPutAccountApp,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpJrttPutAccountApp> queryWrapper = QueryGenerator.initQueryWrapper(
			opJrttPutAccountApp, req.getParameterMap());
		Page<OpJrttPutAccountApp> page = new Page<OpJrttPutAccountApp>(pageNo, pageSize);
		IPage<OpJrttPutAccountApp> pageList = opJrttPutAccountAppService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opJrttPutAccountApp
	 * @return
	 */
	@AutoLog(value = "op_jrtt_put_account_app-添加")
	@ApiOperation(value="op_jrtt_put_account_app-添加", notes="op_jrtt_put_account_app-添加")
	//@RequiresPermissions("opputaccountapp:op_jrtt_put_account_app:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpJrttPutAccountApp opJrttPutAccountApp) {
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		opJrttPutAccountApp.setCreateUser(sysUser.getRealname());
		opJrttPutAccountApp.setCreateTime(new Date());
		opJrttPutAccountAppService.save(opJrttPutAccountApp);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param opJrttPutAccountApp
	 * @return
	 */
	@AutoLog(value = "op_jrtt_put_account_app-编辑")
	@ApiOperation(value="op_jrtt_put_account_app-编辑", notes="op_jrtt_put_account_app-编辑")
	//@RequiresPermissions("opputaccountapp:op_jrtt_put_account_app:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpJrttPutAccountApp opJrttPutAccountApp) {
		opJrttPutAccountAppService.updateById(opJrttPutAccountApp);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_jrtt_put_account_app-通过id删除")
	@ApiOperation(value="op_jrtt_put_account_app-通过id删除", notes="op_jrtt_put_account_app-通过id删除")
	//@RequiresPermissions("opputaccountapp:op_jrtt_put_account_app:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opJrttPutAccountAppService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_jrtt_put_account_app-批量删除")
	@ApiOperation(value="op_jrtt_put_account_app-批量删除", notes="op_jrtt_put_account_app-批量删除")
	//@RequiresPermissions("opputaccountapp:op_jrtt_put_account_app:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opJrttPutAccountAppService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_put_account_app-通过id查询")
	@ApiOperation(value="op_jrtt_put_account_app-通过id查询", notes="op_jrtt_put_account_app-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpJrttPutAccountApp> queryById(@RequestParam(name="id",required=true) String id) {
		OpJrttPutAccountApp opJrttPutAccountApp = opJrttPutAccountAppService.getById(id);
		if(opJrttPutAccountApp ==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opJrttPutAccountApp);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opJrttPutAccountApp
    */
    //@RequiresPermissions("opputaccountapp:op_jrtt_put_account_app:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpJrttPutAccountApp opJrttPutAccountApp) {
        return super.exportXls(request, opJrttPutAccountApp, OpJrttPutAccountApp.class, "op_jrtt_put_account_app");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("opputaccountapp:op_jrtt_put_account_app:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpJrttPutAccountApp.class);
    }

}
