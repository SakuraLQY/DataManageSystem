package org.jeecg.modules.advert.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jeecg.modules.advert.entity.OpChannel;
import org.jeecg.modules.advert.entity.OpChannelType;
import org.jeecg.modules.advert.modal.ChannelModal;
import org.jeecg.modules.advert.modal.ChannelObjModal;
import org.jeecg.modules.advert.service.IOpChannelService;
import org.jeecg.modules.advert.service.IOpChannelSubAccountService;
import org.jeecg.modules.advert.entity.OpChannelSubAccount;
import org.jeecg.modules.advert.mapper.OpChannelSubAccountMapper;
import org.jeecg.modules.advert.service.IOpChannelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_channel_sub_account
 * @Author: jeecg-boot
 * @Date:   2023-01-06
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpChannelSubAccountServiceImpl extends ServiceImpl<OpChannelSubAccountMapper, OpChannelSubAccount> implements
    IOpChannelSubAccountService {

    @Autowired
    private IOpChannelTypeService opChannelTypeService;
    @Autowired
    private IOpChannelService opChannelService;

    @Override
    public Map<Integer, ChannelModal> queryList() {
        //渠道类型
        List<OpChannelType> opChannelTypeList = opChannelTypeService.list();
        //渠道
        List<OpChannel> opChannelList = opChannelService.list();
        //渠道子账号
        List<OpChannelSubAccount> opChannelSubAccountList = list();
        Map<Integer, Map<Integer, List<OpChannelSubAccount>>> opChannelSubAccountCollect = new HashMap<>();

        Map<Integer, ChannelModal> resCollect = new HashMap<>();
        if (null != opChannelSubAccountList && !opChannelSubAccountList.isEmpty()) {
            opChannelSubAccountCollect = opChannelSubAccountList.stream()
                .collect(Collectors.groupingBy(OpChannelSubAccount::getChannelTypeId,
                    Collectors.groupingBy(OpChannelSubAccount::getChannelId)));
            for (OpChannelType opChannelType : opChannelTypeList) {
                ChannelModal channelModal = new ChannelModal();
                channelModal.setId(opChannelType.getId());
                channelModal.setName(opChannelType.getTypeName());
                Map<Integer, ChannelObjModal> channelCollect = new HashMap<>();
                for (OpChannel opChannel : opChannelList) {
                    if (Objects.equals(opChannel.getTypeId(), opChannelType.getId())) {
                        ChannelObjModal channelObjModal = new ChannelObjModal();
                        channelObjModal.setId(opChannel.getId());
                        channelObjModal.setName(opChannel.getChannelName());
                        if (opChannelSubAccountCollect.get(opChannelType.getId()).containsKey(opChannel.getId())) {
                            List<ChannelObjModal> channelList = new ArrayList<>();
                            for (OpChannelSubAccount osg : opChannelSubAccountCollect.get(opChannelType.getId()).get(opChannel.getId())) {
                                ChannelObjModal gv = new ChannelObjModal();
                                gv.setId(osg.getId());
                                gv.setName(osg.getSubAccountName());
                                channelList.add(gv);
                            }
                            channelObjModal.setList(channelList);
                        }
                        channelCollect.put(opChannel.getId(), channelObjModal);
                    }
                }
                channelModal.setMap(channelCollect);
                resCollect.put(opChannelType.getId(), channelModal);
            }
        }
        return resCollect;
    }

    /**
     * @return java.util.Map<java.lang.Integer, org.jeecg.modules.advert.modal.ChannelObjModal>
     * @Author lili
     * @Description 渠道 + 类型
     * @Date 20:04 2023/5/8
     **/
    private Map<Integer, ChannelObjModal> getChannelAndTypeList() {
        //渠道类型
        List<OpChannelType> opChannelTypeList = opChannelTypeService.list();
        Map<Integer, String> opChannelTypeMap = new HashMap<>();
        if (null == opChannelTypeList || opChannelTypeList.isEmpty()) {
            return null;
        }
        for (OpChannelType opChannelType : opChannelTypeList) {
            opChannelTypeMap.put(opChannelType.getId(), opChannelType.getTypeName());
        }
        //渠道
        List<OpChannel> opChannelList = opChannelService.list();
        Map<Integer, ChannelObjModal> resMap = new HashMap<>();
        Map<Integer, List<OpChannel>> opChannelCollect = new HashMap<>();
        if (null != opChannelList && !opChannelList.isEmpty()) {
            opChannelCollect = opChannelList.stream()
                .collect(Collectors.groupingBy(OpChannel::getTypeId));
            for (Integer key2 : opChannelTypeMap.keySet()) {
                ChannelObjModal channelObjModal = new ChannelObjModal();
                channelObjModal.setId(key2);
                channelObjModal.setName(opChannelTypeMap.get(key2));
                if (opChannelCollect.containsKey(key2)) {
                    List<ChannelObjModal> list = new ArrayList<>();
                    for (OpChannel opChannel : opChannelCollect.get(key2)) {
                        ChannelObjModal g2 = new ChannelObjModal();
                        g2.setId(opChannel.getId());
                        g2.setName(opChannel.getChannelName());
                        list.add(g2);
                    }
                    channelObjModal.setList(list);
                }
                resMap.put(key2, channelObjModal);
            }

        }
        return resMap;
    }

    /**
     * @return java.util.Map<java.lang.Integer, org.jeecg.modules.advert.modal.ChannelObjModal>
     * @Author lili
     * @Description 渠道 + 渠道子账号
     * @Date 20:12 2023/5/8
     **/
    private Map<Integer, ChannelObjModal> getChannelAndSubAccountList() {
        //渠道
        List<OpChannel> opChannelList = opChannelService.list();
        Map<Integer, String> opChannelMap = new HashMap<>();
        if (null == opChannelList || opChannelList.isEmpty()) {
            return null;
        }
        for (OpChannel opChannel : opChannelList) {
            opChannelMap.put(opChannel.getId(), opChannel.getChannelName());
        }
        //渠道子账号
        List<OpChannelSubAccount> opChannelSubAccountList = list();
        Map<Integer, ChannelObjModal> resMap = new HashMap<>();
        Map<Integer, List<OpChannelSubAccount>> opChannelSubAccountCollect = new HashMap<>();
        if (null != opChannelSubAccountList && !opChannelSubAccountList.isEmpty()) {
            opChannelSubAccountCollect = opChannelSubAccountList.stream()
                .collect(Collectors.groupingBy(OpChannelSubAccount::getChannelId));
            for (Integer key2 : opChannelMap.keySet()) {
                ChannelObjModal channelObjModal = new ChannelObjModal();
                channelObjModal.setId(key2);
                channelObjModal.setName(opChannelMap.get(key2));
                if (opChannelSubAccountCollect.containsKey(key2)) {
                    List<ChannelObjModal> list = new ArrayList<>();
                    for (OpChannelSubAccount opChannelSubAccount : opChannelSubAccountCollect.get(key2)) {
                        ChannelObjModal g2 = new ChannelObjModal();
                        g2.setId(opChannelSubAccount.getId());
                        g2.setName(opChannelSubAccount.getSubAccountName());
                        list.add(g2);
                    }
                    channelObjModal.setList(list);
                }
                resMap.put(key2, channelObjModal);
            }

        }
        return resMap;
    }

    @Override
    public Map<Integer, Map<Integer, ChannelObjModal>> getThirdOptionMuchList() {
        Map<Integer, Map<Integer, ChannelObjModal>> map = new LinkedHashMap<>();
        //渠道 + 类型
        map.put(1, getChannelAndTypeList());
        //渠道 + 渠道子账号
        map.put(2, getChannelAndSubAccountList());
        return map;
    }
}
