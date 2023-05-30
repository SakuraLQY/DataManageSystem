
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询抖音号id对应的达人信息  请求参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeAuthorInfoRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 抖音号id列表
     **/
    @JSONField(name = "label_ids")
    private List<Long> labelIds ;

    /**
     * 抖音用户行为类型
     **/
    @JSONField(name = "behaviors")
    private List<String> behaviors ;

}
