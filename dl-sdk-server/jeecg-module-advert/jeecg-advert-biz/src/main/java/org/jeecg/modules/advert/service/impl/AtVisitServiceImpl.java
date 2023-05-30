package org.jeecg.modules.advert.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.advert.dto.LogCallbackDataDto;
import org.jeecg.modules.advert.entity.AtVisit;
import org.jeecg.modules.advert.entity.OpChannel;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.mapper.AtVisitMapper;
import org.jeecg.modules.advert.service.IAtVisitService;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.vo.LogCallbackDataVo;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description: at_visit
 * @Author: jeecg-boot
 * @Date: 2023-02-14
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("sharding")
public class AtVisitServiceImpl extends ServiceImpl<AtVisitMapper, AtVisit> implements
    IAtVisitService {

    @Autowired
    private IOpDealService opDealService;

    @Autowired
    private OpChannelServiceImpl opChannelService;

    @Autowired
    private IGameApi iGameApi;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @Override
    public void saveVisit(String uniqueId, String uniqueType, Integer dealId,
        String visitData) {
        if (uniqueId == null || uniqueType == null
            || dealId == null || visitData == null) {
            log.error("找不到归因,归因参数不完整:uniqueId:{}, uniqueType:{}, dealId:{}", uniqueId, uniqueType,
                dealId);
            // 参数为空不执行后续步骤
            return;
        }
        OpDeal opDeal = opDealService.getById(dealId);
        if (opDeal == null) {
            log.error("找不到广告对应的广告记录dealId:{}", dealId);
            return;
        }
        // 根据广告id和设备信息 寻找归因
        LambdaQueryWrapper<AtVisit> lambdaQueryWrapper = new LambdaQueryWrapper<AtVisit>()
            .eq(AtVisit::getDealId, dealId)
            .eq(AtVisit::getUniqueId, uniqueId)
            .eq(AtVisit::getUniqueType, uniqueType);
        AtVisit atVisit = getOne(lambdaQueryWrapper);
        if (atVisit != null) {
            // 找到归因的话 更新update_time 代表最新点击该广告的时间
            AtVisit update = new AtVisit();
            update.setId(atVisit.getId());
            update.setUpdateTime(new Date());
            atVisit.setUpdateTime(new Date());
            update(update, new LambdaQueryWrapper<AtVisit>()
                .eq(AtVisit::getId, atVisit.getId())
                .eq(AtVisit::getUniqueId, atVisit.getUniqueId()));
            updateById(atVisit);
        } else {
            if (opDeal.getPkgId() == null || opDeal.getChannelId() == null) {
                log.error("找不到广告对应的游戏包dealId:{}", dealId);
                return;
            }
            // 找到归因的话 入库
            AtVisit insert = new AtVisit();
            insert.setPkgId(opDeal.getPkgId());
            insert.setDealId(dealId);
            insert.setChannelId(opDeal.getChannelId());
            insert.setUniqueType(uniqueType);
            insert.setUniqueId(uniqueId);
            insert.setVisitData(visitData);
            save(insert);
        }
    }

    @Override
    public AtVisit getVisit(String uniqueId, Integer dealId) {
        if (uniqueId == null || dealId == null) {
            log.error("找不到归因,归因参数不完整:uniqueId:{}, dealId:{}", uniqueId,
                dealId);
            // 参数为空不执行后续步骤
            return null;
        }
        // 根据广告id和设备信息 寻找归因
        AtVisit atVisit = getOne(new LambdaQueryWrapper<AtVisit>()
            .eq(AtVisit::getDealId, dealId)
            .eq(AtVisit::getUniqueId, uniqueId));
        return atVisit;
    }

    @Override
    public AtVisit getVisitByIdfaOrIp(Integer pkgId, String idfa, String ip) {
        if (pkgId == 0 || (StringUtils.isEmpty(idfa) && StringUtils.isEmpty(ip))) {
            log.error("获取归因参数不完整:pkgId:{}, idfa:{}, ip:{}", pkgId, idfa, ip);
            return null;
        }
        List<AtVisit> atVisitList = list(new LambdaQueryWrapper<AtVisit>().eq(AtVisit::getPkgId, pkgId)
            .eq(AtVisit::getUniqueId, idfa));
        if(CollectionUtil.isNotEmpty(atVisitList)){
            return atVisitList.get(0);
        }
        atVisitList = list(new LambdaQueryWrapper<AtVisit>().eq(AtVisit::getPkgId, pkgId)
            .eq(AtVisit::getUniqueId, ip));
        if(CollectionUtil.isNotEmpty(atVisitList)){
            return atVisitList.get(0);
        }
        return null;
    }

    @Override
    public IPage getCallbackData(Page page, LogCallbackDataDto logCallbackDataDto){

        QueryWrapper queryWrapper = new QueryWrapper<>();

        if (ObjectUtils.isNotEmpty(logCallbackDataDto.getUniqueId())){

            queryWrapper.eq("unique_id",logCallbackDataDto.getUniqueId());
            AtVisit atVisit = baseMapper.selectOne(queryWrapper);
            if (ObjectUtils.isEmpty(atVisit)){
                return null;
            }
            OpPkgModel opPkgById = iGameApi.getOpPkgById(atVisit.getPkgId());
            LogCallbackDataVo logCallbackDataVo = new LogCallbackDataVo();
            BeanUtils.copyProperties(atVisit,logCallbackDataVo);
            logCallbackDataVo.setPkgName(opPkgById.getPkgName());

            OpChannel opChannel = opChannelService.selectById(atVisit.getChannelId());
            logCallbackDataVo.setChannelName(opChannel.getChannelName());
            ArrayList<Object> arrayList = new ArrayList<>();
            arrayList.add(logCallbackDataVo);
            return page.setRecords(arrayList);
        }

        if (ObjectUtils.isNotEmpty(logCallbackDataDto.getPkgId())){
            queryWrapper.eq("av.pkg_id",logCallbackDataDto.getPkgId());
        }

        if (ObjectUtils.isNotEmpty(logCallbackDataDto.getDealId())){
            queryWrapper.eq("av.deal_id",logCallbackDataDto.getDealId());
        }

        if (ObjectUtils.isNotEmpty(logCallbackDataDto.getUniqueType())){
            queryWrapper.eq("av.unique_type",logCallbackDataDto.getUniqueType());
        }

        if (ObjectUtils.isNotEmpty(logCallbackDataDto.getChannelId())){
            queryWrapper.eq("av.channel_id",logCallbackDataDto.getChannelId());
        }

        if (ObjectUtils.isNotEmpty(logCallbackDataDto.getStartCreateTime())){
            queryWrapper.ge("av.create_time",logCallbackDataDto.getStartCreateTime());
        }

        if (ObjectUtils.isNotEmpty(logCallbackDataDto.getEndCreateTime())) {
            queryWrapper.le("av.create_time", logCallbackDataDto.getEndCreateTime());
        }

        queryWrapper.orderByAsc("av.create_time");
        IPage callback = opChannelService
            .getCallback(logCallbackDataDto.getTableName(), page, queryWrapper);

        return callback;
    }

    @Override
    public ModelAndView callbackDataExportXls(HttpServletRequest request, LogCallbackDataDto logCallbackDataDto, String title) {

        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        Integer pageNo=1;
        Integer pageSize=1000;
        Page<LogCallbackDataVo> objectPage = new Page<LogCallbackDataVo>(pageNo, pageSize);
        IPage iPage = this.getCallbackData(objectPage, logCallbackDataDto);

        ArrayList<Object> arrayList = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(iPage.getRecords())){

            arrayList.addAll(iPage.getRecords());

            while (true){
                pageNo++;
                objectPage.setCurrent(pageNo);

                IPage newResult = this.getCallbackData(objectPage, logCallbackDataDto);
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
        mv.addObject(NormalExcelConstants.CLASS, LogCallbackDataVo.class);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------
        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + sysUser.getRealname(), title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        mv.addObject(NormalExcelConstants.DATA_LIST, arrayList);
        return mv;
    }
}
