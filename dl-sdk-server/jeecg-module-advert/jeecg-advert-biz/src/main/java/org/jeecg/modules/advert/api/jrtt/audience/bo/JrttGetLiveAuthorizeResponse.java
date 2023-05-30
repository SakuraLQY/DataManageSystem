
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询授权直播抖音达人列表  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetLiveAuthorizeResponse {

    /**
     * 分页信息
     **/
    @JSONField(name = "page_info")
    @JsonProperty("page_info")
    private List<JrttGetLiveAuthorizePageResponse> pageInfo;

    /**
     * 达人信息列表
     **/
    @JSONField(name = "list")
    @JsonProperty("list")
    private List<JrttGetLiveAuthorizeListResponse> list;

}
