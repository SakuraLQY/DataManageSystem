
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取定向包-audience_packages  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAudiencePackagesResponse {

    /**
     * 广告主id
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 定向包id
     **/
    @JSONField(name = "audience_package_id")
    private Long audiencePackageId;

    /**
     * 定向包名称
     **/
    @JSONField(name = "name")
    private String name;

    /**
     * 定向包描述
     **/
    @JSONField(name = "description")
    private String description;

    /**
     * 定向包类型
     **/
    @JSONField(name = "landing_type")
    private String landingType;

    /**
     * 过滤已转化用户
     **/
    @JSONField(name = "hide_if_converted")
    private String hideIfConverted;

    /**
     * 过滤时间范围
     **/
    @JSONField(name = "converted_time_duration")
    private String convertedTimeDuration;

    /**
     * 广告投放范围
     **/
    @JSONField(name = "delivery_range")
    private String deliveryRange;

    /**
     * 已安装用户
     **/
    @JSONField(name = "hide_if_exists")
    private Integer hideIfExists;

    /**
     * 自定内容
     **/
    @JSONField(name = "audience")
    private JrttGetAudiencePackagesAudienceResponse audience;

//    /**
//     * 地图位置
//     **/
//    @JSONField(name = "geolocation")
//    private JrttGetAudiencePackagesGeolocationResponse geolocation;
//
//    /**
//     * 地图位置
//     **/
//    @JSONField(name = "bind_info")
//    private JrttGetAudiencePackagesGeolocationResponse bindInfo;


}
