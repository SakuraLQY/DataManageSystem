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
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.service.IOpPayVendorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_pay_vendor
 * @Author: jeecg-boot
 * @Date:   2022-12-10
 * @Version: V1.0
 */
@Api(tags="op_pay_vendor")
@RestController
@RequestMapping("/pay/vendor/opPayVendor")
@Slf4j
public class OpPayVendorController extends JeecgController<OpPayVendor, IOpPayVendorService> {
	@Resource
	private IOpPayVendorService opPayVendorService;

	/**
	 * 分页列表查询
	 *
	 * @param opPayVendor
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_pay_vendor-分页列表查询")
	@ApiOperation(value="op_pay_vendor-分页列表查询", notes="op_pay_vendor-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpPayVendor>> queryPageList(OpPayVendor opPayVendor,
													@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													HttpServletRequest req) {
		// 支付渠道名模糊查询
		opPayVendor.setPayVendorName("*" + opPayVendor.getPayVendorName() + "*");
		QueryWrapper<OpPayVendor> queryWrapper = QueryGenerator.initQueryWrapper(opPayVendor, req.getParameterMap());
		Page<OpPayVendor> page = new Page<OpPayVendor>(pageNo, pageSize);
		IPage<OpPayVendor> pageList = opPayVendorService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param opPayVendor
	 * @return
	 */
	@AutoLog(value = "op_pay_vendor-添加")
	@ApiOperation(value="op_pay_vendor-添加", notes="op_pay_vendor-添加")
	//@RequiresPermissions("vendor:op_pay_vendor:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpPayVendor opPayVendor) {
		opPayVendorService.save(opPayVendor);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param opPayVendor
	 * @return
	 */
	@AutoLog(value = "op_pay_vendor-编辑")
	@ApiOperation(value="op_pay_vendor-编辑", notes="op_pay_vendor-编辑")
	//@RequiresPermissions("vendor:op_pay_vendor:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpPayVendor opPayVendor) {
		opPayVendorService.updateById(opPayVendor);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_pay_vendor-通过id删除")
	@ApiOperation(value="op_pay_vendor-通过id删除", notes="op_pay_vendor-通过id删除")
	//@RequiresPermissions("vendor:op_pay_vendor:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opPayVendorService.deleteId(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_pay_vendor-批量删除")
	@ApiOperation(value="op_pay_vendor-批量删除", notes="op_pay_vendor-批量删除")
	//@RequiresPermissions("vendor:op_pay_vendor:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opPayVendorService.deleteIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_pay_vendor-通过id查询")
	@ApiOperation(value="op_pay_vendor-通过id查询", notes="op_pay_vendor-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpPayVendor> queryById(@RequestParam(name="id",required=true) String id) {
		OpPayVendor opPayVendor = opPayVendorService.getById(id);
		if(opPayVendor==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opPayVendor);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opPayVendor
    */
    //@RequiresPermissions("vendor:op_pay_vendor:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpPayVendor opPayVendor) {
        return super.exportXls(request, opPayVendor, OpPayVendor.class, "op_pay_vendor");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("vendor:op_pay_vendor:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpPayVendor.class);
    }

}
