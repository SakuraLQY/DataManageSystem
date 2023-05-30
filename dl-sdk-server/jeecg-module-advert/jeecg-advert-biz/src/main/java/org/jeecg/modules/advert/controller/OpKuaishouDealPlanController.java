package org.jeecg.modules.advert.controller;

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
import org.jeecg.modules.advert.dto.OpKuaishouDealPlanDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpKuaishouDealPlan;
import org.jeecg.modules.advert.service.IOpKuaishouDealPlanService;
import org.jeecg.modules.advert.vo.MessageVo;
import org.jeecg.modules.advert.vo.OpKuaishouDealPlanVo;
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

/** @Description: op_kuaishou_deal @Author: jeecg-boot @Date: 2023-03-09 @Version: V1.0 */
@Api(tags = "op_kuaishou_deal")
@RestController
@RequestMapping("/advert/opKuaishouDealPlan")
@Slf4j
public class OpKuaishouDealPlanController
    extends JeecgController<OpKuaishouDealPlan, IOpKuaishouDealPlanService> {
  @Autowired private IOpKuaishouDealPlanService opKuaishouDealService;

  /**
   * 分页列表查询
   *
   * @param opDeal
   * @param pageNo
   * @param pageSize
   * @param req
   * @return
   */
  // @AutoLog(value = "op_kuaishou_deal-分页列表查询")
  @ApiOperation(value = "op_kuaishou_deal-分页列表查询", notes = "op_kuaishou_deal-分页列表查询")
  @GetMapping(value = "/list")
  public Result<IPage<OpKuaishouDealPlanVo>> queryPageList(
      OpDeal opDeal,
      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
      HttpServletRequest req,
      Integer campaignId,
      String startDate,
      String endDate) {
    Page<OpKuaishouDealPlan> page = new Page<OpKuaishouDealPlan>(pageNo, pageSize);
    IPage<OpKuaishouDealPlanVo> pageList =
        opKuaishouDealService.getByPage(page, opDeal, campaignId, startDate, endDate);
    return Result.OK(pageList);
  }

  /**
   * 添加
   *
   * @param opKuaishouDealPlanDto
   * @return
   */
  @AutoLog(value = "op_kuaishou_deal-添加")
  @ApiOperation(value = "op_kuaishou_deal-添加", notes = "op_kuaishou_deal-添加")
  // @RequiresPermissions("advert:op_kuaishou_deal:add")
  @PostMapping(value = "/add")
  public Result<List<MessageVo>> add(@RequestBody OpKuaishouDealPlanDto opKuaishouDealPlanDto) {
    List<MessageVo> list = opKuaishouDealService.addDeal(opKuaishouDealPlanDto);
    return Result.OK(list);
  }

  /**
   * 编辑
   *
   * @param opKuaishouDealPlanDto
   * @return
   */
  @AutoLog(value = "op_kuaishou_deal-编辑")
  @ApiOperation(value = "op_kuaishou_deal-编辑", notes = "op_kuaishou_deal-编辑")
  // @RequiresPermissions("advert:op_kuaishou_deal:edit")
  @RequestMapping(
      value = "/edit",
      method = {RequestMethod.PUT, RequestMethod.POST})
  public Result<String> edit(@RequestBody OpKuaishouDealPlanDto opKuaishouDealPlanDto) {
    opKuaishouDealService.updateDeal(opKuaishouDealPlanDto);
    return Result.OK("编辑成功!");
  }

  /**
   * 通过id删除
   *
   * @param id
   * @return
   */
  @AutoLog(value = "op_kuaishou_deal-通过id删除")
  @ApiOperation(value = "op_kuaishou_deal-通过id删除", notes = "op_kuaishou_deal-通过id删除")
  // @RequiresPermissions("advert:op_kuaishou_deal:delete")
  @DeleteMapping(value = "/delete")
  public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
    opKuaishouDealService.deleteDeal(id);
    return Result.OK("删除成功!");
  }

  /**
   * 批量删除
   *
   * @param ids
   * @return
   */
  @AutoLog(value = "op_kuaishou_deal-批量删除")
  @ApiOperation(value = "op_kuaishou_deal-批量删除", notes = "op_kuaishou_deal-批量删除")
  // @RequiresPermissions("advert:op_kuaishou_deal:deleteBatch")
  @DeleteMapping(value = "/deleteBatch")
  public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
    this.opKuaishouDealService.deleteDeal(Arrays.asList(ids.split(",")));
    return Result.OK("批量删除成功!");
  }

  /**
   * 通过id查询
   *
   * @param id
   * @return
   */
  // @AutoLog(value = "op_kuaishou_deal-通过id查询")
  @ApiOperation(value = "op_kuaishou_deal-通过id查询", notes = "op_kuaishou_deal-通过id查询")
  @GetMapping(value = "/queryById")
  public Result<OpKuaishouDealPlan> queryById(
      @RequestParam(name = "id", required = true) String id) {
    OpKuaishouDealPlan opKuaishouDeal = opKuaishouDealService.getById(id);
    if (opKuaishouDeal == null) {
      return Result.error("未找到对应数据");
    }
    return Result.OK(opKuaishouDeal);
  }

  /**
   * 导出excel
   *
   * @param request
   * @param opKuaishouDeal
   */
  // @RequiresPermissions("advert:op_kuaishou_deal:exportXls")
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, OpKuaishouDealPlan opKuaishouDeal) {
    return super.exportXls(request, opKuaishouDeal, OpKuaishouDealPlan.class, "op_kuaishou_deal");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  // @RequiresPermissions("advert:op_kuaishou_deal:importExcel")
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
    return super.importExcel(request, response, OpKuaishouDealPlan.class);
  }

  /**
   * @author xmh
   * @description 补建广告计划
   * @date 2023/3/16 10:34
   * @param opKuaishouDealPlanDto: 补建信息
   * @return Result<String>
   */
  @AutoLog(value = "补建广告计划")
  @ApiOperation(value = "补建广告计划", notes = "补建广告计划")
  @PostMapping(value = "/makeUp")
  public Result<String> makeUp(@RequestBody OpKuaishouDealPlanDto opKuaishouDealPlanDto) {
    opKuaishouDealService.makeUpDealPlan(opKuaishouDealPlanDto);
    return Result.OK("补建成功");
  }

  /**
   * @author xmh
   * @description 修改计划预算
   * @date 2023/3/16 15:02
   * @param opKuaishouDealPlanDto: 预算信息
   * @return Result<String>
   */
  @AutoLog(value = "修改计划预算")
  @ApiOperation(value = "修改计划预算", notes = "修改计划预算")
  @PostMapping(value = "/modifyBudget")
  public Result<String> modifyBudget(@RequestBody OpKuaishouDealPlanDto opKuaishouDealPlanDto) {
    opKuaishouDealService.modifyBudget(opKuaishouDealPlanDto);
    return Result.OK("修改成功");
  }

  /**
   *
   * @return
   */
  @ApiOperation(value = "打包广告id到APK并发送到CDN和快手")
  @GetMapping("/packDeal")
  public Result<String> packDeal(Integer id) throws Exception {

    opKuaishouDealService.packDeal(id);
    return Result.ok();
  }
}
