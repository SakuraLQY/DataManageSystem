package org.jeecg.modules.advert.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttTokenBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.xingtu.campaign.api.XingtuCampaignApi;
import org.jeecg.modules.advert.api.xingtu.campaign.bo.XingtuCreateCampaignRequest;
import org.jeecg.modules.advert.api.xingtu.campaign.bo.XingtuCreateCampaignResponse;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.xingtu.CampaignConstant;
import org.jeecg.modules.advert.dto.OpXingtuDropDealDto;
import org.jeecg.modules.advert.entity.OpXingtuDropDeal;
import org.jeecg.modules.advert.mapper.OpXingtuDropDealMapper;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.service.IOpXingtuDropDealService;
import org.jeecg.modules.advert.vo.OpXingtuDropDealVo;
import org.jeecg.modules.advert.vo.MessageVo;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: op_xingtu_drop_deal
 * @Author: jeecg-boot
 * @Date: 2023-03-07
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_gateway")
public class OpXingtuDropDealServiceImpl extends
    ServiceImpl<OpXingtuDropDealMapper, OpXingtuDropDeal> implements IOpXingtuDropDealService {

    @Resource
    private IGameApi gameApi;
    @Resource
    private IOpJrttPutAccountService opJrttPutAccountService;

    @Override
    public IPage<OpXingtuDropDealVo> getByPage(Page<OpXingtuDropDealVo> page,
        OpXingtuDropDeal opXingtuDropDeal, String startDate, String endDate) {
        QueryWrapper<OpXingtuDropDeal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getGameId()), "oxd.game_id",
            opXingtuDropDeal.getGameId());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getSubGameId()),
            "oxd.sub_game_id", opXingtuDropDeal.getSubGameId());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getSubGameType()),
            "oxd.sub_game_type", opXingtuDropDeal.getSubGameType());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getAccountId()), "oxd.account_id",
            opXingtuDropDeal.getAccountId());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getAnchorPlanId()),
            "oxd.anchor_plan_id", opXingtuDropDeal.getAnchorPlanId());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getCampaignId()),
            "oxd.campaign_id", opXingtuDropDeal.getCampaignId());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getPkgId()),
            "oxd.pkg_id", opXingtuDropDeal.getPkgId());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getCreateBy()), "oxd.create_by",
            opXingtuDropDeal.getCreateBy());
        queryWrapper.eq(oConvertUtils.isNotEmpty(opXingtuDropDeal.getCampaignId()),
            "oxd.campaign_id", opXingtuDropDeal.getCampaignId());
        queryWrapper.like(oConvertUtils.isNotEmpty(opXingtuDropDeal.getCampaignName()),
            "oxd.campaign_name", opXingtuDropDeal.getCampaignName());
        queryWrapper.ge(oConvertUtils.isNotEmpty(startDate),
            "oxd.create_time",
            startDate);
        queryWrapper.le(oConvertUtils.isNotEmpty(endDate),
            "oxd.create_time",
            endDate);
        queryWrapper.orderByDesc("id");
        return baseMapper.dropDealInfoPage(page, queryWrapper);
    }

    @Override
    public List<MessageVo> addDropDeal(OpXingtuDropDealDto opXingtuDropDealDto) {
        // 返回数据
        List<MessageVo> list = new ArrayList<>();
        int dealNumbers = opXingtuDropDealDto.getDealNumbers();
        for (int i = 0; i < dealNumbers; i++) {
            String accounts = opXingtuDropDealDto.getAccountIds();
            String[] accountArr = accounts.split(",");
            OpXingtuDropDeal xingtuDropDeal = new OpXingtuDropDeal(opXingtuDropDealDto);
            // 子游戏类型
            OpSubGameModel subGameModel = gameApi.getOpSubGame(opXingtuDropDealDto.getSubGameId());
            int gameType = subGameModel.getGameType();
            xingtuDropDeal.setSubGameType(gameType);
            for (String accountStr : accountArr) {
                xingtuDropDeal.setId(null);
                // 投放账号
                Integer account = Integer.valueOf(accountStr);
                xingtuDropDeal.setAccountId(account);
                if (!save(xingtuDropDeal)) {
                    list.add(new MessageVo(null, "创建本地投放广告失败", 500));
                    continue;
                }
                // 更新部分字段
                int dropDealId = xingtuDropDeal.getId();
                UpdateWrapper<OpXingtuDropDeal> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", dropDealId);
                // 拼接广告名
                String newDealName =
                    opXingtuDropDealDto.getCampaignName() + "-" + dropDealId + "-" + account;
                // 检查是否重复
                OpXingtuDropDeal isRepeat = getOne(
                    new LambdaQueryWrapper<OpXingtuDropDeal>().eq(OpXingtuDropDeal::getCampaignName,
                        newDealName));
                if (oConvertUtils.isNotEmpty(isRepeat)) {
                    newDealName += "-" + DateUtil.format(new Date(), "MMdd_HH:mm:ss.SSS");
                }
                wrapper.set("campaign_name", newDealName);
                if (!update(wrapper)) {
                    list.add(new MessageVo(dropDealId, "更新本地投放广告失败", 500));
                    continue;
                }
                // 创建广告组
                MessageVo campaignVo = createCampaign(dropDealId, account, newDealName,
                    opXingtuDropDealDto);
                list.add(campaignVo);
            }
        }
        return list;
    }

    @Override
    @Transactional
    public void updateDropDeal(OpXingtuDropDealDto opXingtuDropDealDto) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        Integer dropDealId = opXingtuDropDealDto.getId();
        String campaignName = opXingtuDropDealDto.getCampaignName();
        Integer account = Integer.valueOf(opXingtuDropDealDto.getAccountIds());
        OpXingtuDropDeal xingtuDropDeal = getById(dropDealId);
        if (oConvertUtils.isEmpty(xingtuDropDeal)) {
            throw new JeecgBootException("该投放广告不存在");
        }
        // 子游戏类型
        OpSubGameModel subGameModel = gameApi.getOpSubGame(opXingtuDropDealDto.getSubGameId());
        int gameType = subGameModel.getGameType();

        LambdaUpdateWrapper<OpXingtuDropDeal> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(OpXingtuDropDeal::getCampaignName, campaignName);
        wrapper.set(OpXingtuDropDeal::getAnchorPlanId, opXingtuDropDealDto.getAnchorPlanId());
        wrapper.set(OpXingtuDropDeal::getGameId, opXingtuDropDealDto.getGameId());
        wrapper.set(OpXingtuDropDeal::getSubGameId, opXingtuDropDealDto.getSubGameId());
        wrapper.set(OpXingtuDropDeal::getSubGameType, gameType);
        wrapper.set(OpXingtuDropDeal::getAccountId, account);
        wrapper.set(OpXingtuDropDeal::getDealArgs, opXingtuDropDealDto.getDealArgs());
        wrapper.set(OpXingtuDropDeal::getPkgId, opXingtuDropDealDto.getPkgId());
        wrapper.set(OpXingtuDropDeal::getDealDesc, opXingtuDropDealDto.getDealDesc());
        wrapper.set(OpXingtuDropDeal::getDownloadUrl, opXingtuDropDealDto.getDownloadUrl());
        wrapper.set(OpXingtuDropDeal::getUpdateBy, sysUser.getUsername());
        wrapper.set(OpXingtuDropDeal::getUpdateTime, new Date());
        wrapper.eq(OpXingtuDropDeal::getId, dropDealId);
        if (!update(wrapper)) {
            throw new JeecgBootException("更新失败");
        }

        // 如果没有头条广告ID或者更换了投放账号，需要创建头条广告组
        if (oConvertUtils.isEmpty(xingtuDropDeal.getCampaignId()) || !xingtuDropDeal.getAccountId()
            .equals(account)) {
            MessageVo campaignVo = createCampaign(dropDealId, account,
                campaignName, opXingtuDropDealDto);
            if (campaignVo.getStatus() == 500) {
                throw new JeecgBootException(campaignVo.getMessage());
            }
        }
    }

    @Override
    public void makeUpDropDeal(OpXingtuDropDealDto opXingtuDropDealDto) {
        Integer dropDealId = opXingtuDropDealDto.getId();
        OpXingtuDropDeal dropDeal = getById(dropDealId);
        if (oConvertUtils.isEmpty(dropDeal)) {
            throw new JeecgBootException("选择的投放广告不存在！");
        }

        String campaignName = opXingtuDropDealDto.getCampaignName();
        // 补建广告组
        MessageVo campaignVo = createCampaign(dropDealId, dropDeal.getAccountId(),
            campaignName, opXingtuDropDealDto);
        if (campaignVo.getStatus() == 500) {
            throw new JeecgBootException(campaignVo.getMessage());
        }
    }

    /**
     * @param dropDealId:          投放广告ID
     * @param account:             投放账号
     * @param campaignName:        广告组名称
     * @param opXingtuDropDealDto: 广告信息
     * @return XingtuCampaignVo
     * @author xmh
     * @description 创建广告组
     * @date 2023/3/7 14:40
     */
    private MessageVo createCampaign(Integer dropDealId, Integer account,
        String campaignName, OpXingtuDropDealDto opXingtuDropDealDto) {
        JrttTokenBo tokenBo = opJrttPutAccountService.getAccountToken(account);
        // 预算类型
        String budgetMode = opXingtuDropDealDto.getBudgetMode();
        // 预算择优分配
        String campaignBudgetOptimization = opXingtuDropDealDto.getCampaignBudgetOptimization();

        XingtuCreateCampaignRequest request = new XingtuCreateCampaignRequest();
        request.setAdvertiserId(tokenBo.getAdvertiserId());
        request.setCampaignName(campaignName);
        request.setOperation(opXingtuDropDealDto.getOperation());
        request.setBudgetMode(budgetMode);
        request.setBudget(opXingtuDropDealDto.getBudget());
        request.setLandingType(opXingtuDropDealDto.getLandingType());
        request.setCampaignBudgetOptimization(campaignBudgetOptimization);
        request.setMarketingPurpose(CampaignConstant.MARKETING_PURPOSE_CONVERSION);
        // 当预算类型为指定预算并且开启预算择优分配时, 出价方式设为特殊值
        if (CampaignConstant.BUDGET_MODE_DAY.equals(budgetMode)
            && CampaignConstant.OPTIMIZATION_ON.equals(campaignBudgetOptimization)) {
            request.setSmartBidType(CampaignConstant.SMART_BID_TYPE_SMART);
        }
        JrttBaseResponse<XingtuCreateCampaignResponse> response = XingtuCampaignApi.createCampaign(
            tokenBo.getAccessToken(), request);
        if (JrttCodeConstant.OK.equals(response.getCode())) {
            XingtuCreateCampaignResponse xingtuCreateCampaignResponse = response.getData();
            String campaignId = xingtuCreateCampaignResponse.getCampaignId();

            UpdateWrapper<OpXingtuDropDeal> wrapper = new UpdateWrapper<>();
            wrapper.set("campaign_id", campaignId);
            wrapper.set("campaign_name", campaignName);
            wrapper.eq("id", dropDealId);
            update(wrapper);
        } else {
            log.error("星图创建广告组出错! 星图投放广告ID:{},投放账号:{},请求数据:{},返回数据:{}", dropDealId, account,
                request, response);
            return new MessageVo(dropDealId, response.getMessage(), 500);
        }
        return new MessageVo(dropDealId, "创建成功", 200);
    }
}
