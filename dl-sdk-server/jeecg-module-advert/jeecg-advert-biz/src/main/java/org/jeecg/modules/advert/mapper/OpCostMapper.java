package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.count.vo.CostMoneyModel;
import org.jeecg.modules.advert.entity.OpCost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.vo.OpCostTotalVo;
import org.jeecg.modules.advert.vo.OpCostVo;

/**
 * @Description: op_cost
 * @Author: jeecg-boot
 * @Date: 2023-04-27
 * @Version: V1.0
 */
public interface OpCostMapper extends BaseMapper<OpCost> {

    /**
     * @param page
     * @param queryWrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.vo.OpCostVo>
     * @author chenyw
     * @description 分页获取成本
     * @date 14:03 2023/5/4/004
     **/
    IPage<OpCostVo> costInfoPage(IPage<OpCostVo> page,
        @Param(Constants.WRAPPER) Wrapper<OpCost> queryWrapper);

    /**
     * @param queryWrapper
     * @return org.jeecg.modules.advert.vo.OpCostTotalVo
     * @author chenyw
     * @description 获取所有成本
     * @date 14:04 2023/5/4/004
     **/
    OpCostTotalVo costTotal(@Param(Constants.WRAPPER) Wrapper<OpCost> queryWrapper);

    /**
     * @param queryWrapper
     * @return java.util.List<org.jeecg.common.count.vo.CostMoneyModel>
     * @author chenyw
     * @description 获取汇总表成本
     * @date 11:38 2023/5/5/005
     **/
    List<CostMoneyModel> getSummaryCost(@Param("groupBy") String groupBy,
        @Param(Constants.WRAPPER) Wrapper<OpCost> queryWrapper);

}
