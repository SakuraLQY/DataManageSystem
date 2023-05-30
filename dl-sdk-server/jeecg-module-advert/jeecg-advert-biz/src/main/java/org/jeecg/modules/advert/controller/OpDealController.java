package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: ad_deal
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Api(tags="op_deal")
@RestController
@RequestMapping("/advert/opDeal")
@Slf4j
public class OpDealController extends JeecgController<OpDeal, IOpDealService> {

	@Autowired
	private IOpDealService jrttDealService;

	/**
	 * 分页列表查询
	 *
	 * @param jrttDeal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_deal-分页列表查询")
	@ApiOperation(value="op_deal-分页列表查询", notes="op_deal-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpDeal>> queryPageList(OpDeal jrttDeal,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpDeal> queryWrapper = QueryGenerator.initQueryWrapper(jrttDeal, req.getParameterMap());
		Page<OpDeal> page = new Page<OpDeal>(pageNo, pageSize);
		IPage<OpDeal> pageList = jrttDealService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param jrttDeal
	 * @return
	 */
	@AutoLog(value = "op_deal-添加")
	@ApiOperation(value="op_deal-添加", notes="op_deal-添加")
	//@RequiresPermissions("advert:ad_deal:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpDeal jrttDeal) {
		jrttDealService.save(jrttDeal);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param jrttDeal
	 * @return
	 */
	@AutoLog(value = "op_deal-编辑")
	@ApiOperation(value="op_deal-编辑", notes="op_deal-编辑")
	//@RequiresPermissions("advert:ad_deal:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpDeal jrttDeal) {
		jrttDealService.updateById(jrttDeal);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_deal-通过id删除")
	@ApiOperation(value="op_deal-通过id删除", notes="op_deal-通过id删除")
	//@RequiresPermissions("advert:ad_deal:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		jrttDealService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_deal-批量删除")
	@ApiOperation(value="op_deal-批量删除", notes="op_deal-批量删除")
	//@RequiresPermissions("advert:ad_deal:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.jrttDealService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_deal-通过id查询")
	@ApiOperation(value="op_deal-通过id查询", notes="op_deal-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpDeal> queryById(@RequestParam(name="id",required=true) String id) {
		OpDeal jrttDeal = jrttDealService.getById(id);
		if(jrttDeal==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(jrttDeal);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param jrttDeal
    */
    //@RequiresPermissions("vendor:ad_deal:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpDeal jrttDeal) {
        return super.exportXls(request, jrttDeal, OpDeal.class, "ad_deal");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("vendor:ad_deal:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpDeal.class);
    }

}
