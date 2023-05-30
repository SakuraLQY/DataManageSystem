package org.jeecg.modules.advert.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.dto.LogDeviceCallbackDataDto;
import org.jeecg.modules.advert.entity.AtUnique;
import org.jeecg.modules.advert.service.IAtUniqueService;
import org.jeecg.modules.advert.vo.LogDeviceCallbackDataVo;
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
 * @Description: at_unique
 * @Author: jeecg-boot
 * @Date:   2023-04-24
 * @Version: V1.0
 */
@Api(tags="at_unique")
@RestController
@RequestMapping("/advert/atUnique")
@Slf4j
public class AtUniqueController extends JeecgController<AtUnique, IAtUniqueService> {
	@Autowired
	private IAtUniqueService atUniqueService;
	
	/**
	 * 分页列表查询
	 *
	 * @param atUnique
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "at_unique-分页列表查询")
	@ApiOperation(value="at_unique-分页列表查询", notes="at_unique-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<AtUnique>> queryPageList(AtUnique atUnique,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AtUnique> queryWrapper = QueryGenerator.initQueryWrapper(atUnique, req.getParameterMap());
		Page<AtUnique> page = new Page<AtUnique>(pageNo, pageSize);
		IPage<AtUnique> pageList = atUniqueService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param atUnique
	 * @return
	 */
	@AutoLog(value = "at_unique-添加")
	@ApiOperation(value="at_unique-添加", notes="at_unique-添加")
	//@RequiresPermissions("advert:at_unique:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody AtUnique atUnique) {
		atUniqueService.save(atUnique);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param atUnique
	 * @return
	 */
	@AutoLog(value = "at_unique-编辑")
	@ApiOperation(value="at_unique-编辑", notes="at_unique-编辑")
	//@RequiresPermissions("advert:at_unique:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody AtUnique atUnique) {
		atUniqueService.updateById(atUnique);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "at_unique-通过id删除")
	@ApiOperation(value="at_unique-通过id删除", notes="at_unique-通过id删除")
	//@RequiresPermissions("advert:at_unique:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		atUniqueService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "at_unique-批量删除")
	@ApiOperation(value="at_unique-批量删除", notes="at_unique-批量删除")
	//@RequiresPermissions("advert:at_unique:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.atUniqueService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "at_unique-通过id查询")
	@ApiOperation(value="at_unique-通过id查询", notes="at_unique-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<AtUnique> queryById(@RequestParam(name="id",required=true) String id) {
		AtUnique atUnique = atUniqueService.getById(id);
		if(atUnique==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(atUnique);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param atUnique
    */
    //@RequiresPermissions("advert:at_unique:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AtUnique atUnique) {
        return super.exportXls(request, atUnique, AtUnique.class, "at_unique");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:at_unique:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, AtUnique.class);
    }

	 @ApiOperation(value="设备回调数据")
	 @GetMapping(value = "/getDeviceCallbackData")
	 public Result<IPage> getDeviceCallbackData(
		 LogDeviceCallbackDataDto logDeviceCallbackDataDto,
		 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
		 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {

		 Page<LogDeviceCallbackDataVo> objectPage = new Page<LogDeviceCallbackDataVo>(pageNo, pageSize);
		 IPage deviceCallbackData = atUniqueService.getDeviceCallbackData(objectPage, logDeviceCallbackDataDto);
		 System.out.println(deviceCallbackData);
		 return Result.OK(deviceCallbackData);
	 }

	 /**
	  * 导出excel
	  *
	  * @param request
	  */
	 //@RequiresPermissions("advert:at_unique:exportXls")
	 @RequestMapping(value = "/deviceCallbackDataExportXls")
	 public ModelAndView deviceCallbackDataExportXls(HttpServletRequest request, LogDeviceCallbackDataDto logDeviceCallbackDataDto) {
		 return atUniqueService.deviceCallbackDataExportXls(request, logDeviceCallbackDataDto, "设备回调数据");
	 }
}
