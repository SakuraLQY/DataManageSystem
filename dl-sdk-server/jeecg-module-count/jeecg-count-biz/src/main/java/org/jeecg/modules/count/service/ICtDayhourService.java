package org.jeecg.modules.count.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.count.dto.DayHourDto;
import org.jeecg.modules.count.vo.CtDayHourVo;
import org.jeecg.modules.count.vo.CtDayhour;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: ct_dayHour
 * @Author: jeecg-boot
 * @Date: 2023-04-24
 * @Version: V1.0
 */
public interface ICtDayhourService extends IService<CtDayhour> {

    /**
     * @param dayHourDto
     * @author chenglin
     * @description 查询今日分时数据信息
     * @date 14:34 2023/04/25
     **/
    IPage<CtDayhour> getDayHourList(Page<T> page, DayHourDto dayHourDto);

    List<CtDayHourVo> queryList(DayHourDto dayHourDto);
}
