
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取定向包-audience_packages-audience-action  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAudiencePackagesAudienceActionResponse {

    /**
     * 行为场景
     **/
    @JSONField(name = "action_scene")
    private List<String> actionScene;

    /**
     * 行为天数
     **/
    @JSONField(name = "action_days")
    private Integer actionDays;

    /**
     * 行为类目
     **/
    @JSONField(name = "action_categories")
    private List<Integer> actionCategories;

    /**
     * 行为关键词
     **/
    @JSONField(name = "action_words")
    private List<Integer> actionWords;


}
