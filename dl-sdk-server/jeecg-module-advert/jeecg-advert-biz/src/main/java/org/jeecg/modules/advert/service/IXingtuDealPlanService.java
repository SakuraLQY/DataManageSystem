package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.advert.entity.OpDeal;
import org.jeecg.modules.advert.entity.XingtuDealPlan;
import org.jeecg.modules.advert.vo.OpXingtuDealVo;
import org.jeecg.modules.advert.vo.XingtuDealPlanVo;
import org.jeecg.modules.advert.vo.XingtuPlanVo;

/**
 * @Description: op_xingtu_deal_plan
 * @Author: jeecg-boot
 * @Date:   2023-03-08
 * @Version: V1.0
 */
public interface IXingtuDealPlanService extends IService<XingtuDealPlan> {

    /**
     * @param page
     * @param xingtuDealPlan
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.vo.XingtuDealPlanVo>
     * @Author lili
     * @Description 分页获取星图广告计划
     * @Date 17:43 2023/3/24
     **/
    IPage<XingtuDealPlanVo> getByPage(IPage<XingtuDealPlan> page, XingtuDealPlan xingtuDealPlan);

    /**
     * @param batchCreateInfo:
     * @return Result
     * @author
     * @description 批量创建计划
     * @date 2023/3/17 18:13
     */
    Result batchCreatePlan(XingtuPlanVo batchCreateInfo);

    /**
     * @param xingtuDealPlan:
     * @return Result
     * @author
     * @description 编辑计划
     * @date 2023/3/17 18:13
     */
    Result editPlan(Integer accountId, XingtuDealPlan xingtuDealPlan);
}
