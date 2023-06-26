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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.dto.StatDealDto;
import org.jeecg.modules.count.entity.StatDeal;
import org.jeecg.modules.count.service.IStatDealService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.StatDealVo;
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
 * @Description: stat_deal
 * @Author: jeecg-boot
 * @Date:   2023-05-30
 * @Version: V1.0
 */
@Api(tags="stat_deal")
@RestController
@RequestMapping("/count/statDeal")
@Slf4j
public class StatDealController extends JeecgController<StatDeal, IStatDealService> {
	@Autowired
	private IStatDealService statDealService;
	
	/**
	 * 分页列表查询
	 *
	 * @param statDeal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "stat_deal-分页列表查询")
	@ApiOperation(value="stat_deal-分页列表查询", notes="stat_deal-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<StatDeal>> queryPageList(StatDeal statDeal,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StatDeal> queryWrapper = QueryGenerator.initQueryWrapper(statDeal, req.getParameterMap());
		Page<StatDeal> page = new Page<StatDeal>(pageNo, pageSize);
		IPage<StatDeal> pageList = statDealService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**@param statDealDto
	 * @author chenglin
	 * @description 查询合作商数据统计-单位
	 * @date 10:34 2023/5/30
	 **/
	 @ApiOperation(value="stat_deal-分页列表查询", notes="stat_deal-分页列表查询")
	 @GetMapping(value = "/queryList")
	 @UserPermissionData(alias = "ct_daily")
	public Result<List<StatDealVo>>queryList(StatDealDto statDealDto,HttpServletRequest request){
		 String  username = JwtUtil.getUserNameByToken(request);
		return Result.OK(statDealService.queryList(statDealDto,username));
	}
	/**
	 *   添加
	 *
	 * @param statDeal
	 * @return
	 */
	@AutoLog(value = "stat_deal-添加")
	@ApiOperation(value="stat_deal-添加", notes="stat_deal-添加")
	//@RequiresPermissions("count:stat_deal:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody StatDeal statDeal) {
		statDealService.save(statDeal);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param statDeal
	 * @return
	 */
	@AutoLog(value = "stat_deal-编辑")
	@ApiOperation(value="stat_deal-编辑", notes="stat_deal-编辑")
	//@RequiresPermissions("count:stat_deal:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody StatDeal statDeal) {
		statDealService.updateById(statDeal);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "stat_deal-通过id删除")
	@ApiOperation(value="stat_deal-通过id删除", notes="stat_deal-通过id删除")
	//@RequiresPermissions("count:stat_deal:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		statDealService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "stat_deal-批量删除")
	@ApiOperation(value="stat_deal-批量删除", notes="stat_deal-批量删除")
	//@RequiresPermissions("count:stat_deal:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.statDealService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "stat_deal-通过id查询")
	@ApiOperation(value="stat_deal-通过id查询", notes="stat_deal-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<StatDeal> queryById(@RequestParam(name="id",required=true) String id) {
		StatDeal statDeal = statDealService.getById(id);
		if(statDeal==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(statDeal);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param statDeal
    */
    //@RequiresPermissions("count:stat_deal:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StatDeal statDeal) {
        return super.exportXls(request, statDeal, StatDeal.class, "stat_deal");
    }

	/**@param statDealDto
	 * @author chenglin
	 * @description 自定义导出功能
	 * @date 17:20 2023/5/30
	 **/
	@RequestMapping(value = "/exportExcel")
	public ModelAndView exportExcel(HttpServletRequest request,StatDealDto statDealDto){
		return statDealService.exportExcel(request,statDealDto,StatDealVo.class,"Stat数据表");
	}
    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:stat_deal:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, StatDeal.class);
    }

}
