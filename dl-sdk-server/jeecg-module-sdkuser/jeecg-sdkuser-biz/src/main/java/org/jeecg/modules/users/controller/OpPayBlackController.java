package org.jeecg.modules.users.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.users.common.ResultPayBlackList;
import org.jeecg.modules.users.entity.OpPayBlack;
import org.jeecg.modules.users.service.IOpPayBlackService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.users.vo.OpPayBlackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_pay_black
 * @Author: jeecg-boot
 * @Date: 2022-12-19
 * @Version: V1.0
 */
@Api(tags = "op_pay_black")
@RestController
@RequestMapping("/black/opPayBlack")
@Slf4j
public class OpPayBlackController extends JeecgController<OpPayBlack, IOpPayBlackService> {

    @Autowired
    private IOpPayBlackService opPayBlackService;

    /**
     * 分页列表查询
     *
     * @param opPayBlack
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_pay_black-分页列表查询")
    @ApiOperation(value = "op_pay_black-分页列表查询", notes = "op_pay_black-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpPayBlackVo>> queryPageList(OpPayBlack opPayBlack,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        IPage<OpPayBlackVo> pageList = opPayBlackService.getPayBlackList(
            new Page<>(pageNo, pageSize),
            opPayBlack);

        return Result.OK(pageList);
    }

    /**
     * @param opPayBlack
     * @return org.jeecg.common.api.vo.Result<java.util.List < org.jeecg.modules.users.vo.OpPayBlackVo>>
     * @Author lili
     * @Description 下拉框列表查询
     * @Date 20:05 2022/12/21
     **/
    @ApiOperation(value = "op_pay_black-下拉框列表查询", notes = "op_pay_black-下拉框列表查询")
    @GetMapping(value = "/optionList")
    public Result<List<OpPayBlackVo>> getOptionList(OpPayBlack opPayBlack) {
        List<OpPayBlackVo> pageList = opPayBlackService.getOptionList(opPayBlack);
        return Result.OK(pageList);
    }

    /**
     * @param userId    用户ID
     * @param gameId    游戏ID
     * @param subGameId 子游戏ID
     * @param ip        IP
     * @param device    设备
     * @return java.lang.Boolean
     * @Author lili
     * @Description 判断是否被拉黑
     * @Date 13:56 2022/12/22
     **/
    @ApiOperation(value = "op_pay_black-判断是否拉黑", notes = "op_pay_black-判断是否拉黑")
    @GetMapping(value = "/checkUser")
    public Boolean checkUser(Integer userId, Integer gameId, Integer subGameId, String ip,
        String device) {
        return opPayBlackService.checkUser(userId, gameId, subGameId, ip, device);
    }

    /**
     * 添加
     *
     * @param opPayBlack
     * @return
     */
    @AutoLog(value = "op_pay_black-添加")
    @ApiOperation(value = "op_pay_black-添加", notes = "op_pay_black-添加")
    //@RequiresPermissions("users:op_pay_black:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpPayBlack opPayBlack) {
        opPayBlackService.add(opPayBlack);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param opPayBlack
     * @return
     */
    @AutoLog(value = "op_pay_black-编辑")
    @ApiOperation(value = "op_pay_black-编辑", notes = "op_pay_black-编辑")
    //@RequiresPermissions("users:op_pay_black:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpPayBlack opPayBlack) {
        opPayBlackService.update(opPayBlack);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_pay_black-通过id删除")
    @ApiOperation(value = "op_pay_black-通过id删除", notes = "op_pay_black-通过id删除")
    //@RequiresPermissions("users:op_pay_black:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        opPayBlackService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_pay_black-批量删除")
    @ApiOperation(value = "op_pay_black-批量删除", notes = "op_pay_black-批量删除")
    //@RequiresPermissions("users:op_pay_black:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opPayBlackService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_pay_black-通过id查询")
    @ApiOperation(value = "op_pay_black-通过id查询", notes = "op_pay_black-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpPayBlack> queryById(@RequestParam(name = "id", required = true) String id) {
        OpPayBlack opPayBlack = opPayBlackService.getById(id);
        if (opPayBlack == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opPayBlack);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opPayBlack
     */
    //@RequiresPermissions("users:op_pay_black:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpPayBlack opPayBlack) {
        return super.exportXls(request, opPayBlack, OpPayBlack.class, "op_pay_black");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("users:op_pay_black:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpPayBlack.class);
    }

}
