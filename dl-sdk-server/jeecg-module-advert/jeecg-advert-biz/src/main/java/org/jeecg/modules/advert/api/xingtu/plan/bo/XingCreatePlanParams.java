package org.jeecg.modules.advert.api.xingtu.plan.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.xingtu.plan.bo
 * @className: XingCreatePlanParams
 * @author: Eric
 * @description: TODO
 * @date: 2023/3/10 18:11
 * @version: 1.0
 */
@Data
public class XingCreatePlanParams {

    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    @JSONField(name = "campaign_id")
    private Long campaignId;

    private String name;

    @JSONField(name = "aweme_account")
    private String awemeAccount;

    @JSONField(name = "delivery_range")
    private String deliveryRange;

    @JSONField(name = "inventory_catalog")
    private String inventoryCatalog;

    @JSONField(name = "inventory_type")
    private List<String> inventoryType;

    @JSONField(name = "asset_ids")
    private List<Long> assetIds;

    @JSONField(name = "external_action")
    private String externalAction;

    @JSONField(name = "deep_external_action")
    private String deep_external_action;

    @JSONField(name = "search_bid_ratio")
    private Float searchBidRatio;

    @JSONField(name = "audience_extend")
    private String audienceExtend;

    @JSONField(name = "audience_package_id")
    private Long audiencePackageId;

    @JSONField(name = "district")
    private String district;

    @JSONField(name = "region_version")
    private String regionVersion;

    @JSONField(name = "city")
    private List<Long> city;
    //地图
//    private geolocation geolocation;

    @JSONField(name = "location_type")
    private String locationType;

    private String gender;

    private List<String> age;

    @JSONField(name = "retargeting_tags_include")
    private List<Long> retargetingTagsInclude;

    @JSONField(name = "retargeting_tags_exclude")
    private List<Long> retargetingTagsExclude;

    @JSONField(name = "interest_action_mode")
    private String interestActionMode;

    @JSONField(name = "action_scene")
    private List<String> actionScene;

    @JSONField(name = "action_days")
    private Integer actionDays;

    @JSONField(name = "action_categories")
    private List<Long> actionCategories;

    @JSONField(name = "action_words")
    private List<Long> actionWords;

    @JSONField(name = "interest_categories")
    private List<Long> interestCategories;

    @JSONField(name = "interestWords")
    private List<Long> interestWords;

    @JSONField(name = "aweme_fan_behaviors")
    private List<String> awemeFanBehaviors;

    @JSONField(name = "awemeFanTimeScope")
    private String awemeFanTimeScope;

    @JSONField(name = "awemeFanCategories")
    private List<Long> aweme_fan_categories;

    @JSONField(name = "aweme_fan_accounts")
    private List<Long> awemeFanAccounts;

    @JSONField(name = "filter_aweme_abnormal_active")
    private Long filterAwemeAbnormalActive;

    @JSONField(name = "filter_aweme_fans_count")
    private Long filterAwemeFansCount;

    @JSONField(name = "filter_own_aweme_fans")
    private Long filterOwnAwemeFans;

    @JSONField(name = "superior_popularity_type")
    private String superiorPopularityType;

    @JSONField(name = "flowPackage")
    private List<Long> flowPackage;

    @JSONField(name = "flowPackage")
    private List<Long> flow_package;

    @JSONField(name = "exclude_flow_package")
    private List<Long> excludeFlowPackage;

    private List<String> platform;

    @JSONField(name = "android_osv")
    private String androidOsv;

    @JSONField(name = "ios_osv")
    private String iosOsv;

    @JSONField(name = "device_type")
    private List<String> deviceType;

    private List<String> ac;

    private List<String> carrier;

    @JSONField(name = "hide_if_exists")
    private Integer hideIfExists;

    @JSONField(name = "hide_if_converted")
    private String hideIfConverted;

    @JSONField(name = "converted_time_duration")
    private String convertedTimeDuration;

    @JSONField(name = "article_category")
    private List<String> articleCategory;

    @JSONField(name = "device_brand")
    private List<String> deviceBrand;

    @JSONField(name = "launch_price")
    private List<Long> launchPrice;

    @JSONField(name = "auto_extend_targets")
    private List<String> autoExtendTargets;

    @JSONField(name = "smart_bid_type")
    private String smartBidType;

    @JSONField(name = "flow_control_mode")
    private String flowControlMode;

    @JSONField(name = "budget_mode")
    private String budgetMode;

    private Float budget;

    @JSONField(name = "schedule_type")
    private String scheduleType;

    @JSONField(name = "start_time")
    private String startTime;

    @JSONField(name = "end_time")
    private String endTime;

    @JSONField(name = "schedule_time")
    private String scheduleTime;

    private String pricing;

    @JSONField(name = "cpa_bid")
    private Float cpaBid;

    @JSONField(name = "deep_bid_type")
    private String deepBidType;

    @JSONField(name = "action_track_url")
    private List<String> actionTrackUrl;

    @JSONField(name = "package")
    private String pkg;

    @JSONField(name = "download_url")
    private String downLoadUrl;

    @JSONField(name = "app_type")
    private String appType;

    @JSONField(name = "track_url_group_type")
    private String trackUrlGroupType;
}
