package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 获取已创建资产 请求参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAssetsRequest {

    /**
     * 广告主id
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 资产类型
     **/
    @JSONField(name = "asset_type")
    private String assetType;

    /**
     * 页码
     **/
    @JSONField(name = "page")
    private Integer page;

    /**
     * 页面大小
     **/
    @JSONField(name = "page_size")
    private Integer pageSize;

}
