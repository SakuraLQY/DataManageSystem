package org.jeecg.modules.count.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.count.dto.GameChargeDto;
import org.jeecg.modules.count.vo.CtGameChargeTotal;
import org.jeecg.modules.count.service.ICtGameChargeTotalService;

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
 * @Description: ct_gameChargeTotal
 * @Author: jeecg-boot
 * @Date: 2023-05-09
 * @Version: V1.0
 */
@Api(tags = "ct_gameChargeTotal")
@RestController
@RequestMapping("/count/ctGameChargeTotal")
@Slf4j
public class CtGameChargeTotalController extends
    JeecgController<CtGameChargeTotal, ICtGameChargeTotalService> {

    @Autowired
    private ICtGameChargeTotalService ctGameChargeTotalService;

    /**
     * 分页列表查询
     *
     * @param ctGameChargeTotal
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "ct_gameChargeTotal-分页列表查询")
    @ApiOperation(value = "ct_gameChargeTotal-分页列表查询", notes = "ct_gameChargeTotal-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<CtGameChargeTotal>> queryPageList(CtGameChargeTotal ctGameChargeTotal,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<CtGameChargeTotal> queryWrapper = QueryGenerator.initQueryWrapper(
            ctGameChargeTotal, req.getParameterMap());
        Page<CtGameChargeTotal> page = new Page<CtGameChargeTotal>(pageNo, pageSize);
        IPage<CtGameChargeTotal> pageList = ctGameChargeTotalService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param ctGameChargeTotal
     * @return
     */
    @AutoLog(value = "ct_gameChargeTotal-添加")
    @ApiOperation(value = "ct_gameChargeTotal-添加", notes = "ct_gameChargeTotal-添加")
    //@RequiresPermissions("count:ct_game_charge:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody CtGameChargeTotal ctGameChargeTotal) {
        ctGameChargeTotalService.save(ctGameChargeTotal);
        return Result.OK("添加成功！");
    }


    @GetMapping(value = "queryList")
    @ApiOperation(value = "gameCharge分页列表查询", notes = "自定义列表查询")
    @UserPermissionData(alias = "ct_daily")
    public Result<List<GameChargeDto>> queryList() {
        List<GameChargeDto> data = ctGameChargeTotalService.queryList();
        return Result.OK(data);
    }

    /**
     * 编辑
     *
     * @param ctGameChargeTotal
     * @return
     */
    @AutoLog(value = "ct_gameChargeTotal-编辑")
    @ApiOperation(value = "ct_gameChargeTotal-编辑", notes = "ct_gameChargeTotal-编辑")
    //@RequiresPermissions("count:ct_game_charge:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody CtGameChargeTotal ctGameChargeTotal) {
        ctGameChargeTotalService.updateById(ctGameChargeTotal);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "ct_gameChargeTotal-通过id删除")
    @ApiOperation(value = "ct_gameChargeTotal-通过id删除", notes = "ct_gameChargeTotal-通过id删除")
    //@RequiresPermissions("count:ct_game_charge:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        ctGameChargeTotalService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "ct_gameChargeTotal-批量删除")
    @ApiOperation(value = "ct_gameChargeTotal-批量删除", notes = "ct_gameChargeTotal-批量删除")
    //@RequiresPermissions("count:ct_game_charge:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ctGameChargeTotalService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "ct_gameChargeTotal-通过id查询")
    @ApiOperation(value = "ct_gameChargeTotal-通过id查询", notes = "ct_gameChargeTotal-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<CtGameChargeTotal> queryById(
        @RequestParam(name = "id", required = true) String id) {
        CtGameChargeTotal ctGameChargeTotal = ctGameChargeTotalService.getById(id);
        if (ctGameChargeTotal == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ctGameChargeTotal);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ctGameChargeTotal
     */
    //@RequiresPermissions("count:ct_game_charge:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CtGameChargeTotal ctGameChargeTotal) {
        return super.exportXls(request, ctGameChargeTotal, CtGameChargeTotal.class,
            "ct_gameChargeTotal");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("count:ct_game_charge:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CtGameChargeTotal.class);
    }

}
