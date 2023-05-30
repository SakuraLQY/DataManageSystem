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
import org.jeecg.modules.count.dto.BlockadeDto;
import org.jeecg.modules.count.dto.CtUserDto;
import org.jeecg.modules.count.dto.OnlineUserDto;
import org.jeecg.modules.count.dto.PayUserDto;
import org.jeecg.modules.count.dto.UserLogDto;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.modal.CtUserModal;
import org.jeecg.modules.count.modal.PayUserModal;
import org.jeecg.modules.count.service.ICtUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.service.ILgLoginService;
import org.jeecg.modules.count.vo.CtUserVo;
import org.jeecg.modules.count.vo.OnlineUserVo;
import org.jeecg.modules.count.vo.PayUserVo;
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
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-25
 * @Version: V1.0
 */
@Api(tags="ct_user")
@RestController
@RequestMapping("/count/ctUser")
@Slf4j
public class CtUserController extends JeecgController<CtUser, ICtUserService> {
	@Autowired
	private ICtUserService ctUserService;
	 @Autowired
	 private ILgLoginService lgLoginService;

	/**
	 * 分页列表查询
	 *
	 * @param ctUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_user-分页列表查询")
	@ApiOperation(value="ct_user-分页列表查询", notes="ct_user-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CtUserVo>> queryPageList(CtUserDto ctUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<T> page = new Page<>(pageNo, pageSize);
		IPage<CtUserVo> pageList = ctUserService.getPageList(page, ctUser);
		return Result.OK(pageList);
	}

	 @ApiOperation(value="ct_user-付费用户数据分页列表查询", notes="ct_user-付费用户数据分页列表查询")
	 @GetMapping(value = "/payUserPageList")
	 public Result<IPage<PayUserVo>> payUserPageList(PayUserDto payUserDto,
		 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
		 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
		 HttpServletRequest req) {
		 Page<T> page = new Page<>(pageNo, pageSize);
		 IPage<PayUserVo> pageList = ctUserService.getPagePayUserList(page, payUserDto);
		 return Result.OK(pageList);
	 }

	 @ApiOperation(value="ct_user-在线统计", notes="ct_user-在线统计")
	 @GetMapping(value = "/onlineUserList")
	 public Result<List<OnlineUserVo>> onlineUserList(OnlineUserDto onlineUserDto) {
		 return Result.OK(ctUserService.getOnlineUserList(onlineUserDto));
	 }

	/**
	 * @param userLogDto
	 * @return org.jeecg.common.api.vo.Result<java.util.List < java.lang.String>>
	 * @Author lili
	 * @Description 获取用户登录日志
	 * @Date 14:51 2023/5/4
	 **/
	 @ApiOperation(value="ct_user-获取用户登录日志", notes="ct_user-获取用户登录日志")
	 @GetMapping(value = "/getUserLoginLog")
	 public Result<List<String>> getUserLoginLog(UserLogDto userLogDto) {
		 return Result.OK(lgLoginService.getUseLoginLog(userLogDto));
	 }

	 /**
	  * @param ctUser
	  * @return org.jeecg.common.api.vo.Result<java.lang.String>
	  * @Author lili
	  * @Description 封号、封IP操作
	  * @Date 11:51 2023/5/5
	  **/
	 @AutoLog(value = "ct_user-封号、封IP操作")
	 @ApiOperation(value="ct_user-封号、封IP操作", notes="ct_user-封号、封IP操作")
	 //@RequiresPermissions("count:ct_user:add")
	 @PostMapping(value = "/blockade")
	 public Result<String> blockade(@RequestBody BlockadeDto ctUser) {
		 ctUserService.blockade(ctUser);
		 return Result.OK("操作成功！");
	 }

	/**
	 *   添加
	 *
	 * @param ctUser
	 * @return
	 */
	@AutoLog(value = "ct_user-添加")
	@ApiOperation(value="ct_user-添加", notes="ct_user-添加")
	//@RequiresPermissions("count:ct_user:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtUser ctUser) {
		ctUserService.save(ctUser);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param ctUser
	 * @return
	 */
	@AutoLog(value = "ct_user-编辑")
	@ApiOperation(value="ct_user-编辑", notes="ct_user-编辑")
	//@RequiresPermissions("count:ct_user:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtUser ctUser) {
		ctUserService.updateById(ctUser);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_user-通过id删除")
	@ApiOperation(value="ct_user-通过id删除", notes="ct_user-通过id删除")
	//@RequiresPermissions("count:ct_user:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctUserService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_user-批量删除")
	@ApiOperation(value="ct_user-批量删除", notes="ct_user-批量删除")
	//@RequiresPermissions("count:ct_user:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_user-通过id查询")
	@ApiOperation(value="ct_user-通过id查询", notes="ct_user-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtUser> queryById(@RequestParam(name="id",required=true) String id) {
		CtUser ctUser = ctUserService.getById(id);
		if(ctUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctUser);
	}

    /**
    * 导出excel
    *
    * @param ctUser
    */
    //@RequiresPermissions("count:ct_user:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(CtUserDto ctUser) {
        return ctUserService.exportXls(ctUser, CtUserModal.class, "用户数据");
    }

	 /**
	  * 导出excel
	  *
	  * @param payUserDto
	  */
	 //@RequiresPermissions("count:ct_user:exportXls")
	 @RequestMapping(value = "/payUseExportXls")
	 public ModelAndView exportXls(PayUserDto payUserDto) {
		 return ctUserService.payUseExportXls(payUserDto, PayUserModal.class, "付费用户数据");
	 }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_user:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtUser.class);
    }

}
