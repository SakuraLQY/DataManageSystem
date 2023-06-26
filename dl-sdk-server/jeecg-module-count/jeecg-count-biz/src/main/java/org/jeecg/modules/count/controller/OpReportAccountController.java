package org.jeecg.modules.count.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.count.dto.ReportAccountDto;
import org.jeecg.modules.count.entity.OpReport;
import org.jeecg.modules.count.modal.ReportAccountModal;
import org.jeecg.modules.count.service.IOpReportAccountService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.count.vo.ReportAccountBillVo;
import org.jeecg.modules.count.vo.ReportAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 数据报表
 * @Author: jeecg-boot
 * @Date:   2023-05-22
 * @Version: V1.0
 */
@Api(tags="数据报表")
@RestController
@RequestMapping("/count/opReport")
@Slf4j
public class OpReportAccountController extends JeecgController<OpReport, IOpReportAccountService> {
	@Autowired
	private IOpReportAccountService OpReportAccountService;

	/**
	 * 分页列表查询
	 *
	 * @param opReport
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "数据报表-分页列表查询")
	@ApiOperation(value="数据报表-分页列表查询", notes="数据报表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpReport>> queryPageList(OpReport opReport,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpReport> queryWrapper = QueryGenerator.initQueryWrapper(opReport, req.getParameterMap());
		Page<OpReport> page = new Page<OpReport>(pageNo, pageSize);
		IPage<OpReport> pageList = OpReportAccountService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**@param reportAccountDto
	 * @author chenglin
	 * @description 查询数据报表中的账号报表的信息
	 * @date 16:23 2023/5/22
	 **/
	@ApiOperation(value="查询账号报表的信息")
	@GetMapping(value = "/queryList")
	public Result<List<ReportAccountVo>>queryList(ReportAccountDto reportAccountDto,HttpServletRequest request){
		String  username = JwtUtil.getUserNameByToken(request);
		return Result.OK(OpReportAccountService.queryAccountList(reportAccountDto,username));
	}

	/**@param account
	 * @author chenglin
	 * @description 根据账户accountId查询对应的账单信息
	 * @date 11:24 2023/5/23
	 **/
	@ApiOperation(value="数据报表查询", notes="数据报表查询")
	@GetMapping(value = "/queryBill")
	public Result<List<ReportAccountBillVo>>queryBill(@RequestParam(value = "account") Integer account){
		return Result.OK(OpReportAccountService.queryBillByAccountId(account));
	}
	/**
	 *   添加
	 *
	 * @param opReport
	 * @return
	 */
	@AutoLog(value = "数据报表-添加")
	@ApiOperation(value="数据报表-添加", notes="数据报表-添加")
	//@RequiresPermissions("count:op_report:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpReport opReport) {
		OpReportAccountService.save(opReport);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param opReport
	 * @return
	 */
	@AutoLog(value = "数据报表-编辑")
	@ApiOperation(value="数据报表-编辑", notes="数据报表-编辑")
	//@RequiresPermissions("count:op_report:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpReport opReport) {
		OpReportAccountService.updateById(opReport);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "数据报表-通过id删除")
	@ApiOperation(value="数据报表-通过id删除", notes="数据报表-通过id删除")
	//@RequiresPermissions("count:op_report:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		OpReportAccountService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "数据报表-批量删除")
	@ApiOperation(value="数据报表-批量删除", notes="数据报表-批量删除")
	//@RequiresPermissions("count:op_report:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.OpReportAccountService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "数据报表-通过id查询")
	@ApiOperation(value="数据报表-通过id查询", notes="数据报表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpReport> queryById(@RequestParam(name="id",required=true) String id) {
		OpReport opReport = OpReportAccountService.getById(id);
		if(opReport==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opReport);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opReport
    */
    //@RequiresPermissions("count:op_report:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpReport opReport) {
        return super.exportXls(request, opReport, OpReport.class, "数据报表");
    }

	/**@param reportAccountDto
	 * @author chenglin
	 * @description 补充导出Excel功能
	 * @date  16:26 2023/5/30
	 **/
	@RequestMapping(value = "/exportExcel")
	public ModelAndView exportExcel(HttpServletRequest request, ReportAccountDto reportAccountDto) {
		return OpReportAccountService.exportExcel(request,reportAccountDto, ReportAccountModal.class,"账号数据表");
	}
    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:op_report:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpReport.class);
    }

}
