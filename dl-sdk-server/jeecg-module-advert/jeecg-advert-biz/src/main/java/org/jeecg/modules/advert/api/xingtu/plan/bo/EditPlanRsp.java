package org.jeecg.modules.advert.api.xingtu.plan.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.xingtu.plan.bo
 * @className: EditPlanRsp
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/17 18:08
 * @version: 1.0
 */
@Data
public class EditPlanRsp {
    @JSONField(name = "ad_id")
    private Long adId;

    @JSONField(name = "need_audit")
    private Integer needAudit;
}
