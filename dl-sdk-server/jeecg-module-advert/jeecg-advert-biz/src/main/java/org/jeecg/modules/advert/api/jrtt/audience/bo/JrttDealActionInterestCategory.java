package org.jeecg.modules.advert.api.jrtt.audience.bo;

import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.interestAction.bo
 * @className: JrttDealActionCategoryRsp
 * @author: Eric
 * @description: 行为、兴趣类目统一返回对象
 * @date: 2023/2/23 13:53
 * @version: 1.0
 */
@Data
public class JrttDealActionInterestCategory {

    private String id;

    private String name;

    private List<JrttDealActionInterestCategory> children;
}
