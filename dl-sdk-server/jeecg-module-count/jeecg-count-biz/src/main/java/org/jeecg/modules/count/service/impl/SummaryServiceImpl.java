package org.jeecg.modules.count.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.advert.dto.OpCostDto;
import org.jeecg.common.advert.vo.OpChannelSubAccountModel;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.advert.vo.OpPutAccountModel;
import org.jeecg.modules.count.constant.enums.SummaryEnum;
import org.jeecg.common.count.vo.CostMoneyModel;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.bo.summary.SummaryDailyBo;
import org.jeecg.modules.count.bo.summary.SummaryOrderBo;
import org.jeecg.modules.count.bo.summary.SummaryOrderDevBo;
import org.jeecg.modules.count.dto.SummaryDealInfoDataDto;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.service.ICtOrderService;
import org.jeecg.modules.count.service.ISummaryService;
import org.jeecg.modules.count.vo.SummaryDealInfoDataVo;
import org.jeecg.modules.count.vo.SummaryDealSameAccountVo;
import org.jeecg.modules.count.vo.SummaryDealSevenDayVo;
import org.jeecg.modules.count.vo.SummaryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummaryServiceImpl implements ISummaryService {

    @Autowired
    private IAdvertApi advertApi;
    @Autowired
    private ICtDailyService ctDailyService;
    @Autowired
    private ICtOrderService ctOrderService;
    @Autowired
    private IGameApi gameApi;

    @Override
    public List<SummaryVo> getSummary(SummaryDto summaryDto) {
        // 初始化时间 + 00:00:00
        if (StringUtils.isNotEmpty(summaryDto.getRegStartTime())) {
            summaryDto.setRegStartTime(summaryDto.getRegStartTime() + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(summaryDto.getRegEndTime())) {
            summaryDto.setRegEndTime(summaryDto.getRegEndTime() + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(summaryDto.getPayStartTime())) {
            summaryDto.setPayStartTime(summaryDto.getRegStartTime() + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(summaryDto.getPayEndTime())) {
            summaryDto.setPayEndTime(summaryDto.getPayEndTime() + " 23:59:59");
        }
        OpCostDto opCostDto = new OpCostDto();
        BeanUtils.copyProperties(summaryDto, opCostDto);
        opCostDto.setGroupType(SummaryEnum.valueOfCode(summaryDto.getGroupType()).getGroupCost());
        // 获取成本
        List<CostMoneyModel> costMoneyModelList = advertApi.getCostMoney(opCostDto);
        // 获取每日数据
        List<SummaryDailyBo> summaryDailyBoList = ctDailyService.getSummaryDaily(summaryDto);
        // 获取周期数据
        List<SummaryOrderBo> summaryOrderBoList = ctOrderService.getSummaryOrder(summaryDto);
        // 获取周期数据-按设备
        List<SummaryOrderDevBo> summaryOrderDevBoList = ctOrderService.getSummaryOrderDev(
            summaryDto);
        // 汇总计算
        List<SummaryVo> result = calcSummary(costMoneyModelList, summaryDailyBoList,
            summaryOrderBoList, summaryOrderDevBoList);
        return result;
    }

    @Override
    public SummaryDealInfoDataVo getDealInfoData(SummaryDealInfoDataDto summaryDealInfoDataDto) {
        // 查询七天数据
        List<SummaryDealSevenDayVo> summaryDealSevenDayVoList = getSummaryDealSevenDay(
            summaryDealInfoDataDto);
        OpDealModel opDealModel = advertApi.getOpDeal(summaryDealInfoDataDto.getDealId());
        // 查询同名广告数据
        List<SummaryDealSameAccountVo> summaryDealSameAccountVoList = getSummaryDealSameAccount(
            summaryDealInfoDataDto, opDealModel);
        // 查询一级游戏包
        OpPkgModel pkgModel = gameApi.getOpPkgById(opDealModel.getPkgId());
        // 查询投放账号
        OpPutAccountModel opPutAccountModel = advertApi.getOpPutAccount(opDealModel.getAccountId());
        // 查询子渠道
        OpChannelSubAccountModel opChannelSubAccountModel = advertApi.getOpChannelSubAccount(
            opDealModel.getChannelSubAccountId());
        SummaryDealInfoDataVo result = new SummaryDealInfoDataVo();
        result.setSevenDay(summaryDealSevenDayVoList);
        result.setSameAccount(summaryDealSameAccountVoList);
        result.setPkgName(pkgModel.getPkgName());
        result.setAccountName(opPutAccountModel.getNickName());
        result.setSubChannelName(opChannelSubAccountModel.getSubAccountName());
        return result;
    }

    /**
     * @param summaryDealInfoDataDto
     * @return java.util.List<org.jeecg.modules.count.vo.SummaryDealSevenDayVo>
     * @author chenyw
     * @description 广告最近七天数据
     * @date 18:31 2023/5/8/008
     **/
    private List<SummaryDealSevenDayVo> getSummaryDealSevenDay(
        SummaryDealInfoDataDto summaryDealInfoDataDto) {
        SummaryDto summaryDto = new SummaryDto();
        List<Integer> dealIdList = new ArrayList<>();
        dealIdList.add(summaryDealInfoDataDto.getDealId());
        summaryDto.setDealId(dealIdList);
        summaryDto.setGroupType(SummaryEnum.TYPE_ALL.getCode());
        // 返回值
        List<SummaryDealSevenDayVo> result = new ArrayList();
        // 计算日期 七天区间
        Calendar calendar = DateUtils.getCalendar();
        calendar.add(Calendar.DATE, -Calendar.DAY_OF_WEEK);
        String regStartDate = DateUtils.date2Str(calendar.getTime(), DateUtils.date_sdf.get());
        String regEndDate = DateUtils.date2Str(new Date(), DateUtils.date_sdf.get());
        summaryDto.setRegStartTime(regStartDate + " 00:00:00");
        summaryDto.setRegEndTime(regEndDate + " 23:59:59");
        OpCostDto opCostDto = new OpCostDto();
        BeanUtils.copyProperties(summaryDto, opCostDto);
        opCostDto.setGroupType(SummaryEnum.valueOfCode(summaryDto.getGroupType()).getGroupCost());
        // 获取成本
        List<CostMoneyModel> costMoneyModelList = advertApi.getCostMoney(opCostDto);
        // 获取每日数据
        List<SummaryDailyBo> summaryDailyBoList = ctDailyService.getSummaryDaily(summaryDto);
        // 成本map
        Map<String, CostMoneyModel> summaryCostMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(costMoneyModelList)) {
            summaryCostMap = costMoneyModelList.stream()
                .collect(Collectors.toMap(CostMoneyModel::getId, costModel -> costModel));
        }
        for (SummaryDailyBo summaryDailyBo : summaryDailyBoList) {
            SummaryVo summaryVo = new SummaryVo();
            CostMoneyModel costMoneyModel = summaryCostMap.get(summaryDailyBo.getId());
            // 计算数据
            calcSummaryBo(costMoneyModel, null, null, summaryDailyBo,
                summaryVo);
            SummaryDealSevenDayVo summaryDealSevenDayVo = new SummaryDealSevenDayVo();
            BeanUtils.copyProperties(summaryVo, summaryDealSevenDayVo);
            // 设置日期
            summaryDealSevenDayVo.setDate(summaryDailyBo.getId());
            result.add(summaryDealSevenDayVo);
        }
        return result;
    }

    /**
     * @param summaryDealInfoDataDto
     * @return java.util.List<org.jeecg.modules.count.vo.SummaryDealSameAccountVo>
     * @author chenyw
     * @description 获取同广告数据
     * @date 18:52 2023/5/8/008
     **/
    private List<SummaryDealSameAccountVo> getSummaryDealSameAccount(
        SummaryDealInfoDataDto summaryDealInfoDataDto, OpDealModel opDealModel) {
        // 返回值
        List<SummaryDealSameAccountVo> result = new ArrayList();
        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setGroupType(SummaryEnum.TYPE_DEAL.getCode());
        // 设置注册时间
        summaryDto.setRegStartTime(summaryDealInfoDataDto.getRegStartTime());
        summaryDto.setRegEndTime(summaryDealInfoDataDto.getRegEndTime());
        // 设置账号id
        List<Integer> accountIdList = new ArrayList<>();
        accountIdList.add(opDealModel.getAccountId());
        summaryDto.setAccountId(accountIdList);
        OpCostDto opCostDto = new OpCostDto();
        BeanUtils.copyProperties(summaryDto, opCostDto);
        opCostDto.setGroupType(SummaryEnum.valueOfCode(summaryDto.getGroupType()).getGroupCost());
        // 获取成本
        List<CostMoneyModel> costMoneyModelList = advertApi.getCostMoney(opCostDto);
        // 获取每日数据
        List<SummaryDailyBo> summaryDailyBoList = ctDailyService.getSummaryDaily(summaryDto);
        // 成本map
        Map<String, CostMoneyModel> summaryCostMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(costMoneyModelList)) {
            summaryCostMap = costMoneyModelList.stream()
                .collect(Collectors.toMap(CostMoneyModel::getId, costModel -> costModel));
        }
        for (SummaryDailyBo summaryDailyBo : summaryDailyBoList) {
            CostMoneyModel costMoneyModel = summaryCostMap.get(summaryDailyBo.getId());
            if (BigDecimal.ZERO.equals(summaryDailyBo.getAliveMoney()) && costMoneyModel != null
                && BigDecimal.ZERO.equals(costMoneyModel.getCostMoney())) {
                // 如果金额为空
                continue;
            }
            if (Objects.equals(opDealModel.getId() + "", summaryDailyBo.getId())) {
                // 如果和原广告id相同
                continue;
            }
            SummaryVo summaryVo = new SummaryVo();
            // 计算数据
            calcSummaryBo(costMoneyModel, null, null, summaryDailyBo,
                summaryVo);
            SummaryDealSameAccountVo summaryDealSameAccountVo = new SummaryDealSameAccountVo();
            BeanUtils.copyProperties(summaryVo, summaryDealSameAccountVo);
            // 设置广告id和广告名
            summaryDealSameAccountVo.setDealId(summaryDailyBo.getId());
            summaryDealSameAccountVo.setDealName(summaryDailyBo.getName());
            result.add(summaryDealSameAccountVo);
        }
        return result;
    }

    /**
     * @param costMoneyModelList
     * @param summaryDailyBoList
     * @param summaryOrderBoList
     * @param summaryOrderDevBoList
     * @return java.util.List<org.jeecg.modules.count.vo.SummaryVo>
     * @author chenyw
     * @description 汇总计算
     * @date 17:17 2023/5/6/006
     **/
    private List<SummaryVo> calcSummary(List<CostMoneyModel> costMoneyModelList,
        List<SummaryDailyBo> summaryDailyBoList, List<SummaryOrderBo> summaryOrderBoList,
        List<SummaryOrderDevBo> summaryOrderDevBoList) {
        String totalId = "合计";
        List<SummaryVo> summaryVoList = new ArrayList();
        SummaryVo totalSummaryVo = new SummaryVo();
        SummaryDailyBo totalSummaryDailyBo = new SummaryDailyBo();
        if (CollectionUtil.isEmpty(summaryDailyBoList)) {
            return null;
        }
        // 成本map
        Map<String, CostMoneyModel> summaryCostMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(costMoneyModelList)) {
            CostMoneyModel totalCostMoneyModel = new CostMoneyModel();
            totalCostMoneyModel.setId(totalId);
            for (CostMoneyModel costMoneyModel : costMoneyModelList) {
                summaryCostMap.put(costMoneyModel.getId(), costMoneyModel);
                totalCostMoneyModel.setCostMoney(
                    totalCostMoneyModel.getCostMoney().add(costMoneyModel.getCostMoney()));
            }
            // 设置合计
            summaryCostMap.put(totalId, totalCostMoneyModel);
        }
        // 周期订单map
        Map<String, SummaryOrderBo> summaryOrderMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(summaryOrderBoList)) {
            SummaryOrderBo totalSummaryOrderBo = new SummaryOrderBo();
            totalSummaryOrderBo.setId(totalId);
            for (SummaryOrderBo summaryOrderBo : summaryOrderBoList) {
                summaryOrderMap.put(summaryOrderBo.getId(), summaryOrderBo);
                totalSummaryOrderBo.setSumMoney(
                    totalSummaryOrderBo.getSumMoney().add(summaryOrderBo.getSumMoney()));
                totalSummaryOrderBo.setCountUser(
                    totalSummaryOrderBo.getCountUser() + summaryOrderBo.getCountUser());
            }
            // 设置合计
            summaryOrderMap.put(totalId, totalSummaryOrderBo);
        }
        // 周期订单-按设备 map
        Map<String, SummaryOrderDevBo> summaryOrderDevMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(summaryOrderDevBoList)) {
            SummaryOrderDevBo totalSummaryOrderDevBo = new SummaryOrderDevBo();
            totalSummaryOrderDevBo.setId(totalId);
            for (SummaryOrderDevBo summaryOrderDevBo : summaryOrderDevBoList) {
                summaryOrderDevMap.put(summaryOrderDevBo.getId(), summaryOrderDevBo);
                totalSummaryOrderDevBo.setSumMoney(
                    totalSummaryOrderDevBo.getSumMoney().add(summaryOrderDevBo.getSumMoney()));
                totalSummaryOrderDevBo.setCountDevice(
                    totalSummaryOrderDevBo.getCountDevice() + summaryOrderDevBo.getCountDevice());
            }
            // 设置合计
            summaryOrderDevMap.put(totalId, totalSummaryOrderDevBo);
        }
        for (SummaryDailyBo summaryDailyBo : summaryDailyBoList) {
            SummaryVo summaryVo = new SummaryVo();
            CostMoneyModel costMoneyModel = summaryCostMap.get(summaryDailyBo.getId());
            SummaryOrderBo summaryOrderBo = summaryOrderMap.get(summaryDailyBo.getId());
            SummaryOrderDevBo summaryOrderdevBo = summaryOrderDevMap.get(summaryDailyBo.getId());
            // 计算数据
            calcSummaryBo(costMoneyModel, summaryOrderBo, summaryOrderdevBo, summaryDailyBo,
                summaryVo);
            // 存放返回值
            summaryVoList.add(summaryVo);
            // 累加bo的数据
            calcTotalSummaryDailyBo(summaryDailyBo, totalSummaryDailyBo);
        }
        CostMoneyModel totalCostMoneyModel = null;
        SummaryOrderBo totalSummaryOrderBo = null;
        SummaryOrderDevBo totalSummaryOrderdevBo = null;
        if (CollectionUtil.isNotEmpty(summaryCostMap)) {
            totalCostMoneyModel = summaryCostMap.get(totalId);
        }
        if (CollectionUtil.isNotEmpty(summaryOrderMap)) {
            totalSummaryOrderBo = summaryOrderMap.get(totalId);
        }
        if (CollectionUtil.isNotEmpty(summaryOrderDevMap)) {
            totalSummaryOrderdevBo = summaryOrderDevMap.get(totalId);
        }
        // 计算汇总数据
        calcSummaryBo(totalCostMoneyModel, totalSummaryOrderBo, totalSummaryOrderdevBo,
            totalSummaryDailyBo, totalSummaryVo);
        totalSummaryVo.setId(totalId);
        // 存放汇总返回值
        summaryVoList.add(totalSummaryVo);
        return summaryVoList;
    }

    /**
     * @param costMoneyModel    成本数据
     * @param summaryOrderBo    周期订单数据
     * @param summaryOrderdevBo 周期订单数据-按设备
     * @param summaryDailyBo    每日汇总数据 (必传)
     * @param summaryVo         汇总数据 最终结果 (必传)
     * @author chenyw
     * @description 数据总表单条计算
     * @date 17:15 2023/5/6/006
     **/
    private void calcSummaryBo(CostMoneyModel costMoneyModel, SummaryOrderBo summaryOrderBo,
        SummaryOrderDevBo summaryOrderdevBo, SummaryDailyBo summaryDailyBo, SummaryVo summaryVo) {
        // 赋值同名参数
        BeanUtils.copyProperties(summaryDailyBo, summaryVo);
        if (costMoneyModel != null) {
            // 设置成本
            summaryVo.setCostMoney(costMoneyModel.getCostMoney());
        }
        // 激活注册率 = 注册数/激活数
        if (summaryDailyBo.getCountActive() != 0) {
            summaryVo.setCountActiveUserRate(
                BigDecimal.valueOf(summaryDailyBo.getCountUser()).multiply(new BigDecimal(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountActive()), 2,
                        RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getCountActiveDev() != 0) {
            summaryVo.setCountActiveUserRateDev(
                BigDecimal.valueOf(summaryDailyBo.getCountUserDev()).multiply(new BigDecimal(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountActiveDev()), 2,
                        RoundingMode.HALF_UP));
        }
        // 注册单价 = 成本/新用户数
        if (summaryDailyBo.getCountUser() != 0) {
            summaryVo.setCostCountUser(summaryVo.getCostMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getCountUserDev() != 0) {
            summaryVo.setCostCountUserDev(costMoneyModel.getCostMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 首日回款率
        if (!BigDecimal.ZERO.equals(summaryVo.getCostMoney())) {
            summaryVo.setFirstMoneyCostMoneyRate(
                summaryDailyBo.getFirstMoney().multiply(new BigDecimal(100))
                    .divide(summaryVo.getCostMoney(), 2, RoundingMode.HALF_UP));
            summaryVo.setFirstMoneyCostMoneyRateDev(
                summaryDailyBo.getFirstMoneyDev().multiply(new BigDecimal(100))
                    .divide(summaryVo.getCostMoney(), 2, RoundingMode.HALF_UP));
        }
        // 首日付费单价 = 首日付费成本/首日付费人数
        if (summaryVo.getFirstPayuser() != 0) {
            summaryVo.setFirstPayuserCostMoney(summaryVo.getCostMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getFirstPayuser()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryVo.getFirstPayuserDev() != 0) {
            summaryVo.setFirstPayuserCostMoney(summaryVo.getCostMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getFirstPayuserDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 首日arpu = 首日付费额/注册数
        if (summaryDailyBo.getCountUser() != 0) {
            summaryVo.setFirstArpu(summaryDailyBo.getFirstMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getCountUserDev() != 0) {
            summaryVo.setFirstArpuDev(summaryDailyBo.getFirstMoneyDev()
                .divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 首日arppu = 首日付费额/首日付费用户数
        if (summaryDailyBo.getFirstPayuser() != 0) {
            summaryVo.setFirstArppu(summaryDailyBo.getFirstMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getFirstPayuser()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getFirstPayuserDev() != 0) {
            summaryVo.setFirstArppuDev(summaryDailyBo.getFirstMoneyDev()
                .divide(BigDecimal.valueOf(summaryDailyBo.getFirstPayuserDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 首日Roi = 首日回本/成本
        if (!BigDecimal.ZERO.equals(summaryVo.getCostMoney())) {
            summaryVo.setFirstRoi(summaryDailyBo.getPayback1()
                .divide(summaryVo.getCostMoney(), 2, RoundingMode.HALF_UP));
            summaryVo.setFirstRoiDev(summaryDailyBo.getPayback1Dev()
                .divide(summaryVo.getCostMoney(), 2, RoundingMode.HALF_UP));
        }
        // 首日订单均值 = 首日付费额/首日付费次数
        if (summaryDailyBo.getFirstOrder() != 0) {
            summaryVo.setFirstMoneyFirstOrder(summaryDailyBo.getFirstMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getFirstOrder()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getFirstOrderDev() != 0) {
            summaryVo.setFirstMoneyFirstOrderDev(summaryDailyBo.getFirstMoneyDev()
                .divide(BigDecimal.valueOf(summaryDailyBo.getFirstOrderDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 首日付费次数单价 = 成本/首日付费次数
        if (summaryDailyBo.getFirstOrder() != 0) {
            summaryVo.setFirstOrderCost(summaryVo.getCostMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getFirstOrder()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getFirstOrderDev() != 0) {
            summaryVo.setFirstOrderCostDev(summaryVo.getCostMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getFirstOrderDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 老用户
        // 老用户付费人数
        BigDecimal oldPayuser = BigDecimal.valueOf(
            summaryDailyBo.getAlivePayuser() - summaryDailyBo.getFirstPayuser());
        BigDecimal oldPayuserDev = BigDecimal.valueOf(
            summaryDailyBo.getAlivePayuserDev() - summaryDailyBo.getFirstPayuserDev());
        // 老用户活跃数
        BigDecimal oldDau = BigDecimal.valueOf(
            summaryDailyBo.getCountDau() - summaryDailyBo.getCountUser());
        BigDecimal oldDauDev = BigDecimal.valueOf(
            summaryDailyBo.getCountDauDev() - summaryDailyBo.getCountUserDev());
        // 老用户支付金额
        BigDecimal oldMoney = summaryDailyBo.getAliveMoney()
            .subtract(summaryDailyBo.getFirstMoney());
        BigDecimal oldMoneyDev = summaryDailyBo.getAliveMoneyDev()
            .subtract(summaryDailyBo.getFirstMoneyDev());
        // 老用户付费率
        if (!BigDecimal.ZERO.equals(oldDau)) {
            summaryVo.setOldUserPayRate(oldPayuser.multiply(BigDecimal.valueOf(100))
                .divide(oldDau, 2, RoundingMode.HALF_UP));
        }
        if (!BigDecimal.ZERO.equals(oldDauDev)) {
            summaryVo.setOldUserPayRateDev(
                oldPayuserDev.multiply(BigDecimal.valueOf(100))
                    .divide(oldDauDev, 2, RoundingMode.HALF_UP));
        }
        // 老用户ARPU
        if (!BigDecimal.ZERO.equals(oldDau)) {
            summaryVo.setOldUserArpu(oldMoney.divide(oldDau, 2, RoundingMode.HALF_UP));
        }
        if (!BigDecimal.ZERO.equals(oldDauDev)) {
            summaryVo.setOldUserArpuDev(oldMoneyDev.divide(oldDauDev, 2, RoundingMode.HALF_UP));
        }
        // 老用户ARPPU
        if (!BigDecimal.ZERO.equals(oldPayuser)) {
            summaryVo.setOldUserArppu(oldMoney.multiply(BigDecimal.valueOf(100))
                .divide(oldPayuser, 2, RoundingMode.HALF_UP));
        }
        if (!BigDecimal.ZERO.equals(oldPayuserDev)) {
            summaryVo.setOldUserArppuDev(oldMoneyDev.multiply(BigDecimal.valueOf(100))
                .divide(oldPayuserDev, 2, RoundingMode.HALF_UP));
        }
        // 周期
        if (summaryOrderBo != null) {
            // 周期付费额
            summaryVo.setCycleMoney(summaryOrderBo.getSumMoney());
            // 周期付费人数
            summaryVo.setCycleUser(summaryOrderBo.getCountUser());
            // 周期回本率 = 收入/成本
            if (!BigDecimal.ZERO.equals(summaryVo.getCostMoney())) {
                summaryVo.setCycleMoneyCost(
                    summaryVo.getCycleMoney().multiply(BigDecimal.valueOf(100))
                        .divide(summaryVo.getCostMoney(), 2, RoundingMode.HALF_UP));
            }
            // 周期付费率 =  收入/注册数
            if (summaryDailyBo.getCountUser() != 0) {
                summaryVo.setCycleUserCountUserRate(
                    summaryVo.getCycleMoney().multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                            RoundingMode.HALF_UP));
            }
            // 周期arpu = 收入/周期内注册数
            if (!BigDecimal.ZERO.equals(summaryDailyBo.getCountUser())) {
                summaryVo.setCycleArpu(summaryVo.getCycleMoney()
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            }
            // 周期arppu = 收入/周期内付费人数
            if (!BigDecimal.ZERO.equals(summaryVo.getCycleUser())) {
                summaryVo.setCycleArppu(summaryVo.getCycleMoney()
                    .divide(BigDecimal.valueOf(summaryVo.getCycleUser()), 2,
                        RoundingMode.HALF_UP));
            }
        }
        // 周期-按设备
        if (summaryOrderdevBo != null) {
            // 周期付费额-按设备
            summaryVo.setCycleMoneyDev(summaryOrderdevBo.getSumMoney());
            // 周期付费人数-按设备
            summaryVo.setCycleUserDev(summaryOrderdevBo.getCountDevice());
            // 周期回本率-按设备 = 收入/成本
            if (!BigDecimal.ZERO.equals(summaryVo.getCostMoney())) {
                summaryVo.setCycleMoneyCostDev(
                    summaryVo.getCycleMoneyDev().multiply(BigDecimal.valueOf(100))
                        .divide(summaryVo.getCostMoney(), 2, RoundingMode.HALF_UP));
            }
            // 周期付费率-按设备 =  收入/注册数
            if (summaryDailyBo.getCountUserDev() != 0) {
                summaryVo.setCycleUserCountUserRateDev(
                    summaryVo.getCycleMoneyDev().multiply(BigDecimal.valueOf(100))
                        .divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                            RoundingMode.HALF_UP));
            }
            // 周期arpu-按设备 = 收入/周期内注册数
            if (!BigDecimal.ZERO.equals(summaryDailyBo.getCountUserDev())) {
                summaryVo.setCycleArpuDev(summaryVo.getCycleMoneyDev()
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                        RoundingMode.HALF_UP));
            }
            // 周期arppu-按设备 = 收入/周期内付费人数
            if (!BigDecimal.ZERO.equals(summaryVo.getCycleUserDev())) {
                summaryVo.setCycleArppuDev(summaryVo.getCycleMoneyDev()
                    .divide(BigDecimal.valueOf(summaryVo.getCycleUserDev()), 2,
                        RoundingMode.HALF_UP));
            }
        }
        //累积回本率 = 累计付费/成本
        if (!BigDecimal.ZERO.equals(summaryVo.getCostMoney())) {
            summaryVo.setTotalMoneyCostMoneyRate(
                summaryDailyBo.getTotalMoney().multiply(BigDecimal.valueOf(100))
                    .divide(summaryVo.getCostMoney(), 2, RoundingMode.HALF_UP));
            summaryVo.setTotalMoneyCostMoneyRateDev(
                summaryDailyBo.getTotalMoneyDev().multiply(BigDecimal.valueOf(100))
                    .divide(summaryVo.getCostMoney(), 2, RoundingMode.HALF_UP));
        }
        // 累积arpu = 累计付费额/日活
        if (summaryDailyBo.getCountDau() != 0) {
            summaryVo.setTotalArpu(summaryDailyBo.getTotalMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getCountDau()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getCountDauDev() != 0) {
            summaryVo.setTotalArpuDev(summaryDailyBo.getTotalMoneyDev()
                .divide(BigDecimal.valueOf(summaryDailyBo.getCountDauDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 累积arppu = 累计付费额/累计付费人数
        if (summaryDailyBo.getTotalPayuser() != 0) {
            summaryVo.setTotalArppu(summaryDailyBo.getTotalMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getTotalPayuser()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getTotalPayuserDev() != 0) {
            summaryVo.setTotalArppu(summaryDailyBo.getTotalMoneyDev()
                .divide(BigDecimal.valueOf(summaryDailyBo.getTotalPayuserDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 活跃订单均值=活跃付费金额/活跃付费次数
        if (summaryDailyBo.getAliveOrder() != 0) {
            summaryVo.setAliveMoneyAliveOrderAlive(summaryDailyBo.getAliveMoney()
                .divide(BigDecimal.valueOf(summaryDailyBo.getAliveOrder()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getAliveOrderDev() != 0) {
            summaryVo.setAliveMoneyAliveOrderAliveDev(summaryDailyBo.getAliveMoneyDev()
                .divide(BigDecimal.valueOf(summaryDailyBo.getAliveOrderDev()), 2,
                    RoundingMode.HALF_UP));
        }
        // 留存
        if (summaryDailyBo.getCountUser() != 0) {
            summaryVo.setRetention2(
                BigDecimal.valueOf(summaryDailyBo.getLoyal2()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention3(
                BigDecimal.valueOf(summaryDailyBo.getLoyal3()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention4(
                BigDecimal.valueOf(summaryDailyBo.getLoyal4()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention5(
                BigDecimal.valueOf(summaryDailyBo.getLoyal5()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention6(
                BigDecimal.valueOf(summaryDailyBo.getLoyal6()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention7(
                BigDecimal.valueOf(summaryDailyBo.getLoyal7()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getCountUserDev() != 0) {
            summaryVo.setRetention2Dev(
                BigDecimal.valueOf(summaryDailyBo.getLoyal2Dev()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention3Dev(
                BigDecimal.valueOf(summaryDailyBo.getLoyal3Dev()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention4Dev(
                BigDecimal.valueOf(summaryDailyBo.getLoyal4Dev()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention5Dev(
                BigDecimal.valueOf(summaryDailyBo.getLoyal5Dev()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention6Dev(
                BigDecimal.valueOf(summaryDailyBo.getLoyal6Dev()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryVo.setRetention7Dev(
                BigDecimal.valueOf(summaryDailyBo.getLoyal7Dev()).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
        }
        // ltv
        if (summaryDailyBo.getCountUser() != 0) {
            BigDecimal payback1 = summaryDailyBo.getPayback1();
            BigDecimal payback2 = summaryDailyBo.getPayback2().add(payback1);
            BigDecimal payback3 = summaryDailyBo.getPayback3().add(payback2);
            BigDecimal payback4 = summaryDailyBo.getPayback4().add(payback3);
            BigDecimal payback5 = summaryDailyBo.getPayback5().add(payback4);
            BigDecimal payback6 = summaryDailyBo.getPayback6().add(payback5);
            BigDecimal payback7 = summaryDailyBo.getPayback7().add(payback6);
            summaryVo.setLtv1(
                payback1.divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv2(
                payback2.divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv3(
                payback3.divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv4(
                payback4.divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv5(
                payback5.divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv6(
                payback6.divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv7(
                payback7.divide(BigDecimal.valueOf(summaryDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
        }
        if (summaryDailyBo.getCountUserDev() != 0) {
            BigDecimal payback1Dev = summaryDailyBo.getPayback1Dev();
            BigDecimal payback2Dev = summaryDailyBo.getPayback2Dev().add(payback1Dev);
            BigDecimal payback3Dev = summaryDailyBo.getPayback3Dev().add(payback2Dev);
            BigDecimal payback4Dev = summaryDailyBo.getPayback4Dev().add(payback3Dev);
            BigDecimal payback5Dev = summaryDailyBo.getPayback5Dev().add(payback4Dev);
            BigDecimal payback6Dev = summaryDailyBo.getPayback6Dev().add(payback5Dev);
            BigDecimal payback7Dev = summaryDailyBo.getPayback7Dev().add(payback6Dev);
            summaryVo.setLtv1Dev(
                payback1Dev.divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv2Dev(
                payback2Dev.divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv3Dev(
                payback3Dev.divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv4Dev(
                payback4Dev.divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv5Dev(
                payback5Dev.divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv6Dev(
                payback6Dev.divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
            summaryVo.setLtv7Dev(
                payback7Dev.divide(BigDecimal.valueOf(summaryDailyBo.getCountUserDev()), 2,
                    RoundingMode.HALF_UP));
        }
    }

    /**
     * @param summaryDailyBo
     * @param totalSummaryDailyBo
     * @author chenyw
     * @description 每日数据 汇总累加
     * @date 17:14 2023/5/6/006
     **/
    private void calcTotalSummaryDailyBo(SummaryDailyBo summaryDailyBo,
        SummaryDailyBo totalSummaryDailyBo) {
        totalSummaryDailyBo.setCountActive(
            totalSummaryDailyBo.getCountActive() + summaryDailyBo.getCountActive());
        totalSummaryDailyBo.setCountActiveDev(
            totalSummaryDailyBo.getCountActiveDev() + summaryDailyBo.getCountActiveDev());
        totalSummaryDailyBo.setCountUser(
            totalSummaryDailyBo.getCountUser() + summaryDailyBo.getCountUser());
        totalSummaryDailyBo.setCountUserDev(
            totalSummaryDailyBo.getCountUserDev() + summaryDailyBo.getCountUserDev());
        totalSummaryDailyBo.setCountValidUser(
            totalSummaryDailyBo.getCountValidUser() + summaryDailyBo.getCountValidUser());
        totalSummaryDailyBo.setCountValidUserDev(
            totalSummaryDailyBo.getCountValidUserDev() + summaryDailyBo.getCountValidUserDev());
        totalSummaryDailyBo.setFirstMoney(
            totalSummaryDailyBo.getFirstMoney().add(summaryDailyBo.getFirstMoney()));
        totalSummaryDailyBo.setFirstMoneyDev(
            totalSummaryDailyBo.getFirstMoneyDev().add(summaryDailyBo.getFirstMoneyDev()));
        totalSummaryDailyBo.setFirstPayuser(
            totalSummaryDailyBo.getFirstPayuser() + summaryDailyBo.getFirstPayuser());
        totalSummaryDailyBo.setFirstPayuserDev(
            totalSummaryDailyBo.getFirstPayuserDev() + summaryDailyBo.getFirstPayuserDev());
        totalSummaryDailyBo.setFirstOrder(
            totalSummaryDailyBo.getFirstOrder() + summaryDailyBo.getFirstOrder());
        totalSummaryDailyBo.setFirstOrderDev(
            totalSummaryDailyBo.getFirstOrderDev() + summaryDailyBo.getFirstOrderDev());
        totalSummaryDailyBo.setTotalMoney(
            totalSummaryDailyBo.getTotalMoney().add(summaryDailyBo.getTotalMoney()));
        totalSummaryDailyBo.setTotalMoneyDev(
            totalSummaryDailyBo.getTotalMoneyDev().add(summaryDailyBo.getTotalMoneyDev()));
        totalSummaryDailyBo.setTotalPayuser(
            totalSummaryDailyBo.getTotalPayuser() + summaryDailyBo.getTotalPayuser());
        totalSummaryDailyBo.setTotalPayuserDev(
            totalSummaryDailyBo.getTotalPayuserDev() + summaryDailyBo.getTotalPayuserDev());
        totalSummaryDailyBo.setCountDau(
            totalSummaryDailyBo.getCountDau() + summaryDailyBo.getCountDau());
        totalSummaryDailyBo.setCountDauDev(
            totalSummaryDailyBo.getCountDauDev() + summaryDailyBo.getCountDauDev());
        totalSummaryDailyBo.setAlivePayuser(
            totalSummaryDailyBo.getAlivePayuser() + summaryDailyBo.getAlivePayuser());
        totalSummaryDailyBo.setAlivePayuserDev(
            totalSummaryDailyBo.getAlivePayuserDev() + summaryDailyBo.getAlivePayuserDev());
        totalSummaryDailyBo.setAliveMoney(
            totalSummaryDailyBo.getAliveMoney().add(summaryDailyBo.getAliveMoney()));
        totalSummaryDailyBo.setAliveMoneyDev(
            totalSummaryDailyBo.getAliveMoneyDev().add(summaryDailyBo.getAliveMoneyDev()));
        totalSummaryDailyBo.setAliveOrder(
            totalSummaryDailyBo.getAliveOrder() + summaryDailyBo.getAliveOrder());
        totalSummaryDailyBo.setLoyal2(totalSummaryDailyBo.getLoyal2() + summaryDailyBo.getLoyal2());
        totalSummaryDailyBo.setLoyal3(totalSummaryDailyBo.getLoyal3() + summaryDailyBo.getLoyal3());
        totalSummaryDailyBo.setLoyal4(totalSummaryDailyBo.getLoyal4() + summaryDailyBo.getLoyal4());
        totalSummaryDailyBo.setLoyal5(totalSummaryDailyBo.getLoyal5() + summaryDailyBo.getLoyal5());
        totalSummaryDailyBo.setLoyal6(totalSummaryDailyBo.getLoyal6() + summaryDailyBo.getLoyal6());
        totalSummaryDailyBo.setLoyal7(totalSummaryDailyBo.getLoyal7() + summaryDailyBo.getLoyal7());
        totalSummaryDailyBo.setLoyal2Dev(
            totalSummaryDailyBo.getLoyal2Dev() + summaryDailyBo.getLoyal2Dev());
        totalSummaryDailyBo.setLoyal3Dev(
            totalSummaryDailyBo.getLoyal3Dev() + summaryDailyBo.getLoyal3Dev());
        totalSummaryDailyBo.setLoyal4Dev(
            totalSummaryDailyBo.getLoyal4Dev() + summaryDailyBo.getLoyal4Dev());
        totalSummaryDailyBo.setLoyal5Dev(
            totalSummaryDailyBo.getLoyal5Dev() + summaryDailyBo.getLoyal5Dev());
        totalSummaryDailyBo.setLoyal6Dev(
            totalSummaryDailyBo.getLoyal6Dev() + summaryDailyBo.getLoyal6Dev());
        totalSummaryDailyBo.setLoyal7Dev(
            totalSummaryDailyBo.getLoyal7Dev() + summaryDailyBo.getLoyal7Dev());
        totalSummaryDailyBo.setPayback1(
            totalSummaryDailyBo.getPayback1().add(summaryDailyBo.getPayback1()));
        totalSummaryDailyBo.setPayback2(
            totalSummaryDailyBo.getPayback2().add(summaryDailyBo.getPayback2()));
        totalSummaryDailyBo.setPayback3(
            totalSummaryDailyBo.getPayback3().add(summaryDailyBo.getPayback3()));
        totalSummaryDailyBo.setPayback4(
            totalSummaryDailyBo.getPayback4().add(summaryDailyBo.getPayback4()));
        totalSummaryDailyBo.setPayback5(
            totalSummaryDailyBo.getPayback5().add(summaryDailyBo.getPayback5()));
        totalSummaryDailyBo.setPayback6(
            totalSummaryDailyBo.getPayback6().add(summaryDailyBo.getPayback6()));
        totalSummaryDailyBo.setPayback7(
            totalSummaryDailyBo.getPayback7().add(summaryDailyBo.getPayback7()));
        totalSummaryDailyBo.setPayback1Dev(
            totalSummaryDailyBo.getPayback1Dev().add(summaryDailyBo.getPayback1Dev()));
        totalSummaryDailyBo.setPayback2Dev(
            totalSummaryDailyBo.getPayback2Dev().add(summaryDailyBo.getPayback2Dev()));
        totalSummaryDailyBo.setPayback3Dev(
            totalSummaryDailyBo.getPayback3Dev().add(summaryDailyBo.getPayback3Dev()));
        totalSummaryDailyBo.setPayback4Dev(
            totalSummaryDailyBo.getPayback4Dev().add(summaryDailyBo.getPayback4Dev()));
        totalSummaryDailyBo.setPayback5Dev(
            totalSummaryDailyBo.getPayback5Dev().add(summaryDailyBo.getPayback5Dev()));
        totalSummaryDailyBo.setPayback6Dev(
            totalSummaryDailyBo.getPayback6Dev().add(summaryDailyBo.getPayback6Dev()));
        totalSummaryDailyBo.setPayback7Dev(
            totalSummaryDailyBo.getPayback7Dev().add(summaryDailyBo.getPayback7Dev()));
    }

}
