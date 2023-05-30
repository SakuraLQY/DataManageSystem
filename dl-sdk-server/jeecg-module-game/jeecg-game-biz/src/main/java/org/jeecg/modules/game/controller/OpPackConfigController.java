package org.jeecg.modules.game.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.game.entity.OpPackConfig;
import org.jeecg.modules.game.service.IOpPackConfigService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.game.vo.OpPackConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_pack_config
 * @Author: jeecg-boot
 * @Date: 2023-01-07
 * @Version: V1.0
 */
@Api(tags = "op_pack_config")
@RestController
@RequestMapping("/game/opPackConfig")
@Slf4j
public class OpPackConfigController extends JeecgController<OpPackConfig, IOpPackConfigService> {

    @Autowired
    private IOpPackConfigService opPackConfigService;

    /**
     * 分页列表查询
     *
     * @param opPackConfig
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_pack_config-分页列表查询")
    @ApiOperation(value = "op_pack_config-分页列表查询", notes = "op_pack_config-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpPackConfig>> queryPageList(OpPackConfig opPackConfig,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<OpPackConfig> queryWrapper = QueryGenerator.initQueryWrapper(opPackConfig,
            req.getParameterMap());
        Page<OpPackConfig> page = new Page<OpPackConfig>(pageNo, pageSize);
        IPage<OpPackConfig> pageList = opPackConfigService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * @param opPackConfig
     * @return org.jeecg.common.api.vo.Result<org.jeecg.modules.game.vo.OpPackConfigVo>
     * @Author lili
     * @Description 列表查询
     * @Date 15:02 2023/1/11
     **/
    @ApiOperation(value = "op_pack_config-列表查询", notes = "op_pack_config-列表查询")
    @GetMapping(value = "/getObjByGameId")
    public Result<OpPackConfigVo> getObjByGameId(OpPackConfig opPackConfig) {
        return Result.OK(opPackConfigService.getObjByGameId(opPackConfig));
    }

    /**
     * 添加
     *
     * @param opPackConfig
     * @return
     */
    @AutoLog(value = "op_pack_config-添加")
    @ApiOperation(value = "op_pack_config-添加", notes = "op_pack_config-添加")
    //@RequiresPermissions("game:op_pack_config:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpPackConfig opPackConfig) {
        opPackConfigService.save(opPackConfig);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param
     * @return
     */
    @AutoLog(value = "op_pack_config-编辑")
    @ApiOperation(value = "op_pack_config-编辑", notes = "op_pack_config-编辑")
    //@RequiresPermissions("game:op_pack_config:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpPackConfigVo opPackConfigVo) {
        opPackConfigService.update(opPackConfigVo);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_pack_config-通过id删除")
    @ApiOperation(value = "op_pack_config-通过id删除", notes = "op_pack_config-通过id删除")
    //@RequiresPermissions("game:op_pack_config:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opPackConfigService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_pack_config-批量删除")
    @ApiOperation(value = "op_pack_config-批量删除", notes = "op_pack_config-批量删除")
    //@RequiresPermissions("game:op_pack_config:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opPackConfigService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_pack_config-通过id查询")
    @ApiOperation(value = "op_pack_config-通过id查询", notes = "op_pack_config-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpPackConfig> queryById(@RequestParam(name = "id", required = true) String id) {
        OpPackConfig opPackConfig = opPackConfigService.getById(id);
        if (opPackConfig == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opPackConfig);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opPackConfig
     */
    //@RequiresPermissions("game:op_pack_config:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpPackConfig opPackConfig) {
        return super.exportXls(request, opPackConfig, OpPackConfig.class, "op_pack_config");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("game:op_pack_config:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpPackConfig.class);
    }

}
