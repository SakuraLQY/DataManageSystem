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
import org.jeecg.modules.count.dto.PublishConversionDto;
import org.jeecg.modules.count.entity.PublishConversion;
import org.jeecg.modules.count.service.IPublishConversionService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.PublishConversionVo;
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
 * @Description: publish_conversion
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Api(tags="publish_conversion")
@RestController
@RequestMapping("/count/publishConversion")
@Slf4j
public class PublishConversionController extends JeecgController<PublishConversion, IPublishConversionService> {
	@Autowired
	private IPublishConversionService publishConversionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param publishConversion
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "publish_conversion-分页列表查询")
	@ApiOperation(value="publish_conversion-分页列表查询", notes="publish_conversion-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PublishConversion>> queryPageList(PublishConversion publishConversion,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PublishConversion> queryWrapper = QueryGenerator.initQueryWrapper(publishConversion, req.getParameterMap());
		Page<PublishConversion> page = new Page<PublishConversion>(pageNo, pageSize);
		IPage<PublishConversion> pageList = publishConversionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**@param publishConversionDto
	 * @author chenglin
	 * @description 查询转换数据统计信息
	 * @date 10:58 2023/5/24
	 **/
	@ApiOperation(value="publish_conversion-分页列表查询", notes="conversion转换数据查询")
	@GetMapping("/queryList")
	@UserPermissionData(alias = "ct_daily")
	public Result<List<PublishConversionVo>>queryList(PublishConversionDto publishConversionDto){
		return Result.OK(publishConversionService.queryConversionList(publishConversionDto));
	}

	/**
	 *   添加
	 *
	 * @param publishConversion
	 * @return
	 */
	@AutoLog(value = "publish_conversion-添加")
	@ApiOperation(value="publish_conversion-添加", notes="publish_conversion-添加")
	//@RequiresPermissions("count:publish_conversion:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PublishConversion publishConversion) {
		publishConversionService.save(publishConversion);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param publishConversion
	 * @return
	 */
	@AutoLog(value = "publish_conversion-编辑")
	@ApiOperation(value="publish_conversion-编辑", notes="publish_conversion-编辑")
	//@RequiresPermissions("count:publish_conversion:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PublishConversion publishConversion) {
		publishConversionService.updateById(publishConversion);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "publish_conversion-通过id删除")
	@ApiOperation(value="publish_conversion-通过id删除", notes="publish_conversion-通过id删除")
	//@RequiresPermissions("count:publish_conversion:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		publishConversionService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "publish_conversion-批量删除")
	@ApiOperation(value="publish_conversion-批量删除", notes="publish_conversion-批量删除")
	//@RequiresPermissions("count:publish_conversion:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.publishConversionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "publish_conversion-通过id查询")
	@ApiOperation(value="publish_conversion-通过id查询", notes="publish_conversion-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PublishConversion> queryById(@RequestParam(name="id",required=true) String id) {
		PublishConversion publishConversion = publishConversionService.getById(id);
		if(publishConversion==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(publishConversion);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param publishConversion
    */
    //@RequiresPermissions("count:publish_conversion:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PublishConversion publishConversion) {
        return super.exportXls(request, publishConversion, PublishConversion.class, "publish_conversion");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:publish_conversion:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PublishConversion.class);
    }

}
