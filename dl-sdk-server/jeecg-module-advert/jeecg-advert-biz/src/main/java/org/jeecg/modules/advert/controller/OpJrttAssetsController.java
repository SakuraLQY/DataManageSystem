package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.dto.OpJrttAssetsDto;
import org.jeecg.modules.advert.entity.OpJrttAssets;
import org.jeecg.modules.advert.service.IOpJrttAssetsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.advert.vo.OpJrttAssetsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_jrtt_assets
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Api(tags="op_jrtt_assets")
@RestController
@RequestMapping("/advert/opJrttAssets")
@Slf4j
public class OpJrttAssetsController extends JeecgController<OpJrttAssets, IOpJrttAssetsService> {
	@Autowired
	private IOpJrttAssetsService opJrttAssetsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param opJrttAssets
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_assets-分页列表查询")
	@ApiOperation(value="op_jrtt_assets-分页列表查询", notes="op_jrtt_assets-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpJrttAssetsVo>> queryPageList(OpJrttAssetsDto opJrttAssets,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		Page<OpJrttAssetsVo> page = new Page<OpJrttAssetsVo>(pageNo, pageSize);
		IPage<OpJrttAssetsVo> pageList = opJrttAssetsService.selectList(page,opJrttAssets);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param opJrttAssets
	 * @return
	 */
	@AutoLog(value = "op_jrtt_assets-添加")
	@ApiOperation(value="op_jrtt_assets-添加", notes="op_jrtt_assets-添加")
	//@RequiresPermissions("advert:op_jrtt_assets:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpJrttAssetsDto opJrttAssets) {
		opJrttAssetsService.add(opJrttAssets);
		return Result.OK("添加成功！");
	}

	/**
	 *   同步资产
	 *
	 * @param opJrttAssets
	 * @return
	 */
	@AutoLog(value = "op_jrtt_assets-同步资产")
	@ApiOperation(value="op_jrtt_assets-同步资产", notes="op_jrtt_assets-同步资产")
	@PostMapping(value = "/syncAssets")
	public Result<String> syncAssets(@RequestBody OpJrttAssetsDto opJrttAssets) {
		opJrttAssetsService.syncAssets(opJrttAssets);
		return Result.OK("同步成功！");
	}

	/**
	 *  编辑
	 *
	 * @param opJrttAssets
	 * @return
	 */
	@AutoLog(value = "op_jrtt_assets-编辑")
	@ApiOperation(value="op_jrtt_assets-编辑", notes="op_jrtt_assets-编辑")
	//@RequiresPermissions("advert:op_jrtt_assets:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpJrttAssets opJrttAssets) {
		opJrttAssetsService.updateById(opJrttAssets);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_jrtt_assets-通过id删除")
	@ApiOperation(value="op_jrtt_assets-通过id删除", notes="op_jrtt_assets-通过id删除")
	//@RequiresPermissions("advert:op_jrtt_assets:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opJrttAssetsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_jrtt_assets-批量删除")
	@ApiOperation(value="op_jrtt_assets-批量删除", notes="op_jrtt_assets-批量删除")
	//@RequiresPermissions("advert:op_jrtt_assets:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opJrttAssetsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_jrtt_assets-通过id查询")
	@ApiOperation(value="op_jrtt_assets-通过id查询", notes="op_jrtt_assets-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpJrttAssets> queryById(@RequestParam(name="id",required=true) String id) {
		OpJrttAssets opJrttAssets = opJrttAssetsService.getById(id);
		if(opJrttAssets==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opJrttAssets);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opJrttAssets
    */
    //@RequiresPermissions("advert:op_jrtt_assets:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpJrttAssets opJrttAssets) {
        return super.exportXls(request, opJrttAssets, OpJrttAssets.class, "op_jrtt_assets");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_jrtt_assets:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpJrttAssets.class);
    }

}
