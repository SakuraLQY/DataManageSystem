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
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.dto.ComputePayDto;
import org.jeecg.modules.count.entity.ComputeMoney;
import org.jeecg.modules.count.service.IComputeMoneyService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.ComputePayVo;
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
 * @Description: data_tool
 * @Author: jeecg-boot
 * @Date:   2023-05-23
 * @Version: V1.0
 */
@Api(tags="data_tool")
@RestController
@RequestMapping("/count/computeMoney")
@Slf4j
public class ComputeMoneyController extends JeecgController<ComputeMoney, IComputeMoneyService> {
	@Autowired
	private IComputeMoneyService computeMoneyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param computeMoney
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "data_tool-分页列表查询")
	@ApiOperation(value="data_tool-分页列表查询", notes="data_tool-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ComputeMoney>> queryPageList(ComputeMoney computeMoney,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ComputeMoney> queryWrapper = QueryGenerator.initQueryWrapper(computeMoney, req.getParameterMap());
		Page<ComputeMoney> page = new Page<ComputeMoney>(pageNo, pageSize);
		IPage<ComputeMoney> pageList = computeMoneyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**@param computePayDto
	 * @author chenglin
	 * @description 返回给前端计算付费对应的数据信息
	 * @date 14:57 2023/5/23
	 **/
	 @ApiOperation(value="data_tool-计算付费列表查询", notes="data_tool-分页列表查询")
	 @GetMapping(value = "/queryList")
	 @UserPermissionData(alias = {"a","ct_user"})
	public Result<List<ComputePayVo>>queryList(ComputePayDto computePayDto){
		return Result.OK(computeMoneyService.queryList(computePayDto));
	}
	/**
	 *   添加
	 *
	 * @param computeMoney
	 * @return
	 */
	@AutoLog(value = "data_tool-添加")
	@ApiOperation(value="data_tool-添加", notes="data_tool-添加")
	//@RequiresPermissions("count:data_tool:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ComputeMoney computeMoney) {
		computeMoneyService.save(computeMoney);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param computeMoney
	 * @return
	 */
	@AutoLog(value = "data_tool-编辑")
	@ApiOperation(value="data_tool-编辑", notes="data_tool-编辑")
	//@RequiresPermissions("count:data_tool:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ComputeMoney computeMoney) {
		computeMoneyService.updateById(computeMoney);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "data_tool-通过id删除")
	@ApiOperation(value="data_tool-通过id删除", notes="data_tool-通过id删除")
	//@RequiresPermissions("count:data_tool:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		computeMoneyService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "data_tool-批量删除")
	@ApiOperation(value="data_tool-批量删除", notes="data_tool-批量删除")
	//@RequiresPermissions("count:data_tool:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.computeMoneyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "data_tool-通过id查询")
	@ApiOperation(value="data_tool-通过id查询", notes="data_tool-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ComputeMoney> queryById(@RequestParam(name="id",required=true) String id) {
		ComputeMoney computeMoney = computeMoneyService.getById(id);
		if(computeMoney==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(computeMoney);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param computeMoney
    */
    //@RequiresPermissions("count:data_tool:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ComputeMoney computeMoney) {
        return super.exportXls(request, computeMoney, ComputeMoney.class, "data_tool");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:data_tool:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ComputeMoney.class);
    }

}
