package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListListResponse;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.modules.advert.dto.OpDealDto;
import org.jeecg.modules.advert.dto.PostJrttSiteDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.service.IJrttDealService;
import org.jeecg.modules.advert.service.IOpJrttSiteService;
import org.jeecg.modules.advert.vo.JrttSiteVo;
import org.jeecg.modules.advert.vo.OpJrttDealVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: op_jrtt_deal
 * @Author: jeecg-boot
 * @Date:   2023-02-14
 * @Version: V1.0
 */
@Api(tags="jrtt_deal")
@RestController
@RequestMapping("/advert/opJrttDeal")
@Slf4j
public class JrttDealController {
	@Autowired
	private IJrttDealService jrttDealService;
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
	//@AutoLog(value = "op_jrtt_deal-分页列表查询")
	 @ApiOperation(value="op_jrtt_deal-分页列表查询", notes="op_jrtt_deal-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<IPage<OpJrttDealVo>> queryPageList(OpDeal opDeal,
		 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
		 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
		 HttpServletRequest req, String startDate, String endDate) {
		 Page<OpJrttDealVo> page = new Page<>(pageNo, pageSize);
		 IPage<OpJrttDealVo> pageList = jrttDealService.getByPage(page, opDeal, startDate, endDate);
		 return Result.OK(pageList);
	 }

	 /**
	  *   添加
	  *
	  * @param opJrttDeal
	  * @return
	  */
	 @AutoLog(value = "op_jrtt_deal-添加")
	 @ApiOperation(value="op_jrtt_deal-添加", notes="op_jrtt_deal-添加")
	 //@RequiresPermissions("advert:op_jrtt_deal:add")
	 @PostMapping(value = "/add")
	 public Result<String> add(@RequestBody OpDealDto opJrttDeal) {
		 jrttDealService.addDeal(opJrttDeal);
		 return Result.OK("添加成功！");
	 }

	/**
	 *  编辑
	 *
	 * @param opJrttDeal
	 * @return
	 */
	@AutoLog(value = "op_jrtt_deal-编辑")
	@ApiOperation(value="op_jrtt_deal-编辑", notes="op_jrtt_deal-编辑")
	//@RequiresPermissions("advert:op_jrtt_deal:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpDealDto opJrttDeal) {
		jrttDealService.updateDeal(opJrttDeal);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_jrtt_deal-通过id删除")
	@ApiOperation(value="op_jrtt_deal-通过id删除", notes="op_jrtt_deal-通过id删除")
	//@RequiresPermissions("advert:op_jrtt_deal:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		jrttDealService.deleteDeal(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_jrtt_deal-批量删除")
	@ApiOperation(value="op_jrtt_deal-批量删除", notes="op_jrtt_deal-批量删除")
	//@RequiresPermissions("advert:op_jrtt_deal:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.jrttDealService.deleteDeals(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	 /**
	  *   创建站点
	  *
	  * @param postJrttSiteDto
	  * @return
	  */
	 @AutoLog(value = "发布头条站点")
	 @ApiOperation(value="发布头条站点", notes="发布头条站点")
	 @PostMapping(value = "/postSite")
	 public Result<JrttSiteVo> add(@RequestBody PostJrttSiteDto postJrttSiteDto) {
		 JrttSiteVo siteVo = opJrttSiteService.post_site(postJrttSiteDto.getDealId(), postJrttSiteDto.getSiteId(),
			 ChannelConstant.JRTT);
		 return Result.OK(siteVo);
	 }

	 /**
	  * 批量创建分包
	  *
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "op_jrtt_deal-创建分包")
	 @ApiOperation(value="op_jrtt_deal-创建分包", notes="op_jrtt_deal-创建分包")
	 //@RequiresPermissions("advert:op_jrtt_deal:deleteBatch")
	 @PostMapping(value = "/extendPackage")
	 public Result<String> extendPackage(@RequestParam(name="id",required=true) Integer id) {
		 this.jrttDealService.reExtendPackage(id);
		 return Result.OK("操作成功");
	 }

}
