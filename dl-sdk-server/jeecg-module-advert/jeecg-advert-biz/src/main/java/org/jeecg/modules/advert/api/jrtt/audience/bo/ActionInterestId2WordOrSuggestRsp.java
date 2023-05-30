package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.interestAction.bo
 * @className: ActionInterestId2WordRsp
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/24 11:00
 * @version: 1.0
 */
@Data
public class ActionInterestId2WordOrSuggestRsp {

    @JSONField(name = "categories")
    private List<KeyWordsOrCategories> categories;

    @JSONField(name = "keywords")
    private List<KeyWordsOrCategories>  keywords;

    @Data
    static class KeyWordsOrCategories {
        String id;
        String name;
        String num;
    }
}
