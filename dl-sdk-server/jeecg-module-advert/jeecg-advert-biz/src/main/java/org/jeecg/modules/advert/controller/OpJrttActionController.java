package org.jeecg.modules.advert.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.dto.OpJrttAssetsDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttAction;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IOpJrttActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: op_jrtt_action
 * @Author: jeecg-boot
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Api(tags = "op_jrtt_action")
@RestController
@RequestMapping("/advert/opJrttAction")
@Slf4j
public class OpJrttActionController extends JeecgController<OpJrttAction, IOpJrttActionService> {

    /**
     * 添加
     *
     * @param opJrttAction
     * @return
     */
    @AutoLog(value = "op_jrtt_action-添加")
    @ApiOperation(value = "op_jrtt_action-添加", notes = "op_jrtt_action-添加")
    //@RequiresPermissions("advert:op_jrtt_assets:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OpJrttAction opJrttAction) {
        service.saveAction(opJrttAction);
        return Result.OK("添加成功！");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "op_deal-通过id删除")
    @ApiOperation(value = "op_deal-通过id删除", notes = "op_deal-通过id删除")
    //@RequiresPermissions("advert:ad_deal:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        service.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 查询list
     *
     * @return
     */
    //@AutoLog(value = "op_jrtt_action-查询list")
    @ApiOperation(value = "op_jrtt_action-查询list", notes = "op_jrtt_action-查询list")
    @GetMapping(value = "/queryByActionType")
    public Result<List<OpJrttAction>> queryList() {
        List<OpJrttAction> opJrttActionList = service.queryByActionType();
        return Result.OK(opJrttActionList);
    }

}
