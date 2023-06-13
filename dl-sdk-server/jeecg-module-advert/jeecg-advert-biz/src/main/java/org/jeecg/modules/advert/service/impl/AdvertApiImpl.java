package org.jeecg.modules.advert.service.impl;

import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.advert.dto.OpCostDto;
import org.jeecg.common.advert.vo.OpChannelSubAccountModel;
import org.apache.commons.io.IOCase;
import org.jeecg.common.advert.vo.OpChannelModel;
import org.jeecg.common.advert.vo.OpChannelTypeModel;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.advert.vo.OpPutAccountModel;
import org.jeecg.common.count.vo.CostModel;
import org.jeecg.common.count.vo.CostMoneyModel;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.modules.advert.entity.AtVisit;
import org.jeecg.modules.advert.entity.OpChannelSubAccount;
import org.jeecg.modules.advert.entity.OpChannel;
import org.jeecg.modules.advert.entity.OpChannelType;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpPutAccount;
import org.jeecg.modules.advert.service.IAtUniqueService;
import org.jeecg.modules.advert.service.IAtVisitService;
import org.jeecg.modules.advert.service.IOpChannelSubAccountService;
import org.jeecg.modules.advert.service.IOpChannelTypeService;
import org.jeecg.modules.advert.service.IOpCostService;
import org.jeecg.modules.advert.service.IOpChannelService;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IOpPutAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Author lili
 * @Description //广告Api实现类
 **/
@Service
@Slf4j
public class AdvertApiImpl implements IAdvertApi {

    @Resource
    private IOpDealService opDealService;
    @Resource
    private IOpChannelService opChannelService;
    @Resource
    private IOpChannelTypeService opChannelTypeService;
    @Resource
    private IAtUniqueService atUniqueService;
    @Resource
    private IAtVisitService atVisitService;
    @Resource
    private IOpCostService opCostService;
    @Resource
    private IOpPutAccountService opPutAccountService;
    @Resource
    private IOpChannelSubAccountService opChannelSubAccountService;

    @Override
    public OpDealModel getOpDeal(Integer id) {
        OpDeal opDeal = opDealService.getById(id);
        OpDealModel opDealModel = new OpDealModel();
        if (null == opDeal) {
            return opDealModel;
        }
        BeanUtils.copyProperties(opDeal, opDealModel);
        return opDealModel;
    }

    @Override
    public OpChannelModel getOpChannel(Integer id) {
        OpChannel opChannel = opChannelService.getById(id);
        if (null == opChannel) {
            return null;
        }
        OpChannelModel opChannelModel = new OpChannelModel();
        BeanUtils.copyProperties(opChannel, opChannelModel);
        return opChannelModel;
    }

    @Override
    public OpChannelTypeModel getOpChannelType(Integer id) {
        OpChannelType opChannelType = opChannelTypeService.getById(id);
        if (null == opChannelType) {
            return null;
        }
        OpChannelTypeModel opChannelTypeModel = new OpChannelTypeModel();
        BeanUtils.copyProperties(opChannelType, opChannelTypeModel);
        return opChannelTypeModel;
    }

    @Override
    public String getUnique(Integer dealId, String uniqueId, String deviceId,
        String serialId, String androidId, String clientIp) {
        return atUniqueService.getUniqueVisit(dealId, uniqueId, deviceId, serialId,
            androidId, clientIp);
    }

    @Override
    public Integer getDealIdByVisit(Integer pkgId, String idfa, String ip) {
        AtVisit atVisit = atVisitService.getVisitByIdfaOrIp(pkgId, idfa, ip);
        if (atVisit == null) {
            // 返回广告id默认为0
            return 0;
        } else {
            return atVisit.getDealId();
        }
    }

    @Override
    public List<CostMoneyModel> getCostMoney(OpCostDto opCostDto) {
        return opCostService.getSummaryCost(opCostDto);
    }

    @Override
    public OpPutAccountModel getOpPutAccount(Integer accountId) {
        OpPutAccountModel result = new OpPutAccountModel();
        OpPutAccount opPutAccount =  opPutAccountService.getById(accountId);
        if(opPutAccount != null){
            BeanUtils.copyProperties(opPutAccount, result);
        }
        return result;
    }

    @Override
    public OpChannelSubAccountModel getOpChannelSubAccount(Integer channelSubAccountId) {
        OpChannelSubAccountModel result = new OpChannelSubAccountModel();
        OpChannelSubAccount opChannelSubAccount =  opChannelSubAccountService.getById(channelSubAccountId);
        if(opChannelSubAccount != null){
            BeanUtils.copyProperties(opChannelSubAccount, result);
        }
        return result;
    }

    @Override
    public List<CostModel> getCostModel(OpCostDto opCostDto) {
        return opCostService.getCostModel(opCostDto);
    }

}
