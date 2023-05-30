package org.jeecg.modules.advert.bo.opJrttAudience;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Author lili
 * @Description 人群包列表参数
 * @Date 16:30 2023/2/20
 **/
@Data
public class RetargetingTagsConfConfig {

    /**定向人群包列表**/
    @JSONField(name = "retargeting_tags")
    @JsonProperty("retargeting_tags")
    private List<Integer> retargetingTags;

    /**排除人群包列表**/
    @JSONField(name = "retargeting_tags_exclude")
    @JsonProperty("retargeting_tags_exclude")
    private List<Integer> retargetingTagsExclude;

}
