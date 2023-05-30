package org.jeecg.modules.count.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.count.dto.LtvDto;
import org.jeecg.modules.count.entity.LtvAnalyze;
import org.jeecg.modules.count.service.ILtvAnalyzeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.LtvPaybackVo;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.count.vo.LtvVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: LTV分析
 * @Author: jeecg-boot
 * @Date:   2023-05-13
 * @Version: V1.0
 */
@Api(tags="LTV分析")
@RestController
@RequestMapping("/count/ltvAnalyze")
@Slf4j
public class LtvAnalyzeController extends JeecgController<LtvAnalyze, ILtvAnalyzeService> {
	@Autowired
	private ILtvAnalyzeService ltvAnalyzeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ltvAnalyze
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "LTV分析-分页列表查询")
	@ApiOperation(value="LTV分析-分页列表查询", notes="LTV分析-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<LtvAnalyze>> queryPageList(LtvAnalyze ltvAnalyze,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<LtvAnalyze> queryWrapper = QueryGenerator.initQueryWrapper(ltvAnalyze, req.getParameterMap());
		Page<LtvAnalyze> page = new Page<LtvAnalyze>(pageNo, pageSize);
		IPage<LtvAnalyze> pageList = ltvAnalyzeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 * @Param ltvDto
	 * @author chenglin
	 * @description Ltv数据查询
	 * @date 17.10 2023/5/15
	 **/
	 @ApiOperation(value="ct_daily-ltv查询", notes="ct_daily-ltv查询")
	 @GetMapping(value = "/queryLtvList")
	public Result<List<LtvVo>>queryLtvList(LtvDto ltvDto){
		return Result.OK(ltvAnalyzeService.qeuryLtvList(ltvDto));
	}


	/**
	 *   添加
	 *
	 * @param ltvAnalyze
	 * @return
	 */
	@AutoLog(value = "LTV分析-添加")
	@ApiOperation(value="LTV分析-添加", notes="LTV分析-添加")
	//@RequiresPermissions("count:ltv_analyze:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody LtvAnalyze ltvAnalyze) {
		ltvAnalyzeService.save(ltvAnalyze);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ltvAnalyze
	 * @return
	 */
	@AutoLog(value = "LTV分析-编辑")
	@ApiOperation(value="LTV分析-编辑", notes="LTV分析-编辑")
	//@RequiresPermissions("count:ltv_analyze:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody LtvAnalyze ltvAnalyze) {
		ltvAnalyzeService.updateById(ltvAnalyze);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "LTV分析-通过id删除")
	@ApiOperation(value="LTV分析-通过id删除", notes="LTV分析-通过id删除")
	//@RequiresPermissions("count:ltv_analyze:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ltvAnalyzeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "LTV分析-批量删除")
	@ApiOperation(value="LTV分析-批量删除", notes="LTV分析-批量删除")
	//@RequiresPermissions("count:ltv_analyze:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ltvAnalyzeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "LTV分析-通过id查询")
	@ApiOperation(value="LTV分析-通过id查询", notes="LTV分析-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<LtvAnalyze> queryById(@RequestParam(name="id",required=true) String id) {
		LtvAnalyze ltvAnalyze = ltvAnalyzeService.getById(id);
		if(ltvAnalyze==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ltvAnalyze);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ltvAnalyze
    */
    //@RequiresPermissions("count:ltv_analyze:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LtvAnalyze ltvAnalyze) {
        return super.exportXls(request, ltvAnalyze, LtvAnalyze.class, "LTV分析");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("count:ltv_analyze:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LtvAnalyze.class);
    }

}
