package org.jeecg.modules.count.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.jeecg.modules.count.dto.DailySummaryDto;
import org.jeecg.modules.count.vo.CtDailySummary;
import org.jeecg.modules.count.mapper.CtDailySummaryMapper;
import org.jeecg.modules.count.service.ICtDailySummaryService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: ct_daily_summary
 * @Author: jeecg-boot
 * @Date: 2023-04-28
 * @Version: V1.0
 */
@Service
@DS("open_countly")
public class CtDailySummaryServiceImpl extends
    ServiceImpl<CtDailySummaryMapper, CtDailySummary> implements ICtDailySummaryService {

    @Resource
    private CtDailySummaryMapper ctDailySummaryMapper;

    @Override
    public List<List<DailySummaryDto>> queryList(CtDailySummary ctDailySummary) {
        List<List<DailySummaryDto>> queryList = new ArrayList<>();
        List<DailySummaryDto> dailyToday = queryTodayList(ctDailySummary);
        List<DailySummaryDto> dailyYesterday = queryYesterdayList((ctDailySummary));
        queryList.add(dailyToday);
        queryList.add(dailyYesterday);
        return queryList;

    }

    @Override
    public List<DailySummaryDto> queryTodayList(CtDailySummary ctDailySummary) {
        String[] levelName = new String[]{"充值金额", "充值人数", "新增充值人数", "新增账号", "在线人数"};
        String[] level = new String[]{"total_money", "total_payuser", "first_payuser", "count_user",
            "count_dau"};
        List<DailySummaryDto> queryList = new ArrayList<>();
        for (int i = 0; i < level.length; i++) {
            DailySummaryDto dailySummary = ctDailySummaryMapper.getQueryTodayList(ctDailySummary,
                level[i]);
            dailySummary.setLevel(levelName[i]);
            queryList.add(dailySummary);
        }
        return queryList;
    }

    @Override
    public List<DailySummaryDto> queryYesterdayList(CtDailySummary ctDailySummary) {
        String[] levelName = new String[]{"充值金额", "充值人数", "新增充值人数", "新增账号", "在线人数"};
        String[] level = new String[]{"total_money", "total_payuser", "first_payuser", "count_user",
            "count_dau"};
        List<DailySummaryDto> queryList = new ArrayList<>();
        for (int i = 0; i < level.length; i++) {
            DailySummaryDto dailySummary = ctDailySummaryMapper.getYesterdayList(ctDailySummary,
                level[i]);
            dailySummary.setLevel(levelName[i]);
            queryList.add(dailySummary);
        }
        return queryList;
    }
}
