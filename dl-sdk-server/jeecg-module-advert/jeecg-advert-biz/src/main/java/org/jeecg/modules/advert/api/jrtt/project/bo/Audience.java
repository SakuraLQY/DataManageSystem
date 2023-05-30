package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.project.bo
 * @className: Audience
 * @author: Eric
 * @description: 定向包对象，文档https://open.oceanengine.com/labels/7/docs/1740868093375503
 * @date: 2023/2/17 17:08
 * @version: 1.0
 */
@Data
public class Audience {
    @JSONField(name = "audience_package_id")
    private Long audiencePackageId;

    @JSONField(name = "district")
    private String district;

    private List<Geolocation> geolocation;

    @JSONField(name = "region_version")
    private String regionVersion;

    private List<Long> city;

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

    @JSONField(name = "interest_words")
    private List<Long> interestWords;

    @JSONField(name = "awemeFanBehaviors")
    private List<String> aweme_fan_behaviors;

    @JSONField(name = "aweme_fan_time_scope")
    private String awemeFanTimeScope;

    @JSONField(name = "aweme_fan_categories")
    private List<Long> awemeFanCategories;

    @JSONField(name = "aweme_fan_accounts")
    private List<Long> awemeFanAccounts;

    @JSONField(name = "superior_popularity_type")
    private String superiorPopularityType;

    @JSONField(name = " flow_package")
    private List<Long>  flowPackage;

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
    private String hideIfExists;

    @JSONField(name = "hide_if_converted")
    private String hideIfConverted;

    @JSONField(name = "converted_time_duration")
    private String convertedTimeDuration;

    @JSONField(name = "filter_aweme_abnormal_active")
    private String filterAwemeAbnormalActive;

    @JSONField(name = "filter_aweme_fans_count")
    private Integer filterAwemeFansCount;

    @JSONField(name = "filter_own_aweme_fans")
    private String filterOwnAwemeFFans;

    @JSONField(name = "device_brand")
    private List<String> deviceBrand;

    @JSONField(name = "launch_price")
    private List<Integer> launchPrice;

    @JSONField(name = "auto_extend_targets")
    private List<String> autoExtendTargets;
}
