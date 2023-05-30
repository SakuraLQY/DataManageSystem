package org.jeecg.modules.game.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.game.dto.OpCommodityDto;
import org.jeecg.modules.game.entity.OpCommodity;
import org.jeecg.modules.game.service.IOpCommodityService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.game.vo.OpCommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_commodity
 * @Author: jeecg-boot
 * @Date: 2022-12-12
 * @Version: V1.0
 */
@Api(tags = "op_commodity")
@RestController
@RequestMapping("/game/opCommodity")
@Slf4j
public class OpCommodityController extends JeecgController<OpCommodity, IOpCommodityService> {

  @Autowired
  private IOpCommodityService opCommodityService;

  /**
   * @param opCommodity
   * @return org.jeecg.common.api.vo.Result<com.baomidou.mybatisplus.core.metadata.IPage <
      * org.jeecg.modules.demo.game.entity.OpCommodity>>
   * @Author lili
   * @Description 分页列表查询
   * @Date 13:35 2022/12/16
   **/
  //@AutoLog(value = "op_commodity-分页列表查询")
  @ApiOperation(value = "op_commodity-分页列表查询", notes = "op_commodity-分页列表查询")
  @GetMapping(value = "/list")
  public Result<OpCommodityVo> queryPageList(OpCommodity opCommodity) {
    return Result.OK(opCommodityService.getOpCommodity(opCommodity));
  }

  /**
   * 添加、编辑
   *
   * @param opCommodity
   * @return
   */
  @AutoLog(value = "op_commodity-添加")
  @ApiOperation(value = "op_commodity-添加", notes = "op_commodity-添加")
  //@RequiresPermissions("game:op_commodity:add")
  @PostMapping(value = "/save")
  public Result<String> save(@RequestBody OpCommodityDto opCommodity) {
    opCommodityService.save(opCommodity);
    return Result.OK("更新成功！");
  }

  /**
   * 编辑
   *
   * @param opCommodity
   * @return
   */
  @AutoLog(value = "op_commodity-编辑")
  @ApiOperation(value = "op_commodity-编辑", notes = "op_commodity-编辑")
  //@RequiresPermissions("game:op_commodity:edit")
  @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
  public Result<String> edit(@RequestBody OpCommodity opCommodity) {
    opCommodityService.updateById(opCommodity);
    return Result.OK("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "op_commodity-通过id删除")
  @ApiOperation(value = "op_commodity-通过id删除", notes = "op_commodity-通过id删除")
  //@RequiresPermissions("game:op_commodity:delete")
  @DeleteMapping(value = "/delete")
  public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
    opCommodityService.removeById(id);
    return Result.OK("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "op_commodity-批量删除")
  @ApiOperation(value = "op_commodity-批量删除", notes = "op_commodity-批量删除")
  //@RequiresPermissions("game:op_commodity:deleteBatch")
  @DeleteMapping(value = "/deleteBatch")
  public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
    this.opCommodityService.removeByIds(Arrays.asList(ids.split(",")));
    return Result.OK("批量删除成功!");
  }

  /**
   * 通过id查询
   *
   * @param id
   * @return
   */
  //@AutoLog(value = "op_commodity-通过id查询")
  @ApiOperation(value = "op_commodity-通过id查询", notes = "op_commodity-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<OpCommodity> queryById(@RequestParam(name = "id", required = true) String id) {
    OpCommodity opCommodity = opCommodityService.getById(id);
    if (opCommodity == null) {
      return Result.error("未找到对应数据");
    }
    return Result.OK(opCommodity);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param opCommodity
   */
  //@RequiresPermissions("game:op_commodity:exportXls")
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, OpCommodity opCommodity) {
    return super.exportXls(request, opCommodity, OpCommodity.class, "op_commodity");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  //@RequiresPermissions("game:op_commodity:importExcel")
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
    return super.importExcel(request, response, OpCommodity.class);
  }

}
