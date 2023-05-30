package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.entity.OpAnchorPlan;
import org.jeecg.modules.advert.service.IOpAnchorPlanService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.advert.vo.CampaignAnchorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_anchor_plan
 * @Author: jeecg-boot
 * @Date:   2023-02-28
 * @Version: V1.0
 */
@Api(tags="op_anchor_plan")
@RestController
@RequestMapping("/advert/opAnchorPlan")
@Slf4j
public class OpAnchorPlanController extends JeecgController<OpAnchorPlan, IOpAnchorPlanService> {
	@Autowired
	private IOpAnchorPlanService opAnchorPlanService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opAnchorPlan
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_anchor_plan-分页列表查询")
	@ApiOperation(value="op_anchor_plan-分页列表查询", notes="op_anchor_plan-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpAnchorPlan>> queryPageList(OpAnchorPlan opAnchorPlan,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpAnchorPlan> queryWrapper = QueryGenerator.initQueryWrapper(opAnchorPlan, req.getParameterMap());
		Page<OpAnchorPlan> page = new Page<OpAnchorPlan>(pageNo, pageSize);
		IPage<OpAnchorPlan> pageList = opAnchorPlanService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opAnchorPlan
	 * @return
	 */
	@AutoLog(value = "op_anchor_plan-添加")
	@ApiOperation(value="op_anchor_plan-添加", notes="op_anchor_plan-添加")
	//@RequiresPermissions("advert:op_anchor_plan:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpAnchorPlan opAnchorPlan) {
		opAnchorPlanService.save(opAnchorPlan);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param opAnchorPlan
	 * @return
	 */
	@AutoLog(value = "op_anchor_plan-编辑")
	@ApiOperation(value="op_anchor_plan-编辑", notes="op_anchor_plan-编辑")
	//@RequiresPermissions("advert:op_anchor_plan:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpAnchorPlan opAnchorPlan) {
		opAnchorPlanService.updateById(opAnchorPlan);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_anchor_plan-通过id删除")
	@ApiOperation(value="op_anchor_plan-通过id删除", notes="op_anchor_plan-通过id删除")
	//@RequiresPermissions("advert:op_anchor_plan:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opAnchorPlanService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_anchor_plan-批量删除")
	@ApiOperation(value="op_anchor_plan-批量删除", notes="op_anchor_plan-批量删除")
	//@RequiresPermissions("advert:op_anchor_plan:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opAnchorPlanService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_anchor_plan-通过id查询")
	@ApiOperation(value="op_anchor_plan-通过id查询", notes="op_anchor_plan-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpAnchorPlan> queryById(@RequestParam(name="id",required=true) String id) {
		OpAnchorPlan opAnchorPlan = opAnchorPlanService.getById(id);
		if(opAnchorPlan==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opAnchorPlan);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opAnchorPlan
    */
    //@RequiresPermissions("advert:op_anchor_plan:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpAnchorPlan opAnchorPlan) {
        return super.exportXls(request, opAnchorPlan, OpAnchorPlan.class, "op_anchor_plan");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_anchor_plan:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpAnchorPlan.class);
    }

	/**
	 * @author xmh
	 * @description 主播绑定广告
	 * @date 2023/3/3 15:21
	 * @param dealId: 广告ID
	 * @param anchorId: 主播ID
	 * @return Result<String>
	 */
	@ApiOperation(value="主播绑定广告", notes="主播绑定广告")
	@GetMapping(value = "/bind")
	public Result<String> bindDeal(@RequestParam(name="dealId") Integer dealId, @RequestParam(name="anchorId") Integer anchorId) {
		opAnchorPlanService.bindDeal(dealId, anchorId);
		return Result.OK("绑定成功");
	}

	/**
	 * @author xmh
	 * @description 主播广告信息
	 * @date 2023/3/8 13:54
	 * @return Result<List<CampaignAnchorVo>>
	 */
	@ApiOperation(value="主播广告信息", notes="主播广告信息")
	@GetMapping(value = "/anchorDealInfo")
	public Result<List<CampaignAnchorVo>> dealInfo() {
		List<CampaignAnchorVo> list = opAnchorPlanService.anchorDealInfo();
		return Result.OK(list);
	}
}
