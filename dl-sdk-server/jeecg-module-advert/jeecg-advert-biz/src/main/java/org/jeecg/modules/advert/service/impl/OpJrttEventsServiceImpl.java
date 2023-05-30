package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.assets.api.JrttAssetApi;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttCreatEventsRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttCreatTrackUrlGroupsRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttCreatTrackUrlRequest;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetEventsDataResponse;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetEventsEventConfigsResponse;
import org.jeecg.modules.advert.api.jrtt.assets.bo.JrttGetEventsRequest;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttEventTrackTypes;
import org.jeecg.modules.advert.constant.jrtt.JrttExternalActionConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttOtherConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttTrackUrl;
import org.jeecg.modules.advert.dto.OpJrttAssetsDto;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttAssets;
import org.jeecg.modules.advert.mapper.OpJrttAssetsMapper;
import org.jeecg.modules.advert.mapper.OpJrttEventsMapper;
import org.jeecg.modules.advert.service.IOpJrttEventsService;
import org.jeecg.modules.advert.entity.OpJrttEvents;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.vo.EventExternalActionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.CollectionUtils;

/**
 * @Description: op_jrtt_events
 * @Author: jeecg-boot
 * @Date: 2023-02-16
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
@Slf4j
public class OpJrttEventsServiceImpl extends
    ServiceImpl<OpJrttEventsMapper, OpJrttEvents> implements
    IOpJrttEventsService {

    @Autowired
    private IGameApi gameApi;
    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;
    @Autowired
    private OpJrttEventsMapper opJrttEventsMapper;
    @Autowired
    private OpJrttAssetsMapper opJrttAssetsMapper;

    @Override
    public void add(OpJrttEvents opJrttEvent) {
        //判断数据库中是否存在记录
        LambdaQueryWrapper<OpJrttEvents> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpJrttEvents::getAssetsId, opJrttEvent.getAssetsId());
        wrapper.eq(OpJrttEvents::getEventId, opJrttEvent.getEventId());
        if (oConvertUtils.isNotEmpty(opJrttEventsMapper.selectOne(wrapper))) {
            throw new JeecgBootException("事件已被创建或不支持创建");
        }
        OpJrttAssets opJrttAssets = opJrttAssetsMapper.selectById(opJrttEvent.getAssetsId());
        JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(
            opJrttAssets.getAccountId());
        Long advertiserId = jrttTokenBo.getAdvertiserId();
        JrttCreatEventsRequest jrttCreatEventsRequest = new JrttCreatEventsRequest();
        jrttCreatEventsRequest.setEventId(opJrttEvent.getEventId());
        jrttCreatEventsRequest.setAdvertiserId(advertiserId);
        jrttCreatEventsRequest.setAssetId(opJrttAssets.getAssetId());
        String value = JrttEventTrackTypes.getValue(opJrttEvent.getTrackType()).getValue();
        List<String> list = new ArrayList<>();
        list.add(value);
        jrttCreatEventsRequest.setTrackTypes(list);
        JrttBaseResponse jrttBaseResponse = JrttAssetApi.creatEvents(jrttTokenBo.getAccessToken(),
            jrttCreatEventsRequest);
        //code不为0
        if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
            //线上存在已有的资产
            if (JrttOtherConstant.JRTT_SAME_EVENTS.equals(jrttBaseResponse.getMessage())) {
                OpJrttAssetsDto opJrttAssetsDto = new OpJrttAssetsDto();
                opJrttAssetsDto.setAccountId(opJrttAssets.getAccountId());
                opJrttAssetsDto.setId(opJrttAssets.getId());
                opJrttAssetsDto.setAssetId(opJrttAssets.getAssetId());
                syncEvents(opJrttAssetsDto);
                throw new JeecgBootException("线上同包名的事件已存在，请不要重复创建，现已同步，请刷新");
            } else if (JrttOtherConstant.JRTT_TRACK_URL_EMPTY.equals(jrttBaseResponse.getMessage())) {
                //创建监测链路组
                JrttCreatTrackUrlRequest jrttCreatTrackUrlRequest = new JrttCreatTrackUrlRequest();
                jrttCreatTrackUrlRequest.setAdvertiserId(advertiserId);
                jrttCreatTrackUrlRequest.setAssetsId(opJrttAssets.getAssetId());
                OpPkgModel opPkgModel = gameApi.getOpPkgById(opJrttAssets.getPkgId());
                jrttCreatTrackUrlRequest.setDownloadUrl(opPkgModel.getDownloadUrl());
                JrttCreatTrackUrlGroupsRequest jrttCreatTrackUrlGroupsRequest = new JrttCreatTrackUrlGroupsRequest();
                jrttCreatTrackUrlGroupsRequest.setActionTrackUrl(JrttTrackUrl.actionTrackUrl);
                jrttCreatTrackUrlGroupsRequest.setTrackUrlGroupName(JrttTrackUrl.trackUrlGroupName);
                List<JrttCreatTrackUrlGroupsRequest> jrttCreatTrackUrlGroupsRequestList = new ArrayList<>();
                jrttCreatTrackUrlGroupsRequestList.add(jrttCreatTrackUrlGroupsRequest);
                jrttCreatTrackUrlRequest.setTrackUrlGroups(jrttCreatTrackUrlGroupsRequestList);
                JrttAssetApi.creatTrackUrl(jrttTokenBo.getAccessToken(), jrttCreatTrackUrlRequest);
                add(opJrttEvent);
            } else {
                throw new JeecgBootException(jrttBaseResponse.getMessage());
            }
        }
        save(opJrttEvent);
    }

    @Override
    public List<EventExternalActionVo> getExternalAction(OpDeal opDeal) {
        LambdaQueryWrapper<OpJrttAssets> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpJrttAssets::getAccountId, opDeal.getAccountId());
        wrapper.eq(OpJrttAssets::getPkgId, opDeal.getPkgId());
        OpJrttAssets opJrttAssets = opJrttAssetsMapper.selectOne(wrapper);
        List<Long> assetIds = new ArrayList<>();
        EventExternalActionVo eventExternalActionVo = new EventExternalActionVo();
        List<EventExternalActionVo> resList = new ArrayList<>();
        if (oConvertUtils.isNotEmpty(opJrttAssets)) {
            LambdaQueryWrapper<OpJrttEvents> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(OpJrttEvents::getAssetsId, opJrttAssets.getId());
            assetIds.add(opJrttAssets.getAssetId());
            List<OpJrttEvents> list = opJrttEventsMapper.selectList(wrapper2);
            if (list != null && !list.isEmpty()) {
                for (OpJrttEvents opJrttEvents : list) {
                    eventExternalActionVo = new EventExternalActionVo();
                    JrttExternalActionConstant jrttExternalActionConstant = JrttExternalActionConstant.getByEventId(
                        opJrttEvents.getEventId());
                    eventExternalActionVo.setAssetIds(assetIds);
                    if (oConvertUtils.isNotEmpty(jrttExternalActionConstant)) {
                        eventExternalActionVo.setLabel(jrttExternalActionConstant.getChineseName());
                        eventExternalActionVo.setValue(jrttExternalActionConstant.getEnglishName());
                        resList.add(eventExternalActionVo);
                    }
                }
            }
        }
        eventExternalActionVo = new EventExternalActionVo();
        eventExternalActionVo.setAssetIds(assetIds);
        eventExternalActionVo.setLabel(JrttExternalActionConstant.AD_CONVERT_TYPE_CLICK_NUM.getChineseName());
        eventExternalActionVo.setValue(JrttExternalActionConstant.AD_CONVERT_TYPE_CLICK_NUM.getEnglishName());
        resList.add(eventExternalActionVo);
        eventExternalActionVo = new EventExternalActionVo();
        eventExternalActionVo.setAssetIds(assetIds);
        eventExternalActionVo.setLabel(JrttExternalActionConstant.AD_CONVERT_TYPE_SHOW_OFF_NUM.getChineseName());
        eventExternalActionVo.setValue(JrttExternalActionConstant.AD_CONVERT_TYPE_SHOW_OFF_NUM.getEnglishName());
        resList.add(eventExternalActionVo);
        if (opDeal.getSubGameType() == 0) {
            eventExternalActionVo = new EventExternalActionVo();
            eventExternalActionVo.setAssetIds(assetIds);
            eventExternalActionVo.setLabel(JrttExternalActionConstant.AD_CONVERT_TYPE_DOWNLOAD_FINISH.getChineseName());
            eventExternalActionVo.setValue(JrttExternalActionConstant.AD_CONVERT_TYPE_DOWNLOAD_FINISH.getEnglishName());
            resList.add(eventExternalActionVo);
            eventExternalActionVo = new EventExternalActionVo();
            eventExternalActionVo.setAssetIds(assetIds);
            eventExternalActionVo.setLabel(JrttExternalActionConstant.AD_CONVERT_TYPE_INSTALL_FINISH.getChineseName());
            eventExternalActionVo.setValue(JrttExternalActionConstant.AD_CONVERT_TYPE_INSTALL_FINISH.getEnglishName());
            resList.add(eventExternalActionVo);
        }
        return resList;
    }

    @Override
    public void syncEvents(OpJrttAssetsDto opJrttAssets) {
        JrttTokenBo jrttTokenBo = opJrttPutAccountService.getAccountToken(
            opJrttAssets.getAccountId());
        Long advertiserId = jrttTokenBo.getAdvertiserId();
        JrttGetEventsRequest jrttGetEventsRequest = new JrttGetEventsRequest();
        jrttGetEventsRequest.setAdvertiserId(advertiserId);
        jrttGetEventsRequest.setAssetId(opJrttAssets.getAssetId());
        JrttGetEventsDataResponse jrttGetEventsDataResponse = JrttAssetApi.getEvents(
            jrttTokenBo.getAccessToken(),
            jrttGetEventsRequest);
        LambdaQueryWrapper<OpJrttEvents> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpJrttEvents::getAssetsId, opJrttAssets.getId());
        List<OpJrttEvents> currentEventsList = opJrttEventsMapper.selectList(wrapper);
        List<Integer> eventList = currentEventsList.stream().map(OpJrttEvents::getEventId)
            .collect(
                Collectors.toList());
        List<Integer> eventIdList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jrttGetEventsDataResponse.getEventConfigs())) {
            for (JrttGetEventsEventConfigsResponse jrttGetEventsEventConfigsResponse : jrttGetEventsDataResponse.getEventConfigs()) {
                OpJrttEvents opJrttEvents = new OpJrttEvents();
                opJrttEvents.setEventId(jrttGetEventsEventConfigsResponse.getEventId());
                opJrttEvents.setAssetsId(opJrttAssets.getId());
                if (jrttGetEventsEventConfigsResponse.getTrackTypes().get(0).contains("API")) {
                    opJrttEvents.setTrackType(0);
                } else {
                    opJrttEvents.setTrackType(1);
                }
                LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
                opJrttEvents.setCreateBy(sysUser.getUsername());
                opJrttEvents.setCreateTime(new Date());
                if (!eventList.contains(jrttGetEventsEventConfigsResponse.getEventId())) {
                    eventIdList.add(opJrttEvents.getEventId());
                    opJrttEventsMapper.insert(opJrttEvents);
                }else {
                    LambdaQueryWrapper<OpJrttEvents> wrapper3 = new LambdaQueryWrapper<>();
                    wrapper3.eq(OpJrttEvents::getAssetsId, opJrttAssets.getId());
                    wrapper3.eq(OpJrttEvents::getEventId, opJrttEvents.getEventId());
                    opJrttEvents.setId(opJrttEventsMapper.selectOne(wrapper3).getId());
                    eventIdList.add(jrttGetEventsEventConfigsResponse.getEventId());
                    opJrttEventsMapper.updateById(opJrttEvents);
                }
            }
        }
        //删除本地有远程没有的数据
        for (Integer eventId : eventList) {
            if (!eventIdList.contains(eventId)) {
                LambdaQueryWrapper<OpJrttEvents> wrapper2 = new LambdaQueryWrapper<>();
                wrapper2.eq(OpJrttEvents::getEventId, eventId);
                wrapper2.eq(OpJrttEvents::getAssetsId, opJrttAssets.getId());
                opJrttEventsMapper.deleteById(
                    opJrttEventsMapper.selectOne(wrapper2).getId());
            }
        }

    }
}
