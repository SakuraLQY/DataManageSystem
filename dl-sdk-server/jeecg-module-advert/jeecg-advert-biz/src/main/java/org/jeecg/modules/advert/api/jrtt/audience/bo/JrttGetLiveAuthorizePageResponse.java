
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询授权直播抖音达人列表-page_info  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetLiveAuthorizePageResponse {

    /**
     * 页数
     **/
    @JSONField(name = "page")
    @JsonProperty("page")
    private Integer page;

    /**
     * 页面大小
     **/
    @JSONField(name = "page_size")
    @JsonProperty("page_size")
    private Integer pageSize;

    /**
     * 总数
     **/
    @JSONField(name = "total_number")
    @JsonProperty("total_number")
    private Integer totalNumber;

    /**
     * 总页数
     **/
    @JSONField(name = "total_page")
    @JsonProperty("total_page")
    private Integer totalPage;


}
