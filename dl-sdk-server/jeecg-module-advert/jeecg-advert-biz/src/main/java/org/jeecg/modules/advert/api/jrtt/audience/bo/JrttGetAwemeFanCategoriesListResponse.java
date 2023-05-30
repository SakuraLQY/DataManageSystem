
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取抖音类目列表-categories  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeFanCategoriesListResponse {

    /**
     * 抖音类目分类
     **/
    @JSONField(name = "id")
    @JsonProperty("id")
    private Integer id;

    /**
     * 覆盖人群数
     **/
    @JSONField(name = "cover_num_str")
    @JsonProperty("cover_num_str")
    private String coverNumStr;

    /**
     * 粉丝数
     **/
    @JSONField(name = "fans_num_str")
    @JsonProperty("fans_num_str")
    private String fansNumStr;

    /**
     * 抖音类目名称
     **/
    @JSONField(name = "value")
    @JsonProperty("value")
    private String value;

    /**
     * 二级分类信息
     **/
    @JSONField(name = "children")
    @JsonProperty("children")
    private List<JrttGetAwemeFanCategoriesListResponse> children;

}
