package org.jeecg.modules.advert.api.jrtt.site.bo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 修改站点请求参数
 * @date: 2023/3/3 17:06
 **/
@Data
public class JrttModifySiteRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 站点名称
     **/
    @JSONField(name = "name")
    private String siteName;

    /**
     * 站点组件数据
     **/
    @JSONField(name = "bricks")
    private JSONArray siteContent;

    /**
     * 站点ID
     **/
    @JSONField(name = "site_id")
    private String siteId;
}
