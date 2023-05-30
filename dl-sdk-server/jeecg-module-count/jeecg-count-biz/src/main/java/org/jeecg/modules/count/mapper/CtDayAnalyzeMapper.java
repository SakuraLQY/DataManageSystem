package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.dto.DailyAnalyzeDto;
import org.jeecg.modules.count.vo.CtDayAnalyze;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: ct_dayanalyze
 * @Author: jeecg-boot
 * @Date:   2023-04-28
 * @Version: V1.0
 */
public interface CtDayAnalyzeMapper extends BaseMapper<CtDayAnalyze> {

    CtDayAnalyze getQueryList(@Param(Constants.WRAPPER) QueryWrapper<DailyAnalyzeDto> q, @Param("level") String level);
}
