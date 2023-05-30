package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.count.dto.DailyAnalyzeDto;
import org.jeecg.modules.count.vo.CtDayAnalyze;
import org.jeecg.modules.count.mapper.CtDayAnalyzeMapper;
import org.jeecg.modules.count.service.ICtDayAnalyzeService;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: ct_dayanalyze
 * @Author: jeecg-boot
 * @Date:   2023-04-28
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtDayAnalyzeServiceImpl extends ServiceImpl<CtDayAnalyzeMapper, CtDayAnalyze> implements ICtDayAnalyzeService {

    @Resource
    private CtDayAnalyzeMapper ctDayAnalyzeMapper;
    @Override
    public List<CtDayAnalyze> queryList(DailyAnalyzeDto dailyAnalyzeDto) {
        QueryWrapper<DailyAnalyzeDto> q = new QueryWrapper();
        if(null!=dailyAnalyzeDto.getDealId()){
            q.eq("deal_id",dailyAnalyzeDto.getDealId());
        }
        if(null!=dailyAnalyzeDto.getChannelId()){
            q.in("channel_id",dailyAnalyzeDto.getChannelId());
        }
        if(null!=dailyAnalyzeDto.getChannelSubAccountId()){
            q.in("channel_sub_account_id",dailyAnalyzeDto.getChannelSubAccountId());
        }
        if(null!=dailyAnalyzeDto.getChannelTypeId()){
            q.in("channel_type_id",dailyAnalyzeDto.getChannelTypeId());
        }
        if(null!=dailyAnalyzeDto.getGameId()){
            q.in("game_id",dailyAnalyzeDto.getGameId());
        }
        if(null!=dailyAnalyzeDto.getSubGameId()){
            q.in("sub_game_id",dailyAnalyzeDto.getSubGameId());
        }
        if(null!=dailyAnalyzeDto.getPkgId()){
            q.in("pkg_id",dailyAnalyzeDto.getPkgId());
        }
        if(null!=dailyAnalyzeDto.getStartTime() && null!=dailyAnalyzeDto.getEndTime()){
            Date startTime = dailyAnalyzeDto.getStartTime();
            Date endTime = dailyAnalyzeDto.getEndTime();
            q.ge("time_hour",DateUtil.beginOfMinute(startTime));
            q.le("time_hour",DateUtil.endOfDay(endTime));
        }

        String[]level = new String[]{"count_active","count_active_dev","count_user","count_user_dev","first_payuser","first_money"
                ,"total_money","alive_payuser","alive_money","count_dau","loyal2","loyal3","loyal4","loyal5","loyal6","loyal7"
                ,"loyal15","loyal30","loyal45","loyal60"};
        String[]levelName = new String[]{"激活数","激活设备数","注册数","注册设备数","新增付费人数","新增付费金额","累计付费金额","活跃付费人数","活跃付费金额",
                "DAU","次日留存","3日留存","4日留存","5日留存","6日留存","7日留存","15日留存","30日留存","45日留存","60日留存"};
        List<CtDayAnalyze>queryAnalyzeList = new ArrayList<>();
        for(int i = 0;i< level.length;i++){
           CtDayAnalyze ctDayAnalyze =  ctDayAnalyzeMapper.getQueryList(q,level[i]);
           ctDayAnalyze.setLevel(levelName[i]);
           queryAnalyzeList.add(ctDayAnalyze);
        }
        return queryAnalyzeList;
    }
}
