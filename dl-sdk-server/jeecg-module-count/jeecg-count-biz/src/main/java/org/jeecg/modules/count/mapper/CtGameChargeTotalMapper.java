package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.entity.CtDaily;
import org.jeecg.modules.count.vo.CtDayhour;
import org.jeecg.modules.count.vo.CtGameChargeTotal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: ct_gameChargeTotal
 * @Author: jeecg-boot
 * @Date: 2023-05-09
 * @Version: V1.0
 */
public interface CtGameChargeTotalMapper extends BaseMapper<CtGameChargeTotal> {

    /**
     * 查询相应的数据
     */
    List<CtGameChargeTotal> selectParams(@Param(Constants.WRAPPER) QueryWrapper<CtDaily> queryWrapper);

    String queryGameById(@Param("id") Integer id);

    String querySubGameById(@Param("id") Integer id);
}
