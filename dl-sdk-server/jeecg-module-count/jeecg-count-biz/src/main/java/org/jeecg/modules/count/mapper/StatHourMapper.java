package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.StatHourBo;
import org.jeecg.modules.count.dto.StatHourDto;
import org.jeecg.modules.count.entity.StatHour;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: cooperator_stat
 * @Author: jeecg-boot
 * @Date:   2023-05-29
 * @Version: V1.0
 */
public interface StatHourMapper extends BaseMapper<StatHour> {

    /**@param  where
     * @author chenglin
     * @description 查询合作商数据之渠道信息
     * @date  14:28 2023/5/29
     **/
    List<StatHourBo> queryList(@Param(Constants.WRAPPER)QueryWrapper<StatHourDto> where,@Param("sql") String sql);

    String selectGameNameById(@Param("id") Integer id);

    String selectDealNameById(Integer id);
}
