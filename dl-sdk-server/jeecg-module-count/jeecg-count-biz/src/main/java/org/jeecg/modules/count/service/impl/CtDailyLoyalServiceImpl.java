package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.entity.CtDailyLoyal;
import org.jeecg.modules.count.mapper.CtDailyLoyalMapper;
import org.jeecg.modules.count.mapper.CtDailyMapper;
import org.jeecg.modules.count.service.ICtDailyLoyalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: ct_daily_loyal
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtDailyLoyalServiceImpl extends ServiceImpl<CtDailyLoyalMapper, CtDailyLoyal> implements ICtDailyLoyalService {


    @Autowired
    private CtDailyMapper ctDailyMapper;

    @Autowired
    private CtDailyLoyalMapper ctDailyLoyalMapper;

    public void updateLoginDaily(ParseLoginDto parseLoginDto,String daysNum,Date timeDate) {

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

        LambdaQueryWrapper<CtDailyLoyal> ctDailyLoyalLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctDailyLoyalLambdaQueryWrapper.eq(CtDailyLoyal::getDailyId,ctDaily.getId());
        CtDailyLoyal ctDailyLoyal = ctDailyLoyalMapper.selectOne(ctDailyLoyalLambdaQueryWrapper);

        if (ObjectUtils.isEmpty(ctDailyLoyal)) {

            Class<CtDailyLoyal> ctDailyLoyalClass = CtDailyLoyal.class;

            CtDailyLoyal insertCtDailyLoyal = new CtDailyLoyal();

            Field declaredField = null;
            try {
                declaredField = ctDailyLoyalClass
                    .getDeclaredField("day" + daysNum);
            } catch (NoSuchFieldException e) {
                log.error("通过反射获取CtDailyLoyal天数字段异常", e);
                throw new JeecgBootException("通过反射获取CtDailyLoyal天数字段异常");
            }
            declaredField.setAccessible(true);
            try {
                declaredField.set(insertCtDailyLoyal,1);
            } catch (IllegalAccessException e) {
                log.error("通过反射设置CtDailyLoyal天数字段异常", e);
                throw new JeecgBootException("通过反射设置CtDailyLoyal天数字段异常");
            }

            insertCtDailyLoyal.setDailyId(ctDaily.getId());
            insertCtDailyLoyal.setCreateTime(day);
            insertCtDailyLoyal.setUpdateTime(day);
            ctDailyLoyalMapper.insert(insertCtDailyLoyal);
        } else{

            Class<CtDailyLoyal> ctDailyLoyalClass = CtDailyLoyal.class;

            Field declaredField = null;
            try {
                declaredField = ctDailyLoyalClass
                    .getDeclaredField("day" + daysNum);
            } catch (NoSuchFieldException e) {
                log.error("通过反射获取CtDailyLoyal天数字段异常", e);
                throw new JeecgBootException("通过反射获取CtDailyLoyal天数字段异常");
            }
            declaredField.setAccessible(true);

            try {
                Integer num = (Integer)declaredField.get(ctDailyLoyal);
                declaredField.set(ctDailyLoyal,num+1);
            } catch (IllegalAccessException e) {
                log.error("通过反射设置CtDailyLoyal天数字段异常", e);
                throw new JeecgBootException("通过反射设置CtDailyLoyal天数字段异常");
            }

            ctDailyLoyal.setUpdateTime(time);
            ctDailyLoyalMapper.updateById(ctDailyLoyal);
        }
    }
}
