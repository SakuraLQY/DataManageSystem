package org.jeecg.modules.advert.bo.opJrttAudience;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Author lili
 * @Description 抖音达人参数
 * @Date 16:30 2023/2/20
 **/
@Data
public class AwemeFanConfConfig {

    /**抖音用户行为类型**/
    @JSONField(name = "aweme_fan_behaviors")
    @JsonProperty("aweme_fan_behaviors")
    private List<String> awemeFanBehaviors;

    /**抖音号id**/
    @JSONField(name = "aweme_fan_accounts")
    @JsonProperty("aweme_fan_accounts")
    private List<Long> awemeFanAccounts;

    /**抖音类目id**/
    @JSONField(name = "aweme_fan_categories")
    @JsonProperty("aweme_fan_categories")
    private List<Long> awemeFanCategories;

    /**抖音达人互动行为时间范围**/
    @JSONField(name = "aweme_fan_time_scope")
    @JsonProperty("aweme_fan_time_scope")
    private String awemeFanTimeScope;

}
