package org.jeecg.modules.users.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.users.entity.OpUser;
import org.jeecg.modules.users.dto.EditModel;
import org.jeecg.modules.users.service.IOpUserService;

import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.users.vo.OpUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_user
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
@Api(tags = "op_user")
@RestController
@RequestMapping("/hello/opUser")
@Slf4j
public class OpUserController extends JeecgController<OpUser, IOpUserService> {

    @Autowired
    private IOpUserService opUserService;

    /**
     * 分页列表查询
     *
     * @param opUser
     * @param pageNo   页码
     * @param pageSize 页面大小
     * @param req
     * @return
     */
    //@AutoLog(value = "op_user-分页列表查询")
    @ApiOperation(value = "op_user-分页列表查询", notes = "op_user-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpUserVo>> queryPageList(OpUser opUser,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        IPage<OpUserVo> pageList = opUserService.SysUserPage(new Page<>(pageNo, pageSize), opUser);
        return Result.OK(pageList);
    }

    /**
     * 修改密码
     *
     * @param
     * @return
     */
    @AutoLog(value = "op_user-修改密码")
    @ApiOperation(value = "op_user-修改密码", notes = "op_user-修改密码")
    //@RequiresPermissions("hello:op_user:add")
    @PostMapping(value = "/editPass")
    public Result<String> editPass(@RequestBody EditModel editModel) {
        opUserService.editPass(editModel.getId(), editModel.getUserPassword());
        return Result.OK("修改成功！");
    }

    /**
     * 修改余额
     *
     * @param
     * @return
     */
    @AutoLog(value = "op_user-修改余额")
    @ApiOperation(value = "op_user-修改余额", notes = "op_user-修改余额")
    //@RequiresPermissions("hello:op_user:add")
    @PostMapping(value = "/editPlatformCurrency")
    public Result<String> editPlatformCurrency(@RequestBody EditModel editModel) {
        opUserService.editPlatformCurrency(editModel.getId(), editModel.getPlatformCurrency());
        return Result.OK("修改成功！");
    }

    /**
     * 解绑手机
     *
     * @param
     * @return
     */
    @AutoLog(value = "op_user-解绑手机")
    @ApiOperation(value = "op_user-解绑手机", notes = "op_user-解绑手机")
    //@RequiresPermissions("hello:op_user:add")
    @PostMapping(value = "/removePhone")
    public Result<String> removePhone(@RequestBody EditModel editModel) {
        opUserService.removePhone(editModel.getId());
        return Result.OK("解绑成功！");
    }


    /**
     * 添加
     *
     * @param opUser
     * @return
     */
    @AutoLog(value = "op_user-添加")
    @ApiOperation(value = "op_user-添加", notes = "op_user-添加")
    //@RequiresPermissions("hello:op_user:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpUser opUser) {
        opUserService.save(opUser);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param opUser
     * @return
     */
    @AutoLog(value = "op_user-编辑")
    @ApiOperation(value = "op_user-编辑", notes = "op_user-编辑")
    //@RequiresPermissions("hello:op_user:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpUser opUser) {
        opUserService.updateById(opUser);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_user-通过id删除")
    @ApiOperation(value = "op_user-通过id删除", notes = "op_user-通过id删除")
    //@RequiresPermissions("hello:op_user:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opUserService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_user-批量删除")
    @ApiOperation(value = "op_user-批量删除", notes = "op_user-批量删除")
    //@RequiresPermissions("hello:op_user:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opUserService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_user-通过id查询")
    @ApiOperation(value = "op_user-通过id查询", notes = "op_user-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpUser> queryById(@RequestParam(name = "id", required = true) String id) {
        OpUser opUser = opUserService.getById(id);
        if (opUser == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opUser);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opUser
     */
    //@RequiresPermissions("hello:op_user:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpUser opUser) {
        return super.exportXls(request, opUser, OpUser.class, "op_user");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("hello:op_user:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpUser.class);
    }

}
