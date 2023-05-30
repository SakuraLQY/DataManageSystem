package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.advert.entity.OpPutAccount;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.service.IOpPutAccountService;
import org.jeecg.modules.advert.vo.OpJrttPutAccountVo;
import org.jeecg.modules.advert.vo.SYNCVO;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_put_account
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
@Api(tags = "op_put_account")
@RestController
@RequestMapping("/opputaccount/opJrttPutAccount")
@Slf4j
public class OpJrttPutAccountController extends JeecgController<OpPutAccount, IOpPutAccountService> {

    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;
    @Autowired
    private IOpPutAccountService opPutAccountService;

    /**
     * 分页列表查询
     *
     * @param opPutAccount
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "op_put_account-分页列表查询")
    @ApiOperation(value = "op_put_account-分页列表查询", notes = "op_put_account-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OpJrttPutAccountVo>> queryPageList(OpPutAccount opPutAccount,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        IPage<OpJrttPutAccountVo> pageList = opJrttPutAccountService.getPage(opPutAccount, pageNo, pageSize, req);
        return Result.OK(pageList);
    }


    @GetMapping(value = "/auth")
    @ApiOperation(value = "前往授权")
    public Result<String> auth(@RequestParam(name = "accountId") Integer accountId) {
        return opJrttPutAccountService.auth(accountId);
    }

    @GetMapping(value = "/callback")
    @ApiOperation(value = "回调方法")
    public String callback(@RequestParam(name = "state") Integer state,
        @RequestParam(name = "auth_code") String authCode) {
        return opJrttPutAccountService.callBack(state, authCode);
    }

    @GetMapping(value = "/synAccount")
    @ApiOperation(value = "同步账号")
    public Result<List<SYNCVO>> synAccount(@RequestParam(name = "accountId") Integer accountId) {
        return opJrttPutAccountService.synAccount(accountId);
    }

    /**
     * 添加
     *
     * @param opPutAccount
     * @return
     */
    @AutoLog(value = "op_put_account-添加")
    @ApiOperation(value = "op_put_account-添加", notes = "op_put_account-添加")
    //@RequiresPermissions("opputaccount:op_put_account:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpPutAccount opPutAccount) {
        opPutAccountService.save(opPutAccount);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param opPutAccount
     * @return
     */
    @AutoLog(value = "op_put_account-编辑")
    @ApiOperation(value = "op_put_account-编辑", notes = "op_put_account-编辑")
    //@RequiresPermissions("opputaccount:op_put_account:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OpPutAccount opPutAccount) {
        opPutAccountService.updateById(opPutAccount);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_put_account-通过id删除")
    @ApiOperation(value = "op_put_account-通过id删除", notes = "op_put_account-通过id删除")
    //@RequiresPermissions("opputaccount:op_put_account:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        this.opJrttPutAccountService.deleteByAccountId(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "op_put_account-批量删除")
    @ApiOperation(value = "op_put_account-批量删除", notes = "op_put_account-批量删除")
    //@RequiresPermissions("opputaccount:op_put_account:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.opJrttPutAccountService.batchDeleteByAccountId(ids);
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "op_put_account-通过id查询")
    @ApiOperation(value = "op_put_account-通过id查询", notes = "op_put_account-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OpPutAccount> queryById(@RequestParam(name = "id", required = true) String id) {
        OpPutAccount opPutAccount = opPutAccountService.getById(id);
        if (opPutAccount == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(opPutAccount);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param opPutAccount
     */
    //@RequiresPermissions("opputaccount:op_put_account:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpPutAccount opPutAccount) {
        return super.exportXls(request, opPutAccount, OpPutAccount.class, "op_put_account");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("opputaccount:op_put_account:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpPutAccount.class);
    }

}
