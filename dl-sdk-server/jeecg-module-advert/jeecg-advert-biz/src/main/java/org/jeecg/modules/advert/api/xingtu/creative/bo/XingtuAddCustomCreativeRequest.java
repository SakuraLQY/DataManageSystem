package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建自定义创意（营销链路）  请求参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuAddCustomCreativeRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 广告计划ID
     **/
    @JSONField(name = "ad_id")
    private Long adId;

    /**
     * 自定义素材信息
     **/
    @JSONField(name = "creative_list")
    private List<XingtuCustomCreativeListRequest> creativeList  ;

    /**
     * 广告计划数据
     **/
    @JSONField(name = "ad_data")
    private XingtuCustomAdDataRequest adData ;



}
