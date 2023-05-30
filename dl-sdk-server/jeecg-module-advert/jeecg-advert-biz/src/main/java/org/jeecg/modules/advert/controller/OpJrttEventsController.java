package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.dto.OpJrttAssetsDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttEvents;
import org.jeecg.modules.advert.service.IOpJrttEventsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.advert.vo.EventExternalActionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_jrtt_events
 * @Author: jeecg-boot
 * @Date:   2023-02-16
 * @Version: V1.0
 */
@Api(tags="op_jrtt_events")
@RestController
@RequestMapping("/advert/opJrttEvents")
@Slf4j
public class OpJrttEventsController extends JeecgController<OpJrttEvents, IOpJrttEventsService> {
	@Autowired
	private IOpJrttEventsService opJrttEventsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opJrttEvents
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_events-分页列表查询")
	@ApiOperation(value="op_jrtt_events-分页列表查询", notes="op_jrtt_events-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpJrttEvents>> queryPageList(OpJrttEvents opJrttEvents,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpJrttEvents> queryWrapper = QueryGenerator.initQueryWrapper(opJrttEvents, req.getParameterMap());
		Page<OpJrttEvents> page = new Page<OpJrttEvents>(pageNo, pageSize);
		IPage<OpJrttEvents> pageList = opJrttEventsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opJrttEvents
	 * @return
	 */
	@AutoLog(value = "op_jrtt_events-添加")
	@ApiOperation(value="op_jrtt_events-添加", notes="op_jrtt_events-添加")
	//@RequiresPermissions("advert:op_jrtt_events:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpJrttEvents opJrttEvents) {
		opJrttEventsService.add(opJrttEvents);
		return Result.OK("添加成功！");
	}

	/**
	 *   查询资产下所有的事件
	 *
	 * @param opDeal
	 * @return
	 */
	@AutoLog(value = "op_jrtt_events-查询资产下所有的事件")
	@ApiOperation(value="op_jrtt_events-查询资产下所有的事件", notes="op_jrtt_events-查询资产下所有的事件")
	//@RequiresPermissions("advert:op_jrtt_events:add")
	@GetMapping(value = "/getExternalAction")
	public Result<List<EventExternalActionVo>> getExternalAction( OpDeal opDeal) {
		return Result.OK(opJrttEventsService.getExternalAction(opDeal));
	}

	/**
	 *   同步事件
	 *
	 * @param opJrttAssetsDto
	 * @return
	 */
	@AutoLog(value = "op_jrtt_events-同步事件")
	@ApiOperation(value="op_jrtt_events-同步事件", notes="op_jrtt_events-同步事件")
	//@RequiresPermissions("advert:op_jrtt_events:add")
	@PostMapping(value = "/syncEvents")
	public Result<String> syncEvents(@RequestBody OpJrttAssetsDto opJrttAssetsDto) {
		opJrttEventsService.syncEvents(opJrttAssetsDto);
		return Result.OK("同步成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param opJrttEvents
	 * @return
	 */
	@AutoLog(value = "op_jrtt_events-编辑")
	@ApiOperation(value="op_jrtt_events-编辑", notes="op_jrtt_events-编辑")
	//@RequiresPermissions("advert:op_jrtt_events:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpJrttEvents opJrttEvents) {
		opJrttEventsService.updateById(opJrttEvents);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_jrtt_events-通过id删除")
	@ApiOperation(value="op_jrtt_events-通过id删除", notes="op_jrtt_events-通过id删除")
	//@RequiresPermissions("advert:op_jrtt_events:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opJrttEventsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_jrtt_events-批量删除")
	@ApiOperation(value="op_jrtt_events-批量删除", notes="op_jrtt_events-批量删除")
	//@RequiresPermissions("advert:op_jrtt_events:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opJrttEventsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_events-通过id查询")
	@ApiOperation(value="op_jrtt_events-通过id查询", notes="op_jrtt_events-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpJrttEvents> queryById(@RequestParam(name="id",required=true) String id) {
		OpJrttEvents opJrttEvents = opJrttEventsService.getById(id);
		if(opJrttEvents==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opJrttEvents);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opJrttEvents
    */
    //@RequiresPermissions("advert:op_jrtt_events:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpJrttEvents opJrttEvents) {
        return super.exportXls(request, opJrttEvents, OpJrttEvents.class, "op_jrtt_events");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_jrtt_events:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpJrttEvents.class);
    }

}
