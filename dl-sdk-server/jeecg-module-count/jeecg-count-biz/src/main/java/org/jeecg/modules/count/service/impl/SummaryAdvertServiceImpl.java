package org.jeecg.modules.count.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.count.bo.GetHourBo;
import org.jeecg.modules.count.bo.GetOrderRateBo;
import org.jeecg.modules.count.bo.HourBo;
import org.jeecg.modules.count.bo.SummaryAdvertDailyBo;
import org.jeecg.modules.count.dto.SummaryAdvertDto;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.service.ICtHourService;
import org.jeecg.modules.count.service.ICtOrderService;
import org.jeecg.modules.count.service.ISummaryAdvertService;
import org.jeecg.modules.count.vo.OrderMoneyGroupRateVo;
import org.jeecg.modules.count.vo.SummaryAdvertBarVo;
import org.jeecg.modules.count.vo.SummaryAdvertVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class SummaryAdvertServiceImpl implements ISummaryAdvertService {

    @Autowired
    private ICtDailyService ctDailyService;
    @Autowired
    private ICtOrderService ctOrderService;
    @Autowired
    private ICtHourService ctHourService;

    @Override
    public List<SummaryAdvertVo> getSummaryAdvert(SummaryAdvertDto summaryAdvertDto) {
        List<SummaryAdvertVo> result = new ArrayList<>();
        // 初始化时间 + 00:00:00
        if (StringUtils.isNotEmpty(summaryAdvertDto.getRegStartTime())) {
            summaryAdvertDto.setRegStartTime(summaryAdvertDto.getRegStartTime() + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(summaryAdvertDto.getRegEndTime())) {
            summaryAdvertDto.setRegEndTime(summaryAdvertDto.getRegEndTime() + " 23:59:59");
        }
        // 获取每日数据
        List<SummaryAdvertDailyBo> summaryDailyBoList = ctDailyService.getSummaryAdvertDaily(
            summaryAdvertDto);
        SummaryAdvertDailyBo totalSummaryAdvertDailyBo = new SummaryAdvertDailyBo();
        totalSummaryAdvertDailyBo.setDay("合计");
        for (SummaryAdvertDailyBo summaryAdvertDailyBo : summaryDailyBoList) {
            // 汇总计算
            SummaryAdvertVo summaryAdvertVo = new SummaryAdvertVo();
            calcSummaryAdvertDailyBo(summaryAdvertDailyBo, summaryAdvertVo);
            calcTotalSummaryAdvertDailyBo(summaryAdvertDailyBo, totalSummaryAdvertDailyBo);
            result.add(summaryAdvertVo);
        }
        // 汇总计算
        SummaryAdvertVo totalSummaryAdvertVo = new SummaryAdvertVo();
        calcSummaryAdvertDailyBo(totalSummaryAdvertDailyBo, totalSummaryAdvertVo);
        result.add(totalSummaryAdvertVo);
        return result;
    }

    @Override
    public SummaryAdvertBarVo getSummaryAdvertEChart(SummaryAdvertDto summaryAdvertDto) {
        SummaryAdvertBarVo result = new SummaryAdvertBarVo();
        GetOrderRateBo getOrderRateBo = new GetOrderRateBo();
        BeanUtils.copyProperties(summaryAdvertDto, getOrderRateBo);
        result.setAliveMoneyRateGroup(ctOrderService.getAliveMoneyGroup(getOrderRateBo));
        result.setFirstMoneyRateGroup(ctOrderService.getFirstMoneyGroup(getOrderRateBo));
        result.setDateRateGroup(ctOrderService.getRegDateGroup(getOrderRateBo));
        return result;
    }

    public Map<String, Map<String, BigDecimal>> getSummaryAdvertLine(
        SummaryAdvertDto summaryAdvertDto) {
        GetHourBo getHourBo = new GetHourBo();
        BeanUtils.copyProperties(summaryAdvertDto, getHourBo);
        String[] levels = {"count_active", "count_active_dev", "count_user", "count_user_dev",
            "first_money", "first_payuser", "total_payuser", "total_money", "alive_payuser",
            "alive_money", "count_dau"};

        Map<String, Map<String, BigDecimal>> result = new HashMap<>();
        for (String level : levels) {
            getHourBo.setLevel(level);
            List<HourBo> hourBoList = ctHourService.getHourBo(getHourBo);
            Map<String, BigDecimal> hour = new HashMap();
            for (HourBo hourBo : hourBoList) {
                hour.put(hourBo.getDay() + " 0时", hourBo.getZeroHour());
                hour.put(hourBo.getDay() + " 1时", hourBo.getOneHour());
                hour.put(hourBo.getDay() + " 2时", hourBo.getTwoHour());
                hour.put(hourBo.getDay() + " 3时", hourBo.getThreeHour());
                hour.put(hourBo.getDay() + " 4时", hourBo.getFourHour());
                hour.put(hourBo.getDay() + " 5时", hourBo.getFiveHour());
                hour.put(hourBo.getDay() + " 6时", hourBo.getSixHour());
                hour.put(hourBo.getDay() + " 7时", hourBo.getSevenHour());
                hour.put(hourBo.getDay() + " 8时", hourBo.getEightHour());
                hour.put(hourBo.getDay() + " 9时", hourBo.getNineHour());
                hour.put(hourBo.getDay() + " 10时", hourBo.getTenHour());
                hour.put(hourBo.getDay() + " 11时", hourBo.getElevenHour());
                hour.put(hourBo.getDay() + " 12时", hourBo.getTwelveHour());
                hour.put(hourBo.getDay() + " 13时", hourBo.getThirteenHour());
                hour.put(hourBo.getDay() + " 14时", hourBo.getThirteenHour());
                hour.put(hourBo.getDay() + " 15时", hourBo.getFifteenHour());
                hour.put(hourBo.getDay() + " 16时", hourBo.getSixteenHour());
                hour.put(hourBo.getDay() + " 17时", hourBo.getSeventeenHour());
                hour.put(hourBo.getDay() + " 18时", hourBo.getEighteenHour());
                hour.put(hourBo.getDay() + " 19时", hourBo.getNineteenHour());
                hour.put(hourBo.getDay() + " 20时", hourBo.getTwentyHour());
                hour.put(hourBo.getDay() + " 21时", hourBo.getTwentyoneHour());
                hour.put(hourBo.getDay() + " 22时", hourBo.getTwentytwoHour());
                hour.put(hourBo.getDay() + " 23时", hourBo.getTwentythreeHour());
            }
            result.put(level, hour);
        }
        return result;
    }


    /**
     * @param summaryAdvertDailyBo
     * @param summaryAdvertVo
     * @author chenyw
     * @description 计算 汇总数据
     * @date 18:26 2023/5/12/012
     **/
    private void calcSummaryAdvertDailyBo(SummaryAdvertDailyBo summaryAdvertDailyBo,
        SummaryAdvertVo summaryAdvertVo) {
        // 同名属性赋值
        BeanUtils.copyProperties(summaryAdvertDailyBo, summaryAdvertVo);
        // 激活注册率 = 注册数/激活数
        if (summaryAdvertDailyBo.getCountActive() != 0) {
            summaryAdvertVo.setCountActiveUserRate(
                BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser())
                    .multiply(new BigDecimal(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountActive()), 2,
                        RoundingMode.HALF_UP));
        }
        // 有效注册率 = 有效注册数/注册数
        if (summaryAdvertDailyBo.getCountUser() != 0) {
            summaryAdvertVo.setCountValidUserCountUserRate(
                BigDecimal.valueOf(summaryAdvertDailyBo.getCountValidUser())
                    .multiply(new BigDecimal(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
        }
        // 新增付费率 = 新增付费人数/注册数
        if (summaryAdvertDailyBo.getCountUser() != 0) {
            summaryAdvertVo.setCountValidUserCountUserRate(
                BigDecimal.valueOf(summaryAdvertDailyBo.getFirstPayuser())
                    .multiply(new BigDecimal(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
        }
        // 新增ARPU = 首日付费额/注册数
        if (summaryAdvertDailyBo.getCountUser() != 0) {
            summaryAdvertVo.setArpu(summaryAdvertDailyBo.getFirstMoney()
                .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
        }
        // 新增ARPPU = 首日付费额/首日付费用户数
        if (summaryAdvertDailyBo.getFirstPayuser() != 0) {
            summaryAdvertVo.setFirstArppu(summaryAdvertDailyBo.getFirstMoney()
                .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getFirstPayuser()), 2,
                    RoundingMode.HALF_UP));
        }
        // 老用户
        // 老用户付费人数
        Integer oldPayuser =
            summaryAdvertDailyBo.getAlivePayuser() - summaryAdvertDailyBo.getFirstPayuser();
        // 老用户活跃数
        Integer oldDau = summaryAdvertDailyBo.getCountDau() - summaryAdvertDailyBo.getCountUser();
        // 老用户支付金额
        BigDecimal oldMoney = summaryAdvertDailyBo.getAliveMoney()
            .subtract(summaryAdvertDailyBo.getFirstMoney());
        // 老用户付费数
        summaryAdvertVo.setOldUserPayuser(oldPayuser);
        // 老用户付费金额
        summaryAdvertVo.setOldUserMoney(oldMoney);
        if (oldDau != 0) {
            // 老用户付费率
            summaryAdvertVo.setOldUserPayRate(
                BigDecimal.valueOf(oldPayuser).multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(oldDau), 2, RoundingMode.HALF_UP));
            // 老用户ARPU
            summaryAdvertVo.setOldUserArpu(
                oldMoney.divide(BigDecimal.valueOf(oldDau), 2, RoundingMode.HALF_UP));
        }
        // 老用户ARPPU
        if (oldPayuser != 0) {
            summaryAdvertVo.setOldUserArppu(oldMoney.multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(oldPayuser), 2, RoundingMode.HALF_UP));
        }
        // ARPU = 活跃付费额/日活
        if (summaryAdvertDailyBo.getCountDau() != 0) {
            summaryAdvertVo.setArpu(summaryAdvertDailyBo.getAliveMoney()
                .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountDau()), 2,
                    RoundingMode.HALF_UP));
        }
        // ARPPU = 活跃付费额/活跃付费人数
        if (summaryAdvertDailyBo.getAlivePayuser() != 0) {
            summaryAdvertVo.setArppu(summaryAdvertDailyBo.getAliveMoney()
                .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getAlivePayuser()), 2,
                    RoundingMode.HALF_UP));
        }
        // 总付费率 =  总付费率 活跃付费人数/DAU
        if (summaryAdvertDailyBo.getCountDau() != 0) {
            summaryAdvertVo.setArppu(BigDecimal.valueOf(summaryAdvertDailyBo.getAlivePayuser())
                .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountDau()), 2,
                    RoundingMode.HALF_UP));
        }
        // 留存
        if (summaryAdvertDailyBo.getCountUser() != 0) {
            summaryAdvertVo.setRetention2(
                BigDecimal.valueOf(summaryAdvertDailyBo.getLoyal2())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryAdvertVo.setRetention3(
                BigDecimal.valueOf(summaryAdvertDailyBo.getLoyal3())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryAdvertVo.setRetention4(
                BigDecimal.valueOf(summaryAdvertDailyBo.getLoyal4())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryAdvertVo.setRetention5(
                BigDecimal.valueOf(summaryAdvertDailyBo.getLoyal5())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryAdvertVo.setRetention6(
                BigDecimal.valueOf(summaryAdvertDailyBo.getLoyal6())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
            summaryAdvertVo.setRetention7(
                BigDecimal.valueOf(summaryAdvertDailyBo.getLoyal7())
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                        RoundingMode.HALF_UP));
        }
        // ltv
        if (summaryAdvertDailyBo.getCountUser() != 0) {
            BigDecimal payback1 = summaryAdvertDailyBo.getPayback1();
            BigDecimal payback2 = summaryAdvertDailyBo.getPayback2().add(payback1);
            BigDecimal payback3 = summaryAdvertDailyBo.getPayback3().add(payback2);
            BigDecimal payback4 = summaryAdvertDailyBo.getPayback4().add(payback3);
            BigDecimal payback5 = summaryAdvertDailyBo.getPayback5().add(payback4);
            BigDecimal payback6 = summaryAdvertDailyBo.getPayback6().add(payback5);
            BigDecimal payback7 = summaryAdvertDailyBo.getPayback7().add(payback6);
            summaryAdvertVo.setLtv1(
                payback1.divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryAdvertVo.setLtv2(
                payback2.divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryAdvertVo.setLtv3(
                payback3.divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryAdvertVo.setLtv4(
                payback4.divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryAdvertVo.setLtv5(
                payback5.divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryAdvertVo.setLtv6(
                payback6.divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
            summaryAdvertVo.setLtv7(
                payback7.divide(BigDecimal.valueOf(summaryAdvertDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
        }

    }

    /**
     * @param summaryAdvertDailyBo
     * @param totalSummaryAdvertDailyBo
     * @author chenyw
     * @description 汇总bo
     * @date 15:48 2023/5/12/012
     **/
    private void calcTotalSummaryAdvertDailyBo(SummaryAdvertDailyBo summaryAdvertDailyBo,
        SummaryAdvertDailyBo totalSummaryAdvertDailyBo) {
        totalSummaryAdvertDailyBo.setCountActive(
            totalSummaryAdvertDailyBo.getCountActive() + summaryAdvertDailyBo.getCountActive());
        totalSummaryAdvertDailyBo.setCountActiveDev(totalSummaryAdvertDailyBo.getCountActiveDev()
            + summaryAdvertDailyBo.getCountActiveDev());
        totalSummaryAdvertDailyBo.setCountUser(
            totalSummaryAdvertDailyBo.getCountUser() + summaryAdvertDailyBo.getCountUser());
        totalSummaryAdvertDailyBo.setCountUserDev(
            totalSummaryAdvertDailyBo.getCountUserDev() + summaryAdvertDailyBo.getCountUserDev());
        totalSummaryAdvertDailyBo.setCountValidUser(totalSummaryAdvertDailyBo.getCountValidUser());
        totalSummaryAdvertDailyBo.setFirstPayuser(
            totalSummaryAdvertDailyBo.getFirstPayuser() + summaryAdvertDailyBo.getFirstPayuser());
        totalSummaryAdvertDailyBo.setFirstMoney(
            totalSummaryAdvertDailyBo.getFirstMoney().add(summaryAdvertDailyBo.getFirstMoney()));
        totalSummaryAdvertDailyBo.setTotalMoney(
            totalSummaryAdvertDailyBo.getTotalMoney().add(summaryAdvertDailyBo.getTotalMoney()));
        totalSummaryAdvertDailyBo.setCountDau(
            totalSummaryAdvertDailyBo.getCountDau() + summaryAdvertDailyBo.getCountDau());
        totalSummaryAdvertDailyBo.setAlivePayuser(
            totalSummaryAdvertDailyBo.getAlivePayuser() + summaryAdvertDailyBo.getAlivePayuser());
        totalSummaryAdvertDailyBo.setAliveMoney(
            totalSummaryAdvertDailyBo.getAliveMoney().add(summaryAdvertDailyBo.getAliveMoney()));
        totalSummaryAdvertDailyBo.setLoyal2(
            totalSummaryAdvertDailyBo.getLoyal2() + summaryAdvertDailyBo.getLoyal2());
        totalSummaryAdvertDailyBo.setLoyal3(
            totalSummaryAdvertDailyBo.getLoyal3() + summaryAdvertDailyBo.getLoyal3());
        totalSummaryAdvertDailyBo.setLoyal4(
            totalSummaryAdvertDailyBo.getLoyal4() + summaryAdvertDailyBo.getLoyal4());
        totalSummaryAdvertDailyBo.setLoyal5(
            totalSummaryAdvertDailyBo.getLoyal5() + summaryAdvertDailyBo.getLoyal5());
        totalSummaryAdvertDailyBo.setLoyal6(
            totalSummaryAdvertDailyBo.getLoyal6() + summaryAdvertDailyBo.getLoyal6());
        totalSummaryAdvertDailyBo.setLoyal7(
            totalSummaryAdvertDailyBo.getLoyal7() + summaryAdvertDailyBo.getLoyal7());
        totalSummaryAdvertDailyBo.setPayback1(
            totalSummaryAdvertDailyBo.getPayback1().add(summaryAdvertDailyBo.getPayback1()));
        totalSummaryAdvertDailyBo.setPayback1(
            totalSummaryAdvertDailyBo.getPayback2().add(summaryAdvertDailyBo.getPayback2()));
        totalSummaryAdvertDailyBo.setPayback1(
            totalSummaryAdvertDailyBo.getPayback3().add(summaryAdvertDailyBo.getPayback3()));
        totalSummaryAdvertDailyBo.setPayback1(
            totalSummaryAdvertDailyBo.getPayback4().add(summaryAdvertDailyBo.getPayback4()));
        totalSummaryAdvertDailyBo.setPayback1(
            totalSummaryAdvertDailyBo.getPayback5().add(summaryAdvertDailyBo.getPayback5()));
        totalSummaryAdvertDailyBo.setPayback1(
            totalSummaryAdvertDailyBo.getPayback6().add(summaryAdvertDailyBo.getPayback6()));
        totalSummaryAdvertDailyBo.setPayback1(
            totalSummaryAdvertDailyBo.getPayback7().add(summaryAdvertDailyBo.getPayback7()));
    }

}
