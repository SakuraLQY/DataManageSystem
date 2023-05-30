
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取行政信息  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetCountryInfoResponse {

    /**
     * 人群包列表数据
     **/
    @JSONField(name = "districts")
    @JsonProperty("districts")
    private List<JrttGetCountryInfoLevelResponse> districts;

    /**
     * 行政信息版本号
     **/
    @JSONField(name = "version")
    @JsonProperty("version")
    private String version;

}
