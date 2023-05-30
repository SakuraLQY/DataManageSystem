
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询抖音号id对应的达人信息-authors  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeAuthorInfoListResponse {

    /**
     * 抖音作者名
     **/
    @JSONField(name = "author_name")
    @JsonProperty("author_name")
    private String authorName;

    /**
     * 粉丝数
     **/
    @JSONField(name = "total_fans_num_str")
    @JsonProperty("total_fans_num_str")
    private String totalFansNumStr;

    /**
     * 覆盖人群数
     **/
    @JSONField(name = "cover_num_str")
    @JsonProperty("cover_num_str")
    private String coverNumStr;

    /**
     * 抖音号id
     **/
    @JSONField(name = "label_id")
    @JsonProperty("label_id")
    private Long labelId;

    /**
     * 抖音id
     **/
    @JSONField(name = "aweme_id")
    @JsonProperty("aweme_id")
    private String awemeId;

    /**
     * 抖音头像
     **/
    @JSONField(name = "avatar")
    @JsonProperty("avatar")
    private String avatar;

}
