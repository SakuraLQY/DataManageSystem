package org.jeecg.modules.advert.api.xingtu.plan.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.xingtu.plan.bo
 * @className: CreatePlanRsp
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/10 20:13
 * @version: 1.0
 */
@Data
public class CreatePlanRsp {
    @JSONField(name = "ad_id")
    private Long adId;
}
