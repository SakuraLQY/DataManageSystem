package org.jeecg.modules.advert.bo.opJrttAudience;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Author lili
 * @Description 地域类型参数
 * @Date 16:30 2023/2/20
 **/
@Data
public class DistrictConfConfig {

    /**行为类目**/
    @JSONField(name = "region_version")
    @JsonProperty("region_version")
    private String regionVersion;

    /**地域定向省市或者区县列表**/
    @JSONField(name = "city")
    private List<Integer> city;

    /**受众位置类型**/
    @JSONField(name = "location_type")
    @JsonProperty("location_type")
    private String locationType;

}
