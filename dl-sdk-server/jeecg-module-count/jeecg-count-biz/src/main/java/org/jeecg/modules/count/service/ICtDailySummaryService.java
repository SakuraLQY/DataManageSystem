package org.jeecg.modules.count.service;

import org.jeecg.modules.count.dto.DailySummaryDto;
import org.jeecg.modules.count.vo.CtDailySummary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: ct_daily_summary
 * @Author: jeecg-boot
 * @Date: 2023-04-28
 * @Version: V1.0
 */
public interface ICtDailySummaryService extends IService<CtDailySummary> {

    /**
     * @param ctDailySummary
     * @author chenglin
     * @description 返回今天和昨天的数据和
     * @date 10:56 2023/05/06
     **/
    List<List<DailySummaryDto>> queryList(CtDailySummary ctDailySummary);

    /**
     * @param ctDailySummary
     * @author chenglin
     * @description 查询今日汇总当天的数据
     * @date 10:34 2023/05/06
     **/
    List<DailySummaryDto> queryTodayList(CtDailySummary ctDailySummary);

    /**
     * @param ctDailySummary
     * @author chenglin
     * @description 查询今日汇总昨天的数据
     * @date 10:43 2023/05/06
     **/
    List<DailySummaryDto> queryYesterdayList(CtDailySummary ctDailySummary);
}
