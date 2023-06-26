package org.jeecg.modules.count.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.modules.count.dto.DayHourDto;
import org.jeecg.modules.count.vo.CtDayHourVo;
import org.jeecg.modules.count.vo.CtDayhour;
import org.jeecg.modules.count.service.ICtDayhourService;

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
 * @Description: ct_dayHour
 * @Author: jeecg-boot
 * @Date: 2023-04-24
 * @Version: V1.0
 */
@Api(tags = "ct_dayHour")
@RestController
@RequestMapping("/count/ctDayhour")
@Slf4j
public class CtDayhourController extends JeecgController<CtDayhour, ICtDayhourService> {

    @Autowired
    private ICtDayhourService ctDayhourService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "ct_dayHour-分页列表查询")
    @ApiOperation(value = "ct_dayHour-分页列表查询", notes = "ct_dayHour-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<CtDayhour>> queryPageList(DayHourDto dayHourDto,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {

        IPage<CtDayhour> pageList = ctDayhourService.getDayHourList(new Page<>(pageNo, pageSize),
            dayHourDto);
        return Result.OK(pageList);
    }

    //	 @ApiOperation(value = "op_sub_game-下拉框列表查询", notes = "op_sub_game-下拉框列表查询")
//	 @GetMapping(value = "/optionList")
//	public Result<Map<Integer,DayHourDto>>optionList(CtDayhour ctDayhour){
//		Map<Integer,DayHourDto>pageList = ctDayhourService.optionList(ctDayhour);
//		return Result.OK(pageList);
//	}
    /**@param dayHourDto
     * @author chenglin
     * @description 补充汇总
     * @date 10:42 2023/6/7
     **/
    @ApiOperation(value = "ct_dayHour查询", notes = "ct_dayHour列表查询")
    @GetMapping(value = "/queryList")
    @UserPermissionData(alias = "ct_hour")
    public Result<List<CtDayHourVo>> queryList(DayHourDto dayHourDto) {
        List<CtDayHourVo> list = ctDayhourService.queryList(dayHourDto);
        return Result.OK(list);
    }


    /**
     * 添加
     *
     * @param ctDayhour
     * @return
     */
    @AutoLog(value = "ct_dayHour-添加")
    @ApiOperation(value = "ct_dayHour-添加", notes = "ct_dayHour-添加")
    //@RequiresPermissions("count:ct_dayhour:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CtDayhour ctDayhour) {
        ctDayhourService.save(ctDayhour);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param ctDayhour
     * @return
     */
    @AutoLog(value = "ct_dayHour-编辑")
    @ApiOperation(value = "ct_dayHour-编辑", notes = "ct_dayHour-编辑")
    //@RequiresPermissions("count:ct_dayhour:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody CtDayhour ctDayhour) {
        ctDayhourService.updateById(ctDayhour);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "ct_dayHour-通过id删除")
    @ApiOperation(value = "ct_dayHour-通过id删除", notes = "ct_dayHour-通过id删除")
    //@RequiresPermissions("count:ct_dayhour:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        ctDayhourService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "ct_dayHour-批量删除")
    @ApiOperation(value = "ct_dayHour-批量删除", notes = "ct_dayHour-批量删除")
    //@RequiresPermissions("count:ct_dayhour:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ctDayhourService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "ct_dayHour-通过id查询")
    @ApiOperation(value = "ct_dayHour-通过id查询", notes = "ct_dayHour-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<CtDayhour> queryById(@RequestParam(name = "id", required = true) String id) {
        CtDayhour ctDayhour = ctDayhourService.getById(id);
        if (ctDayhour == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ctDayhour);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ctDayhour
     */
    //@RequiresPermissions("count:ct_dayhour:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtDayhour ctDayhour) {
        return super.exportXls(request, ctDayhour, CtDayhour.class, "ct_dayHour");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("count:ct_dayhour:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtDayhour.class);
    }

}
