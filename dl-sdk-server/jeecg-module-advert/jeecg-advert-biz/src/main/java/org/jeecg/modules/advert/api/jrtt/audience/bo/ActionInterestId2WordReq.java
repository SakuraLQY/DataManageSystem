package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: JrttDealActionVo
 * @author: Eric
 * @description: 查询行为类目请求对象
 * @date: 2023/2/23 11:32
 * @version: 1.0
 */
@Data
public class ActionInterestId2WordReq extends ActionInterestCommonReq{

    @JSONField(name = "ids")
    private List<Integer> ids;

    public ActionInterestId2WordReq(Long advertiserId, String tagType, String targetingType,
        List<String> actionScene, Integer actionDays, List<Integer> ids) {
        super(advertiserId, tagType, targetingType, actionScene, actionDays);
        this.ids = ids;
    }
}
