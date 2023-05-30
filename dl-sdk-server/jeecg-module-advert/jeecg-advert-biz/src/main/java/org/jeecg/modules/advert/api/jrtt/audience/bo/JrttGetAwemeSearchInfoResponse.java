
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询抖音帐号和类目信息  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeSearchInfoResponse {

    /**
     * 广告数组
     **/
    @JSONField(name = "list")
    @JsonProperty("list")
    private List<JrttGetAwemeSearchInfoListResponse> list;

}
