package org.jeecg.modules.count.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.dto.DailySummaryDto;
import org.jeecg.modules.count.vo.CtDailySummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: ct_daily_summary
 * @Author: jeecg-boot
 * @Date:   2023-04-28
 * @Version: V1.0
 */
public interface CtDailySummaryMapper extends BaseMapper<CtDailySummary> {

    DailySummaryDto getQueryTodayList( @Param("ctDailySummary") CtDailySummary ctDailySummary, @Param("level") String level);

    DailySummaryDto getYesterdayList(@Param("ctDailySummary") CtDailySummary ctDailySummary, @Param("level") String level);

}
