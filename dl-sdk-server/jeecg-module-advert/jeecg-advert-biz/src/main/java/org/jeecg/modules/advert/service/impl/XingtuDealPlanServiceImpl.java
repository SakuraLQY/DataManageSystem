package org.jeecg.modules.advert.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.util.Json;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.xml.stream.events.EndDocument;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.SubGameTypeConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.xingtu.plan.api.XingtuPlanApi;
import org.jeecg.modules.advert.api.xingtu.plan.bo.Audience;
import org.jeecg.modules.advert.api.xingtu.plan.bo.CreatePlanRsp;
import org.jeecg.modules.advert.api.xingtu.plan.bo.DeliveryRange;
import org.jeecg.modules.advert.api.xingtu.plan.bo.DeliverySearch;
import org.jeecg.modules.advert.api.xingtu.plan.bo.DeliverySetting;
import org.jeecg.modules.advert.api.xingtu.plan.bo.EditPlanRsp;
import org.jeecg.modules.advert.api.xingtu.plan.bo.OptimizeGoal;
import org.jeecg.modules.advert.api.xingtu.plan.bo.XingCreatePlanParams;
import org.jeecg.modules.advert.api.xingtu.plan.bo.XingtuEditPlanParams;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.entity.OpAnchorPlan;
import org.jeecg.modules.advert.entity.OpXingtuDropDeal;
import org.jeecg.modules.advert.entity.XingtuDealPlan;
import org.jeecg.modules.advert.mapper.OpAnchorPlanMapper;
import org.jeecg.modules.advert.mapper.OpXingtuDropDealMapper;
import org.jeecg.modules.advert.mapper.XingtuDealPlanMapper;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.service.IXingtuDealPlanService;
import org.jeecg.modules.advert.vo.OpXingtuDealVo;
import org.jeecg.modules.advert.vo.XingtuDealPlanVo;
import org.jeecg.modules.advert.vo.XingtuPlanRspVo;
import org.jeecg.modules.advert.vo.XingtuPlanVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_xingtu_deal_plan
 * @Author: jeecg-boot
 * @Date: 2023-03-08
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class XingtuDealPlanServiceImpl extends
    ServiceImpl<XingtuDealPlanMapper, XingtuDealPlan> implements
    IXingtuDealPlanService {

    @Autowired
    private OpXingtuDropDealMapper dropDealMapper;

    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;

    @Autowired
    private OpAnchorPlanMapper anchorPlanMapper;

    @Autowired
    private IGameApi gameApi;

    @Override
    public IPage<XingtuDealPlanVo> getByPage(IPage<XingtuDealPlan> page,
        XingtuDealPlan xingtuDealPlan) {
        LambdaQueryWrapper<XingtuDealPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(XingtuDealPlan::getDealId,xingtuDealPlan.getDealId());
        return baseMapper.dealPlanInfoPage(page, wrapper);
    }

    @Override
    public Result<XingtuPlanRspVo> batchCreatePlan(XingtuPlanVo batchCreateInfo) {
        Integer dealId = batchCreateInfo.getDealId();
        OpXingtuDropDeal deal = dropDealMapper.selectById(dealId);
        JrttTokenBo accountToken = opJrttPutAccountService
            .getAccountToken(deal.getAccountId());
        XingtuDealPlan templatePlan = batchCreateInfo.getXingtuDealPlan();
        OpAnchorPlan opAnchorPlan = anchorPlanMapper.selectById(deal.getAnchorPlanId());
        XingCreatePlanParams createPlanParams = createParams(opAnchorPlan,deal, accountToken,batchCreateInfo.getXingtuDealPlan());
        JrttBaseResponse<CreatePlanRsp> response = XingtuPlanApi
            .createPlan(accountToken.getAccessToken(), createPlanParams);
        if (response.getCode().equals(JrttCodeConstant.OK)){
            XingtuDealPlan xingtuDealPlan = new XingtuDealPlan();
            BeanUtils.copyProperties(templatePlan, xingtuDealPlan);
            xingtuDealPlan.setAdId(response.getData().getAdId());
            xingtuDealPlan.setDealId(dealId);
            xingtuDealPlan.setAdvertiserId(accountToken.getAdvertiserId());
            xingtuDealPlan.setAwemeAccount(opAnchorPlan.getTrillId().toString());
            xingtuDealPlan.setName(createPlanParams.getName());
            xingtuDealPlan.setCampaignId(deal.getCampaignId());
            save(xingtuDealPlan);
        }
        XingtuPlanRspVo xingtuPlanRspVo = new XingtuPlanRspVo();
        xingtuPlanRspVo.setCode(response.getCode());
        xingtuPlanRspVo.setMessage(response.getMessage());
        xingtuPlanRspVo.setDealId(deal.getId());
        xingtuPlanRspVo.setDealName(deal.getCampaignName());
        return Result.ok(xingtuPlanRspVo);
    }

    @Override
    public Result editPlan(Integer accountId, XingtuDealPlan xingtuDealPlan) {
        XingtuEditPlanParams editParams = createEditParams(xingtuDealPlan);
        JrttTokenBo accountToken = opJrttPutAccountService.getAccountToken(accountId);
        JrttBaseResponse<EditPlanRsp> response = XingtuPlanApi.editPlan(accountToken.getAccessToken(), editParams);
        if (!response.getCode().equals(JrttCodeConstant.OK)){
            throw new JeecgBootException(response.getMessage());
        }
        updateById(xingtuDealPlan);
        return Result.ok("修改成功");
    }

    private XingtuEditPlanParams createEditParams(XingtuDealPlan xingtuDealPlan) {
        XingtuEditPlanParams editPlanParams = new XingtuEditPlanParams();
        editPlanParams.setAdId(xingtuDealPlan.getAdId());
        editPlanParams.setAdvertiserId(xingtuDealPlan.getAdvertiserId());
        editPlanParams.setName(xingtuDealPlan.getName());

        //用户定向
        Audience audience = JSON.parseObject(xingtuDealPlan.getAudience(), Audience.class);
        BeanUtils.copyProperties(audience, editPlanParams);
        //排期与预算
        DeliverySetting deliverySetting = JSON
            .parseObject(xingtuDealPlan.getDeliverySetting(), DeliverySetting.class);
        BeanUtils.copyProperties(deliverySetting, editPlanParams);
        //搜索快投
        DeliverySearch deliverySearch = JSON
            .parseObject(xingtuDealPlan.getDeliverySearch(), DeliverySearch.class);
        BeanUtils.copyProperties(editPlanParams, deliverySearch);

        return editPlanParams;
    }

    private XingCreatePlanParams createParams(OpAnchorPlan opAnchorPlan, OpXingtuDropDeal deal, JrttTokenBo accountToken,
        XingtuDealPlan xingtuDealPlan) {
        XingCreatePlanParams xingCreatePlanParams = new XingCreatePlanParams();

        xingCreatePlanParams.setCampaignId(deal.getCampaignId());
        xingCreatePlanParams.setAdvertiserId(accountToken.getAdvertiserId());
        xingCreatePlanParams.setAwemeAccount(opAnchorPlan.getTrillId().toString());

        //投放范围
        DeliveryRange deliverRange = JSON.parseObject(xingtuDealPlan.getDeliverRange(), DeliveryRange.class);
        BeanUtils.copyProperties(deliverRange,xingCreatePlanParams);
        //优化目标
        OptimizeGoal optimizeGoal = JSON.parseObject(xingtuDealPlan.getOptimizeGoal(), OptimizeGoal.class);
        BeanUtils.copyProperties(optimizeGoal,xingCreatePlanParams);
        //搜索快投
        DeliverySearch deliverySearch = JSON.parseObject(xingtuDealPlan.getDeliverySearch(), DeliverySearch.class);
        BeanUtils.copyProperties(deliverySearch,xingCreatePlanParams);
        //用户定向
        Audience audience = JSON.parseObject(xingtuDealPlan.getAudience(), Audience.class);
        List<String> platform;
        if (deal.getSubGameType().equals(SubGameTypeConstant.IOS)){
            platform = Collections.singletonList("IOS");
        }else {
            platform = Collections.singletonList("ANDROID");
        }
        audience.setPlatform(platform);
        BeanUtils.copyProperties(audience,xingCreatePlanParams);
        //预算和出价
        DeliverySetting deliverySetting = JSON.parseObject(xingtuDealPlan.getDeliverySetting(), DeliverySetting.class);
        BeanUtils.copyProperties(deliverySetting,xingCreatePlanParams);
        //监测链接
        xingCreatePlanParams.setTrackUrlGroupType("CUSTOM");
        xingCreatePlanParams.setActionTrackUrl(Collections.singletonList(deal.getDealArgs()));

        OpPkgModel pkg = gameApi.getOpPkgById(deal.getPkgId());

        xingCreatePlanParams.setDownLoadUrl(deal.getDownloadUrl());
        xingCreatePlanParams.setPkg(pkg.getPackageName());

        if (deal.getSubGameType().equals(SubGameTypeConstant.IOS)){
            xingCreatePlanParams.setAppType("APP_IOS");
        }else{
            xingCreatePlanParams.setAppType("APP_ANDROID");
        }
        xingCreatePlanParams.setName(deal.getCampaignName() + "-"+ xingtuDealPlan.getName() + "-" + new Date());
        return xingCreatePlanParams;
    }
}
