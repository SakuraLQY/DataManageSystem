
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.models.auth.In;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询抖音类目下的推荐达人  请求参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeFanAccountsRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 类目id
     **/
    @JSONField(name = "category_id")
    private Integer categoryId ;

    /**
     * 抖音用户行为类型
     **/
    @JSONField(name = "behaviors")
    private List<String> behaviors ;

}
