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
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.dto.ChannelDetailDto;
import org.jeecg.modules.count.dto.StatHourDto;
import org.jeecg.modules.count.entity.StatHour;
import org.jeecg.modules.count.modal.ChannelDetailModal;
import org.jeecg.modules.count.modal.StatHourModal;
import org.jeecg.modules.count.service.IStatHourService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.StatHourVo;
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
 * @Description: cooperator_stat
 * @Author: jeecg-boot
 * @Date:   2023-05-29
 * @Version: V1.0
 */
@Api(tags="cooperator_stat")
@RestController
@RequestMapping("/count/statHour")
@Slf4j
public class StatHourController extends JeecgController<StatHour, IStatHourService> {
	@Autowired
	private IStatHourService statHourService;
	
	/**
	 * 分页列表查询
	 *
	 * @param statHour
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "cooperator_stat-分页列表查询")
	@ApiOperation(value="cooperator_stat-分页列表查询", notes="cooperator_stat-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<StatHour>> queryPageList(StatHour statHour,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StatHour> queryWrapper = QueryGenerator.initQueryWrapper(statHour, req.getParameterMap());
		Page<StatHour> page = new Page<StatHour>(pageNo, pageSize);
		IPage<StatHour> pageList = statHourService.page(page, queryWrapper);
		return Result.OK(pageList);
	}



	/**@param statHourDto
	 * @author chenglin
	 * @description 查询合作商数据-渠道
	 * @date 11:37 2023/5/29
	 **/
	 @ApiOperation(value="cooperator_stat-自定义列表查询", notes="cooperate-渠道分页列表查询")
	 @GetMapping(value = "/queryList")
	public Result<List<StatHourVo>>queryList(StatHourDto statHourDto,HttpServletRequest request){
		 String  username = JwtUtil.getUserNameByToken(request);
		return Result.OK(statHourService.queryList(statHourDto,username));
	}


	 /**@param statHourDto
	  * @author chenglin
	  * @description 补充导出Excel功能
	  * @date  16:12 2023/6/13
	  **/
	 @GetMapping(value = "/exportExcel")
	 public ModelAndView exportExcel(HttpServletRequest request, StatHourDto statHourDto) {
		 return statHourService.exportExcel(request,statHourDto, StatHourModal.class,"合作商数据-渠道信息表");
	 }

	/**
	 *   添加
	 *
	 * @param statHour
	 * @return
	 */
	@AutoLog(value = "cooperator_stat-添加")
	@ApiOperation(value="cooperator_stat-添加", notes="cooperator_stat-添加")
	//@RequiresPermissions("count:cooperator_stat:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody StatHour statHour) {
		statHourService.save(statHour);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param statHour
	 * @return
	 */
	@AutoLog(value = "cooperator_stat-编辑")
	@ApiOperation(value="cooperator_stat-编辑", notes="cooperator_stat-编辑")
	//@RequiresPermissions("count:cooperator_stat:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody StatHour statHour) {
		statHourService.updateById(statHour);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "cooperator_stat-通过id删除")
	@ApiOperation(value="cooperator_stat-通过id删除", notes="cooperator_stat-通过id删除")
	//@RequiresPermissions("count:cooperator_stat:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		statHourService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "cooperator_stat-批量删除")
	@ApiOperation(value="cooperator_stat-批量删除", notes="cooperator_stat-批量删除")
	//@RequiresPermissions("count:cooperator_stat:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.statHourService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "cooperator_stat-通过id查询")
	@ApiOperation(value="cooperator_stat-通过id查询", notes="cooperator_stat-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<StatHour> queryById(@RequestParam(name="id",required=true) String id) {
		StatHour statHour = statHourService.getById(id);
		if(statHour==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(statHour);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param statHour
    */
    //@RequiresPermissions("count:cooperator_stat:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StatHour statHour) {
        return super.exportXls(request, statHour, StatHour.class, "cooperator_stat");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:cooperator_stat:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, StatHour.class);
    }

}
