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
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.game.entity.OpFileChunk;
import org.jeecg.modules.game.entity.OpPkgParent;
import org.jeecg.modules.game.service.IOpFileChunkService;
import org.jeecg.modules.game.service.IOpPkgParentService;
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
 * @Description: op_pkg_parent
 * @Author: jeecg-boot
 * @Date: 2023-01-06
 * @Version: V1.0
 */
@Api(tags = "op_pkg_parent")
@RestController
@RequestMapping("/opPkgParent/opPkgParent")
@Slf4j
public class OpPkgParentController extends JeecgController<OpPkgParent, IOpPkgParentService> {

    @Autowired
    private IOpPkgParentService opPkgParentService;

    @Autowired
    private IOpFileChunkService opFileChunkService;

    @ApiOperation(value = "查询已上传分块")
    @GetMapping(value = "/upload")
    public Result<Map<String,Object>> checkUpload(String identifier, Integer gameId, Integer subGameId){
        return opFileChunkService.checkUpload(identifier, gameId, subGameId);
    }
    @ApiOperation(value = "上传母包分片")
    @PostMapping(value = "/upload")
    public Result<Map<String,Object>> chunkUpload(OpFileChunk opFileChunk){
        return opFileChunkService.uploadFile(opFileChunk);
    }
    @GetMapping(value = "/updatePkgInfo")
    @ApiOperation(value = "仅更新母包信息不上传母包，秒传时使用")
    public Result<String> updatePkgInfo(Integer gameId, Integer subGameId,String fileName){
        return opPkgParentService.updatePkgInfo(gameId,subGameId,fileName);
    }
    /**
     * 分页列表查询
     *
     * @param opPkgParent
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_pkg_parent-分页列表查询")
    @ApiOperation(value = "op_pkg_parent-分页列表查询", notes = "op_pkg_parent-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpPkgParent>> queryPageList(OpPkgParent opPkgParent,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<OpPkgParent> queryWrapper = QueryGenerator
            .initQueryWrapper(opPkgParent, req.getParameterMap());
        Page<OpPkgParent> page = new Page<OpPkgParent>(pageNo, pageSize);
        IPage<OpPkgParent> pageList = opPkgParentService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param opPkgParent
     * @return
     */
    @AutoLog(value = "op_pkg_parent-添加")
    @ApiOperation(value = "op_pkg_parent-添加", notes = "op_pkg_parent-添加")
    //@RequiresPermissions("opPkgParent:op_pkg_parent:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpPkgParent opPkgParent) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        opPkgParent.setCreatUser(sysUser.getRealname());
        opPkgParentService.add(opPkgParent);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param opPkgParent
     * @return
     */
    @AutoLog(value = "op_pkg_parent-编辑")
    @ApiOperation(value = "op_pkg_parent-编辑", notes = "op_pkg_parent-编辑")
    //@RequiresPermissions("opPkgParent:op_pkg_parent:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpPkgParent opPkgParent) {
        opPkgParentService.updateById(opPkgParent);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_pkg_parent-通过id删除")
    @ApiOperation(value = "op_pkg_parent-通过id删除", notes = "op_pkg_parent-通过id删除")
    //@RequiresPermissions("opPkgParent:op_pkg_parent:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        return opPkgParentService.deletePkgInfo(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_pkg_parent-批量删除")
    @ApiOperation(value = "op_pkg_parent-批量删除", notes = "op_pkg_parent-批量删除")
    //@RequiresPermissions("opPkgParent:op_pkg_parent:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opPkgParentService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_pkg_parent-通过id查询")
    @ApiOperation(value = "op_pkg_parent-通过id查询", notes = "op_pkg_parent-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpPkgParent> queryById(@RequestParam(name = "id", required = true) String id) {
        OpPkgParent opPkgParent = opPkgParentService.getById(id);
        if (opPkgParent == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opPkgParent);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opPkgParent
     */
    //@RequiresPermissions("opPkgParent:op_pkg_parent:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpPkgParent opPkgParent) {
        return super.exportXls(request, opPkgParent, OpPkgParent.class, "op_pkg_parent");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("opPkgParent:op_pkg_parent:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpPkgParent.class);
    }

}
