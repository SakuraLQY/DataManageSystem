package org.jeecg.modules.advert.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.jeecg.common.advert.dto.OpCostDto;
import org.jeecg.common.count.vo.CostMoneyModel;
import org.jeecg.modules.advert.entity.OpCost;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.advert.vo.OpCostTotalVo;
import org.jeecg.modules.advert.vo.OpCostVo;

/**
 * @Description: op_cost
 * @Author: jeecg-boot
 * @Date:   2023-04-27
 * @Version: V1.0
 */
public interface IOpCostService extends IService<OpCost> {

    /**
     * @param page
     * @param opCost
     * @param startDate
     * @param endDate
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.jeecg.modules.advert.vo.OpCostVo>
     * @author chenyw
     * @description 获取成本分页
     * @date 16:12 2023/4/28/028
     **/
    IPage<OpCostVo> getByPage(Page<OpCostVo> page, OpCost opCost, String startDate,
        String endDate);

    /**
     * @param opCost
     * @param startDate
     * @param endDate
     * @return org.jeecg.modules.advert.vo.OpCostTotalVo
     * @author chenyw
     * @description 获取成本合计
     * @date 14:00 2023/5/4/004
     **/
    OpCostTotalVo getTotalCost(OpCost opCost, String startDate, String endDate);

    /**
     * @param opCostDto
     * @return java.util.List<org.jeecg.common.count.vo.CostMoneyModel>
     * @author chenyw
     * @description 获取成本
     * @date 10:56 2023/5/5/005
     **/
    List<CostMoneyModel> getSummaryCost(OpCostDto opCostDto);
}
