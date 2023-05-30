package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.entity.OpChannelType;
import org.jeecg.modules.advert.service.IOpChannelTypeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_channel_type
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
@Api(tags="op_channel_type")
@RestController
@RequestMapping("/advert/opChannelType")
@Slf4j
public class OpChannelTypeController extends JeecgController<OpChannelType, IOpChannelTypeService> {
	@Autowired
	private IOpChannelTypeService opChannelTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opChannelType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_channel_type-分页列表查询")
	@ApiOperation(value="op_channel_type-分页列表查询", notes="op_channel_type-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpChannelType>> queryPageList(OpChannelType opChannelType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpChannelType> queryWrapper = QueryGenerator.initQueryWrapper(opChannelType, req.getParameterMap());
		Page<OpChannelType> page = new Page<OpChannelType>(pageNo, pageSize);
		IPage<OpChannelType> pageList = opChannelTypeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opChannelType
	 * @return
	 */
	@AutoLog(value = "op_channel_type-添加")
	@ApiOperation(value="op_channel_type-添加", notes="op_channel_type-添加")
	//@RequiresPermissions("advert:op_channel_type:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpChannelType opChannelType) {
		opChannelTypeService.save(opChannelType);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param opChannelType
	 * @return
	 */
	@AutoLog(value = "op_channel_type-编辑")
	@ApiOperation(value="op_channel_type-编辑", notes="op_channel_type-编辑")
	//@RequiresPermissions("advert:op_channel_type:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpChannelType opChannelType) {
		opChannelTypeService.updateById(opChannelType);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_channel_type-通过id删除")
	@ApiOperation(value="op_channel_type-通过id删除", notes="op_channel_type-通过id删除")
	//@RequiresPermissions("advert:op_channel_type:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opChannelTypeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_channel_type-批量删除")
	@ApiOperation(value="op_channel_type-批量删除", notes="op_channel_type-批量删除")
	//@RequiresPermissions("advert:op_channel_type:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opChannelTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_channel_type-通过id查询")
	@ApiOperation(value="op_channel_type-通过id查询", notes="op_channel_type-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpChannelType> queryById(@RequestParam(name="id",required=true) String id) {
		OpChannelType opChannelType = opChannelTypeService.getById(id);
		if(opChannelType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opChannelType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opChannelType
    */
    //@RequiresPermissions("advert:op_channel_type:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpChannelType opChannelType) {
        return super.exportXls(request, opChannelType, OpChannelType.class, "op_channel_type");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_channel_type:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpChannelType.class);
    }

}
