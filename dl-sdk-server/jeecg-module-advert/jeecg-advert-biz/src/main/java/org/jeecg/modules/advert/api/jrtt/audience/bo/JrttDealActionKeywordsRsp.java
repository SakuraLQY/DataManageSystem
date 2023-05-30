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
 * @description: 行为、兴趣关键字统一返回对象
 * @date: 2023/2/23 18:20
 * @version: 1.0
 */
@Data
@AllArgsConstructor
public class JrttDealActionKeywordsRsp {

    @JSONField(name = "list")
    private List<keyWord> list;

    @Data
    static class keyWord {
        String id;
        String name;
        String num;
    }
}
