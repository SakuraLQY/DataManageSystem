
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询抖音帐号和类目信息-list  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeSearchInfoListResponse {

    /**
     * 抖音类目信息
     **/
    @JSONField(name = "categories")
    @JsonProperty("categories")
    private List<JrttGetAwemeSearchInfoListCategoriesResponse> categories;

    /**
     * 抖音账户信息
     **/
    @JSONField(name = "authors")
    @JsonProperty("authors")
    private List<JrttGetAwemeSearchInfoListAuthorsResponse> authors;

}
