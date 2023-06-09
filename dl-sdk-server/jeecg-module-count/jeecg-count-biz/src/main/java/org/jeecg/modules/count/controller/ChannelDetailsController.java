package org.jeecg.modules.count.controller;

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
import org.jeecg.common.aspect.annotation.UserPermissionData;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.dto.ChannelDetailDto;
import org.jeecg.modules.count.dto.ReportAccountDto;
import org.jeecg.modules.count.entity.ChannelDetails;
import org.jeecg.modules.count.modal.ChannelDetailModal;
import org.jeecg.modules.count.modal.ReportAccountModal;
import org.jeecg.modules.count.service.IChannelDetailsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.count.vo.ChannelDetailVo;
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
 * @Description: 渠道明细表数据
 * @Author: jeecg-boot
 * @Date: 2023-05-11
 * @Version: V1.0
 */
@Api(tags = "渠道明细表数据")
@RestController
@RequestMapping("/count/channelDetails")
@Slf4j
public class ChannelDetailsController extends
    JeecgController<ChannelDetails, IChannelDetailsService> {

    @Autowired
    private IChannelDetailsService channelDetailsService;

    /**
     * 分页列表查询
     *
     * @param channelDetails
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "渠道明细表数据-分页列表查询")
    @ApiOperation(value = "渠道明细表数据-分页列表查询", notes = "渠道明细表数据-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ChannelDetails>> queryPageList(ChannelDetails channelDetails,
        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
        HttpServletRequest req) {
        QueryWrapper<ChannelDetails> queryWrapper = QueryGenerator.initQueryWrapper(channelDetails,
            req.getParameterMap());
        Page<ChannelDetails> page = new Page<ChannelDetails>(pageNo, pageSize);
        IPage<ChannelDetails> pageList = channelDetailsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * @param channelDetailDto
     * @author chenglin
     * @description
     * @date 19:23 2023/05/11
     **/
    @ApiOperation(value = "渠道明细表数据-列表查询", notes = "渠道明细表数据-列表查询")
    @GetMapping(value = "queryList")
    @UserPermissionData(alias = "a")
    public Result<List<ChannelDetailVo>> queryList(ChannelDetailDto channelDetailDto,
        HttpServletRequest req) {
        List<ChannelDetailVo> list = channelDetailsService.queryList(channelDetailDto);
        return Result.OK(list);
    }



    /**@param channelDetailDto
     * @author chenglin
     * @description 补充导出Excel功能
     * @date  14:52 2023/6/13
     **/
    @GetMapping(value = "/exportExcel")
    public ModelAndView exportExcel(HttpServletRequest request, ChannelDetailDto channelDetailDto) {
        return channelDetailsService.exportExcel(request,channelDetailDto, ChannelDetailModal.class,"渠道明细表");
    }
    /**
     * 添加
     *
     * @param channelDetails
     * @return
     */
    @AutoLog(value = "渠道明细表数据-添加")
    @ApiOperation(value = "渠道明细表数据-添加", notes = "渠道明细表数据-添加")
    //@RequiresPermissions("count:channel_details:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ChannelDetails channelDetails) {
        channelDetailsService.save(channelDetails);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param channelDetails
     * @return
     */
    @AutoLog(value = "渠道明细表数据-编辑")
    @ApiOperation(value = "渠道明细表数据-编辑", notes = "渠道明细表数据-编辑")
    //@RequiresPermissions("count:channel_details:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ChannelDetails channelDetails) {
        channelDetailsService.updateById(channelDetails);
        return Result.OK("编辑成功!");
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "渠道明细表数据-通过id删除")
    @ApiOperation(value = "渠道明细表数据-通过id删除", notes = "渠道明细表数据-通过id删除")
    //@RequiresPermissions("count:channel_details:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        channelDetailsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "渠道明细表数据-批量删除")
    @ApiOperation(value = "渠道明细表数据-批量删除", notes = "渠道明细表数据-批量删除")
    //@RequiresPermissions("count:channel_details:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.channelDetailsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "渠道明细表数据-通过id查询")
    @ApiOperation(value = "渠道明细表数据-通过id查询", notes = "渠道明细表数据-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ChannelDetails> queryById(@RequestParam(name = "id", required = true) String id) {
        ChannelDetails channelDetails = channelDetailsService.getById(id);
        if (channelDetails == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(channelDetails);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param channelDetails
     */
    //@RequiresPermissions("count:channel_details:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ChannelDetails channelDetails) {
        return super.exportXls(request, channelDetails, ChannelDetails.class, "渠道明细表数据");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("count:channel_details:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ChannelDetails.class);
    }

}
