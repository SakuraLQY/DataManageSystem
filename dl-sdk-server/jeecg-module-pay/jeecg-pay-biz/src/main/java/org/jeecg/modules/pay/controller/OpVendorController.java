package org.jeecg.modules.pay.controller;

import java.util.Arrays;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.pay.entity.OpVendor;
import org.jeecg.modules.pay.service.IOpVendorService;
import org.jeecg.modules.pay.vo.OpVendorVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_vendor
 * @Author: jeecg-boot
 * @Date:   2022-12-10
 * @Version: V1.0
 */
@Api(tags="op_vendor")
@RestController
@RequestMapping("/pay/vendor/opVendor")
@Slf4j
public class OpVendorController extends JeecgController<OpVendor, IOpVendorService> {
	@Resource
	private IOpVendorService opVendorService;

	/**
	 * 分页列表查询
	 *
	 * @param opVendor
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_vendor-分页列表查询")
	@ApiOperation(value="op_vendor-分页列表查询", notes="op_vendor-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpVendorVo>> queryPageList(OpVendor opVendor,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		// 厂商名模糊查询
		opVendor.setVendorName("*" + opVendor.getVendorName() + "*");
		QueryWrapper<OpVendor> queryWrapper = QueryGenerator.initQueryWrapper(opVendor, req.getParameterMap());
		Page<OpVendor> page = new Page<OpVendor>(pageNo, pageSize);
		IPage<OpVendorVo> pageList = opVendorService.getOpVendorByPage(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param opVendor
	 * @return
	 */
	@AutoLog(value = "op_vendor-添加")
	@ApiOperation(value="op_vendor-添加", notes="op_vendor-添加")
	//@RequiresPermissions("vendor:op_vendor:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpVendor opVendor) {
		opVendorService.insert(opVendor);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param opVendor
	 * @return
	 */
	@AutoLog(value = "op_vendor-编辑")
	@ApiOperation(value="op_vendor-编辑", notes="op_vendor-编辑")
	//@RequiresPermissions("vendor:op_vendor:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpVendor opVendor) {
		opVendorService.update(opVendor);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_vendor-通过id删除")
	@ApiOperation(value="op_vendor-通过id删除", notes="op_vendor-通过id删除")
	//@RequiresPermissions("vendor:op_vendor:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opVendorService.deleteId(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_vendor-批量删除")
	@ApiOperation(value="op_vendor-批量删除", notes="op_vendor-批量删除")
	//@RequiresPermissions("vendor:op_vendor:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opVendorService.deleteIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_vendor-通过id查询")
	@ApiOperation(value="op_vendor-通过id查询", notes="op_vendor-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpVendor> queryById(@RequestParam(name="id",required=true) String id) {
		OpVendor opVendor = opVendorService.getById(id);
		if(opVendor==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opVendor);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opVendor
    */
    //@RequiresPermissions("vendor:op_vendor:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpVendor opVendor) {
        return super.exportXls(request, opVendor, OpVendor.class, "op_vendor");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("vendor:op_vendor:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpVendor.class);
    }

}
