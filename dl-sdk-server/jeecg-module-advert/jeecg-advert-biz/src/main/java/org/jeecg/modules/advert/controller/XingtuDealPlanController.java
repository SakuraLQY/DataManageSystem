package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import javafx.geometry.Pos;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.entity.XingtuDealPlan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.service.IXingtuDealPlanService;
import org.jeecg.modules.advert.vo.XingtuDealPlanVo;
import org.jeecg.modules.advert.vo.XingtuEditPlanVo;
import org.jeecg.modules.advert.vo.XingtuPlanVo;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_xingtu_deal_plan
 * @Author: jeecg-boot
 * @Date:   2023-03-08
 * @Version: V1.0
 */
@Api(tags="op_xingtu_deal_plan")
@RestController
@RequestMapping("/XingtuDealPlan/xingtuDealPlan")
@Slf4j
public class XingtuDealPlanController extends JeecgController<XingtuDealPlan, IXingtuDealPlanService> {
	@Autowired
	private IXingtuDealPlanService xingtuDealPlanService;
	
	/**
	 * 分页列表查询
	 *
	 * @param xingtuDealPlan
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_xingtu_deal_plan-分页列表查询")
	@ApiOperation(value="op_xingtu_deal_plan-分页列表查询", notes="op_xingtu_deal_plan-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<XingtuDealPlanVo>> queryPageList(XingtuDealPlan xingtuDealPlan,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		IPage<XingtuDealPlan> page = new Page<>(pageNo,pageSize);
		return Result.OK(xingtuDealPlanService.getByPage(page, xingtuDealPlan));
	}


	@PostMapping(value = "/batchCreatePlan")
	public Result batchCreatePlan(@RequestBody XingtuPlanVo batchCreateInfo){
		return xingtuDealPlanService.batchCreatePlan(batchCreateInfo);
	}

	@PostMapping(value = "/editPlan")
	public Result editPlan(@RequestBody XingtuEditPlanVo editPlanVo){
		return xingtuDealPlanService.editPlan(editPlanVo.getAccountId(),editPlanVo.getXingtuDealPlan());
	}
	/**
	 *   添加
	 *
	 * @param xingtuDealPlan
	 * @return
	 */
	@AutoLog(value = "op_xingtu_deal_plan-添加")
	@ApiOperation(value="op_xingtu_deal_plan-添加", notes="op_xingtu_deal_plan-添加")
	//@RequiresPermissions("XingtuDealPlan:op_xingtu_deal_plan:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody XingtuDealPlan xingtuDealPlan) {
		xingtuDealPlanService.save(xingtuDealPlan);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param xingtuDealPlan
	 * @return
	 */
	@AutoLog(value = "op_xingtu_deal_plan-编辑")
	@ApiOperation(value="op_xingtu_deal_plan-编辑", notes="op_xingtu_deal_plan-编辑")
	//@RequiresPermissions("XingtuDealPlan:op_xingtu_deal_plan:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody XingtuDealPlan xingtuDealPlan) {
		xingtuDealPlanService.updateById(xingtuDealPlan);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_xingtu_deal_plan-通过id删除")
	@ApiOperation(value="op_xingtu_deal_plan-通过id删除", notes="op_xingtu_deal_plan-通过id删除")
	//@RequiresPermissions("XingtuDealPlan:op_xingtu_deal_plan:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		xingtuDealPlanService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_xingtu_deal_plan-批量删除")
	@ApiOperation(value="op_xingtu_deal_plan-批量删除", notes="op_xingtu_deal_plan-批量删除")
	//@RequiresPermissions("XingtuDealPlan:op_xingtu_deal_plan:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.xingtuDealPlanService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_xingtu_deal_plan-通过id查询")
	@ApiOperation(value="op_xingtu_deal_plan-通过id查询", notes="op_xingtu_deal_plan-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<XingtuDealPlan> queryById(@RequestParam(name="id",required=true) String id) {
		XingtuDealPlan xingtuDealPlan = xingtuDealPlanService.getById(id);
		if(xingtuDealPlan==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(xingtuDealPlan);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param xingtuDealPlan
    */
    //@RequiresPermissions("XingtuDealPlan:op_xingtu_deal_plan:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, XingtuDealPlan xingtuDealPlan) {
        return super.exportXls(request, xingtuDealPlan, XingtuDealPlan.class, "op_xingtu_deal_plan");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("XingtuDealPlan:op_xingtu_deal_plan:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, XingtuDealPlan.class);
    }

}
