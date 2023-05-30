
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询授权直播抖音达人列表-list  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetLiveAuthorizeListResponse {

    /**
     * 抖音号id
     **/
    @JSONField(name = "ies_id")
    @JsonProperty("ies_id")
    private String iesId;

    /**
     * 抖音昵称
     **/
    @JSONField(name = "ies_user_name")
    @JsonProperty("ies_user_name")
    private String iesUserName;

    /**
     * 广告主id
     **/
    @JSONField(name = "advertiser_id")
    @JsonProperty("advertiser_id")
    private Long advertiserId;

    /**
     * 发起时间
     **/
    @JSONField(name = "create_time")
    @JsonProperty("create_time")
    private String createTime;

    /**
     * 直播授权开始时间
     **/
    @JSONField(name = "authorize_starttime")
    @JsonProperty("authorize_starttime")
    private String authorizeStarttime;

    /**
     * 直播授权结束时间
     **/
    @JSONField(name = "authorize_endtime")
    @JsonProperty("authorize_endtime")
    private String authorizeEndtime;

    /**
     * 限制抖音号使用场景信息
     **/
    @JSONField(name = "limited_promotion_types")
    @JsonProperty("limited_promotion_types")
    private List<JrttGetLiveAuthorizeListObjectResponse> limitedPromotionTypes;


}
