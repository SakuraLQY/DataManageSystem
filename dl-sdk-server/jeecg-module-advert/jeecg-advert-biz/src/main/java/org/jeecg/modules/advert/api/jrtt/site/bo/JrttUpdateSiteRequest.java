package org.jeecg.modules.advert.api.jrtt.site.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 更改站点状态请求数据
 * @date: 2023/2/20 17:45
 **/
@Data
public class JrttUpdateSiteRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 橙子建站站点ID列表
     **/
    @JSONField(name = "site_ids")
    private String[] siteIDs;

    /**
     * 站点状态
     **/
    @JSONField(name = "status")
    private String status;
}
