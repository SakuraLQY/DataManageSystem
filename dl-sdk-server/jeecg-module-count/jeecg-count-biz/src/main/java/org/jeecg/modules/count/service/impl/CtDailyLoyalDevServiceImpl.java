package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.entity.CtDailyLoyalDev;
import org.jeecg.modules.count.mapper.CtDailyLoyalDevMapper;
import org.jeecg.modules.count.mapper.CtDailyMapper;
import org.jeecg.modules.count.service.ICtDailyLoyalDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: ct_daily_loyal_dev
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_countly")
public class CtDailyLoyalDevServiceImpl extends ServiceImpl<CtDailyLoyalDevMapper, CtDailyLoyalDev> implements ICtDailyLoyalDevService {

    @Autowired
    private CtDailyMapper ctDailyMapper;

    @Autowired
    private CtDailyLoyalDevMapper ctDailyLoyalDevMapper;

    public void updateLoginDailyDev(ParseLoginDto parseLoginDto,String devDaysNum,Date timeDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date=simpleDateFormat.format(timeDate);
        String allDate=dateFormat.format(parseLoginDto.getTime());

        Date day= DateUtils.str2Date(date,simpleDateFormat);
        Date time = DateUtils.str2Date(allDate, dateFormat);

        LambdaQueryWrapper<CtDaily> ctDailyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDailyLambdaQueryWrapper.eq(CtDaily::getDealId,parseLoginDto.getDealId());
        ctDailyLambdaQueryWrapper.eq(CtDaily::getTimeDaily,day);
        CtDaily ctDaily = ctDailyMapper.selectOne(ctDailyLambdaQueryWrapper);

        LambdaQueryWrapper<CtDailyLoyalDev> ctDailyLoyalDevLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDailyLoyalDevLambdaQueryWrapper.eq(CtDailyLoyalDev::getDailyId,ctDaily.getId());
        CtDailyLoyalDev ctDailyLoyalDev = ctDailyLoyalDevMapper.selectOne(ctDailyLoyalDevLambdaQueryWrapper);

        if (ObjectUtils.isEmpty(ctDailyLoyalDev)) {

            Class<CtDailyLoyalDev> ctDailyLoyalDevClass = CtDailyLoyalDev.class;

            CtDailyLoyalDev insertCtDailyLoyalDev = new CtDailyLoyalDev();

            Field declaredField = null;
            try {
                declaredField = ctDailyLoyalDevClass
                    .getDeclaredField("day" + devDaysNum);
            } catch (NoSuchFieldException e) {
                log.error("通过反射获取CtDailyLoyalDev字段异常", e);
                throw new JeecgBootException("通过反射获取CtDailyLoyalDev天数字段异常");
            }
            declaredField.setAccessible(true);
            try {
                declaredField.set(insertCtDailyLoyalDev,1);
            } catch (IllegalAccessException e) {
                log.error("通过反射设置CtDailyLoyalDev天数字段异常", e);
                throw new JeecgBootException("通过反射设置CtDailyLoyalDev天数字段异常");
            }

            insertCtDailyLoyalDev.setDailyId(ctDaily.getId());
            insertCtDailyLoyalDev.setCreateTime(day);
            insertCtDailyLoyalDev.setUpdateTime(day);
            ctDailyLoyalDevMapper.insert(insertCtDailyLoyalDev);
        } else{

            Class<CtDailyLoyalDev> ctDailyLoyalDevClass = CtDailyLoyalDev.class;

            Field declaredField = null;
            try {
                declaredField = ctDailyLoyalDevClass
                    .getDeclaredField("day" + devDaysNum);
            } catch (NoSuchFieldException e) {
                log.error("通过反射获取CtDailyLoyalDev天数字段异常", e);
                throw new JeecgBootException("通过反射获取CtDailyLoyalDev天数字段异常");
            }
            declaredField.setAccessible(true);
            try {
                Integer num = (Integer)declaredField.get(ctDailyLoyalDev);
                declaredField.set(ctDailyLoyalDev,num+1);
            } catch (IllegalAccessException e) {
                log.error("通过反射设置CtDailyLoyalDev天数字段异常", e);
                throw new JeecgBootException("通过反射设置CtDailyLoyalDev天数字段异常");
            }
            ctDailyLoyalDev.setUpdateTime(time);
            ctDailyLoyalDevMapper.updateById(ctDailyLoyalDev);
        }
    }
}
