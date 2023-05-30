package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 获取定向包 响应参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAudienceRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 页码
     **/
    @JSONField(name = "page")
    private Integer page;

    /**
     * 页面数据量,默认值: 10，取值范围1～100
     **/
    @JSONField(name = "page_size")
    private Integer pageSize;

}
