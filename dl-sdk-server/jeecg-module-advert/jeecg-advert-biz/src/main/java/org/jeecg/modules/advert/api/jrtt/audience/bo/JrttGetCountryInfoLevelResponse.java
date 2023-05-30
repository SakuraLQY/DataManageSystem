
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
public class JrttGetCountryInfoLevelResponse {

    /**
     * 行政区域名称
     **/
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

    /**
     * 行政区域层级
     **/
    @JSONField(name = "level")
    @JsonProperty("level")
    private String level;

    /**
     * 中国大陆行政区域编码
     **/
    @JSONField(name = "code")
    @JsonProperty("code")
    private String code;

    /**
     * 港澳台、国外行政区域编码
     **/
    @JSONField(name = "geoname_id")
    @JsonProperty("geoname_id")
    private Integer geonameId;

    /**
     * 子行政层级信息
     **/
    @JSONField(name = "sub_districts")
    @JsonProperty("sub_districts")
    private List<JrttGetCountryInfoLevelResponse> subDistricts;

}
