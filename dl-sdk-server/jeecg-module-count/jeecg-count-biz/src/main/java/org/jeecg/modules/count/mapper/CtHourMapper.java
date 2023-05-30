package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.HourBo;
import org.jeecg.modules.count.entity.CtHour;
import org.springframework.stereotype.Repository;

/**
 * @Description: ct_hour
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Repository
public interface CtHourMapper extends BaseMapper<CtHour> {

    /**
     * @param level
     * @param queryWrapper
     * @return org.jeecg.modules.count.bo.HourBo
     * @author chenyw
     * @description 获取小时统计数据
     * @date 16:38 2023/5/13/013
     **/
    List<HourBo> getSummaryAdvertHour(@Param("level") String level,
        @Param(Constants.WRAPPER) Wrapper<CtHour> queryWrapper);

}
