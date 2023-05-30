package org.jeecg.modules.count.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.count.bo.PayResBo;
import org.jeecg.modules.count.bo.PayUserBo;
import org.jeecg.modules.count.bo.RetainAnalyzeBo;
import org.jeecg.modules.count.bo.RetainCostBo;
import org.jeecg.modules.count.bo.RetainLoyalBo;
import org.jeecg.modules.count.dto.RetainAnalyzeDto;
import org.jeecg.modules.count.entity.RetainAnalyze;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 留存分析
 * @Author: jeecg-boot
 * @Date:   2023-05-17
 * @Version: V1.0
 */
public interface RetainAnalyzeMapper extends BaseMapper<RetainAnalyze> {

    /**@param cost_where
     * @author chenglin
     * @description 查询成本
     * @date 17:08 2023/5/17
     **/
    List<RetainCostBo> queryCostList(@Param(Constants.WRAPPER)QueryWrapper cost_where);

    /**@param where
     * @author chenglin
     * @description 查询注册数和新增付费用户数
     * @date 18:45 2023/5/17
     **/
    List<RetainAnalyzeBo> queryRetainList(@Param(Constants.WRAPPER) QueryWrapper<RetainAnalyzeDto> where);

    /**@param section_where
     * @author chenglin
     * @description 根据充值区间来查询对应的信息
     * @date 11:10 2023/5/18
     **/
    List<PayUserBo> queryOrderUser(@Param(Constants.WRAPPER) QueryWrapper section_where);

    /**@param  pay_where
     * @author chenglin
     * @description 查询根据Id查询对应的信息
     * @date  18:49 2023/5/27
     **/
    List<PayResBo> queryPayRes(@Param(Constants.WRAPPER) QueryWrapper pay_where);

    /**@param loyalSql
     * @author chenglin
     * @description 用来查询留存的150天数据
     * @date 15:55 2023/5/18
     **/
    List<RetainLoyalBo> queryLoyalList(@Param(Constants.WRAPPER)QueryWrapper<RetainAnalyzeDto> where, @Param("loyalSql") String loyalSql);
}
