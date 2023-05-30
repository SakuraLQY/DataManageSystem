package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.dto.OpXingtuDealDto;
import org.jeecg.modules.advert.dto.PostJrttSiteDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpXingtuDeal;
import org.jeecg.modules.advert.service.IOpJrttSiteService;
import org.jeecg.modules.advert.service.IOpXingtuDealService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.advert.vo.JrttSiteVo;
import org.jeecg.modules.advert.vo.OpXingtuDealVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_xingtu_deal
 * @Author: jeecg-boot
 * @Date:   2023-03-02
 * @Version: V1.0
 */
@Api(tags="op_xingtu_deal")
@RestController
@RequestMapping("/advert/opXingtuDeal")
@Slf4j
public class OpXingtuDealController extends JeecgController<OpXingtuDeal, IOpXingtuDealService> {
	@Autowired
	private IOpXingtuDealService opXingtuDealService;
	@Autowired
	private IOpJrttSiteService opJrttSiteService;

	/**
	 * 分页列表查询
	 *
	 * @param opDeal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_xingtu_deal-分页列表查询")
	@ApiOperation(value="op_xingtu_deal-分页列表查询", notes="op_xingtu_deal-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpXingtuDealVo>> queryPageList(OpDeal opDeal,
								   @RequestParam(name="anchorId", required = false) Integer anchorId,
		                           @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req, String startDate, String endDate) {
		Page<OpXingtuDealVo> page = new Page<>(pageNo, pageSize);
		IPage<OpXingtuDealVo> pageList = opXingtuDealService.getByPage(page, opDeal, anchorId, startDate, endDate);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param opXingtuDealDto
	 * @return
	 */
	@AutoLog(value = "op_xingtu_deal-添加")
	@ApiOperation(value="op_xingtu_deal-添加", notes="op_xingtu_deal-添加")
	//@RequiresPermissions("advert:op_xingtu_deal:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpXingtuDealDto opXingtuDealDto) {
		opXingtuDealService.addDeal(opXingtuDealDto);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param opXingtuDeal
	 * @return
	 */
	@AutoLog(value = "op_xingtu_deal-编辑")
	@ApiOperation(value="op_xingtu_deal-编辑", notes="op_xingtu_deal-编辑")
	//@RequiresPermissions("advert:op_xingtu_deal:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpXingtuDealDto opXingtuDealDto) {
		opXingtuDealService.updateDeal(opXingtuDealDto);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_xingtu_deal-通过id删除")
	@ApiOperation(value="op_xingtu_deal-通过id删除", notes="op_xingtu_deal-通过id删除")
	//@RequiresPermissions("advert:op_xingtu_deal:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opXingtuDealService.deleteDeal(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_xingtu_deal-批量删除")
	@ApiOperation(value="op_xingtu_deal-批量删除", notes="op_xingtu_deal-批量删除")
	//@RequiresPermissions("advert:op_xingtu_deal:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opXingtuDealService.deleteDeal(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_xingtu_deal-通过id查询")
	@ApiOperation(value="op_xingtu_deal-通过id查询", notes="op_xingtu_deal-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpXingtuDeal> queryById(@RequestParam(name="id",required=true) String id) {
		OpXingtuDeal opXingtuDeal = opXingtuDealService.getById(id);
		if(opXingtuDeal==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opXingtuDeal);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opXingtuDeal
    */
    //@RequiresPermissions("advert:op_xingtu_deal:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpXingtuDeal opXingtuDeal) {
        return super.exportXls(request, opXingtuDeal, OpXingtuDeal.class, "op_xingtu_deal");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_xingtu_deal:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpXingtuDeal.class);
    }

	/**
	 * @author xmh
	 * @description 发布星图站点
	 * @date 2023/3/3 16:23
	 * @param postJrttSiteDto: 星图站点信息
	 * @return Result<JrttSiteVo>
	 */
	@AutoLog(value = "发布星图站点")
	@ApiOperation(value="发布星图站点", notes="发布星图站点")
	@PostMapping(value = "/postSite")
	public Result<JrttSiteVo> add(@RequestBody PostJrttSiteDto postJrttSiteDto) {
		JrttSiteVo siteVo = opJrttSiteService.post_site(postJrttSiteDto.getDealId(), postJrttSiteDto.getSiteId(),
			ChannelConstant.XING_TU);
		return Result.OK(siteVo);
	}
}
