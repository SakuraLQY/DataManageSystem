package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.interestAction.bo
 * @className: ActionInterestCommonReq
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/24 13:56
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionInterestCommonReq {
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    @JSONField(name = "tag_type")
    private String tagType;

    @JSONField(name = "targeting_type")
    private String targetingType;

    @JSONField(name = "action_scene")
    private List<String> actionScene;

    @JSONField(name = "action_days")
    private Integer actionDays;
}
