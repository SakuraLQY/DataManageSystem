package org.jeecg.modules.users.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.users.entity.OpHolidayConfig;
import org.jeecg.modules.users.dto.HolidayTimeDTO;
import org.jeecg.modules.users.service.IOpHolidayConfigService;

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
 * @Description: op_holiday_config
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Api(tags = "op_holiday_config")
@RestController
@RequestMapping("/holiday/opHolidayConfig")
@Slf4j
public class OpHolidayConfigController extends
    JeecgController<OpHolidayConfig, IOpHolidayConfigService> {

    @Autowired
    private IOpHolidayConfigService opHolidayConfigService;

    /**
     * 分页列表查询
     *
     * @param opHolidayConfig
     * @param pageNo          页码
     * @param pageSize        页面大小
     * @param req
     * @return
     */
    //@AutoLog(value = "op_holiday_config-分页列表查询")
    @ApiOperation(value = "op_holiday_config-分页列表查询", notes = "op_holiday_config-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpHolidayConfig>> queryPageList(OpHolidayConfig opHolidayConfig,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<OpHolidayConfig> queryWrapper = QueryGenerator.initQueryWrapper(
            opHolidayConfig,
            req.getParameterMap());
        Page<OpHolidayConfig> page = new Page<OpHolidayConfig>(pageNo, pageSize);
        IPage<OpHolidayConfig> pageList = opHolidayConfigService.page(page, queryWrapper);
        return Result.OK(pageList);
    }


    /**
     * 手动添加
     *
     * @param opHolidayConfig
     * @return
     */
    @AutoLog(value = "op_holiday_config-手动添加")
    @ApiOperation(value = "op_holiday_config-手动添加", notes = "op_holiday_config-手动添加")
    //@RequiresPermissions("holiday:op_holiday_config:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpHolidayConfig opHolidayConfig) {
        opHolidayConfigService.save(opHolidayConfig);
        return Result.OK("添加成功！");
    }

    /**
     * 自动同步
     *
     * @param
     * @return
     */
    @AutoLog(value = "op_holiday_config-自动同步")
    @ApiOperation(value = "op_holiday_config-自动同步", notes = "op_holiday_config-自动同步")
    //@RequiresPermissions("holiday:op_holiday_config:add")
    @GetMapping(value = "/addSync")
    public Result<List<String>> addSync(HolidayTimeDTO holidayTimeDTO) {
        List<String> msgList = opHolidayConfigService.syncList(holidayTimeDTO);
        return Result.OK(msgList);
    }

    /**
     * 编辑
     *
     * @param opHolidayConfig
     * @return
     */
    @AutoLog(value = "op_holiday_config-编辑")
    @ApiOperation(value = "op_holiday_config-编辑", notes = "op_holiday_config-编辑")
    //@RequiresPermissions("holiday:op_holiday_config:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpHolidayConfig opHolidayConfig) {
        opHolidayConfigService.updateById(opHolidayConfig);
        return Result.OK("编辑成功!");
    }

    /**
     * 删除过期时间
     *
     * @param
     * @return
     */
    @AutoLog(value = "op_holiday_config-删除过期时间")
    @ApiOperation(value = "op_holiday_config-删除过期时间", notes = "op_holiday_config-删除过期时间")
    //@RequiresPermissions("holiday:op_holiday_config:delete")
    @DeleteMapping(value = "/deleteSome")
    public Result<String> deleteSome() {
        opHolidayConfigService.remove();
        return Result.OK("删除成功!");
    }

    /**
     * 删除单个
     *
     * @param
     * @return
     */
    @AutoLog(value = "op_holiday_config-删除单个")
    @ApiOperation(value = "op_holiday_config-删除单个", notes = "op_holiday_config-删除单个")
    //@RequiresPermissions("holiday:op_holiday_config:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opHolidayConfigService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_holiday_config-批量删除")
    @ApiOperation(value = "op_holiday_config-批量删除", notes = "op_holiday_config-批量删除")
    //@RequiresPermissions("holiday:op_holiday_config:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opHolidayConfigService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_holiday_config-通过id查询")
    @ApiOperation(value = "op_holiday_config-通过id查询", notes = "op_holiday_config-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpHolidayConfig> queryById(
        @RequestParam(name = "id", required = true) String id) {
        OpHolidayConfig opHolidayConfig = opHolidayConfigService.getById(id);
        if (opHolidayConfig == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opHolidayConfig);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opHolidayConfig
     */
    //@RequiresPermissions("holiday:op_holiday_config:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpHolidayConfig opHolidayConfig) {
        return super.exportXls(request, opHolidayConfig, OpHolidayConfig.class,
            "op_holiday_config");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("holiday:op_holiday_config:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpHolidayConfig.class);
    }
}
