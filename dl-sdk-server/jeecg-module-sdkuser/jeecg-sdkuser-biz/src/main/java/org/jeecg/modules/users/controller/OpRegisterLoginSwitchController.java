package org.jeecg.modules.users.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.users.entity.OpHolidayConfig;
import org.jeecg.modules.users.entity.OpRegisterLoginSwitch;
import org.jeecg.modules.users.service.IOpRegisterLoginSwitchService;

import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.users.vo.OpRegisterLoginSwitchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_register_login_switch
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Api(tags = "op_register_login_switch")
@RestController
@RequestMapping("/hello/opRegisterLoginSwitch")
@Slf4j
public class OpRegisterLoginSwitchController extends
    JeecgController<OpRegisterLoginSwitch, IOpRegisterLoginSwitchService> {

  @Autowired
  private IOpRegisterLoginSwitchService opRegisterLoginSwitchService;

  /**
   * 分页列表查询
   *
   * @param opRegisterLoginSwitch
   * @param pageNo                页码
   * @param pageSize              页面大小
   * @param req
   * @return
   */
  //@AutoLog(value = "op_register_login_switch-分页列表查询")
  @ApiOperation(value = "op_register_login_switch-分页列表查询", notes = "op_register_login_switch-分页列表查询")
  @GetMapping(value = "/list")
  public Result<IPage<OpRegisterLoginSwitchVo>> queryPageList(
      OpRegisterLoginSwitch opRegisterLoginSwitch,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    Page<OpRegisterLoginSwitch> page = new Page<OpRegisterLoginSwitch>(pageNo, pageSize);
    return Result.OK(opRegisterLoginSwitchService.selectList(page, opRegisterLoginSwitch));
  }

  /**
   * 添加
   *
   * @param opRegisterLoginSwitch
   * @return
   */
  @AutoLog(value = "op_register_login_switch-添加")
  @ApiOperation(value = "op_register_login_switch-添加", notes = "op_register_login_switch-添加")
  //@RequiresPermissions("hello:op_register_login_switch:add")
  @PostMapping(value = "/add")
  public Result<String> add(@RequestBody OpRegisterLoginSwitch opRegisterLoginSwitch) {
    opRegisterLoginSwitchService.add(opRegisterLoginSwitch);
    return Result.OK("添加成功！");
  }

  /**
   * 编辑
   *
   * @param
   * @return
   */
  @AutoLog(value = "op_register_login_switch-编辑")
  @ApiOperation(value = "op_register_login_switch-编辑", notes = "op_register_login_switch-编辑")
  //@RequiresPermissions("hello:op_register_login_switch:edit")
  @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
  public Result<String> edit(@RequestBody OpRegisterLoginSwitch opRegisterLoginSwitch) {
    opRegisterLoginSwitchService.update(opRegisterLoginSwitch);
    return Result.OK("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "op_register_login_switch-通过id删除")
  @ApiOperation(value = "op_register_login_switch-通过id删除", notes = "op_register_login_switch-通过id删除")
  //@RequiresPermissions("hello:op_register_login_switch:delete")
  @DeleteMapping(value = "/delete")
  public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
    opRegisterLoginSwitchService.removeById(id);
    return Result.OK("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "op_register_login_switch-批量删除")
  @ApiOperation(value = "op_register_login_switch-批量删除", notes = "op_register_login_switch-批量删除")
  //@RequiresPermissions("hello:op_register_login_switch:deleteBatch")
  @DeleteMapping(value = "/deleteBatch")
  public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
    opRegisterLoginSwitchService.removeByIds(Arrays.asList(ids.split(",")));
    return Result.OK("批量删除成功!");
  }

  /**
   * 通过id查询
   *
   * @param id
   * @return
   */
  //@AutoLog(value = "op_register_login_switch-通过id查询")
  @ApiOperation(value = "op_register_login_switch-通过id查询", notes = "op_register_login_switch-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<OpRegisterLoginSwitch> queryById(
      @RequestParam(name = "id", required = true) String id) {
    OpRegisterLoginSwitch opRegisterLoginSwitch = opRegisterLoginSwitchService.getById(id);
    if (opRegisterLoginSwitch == null) {
      return Result.error("未找到对应数据");
    }
    return Result.OK(opRegisterLoginSwitch);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param opRegisterLoginSwitch
   */
  //@RequiresPermissions("hello:op_register_login_switch:exportXls")
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request,
      OpRegisterLoginSwitch opRegisterLoginSwitch) {
    return super.exportXls(request, opRegisterLoginSwitch, OpRegisterLoginSwitch.class,
        "op_register_login_switch");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  //@RequiresPermissions("hello:op_register_login_switch:importExcel")
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
    return super.importExcel(request, response, OpRegisterLoginSwitch.class);
  }

}
