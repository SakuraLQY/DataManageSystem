package org.jeecg.modules.advert.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.dto.OpMaterialDto;
import org.jeecg.modules.advert.dto.SetAccountDto;
import org.jeecg.modules.advert.entity.OpMaterial;
import org.jeecg.modules.advert.service.IOpMaterialService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.advert.vo.AccountVo;
import org.jeecg.modules.advert.vo.OpMaterialVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_material
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
@Api(tags = "op_material")
@RestController
@RequestMapping("/advert/opMaterial")
@Slf4j
public class OpMaterialController extends JeecgController<OpMaterial, IOpMaterialService> {

    @Autowired
    private IOpMaterialService opMaterialService;

    /**
     * 分页列表查询
     *
     * @param opMaterial
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_material-分页列表查询")
    @ApiOperation(value = "op_material-分页列表查询", notes = "op_material-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpMaterialVo>> queryPageList(OpMaterialDto opMaterial,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        return Result.OK(
            opMaterialService.getMaterialList(new Page<>(pageNo, pageSize), opMaterial));
    }

    /**
     * 更新账号
     *
     * @param setAccountDto
     * @return
     */
    @ApiOperation(value = "op_material-更新账号", notes = "op_material-更新账号")
    @PostMapping(value = "/setAccount")
    public Result<String> setAccount(@RequestBody SetAccountDto setAccountDto) {
        opMaterialService.setAccount(setAccountDto);
        return Result.OK("设置成功！");
    }

    /**
     * 添加
     *
     * @param opMaterial
     * @return
     */
    @ApiOperation(value = "op_material-添加", notes = "op_material-添加")
    //@RequiresPermissions("advert:op_material:add")
    @PostMapping(value = "/add")
    public Result<String> add(OpMaterialDto opMaterial) {
        opMaterialService.add(opMaterial);
        return Result.OK("添加成功！");
    }

    /**
     * 推送素材
     *
     * @param setAccountDto
     * @return
     */
    @ApiOperation(value = "op_material-推送素材", notes = "op_material-推送素材")
    @PostMapping(value = "/pushMaterial")
    public Result<String> pushMaterial(@RequestBody SetAccountDto setAccountDto) {
        opMaterialService.pushMaterial(setAccountDto);
        return Result.OK("推送成功！");
    }

    /**
     * @param file
     * @Author lili
     * @Description 上传文件到指定位置
     * @Date 16:52 2023/1/12
     **/
    @PostMapping(value = "/upload")
    public Result<String> readPhoto(@RequestParam(name = "file") MultipartFile file)
        throws IOException {
        return Result.ok(opMaterialService.upload(file));
    }

    /**
     * 编辑
     *
     * @param opMaterial
     * @return
     */
    @AutoLog(value = "op_material-编辑")
    @ApiOperation(value = "op_material-编辑", notes = "op_material-编辑")
    //@RequiresPermissions("advert:op_material:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpMaterial opMaterial) {
        opMaterialService.updateById(opMaterial);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_material-通过id删除")
    @ApiOperation(value = "op_material-通过id删除", notes = "op_material-通过id删除")
    //@RequiresPermissions("advert:op_material:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opMaterialService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_material-批量删除")
    @ApiOperation(value = "op_material-批量删除", notes = "op_material-批量删除")
    //@RequiresPermissions("advert:op_material:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opMaterialService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_material-通过id查询")
    @ApiOperation(value = "op_material-通过id查询", notes = "op_material-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpMaterial> queryById(@RequestParam(name = "id", required = true) String id) {
        OpMaterial opMaterial = opMaterialService.getById(id);
        if (opMaterial == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opMaterial);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opMaterial
     */
    //@RequiresPermissions("advert:op_material:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpMaterial opMaterial) {
        return super.exportXls(request, opMaterial, OpMaterial.class, "op_material");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("advert:op_material:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpMaterial.class);
    }

    /**
     * 通过id查询
     *
     * @param ids
     * @return
     */
    //@AutoLog(value = "op_material-通过id查询")
    @ApiOperation(value = "op_material-通过id批量查询", notes = "op_material-通过id批量查询")
    @GetMapping(value = "/queryByIdList")
    public Result<List<OpMaterial>> queryByIdList(@RequestParam(name = "ids", required = true) String ids) {
        List<OpMaterial> opMaterialList= opMaterialService.queryByIdList(ids);
        if (opMaterialList == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opMaterialList);
    }

}
