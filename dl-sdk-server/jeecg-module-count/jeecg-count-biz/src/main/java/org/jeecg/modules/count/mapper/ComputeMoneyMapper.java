package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.ComputePayBo;
import org.jeecg.modules.count.entity.ComputeMoney;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: data_tool
 * @Author: jeecg-boot
 * @Date:   2023-05-23
 * @Version: V1.0
 */
public interface ComputeMoneyMapper extends BaseMapper<ComputeMoney> {

    /**@param
     * @author chenglin
     * @description
     * @date 15:27 2023/5/27
     **/
    List<ComputePayBo> queryPayList(@Param(Constants.WRAPPER)QueryWrapper where);

    Integer selectPeriodUser(@Param(Constants.WRAPPER) QueryWrapper where3,@Param("time") Date time);

    BigDecimal selectPeriodMoney(@Param(Constants.WRAPPER) QueryWrapper where3,@Param("time")Date time);
}
