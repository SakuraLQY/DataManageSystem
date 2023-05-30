package org.jeecg.modules.count.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.count.bo.GetHourBo;
import org.jeecg.modules.count.bo.HourBo;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtHour;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.mapper.CtHourMapper;
import org.jeecg.modules.count.service.ICtHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: ct_hour
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtHourServiceImpl extends ServiceImpl<CtHourMapper, CtHour> implements ICtHourService {

    @Autowired
    private CtHourMapper ctHourMapper;

    @Override
    public void updateStartHour(ParseStartDto parseStartDto, CtDevice ctDevice){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date=simpleDateFormat.format(parseStartDto.getTime());
        String allDate=dateFormat.format(parseStartDto.getTime());

        Date hour=DateUtils.str2Date(date,simpleDateFormat);
        Date allDay=DateUtils.str2Date(allDate,dateFormat);

        LambdaQueryWrapper<CtHour> ctHourLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctHourLambdaQueryWrapper.eq(CtHour::getDealId,parseStartDto.getDealId());
        ctHourLambdaQueryWrapper.eq(CtHour::getTimeHour,hour);
        CtHour ctHour = ctHourMapper.selectOne(ctHourLambdaQueryWrapper);

        if (ObjectUtils.isEmpty(ctHour)){
            CtHour insertCtHour = new CtHour();
            insertCtHour.setTimeHour(hour);
            insertCtHour.setGameId(parseStartDto.getGameId());
            insertCtHour.setPkgId(parseStartDto.getPkgId());
            insertCtHour.setSubGameId(parseStartDto.getSubGameId());
            insertCtHour.setChannelId(parseStartDto.getChannelId());
            insertCtHour.setChannelTypeId(parseStartDto.getChannelTypeId());
            insertCtHour.setChannelSubAccountId(parseStartDto.getChannelSubAccountId());
            insertCtHour.setDealId(parseStartDto.getDealId());
            insertCtHour.setUpdateTime(allDay);
            insertCtHour.setCreateTime(allDay);

            if (ObjectUtils.isEmpty(ctDevice.getStartupTime())){
                insertCtHour.setCountActive(1);
                insertCtHour.setCountActiveDev(1);
            }else {
                if (parseStartDto.getFirstActive()==1){
                    insertCtHour.setCountActive(1);
                }
            }
            ctHourMapper.insert(insertCtHour);
        }else{
            if (ObjectUtils.isEmpty(ctDevice.getStartupTime())){
                ctHour.setCountActive(ctHour.getCountActive()+1);
                ctHour.setCountActiveDev(ctHour.getCountActiveDev()+1);
            }else {
                if (parseStartDto.getFirstActive()==1){
                    ctHour.setCountActive(ctHour.getCountActive()+1);
                }
            }
            ctHour.setUpdateTime(allDay);
            ctHourMapper.updateById(ctHour);
        }
    }


    @Override
    public void updateLoginHourState(ParseLoginDto parseLoginDto,Integer countUser,Integer countUserDev,Integer countDau,Integer countDauDev){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date=simpleDateFormat.format(parseLoginDto.getTime());
        String allDate=dateFormat.format(parseLoginDto.getTime());

        Date hour=DateUtils.str2Date(date,simpleDateFormat);
        Date allDay=DateUtils.str2Date(allDate,dateFormat);

        LambdaQueryWrapper<CtHour> ctHourLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctHourLambdaQueryWrapper.eq(CtHour::getDealId,parseLoginDto.getDealId());
        ctHourLambdaQueryWrapper.eq(CtHour::getTimeHour,hour);
        CtHour ctHour = ctHourMapper.selectOne(ctHourLambdaQueryWrapper);

        if (ObjectUtils.isEmpty(ctHour)){
            CtHour insertCtHour = new CtHour();
            insertCtHour.setTimeHour(hour);
            insertCtHour.setGameId(parseLoginDto.getGameId());
            insertCtHour.setPkgId(parseLoginDto.getPkgId());
            insertCtHour.setSubGameId(parseLoginDto.getSubGameId());
            insertCtHour.setChannelId(parseLoginDto.getChannelId());
            insertCtHour.setChannelTypeId(parseLoginDto.getChannelTypeId());
            insertCtHour.setChannelSubAccountId(parseLoginDto.getChannelSubAccountId());
            insertCtHour.setDealId(parseLoginDto.getDealId());
            insertCtHour.setUpdateTime(allDay);
            insertCtHour.setCreateTime(allDay);

            insertCtHour.setCountUser(countUser);
            insertCtHour.setCountUserDev(countUserDev);
            insertCtHour.setCountDau(countDau);
            insertCtHour.setCountDauDev(countDauDev);
            ctHourMapper.insert(insertCtHour);
        }else{
            ctHour.setUpdateTime(allDay);
            ctHour.setCountUser(ctHour.getCountUser()+countUser);
            ctHour.setCountUserDev(ctHour.getCountUserDev()+countUserDev);
            ctHour.setCountDau(ctHour.getCountDau()+countDau);
            ctHour.setCountDauDev(ctHour.getCountDauDev()+countDauDev);
            ctHourMapper.updateById(ctHour);
        }
    }

    @Override
    public void updateLogin(ParseLoginDto parseLoginDto, CtUser ctUser) {

        Long mask = ctUser.getLoginMask();
        String hourLoyalDay="";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(parseLoginDto.getTime());
        Date timeDay= DateUtils.str2Date(date,simpleDateFormat);

        int days = DateUtils.dateToDiff('d', timeDay, ctUser.getCreateTime());

        if (days>=0 && days<64) {

            if (0 == (mask & (1 << days))) {
                if (59 == days) {
                    hourLoyalDay="60";
                } else if (44 == days) {
                    hourLoyalDay="45";
                } else if (29 == days) {
                    hourLoyalDay="30";
                } else if (14 == days) {
                    hourLoyalDay="15";
                } else if (6 == days) {
                    hourLoyalDay="7";
                } else if (5 == days) {
                    hourLoyalDay="6";
                } else if (4 == days) {
                    hourLoyalDay="5";
                } else if (3 == days) {
                    hourLoyalDay="4";
                } else if (2 == days) {
                    hourLoyalDay="3";
                } else if (1 == days) {
                    hourLoyalDay="2";
                }
            }
        }

        if (hourLoyalDay!=""){
            updateLoginHourLoyal(parseLoginDto,hourLoyalDay,ctUser.getCreateTime());
            // TODO 快手和广点通次日留存数据回传
            if (hourLoyalDay=="2"){

            }
        }

    }

    public void updateLoginHourLoyal(ParseLoginDto parseLoginDto, String hourLoyalDay,Date timeHour) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date=simpleDateFormat.format(timeHour);
        String allDate=dateFormat.format(parseLoginDto.getTime());

        Date timehour= DateUtils.str2Date(date,simpleDateFormat);
        Date time = DateUtils.str2Date(allDate, dateFormat);

        LambdaQueryWrapper<CtHour> ctHourLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctHourLambdaQueryWrapper.eq(CtHour::getDealId,parseLoginDto.getDealId());
        ctHourLambdaQueryWrapper.eq(CtHour::getTimeHour,timehour);
        CtHour ctHour = ctHourMapper.selectOne(ctHourLambdaQueryWrapper);

        Class<CtHour> ctHourClass = CtHour.class;
        Field declaredField = null;
        try {
            declaredField = ctHourClass.getDeclaredField("loyal" + hourLoyalDay);
        } catch (NoSuchFieldException e) {
            log.error("通过反射获取CtHour留存天数字段异常", e);
            throw new JeecgBootException("通过反射获取CtHour留存天数字段异常");
        }
        declaredField.setAccessible(true);
        Integer num = null;
        try {
            num = (Integer)declaredField.get(ctHour);
            declaredField.set(ctHour,num+1);
        } catch (IllegalAccessException e) {
            log.error("通过反射设置CtHour留存天数字段异常", e);
            throw new JeecgBootException("设置反射设置CtHour留存天数字段异常");
        }

        ctHour.setUpdateTime(time);

        ctHourMapper.updateById(ctHour);
    }

    @Override
    public void updateAliveHourState(ParseAliveDto parseAliveDto,Integer countValid,Integer countValidDev,Date userTime){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date=simpleDateFormat.format(parseAliveDto.getTime());
        String time = simpleDateFormat.format(userTime);
        String allDate=dateFormat.format(parseAliveDto.getTime());

        Date day=DateUtils.str2Date(date,simpleDateFormat);
        Date timeHour = DateUtils.str2Date(time, simpleDateFormat);
        Date allDay=DateUtils.str2Date(allDate,dateFormat);

        LambdaQueryWrapper<CtHour> ctHourLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ctHourLambdaQueryWrapper.eq(CtHour::getDealId,parseAliveDto.getDealId());
        ctHourLambdaQueryWrapper.eq(CtHour::getTimeHour,timeHour);

        CtHour ctHour = ctHourMapper.selectOne(ctHourLambdaQueryWrapper);

        if (ObjectUtils.isEmpty(ctHour)){
            CtHour insertCtHour = new CtHour();
            insertCtHour.setTimeHour(day);
            insertCtHour.setGameId(parseAliveDto.getGameId());
            insertCtHour.setPkgId(parseAliveDto.getPkgId());
            insertCtHour.setSubGameId(parseAliveDto.getSubGameId());
            insertCtHour.setChannelId(parseAliveDto.getChannelId());
            insertCtHour.setChannelTypeId(parseAliveDto.getChannelTypeId());
            insertCtHour.setChannelSubAccountId(parseAliveDto.getChannelSubAccountId());
            insertCtHour.setDealId(parseAliveDto.getDealId());
            insertCtHour.setUpdateTime(allDay);
            insertCtHour.setCreateTime(allDay);

            insertCtHour.setCountValidUser(countValid);
            insertCtHour.setCountValidUserDev(countValidDev);
            ctHourMapper.insert(insertCtHour);
        }else{
            ctHour.setUpdateTime(allDay);
            ctHour.setCountValidUser(ctHour.getCountValidUser()+countValid);
            ctHour.setCountValidUserDev(ctHour.getCountValidUserDev()+countValidDev);
            ctHourMapper.updateById(ctHour);
        }
    }

    @Override
    public CtHour initPayHour(ParsePayDto parsePayDto, Date hourDate, Date logTime) {
        Date timeHour = DateUtils.str2Date(
            DateUtils.date_sdf_hour.get().format(hourDate),
            DateUtils.date_sdf_hour.get());
        CtHour ctHour = getOne(new LambdaQueryWrapper<CtHour>()
            .eq(CtHour::getDealId, parsePayDto.getDealId())
            .eq(CtHour::getTimeHour, timeHour));
        if(ctHour == null){
            ctHour = new CtHour();
            ctHour.setGameId(parsePayDto.getGameId());
            ctHour.setSubGameId(parsePayDto.getSubGameId());
            ctHour.setChannelId(parsePayDto.getChannelId());
            ctHour.setChannelTypeId(parsePayDto.getChannelTypeId());
            ctHour.setChannelSubAccountId(parsePayDto.getChannelSubAccountId());
            ctHour.setDealId(parsePayDto.getDealId());
            ctHour.setPkgId(parsePayDto.getPkgId());
            ctHour.setTimeHour(timeHour);
            ctHour.setCreateTime(logTime);
            ctHour.setUpdateTime(logTime);
        }
        return ctHour;
    }

    @Override
    public List<HourBo> getHourBo(GetHourBo getHourBo) {
        QueryWrapper<CtHour> wrapper = new QueryWrapper();
        wrapper.in(CollectionUtil.isNotEmpty(getHourBo.getGameId()), "game_id",
            getHourBo.getGameId());
        wrapper.in(CollectionUtil.isNotEmpty(getHourBo.getSubGameId()), "sub_game_id",
            getHourBo.getSubGameId());
        wrapper.in(CollectionUtil.isNotEmpty(getHourBo.getPkgId()), "pkg_id",
            getHourBo.getPkgId());
        wrapper.in(CollectionUtil.isNotEmpty(getHourBo.getDealId()), "deal_id",
            getHourBo.getDealId());
        wrapper.in(CollectionUtil.isNotEmpty(getHourBo.getChannelTypeId()),
            "channel_type_id",
            getHourBo.getChannelTypeId());
        wrapper.in(CollectionUtil.isNotEmpty(getHourBo.getChannelId()), "channel_id",
            getHourBo.getChannelId());
        wrapper.in(CollectionUtil.isNotEmpty(getHourBo.getChannelSubAccountId()),
            "channel_sub_account_id",
            getHourBo.getChannelSubAccountId());
        wrapper.ge(oConvertUtils.isNotEmpty(getHourBo.getRegStartTime()), "time_hour",
            getHourBo.getRegStartTime());
        wrapper.le(oConvertUtils.isNotEmpty(getHourBo.getRegEndTime()), "time_hour",
            getHourBo.getRegEndTime());
        return baseMapper.getSummaryAdvertHour(getHourBo.getLevel(), wrapper);
    }
}
