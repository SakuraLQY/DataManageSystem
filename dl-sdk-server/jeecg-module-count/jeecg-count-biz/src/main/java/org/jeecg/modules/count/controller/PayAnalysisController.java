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
import org.jeecg.modules.count.dto.PayAnalysisDto;
import org.jeecg.modules.count.entity.PayAnalysis;
import org.jeecg.modules.count.service.IPayAnalysisService;
import org.jeecg.modules.count.vo.PayAnalysisVo;
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
 * @Description: pay_analysis
 * @Author: jeecg-boot
 * @Date:   2023-05-24
 * @Version: V1.0
 */
@Api(tags="pay_analysis")
@RestController
@RequestMapping("/count/payAnalysis")
@Slf4j
public class PayAnalysisController extends JeecgController<PayAnalysis, IPayAnalysisService> {
	@Autowired
	private IPayAnalysisService payAnalysisService;
	
	/**
	 * 分页列表查询
	 *
	 * @param payAnalysis
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "pay_analysis-分页列表查询")
	@ApiOperation(value="pay_analysis-分页列表查询", notes="pay_analysis-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PayAnalysis>> queryPageList(PayAnalysis payAnalysis,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PayAnalysis> queryWrapper = QueryGenerator.initQueryWrapper(payAnalysis, req.getParameterMap());
		Page<PayAnalysis> page = new Page<PayAnalysis>(pageNo, pageSize);
		IPage<PayAnalysis> pageList = payAnalysisService.page(page, queryWrapper);
		return Result.OK(pageList);
	}


	/**@param payAnalysisDto
	 * @author chenglin
	 * @description 自定义查询付费数据统计
	 * @date 15:26 2023/5/24
	 **/
	@ApiOperation(value="付费数据统计")
	@GetMapping("/queryList")
	@UserPermissionData(alias = {"ct_daily", "ct_order"})
	public Result<List<PayAnalysisVo>>queryList(PayAnalysisDto payAnalysisDto){
		return Result.OK(payAnalysisService.queryList(payAnalysisDto));
	}
	/**
	 *   添加
	 *
	 * @param payAnalysis
	 * @return
	 */
	@AutoLog(value = "pay_analysis-添加")
	@ApiOperation(value="pay_analysis-添加", notes="pay_analysis-添加")
	//@RequiresPermissions("count:pay_analysis:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PayAnalysis payAnalysis) {
		payAnalysisService.save(payAnalysis);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param payAnalysis
	 * @return
	 */
	@AutoLog(value = "pay_analysis-编辑")
	@ApiOperation(value="pay_analysis-编辑", notes="pay_analysis-编辑")
	//@RequiresPermissions("count:pay_analysis:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PayAnalysis payAnalysis) {
		payAnalysisService.updateById(payAnalysis);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "pay_analysis-通过id删除")
	@ApiOperation(value="pay_analysis-通过id删除", notes="pay_analysis-通过id删除")
	//@RequiresPermissions("count:pay_analysis:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		payAnalysisService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "pay_analysis-批量删除")
	@ApiOperation(value="pay_analysis-批量删除", notes="pay_analysis-批量删除")
	//@RequiresPermissions("count:pay_analysis:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.payAnalysisService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "pay_analysis-通过id查询")
	@ApiOperation(value="pay_analysis-通过id查询", notes="pay_analysis-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PayAnalysis> queryById(@RequestParam(name="id",required=true) String id) {
		PayAnalysis payAnalysis = payAnalysisService.getById(id);
		if(payAnalysis==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(payAnalysis);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param payAnalysis
    */
    //@RequiresPermissions("count:pay_analysis:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PayAnalysis payAnalysis) {
        return super.exportXls(request, payAnalysis, PayAnalysis.class, "pay_analysis");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:pay_analysis:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PayAnalysis.class);
    }

}
