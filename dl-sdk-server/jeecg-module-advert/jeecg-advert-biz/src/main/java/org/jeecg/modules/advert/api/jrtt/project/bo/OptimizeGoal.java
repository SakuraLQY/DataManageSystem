package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.bo.jrtt.project
 * @className: OptimizeGoal
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/17 14:20
 * @version: 1.0
 */
@Data
public class OptimizeGoal {

    @JSONField(name = "asset_ids")
    private List<Long> assetIds;

    @JSONField(name = "external_action")
    private String externalAction;

    @JSONField(name = "deep_external_action")
    private String deepExternalAction;
}
