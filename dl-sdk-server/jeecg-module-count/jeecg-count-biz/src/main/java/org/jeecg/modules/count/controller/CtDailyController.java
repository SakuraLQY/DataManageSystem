package org.jeecg.modules.count.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.dto.OverViewDto;
import org.jeecg.modules.count.dto.RetentionDto;
import org.jeecg.modules.count.dto.RoiDto;
import org.jeecg.modules.count.dto.XingtuDayReportDto;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.modal.DayReportResultModal;
import org.jeecg.modules.count.modal.RoiModal;
import org.jeecg.modules.count.modal.XingtuDayReportModal;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.vo.FinanceUserVo;
import org.jeecg.modules.count.vo.OverViewVo;
import org.jeecg.modules.count.vo.RecoveryVo;
import org.jeecg.modules.count.vo.RetentionVo;
import org.jeecg.modules.count.vo.RoiVo;
import org.jeecg.modules.count.vo.StatCustomVo;
import org.jeecg.modules.count.vo.XingtuDayReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: ct_daily
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Api(tags="ct_daily")
@RestController
@RequestMapping("/count/ctDaily")
@Slf4j
public class CtDailyController extends JeecgController<CtDaily, ICtDailyService> {
	@Autowired
	private ICtDailyService ctDailyService;

	/**
	 * 分页列表查询
	 *
	 * @param ctDaily
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "ct_daily-分页列表查询")
	@ApiOperation(value="ct_daily-分页列表查询", notes="ct_daily-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CtDaily>> queryPageList(CtDaily ctDaily,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CtDaily> queryWrapper = QueryGenerator.initQueryWrapper(ctDaily, req.getParameterMap());
		Page<CtDaily> page = new Page<CtDaily>(pageNo, pageSize);
		IPage<CtDaily> pageList = ctDailyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	@ApiOperation(value="ct_daily-发行合作商数据统计-留存数据统计", notes="ct_daily-发行合作商数据统计-留存数据统计")
	@GetMapping(value = "/queryRetentionList")
	@UserPermissionData(alias = "a")
	public Result<List<RetentionVo>> queryRetentionList(RetentionDto retentionDto) {
		return Result.OK(ctDailyService.queryRetentionList(retentionDto));
	}

	@ApiOperation(value="ct_daily-合作商数据统计 合作商数据【数据】", notes="ct_daily-合作商数据统计 合作商数据【数据】")
	@GetMapping(value = "/queryStatCustomList")
	@UserPermissionData(alias = "a")
	public Result<List<StatCustomVo>> queryStatCustomList(RetentionDto retentionDto) {
		return Result.OK(ctDailyService.queryStatCustomList(retentionDto));
	}

	/**
	 * @param request:
	 * @param retentionDto:
	 * @return ModelAndView
	 * @author Fkh
	 * @description 合作商数据【数据】导出功能
	 * @date 2023/6/21 14:52
	 */
	@RequestMapping(value = "/statCustomListexportXls")
	public ModelAndView statCustomListexportXls(HttpServletRequest request, RetentionDto retentionDto) {
		return ctDailyService.statCustomListexportXls(request, retentionDto, "合作商数据【数据】");
	}

	@ApiOperation(value="ct_daily-数据日报", notes="ct_daily-数据日报")
	@GetMapping(value = "/queryDayReportList")
	@UserPermissionData(alias = {"`op_cost`", "`ct_daily`"})
	public Result<DayReportResultModal> queryDayReportList(OverViewDto overViewDto) {
		return Result.OK(ctDailyService.queryDayReportList(overViewDto));
	}

	@ApiOperation(value="ct_daily-星图日报", notes="ct_daily-星图日报")
	@GetMapping(value = "/queryXingtuDayReportList")
	@UserPermissionData(alias = "a")
	public Result<List<XingtuDayReportVo>> queryXingtuDayReportList(XingtuDayReportDto xingtuDayReportDto) {
		return Result.OK(ctDailyService.queryXingtuDayReportList(xingtuDayReportDto));
	}

	@ApiOperation(value="ct_daily-星图日报导出", notes="ct_daily-星图日报导出")
	@RequestMapping(value = "/xingtuDayReportExportXls")
	@UserPermissionData(alias = "a")
	public ModelAndView xingtuDayReportExportXls(XingtuDayReportDto xingtuDayReportDto) {
		return ctDailyService.xingtuDayReportExportXls(xingtuDayReportDto, XingtuDayReportModal.class, "星图日报");
	}

	/**
	 * @param roiDto
	 * @return org.jeecg.common.api.vo.Result<java.util.List < org.jeecg.modules.count.vo.RoiVo>>
	 * @Author lili
	 * @Description ROI查询
	 * @Date 11:36 2023/5/11
	 **/
	 @ApiOperation(value="ct_daily-ROI查询", notes="ct_daily-ROI查询")
	 @GetMapping(value = "/queryROIList")
	 @UserPermissionData(alias = "a")
	 public Result<List<RoiVo>> queryROIList(RoiDto roiDto) {
		 return Result.OK(ctDailyService.queryROIList(roiDto));
	 }

	 /**
	  * @param overViewDto
	  * @return org.jeecg.common.api.vo.Result<java.util.List < org.jeecg.modules.count.vo.RecoveryVo>>
	  * @Author lili
	  * @Description 财务数据-回收数据
	  * @Date 16:34 2023/5/16
	  **/
	@ApiOperation(value="ct_daily-财务数据-回收数据", notes="ct_daily-财务数据-回收数据")
	@GetMapping(value = "/queryRecoveryList")
	@UserPermissionData(alias = "a")
	public Result<List<RecoveryVo>> queryRecoveryList(OverViewDto overViewDto) {
		return Result.OK(ctDailyService.queryRecoveryList(overViewDto));
	}

	 /**
	  * @param overViewDto
	  * @return org.jeecg.common.api.vo.Result<java.util.List < org.jeecg.modules.count.vo.OverViewListVo>>
	  * @Author lili
	  * @Description 游戏数据概况查询
	  * @Date 15:52 2023/5/15
	  **/
	 @ApiOperation(value="游戏数据概况查询", notes="游戏数据概况查询")
	 @GetMapping(value = "/queryOverViewList")
	 @UserPermissionData(alias = "a")
	 public Result<List<OverViewVo>> queryOverViewList(OverViewDto overViewDto) {
		 return Result.OK(ctDailyService.queryOverViewList(overViewDto));
	 }

	 /**
	  * @param overViewDto
	  * @return org.jeecg.common.api.vo.Result<java.util.List < org.jeecg.modules.count.vo.FinanceUserVo>>
	  * @Author lili
	  * @Description 财务数据-用户数据查询
	  * @Date 14:54 2023/5/17
	  **/
	@ApiOperation(value="财务数据-用户数据查询", notes="财务数据-用户数据查询")
	@GetMapping(value = "/queryFinanceUserList")
	@UserPermissionData(alias = "a")
	public Result<List<FinanceUserVo>> queryFinanceUserList(OverViewDto overViewDto) {
		return Result.OK(ctDailyService.queryFinanceUserList(overViewDto));
	}

	 /**
	  * 导出excel
	  *
	  * @param roiDto
	  */
	 //@RequiresPermissions("count:ct_daily:exportXls")
	 @RequestMapping(value = "/roiExportXls")
	 @UserPermissionData(alias = "a")
	 public ModelAndView roiExportXls(RoiDto roiDto) {
		 return ctDailyService.exportXls(roiDto, RoiModal.class, "ROI数据表");
	 }

	/**
	 * 导出excel
	 *
	 * @param overViewDto
	 */
	//@RequiresPermissions("count:ct_daily:exportXls")
	@RequestMapping(value = "/recoveryExportXls")
	public ModelAndView recoveryExportXls(OverViewDto overViewDto) {
		return ctDailyService.recoveryExportXls(overViewDto, RecoveryVo.class, "回收数据表" + DateUtils.formatDate());
	}

	/**
	 *   添加
	 *
	 * @param ctDaily
	 * @return
	 */
	@AutoLog(value = "ct_daily-添加")
	@ApiOperation(value="ct_daily-添加", notes="ct_daily-添加")
	//@RequiresPermissions("count:ct_daily:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CtDaily ctDaily) {
		ctDailyService.save(ctDaily);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param ctDaily
	 * @return
	 */
	@AutoLog(value = "ct_daily-编辑")
	@ApiOperation(value="ct_daily-编辑", notes="ct_daily-编辑")
	//@RequiresPermissions("count:ct_daily:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CtDaily ctDaily) {
		ctDailyService.updateById(ctDaily);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ct_daily-通过id删除")
	@ApiOperation(value="ct_daily-通过id删除", notes="ct_daily-通过id删除")
	//@RequiresPermissions("count:ct_daily:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ctDailyService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ct_daily-批量删除")
	@ApiOperation(value="ct_daily-批量删除", notes="ct_daily-批量删除")
	//@RequiresPermissions("count:ct_daily:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ctDailyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "ct_daily-通过id查询")
	@ApiOperation(value="ct_daily-通过id查询", notes="ct_daily-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CtDaily> queryById(@RequestParam(name="id",required=true) String id) {
		CtDaily ctDaily = ctDailyService.getById(id);
		if(ctDaily==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ctDaily);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ctDaily
    */
    //@RequiresPermissions("count:ct_daily:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDaily ctDaily) {
        return super.exportXls(request, ctDaily, CtDaily.class, "ct_daily");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ct_daily:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDaily.class);
    }

}
