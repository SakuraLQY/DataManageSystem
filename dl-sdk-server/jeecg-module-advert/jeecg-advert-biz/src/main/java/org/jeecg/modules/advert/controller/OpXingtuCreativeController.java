package org.jeecg.modules.advert.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.dto.OpXingtuCreativeDto;
import org.jeecg.modules.advert.entity.OpXingtuCreative;
import org.jeecg.modules.advert.service.IOpXingtuCreativeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.advert.vo.CreativeIndustryVo;
import org.jeecg.modules.advert.vo.OpXingtuCreativeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_xingtu_creative
 * @Author: jeecg-boot
 * @Date:   2023-03-20
 * @Version: V1.0
 */
@Api(tags="op_xingtu_creative")
@RestController
@RequestMapping("/advert/opXingtuCreative")
@Slf4j
public class OpXingtuCreativeController extends JeecgController<OpXingtuCreative, IOpXingtuCreativeService> {
	@Autowired
	private IOpXingtuCreativeService opXingtuCreativeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opXingtuCreative
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_xingtu_creative-分页列表查询")
	@ApiOperation(value="op_xingtu_creative-分页列表查询", notes="op_xingtu_creative-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpXingtuCreative>> queryPageList(OpXingtuCreative opXingtuCreative,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpXingtuCreative> queryWrapper = QueryGenerator.initQueryWrapper(opXingtuCreative, req.getParameterMap());
		Page<OpXingtuCreative> page = new Page<OpXingtuCreative>(pageNo, pageSize);
		IPage<OpXingtuCreative> pageList = opXingtuCreativeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opXingtuCreative
	 * @return
	 */
	@AutoLog(value = "op_xingtu_creative-添加")
	@ApiOperation(value="op_xingtu_creative-添加", notes="op_xingtu_creative-添加")
	//@RequiresPermissions("advert:op_xingtu_creative:add")
	@PostMapping(value = "/save")
	public Result<String> save(@RequestBody OpXingtuCreativeDto opXingtuCreative) {
			opXingtuCreativeService.save(opXingtuCreative);
			return Result.OK("更新成功！");
	}

	/**
	 *   得到该计划的创意
	 *
	 * @param opXingtuCreative
	 * @return
	 */
	@AutoLog(value = "op_xingtu_creative-得到该计划的创意")
	@ApiOperation(value="op_xingtu_creative-得到该计划的创意", notes="op_xingtu_creative-得到该计划的创意")
	//@RequiresPermissions("advert:op_xingtu_creative:add")
	@GetMapping(value = "/getCreative")
	public Result<OpXingtuCreativeVo> getCreative(OpXingtuCreative opXingtuCreative) {
		return Result.OK(opXingtuCreativeService.getCreative(opXingtuCreative));
	}

	/**
	 *   获取行业列表
	 *
	 * @param opXingtuCreative
	 * @return
	 */
	@AutoLog(value = "op_xingtu_creative-获取行业列表")
	@ApiOperation(value="op_xingtu_creative-获取行业列表", notes="op_xingtu_creative-获取行业列表")
	//@RequiresPermissions("advert:op_xingtu_creative:add")
	@GetMapping(value = "/getIndustryList")
	public Result<List<CreativeIndustryVo>> getIndustryList(OpXingtuCreative opXingtuCreative) {
		return Result.OK(opXingtuCreativeService.getIndustryList());
	}
	
	/**
	 *  编辑
	 *
	 * @param opXingtuCreative
	 * @return
	 */
	@AutoLog(value = "op_xingtu_creative-编辑")
	@ApiOperation(value="op_xingtu_creative-编辑", notes="op_xingtu_creative-编辑")
	//@RequiresPermissions("advert:op_xingtu_creative:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpXingtuCreative opXingtuCreative) {
		opXingtuCreativeService.updateById(opXingtuCreative);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_xingtu_creative-通过id删除")
	@ApiOperation(value="op_xingtu_creative-通过id删除", notes="op_xingtu_creative-通过id删除")
	//@RequiresPermissions("advert:op_xingtu_creative:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opXingtuCreativeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_xingtu_creative-批量删除")
	@ApiOperation(value="op_xingtu_creative-批量删除", notes="op_xingtu_creative-批量删除")
	//@RequiresPermissions("advert:op_xingtu_creative:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opXingtuCreativeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_xingtu_creative-通过id查询")
	@ApiOperation(value="op_xingtu_creative-通过id查询", notes="op_xingtu_creative-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpXingtuCreative> queryById(@RequestParam(name="id",required=true) String id) {
		OpXingtuCreative opXingtuCreative = opXingtuCreativeService.getById(id);
		if(opXingtuCreative==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opXingtuCreative);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opXingtuCreative
    */
    //@RequiresPermissions("advert:op_xingtu_creative:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpXingtuCreative opXingtuCreative) {
        return super.exportXls(request, opXingtuCreative, OpXingtuCreative.class, "op_xingtu_creative");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_xingtu_creative:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpXingtuCreative.class);
    }

}
