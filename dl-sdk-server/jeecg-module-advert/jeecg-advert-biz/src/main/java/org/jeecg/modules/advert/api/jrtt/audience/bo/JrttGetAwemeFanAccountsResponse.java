
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询抖音类目下的推荐达人  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeFanAccountsResponse {

    /**
     * 抖音达人列表
     **/
    @JSONField(name = "authors")
    @JsonProperty("authors")
    private List<JrttGetAwemeFanAccountsListResponse> authors;

}
