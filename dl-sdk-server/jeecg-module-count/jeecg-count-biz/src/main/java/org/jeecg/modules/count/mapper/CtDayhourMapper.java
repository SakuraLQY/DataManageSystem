package org.jeecg.modules.count.mapper;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.modules.count.vo.CtDayhour;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: ct_dayHour
 * @Author: jeecg-boot
 * @Date:   2023-04-24
 * @Version: V1.0
 */
public interface CtDayhourMapper extends BaseMapper<CtDayhour> {


    IPage<CtDayhour>  getDayHourList(Page<T> page,@Param(Constants.WRAPPER)  QueryWrapper<CtDayhour> queryWrapper, @Param("level")String level);
}
