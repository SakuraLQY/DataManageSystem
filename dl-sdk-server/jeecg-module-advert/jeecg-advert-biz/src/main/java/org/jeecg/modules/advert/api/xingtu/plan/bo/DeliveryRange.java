package org.jeecg.modules.advert.api.xingtu.plan.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.xingtu.plan.bo
 * @className: DeliveryRange
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/10 19:22
 * @version: 1.0
 */
@Data
public class DeliveryRange {

    @JSONField(name = "delivery_range")
    private String deliveryRange;

    @JSONField(name = "inventory_catalog")
    private String inventoryCatalog;

    @JSONField(name = "inventory_type")
    private List<String> inventoryType;
}
