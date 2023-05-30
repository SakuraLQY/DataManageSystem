
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 查询抖音类似帐号  请求参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAwemeSimilarAccountsRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 抖音id
     **/
    @JSONField(name = "aweme_id")
    private String awemeId ;

    /**
     * 抖音用户行为类型
     **/
    @JSONField(name = "behaviors")
    private List<String> behaviors ;

}
