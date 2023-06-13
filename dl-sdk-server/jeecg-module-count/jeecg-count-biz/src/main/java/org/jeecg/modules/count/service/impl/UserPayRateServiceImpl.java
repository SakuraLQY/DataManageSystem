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
import org.jeecg.common.count.vo.CostMoneyModel;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.bo.GetOrderGroupBo;
import org.jeecg.modules.count.bo.SummaryDailyBo;
import org.jeecg.modules.count.bo.SummaryOrderBo;
import org.jeecg.modules.count.bo.SummaryOrderDevBo;
import org.jeecg.modules.count.bo.UserPayRateDailyBo;
import org.jeecg.modules.count.constant.enums.SummaryEnum;
import org.jeecg.modules.count.dto.SummaryDealInfoDataDto;
import org.jeecg.modules.count.dto.SummaryDto;
import org.jeecg.modules.count.dto.UserPayRateDto;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.service.ICtOrderService;
import org.jeecg.modules.count.service.ISummaryService;
import org.jeecg.modules.count.service.IUserPayRateService;
import org.jeecg.modules.count.vo.SummaryDealInfoDataVo;
import org.jeecg.modules.count.vo.SummaryDealSameAccountVo;
import org.jeecg.modules.count.vo.SummaryDealSevenDayVo;
import org.jeecg.modules.count.vo.SummaryVo;
import org.jeecg.modules.count.vo.UserPayRateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPayRateServiceImpl implements IUserPayRateService {

    @Autowired
    private ICtDailyService ctDailyService;
    @Autowired
    private ICtOrderService ctOrderService;

    @Override
    public List<UserPayRateVo> getPayRate(UserPayRateDto userPayRateDto) {
        List<UserPayRateVo> result = new ArrayList<>();
        List<UserPayRateDailyBo> userPayRateDailyBoList = ctDailyService.getUserPayRateDaily(
            userPayRateDto);
        GetOrderGroupBo getOrderGroupBo = new GetOrderGroupBo();
        BeanUtils.copyProperties(userPayRateDto, getOrderGroupBo);
        Map<String, BigDecimal> moneyMap = ctOrderService.getMoneyGroupRegTimeCreateTime(
            getOrderGroupBo);
        Map<String, Integer> numMap = ctOrderService.getNumGroupRegTimeCreateTime(getOrderGroupBo);
        UserPayRateDailyBo totalUserPayRateDailyBo = new UserPayRateDailyBo();
        String totalId = "合计";
        totalUserPayRateDailyBo.setDay(totalId);
        Map<String, BigDecimal> totalMoneyMap = ctOrderService.getMoneyGroupRegTimeCreateTime(
            getOrderGroupBo);
        Map<String, Integer> totalNumMap = ctOrderService.getNumGroupRegTimeCreateTime(
            getOrderGroupBo);
        // 需要计算的key
        String[] keys = {"-1","-2", "-3", "-4", "-5", "-6", "-14", "-29", "-44", "-59"};
        // 初始化合计map
        for (String key : keys) {
            totalNumMap.put(totalId + key, 0);
            totalMoneyMap.put(totalId + key, BigDecimal.ZERO);
        }
        for (UserPayRateDailyBo userPayRateDailyBo : userPayRateDailyBoList) {
            UserPayRateVo userPayRateVo = calcPayRate(userPayRateDailyBo, moneyMap, numMap);
            result.add(userPayRateVo);
            // 计算合计bo
            totalUserPayRateDailyBo.setCountUser(
                totalUserPayRateDailyBo.getCountUser() + userPayRateDailyBo.getCountUser());
            totalUserPayRateDailyBo.setFirstPayuser(
                totalUserPayRateDailyBo.getFirstPayuser() + userPayRateDailyBo.getFirstPayuser());
            totalUserPayRateDailyBo.setFirstMoney(
                totalUserPayRateDailyBo.getFirstMoney().add(userPayRateDailyBo.getFirstMoney()));
            totalUserPayRateDailyBo.setLoyal2(
                totalUserPayRateDailyBo.getLoyal2() + userPayRateDailyBo.getLoyal2());
            totalUserPayRateDailyBo.setLoyal3(
                totalUserPayRateDailyBo.getLoyal3() + userPayRateDailyBo.getLoyal3());
            totalUserPayRateDailyBo.setLoyal4(
                totalUserPayRateDailyBo.getLoyal4() + userPayRateDailyBo.getLoyal4());
            totalUserPayRateDailyBo.setLoyal5(
                totalUserPayRateDailyBo.getLoyal5() + userPayRateDailyBo.getLoyal5());
            totalUserPayRateDailyBo.setLoyal6(
                totalUserPayRateDailyBo.getLoyal6() + userPayRateDailyBo.getLoyal6());
            totalUserPayRateDailyBo.setLoyal7(
                totalUserPayRateDailyBo.getLoyal7() + userPayRateDailyBo.getLoyal7());
            totalUserPayRateDailyBo.setLoyal15(
                totalUserPayRateDailyBo.getLoyal15() + userPayRateDailyBo.getLoyal15());
            totalUserPayRateDailyBo.setLoyal30(
                totalUserPayRateDailyBo.getLoyal30() + userPayRateDailyBo.getLoyal30());
            totalUserPayRateDailyBo.setLoyal45(
                totalUserPayRateDailyBo.getLoyal45() + userPayRateDailyBo.getLoyal45());
            totalUserPayRateDailyBo.setLoyal60(
                totalUserPayRateDailyBo.getLoyal60() + userPayRateDailyBo.getLoyal60());
            for (String key : keys) {
                if (totalMoneyMap.get(userPayRateDailyBo.getDay() + key) != null) {
                    totalMoneyMap.put(totalId + key, totalMoneyMap.get(totalId + key)
                        .add(totalMoneyMap.get(userPayRateDailyBo.getDay() + key)));
                }
                if (totalNumMap.get(userPayRateDailyBo.getDay() + key) != null) {
                    totalNumMap.put(totalId + key, totalNumMap.get(totalId + key) + totalNumMap.get(
                        userPayRateDailyBo.getDay() + key));
                }
            }
        }
        UserPayRateVo userPayRateVo = calcPayRate(totalUserPayRateDailyBo, totalMoneyMap, totalNumMap);
        // 合计
        result.add(userPayRateVo);
        return result;
    }

    /**
     * @param userPayRateDailyBo
     * @param moneyMap
     * @param numMap
     * @return org.jeecg.modules.count.vo.UserPayRateVo
     * @author chenyw
     * @description 计算单条
     * @date 19:20 2023/5/17/017
     **/
    private UserPayRateVo calcPayRate(UserPayRateDailyBo userPayRateDailyBo,
        Map<String, BigDecimal> moneyMap, Map<String, Integer> numMap) {
        UserPayRateVo userPayRateVo = new UserPayRateVo();
        BeanUtils.copyProperties(userPayRateDailyBo, userPayRateVo);
        // 首日数据
        if (userPayRateDailyBo.getCountUser() != 0) {
            userPayRateVo.setUserPayRate1(BigDecimal.valueOf(userPayRateDailyBo.getFirstPayuser())
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getCountUser()), 2,
                    RoundingMode.HALF_UP));
        }
        if (userPayRateDailyBo.getFirstPayuser() != 0) {
            userPayRateVo.setArppu1(userPayRateDailyBo.getFirstMoney()
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getFirstPayuser()), 2,
                    RoundingMode.HALF_UP));
        }
        // 次日数据
        Integer num = numMap.get(userPayRateDailyBo.getDay() + "-1");
        BigDecimal money = moneyMap.get(userPayRateDailyBo.getDay() + "-1");
        if (userPayRateDailyBo.getLoyal2() != 0 && num != null) {
            userPayRateVo.setUserPayRate2(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal2()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu2(money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 三日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-2");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-2");
        if (userPayRateDailyBo.getLoyal3() != 0 && num != null) {
            userPayRateVo.setUserPayRate3(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal3()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu3(money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 四日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-3");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-3");
        if (userPayRateDailyBo.getLoyal4() != 0 && num != null) {
            userPayRateVo.setUserPayRate4(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal4()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu4(money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 五日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-4");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-4");
        if (userPayRateDailyBo.getLoyal5() != 0 && num != null) {
            userPayRateVo.setUserPayRate5(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal5()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu5(money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 六日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-5");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-5");
        if (userPayRateDailyBo.getLoyal6() != 0 && num != null) {
            userPayRateVo.setUserPayRate6(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal6()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu6(money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 七日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-6");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-6");
        if (userPayRateDailyBo.getLoyal7() != 0 && num != null) {
            userPayRateVo.setUserPayRate7(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal7()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu7(money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 15日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-14");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-14");
        if (userPayRateDailyBo.getLoyal15() != 0 && num != null) {
            userPayRateVo.setUserPayRate15(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal15()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu15(
                money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 30日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-29");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-29");
        if (userPayRateDailyBo.getLoyal30() != 0 && num != null) {
            userPayRateVo.setUserPayRate30(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal30()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu30(
                money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 45日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-44");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-44");
        if (userPayRateDailyBo.getLoyal45() != 0 && num != null) {
            userPayRateVo.setUserPayRate45(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal45()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu45(
                money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        // 60日数据
        num = numMap.get(userPayRateDailyBo.getDay() + "-59");
        money = moneyMap.get(userPayRateDailyBo.getDay() + "-59");
        if (userPayRateDailyBo.getLoyal60() != 0 && num != null) {
            userPayRateVo.setUserPayRate60(BigDecimal.valueOf(num).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(userPayRateDailyBo.getLoyal60()), 2,
                    RoundingMode.HALF_UP));
        }
        if (num != null && num != 0 && money != null && !BigDecimal.ZERO.equals(money)) {
            userPayRateVo.setArppu60(
                money.divide(BigDecimal.valueOf(num), 2, RoundingMode.HALF_UP));
        }
        return userPayRateVo;
    }

}
