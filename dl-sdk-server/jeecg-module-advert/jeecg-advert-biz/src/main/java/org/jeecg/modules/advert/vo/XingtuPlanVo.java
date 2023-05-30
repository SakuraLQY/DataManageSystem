package org.jeecg.modules.advert.vo;

import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.entity.XingtuDealPlan;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: XingtuProjectVo
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/10 16:39
 * @version: 1.0
 */
@Data
public class XingtuPlanVo {
    private Integer dealId;
    private XingtuDealPlan xingtuDealPlan;
}
