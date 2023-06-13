package org.jeecg.modules.count.service;

import java.util.List;
import org.jeecg.modules.count.dto.UserPayRateDto;
import org.jeecg.modules.count.vo.UserPayRateVo;
import org.jeecg.modules.count.vo.WeekReportDataVo;
import org.jeecg.modules.count.vo.WeekReportVo;

/**
 * @Description: 数据报表-数据周报
 * @Author: jeecg-boot
 * @Date:   2023-04-19
 * @Version: V1.0
 */
public interface IWeekReportService {

    /**
     * @param startDate
     * @param endDate 
     * @return org.jeecg.modules.count.vo.WeekReportDataVo
     * @author chenyw
     * @description 获取数据周报
     * @date 15:33 2023/5/22/022
     **/
    WeekReportVo getWeekReportData(String startDate, String endDate);

}
