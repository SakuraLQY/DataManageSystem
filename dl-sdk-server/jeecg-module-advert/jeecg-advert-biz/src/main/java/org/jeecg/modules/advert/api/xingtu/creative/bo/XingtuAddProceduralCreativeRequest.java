package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建程序化创意（营销链路）  请求参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuAddProceduralCreativeRequest {

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
     * 程序化素材信息
     **/
    @JSONField(name = "creative")
    private XingtuCreativeRequest creative ;

    /**
     * 广告计划数据
     **/
    @JSONField(name = "ad_data")
    private XingtuAdDataRequest adData ;



}
