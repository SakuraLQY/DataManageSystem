package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.CountUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.entity.CtDailyPayback;
import org.jeecg.modules.count.mapper.CtDailyPaybackMapper;
import org.jeecg.modules.count.service.ICtDailyPaybackService;
import org.springframework.stereotype.Service;

/**
 * @Description: ct_daily_payback
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtDailyPaybackServiceImpl extends
    ServiceImpl<CtDailyPaybackMapper, CtDailyPayback> implements ICtDailyPaybackService {

    @Override
    public void parsePayback(Integer dailyId, Date userDate, BigDecimal money, Date logDate) {
        CtDailyPayback ctDailyPayback = getOne(
            new LambdaQueryWrapper<CtDailyPayback>().eq(CtDailyPayback::getDailyId, dailyId));
        // 比较两个日期 日志时间-用户创建时间
        int days = DateUtils.dateToDiff('d', logDate, userDate);
        if (days < 0 || days > 149) {
            // 超出时间范围 不记录
            return;
        }
        String dayStr = "day" + (days + 1);
        if (null == ctDailyPayback) {
            ctDailyPayback = new CtDailyPayback();
            ctDailyPayback.setCreateTime(logDate);
            ctDailyPayback.setUpdateTime(logDate);
            setCtDailyPaybackDay(ctDailyPayback, dailyId, dayStr, money);
            save(ctDailyPayback);
        } else {
            ctDailyPayback.setUpdateTime(logDate);
            setCtDailyPaybackDay(ctDailyPayback, dailyId, dayStr, money);
            updateById(ctDailyPayback);
        }
    }

    /**
     * @param ctDailyPayback
     * @param dailyId
     * @param dayStr
     * @param money
     * @author chenyw
     * @description 更新对应日期字段
     * @date 11:02 2023/4/21/021
     **/
    private void setCtDailyPaybackDay(CtDailyPayback ctDailyPayback, Integer dailyId, String dayStr,
        BigDecimal money) {
        Class<CtDailyPayback> ctDailyLoyalClass = CtDailyPayback.class;
        ctDailyPayback.setDailyId(dailyId);
        Field declaredField = null;
        try {
            declaredField = ctDailyLoyalClass
                .getDeclaredField(dayStr);
        } catch (NoSuchFieldException e) {
            log.error("通过反射获取DailyPayback天数字段异常", e);
            throw new JeecgBootException("通过反射获取DailyPayback天数字段异常");
        }
        declaredField.setAccessible(true);
        try {
            BigDecimal value = (BigDecimal) declaredField.get(ctDailyPayback);
            declaredField.set(ctDailyPayback, CountUtil.addDec(value, money));
        } catch (IllegalAccessException e) {
            log.error("设置DailyPayback天数字段异常", e);
            throw new JeecgBootException("通过反射设置DailyPayback天数字段异常");
        }
    }

}
