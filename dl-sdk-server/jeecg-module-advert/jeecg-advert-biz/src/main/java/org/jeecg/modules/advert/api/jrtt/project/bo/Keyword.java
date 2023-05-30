package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.bo.jrtt.project
 * @className: Keyword
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/17 14:18
 * @version: 1.0
 */
@Data
public class Keyword {

    @JSONField(name = "word")
    private String word;

    @JSONField(name = "bid_type")
    private String BidType;

    @JSONField(name = "match_type")
    private String matchType;
}
