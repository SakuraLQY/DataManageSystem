package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.interestAction.bo
 * @className: JrttDealActionKeywordsReq
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/23 18:20
 * @version: 1.0
 */
@Data
@AllArgsConstructor
public class JrttDealActionKeywordsReq extends JrttDealActionCategoryReq{
    @JSONField(name = "query_words")
    private String keywords;

    public JrttDealActionKeywordsReq(Long advertiserId, List<String> actionScene,
        Integer actionDays, String keywords) {
        super(advertiserId, actionScene, actionDays);
        this.keywords = keywords;
    }
    public JrttDealActionKeywordsReq(Long advertiserId,String keywords) {
        super(advertiserId);
        this.keywords = keywords;
    }

}
