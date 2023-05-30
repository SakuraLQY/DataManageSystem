package org.jeecg.modules.advert.controller;

import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.advert.entity.OpChannelSubAccount;
import org.jeecg.modules.advert.modal.ChannelModal;
import org.jeecg.modules.advert.modal.ChannelObjModal;
import org.jeecg.modules.advert.service.IOpChannelSubAccountService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: op_channel_sub_account
 * @Author: jeecg-boot
 * @Date:   2023-01-06
 * @Version: V1.0
 */
@Api(tags="op_channel_sub_account")
@RestController
@RequestMapping("/advert/opChannelSubAccount")
@Slf4j
public class OpChannelSubAccountController extends JeecgController<OpChannelSubAccount, IOpChannelSubAccountService> {
	@Autowired
	private IOpChannelSubAccountService opChannelSubAccountService;

	/**
	 * 分页列表查询
	 *
	 * @param opChannelSubAccount
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "op_channel_sub_account-分页列表查询")
	@ApiOperation(value="op_channel_sub_account-分页列表查询", notes="op_channel_sub_account-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OpChannelSubAccount>> queryPageList(OpChannelSubAccount opChannelSubAccount,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OpChannelSubAccount> queryWrapper = QueryGenerator.initQueryWrapper(opChannelSubAccount, req.getParameterMap());
		Page<OpChannelSubAccount> page = new Page<OpChannelSubAccount>(pageNo, pageSize);
		IPage<OpChannelSubAccount> pageList = opChannelSubAccountService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 * @return org.jeecg.common.api.vo.Result<java.util.Map < java.lang.Integer, org.jeecg.modules.advert.modal.ChannelModal>>
	 * @Author lili
	 * @Description 三级联动下拉框
	 * @Date 17:10 2023/4/28
	 **/
	@ApiOperation(value="op_channel_sub_account-三级联动下拉框(单选)", notes="op_channel_sub_account-三级联动下拉框(单选)")
	@GetMapping(value = "/queryList")
	public Result<Map<Integer, ChannelModal>> queryList() {
		return Result.OK(opChannelSubAccountService.queryList());
	}

	@ApiOperation(value="op_channel_sub_account-三级联动下拉框(多选)", notes="op_channel_sub_account-三级联动下拉框(多选)")
	@GetMapping(value = "/thirdMuchList")
	public Result<Map<Integer, Map<Integer, ChannelObjModal>>> thirdMuchList() {
		return Result.OK(opChannelSubAccountService.getThirdOptionMuchList());
	}

	/**
	 *   添加
	 *
	 * @param opChannelSubAccount
	 * @return
	 */
	@AutoLog(value = "op_channel_sub_account-添加")
	@ApiOperation(value="op_channel_sub_account-添加", notes="op_channel_sub_account-添加")
	//@RequiresPermissions("advert:op_channel_sub_account:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OpChannelSubAccount opChannelSubAccount) {
		opChannelSubAccountService.save(opChannelSubAccount);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param opChannelSubAccount
	 * @return
	 */
	@AutoLog(value = "op_channel_sub_account-编辑")
	@ApiOperation(value="op_channel_sub_account-编辑", notes="op_channel_sub_account-编辑")
	//@RequiresPermissions("advert:op_channel_sub_account:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OpChannelSubAccount opChannelSubAccount) {
		opChannelSubAccountService.updateById(opChannelSubAccount);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "op_channel_sub_account-通过id删除")
	@ApiOperation(value="op_channel_sub_account-通过id删除", notes="op_channel_sub_account-通过id删除")
	//@RequiresPermissions("advert:op_channel_sub_account:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		opChannelSubAccountService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "op_channel_sub_account-批量删除")
	@ApiOperation(value="op_channel_sub_account-批量删除", notes="op_channel_sub_account-批量删除")
	//@RequiresPermissions("advert:op_channel_sub_account:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.opChannelSubAccountService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "op_channel_sub_account-通过id查询")
	@ApiOperation(value="op_channel_sub_account-通过id查询", notes="op_channel_sub_account-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OpChannelSubAccount> queryById(@RequestParam(name="id",required=true) String id) {
		OpChannelSubAccount opChannelSubAccount = opChannelSubAccountService.getById(id);
		if(opChannelSubAccount==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(opChannelSubAccount);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param opChannelSubAccount
    */
    //@RequiresPermissions("advert:op_channel_sub_account:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OpChannelSubAccount opChannelSubAccount) {
        return super.exportXls(request, opChannelSubAccount, OpChannelSubAccount.class, "op_channel_sub_account");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("advert:op_channel_sub_account:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OpChannelSubAccount.class);
    }

}
