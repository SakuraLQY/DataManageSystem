package org.jeecg.modules.advert.api.jrtt.site.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 创建站点相应参数
 * @date: 2023/2/16 15:43
 **/
@Data
public class JrttSiteResponse {

    /**
     * 头条站点ID
     **/
    @JSONField(name = "site_id")
    private String siteId;
}
