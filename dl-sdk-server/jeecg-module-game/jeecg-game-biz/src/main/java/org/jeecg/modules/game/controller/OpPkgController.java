package org.jeecg.modules.game.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.game.bo.GameSelectModal;
import org.jeecg.modules.game.entity.OpPkg;
import org.jeecg.modules.game.service.IOpPkgService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.game.vo.GameObjVo;
import org.jeecg.modules.game.vo.GameVo;
import org.jeecg.modules.game.vo.OpPkgVo;
import org.jeecg.modules.game.vo.SubAndOpPackGameVo;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: op_pkg
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
@Api(tags = "op_pkg")
@RestController
@RequestMapping("/game/opPkg")
@Slf4j
public class OpPkgController extends JeecgController<OpPkg, IOpPkgService> {

    @Autowired
    private IOpPkgService opPkgService;

    /**
     * @return Result<List < Map < String, Object>>>
     * @author xmh
     * @description 获取指定渠道游戏包
     * @date 2023/2/14 14:35
     */
    @ApiOperation(value = "获取指定渠道游戏包", notes = "获取指定渠道游戏包")
    @GetMapping(value = "/option")
    public Result<List<Map<String, Object>>> getJrttPkg(
        @RequestParam(name = "gameId", required = false) Integer gameId,
        @RequestParam(name = "subGameId", required = false) Integer subGameId,
        @RequestParam(name = "channelId", required = false) Integer channelId) {
        List<Map<String, Object>> result = opPkgService.getJrttPkg(gameId, subGameId, channelId);
        return Result.ok(result);
    }

    /**
     * 分页列表查询
     *
     * @param opPkg
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_pkg-分页列表查询")
    @ApiOperation(value = "op_pkg-分页列表查询", notes = "op_pkg-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpPkgVo>> queryPageList(OpPkg opPkg,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<OpPkg> queryWrapper = QueryGenerator.initQueryWrapper(opPkg,
            req.getParameterMap());
        queryWrapper.eq("status", CommonConstant.DEL_FLAG_0);
        Page<OpPkg> page = new Page<OpPkg>(pageNo, pageSize);
        IPage<OpPkgVo> pageList = opPkgService.getPage(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * @param type 0/1 单选/多选
     * @return org.jeecg.common.api.vo.Result<java.util.List < org.jeecg.modules.game.vo.SubAndOpPackGameVo>>
     * @Author lili
     * @Description 返回游戏+子游戏+一级游戏包列表
     * @Date 11:22 2023/2/14
     **/
    @ApiOperation(value = "op_pkg-列表查询", notes = "op_pkg-列表查询")
    @GetMapping(value = "/getOptionList")
    public Result<List<SubAndOpPackGameVo>> optionList(
        GameSelectModal gameSelectModal) {
        List<SubAndOpPackGameVo> list = opPkgService.getOptionList(gameSelectModal);
        return Result.OK(list);
    }

    @ApiOperation(value = "op_pkg-列表查询", notes = "op_pkg-列表查询")
    @GetMapping(value = "/queryList")
    public Result<Map<Integer, GameVo>> queryList() {
        Map<Integer, GameVo> list = opPkgService.queryList();
        return Result.OK(list);
    }

    /**
     * 添加
     *
     * @param opPkg
     * @return
     */
    @AutoLog(value = "op_pkg-添加")
    @ApiOperation(value = "op_pkg-添加", notes = "op_pkg-添加")
    //@RequiresPermissions("game:op_pkg:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpPkg opPkg) {
        opPkgService.saveOpPkg(opPkg);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param opPkg
     * @return
     */
    @AutoLog(value = "op_pkg-编辑")
    @ApiOperation(value = "op_pkg-编辑", notes = "op_pkg-编辑")
    //@RequiresPermissions("game:op_pkg:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpPkg opPkg) {
        opPkgService.updateById(opPkg);
        return Result.OK("编辑成功!");
    }

    /**
     * 打包
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_pkg-打包")
    @ApiOperation(value = "op_pkg-打包", notes = "op_pkg-打包")
    //@RequiresPermissions("game:op_pkg:add")
    @PostMapping(value = "/updatePackState")
    public Result<String> updatePackState(@RequestParam(name = "id", required = true) Integer id) {
        opPkgService.updatePackState(id);
        return Result.OK("修改打包状态成功，正在执行打包任务");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_pkg-通过id删除")
    @ApiOperation(value = "op_pkg-通过id删除", notes = "op_pkg-通过id删除")
    //@RequiresPermissions("game:op_pkg:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        opPkgService.deleteOpPkgById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_pkg-批量删除")
    @ApiOperation(value = "op_pkg-批量删除", notes = "op_pkg-批量删除")
    //@RequiresPermissions("game:op_pkg:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opPkgService.deleteOpPkgByIds(ids);
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_pkg-通过id查询")
    @ApiOperation(value = "op_pkg-通过id查询", notes = "op_pkg-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpPkg> queryById(@RequestParam(name = "id", required = true) String id) {
        OpPkg opPkg = opPkgService.getById(id);
        if (opPkg == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opPkg);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opPkg
     */
    //@RequiresPermissions("game:op_pkg:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpPkg opPkg) {
        return super.exportXls(request, opPkg, OpPkg.class, "op_pkg");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("game:op_pkg:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpPkg.class);
    }

}
