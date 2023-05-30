package org.jeecg.modules.advert.bo.opJrttAudience;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Author lili
 * @Description 媒体定向参数
 * @Date 16:30 2023/2/20
 **/
@Data
public class SuperiorPopularityTypeConfConfig {

    /**定向逻辑**/
    @JSONField(name = "flow_package")
    @JsonProperty("flow_package")
    private List<Integer> flowPackage;

    /**排除定向逻辑**/
    @JSONField(name = "exclude_flow_package")
    @JsonProperty("exclude_flow_package")
    private List<Integer> excludeFlowPackage;

}
