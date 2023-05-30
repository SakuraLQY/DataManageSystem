package org.jeecg.modules.game.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.service.IOpSubGameService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.game.vo.GameObjVo;
import org.jeecg.modules.game.vo.OpSubGameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_son_game
 * @Author: jeecg-boot
 * @Date: 2022-12-09
 * @Version: V1.0
 */
@Api(tags = "op_sub_game")
@RestController
@RequestMapping("/game/opSubGame")
@Slf4j
public class OpSubGameController extends JeecgController<OpSubGame, IOpSubGameService> {

  @Autowired
  private IOpSubGameService opSubGameService;

  /**
   * @param opSubGame
   * @return org.jeecg.common.api.vo.Result<org.jeecg.modules.demo.game.common.ResultSubGameListPage
      * < org.jeecg.modules.demo.game.vo.OpSubGameVo>>
   * @Author lili
   * @Description 下拉框列表查询
   * @Date 13:36 2022/12/16
   **/
  //@AutoLog(value = "op_sub_game-分页列表查询")
  @ApiOperation(value = "op_sub_game-下拉框列表查询", notes = "op_sub_game-下拉框列表查询")
  @GetMapping(value = "/optionList")
  public Result<Map<Integer, GameObjVo>> optionList(OpSubGame opSubGame) {
    Map<Integer, GameObjVo> pageList = opSubGameService.getOptionList(opSubGame);
    return Result.OK(pageList);
  }

  @ApiOperation(value = "op_sub_game-多选下拉框列表查询", notes = "op_sub_game-多选下拉框列表查询")
  @GetMapping(value = "/gameAndSubGameAndPkgList")
  public Result<Map<Integer, Map<Integer, GameObjVo>>> gameAndSubGameAndPkgList() {
    return Result.OK(opSubGameService.getGameAndSubGameAndPkgList());
  }

  /**
   * @return org.jeecg.common.api.vo.Result<org.jeecg.modules.demo.game.common.ResultSubGameListPage
      * < org.jeecg.modules.demo.game.vo.OpSubGameVo>>
   * @Author lili
   * @Description 不分页查询
   * @Date 13:36 2022/12/29
   **/
  //@AutoLog(value = "op_sub_game-不分页查询")
  @ApiOperation(value = "op_sub_game-不分页查询", notes = "op_sub_game-不分页查询")
  @GetMapping(value = "/queryAll")
  public Result<List<OpSubGame>> queryAll() {
    return Result.OK(opSubGameService.queryAll());
  }

  /**
   * @param opSubGame
   * @param pageNo
   * @param pageSize
   * @param req
   * @return org.jeecg.common.api.vo.Result<com.baomidou.mybatisplus.core.metadata.IPage <
      * org.jeecg.modules.demo.game.vo.OpSubGameVo>>
   * @Author lili
   * @Description 分页列表查询
   * @Date 11:00 2022/12/16
   **/
  @ApiOperation(value = "op_sub_game-分页列表查询", notes = "op_sub_game-分页列表查询")
  @GetMapping(value = "/list")
  public Result<IPage<OpSubGameVo>> queryPageList(OpSubGame opSubGame,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req) {
    IPage<OpSubGameVo> pageList = opSubGameService.getSubGameList(new Page<>(pageNo, pageSize),
        opSubGame);
    return Result.OK(pageList);
  }

  /**
   * 添加
   *
   * @param opSubGame
   * @return
   */
  @AutoLog(value = "op_sub_game-添加")
  @ApiOperation(value = "op_sub_game-添加", notes = "op_sub_game-添加")
  //@RequiresPermissions("game:op_son_game:add")
  @PostMapping(value = "/add")
  public Result<String> add(@RequestBody OpSubGame opSubGame) {
    return opSubGameService.add(opSubGame);
  }

  /**
   * @param opSubGame
   * @return org.jeecg.common.api.vo.Result<java.lang.String>
   * @Author lili
   * @Description 恢复
   * @Date 13:36 2022/12/16
   **/
  @AutoLog(value = "op_sub_game-恢复")
  @ApiOperation(value = "op_sub_game-恢复", notes = "op_sub_game-恢复")
  //@RequiresPermissions("game:op_son_game:add")
  @PostMapping(value = "/recover")
  public Result<String> recover(@RequestBody OpSubGame opSubGame) {
    opSubGame.setStatus(0);
    opSubGameService.updateById(opSubGame);
    return Result.OK("恢复成功！");
  }

  /**
   * 编辑
   *
   * @param opSubGame
   * @return
   */
  @AutoLog(value = "op_sub_game-编辑")
  @ApiOperation(value = "op_sub_game-编辑", notes = "op_sub_game-编辑")
  //@RequiresPermissions("game:op_sub_game:edit")
  @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
  public Result<String> edit(@RequestBody OpSubGame opSubGame) {
    return opSubGameService.update(opSubGame);
  }

  /**
   * @param opSubGame
   * @return org.jeecg.common.api.vo.Result<java.lang.String>
   * @Author lili
   * @Description 参数
   * @Date 13:36 2022/12/16
   **/
  @AutoLog(value = "op_sub_game-参数")
  @ApiOperation(value = "op_sub_game-参数", notes = "op_sub_game-参数")
  //@RequiresPermissions("game:op_son_game:add")
  @PostMapping(value = "/getConf")
  public Result<String> getConf(@RequestBody OpSubGame opSubGame) {
    opSubGameService.splicingConf(opSubGame);
    return Result.OK(opSubGameService.splicingConf(opSubGame));
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "op_sub_game-通过id删除")
  @ApiOperation(value = "op_sub_game-通过id删除", notes = "op_sub_game-通过id删除")
  //@RequiresPermissions("game:op_sub_game:delete")
  @DeleteMapping(value = "/delete")
  public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
    opSubGameService.delete(id);
    return Result.OK("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "op_sub_game-批量删除")
  @ApiOperation(value = "op_sub_game-批量删除", notes = "op_sub_game-批量删除")
  //@RequiresPermissions("game:op_sub_game:deleteBatch")
  @DeleteMapping(value = "/deleteBatch")
  public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
    this.opSubGameService.removeByIds(Arrays.asList(ids.split(",")));
    return Result.OK("批量删除成功!");
  }

  /**
   * 通过id查询
   *
   * @param id
   * @return
   */
  //@AutoLog(value = "op_sub_game-通过id查询")
  @ApiOperation(value = "op_sub_game-通过id查询", notes = "op_sub_game-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<OpSubGame> queryById(@RequestParam(name = "id", required = true) String id) {
    OpSubGame opSubGame = opSubGameService.getById(id);
    if (opSubGame == null) {
      return Result.error("未找到对应数据");
    }
    return Result.OK(opSubGame);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param opSubGame
   */
  //@RequiresPermissions("game:op_sub_game:exportXls")
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, OpSubGame opSubGame) {
    return super.exportXls(request, opSubGame, OpSubGame.class, "op_son_game");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  //@RequiresPermissions("game:op_sub_game:importExcel")
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
    return super.importExcel(request, response, OpSubGame.class);
  }

}
