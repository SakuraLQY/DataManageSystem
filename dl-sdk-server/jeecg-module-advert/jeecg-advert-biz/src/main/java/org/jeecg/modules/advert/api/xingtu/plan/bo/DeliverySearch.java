package org.jeecg.modules.advert.api.xingtu.plan.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.xingtu.plan.bo
 * @className: DeliverySearch
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/10 19:25
 * @version: 1.0
 */
@Data
public class DeliverySearch {

    @JSONField(name = "search_bid_ratio")
    private Float searchBidRatio;

    @JSONField(name = "audience_extend")
    private String audienceExtend;

}
