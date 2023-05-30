package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建自定义创意（营销链路）-ad_data  请求参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuCustomAdDataRequest {

    /**
     * 三级行业ID
     **/
    @JSONField(name = "third_industry_id")
    private Integer thirdIndustryId ;

    /**
     * 创意标签
     **/
    @JSONField(name = "ad_keywords")
    private List<String> adKeywords ;

    /**
     * 广告来源
     **/
    @JSONField(name = "source")
    private String source  ;

    /**
     * 是否开启来源智能生成
     **/
    @JSONField(name = "enable_smart_source")
    private String enableSmartSource ;

    /**
     * 品牌主页-推广抖音号
     **/
    @JSONField(name = "ies_core_user_id")
    private String iesCoreUserId ;

    /**
     * 主页作品列表隐藏广告内容
     **/
    @JSONField(name = "is_feed_and_fav_see")
    private Integer isFeedAndFavSee ;

//    /**
//     * 是否开启自动生成素材
//     **/
//    @JSONField(name = "creative_auto_generate_switch")
//    private Integer creativeAutoGenerateSwitch ;

//    /**
//     * 应用名
//     **/
//    @JSONField(name = "app_name")
//    private String appName ;

}
