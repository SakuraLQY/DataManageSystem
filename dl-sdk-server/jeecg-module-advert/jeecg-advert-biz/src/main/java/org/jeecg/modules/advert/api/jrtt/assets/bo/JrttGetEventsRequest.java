package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 获取已创建事件 请求参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetEventsRequest {

    /**
     * 广告主id
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 资产ID
     **/
    @JSONField(name = "asset_id")
    private Long assetId;

}
