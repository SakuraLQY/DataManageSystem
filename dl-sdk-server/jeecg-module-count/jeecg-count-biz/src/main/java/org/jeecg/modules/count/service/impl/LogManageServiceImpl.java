package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.count.dto.LogDeviceDataDto;
import org.jeecg.modules.count.mapper.LogManageMapper;
import org.jeecg.modules.count.service.ILogManageService;
import org.jeecg.modules.count.vo.LogDeviceDataVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.service.impl
 * @className: LogManageServiceImpl
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 10:03
 */
@Service
@DS("open_countly")
@Slf4j
public class LogManageServiceImpl implements ILogManageService {

    @Autowired
    private LogManageMapper logManageMapper;


    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Override
    public IPage getDeviceData(Page page,LogDeviceDataDto logDeviceDataDto){

        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getGameId())){
            queryWrapper.eq("cd.game_id",logDeviceDataDto.getGameId());
        }

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getSubGameId())){
            queryWrapper.eq("cd.sub_game_id",logDeviceDataDto.getSubGameId());
        }

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getPkgId())){
            queryWrapper.eq("cd.pkg_id",logDeviceDataDto.getPkgId());
        }

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getChannelTypeId())){
            queryWrapper.eq("cd.channel_type_id",logDeviceDataDto.getChannelTypeId());
        }

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getChannelId())){
            queryWrapper.eq("cd.channel_id",logDeviceDataDto.getChannelId());
        }

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getChannelSubAccountId())) {
            queryWrapper.eq("cd.channel_sub_account_id",
                logDeviceDataDto.getChannelSubAccountId());
        }

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getUniqueId())){
            queryWrapper.eq("cd.unique_id",logDeviceDataDto.getUniqueId());
        }

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getStartCreateTime())){
            queryWrapper.ge("cd.create_time",logDeviceDataDto.getStartCreateTime());
        }

        if (ObjectUtils.isNotEmpty(logDeviceDataDto.getEndCreateTime())) {
            queryWrapper.le("cd.create_time", logDeviceDataDto.getEndCreateTime());
        }
        queryWrapper.orderByAsc("cd.create_time");
        List<LogDeviceDataVo> deviceData = logManageMapper.getDeviceData(page,queryWrapper);

        return page.setRecords(deviceData);
    }

    /**
     * 设备数据导出excel
     *
     * @param request
     */
    @Override
    public ModelAndView deviceDataExportXls(HttpServletRequest request, LogDeviceDataDto logDeviceDataDto, String title) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer pageNo=1;
        Integer pageSize=1000;
        Page<LogDeviceDataVo> objectPage = new Page<LogDeviceDataVo>(pageNo, pageSize);
        IPage iPage = this.getDeviceData(objectPage, logDeviceDataDto);

        ArrayList<Object> arrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(iPage.getRecords())){

            arrayList.addAll(iPage.getRecords());

            while (true){
                pageNo++;
                objectPage.setCurrent(pageNo);

                IPage newResult = this.getDeviceData(objectPage, logDeviceDataDto);
                if (ObjectUtils.isEmpty(newResult.getRecords())){
                    break;
                }
                arrayList.addAll(newResult.getRecords());
            }
        }

        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //此处设置的filename无效 ,前端会重更新设置一下
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, LogDeviceDataVo.class);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, arrayList);
        return mv;
    }


}
