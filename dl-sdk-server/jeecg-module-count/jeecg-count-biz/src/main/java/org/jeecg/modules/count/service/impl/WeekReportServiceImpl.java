package org.jeecg.modules.count.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.advert.dto.OpCostDto;
import org.jeecg.common.count.vo.CostModel;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.bo.GetWeekReportDailyBo;
import org.jeecg.modules.count.bo.ReportConfigWeek;
import org.jeecg.modules.count.bo.ReportConfigWeekData;
import org.jeecg.modules.count.bo.WeekReportDailyBo;
import org.jeecg.modules.count.constant.DivideConstant;
import org.jeecg.modules.count.constant.ReportConfigName;
import org.jeecg.modules.count.entity.CtReportConfig;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.service.ICtReportConfigService;
import org.jeecg.modules.count.service.IWeekReportService;
import org.jeecg.modules.count.vo.WeekReportDataVo;
import org.jeecg.modules.count.vo.WeekReportDetailVo;
import org.jeecg.modules.count.vo.WeekReportMarketVo;
import org.jeecg.modules.count.vo.WeekReportVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeekReportServiceImpl implements IWeekReportService {

    @Autowired
    private ICtReportConfigService ctReportConfigService;
    @Autowired
    private IAdvertApi advertApi;
    @Autowired
    private ICtDailyService ctDailyService;

    @Override
    public WeekReportVo getWeekReportData(String startDate, String endDate) {
        // 初始化返回值
        WeekReportVo result = new WeekReportVo();
        List<WeekReportDataVo> dataVoList = new ArrayList<>();
        List<WeekReportMarketVo> marketVoList = new ArrayList<>();
        List<WeekReportDetailVo> detailVoList = new ArrayList<>();
        result.setData(dataVoList);
        result.setMarket(marketVoList);
        result.setDetail(detailVoList);
        CtReportConfig ctReportConfig = ctReportConfigService.getOne(
            new LambdaQueryWrapper<CtReportConfig>().eq(CtReportConfig::getConfigName,
                ReportConfigName.WEEK_REPORT));
        List<ReportConfigWeek> reportConfigWeeks = new ArrayList<>();
        if (null != ctReportConfig && StringUtils.isNotEmpty(ctReportConfig.getConfig())) {
            reportConfigWeeks = JSONObject.parseArray(ctReportConfig.getConfig(),
                ReportConfigWeek.class);
        }
        // 时间1
        String startDate1 = startDate + " 00:00:00";
        String endDate1 = endDate + " 23:59:59";
        // 计算时间差
        int date = DateUtils.dateToDiff('d', DateUtils.parseDatetime(startDate1),
            DateUtils.parseDatetime(endDate1));
        // 时间2
        String startDate2 =
            DateUtils.formatAddDate(startDate, "yyyy-MM-dd", date - 1) + " 00:00:00";
        String endDate2 = DateUtils.formatAddDate(endDate, "yyyy-MM-dd", date - 1) + " 23:59:59";
        // 时间3
        String startDate3 =
            DateUtils.formatAddDate(startDate, "yyyy-MM-dd", (date - 1) * 2) + " 00:00:00";
        String endDate3 =
            DateUtils.formatAddDate(endDate, "yyyy-MM-dd", (date - 1) * 2) + " 23:59:59";
        // 时间4
        String startDate4 =
            DateUtils.formatAddDate(startDate, "yyyy-MM-dd", (date - 1) * 3) + " 00:00:00";
        String endDate4 =
            DateUtils.formatAddDate(endDate, "yyyy-MM-dd", (date - 1) * 3) + " 23:59:59";
        // 时间4
        String startDate5 =
            DateUtils.formatAddDate(startDate, "yyyy-MM-dd", (date - 1) * 4) + " 00:00:00";
        String endDate5 =
            DateUtils.formatAddDate(endDate, "yyyy-MM-dd", (date - 1) * 4) + " 23:59:59";
        // 组装查询条件
        Set<Integer> gameIdSet = new HashSet<>();
        Set<Integer> subGameIdSet = new HashSet<>();
        Set<Integer> pkgIdSet = new HashSet<>();
        Set<Integer> channelTypeIdSet = new HashSet<>();
        Set<Integer> channelIdSet = new HashSet<>();
        Set<Integer> channelSubAccountIdSet = new HashSet<>();
        for (ReportConfigWeek reportConfigWeek : reportConfigWeeks) {
            for (ReportConfigWeekData reportConfigWeekData : reportConfigWeek.getData()) {
                gameIdSet.addAll(reportConfigWeekData.getGameId());
                subGameIdSet.addAll(reportConfigWeekData.getSubGameId());
                pkgIdSet.addAll(reportConfigWeekData.getPkgId());
                channelTypeIdSet.addAll(reportConfigWeekData.getChannelTypeId());
                channelTypeIdSet.addAll(reportConfigWeekData.getChannelId());
                channelTypeIdSet.addAll(reportConfigWeekData.getChannelSubAccountId());
            }
        }
        // 查询消耗
        OpCostDto opCostDto = new OpCostDto();
        opCostDto.setGameId(new ArrayList<>(gameIdSet));
        opCostDto.setSubGameId(new ArrayList<>(subGameIdSet));
        opCostDto.setPkgId(new ArrayList<>(pkgIdSet));
        opCostDto.setChannelTypeId(new ArrayList<>(channelTypeIdSet));
        opCostDto.setChannelId(new ArrayList<>(channelIdSet));
        opCostDto.setChannelSubAccountId(new ArrayList<>(channelSubAccountIdSet));
        opCostDto.setRegStartTime(startDate5);
        opCostDto.setRegEndTime(endDate1);
        List<CostModel> costModelList = advertApi.getCostModel(opCostDto);
        // 查询每日数据
        GetWeekReportDailyBo getWeekReportDailyBo = new GetWeekReportDailyBo();
        BeanUtils.copyProperties(opCostDto, getWeekReportDailyBo);
        List<WeekReportDailyBo> weekReportDailyBoList = ctDailyService.getWeekReportDaily(
            getWeekReportDailyBo);
        for (ReportConfigWeek reportConfigWeek : reportConfigWeeks) {
            for (ReportConfigWeekData reportConfigWeekData : reportConfigWeek.getData()) {
                // 数据报表
                WeekReportDataVo data = new WeekReportDataVo();
                data.setFirstGroup(reportConfigWeek.getFirstGroup());
                data.setGameNickName(reportConfigWeekData.getGameNickName());
                data.setChannelNickName(reportConfigWeekData.getChannelNickName());
                // 环比详细数据
                WeekReportDetailVo detail2 = new WeekReportDetailVo();
                detail2.setFirstGroup(reportConfigWeek.getFirstGroup());
                detail2.setSecondGroup(reportConfigWeekData.getSecondGroup());
                detail2.setDay(startDate2.substring(0, 10) + "~" + endDate2.substring(0, 10));
                WeekReportDetailVo detail3 = new WeekReportDetailVo();
                detail3.setFirstGroup(reportConfigWeek.getFirstGroup());
                detail3.setSecondGroup(reportConfigWeekData.getSecondGroup());
                detail3.setDay(startDate3.substring(0, 10) + "~" + endDate3.substring(0, 10));
                WeekReportDetailVo detail4 = new WeekReportDetailVo();
                detail4.setFirstGroup(reportConfigWeek.getFirstGroup());
                detail4.setSecondGroup(reportConfigWeekData.getSecondGroup());
                detail4.setDay(startDate4.substring(0, 10) + "~" + endDate4.substring(0, 10));
                WeekReportDetailVo detail5 = new WeekReportDetailVo();
                detail5.setFirstGroup(reportConfigWeek.getFirstGroup());
                detail5.setSecondGroup(reportConfigWeekData.getSecondGroup());
                detail5.setDay(startDate5.substring(0, 10) + "~" + endDate5.substring(0, 10));
                if (CollectionUtil.isNotEmpty(costModelList)) {
                    for (CostModel costModel : costModelList) {
                        if (!isInReportConfigWeek(reportConfigWeekData, costModel.getGameId(),
                            costModel.getSubGameId(), costModel.getPkgId(),
                            costModel.getChannelTypeId(), costModel.getChannelId(),
                            costModel.getChannelSubAccountId())) {
                            continue;
                        }
                        if (DateUtils.isDateInRange(costModel.getCostDay(), startDate1,
                            endDate1)) {
                            data.setCostMoney(data.getCostMoney().add(costModel.getCostMoney()));
                        } else if (DateUtils.isDateInRange(costModel.getCostDay(), startDate2,
                            endDate2)) {
                            detail2.setCostMoney(
                                detail2.getCostMoney().add(costModel.getCostMoney()));
                        } else if (DateUtils.isDateInRange(costModel.getCostDay(), startDate3,
                            endDate3)) {
                            detail3.setCostMoney(
                                detail3.getCostMoney().add(costModel.getCostMoney()));
                        } else if (DateUtils.isDateInRange(costModel.getCostDay(), startDate4,
                            endDate4)) {
                            detail4.setCostMoney(
                                detail4.getCostMoney().add(costModel.getCostMoney()));
                        } else if (DateUtils.isDateInRange(costModel.getCostDay(), startDate5,
                            endDate5)) {
                            detail5.setCostMoney(
                                detail5.getCostMoney().add(costModel.getCostMoney()));
                        }
                    }
                }
                if (CollectionUtil.isNotEmpty(weekReportDailyBoList)) {
                    for (WeekReportDailyBo weekReportDailyBo : weekReportDailyBoList) {
                        if (!isInReportConfigWeek(reportConfigWeekData,
                            weekReportDailyBo.getGameId(),
                            weekReportDailyBo.getSubGameId(), weekReportDailyBo.getPkgId(),
                            weekReportDailyBo.getChannelTypeId(), weekReportDailyBo.getChannelId(),
                            weekReportDailyBo.getChannelSubAccountId())) {
                            continue;
                        }
                        if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                            startDate1, endDate1)) {
                            setDataDaily(data, weekReportDailyBo);
                        } else if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                            startDate2, endDate2)) {
                            setDetailDaily(detail2, weekReportDailyBo);
                        } else if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                            startDate3, endDate3)) {
                            setDetailDaily(detail3, weekReportDailyBo);
                        } else if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                            startDate4, endDate4)) {
                            setDetailDaily(detail4, weekReportDailyBo);
                        } else if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                            startDate5, endDate5)) {
                            setDetailDaily(detail5, weekReportDailyBo);
                        }
                    }
                }
                calcData(data);
                calcDetail(detail2);
                calcDetail(detail3);
                calcDetail(detail4);
                calcDetail(detail5);
                // 装数据
                dataVoList.add(data);
                detailVoList.add(detail5);
                detailVoList.add(detail4);
                detailVoList.add(detail3);
                detailVoList.add(detail2);
            }
        }
        // 数据表汇总
        WeekReportDataVo dataTotal = new WeekReportDataVo();
        dataTotal.setFirstGroup("合计汇总");
        // 环比数据汇总
        WeekReportDetailVo detailTotal2 = new WeekReportDetailVo();
        detailTotal2.setFirstGroup("合计");
        detailTotal2.setDay(startDate2.substring(0, 10) + "~" + endDate2.substring(0, 10));
        WeekReportDetailVo detailTotal3 = new WeekReportDetailVo();
        detailTotal3.setFirstGroup("合计");
        detailTotal3.setDay(startDate3.substring(0, 10) + "~" + endDate3.substring(0, 10));
        WeekReportDetailVo detailTotal4 = new WeekReportDetailVo();
        detailTotal4.setFirstGroup("合计");
        detailTotal4.setDay(startDate4.substring(0, 10) + "~" + endDate4.substring(0, 10));
        WeekReportDetailVo detailTotal5 = new WeekReportDetailVo();
        detailTotal5.setFirstGroup("合计");
        detailTotal5.setDay(startDate5.substring(0, 10) + "~" + endDate5.substring(0, 10));
        // 计算大盘数据 &  数据报表合计汇总
        WeekReportMarketVo market1 = new WeekReportMarketVo();
        market1.setDay(startDate1.substring(0, 10) + "~" + endDate1.substring(0, 10));
        WeekReportMarketVo market2 = new WeekReportMarketVo();
        market2.setDay(startDate2.substring(0, 10) + "~" + endDate2.substring(0, 10));
        WeekReportMarketVo marketTotal = new WeekReportMarketVo();
        marketTotal.setDay("增长幅度");
        for (CostModel costModel : costModelList) {
            if (DateUtils.isDateInRange(costModel.getCostDay(), startDate1,
                endDate1)) {
                market1.setCostMoney((new BigDecimal(market1.getCostMoney())).add(
                    costModel.getCostMoney()).toString());
                // 数据报表-汇总
                dataTotal.setCostMoney(
                    dataTotal.getCostMoney().add(costModel.getCostMoney()));
            } else if (DateUtils.isDateInRange(costModel.getCostDay(), startDate2,
                endDate2)) {
                market2.setCostMoney((new BigDecimal(market2.getCostMoney())).add(
                    costModel.getCostMoney()).toString());
                detailTotal2.setCostMoney(
                    detailTotal2.getCostMoney().add(costModel.getCostMoney()));
            } else if (DateUtils.isDateInRange(costModel.getCostDay(), startDate3,
                endDate3)) {
                detailTotal3.setCostMoney(
                    detailTotal3.getCostMoney().add(costModel.getCostMoney()));
            } else if (DateUtils.isDateInRange(costModel.getCostDay(), startDate4,
                endDate4)) {
                detailTotal4.setCostMoney(
                    detailTotal4.getCostMoney().add(costModel.getCostMoney()));
            } else if (DateUtils.isDateInRange(costModel.getCostDay(), startDate5,
                endDate5)) {
                detailTotal5.setCostMoney(
                    detailTotal5.getCostMoney().add(costModel.getCostMoney()));
            }

        }
        for (WeekReportDailyBo weekReportDailyBo : weekReportDailyBoList) {
            if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(), startDate1,
                endDate1)) {
                setMarketDaily(market1, weekReportDailyBo);
                // 数据报表-汇总
                setDataDaily(dataTotal, weekReportDailyBo);
            } else if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                startDate2, endDate2)) {
                setMarketDaily(market2, weekReportDailyBo);
                setDetailDaily(detailTotal2, weekReportDailyBo);
            } else if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                startDate3, endDate3)) {
                setDetailDaily(detailTotal3, weekReportDailyBo);
            } else if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                startDate4, endDate4)) {
                setDetailDaily(detailTotal4, weekReportDailyBo);
            } else if (DateUtils.isDateInRange(weekReportDailyBo.getTimeDaily(),
                startDate5, endDate5)) {
                setDetailDaily(detailTotal5, weekReportDailyBo);
            }

        }
        // 计算汇总
        calcMarket(market1);
        calcMarket(market2);
        sumMarket(market1, market2, marketTotal);
        calcData(dataTotal);
        calcDetail(detailTotal2);
        calcDetail(detailTotal3);
        calcDetail(detailTotal4);
        calcDetail(detailTotal5);
        // 装数据
        marketVoList.add(market2);
        marketVoList.add(market1);
        marketVoList.add(marketTotal);
        dataVoList.add(dataTotal);
        detailVoList.add(detailTotal5);
        detailVoList.add(detailTotal4);
        detailVoList.add(detailTotal3);
        detailVoList.add(detailTotal2);
        return result;
    }

    /**
     * @param market1
     * @param market2
     * @param marketTotal
     * @author chenyw
     * @description 大盘同期环比数据 合计
     * @date 14:11 2023/5/24/024
     **/
    private void sumMarket(WeekReportMarketVo market1, WeekReportMarketVo market2,
        WeekReportMarketVo marketTotal) {
        BigDecimal market1CostMoney = new BigDecimal(market1.getCostMoney());
        BigDecimal market2CostMoney = new BigDecimal(market2.getCostMoney());
        if (BigDecimal.ZERO.compareTo(market1CostMoney) != 0 && BigDecimal.ZERO.compareTo(
            market2CostMoney) != 0) {
            marketTotal.setCostMoney(market1CostMoney.subtract(market2CostMoney)
                .divide(market2CostMoney, 2, RoundingMode.HALF_UP) + "%");
        }
        BigDecimal market1CountUser = new BigDecimal(market1.getCountUser());
        BigDecimal market2CountUser = new BigDecimal(market2.getCountUser());
        if (BigDecimal.ZERO.compareTo(market1CountUser) != 0 && BigDecimal.ZERO.compareTo(
            market2CountUser) != 0) {
            marketTotal.setCountUser(market1CountUser.subtract(market2CountUser)
                .divide(market2CountUser, 2, RoundingMode.HALF_UP) + "%");
        }
        BigDecimal market1CountDau = new BigDecimal(market1.getCountDau());
        BigDecimal market2CountDau = new BigDecimal(market2.getCountDau());
        if (BigDecimal.ZERO.compareTo(market1CountDau) != 0
            && BigDecimal.ZERO.compareTo(market2CountDau) != 0) {
            marketTotal.setCountDau(market1CountDau.subtract(market2CountDau)
                .divide(market2CountDau, 2, RoundingMode.HALF_UP) + "%");
        }
        BigDecimal market1FirstMoney = new BigDecimal(market1.getFirstMoney());
        BigDecimal market2FirstMoney = new BigDecimal(market2.getFirstMoney());
        if (BigDecimal.ZERO.compareTo(market1FirstMoney) != 0 && BigDecimal.ZERO.compareTo(
            market2FirstMoney) != 0) {
            marketTotal.setFirstMoney(market1FirstMoney.subtract(market2FirstMoney)
                .divide(market2FirstMoney, 2, RoundingMode.HALF_UP) + "%");
        }
        BigDecimal market1AliveMoney = new BigDecimal(market1.getAliveMoney());
        BigDecimal market2AliveMoney = new BigDecimal(market2.getAliveMoney());
        if (BigDecimal.ZERO.compareTo(market1AliveMoney) != 0
            && BigDecimal.ZERO.compareTo(market2AliveMoney) != 0) {
            marketTotal.setAliveMoney(market1AliveMoney.subtract(market2AliveMoney)
                .divide(market2AliveMoney, 2, RoundingMode.HALF_UP) + "%");
        }
        BigDecimal market1DivideMoney = new BigDecimal(market1.getDivideMoney());
        BigDecimal market2DivideMoney = new BigDecimal(market2.getDivideMoney());
        if (BigDecimal.ZERO.compareTo(market1DivideMoney) != 0 && BigDecimal.ZERO.compareTo(
            market2DivideMoney) != 0) {
            marketTotal.setDivideMoney(market1DivideMoney.subtract(market2AliveMoney)
                .divide(market2DivideMoney, 2, RoundingMode.HALF_UP) + "%");
        }
    }

    /**
     * @param detail
     * @author chenyw
     * @description 计算环比详细数据
     * @date 13:52 2023/5/24/024
     **/
    private void calcDetail(WeekReportDetailVo detail) {
        if (!BigDecimal.ZERO.equals(detail.getCostMoney())) {
            // 首日roi
            detail.setFirstDayRoi(detail.getFirstMoney().multiply(BigDecimal.valueOf(100))
                .subtract(detail.getFirstMoneyIos())
                .divide(detail.getCostMoney(), 2, RoundingMode.HALF_UP));
            // 首周roi 这里放弃ios支付类型
            detail.setFirstWeekRoi(
                detail.getWeekMoney().multiply(BigDecimal.valueOf(100))
                    .divide(detail.getCostMoney(), 2, RoundingMode.HALF_UP));
            // 累计ROI
            detail.setTotalRoi(detail.getTotalRoi().multiply(BigDecimal.valueOf(100))
                .subtract(detail.getTotalMoneyIos())
                .divide(detail.getCostMoney(), 2, RoundingMode.HALF_UP));
        }
        if (detail.getCountUser() != 0) {
            // 单价
            detail.setRegPrice(detail.getCostMoney()
                .divide(BigDecimal.valueOf(detail.getCountUser()), 2, RoundingMode.HALF_UP));
            // ltv
            detail.setLtv1(
                detail.getPayback1().divide(BigDecimal.valueOf(detail.getCountUser())));
            detail.setLtv2(
                detail.getPayback2().divide(BigDecimal.valueOf(detail.getCountUser())));
            detail.setLtv3(
                detail.getPayback3().divide(BigDecimal.valueOf(detail.getCountUser())));
            detail.setLtv4(
                detail.getPayback4().divide(BigDecimal.valueOf(detail.getCountUser())));
            detail.setLtv5(
                detail.getPayback5().divide(BigDecimal.valueOf(detail.getCountUser())));
            detail.setLtv6(
                detail.getPayback6().divide(BigDecimal.valueOf(detail.getCountUser())));
            detail.setLtv7(
                detail.getPayback7().divide(BigDecimal.valueOf(detail.getCountUser())));
            detail.setLtv15(
                detail.getPayback15().divide(BigDecimal.valueOf(detail.getCountUser())));
        }
    }

    /**
     * @param market
     * @author chenyw
     * @description 计算大盘数据
     * @date 11:10 2023/5/24/024
     **/
    private void calcMarket(WeekReportMarketVo market) {
        market.setDivideMoney(new BigDecimal(market.getAliveMoney()).subtract(new BigDecimal(
            market.getAliveMoneyIos())).toString());
    }

    /**
     * @param data
     * @author chenyw
     * @description 数据报表需要计算的数据
     * @date 10:50 2023/5/24/024
     **/
    private void calcData(WeekReportDataVo data) {
        // 新增付费率
        if (data.getCountUser() != 0) {
            data.setFirstPayRate(BigDecimal.valueOf(data.getFirstPayuser())
                .divide(BigDecimal.valueOf(data.getCountUser()), 2, RoundingMode.HALF_UP));
        }
        if (!BigDecimal.ZERO.equals(data.getCostMoney())) {
            // 首日ROI
            data.setFirstRoi(data.getFirstMoney().multiply(BigDecimal.valueOf(100))
                .subtract(data.getFirstMoneyIos().multiply(DivideConstant.IOS))
                .divide(data.getCostMoney(), 2, RoundingMode.HALF_UP));
            // 累计ROI
            data.setTotalRoi(data.getTotalMoney().multiply(BigDecimal.valueOf(100))
                .subtract(data.getTotalMoneyIos().multiply(DivideConstant.IOS))
                .divide(data.getCostMoney(), 2, RoundingMode.HALF_UP));
            // 活跃ROI
            data.setAliveRoi(data.getAliveMoney().multiply(BigDecimal.valueOf(100))
                .subtract(data.getAliveMoneyIos().multiply(DivideConstant.IOS))
                .divide(data.getCostMoney(), 2, RoundingMode.HALF_UP));
        }
        // 利润
        data.setProfit(data.getAliveMoney()
            .subtract(data.getAliveMoneyIos().multiply(DivideConstant.IOS))
            .subtract(data.getCostMoney()).setScale(2, RoundingMode.HALF_UP));
        // 累亏 = 利润/dau
        if (data.getCountDau() != 0) {
            data.setLoss(data.getProfit()
                .divide(BigDecimal.valueOf(data.getCountDau()), 2, RoundingMode.HALF_UP));
        }
    }

    /**
     * @param data
     * @param weekReportDailyBo
     * @author chenyw
     * @description 设置数据报表
     * @date 10:16 2023/5/24/024
     **/
    private void setDataDaily(WeekReportDataVo data, WeekReportDailyBo weekReportDailyBo) {
        data.setCountUser(data.getCountUser() + weekReportDailyBo.getCountUser());
        data.setAliveMoney(
            data.getAliveMoney().add(weekReportDailyBo.getAliveMoney()));
        data.setFirstMoney(
            data.getFirstMoney().add(weekReportDailyBo.getFirstMoney()));
        data.setTotalMoney(data.getTotalMoney().add(weekReportDailyBo.getTotalMoney()));
        data.setFirstMoneyIos(data.getFirstMoneyIos().add(weekReportDailyBo.getFirstMoneyIos()));
        data.setAliveMoneyIos(data.getAliveMoneyIos().add(weekReportDailyBo.getAliveMoneyIos()));
        data.setTotalMoneyIos(data.getTotalMoneyIos().add(weekReportDailyBo.getTotalMoneyIos()));
        data.setCountDau(data.getCountDau() + weekReportDailyBo.getCountDau());
        data.setFirstPayuser(data.getFirstPayuser() + weekReportDailyBo.getFirstpayuser());
    }

    /**
     * @param detail
     * @param weekReportDailyBo
     * @author chenyw
     * @description 设置详细数据 （来自每日数据）
     * @date 17:18 2023/5/23/023
     **/
    private void setDetailDaily(WeekReportDetailVo detail, WeekReportDailyBo weekReportDailyBo) {
        detail.setCountUser(
            detail.getCountUser() + weekReportDailyBo.getCountUser());
        BigDecimal payback1 = weekReportDailyBo.getPayback1();
        BigDecimal payback2 = payback1.add(weekReportDailyBo.getPayback2());
        BigDecimal payback3 = payback2.add(weekReportDailyBo.getPayback3());
        BigDecimal payback4 = payback3.add(weekReportDailyBo.getPayback4());
        BigDecimal payback5 = payback4.add(weekReportDailyBo.getPayback5());
        BigDecimal payback6 = payback5.add(weekReportDailyBo.getPayback6());
        BigDecimal payback7 = payback6.add(weekReportDailyBo.getPayback7());
        BigDecimal payback15 = payback7.add(weekReportDailyBo.getPayback8())
            .add(weekReportDailyBo.getPayback9())
            .add(weekReportDailyBo.getPayback10())
            .add(weekReportDailyBo.getPayback11())
            .add(weekReportDailyBo.getPayback12())
            .add(weekReportDailyBo.getPayback13())
            .add(weekReportDailyBo.getPayback14())
            .add(weekReportDailyBo.getPayback15());
        detail.setPayback1(detail.getPayback1().add(payback1));
        detail.setPayback2(detail.getPayback2().add(payback2));
        detail.setPayback3(detail.getPayback3().add(payback3));
        detail.setPayback4(detail.getPayback4().add(payback4));
        detail.setPayback5(detail.getPayback5().add(payback5));
        detail.setPayback6(detail.getPayback6().add(payback6));
        detail.setPayback7(detail.getPayback7().add(payback7));
        detail.setPayback15(detail.getPayback15().add(payback15));
        detail.setFirstMoney(
            detail.getFirstMoney().add(weekReportDailyBo.getFirstMoney()));
        detail.setTotalMoney(
            detail.getTotalMoney().add(weekReportDailyBo.getTotalMoney()));
        detail.setAliveMoney(detail.getAliveMoney().add(weekReportDailyBo.getAliveMoney()));
        detail.setAliveMoneyIos(
            detail.getAliveMoneyIos().add(weekReportDailyBo.getAliveMoneyIos()));
        detail.setTotalMoneyIos(
            detail.getTotalMoneyIos().add(weekReportDailyBo.getTotalMoneyIos()));
        detail.setFirstMoneyIos(
            detail.getFirstMoneyIos().add(weekReportDailyBo.getFirstMoneyIos()));
        String endTimeStr = detail.getDay().split("~")[1];
        int dayDiff = 0;
        try {
            Date endTime = DateUtils.parseDate(endTimeStr, "YYYY-mm-dd");
            Date timeDaily = DateUtils.parseDatetime(weekReportDailyBo.getTimeDaily());
            dayDiff = DateUtils.dateToDiff('d', endTime, timeDaily);
        } catch (ParseException e) {
            throw new JeecgBootException("解析日期失败");
        }
        if (dayDiff == 1) {
            detail.setWeekMoney(detail.getWeekMoney().add(payback1));
        } else if (dayDiff == 2) {
            detail.setWeekMoney(detail.getWeekMoney().add(payback2));
        } else if (dayDiff == 3) {
            detail.setWeekMoney(detail.getWeekMoney().add(payback3));
        } else if (dayDiff == 4) {
            detail.setWeekMoney(detail.getWeekMoney().add(payback4));
        } else if (dayDiff == 5) {
            detail.setWeekMoney(detail.getWeekMoney().add(payback5));
        } else if (dayDiff == 6) {
            detail.setWeekMoney(detail.getWeekMoney().add(payback6));
        } else if (dayDiff >= 7) {
            detail.setWeekMoney(detail.getWeekMoney().add(payback7));
        }
    }

    /**
     * @param market
     * @param weekReportDailyBo
     * @author chenyw
     * @description 设置大盘数据 （来自每日数据）
     * @date 16:08 2023/5/23/023
     **/
    private void setMarketDaily(WeekReportMarketVo market, WeekReportDailyBo weekReportDailyBo) {
        market.setCountUser(String.valueOf(Integer.valueOf(market.getCountUser()) +
            weekReportDailyBo.getCountUser()));
        market.setCountDau(String.valueOf(Integer.valueOf(market.getCountDau()) +
            weekReportDailyBo.getCountDau()));
        market.setFirstMoney((new BigDecimal(market.getFirstMoney())).add(
            weekReportDailyBo.getFirstMoney()).toString());
        market.setAliveMoney((new BigDecimal(market.getAliveMoney())).add(
            weekReportDailyBo.getAliveMoney()).toString());
        market.setAliveMoneyIos(
            (new BigDecimal(market.getAliveMoneyIos()).add(
                weekReportDailyBo.getAliveMoneyIos())).toString());
    }

    /**
     * @param reportConfigWeekData
     * @param gameId
     * @param subGameId
     * @param pkgId
     * @param channelTypeId
     * @param channelId
     * @param channelSubAccountId
     * @return boolean
     * @author chenyw
     * @description 判断是否在配置里面
     * @date 19:17 2023/5/24/024
     **/
    private boolean isInReportConfigWeek(ReportConfigWeekData reportConfigWeekData, Integer gameId,
        Integer subGameId, Integer pkgId, Integer channelTypeId, Integer channelId,
        Integer channelSubAccountId) {
        if (!judgeContain(gameId, reportConfigWeekData.getGameId())) {
            return false;
        }
        if (!judgeContain(subGameId, reportConfigWeekData.getSubGameId())) {
            return false;
        }
        if (!judgeContain(pkgId, reportConfigWeekData.getPkgId())) {
            return false;
        }
        if (!judgeContain(channelTypeId, reportConfigWeekData.getChannelTypeId())) {
            return false;
        }
        if (!judgeContain(channelId, reportConfigWeekData.getChannelId())) {
            return false;
        }
        if (!judgeContain(channelSubAccountId, reportConfigWeekData.getChannelSubAccountId())) {
            return false;
        }
        return true;
    }

    /**
     * @param id
     * @param idList
     * @return boolean
     * @author chenyw
     * @description 判断id是否在集合里面
     * @date 19:06 2023/5/24/024
     **/
    public boolean judgeContain(Integer id, List<Integer> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return true;
        }
        if (idList.contains(id)) {
            return true;
        }
        return false;
    }

}
