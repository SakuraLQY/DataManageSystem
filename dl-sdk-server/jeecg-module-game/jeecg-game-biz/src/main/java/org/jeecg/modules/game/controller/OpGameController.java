package org.jeecg.modules.game.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.game.common.ResultGameAndSubGameList;
import org.jeecg.modules.game.entity.OpGame;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.service.IOpGameService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.game.service.IOpSubGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_game
 * @Author: jeecg-boot
 * @Date: 2022-12-08
 * @Version: V1.0
 */
@Api(tags = "op_game")
@RestController
@RequestMapping("/game/opGame")
@Slf4j
public class OpGameController extends JeecgController<OpGame, IOpGameService> {

    @Autowired
    private IOpGameService opGameService;

    @Autowired
    private IOpSubGameService opSubGameService;

    /**
     * 分页列表查询
     *
     * @param opGame
     * @param pageNo   页码
     * @param pageSize 页面大小
     * @param req
     * @return
     */
    //@AutoLog(value = "op_game-分页列表查询")
    @ApiOperation(value = "op_game-分页列表查询", notes = "op_game-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpGame>> queryPageList(OpGame opGame,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<OpGame> queryWrapper = QueryGenerator.initQueryWrapper(opGame,
            req.getParameterMap());
        Page<OpGame> page = new Page<OpGame>(pageNo, pageSize);
        IPage<OpGame> pageList = opGameService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * @return org.jeecg.common.api.vo.Result<java.util.List < org.jeecg.modules.game.entity.OpGame>>
     * @Author lili
     * @Description 得到游戏和子游戏列表
     * @Date 15:26 2022/12/21
     **/
    @ApiOperation(value = "op_game-游戏和子游戏列表查询", notes = "op_game-游戏和子游戏列表查询")
    @GetMapping(value = "/gameAndSubGameList")
    public Result<ResultGameAndSubGameList<OpGame>> getGameAndSubGameList() {
        return Result.OK(opGameService.getGameAndSubGameList());
    }

    /**
     * 添加
     *
     * @param opGame
     * @return
     */
    @AutoLog(value = "op_game-添加")
    @ApiOperation(value = "op_game-添加", notes = "op_game-添加")
    //@RequiresPermissions("game:op_game:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpGame opGame) {
        opGameService.save(opGame);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param opGame
     * @return
     */
    @AutoLog(value = "op_game-编辑")
    @ApiOperation(value = "op_game-编辑", notes = "op_game-编辑")
    //@RequiresPermissions("game:op_game:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpGame opGame) {
        opGameService.updateById(opGame);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_game-通过id删除")
    @ApiOperation(value = "op_game-通过id删除", notes = "op_game-通过id删除")
    //@RequiresPermissions("game:op_game:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opGameService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_game-批量删除")
    @ApiOperation(value = "op_game-批量删除", notes = "op_game-批量删除")
    //@RequiresPermissions("game:op_game:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opGameService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_game-通过id查询")
    @ApiOperation(value = "op_game-通过id查询", notes = "op_game-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpGame> queryById(@RequestParam(name = "id", required = true) String id) {
        OpGame opGame = opGameService.getById(id);
        if (opGame == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opGame);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opGame
     */
    //@RequiresPermissions("game:op_game:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpGame opGame) {
        return super.exportXls(request, opGame, OpGame.class, "op_game");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("game:op_game:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpGame.class);
    }

}
