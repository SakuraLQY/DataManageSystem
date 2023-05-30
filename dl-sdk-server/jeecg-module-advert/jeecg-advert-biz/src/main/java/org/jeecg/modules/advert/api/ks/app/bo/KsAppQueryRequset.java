package org.jeecg.modules.advert.api.ks.app.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class KsAppQueryRequset {

    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    @JSONField(name = "page")
    private Integer page;

    @JSONField(name = "page_size")
    private Integer pageSize;
}
