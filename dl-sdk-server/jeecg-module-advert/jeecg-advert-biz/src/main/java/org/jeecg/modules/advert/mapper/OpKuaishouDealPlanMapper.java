package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.OpKuaishouDealPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.vo.OpKuaishouDealPlanVo;

/**
 * @Description: op_kuaishou_deal
 * @Author: jeecg-boot
 * @Date: 2023-03-09
 * @Version: V1.0
 */
public interface OpKuaishouDealPlanMapper extends BaseMapper<OpKuaishouDealPlan> {

    /**
     * @param page:         分页信息
     * @param queryWrapper: 查询信息
     * @return IPage<OpKuaishouDealPlanVo>
     * @author xmh
     * @description 分页获取快手广告计划信息
     * @date 2023/3/14 16:04
     */
    IPage<OpKuaishouDealPlanVo> dealPlanInfoPage(IPage<OpKuaishouDealPlan> page,
        @Param(Constants.WRAPPER) Wrapper<OpDeal> queryWrapper);
}
