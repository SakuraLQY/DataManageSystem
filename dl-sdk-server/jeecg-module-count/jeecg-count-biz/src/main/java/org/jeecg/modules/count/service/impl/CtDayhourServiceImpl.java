package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import org.jeecg.modules.count.dto.DayHourDto;
import org.jeecg.modules.count.vo.CtDayhour;
import org.jeecg.modules.count.mapper.CtDayhourMapper;
import org.jeecg.modules.count.service.ICtDayhourService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

import java.util.Date;

/**
 * @Description: ct_dayHour
 * @Author: jeecg-boot
 * @Date:   2023-04-24
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_countly")
public class CtDayhourServiceImpl extends ServiceImpl<CtDayhourMapper, CtDayhour> implements ICtDayhourService {

    @Resource
    private CtDayhourMapper ctDayhourMapper;

    @Override
    public IPage<CtDayhour> getDayHourList(Page<T> page, DayHourDto dayHourDto) {
        QueryWrapper<CtDayhour>queryWrapper = new QueryWrapper<>();
        if(null!=dayHourDto.getDealId()){
            queryWrapper.in("deal_id",dayHourDto.getDealId());
        }
        if(null!=dayHourDto.getChannelId()){
            queryWrapper.in("channel_id",dayHourDto.getChannelId());
        }
        if(null!=dayHourDto.getChannelSubAccountId()){
            queryWrapper.in("channel_sub_account_id",dayHourDto.getChannelSubAccountId());
        }
        if(null!=dayHourDto.getChannelTypeId()){
            queryWrapper.in("channel_type_id",dayHourDto.getChannelTypeId());
        }
        if(null!=dayHourDto.getGameId()){
            queryWrapper.in("game_id",dayHourDto.getGameId());
        }
        if(null!=dayHourDto.getSubGameId()){
            queryWrapper.in("sub_game_id",dayHourDto.getSubGameId());
        }
        if(null!=dayHourDto.getPkgId()){
            queryWrapper.in("pkg_id",dayHourDto.getPkgId());
        }
        if(null!=dayHourDto.getStartTime()&&null!=dayHourDto.getEndTime()){
            Date endTime = dayHourDto.getEndTime();
            Date startTime = dayHourDto.getStartTime();
            DateTime  startTimes= DateUtil.beginOfMinute(startTime);
            DateTime endTimes = DateUtil.endOfDay(endTime);
            queryWrapper.ge("time_hour",startTimes);
            queryWrapper.le("time_hour",endTimes);
        }
        //根据传的维度进行查询
        IPage<CtDayhour> dayHourList = ctDayhourMapper.getDayHourList(page, queryWrapper, dayHourDto.getLevel());
        return  dayHourList;
    }


}
