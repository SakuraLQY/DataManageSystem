package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.CountUtil;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.entity.CtDailyPaybackDev;
import org.jeecg.modules.count.mapper.CtDailyPaybackDevMapper;
import org.jeecg.modules.count.service.ICtDailyPaybackDevService;
import org.springframework.stereotype.Service;

/**
 * @Description: ct_daily_payback_dev
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_countly")
public class CtDailyPaybackDevServiceImpl extends
    ServiceImpl<CtDailyPaybackDevMapper, CtDailyPaybackDev> implements ICtDailyPaybackDevService {

    @Override
    public void parsePaybackDev(Integer dailyId, Date deviceDate, BigDecimal money, Date logDate) {
        CtDailyPaybackDev ctDailyPaybackDev = getOne(
            new LambdaQueryWrapper<CtDailyPaybackDev>().eq(CtDailyPaybackDev::getDailyId, dailyId));
        // 比较两个日期 日志时间-用户创建时间
        int days = DateUtils.dateToDiff('d', logDate, deviceDate);
        if (days < 0 || days > 149) {
            // 超出时间范围 不记录
            return;
        }
        String dayStr = "day" + (days + 1);
        if (null == ctDailyPaybackDev) {
            ctDailyPaybackDev = new CtDailyPaybackDev();
            ctDailyPaybackDev.setCreateTime(logDate);
            ctDailyPaybackDev.setUpdateTime(logDate);
            setCtDailyPaybackDevDay(ctDailyPaybackDev, dailyId, dayStr, money);
            save(ctDailyPaybackDev);
        } else {
            ctDailyPaybackDev.setUpdateTime(logDate);
            setCtDailyPaybackDevDay(ctDailyPaybackDev, dailyId, dayStr, money);
            updateById(ctDailyPaybackDev);
        }
    }

    /**
     * @param ctDailyPaybackDev
     * @param dailyId
     * @param dayStr
     * @param money
     * @author chenyw
     * @description 更新对应日期字段
     * @date 11:02 2023/4/21/021
     **/
    private void setCtDailyPaybackDevDay(CtDailyPaybackDev ctDailyPaybackDev, Integer dailyId,
        String dayStr, BigDecimal money) {
        Class<CtDailyPaybackDev> ctDailyLoyalClass = CtDailyPaybackDev.class;
        ctDailyPaybackDev.setDailyId(dailyId);
        Field declaredField = null;
        try {
            declaredField = ctDailyLoyalClass
                .getDeclaredField(dayStr);
        } catch (NoSuchFieldException e) {
            log.error("通过反射获取DailyPaybackDev天数字段异常", e);
            throw new JeecgBootException("通过反射获取DailyPaybackDev天数字段异常");
        }
        declaredField.setAccessible(true);
        try {
            BigDecimal value = (BigDecimal) declaredField.get(ctDailyPaybackDev);
            declaredField.set(ctDailyPaybackDev, CountUtil.addDec(value, money));
        } catch (IllegalAccessException e) {
            log.error("通过反射设置DailyPaybackDev天数字段异常", e);
            throw new JeecgBootException("通过反射设置DailyPaybackDev天数字段异常");
        }
    }
}
