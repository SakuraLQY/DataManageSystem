package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.advert.dto.OpXingtuDropDealDto;
import org.jeecg.modules.advert.entity.OpXingtuDropDeal;
import org.jeecg.modules.advert.service.IOpXingtuDropDealService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.advert.vo.OpXingtuDropDealVo;
import org.jeecg.modules.advert.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_xingtu_drop_deal
 * @Author: jeecg-boot
 * @Date:   2023-03-07
 * @Version: V1.0
 */
@Api(tags="op_xingtu_drop_deal")
@RestController
@RequestMapping("/advert/opXingtuDropDeal")
@Slf4j
public class OpXingtuDropDealController extends JeecgController<OpXingtuDropDeal, IOpXingtuDropDealService> {
	@Autowired
	private IOpXingtuDropDealService opXingtuDropDealService;

	/**
	 * 分页列表查询
	 *
	 * @param opXingtuDropDeal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_xingtu_drop_deal-分页列表查询")
	@ApiOperation(value="op_xingtu_drop_deal-分页列表查询", notes="op_xingtu_drop_deal-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpXingtuDropDealVo>> queryPageList(OpXingtuDropDeal opXingtuDropDeal,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req, String startDate, String endDate) {
		Page<OpXingtuDropDealVo> page = new Page<OpXingtuDropDealVo>(pageNo, pageSize);
		IPage<OpXingtuDropDealVo> pageList = opXingtuDropDealService.getByPage(page, opXingtuDropDeal, startDate, endDate);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param opXingtuDropDealDto
	 * @return
	 */
	@AutoLog(value = "op_xingtu_drop_deal-添加")
	@ApiOperation(value="op_xingtu_drop_deal-添加", notes="op_xingtu_drop_deal-添加")
	//@RequiresPermissions("advert:op_xingtu_drop_deal:add")
	@PostMapping(value = "/add")
	public Result<List<MessageVo>> add(@RequestBody OpXingtuDropDealDto opXingtuDropDealDto) {
		List<MessageVo> campaignVoList = opXingtuDropDealService.addDropDeal(opXingtuDropDealDto);
		return Result.OK(campaignVoList);
	}

	/**
	 *  编辑
	 *
	 * @param opXingtuDropDealDto
	 * @return
	 */
	@AutoLog(value = "op_xingtu_drop_deal-编辑")
	@ApiOperation(value="op_xingtu_drop_deal-编辑", notes="op_xingtu_drop_deal-编辑")
	//@RequiresPermissions("advert:op_xingtu_drop_deal:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpXingtuDropDealDto opXingtuDropDealDto) {
		opXingtuDropDealService.updateDropDeal(opXingtuDropDealDto);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_xingtu_drop_deal-通过id删除")
	@ApiOperation(value="op_xingtu_drop_deal-通过id删除", notes="op_xingtu_drop_deal-通过id删除")
	//@RequiresPermissions("advert:op_xingtu_drop_deal:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opXingtuDropDealService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_xingtu_drop_deal-批量删除")
	@ApiOperation(value="op_xingtu_drop_deal-批量删除", notes="op_xingtu_drop_deal-批量删除")
	//@RequiresPermissions("advert:op_xingtu_drop_deal:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opXingtuDropDealService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_xingtu_drop_deal-通过id查询")
	@ApiOperation(value="op_xingtu_drop_deal-通过id查询", notes="op_xingtu_drop_deal-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpXingtuDropDeal> queryById(@RequestParam(name="id",required=true) String id) {
		OpXingtuDropDeal opXingtuDropDeal = opXingtuDropDealService.getById(id);
		if(opXingtuDropDeal==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opXingtuDropDeal);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opXingtuDropDeal
    */
    //@RequiresPermissions("advert:op_xingtu_drop_deal:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpXingtuDropDeal opXingtuDropDeal) {
        return super.exportXls(request, opXingtuDropDeal, OpXingtuDropDeal.class, "op_xingtu_drop_deal");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_xingtu_drop_deal:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpXingtuDropDeal.class);
    }

	/**
	 * @author xmh
	 * @description 补建星图广告
	 * @date 2023/3/7 17:10
	 * @param opXingtuDropDealDto: 星图广告信息
	 * @return Result<String>
	 */
	@AutoLog(value = "补建星图投放广告")
	@ApiOperation(value="补建星图投放广告", notes="补建星图投放广告")
	@PostMapping(value = "/makeUp")
	public Result<String> makeUp(@RequestBody OpXingtuDropDealDto opXingtuDropDealDto) {
		opXingtuDropDealService.makeUpDropDeal(opXingtuDropDealDto);
		return Result.OK("补建成功");
	}
}
