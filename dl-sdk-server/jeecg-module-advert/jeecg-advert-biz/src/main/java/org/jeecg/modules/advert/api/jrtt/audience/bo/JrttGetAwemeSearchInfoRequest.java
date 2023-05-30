
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询抖音帐号和类目信息  请求参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeSearchInfoRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 搜索词
     **/
    @JSONField(name = "query_word")
    private String queryWord ;

    /**
     * 抖音用户行为类型
     **/
    @JSONField(name = "behaviors")
    private List<String> behaviors ;

}
