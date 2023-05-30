package org.jeecg.modules.advert.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.api.jrtt.project.bo.UpdateProjectResponse;
import org.jeecg.modules.advert.entity.OpJrttProject;
import org.jeecg.modules.advert.service.IOpJrttProjectService;
import org.jeecg.modules.advert.vo.CreateProjectRspVo;
import org.jeecg.modules.advert.vo.OpJrttProjectInfo;
import org.jeecg.modules.advert.vo.OpJrttProjectVo;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
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
 * @Description: op_jrtt_project
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Api(tags = "op_jrtt_project")
@RestController
@RequestMapping("/opJrttProject/opJrttProject")
@Slf4j
public class OpJrttProjectController extends JeecgController<OpJrttProject, IOpJrttProjectService> {

    @Autowired
    private IOpJrttProjectService opJrttProjectService;

    /**
     * 分页列表查询
     *
     * @param opJrttProject
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_jrtt_project-分页列表查询")
    @ApiOperation(value = "op_jrtt_project-分页列表查询", notes = "op_jrtt_project-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpJrttProject>> queryPageList(OpJrttProject opJrttProject,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<OpJrttProject> queryWrapper = QueryGenerator
            .initQueryWrapper(opJrttProject, req.getParameterMap());
        Page<OpJrttProject> page = new Page<OpJrttProject>(pageNo, pageSize);
        IPage<OpJrttProject> pageList = opJrttProjectService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param opJrttProject
     * @return
     */
    @AutoLog(value = "op_jrtt_project-添加")
    @ApiOperation(value = "op_jrtt_project-添加", notes = "op_jrtt_project-添加")
    //@RequiresPermissions("opJrttProject:op_jrtt_project:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpJrttProject opJrttProject) {
        opJrttProjectService.save(opJrttProject);
        return Result.OK("添加成功！");
    }


    /**
     * @param opJrttProjectVo 批量创建项目对象，广告id + 模板
     * @return
     */
    @AutoLog(value = "op_jrtt_project-添加")
    @ApiOperation(value = "op_jrtt_project-添加", notes = "op_jrtt_project-添加")
    //@RequiresPermissions("opJrttProject:op_jrtt_project:add")
    @PostMapping(value = "/batchCreateProject")
    public Result<List<CreateProjectRspVo>> batchCreateProject(
        @RequestBody OpJrttProjectVo opJrttProjectVo) {

        return opJrttProjectService.batchCreateProject(opJrttProjectVo);
    }

    /**
     * 编辑
     *
     * @param opJrttProject
     * @return
     */
    @AutoLog(value = "op_jrtt_project-编辑")
    @ApiOperation(value = "op_jrtt_project-编辑", notes = "op_jrtt_project-编辑")
    //@RequiresPermissions("opJrttProject:op_jrtt_project:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<UpdateProjectResponse> edit(@RequestBody OpJrttProject opJrttProject) {
       return opJrttProjectService.updateProject(opJrttProject);
    }

    @ApiOperation(value = "获取项目编辑信息", notes = "获取项目编辑信息")
    @GetMapping("/getEditInfo")
    public Result<OpJrttProjectInfo> getEditInfo(Integer dealId) {
        OpJrttProject projectInfo = opJrttProjectService
            .getOne(new LambdaQueryWrapper<OpJrttProject>().eq(OpJrttProject::getDealId, dealId));
        if (projectInfo == null){
            return Result.ok(null);
        }
        String name = projectInfo.getName();
        char[] chars = name.toCharArray();
		int i;
		for(i= chars.length-1;i>=0;i--){
			if (chars[i] == '-'){
				break;
			}
		}
		projectInfo.setName(name.substring(i+1));
        OpJrttProjectInfo opJrttProjectInfo = new OpJrttProjectInfo();
        String[] ignoreFields = new String[]{"projectId"};
        BeanUtils.copyProperties(projectInfo,opJrttProjectInfo,ignoreFields);
        opJrttProjectInfo.setProjectId(projectInfo.getProjectId().toString());
        return Result.ok(opJrttProjectInfo);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_jrtt_project-通过id删除")
    @ApiOperation(value = "op_jrtt_project-通过id删除", notes = "op_jrtt_project-通过id删除")
    //@RequiresPermissions("opJrttProject:op_jrtt_project:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opJrttProjectService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_jrtt_project-批量删除")
    @ApiOperation(value = "op_jrtt_project-批量删除", notes = "op_jrtt_project-批量删除")
    //@RequiresPermissions("opJrttProject:op_jrtt_project:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opJrttProjectService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_jrtt_project-通过id查询")
    @ApiOperation(value = "op_jrtt_project-通过id查询", notes = "op_jrtt_project-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpJrttProject> queryById(@RequestParam(name = "id", required = true) String id) {
        OpJrttProject opJrttProject = opJrttProjectService.getById(id);
        if (opJrttProject == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opJrttProject);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opJrttProject
     */
    //@RequiresPermissions("opJrttProject:op_jrtt_project:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpJrttProject opJrttProject) {
        return super.exportXls(request, opJrttProject, OpJrttProject.class, "op_jrtt_project");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("opJrttProject:op_jrtt_project:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpJrttProject.class);
    }

}
