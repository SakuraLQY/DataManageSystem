package org.jeecg.modules.advert.bo.opJrttAudience;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Author lili
 * @Description 行为和兴趣参数
 * @Date 16:30 2023/2/20
 **/
@Data
public class InterestActionConfConfig {

    /**行为类目**/
    @JSONField(name = "action_categories")
    @JsonProperty("action_categories")
    private List<Integer> actionCategories;

    /**行为天数**/
    @JSONField(name = "action_days")
    @JsonProperty("action_days")
    private Integer actionDays;

    /**行为关键词**/
    @JSONField(name = "action_words")
    @JsonProperty("action_words")
    private List<Integer> actionWords;

    /**行为场景**/
    @JSONField(name = "action_scene")
    @JsonProperty("action_scene")
    private List<String> actionScene;

    /**兴趣分类**/
    @JSONField(name = "interest_categories")
    @JsonProperty("interest_categories")
    private List<Integer> interestCategories;

    /**兴趣关键词**/
    @JSONField(name = "interest_words")
    @JsonProperty("interest_words")
    private List<Integer> interestWords;

}
