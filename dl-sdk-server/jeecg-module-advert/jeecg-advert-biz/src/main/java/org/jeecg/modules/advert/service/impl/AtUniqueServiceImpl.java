package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.advert.constant.EmptyVisitConstant;
import org.jeecg.modules.advert.dto.LogDeviceCallbackDataDto;
import org.jeecg.modules.advert.entity.AtUnique;
import org.jeecg.modules.advert.entity.AtVisit;
import org.jeecg.modules.advert.mapper.AtUniqueMapper;
import org.jeecg.modules.advert.service.IAtUniqueService;
import org.jeecg.modules.advert.service.IAtVisitService;
import org.jeecg.modules.advert.vo.LogDeviceCallbackDataVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: at_unique
 * @Author: jeecg-boot
 * @Date: 2023-04-24
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("sharding")
public class AtUniqueServiceImpl extends ServiceImpl<AtUniqueMapper, AtUnique> implements
    IAtUniqueService {

    @Autowired
    private IAtVisitService atVisitService;

    @Autowired
    private AtUniqueMapper atUniqueMapper;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Override
    public String getUniqueVisit(Integer dealId, String uniqueId, String deviceId,
        String serialId, String androidId, String clientIp) {
        AtUnique atUnique = getOne(
            new LambdaQueryWrapper<AtUnique>().eq(AtUnique::getUniqueId, uniqueId)
                .eq(AtUnique::getDealId, dealId));
        if (atUnique != null) {
            // 如果有唯一归因 直接返回
            return atUnique.getVisitData();
        }
        TreeSet<String> treeSet = new TreeSet();
        // 第一设备查询
        if (StringUtils.isNotEmpty(uniqueId) && !EmptyVisitConstant.EMPTY_DEVICE.equals(uniqueId)) {
            treeSet.add(uniqueId);
        }
        // 第二设备查询
        if (StringUtils.isNotEmpty(deviceId) && !EmptyVisitConstant.EMPTY_DEVICE.equals(deviceId)) {
            treeSet.add(deviceId);
        }
        // 第三设备查询
        if (StringUtils.isNotEmpty(serialId) && !EmptyVisitConstant.EMPTY_DEVICE.equals(serialId)) {
            treeSet.add(serialId);
        }
        // 第四设备查询
        if (StringUtils.isNotEmpty(androidId) && !EmptyVisitConstant.EMPTY_DEVICE.equals(
            androidId)) {
            treeSet.add(androidId);
        }
        // IP查询
        if (StringUtils.isNotEmpty(clientIp) && !EmptyVisitConstant.EMPTY_DEVICE.equals(clientIp)) {
            treeSet.add(clientIp);
        }
        for (String device : treeSet) {
            AtVisit atVisit = atVisitService.getVisit(device, dealId);
            if (atVisit != null) {
                // 设置唯一归因
                atUnique = new AtUnique();
                atUnique.setUniqueId(uniqueId);
                atUnique.setDealId(dealId);
                atUnique.setVisitData(atVisit.getVisitData());
                save(atUnique);
                return atVisit.getVisitData();
            }
        }
        return null;
    }

    @Override
    public IPage getDeviceCallbackData(Page page, LogDeviceCallbackDataDto logDeviceCallbackDataDto) {
        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(logDeviceCallbackDataDto.getUniqueId())){
            queryWrapper.eq("unique_id",logDeviceCallbackDataDto.getUniqueId());
            AtUnique atUnique = baseMapper.selectOne(queryWrapper);
            List<AtUnique> arrayList = new ArrayList<>();
            arrayList.add(atUnique);
            return page.setRecords(arrayList);
        }

        if (ObjectUtils.isNotEmpty(logDeviceCallbackDataDto.getDealId())){
            queryWrapper.eq("au.deal_id",logDeviceCallbackDataDto.getDealId());
        }

        if (ObjectUtils.isNotEmpty(logDeviceCallbackDataDto.getStartCreateTime())){
            queryWrapper.ge("au.create_time",logDeviceCallbackDataDto.getStartCreateTime());
        }

        if (ObjectUtils.isNotEmpty(logDeviceCallbackDataDto.getEndCreateTime())) {
            queryWrapper.le("au.create_time", logDeviceCallbackDataDto.getEndCreateTime());
        }

        List<LogDeviceCallbackDataVo> deviceCallbackDatas = atUniqueMapper
            .getDeviceCallbackData(logDeviceCallbackDataDto.getTableName(), page, queryWrapper);


        return page.setRecords(deviceCallbackDatas);
    }

    @Override
    public ModelAndView deviceCallbackDataExportXls(HttpServletRequest request, LogDeviceCallbackDataDto logDeviceCallbackDataDto, String title) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer pageNo=1;
        Integer pageSize=1000;
        Page<LogDeviceCallbackDataVo> objectPage = new Page<LogDeviceCallbackDataVo>(pageNo, pageSize);
        IPage iPage = this.getDeviceCallbackData(objectPage, logDeviceCallbackDataDto);

        ArrayList<Object> arrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(iPage.getRecords())){

            arrayList.addAll(iPage.getRecords());

            while (true){
                pageNo++;
                objectPage.setCurrent(pageNo);

                IPage newResult = this.getDeviceCallbackData(objectPage, logDeviceCallbackDataDto);
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
        mv.addObject(NormalExcelConstants.CLASS, LogDeviceCallbackDataVo.class);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, arrayList);
        return mv;
    }
}
