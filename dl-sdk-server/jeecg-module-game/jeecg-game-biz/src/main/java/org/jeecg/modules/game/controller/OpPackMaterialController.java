package org.jeecg.modules.game.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.game.dto.OpPackMaterialDto;
import org.jeecg.modules.game.entity.OpPackMaterial;
import org.jeecg.modules.game.service.IOpPackMaterialService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.game.vo.OpPackMaterialVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_pack_material
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
@Api(tags = "op_pack_material")
@RestController
@RequestMapping("/game/opPackMaterial")
@Slf4j
public class OpPackMaterialController extends
    JeecgController<OpPackMaterial, IOpPackMaterialService> {

    @Autowired
    private IOpPackMaterialService opPackMaterialService;

    /**
     * 分页列表查询
     *
     * @param opPackMaterial
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_pack_material-分页列表查询")
    @ApiOperation(value = "op_pack_material-分页列表查询", notes = "op_pack_material-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpPackMaterial>> queryPageList(OpPackMaterialDto opPackMaterial,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        IPage<OpPackMaterial> pageList = opPackMaterialService.getPackMaterialList(
            new Page<>(pageNo, pageSize), opPackMaterial);
        return Result.OK(pageList);
    }

    /**
     * 添加、编辑
     *
     * @param opPackMaterial
     * @return
     */
    @AutoLog(value = "op_pack_material-添加")
    @ApiOperation(value = "op_pack_material-添加", notes = "op_pack_material-添加")
    //@RequiresPermissions("game:op_pack_material:add")
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody OpPackMaterialDto opPackMaterial) throws IOException {
        opPackMaterialService.save(opPackMaterial);
        return Result.OK("更新成功！");
    }

    /**
     * 编辑
     *
     * @param opPackMaterial
     * @return
     */
    @AutoLog(value = "op_pack_material-编辑")
    @ApiOperation(value = "op_pack_material-编辑", notes = "op_pack_material-编辑")
    //@RequiresPermissions("game:op_pack_material:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpPackMaterial opPackMaterial) {
        opPackMaterialService.updateById(opPackMaterial);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_pack_material-通过id删除")
    @ApiOperation(value = "op_pack_material-通过id删除", notes = "op_pack_material-通过id删除")
    //@RequiresPermissions("game:op_pack_material:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opPackMaterialService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_pack_material-批量删除")
    @ApiOperation(value = "op_pack_material-批量删除", notes = "op_pack_material-批量删除")
    //@RequiresPermissions("game:op_pack_material:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opPackMaterialService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_pack_material-通过id查询")
    @ApiOperation(value = "op_pack_material-通过id查询", notes = "op_pack_material-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpPackMaterial> queryById(@RequestParam(name = "id", required = true) String id) {
        OpPackMaterial opPackMaterial = opPackMaterialService.getById(id);
        if (opPackMaterial == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opPackMaterial);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opPackMaterial
     */
    //@RequiresPermissions("game:op_pack_material:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpPackMaterial opPackMaterial) {
        return super.exportXls(request, opPackMaterial, OpPackMaterial.class, "op_pack_material");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("game:op_pack_material:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpPackMaterial.class);
    }

}
