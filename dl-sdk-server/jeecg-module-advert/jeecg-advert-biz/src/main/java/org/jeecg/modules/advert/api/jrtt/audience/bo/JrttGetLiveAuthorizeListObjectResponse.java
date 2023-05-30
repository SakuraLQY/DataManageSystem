
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description: 查询授权直播抖音达人列表-list-limited_promotion_types  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetLiveAuthorizeListObjectResponse {

    /**
     * 场景信息
     **/
    @JSONField(name = "msg")
    @JsonProperty("msg")
    private String msg;

}
