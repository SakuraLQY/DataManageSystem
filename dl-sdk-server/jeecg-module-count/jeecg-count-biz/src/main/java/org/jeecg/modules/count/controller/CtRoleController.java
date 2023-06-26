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
import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.dto.CtRoleDto;
import org.jeecg.modules.count.dto.OrderDetailDto;
import org.jeecg.modules.count.entity.CtRole;
import org.jeecg.modules.count.modal.CtRoleModal;
import org.jeecg.modules.count.modal.OrderDetailModal;
import org.jeecg.modules.count.service.ICtOrderService;
import org.jeecg.modules.count.service.ICtRoleService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.CtRoleVo;
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
 * @Description: ct_role
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="ct_role")
@RestController
@RequestMapping("/count/ctRole")
@Slf4j
public class CtRoleController extends JeecgController<CtRole, ICtRoleService> {
	@Autowired
	private ICtRoleService ctRoleService;
	@Autowired
	private ICtOrderService orderService;

	/**
	 * 分页列表查询
	 *
	 * @param ctRole
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_role-分页列表查询")
	@ApiOperation(value="ct_role-分页列表查询", notes="ct_role-分页列表查询")
	@GetMapping(value = "/list")
	@UserPermissionData(alias = "a")
	public Result<IPage<CtRoleVo>> queryPageList(CtRoleDto ctRole,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<T> page = new Page<>(pageNo, pageSize);
		IPage<CtRoleVo> pageList = ctRoleService.getPageList(page, ctRole);
		return Result.OK(pageList);
	}

	 @ApiOperation(value="ct_role-查询用户充值数据", notes="ct_role-查询用户充值数据")
	 @GetMapping(value = "/getOrderDetail")
	 public Result<OrderDetailModal> getOrderDetail(OrderDetailDto orderDetailDto) {
		 return Result.OK(orderService.getOrderDetail(orderDetailDto));
	 }

	/**
	 *   添加
	 *
	 * @param ctRole
	 * @return
	 */
	@AutoLog(value = "ct_role-添加")
	@ApiOperation(value="ct_role-添加", notes="ct_role-添加")
	//@RequiresPermissions("count:ct_role:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtRole ctRole) {
		ctRoleService.save(ctRole);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param ctRole
	 * @return
	 */
	@AutoLog(value = "ct_role-编辑")
	@ApiOperation(value="ct_role-编辑", notes="ct_role-编辑")
	//@RequiresPermissions("count:ct_role:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtRole ctRole) {
		ctRoleService.updateById(ctRole);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_role-通过id删除")
	@ApiOperation(value="ct_role-通过id删除", notes="ct_role-通过id删除")
	//@RequiresPermissions("count:ct_role:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctRoleService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_role-批量删除")
	@ApiOperation(value="ct_role-批量删除", notes="ct_role-批量删除")
	//@RequiresPermissions("count:ct_role:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctRoleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_role-通过id查询")
	@ApiOperation(value="ct_role-通过id查询", notes="ct_role-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtRole> queryById(@RequestParam(name="id",required=true) String id) {
		CtRole ctRole = ctRoleService.getById(id);
		if(ctRole==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctRole);
	}

    /**
    * 导出excel
    *
    * @param ctRole
    */
    //@RequiresPermissions("count:ct_role:exportXls")
    @RequestMapping(value = "/exportXls")
	@UserPermissionData(alias = "a")
    public ModelAndView exportXls(CtRoleDto ctRole) {
        return ctRoleService.exportXls(ctRole, CtRoleModal.class, "角色数据");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_role:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtRole.class);
    }

}
