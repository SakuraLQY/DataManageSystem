package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.service.IOpChannelService;
import org.jeecg.modules.advert.entity.OpChannel;

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
 * @Description: op_channel
 * @Author: jeecg-boot
 * @Date:   2023-01-05
 * @Version: V1.0
 */
@Api(tags="op_channel")
@RestController
@RequestMapping("/advert/opChannel")
@Slf4j
public class OpChannelController extends JeecgController<OpChannel, IOpChannelService> {
	@Autowired
	private IOpChannelService opChannelService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opChannel
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_channel-分页列表查询")
	@ApiOperation(value="op_channel-分页列表查询", notes="op_channel-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpChannel>> queryPageList(OpChannel opChannel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpChannel> queryWrapper = QueryGenerator.initQueryWrapper(opChannel, req.getParameterMap());
		Page<OpChannel> page = new Page<OpChannel>(pageNo, pageSize);
		IPage<OpChannel> pageList = opChannelService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   根据typeId查询列表
	 *
	 * @param opChannel
	 * @return
	 */
	@AutoLog(value = "op_channel-根据typeId查询列表")
	@ApiOperation(value="op_channel-根据typeId查询列表", notes="op_channel-根据typeId查询列表")
	//@RequiresPermissions("advert:op_channel:add")
	@GetMapping(value = "/selectByTypeId")
	public Result<List<OpChannel>> selectByTypeId( OpChannel opChannel) {
		return Result.OK(opChannelService.selectByTypeId(opChannel.getTypeId()));
	}

	/**
	 *   根据id查询对象
	 *
	 * @param opChannel
	 * @return
	 */
	@AutoLog(value = "op_channel-根据id查询对象")
	@ApiOperation(value="op_channel-根据id查询对象", notes="op_channel-根据id查询对象")
	//@RequiresPermissions("advert:op_channel:add")
	@GetMapping(value = "/selectById")
	public Result<OpChannel> selectById( OpChannel opChannel) {
		return Result.OK(opChannelService.getById(opChannel.getId()));
	}
	
	/**
	 *   添加
	 *
	 * @param opChannel
	 * @return
	 */
	@AutoLog(value = "op_channel-添加")
	@ApiOperation(value="op_channel-添加", notes="op_channel-添加")
	//@RequiresPermissions("advert:op_channel:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpChannel opChannel) {
		opChannelService.save(opChannel);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param opChannel
	 * @return
	 */
	@AutoLog(value = "op_channel-编辑")
	@ApiOperation(value="op_channel-编辑", notes="op_channel-编辑")
	//@RequiresPermissions("advert:op_channel:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpChannel opChannel) {
		opChannelService.updateById(opChannel);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_channel-通过id删除")
	@ApiOperation(value="op_channel-通过id删除", notes="op_channel-通过id删除")
	//@RequiresPermissions("advert:op_channel:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opChannelService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_channel-批量删除")
	@ApiOperation(value="op_channel-批量删除", notes="op_channel-批量删除")
	//@RequiresPermissions("advert:op_channel:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opChannelService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_channel-通过id查询")
	@ApiOperation(value="op_channel-通过id查询", notes="op_channel-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpChannel> queryById(@RequestParam(name="id",required=true) String id) {
		OpChannel opChannel = opChannelService.getById(id);
		if(opChannel==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opChannel);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opChannel
    */
    //@RequiresPermissions("advert:op_channel:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpChannel opChannel) {
        return super.exportXls(request, opChannel, OpChannel.class, "op_channel");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_channel:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpChannel.class);
    }

}
