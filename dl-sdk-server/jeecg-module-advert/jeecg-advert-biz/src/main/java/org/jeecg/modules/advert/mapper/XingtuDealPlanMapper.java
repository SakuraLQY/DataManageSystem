package org.jeecg.modules.advert.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.XingtuDealPlan;
import org.jeecg.modules.advert.vo.OpXingtuDealVo;
import org.jeecg.modules.advert.vo.XingtuDealPlanVo;

/**
 * @Description: op_xingtu_deal_plan
 * @Author: jeecg-boot
 * @Date:   2023-03-08
 * @Version: V1.0
 */
public interface XingtuDealPlanMapper extends BaseMapper<XingtuDealPlan> {

    /**
     * @param page
     * @param queryWrapper
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.vo.XingtuDealPlanVo>
     * @Author lili
     * @Description 分页获取星图广告计划
     * @Date 17:48 2023/3/24
     **/
    IPage<XingtuDealPlanVo> dealPlanInfoPage(IPage<XingtuDealPlan> page, @Param(Constants.WRAPPER) Wrapper<XingtuDealPlan> queryWrapper);

}
