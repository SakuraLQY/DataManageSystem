package org.jeecg.modules.advert.vo;

import lombok.Data;
import org.jeecg.modules.advert.entity.XingtuDealPlan;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: XingtuEditPlanVo
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/17 18:17
 * @version: 1.0
 */
@Data
public class XingtuEditPlanVo {
    private Integer accountId;
    private XingtuDealPlan xingtuDealPlan;
}
