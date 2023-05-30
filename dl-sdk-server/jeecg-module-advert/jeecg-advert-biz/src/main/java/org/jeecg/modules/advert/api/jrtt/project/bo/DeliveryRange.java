package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.bo.jrtt.project
 * @className: DeliveryRange
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/17 14:22
 * @version: 1.0
 */
@Data
public class DeliveryRange {

    @JSONField(name = "inventory_catalog")
    private String inventoryCatalog;

    @JSONField(name = "inventory_type")
    private String[] inventoryType ;

    @JSONField(name = "union_video_type")
    private String unionVideoType ;
}
