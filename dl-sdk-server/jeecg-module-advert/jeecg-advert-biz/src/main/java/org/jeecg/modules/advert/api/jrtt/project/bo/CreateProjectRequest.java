package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.project.bo
 * @className: CreateProjectRequest
 * @author: Eric
 * @description: 创建项目请求对象
 * @date: 2023/2/17 14:34
 * @version: 1.0
 */

@Data
public class CreateProjectRequest {
    @JSONField(name = "advertiser_id")
    private java.lang.Long advertiserId;

    @ApiModelProperty(value = "计划状态")
    private java.lang.String operation;

    @JSONField(name = "delivery_mode")
    private java.lang.String deliveryMode;

    @JSONField(name = "landing_type")
    private java.lang.String landingType;

    @JSONField(name = "app_promotion_type")
    private java.lang.String appPromotionType;

    @JSONField(name = "marketing_goal")
    private java.lang.String marketingGoal;

    @JSONField(name = "ad_type")
    private java.lang.String adType;

    @JSONField(name = "name")
    private java.lang.String name;

    @JSONField(name = "search_bid_ratio")
    private java.lang.Double searchBidRatio;

    @JSONField(name = "audience_extend")
    private java.lang.String audienceExtend;

    @JSONField(name = "keywords")
    private Keyword[] keywords;

    @JSONField(name = "download_url")
    private java.lang.String downloadUrl;

    @ApiModelProperty(value = "下载方式")
    @JSONField(name = "download_type")
    private java.lang.String downloadType;

    @JSONField(name = "download_mode")
    private java.lang.String downloadMode;

    @JSONField(name = "asset_type")
    private java.lang.String assetType;

    @JSONField(name = "optimize_goal")
    private OptimizeGoal optimizeGoal;

    @JSONField(name = "delivery_range")
    private DeliveryRange deliveryRange;

    @JSONField(name = "delivery_setting")
    private DeliverySetting deliverySetting;

    @JSONField(name = "track_url_setting")
    private TrackUrlSetting trackUrlSetting;

    @JSONField(name = "audience")
    private Audience audience;
}
