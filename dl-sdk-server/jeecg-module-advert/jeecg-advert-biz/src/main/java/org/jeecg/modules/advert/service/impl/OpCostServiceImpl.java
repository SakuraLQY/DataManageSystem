package org.jeecg.modules.advert.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.advert.dto.OpCostDto;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.ChannelTypeConstant;
import org.jeecg.common.count.vo.CostMoneyModel;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.advert.api.jrtt.account.bo.JrttAccountBo;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttBaseResponse;
import org.jeecg.modules.advert.api.jrtt.report.api.JrttReportApi;
import org.jeecg.modules.advert.api.jrtt.report.bo.CampaignList;
import org.jeecg.modules.advert.api.jrtt.report.bo.CustomnRows;
import org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCampaignRequest;
import org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCampaignResponse;
import org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCustomRequest;
import org.jeecg.modules.advert.api.jrtt.report.bo.JrttReportCustomResponse;
import org.jeecg.modules.advert.constant.enums.PlatformEnum;
import org.jeecg.modules.advert.constant.jrtt.JrttCodeConstant;
import org.jeecg.modules.advert.constant.jrtt.JrttReportConstant;
import org.jeecg.modules.advert.entity.OpCost;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpJrttProject;
import org.jeecg.modules.advert.entity.XingtuDealPlan;
import org.jeecg.modules.advert.mapper.OpCostMapper;
import org.jeecg.modules.advert.service.IOpCostService;
import org.jeecg.modules.advert.service.IOpDealService;
import org.jeecg.modules.advert.service.IOpJrttProjectService;
import org.jeecg.modules.advert.service.IOpJrttPutAccountService;
import org.jeecg.modules.advert.service.IXingtuDealPlanService;
import org.jeecg.modules.advert.vo.OpCostTotalVo;
import org.jeecg.modules.advert.vo.OpCostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_cost
 * @Author: jeecg-boot
 * @Date: 2023-04-27
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_gateway")
public class OpCostServiceImpl extends ServiceImpl<OpCostMapper, OpCost> implements IOpCostService {

    @Autowired
    private IOpJrttPutAccountService opJrttPutAccountService;
    @Autowired
    private IOpDealService opDealService;
    @Autowired
    private IOpJrttProjectService opJrttProjectService;
    @Autowired
    private IXingtuDealPlanService xingtuDealPlanService;

    @Override
    public IPage<OpCostVo> getByPage(Page<OpCostVo> page, OpCost opCost, String startDate,
        String endDate) {
        QueryWrapper<OpCost> wrapper = getOpCostQueryWrapper(opCost, startDate, endDate);
        wrapper.orderByDesc("id");
        return baseMapper.costInfoPage(page, wrapper);
    }

    public OpCostTotalVo getTotalCost(OpCost opCost, String startDate, String endDate) {
        QueryWrapper<OpCost> wrapper = getOpCostQueryWrapper(opCost, startDate, endDate);
        return baseMapper.costTotal(wrapper);
    }

    @Override
    public List<CostMoneyModel> getSummaryCost(OpCostDto opCostDto) {
        QueryWrapper<OpCost> wrapper = new QueryWrapper<>();
        wrapper.in(CollectionUtil.isNotEmpty(opCostDto.getGameId()), "a.game_id",
            opCostDto.getGameId());
        wrapper.in(CollectionUtil.isNotEmpty(opCostDto.getSubGameId()), "a.sub_game_id",
            opCostDto.getSubGameId());
        wrapper.eq(oConvertUtils.isNotEmpty(opCostDto.getCostPlatform()), "a.platform",
            opCostDto.getCostPlatform());
        wrapper.in(CollectionUtil.isNotEmpty(opCostDto.getPkgId()), "a.pkg_id",
            opCostDto.getPkgId());
        wrapper.in(CollectionUtil.isNotEmpty(opCostDto.getAccountId()), "b.account_id",
            opCostDto.getAccountId());
        wrapper.in(CollectionUtil.isNotEmpty(opCostDto.getDealId()), "a.deal_id",
            opCostDto.getDealId());
        wrapper.in(CollectionUtil.isNotEmpty(opCostDto.getChannelTypeId()), "a.channel_type_id",
            opCostDto.getChannelTypeId());
        wrapper.in(CollectionUtil.isNotEmpty(opCostDto.getChannelId()), "a.channel_id",
            opCostDto.getChannelId());
        wrapper.in(CollectionUtil.isNotEmpty(opCostDto.getChannelSubAccountId()), "a.channel_sub_account_id",
            opCostDto.getChannelSubAccountId());
        wrapper.ge(oConvertUtils.isNotEmpty(opCostDto.getRegStartTime()), "a.cost_day",
            opCostDto.getRegStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(opCostDto.getRegEndTime()), "a.cost_day",
            opCostDto.getRegEndTime());
        // 创建用户
        wrapper.eq(oConvertUtils.isNotEmpty(opCostDto.getCreateUser()), "b.create_by",
            opCostDto.getCreateUser());
        String groupBy = "DATE(a.cost_day)";
        if(StringUtils.isNotEmpty(opCostDto.getGroupType())){
            groupBy = opCostDto.getGroupType();
        }
        wrapper.groupBy(groupBy);
        List<CostMoneyModel> costMoneyModelList = baseMapper.getSummaryCost(groupBy, wrapper);
        return costMoneyModelList;
    }

    /**
     * @param opCost
     * @param startDate
     * @param endDate
     * @return com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<org.jeecg.modules.advert.entity.OpCost>
     * @author chenyw
     * @description 获取基础Wrapper
     * @date 13:54 2023/5/4/004
     **/
    private QueryWrapper<OpCost> getOpCostQueryWrapper(OpCost opCost, String startDate,
        String endDate) {
        QueryWrapper<OpCost> wrapper = new QueryWrapper<>();
        wrapper.eq(oConvertUtils.isNotEmpty(opCost.getGameId()), "oc.game_id", opCost.getGameId());
        wrapper.eq(oConvertUtils.isNotEmpty(opCost.getSubGameId()), "oc.sub_game_id",
            opCost.getSubGameId());
        wrapper.eq(oConvertUtils.isNotEmpty(opCost.getPlatform()), "oc.platform",
            opCost.getPlatform());
        wrapper.eq(oConvertUtils.isNotEmpty(opCost.getPkgId()), "oc.pkg_id", opCost.getPkgId());
        wrapper.eq(oConvertUtils.isNotEmpty(opCost.getAccountId()), "oc.account_id",
            opCost.getAccountId());
        wrapper.eq(oConvertUtils.isNotEmpty(opCost.getDealId()), "oc.deal_id",
            opCost.getDealId());
        wrapper.ge(oConvertUtils.isNotEmpty(startDate), "oc.cost_day",
            startDate);
        wrapper.le(oConvertUtils.isNotEmpty(endDate), "oc.cost_day",
            endDate);
        return wrapper;
    }

    /**
     * @author chenyw
     * @description 每十分钟同步一次当日消耗
     * @date 14:39 2023/4/28/028
     **/
    @Scheduled(cron = "0 */10 * * * ?")
    public void syncCostToday() {
        Date date = DateUtils.str2Date(
            DateUtils.date_sdf.get().format(new Date()),
            DateUtils.date_sdf.get());
        // 同步今日头条消耗
        JrttSyncCost(date);
    }

    /**
     * @author chenyw
     * @description 每2小时同步一次昨日消耗
     * @date 11:00 2023/4/28/028
     **/
    @Scheduled(cron = "0 5 */2 * * ?")
    public void syncCostYesterday() {
        Date date = DateUtils.str2Date(
            DateUtils.date_sdf.get().format(new Date()),
            DateUtils.date_sdf.get());
        Date yesterday = org.apache.commons.lang.time.DateUtils.addDays(date, -1);
        // 同步今日头条消耗
        JrttSyncCost(yesterday);
    }

    /**
     * @param date 时间 末尾需要为00:00:00
     * @author chenyw
     * @description 定时同步头条消耗
     * @date 14:47 2023/4/27/027
     **/
    public void JrttSyncCost(Date date) {
        // 日期字符串 YYYY-MM-DD
        String dateStr = DateUtils.date2Str(date, DateUtils.date_sdf.get());
        //1、获取所有的一级账号
        List<JrttAccountBo> firstLevelAccountList = opJrttPutAccountService.getFirstLevelAccount();
        for (JrttAccountBo firstLevelAccount : firstLevelAccountList) {
            //2、根据一级账号获取所有的二级账号
            List<JrttAccountBo> accountList = opJrttPutAccountService.getListByPid(
                firstLevelAccount.getAccountId());
            for (JrttAccountBo account : accountList) {
                String accessToken = firstLevelAccount.getAccessToken();
                Long advertiserId = account.getAdvertiserId();
                Integer accountId = account.getAccountId();
                // 同步体验版消耗
                jrttSyncCostExperience(date, dateStr, accessToken, advertiserId, accountId);
                // 同步原版消耗
                jrttSyncCostOriginal(date, dateStr, accessToken, advertiserId, accountId);
            }
        }
    }

    /**
     * @param date
     * @param dateStr
     * @param accessToken
     * @param advertiserId
     * @param accountId
     * @author chenyw
     * @description 同步今日头条体验版
     * @date 20:50 2023/4/27/027
     **/
    private void jrttSyncCostExperience(Date date, String dateStr, String accessToken,
        Long advertiserId, Integer accountId) {
        log.info("正在同步体验版头条消耗,账号id:{}", accountId);
        try {
            //拉取原版线上报表数据 与本地数据进行比较
            JrttReportCustomRequest jrttReportCustomRequest = new JrttReportCustomRequest();
            jrttReportCustomRequest.setAdvertiserId(advertiserId);
            jrttReportCustomRequest.setDimensions(JrttReportConstant.CUSTOM_DIMENSIONS);
            jrttReportCustomRequest.setMetrics(JrttReportConstant.CUSTOM_METRICS);
            // 最大拉取一百条
            jrttReportCustomRequest.setPageSize(100);
            jrttReportCustomRequest.setStartTime(dateStr);
            jrttReportCustomRequest.setEndTime(dateStr);
            // 休眠100毫秒 接口qps有限制
            Thread.sleep(100);
            JrttBaseResponse<JrttReportCustomResponse> jrttBaseResponse = JrttReportApi.getReportCustom(
                jrttReportCustomRequest, accessToken);
            if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
                log.error("账号:{}同步体验版请求接口失败:{}", accountId,
                    jrttBaseResponse.getMessage());
                return;
            }
            int count = 0;
            if (jrttBaseResponse.getData() != null && CollectionUtil.isNotEmpty(
                jrttBaseResponse.getData().getRows())) {
                for (CustomnRows customnRows : jrttBaseResponse.getData().getRows()) {
                    if (customnRows.getMetrics() == null
                        || customnRows.getDimensions() == null) {
                        // 没有数据继续下一条
                        continue;
                    }
                    OpCost opCost = getOne(
                        new LambdaQueryWrapper<OpCost>().eq(OpCost::getChannelDealId,
                                customnRows.getDimensions().getCdpProjectId())
                            .eq(OpCost::getCostDay, date));
                    opCost.setCostDay(date);
                    opCost.setChannelTypeId(ChannelTypeConstant.MEDIA);
                    opCost.setChannelId(ChannelConstant.JRTT);
                    opCost.setCostMoney(customnRows.getMetrics().getStatCost());
                    opCost.setDownload(customnRows.getMetrics().getDownloadFinishCnt());
                    opCost.setClick(customnRows.getMetrics().getClickCnt());
                    opCost.setExhibition(customnRows.getMetrics().getShowCnt());
                    opCost.setChannelDealId(customnRows.getDimensions().getCdpProjectId());
                    PlatformEnum platformEnum = PlatformEnum.valueOfCode(
                        customnRows.getDimensions().getPlatform());
                    opCost.setPlatform(platformEnum.getValue());
                    if (opCost == null) {
                        opCost = new OpCost();
                        OpJrttProject opJrttProject = opJrttProjectService.getOne(
                            new LambdaQueryWrapper<OpJrttProject>().eq(
                                OpJrttProject::getProjectId,
                                customnRows.getDimensions().getCdpProjectId()));
                        if (opJrttProject != null) {
                            opCost.setDealId(opJrttProject.getDealId());
                            OpDeal opDeal = opDealService.getById(
                                opJrttProject.getDealId());
                            if (opDeal != null) {
                                opCost.setGameId(opDeal.getGameId());
                                opCost.setSubGameId(opDeal.getSubGameId());
                                opCost.setPkgId(opDeal.getPkgId());
                                opCost.setChannelId(opDeal.getChannelId());
                                opCost.setChannelSubAccountId(
                                    opDeal.getChannelSubAccountId());
                                opCost.setAccountId(opDeal.getAccountId());
                            }
                        }
                    }
                    boolean res = saveOrUpdate(opCost);
                    if (res) {
                        count++;
                    }
                }
            }
            log.info("体验版头条消耗同步结束,数量:{}, 账号id:{}", count, accountId);
        } catch (Exception e) {
            log.error("同步体验版头条消耗异常,账号id:{}", accountId, e);
        }
    }

    /**
     * @param date
     * @param dateStr
     * @param accessToken
     * @param advertiserId
     * @param accountId
     * @author chenyw
     * @description 同步今日头条原版
     * @date 21:20 2023/4/27/027
     **/
    public void jrttSyncCostOriginal(Date date, String dateStr, String accessToken,
        Long advertiserId, Integer accountId) {
        log.info("正在同步原版头条消耗,账号id:{}", accountId);
        try {
            JrttReportCampaignRequest jrttReportCampaignRequest = new JrttReportCampaignRequest();
            jrttReportCampaignRequest.setAdvertiserId(advertiserId);
            jrttReportCampaignRequest.setStartDate(dateStr);
            jrttReportCampaignRequest.setEndDate(dateStr);
            jrttReportCampaignRequest.setPageSize(100);
            jrttReportCampaignRequest.setGroupBy(JrttReportConstant.CAMPAIGN_GROUPBY);
            // 休眠100毫秒 接口qps有限制
            Thread.sleep(100);
            JrttBaseResponse<JrttReportCampaignResponse> jrttBaseResponse = JrttReportApi.getReportCampaign(
                jrttReportCampaignRequest, accessToken);
            if (!JrttCodeConstant.OK.equals(jrttBaseResponse.getCode())) {
                log.error("账号:{}同步原版消耗广告请求接口失败:{}", accountId,
                    jrttBaseResponse.getMessage());
                return;
            }
            int count = 0;
            if (jrttBaseResponse.getData() != null && CollectionUtil.isNotEmpty(
                jrttBaseResponse.getData().getList())) {
                for (CampaignList campaignList : jrttBaseResponse.getData().getList()) {
                    OpCost opCost = getOne(
                        new LambdaQueryWrapper<OpCost>().eq(OpCost::getChannelDealId,
                            campaignList.getCampaignId()).eq(OpCost::getCostDay, date));
                    opCost.setChannelTypeId(ChannelTypeConstant.MEDIA);
                    // 旧版头条渠道默认为星图
                    opCost.setChannelId(ChannelConstant.XING_TU);
                    opCost.setCostDay(date);
                    opCost.setCostMoney(campaignList.getCost());
                    opCost.setDownload(campaignList.getConvert());
                    opCost.setClick(campaignList.getClick());
                    opCost.setExhibition(campaignList.getShow());
                    opCost.setChannelDealId(campaignList.getCampaignId());
                    PlatformEnum platformEnum = PlatformEnum.valueOfCode(
                        campaignList.getPlatform());
                    opCost.setPlatform(platformEnum.getValue());
                    if (opCost == null) {
                        opCost = new OpCost();
                        // 现在原版的广告投放都是星图
                        XingtuDealPlan xingtuDealPlan = xingtuDealPlanService.getOne(
                            new LambdaQueryWrapper<XingtuDealPlan>().eq(
                                XingtuDealPlan::getCampaignId,
                                campaignList.getCampaignId()));
                        if (xingtuDealPlan != null) {
                            opCost.setDealId(xingtuDealPlan.getDealId());
                            OpDeal opDeal = opDealService.getById(
                                xingtuDealPlan.getDealId());
                            if (opDeal != null) {
                                opCost.setGameId(opDeal.getGameId());
                                opCost.setSubGameId(opDeal.getSubGameId());
                                opCost.setPkgId(opDeal.getPkgId());
                                opCost.setChannelSubAccountId(
                                    opDeal.getChannelSubAccountId());
                                opCost.setChannelId(opDeal.getChannelId());
                                opCost.setAccountId(opDeal.getAccountId());
                            }
                        }
                    }
                    boolean res = saveOrUpdate(opCost);
                    if (res) {
                        count++;
                    }
                }
            }
            log.info("原版头条消耗同步结束,数量:{},账号id:{}", count, accountId);
        } catch (Exception e) {
            log.error("同步原版头条消耗异常,账号id:{}", accountId, e);
        }
    }

}
