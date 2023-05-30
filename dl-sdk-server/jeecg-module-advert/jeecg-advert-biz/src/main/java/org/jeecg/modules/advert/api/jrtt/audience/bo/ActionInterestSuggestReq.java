package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.interestAction.bo
 * @className: ActionInterestSuggestReq
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/24 13:59
 * @version: 1.0
 */
@Data
public class ActionInterestSuggestReq extends ActionInterestCommonReq{

    @JSONField(name = "id")
    private Integer id;

    public ActionInterestSuggestReq(Long advertiserId, String tagType, String targetingType,
        List<String> actionScene, Integer actionDays, Integer id) {
        super(advertiserId, tagType, targetingType, actionScene, actionDays);
        this.id = id;
    }
}
