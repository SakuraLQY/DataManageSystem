package org.jeecg.modules.count.service;

import org.jeecg.modules.count.dto.DailyAnalyzeDto;
import org.jeecg.modules.count.vo.CtDayAnalyze;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: ct_dayanalyze
 * @Author: jeecg-boot
 * @Date: 2023-04-28
 * @Version: V1.0
 */
public interface ICtDayAnalyzeService extends IService<CtDayAnalyze> {

    /**
     * @param dailyAnalyzeDto
     * @author chenglin
     * @description 查询多日分析数据
     * @date 14:24 2023/04/25
     **/
    List<CtDayAnalyze> queryList(DailyAnalyzeDto dailyAnalyzeDto);
}
