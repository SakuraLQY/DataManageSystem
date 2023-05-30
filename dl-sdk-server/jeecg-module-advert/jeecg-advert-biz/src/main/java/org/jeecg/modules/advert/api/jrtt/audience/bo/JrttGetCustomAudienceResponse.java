
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 人群包列表  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetCustomAudienceResponse {

    /**
     * 人群包列表数据
     **/
    @JSONField(name = "custom_audience_list")
    @JsonProperty("custom_audience_list")
    private List<JrttGetCustomAudienceListResponse> customAudienceList;

    /**
     * 偏移
     **/
    @JSONField(name = "offset")
    @JsonProperty("offset")
    private Integer offset;

    /**
     * 总的人群包数量
     **/
    @JSONField(name = "total_num")
    @JsonProperty("total_num")
    private Integer totalNum;

}
