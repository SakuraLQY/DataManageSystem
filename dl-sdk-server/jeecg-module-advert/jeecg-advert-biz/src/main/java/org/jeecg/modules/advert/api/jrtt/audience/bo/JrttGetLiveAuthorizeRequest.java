
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询授权直播抖音达人列表  请求参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetLiveAuthorizeRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 直播达人授权状态列表
     **/
    @JSONField(name = "status")
    private List<String> status ;

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

}
