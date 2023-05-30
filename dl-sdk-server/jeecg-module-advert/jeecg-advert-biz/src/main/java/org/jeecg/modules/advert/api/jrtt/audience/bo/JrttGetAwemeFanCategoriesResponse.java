
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取抖音类目列表  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeFanCategoriesResponse {

    /**
     * 人群包列表数据
     **/
    @JSONField(name = "categories")
    @JsonProperty("categories")
    private List<JrttGetAwemeFanCategoriesListResponse> categories;

}
