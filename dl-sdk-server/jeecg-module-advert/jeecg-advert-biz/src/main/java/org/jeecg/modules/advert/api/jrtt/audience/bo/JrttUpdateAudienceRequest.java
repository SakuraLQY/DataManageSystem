
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 更新定向包  请求参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttUpdateAudienceRequest {

    /**
     * 广告主id
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

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
     * 修改的定向包ID
     **/
    @JSONField(name = "audience_package_id")
    private Long audiencePackageId;

    /**
     * 定向人群包列表
     **/
    @JSONField(name = "retargeting_tags")
    private List<Integer> retargetingTags;

    /**
     * 排除人群包列表
     **/
    @JSONField(name = "retargeting_tags_exclude")
    private List<Integer> retargetingTagsExclude;

    /**
     * 受众性别
     **/
    @JSONField(name = "gender")
    private String gender;

    /**
     * 受众年龄区间
     **/
    @JSONField(name = "age")
    private List<String> age;

    /**
     * 受众最低android版本(
     **/
    @JSONField(name = "android_osv")
    private String androidOsv;

    /**
     * 受众最低ios版本
     **/
    @JSONField(name = "ios_osv")
    private String iosOsv;

    /**
     * 受众运营商
     **/
    @JSONField(name = "carrier")
    private List<String> carrier;

    /**
     * 受众网络类型
     **/
    @JSONField(name = "ac")
    private List<String> ac;

    /**
     * 受众手机品牌
     **/
    @JSONField(name = "device_brand")
    private List<String> deviceBrand;

    /**
     * 受众文章分类
     **/
    @JSONField(name = "article_category")
    private List<String> articleCategory;

    /**
     * 受众平台
     **/
    @JSONField(name = "platform")
    private List<String> platform;

    /**
     * 智能放量定向
     **/
    @JSONField(name = "auto_extend_targets")
    private List<String> autoExtendTargets;

    /**
     * 手机价格定向,传入价格区间
     **/
    @JSONField(name = "launch_price")
    private List<Integer> launchPrice;

    /**
     * 行为兴趣选择
     **/
    @JSONField(name = "interest_action_mode")
    private String interestActionMode;

    /**
     * 行为场景
     **/
    @JSONField(name = "action_scene")
    private List<String> actionScene;

    /**
     * 行为天数
     **/
    @JSONField(name = "action_days")
    private Integer actionDays;

    /**
     * 行为类目
     **/
    @JSONField(name = "action_categories")
    private List<Integer> actionCategories;

    /**
     * 行为关键词
     **/
    @JSONField(name = "action_words")
    private List<Integer> actionWords;

    /**
     * 兴趣分类
     **/
    @JSONField(name = "interest_categories")
    private List<Integer> interestCategories;

    /**
     * 兴趣关键词
     **/
    @JSONField(name = "interest_words")
    private List<Integer> interestWords;

    /**
     * 地域类型
     **/
    @JSONField(name = "district")
    private String district;

    /**
     * 行政区域版本号
     **/
    @JSONField(name = "region_version")
    private String regionVersion;

    /**
     * 地域定向省市或者区县列表
     **/
    @JSONField(name = "city")
    private List<Integer> city;

    /**
     * 受众位置类型
     **/
    @JSONField(name = "location_type")
    private String locationType;

    /**
     * 媒体定向
     **/
    @JSONField(name = "superior_popularity_type")
    private String superiorPopularityType;

    /**
     * 定向逻辑
     **/
    @JSONField(name = "flow_package")
    private List<Integer> flowPackage;

    /**
     * 排除定向逻辑
     **/
    @JSONField(name = "exclude_flow_package")
    private List<Integer> excludeFlowPackage;

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
     * 设备类型
     **/
    @JSONField(name = "device_type")
    private List<String> deviceType;

    /**
     * 地图位置
     **/
    @JSONField(name = "geolocation")
    private Object geolocation;

    /**
     * 账号粉丝相似人群
     **/
    @JSONField(name = "aweme_fans_numbers")
    private List<Integer> awemeFansNumbers;

    /**
     * 过滤高活跃用户
     **/
    @JSONField(name = "filter_aweme_abnormal_active")
    private Integer filterAwemeAbnormalActive;

    /**
     * 过滤高关注数用户
     **/
    @JSONField(name = "filter_aweme_fans_count")
    private Integer filterAwemeFansCount;

    /**
     * 过滤自己的粉丝
     **/
    @JSONField(name = "filter_own_aweme_fans")
    private Integer filterOwnAwemeFans;

    /**
     * 抖音号id
     **/
    @JSONField(name = "aweme_fan_accounts")
    private List<Long> awemeFanAccounts;

    /**
     * 抖音类目id
     **/
    @JSONField(name = "aweme_fan_categories")
    private List<Long> awemeFanCategories;

    /**
     * 抖音用户行为类型
     **/
    @JSONField(name = "aweme_fan_behaviors")
    private List<String> awemeFanBehaviors;

    /**
     * 抖音达人互动行为时间范围
     **/
    @JSONField(name = "aweme_fan_time_scope")
    private String awemeFanTimeScope;

    /**
     * 已安装用户
     **/
    @JSONField(name = "hide_if_exists")
    private Integer hideIfExists;

}
