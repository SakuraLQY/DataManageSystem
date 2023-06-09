package org.jeecg.modules.game.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.game.dto.OpPrivacyPolicyDto;
import org.jeecg.modules.game.entity.OpPrivacyPolicy;
import org.jeecg.modules.game.service.IOpPrivacyPolicyService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.game.vo.OpSubGameVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_privacy_policy
 * @Author: jeecg-boot
 * @Date: 2022-12-28
 * @Version: V1.0
 */
@Api(tags = "op_privacy_policy")
@RestController
@RequestMapping("/privacyPolicy/opPrivacyPolicy")
@Slf4j
public class OpPrivacyPolicyController extends
    JeecgController<OpPrivacyPolicy, IOpPrivacyPolicyService> {

    @Autowired
    private IOpPrivacyPolicyService opPrivacyPolicyService;

    /**
     * 分页列表查询
     *
     * @param opPrivacyPolicy
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_privacy_policy-分页列表查询")
    @ApiOperation(value = "op_privacy_policy-分页列表查询", notes = "op_privacy_policy-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpPrivacyPolicy>> queryPageList(OpPrivacyPolicyDto opPrivacyPolicy,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        IPage<OpPrivacyPolicy> pageList = opPrivacyPolicyService.getPrivacyPolicyList(
            new Page<>(pageNo, pageSize),
            opPrivacyPolicy);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param opPrivacyPolicy
     * @return
     */
    @AutoLog(value = "op_privacy_policy-添加")
    @ApiOperation(value = "op_privacy_policy-添加", notes = "op_privacy_policy-添加")
    //@RequiresPermissions("privacyPolicy:op_privacy_policy:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpPrivacyPolicy opPrivacyPolicy) {
        opPrivacyPolicyService.add(opPrivacyPolicy);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param opPrivacyPolicy
     * @return
     */
    @AutoLog(value = "op_privacy_policy-编辑")
    @ApiOperation(value = "op_privacy_policy-编辑", notes = "op_privacy_policy-编辑")
    //@RequiresPermissions("privacyPolicy:op_privacy_policy:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpPrivacyPolicy opPrivacyPolicy) {
        opPrivacyPolicyService.update(opPrivacyPolicy);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_privacy_policy-通过id删除")
    @ApiOperation(value = "op_privacy_policy-通过id删除", notes = "op_privacy_policy-通过id删除")
    //@RequiresPermissions("privacyPolicy:op_privacy_policy:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opPrivacyPolicyService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_privacy_policy-批量删除")
    @ApiOperation(value = "op_privacy_policy-批量删除", notes = "op_privacy_policy-批量删除")
    //@RequiresPermissions("privacyPolicy:op_privacy_policy:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opPrivacyPolicyService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_privacy_policy-通过id查询")
    @ApiOperation(value = "op_privacy_policy-通过id查询", notes = "op_privacy_policy-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpPrivacyPolicy> queryById(
        @RequestParam(name = "id", required = true) String id) {
        OpPrivacyPolicy opPrivacyPolicy = opPrivacyPolicyService.getById(id);
        if (opPrivacyPolicy == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opPrivacyPolicy);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opPrivacyPolicy
     */
    //@RequiresPermissions("privacyPolicy:op_privacy_policy:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpPrivacyPolicy opPrivacyPolicy) {
        return super.exportXls(request, opPrivacyPolicy, OpPrivacyPolicy.class,
            "op_privacy_policy");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("privacyPolicy:op_privacy_policy:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpPrivacyPolicy.class);
    }

}
