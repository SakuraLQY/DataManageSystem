package org.jeecg.modules.count.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import org.jeecg.modules.count.bo.CtDayHourBo;
import org.jeecg.modules.count.dto.DayHourDto;
import org.jeecg.modules.count.vo.CtDayHourVo;
import org.jeecg.modules.count.vo.CtDayhour;
import org.jeecg.modules.count.mapper.CtDayhourMapper;
import org.jeecg.modules.count.service.ICtDayhourService;
import org.springframework.beans.BeanUtils;
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

    @Override
    public List<CtDayHourVo> queryList(DayHourDto dayHourDto) {
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
        List<CtDayhour> dayHourList = ctDayhourMapper.getDayHourListForAll( queryWrapper, dayHourDto.getLevel());
        List<CtDayHourVo>resList = new ArrayList<>();
        //计算汇总
        Integer zero = 0;
        Integer one = 0;
        Integer two = 0;
        Integer three = 0;
        Integer four = 0;
        Integer five = 0;
        Integer six = 0;
        Integer seven = 0;
        Integer eight = 0;
        Integer nine = 0;
        Integer ten = 0;
        Integer eleven = 0;
        Integer twelve = 0;
        Integer thirteen = 0;
        Integer fourteen = 0;
        Integer fifteen = 0;
        Integer sixteen = 0;
        Integer seventeen = 0;
        Integer eighteen = 0;
        Integer ninteen = 0;
        Integer twenty = 0;
        Integer twentyone = 0;
        Integer twentytwo = 0;
        Integer twentythree = 0;
        Integer sum = 0 ;
        for (CtDayhour ctDayhour : dayHourList) {
            CtDayHourBo ctDayHourBo = new CtDayHourBo();
            //除了时间都拷贝过去
            BeanUtils.copyProperties(ctDayhour,ctDayHourBo);
            CtDayHourVo dayHourVo = new CtDayHourVo();
            dayHourVo.setDate(DateUtil.format(ctDayhour.getDate(),"yyyy-MM-dd"));
            BeanUtils.copyProperties(ctDayHourBo,dayHourVo);
            resList.add(dayHourVo);
            zero+=ctDayhour.getZeroHour();
            one+=ctDayhour.getOneHour();
            two+=ctDayhour.getTwoHour();
            three+=ctDayhour.getThreeHour();
            four+=ctDayhour.getFourHour();
            five+= ctDayhour.getFiveHour();
            six+= ctDayhour.getSixHour();
            seven+= ctDayhour.getSevenHour();
            eight+=ctDayhour.getEightHour();
            nine+= ctDayhour.getNineHour();
            ten+= ctDayhour.getTenHour();
            eleven+=ctDayhour.getElevenHour();
            twelve+= ctDayhour.getTwelveHour();
            thirteen+= ctDayhour.getThirteenHour();
            fourteen+= ctDayhour.getFourteenHour();
            fifteen+= ctDayhour.getFifteenHour();
            sixteen+= ctDayhour.getSixteenHour();
            seventeen+= ctDayhour.getSeventeenHour();
            eighteen+=ctDayhour.getSeventeenHour();
            ninteen+=ctDayhour.getNineteenHour();
            twenty+=ctDayhour.getTwentyHour();
            twentyone+= ctDayhour.getTwentyoneHour();;
            twentytwo+=ctDayhour.getTwentytwoHour();
            twentythree+=ctDayhour.getTwentythreeHour();
            sum += ctDayhour.getTotal();
        }
        CtDayHourVo allTotal = new CtDayHourVo();
        allTotal.setDate("汇总");
        allTotal.setTotal(sum);
        allTotal.setZeroHour(zero);
        allTotal.setOneHour(one);
        allTotal.setTwoHour(two);
        allTotal.setThreeHour(three);
        allTotal.setFourHour(four);
        allTotal.setFiveHour(five);
        allTotal.setSixHour(six);
        allTotal.setSevenHour(seven);
        allTotal.setEightHour(eight);
        allTotal.setNineHour(nine);
        allTotal.setTenHour(ten);
        allTotal.setElevenHour(eleven);
        allTotal.setTwelveHour(twelve);
        allTotal.setThirteenHour(thirteen);
        allTotal.setFourteenHour(fourteen);
        allTotal.setFifteenHour(fifteen);
        allTotal.setSixteenHour(sixteen);
        allTotal.setSeventeenHour(seventeen);
        allTotal.setEighteenHour(eighteen);
        allTotal.setNineteenHour(ninteen);
        allTotal.setTwentyHour(twenty);
        allTotal.setTwentyoneHour(twentyone);
        allTotal.setTwentytwoHour(twentytwo);
        allTotal.setTwentythreeHour(twentythree);
        resList.add(allTotal);
        return resList;
    }


}
