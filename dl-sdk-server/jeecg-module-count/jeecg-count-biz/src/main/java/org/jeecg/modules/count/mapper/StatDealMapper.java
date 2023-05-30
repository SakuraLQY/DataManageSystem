package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.StatDealBo;
import org.jeecg.modules.count.dto.StatDealDto;
import org.jeecg.modules.count.entity.StatDeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: stat_deal
 * @Author: jeecg-boot
 * @Date:   2023-05-30
 * @Version: V1.0
 */
public interface StatDealMapper extends BaseMapper<StatDeal> {

    /**@param where
     * @author chenglin
     * @description 查询信息
     * @date 11:04 2023/5/30
     **/
    List<StatDealBo> queryList(@Param(Constants.WRAPPER) QueryWrapper<StatDealDto> where);

    String selectGameNameById(Integer id);

    String selectDealNameById(Integer id);
}
