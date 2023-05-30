package org.jeecg.modules.advert.constant.jrtt;

/**
 * @Description: 今日头条接口
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
public class JrttUrlConstant {

    /*
     * 获取access_token
     **/
    public static final String ACCESS_TOKEN = "https://ad.oceanengine.com/open_api/oauth2/access_token/";

    /*
     * 刷新access_token
     **/
    public static final String REFRESH_TOKEN = "https://ad.oceanengine.com/open_api/oauth2/refresh_token/";

    /*
     * 获取纵横组织下资产账户列表
     **/
    public static final String ADVERTISER_SELECT = "https://ad.oceanengine.com/open_api/2/majordomo/advertiser/select/";

    /*
     * IOS监测链接
     **/
    public static final String IOS_TRACK_URL = "https://loggzml.booona.com/log/jrtt.php?projectId=__PROJECT_ID__&promotionId=__PROMOTION_ID__&campaignId=__CAMPAIGN_ID__&aid=__AID__&cid=__CID__&idfa=__IDFA__&mac=__MAC__&os=__OS__&ip=__IP__&time=__TS__&callbackUrl=__CALLBACK_URL__&deal_id=";

    /*
     * 安卓监测链接
     **/
    public static final String ANDROID_TRACK_URL = "https://loggzml.booona.com/log/jrtt.php?projectId=__PROJECT_ID__&promotionId=__PROMOTION_ID__&campaignId=__CAMPAIGN_ID__&aid=__AID__&cid=__CID__&androidId=__ANDROIDID__&oaid=__OAID__&imei=__IMEI__&mac=__MAC__&os=__OS__&ip=__IP__&time=__TS__&callbackUrl=__CALLBACK_URL__&deal_id=";

    /*
     * 创建应用分包(支持所有账户体系)
     **/
    public static final String EXTEND_PACKAGE_V2 = "https://ad.oceanengine.com/open_api/2/tools/app_management/extend_package/create_v2/";

    /*
     * 查询应用分包列表 （支持所有账户体系）
     **/
    public static final String EXTEND_PACKAGE_LIST_V2 = "https://ad.oceanengine.com/open_api/2/tools/app_management/extend_package/list_v2/";

    /*
     * 新增资产
     **/
    public static final String ADD_ASSETS = "https://ad.oceanengine.com/open_api/2/event_manager/assets/create/";

    /*
     * 获取已创建资产
     **/
    public static final String GET_ASSETS = "https://ad.oceanengine.com/open_api/2/tools/event/assets/get/";

    /*
     * 获取已创建事件
     **/
    public static final String GET_EVENTS = "https://ad.oceanengine.com/open_api/2/event_manager/event_configs/get/";

    /*
     * 创建事件
     **/
    public static final String CREAT_EVENTS = "https://ad.oceanengine.com/open_api/2/event_manager/events/create/";

    /*
     * 创建事件
     **/
    public static final String CREAT_TRACK_URL = "https://ad.oceanengine.com/open_api/2/event_manager/track_url/create/";
    /*
     * 行为类目查询
     **/
    public static final String SEARCH_ACTION_CATEGORIES_URL = "https://ad.oceanengine.com/open_api/2/tools/interest_action/action/category/";
    /*
     * 兴趣类目查询
     **/
    public static final String SEARCH_INTEREST_CATEGORIES_URL = "https://ad.oceanengine.com/open_api/2/tools/interest_action/interest/category/";
    /*
     * 行为关键字查询
     **/
    public static final String SEARCH_ACTION_KEYWORD_URL = "https://ad.oceanengine.com/open_api/2/tools/interest_action/action/keyword/";
    /*
     * 兴趣关键字查询
     **/
    public static final String SEARCH_INTEREST_KEYWORD_URL = "https://ad.oceanengine.com/open_api/2/tools/interest_action/interest/keyword/";
    /*
     * 兴趣行为类目关键词id转词
     **/
    public static final String SEARCH_ACTION_INTEREST_ID2WORD_URL = "https://ad.oceanengine.com/open_api/2/tools/interest_action/id2word/";
    /*
     * 获取行为兴趣推荐关键词
     **/
    public static final String SEARCH_ACTION_INTEREST_SUGGEST_URL = "https://ad.oceanengine.com/open_api/2/tools/interest_action/keyword/suggest/";

    /*
     * 创建站点
     **/
    public static final String CREATE_SITE = "https://ad.oceanengine.com/open_api/2/tools/site/create/";
    /*
     * 创建定向包
     **/
    public static final String CREAT_AUDIENCE_PACKAGE_URL = "https://ad.oceanengine.com/open_api/2/audience_package/create/";

    /*
     * 获取定向包
     **/
    public static final String GET_AUDIENCE_PACKAGE_URL = "https://ad.oceanengine.com/open_api/2/audience_package/get/";

    /*
     * 获取抖音授权关系
     **/
    public static final String AWEME_AUTH_LIST = "https://ad.oceanengine.com/open_api/2/tools/aweme_auth_list/";

    /*
     * 创建广告
     **/
    public static final String PROMOTION_CREATE = "https://api.oceanengine.com/open_api/v3.0/promotion/create/";

    /*
     * 更新广告
     **/
    public static final String PROMOTION_UPDATE = "https://api.oceanengine.com/open_api/v3.0/promotion/update/";

    /*
     * 更新定向包
     **/
    public static final String UPDATE_AUDIENCE_PACKAGE_URL = "https://ad.oceanengine.com/open_api/2/audience_package/update/";

    /*
     * 删除定向包
     **/
    public static final String DELETE_AUDIENCE_PACKAGE_URL = "https://ad.oceanengine.com/open_api/2/audience_package/delete/";

    /*
     * 获取人群包列表
     **/
    public static final String GET_CUSTOM_AUDIENCE_URL = "https://ad.oceanengine.com/open_api/2/dmp/custom_audience/select/";

    /*
     * 获取人群包列表
     **/
    public static final String GET_COUNTRY_INFO_URL = "https://ad.oceanengine.com/open_api/2/tools/admin/info/";

    /*
     * 查询抖音类目列表
     **/
    public static final String GET_AWEME_FAN_CATEGORIES_URL = "https://ad.oceanengine.com/open_api/2/tools/aweme_multi_level_category/get/";

    /*
     * 查询抖音号id对应的达人信息
     **/
    public static final String GET_AWEME_AUTHOR_INFO_URL = "https://ad.oceanengine.com/open_api/2/tools/aweme_author_info/get/";

    /*
     * 查询抖音帐号和类目信息
     **/
    public static final String GET_AWEME_INFO_SEARCH_URL = "https://ad.oceanengine.com/open_api/2/tools/aweme_info_search/";

    /*
     * 查询抖音类似帐号
     **/
    public static final String GET_AWEME_SIMILAR_AUTHOR_SEARCH_URL = "https://ad.oceanengine.com/open_api/2/tools/aweme_similar_author_search/";

    /*
     * 查询抖音类目下的推荐达人
     **/
    public static final String GET_AWEME_CATEGORY_TOP_AUTHOR_URL = "https://ad.oceanengine.com/open_api/2/tools/aweme_category_top_author/get/";

    /*
     * 查询授权直播抖音达人列表
     **/
    public static final String GET_LIVE_AUTHORIZE_URL = "https://ad.oceanengine.com/open_api/2/tools/live_authorize/list/";

    /*
     * 发布站点
     **/
    public static final String POST_SITE = "https://ad.oceanengine.com/open_api/2/tools/site/update_status/";

    /*
     * 修改站点
     **/
    public static final String MODIFY_SITE = "https://ad.oceanengine.com/open_api/2/tools/site/update/";

    /*
     * 上传广告图片
     **/
    public static final String IMAGE_AD = "https://ad.oceanengine.com/open_api/2/file/image/ad/";

    /*
     * 上传视频
     **/
    public static final String VIDEO_AD = "https://ad.oceanengine.com/open_api/2/file/video/ad/";

    /*
     * 橙子建站根地址
     **/
    public static final String CHENGZIJIANZHAN = "https://www.chengzijianzhan.com/tetris/page/";

    /*
     * 获取行业列表
     **/
    public static final String GET_INDUSTRY = "https://ad.oceanengine.com/open_api/2/tools/industry/get/";

    /*
     * 创建自定义创意（营销链路）
     **/
    public static final String CREATE_CUSTOM_CREATIVE = "https://ad.oceanengine.com/open_api/2/creative/custom_creative/create/";

    /*
     * 创建程序化创意（营销链路）
     **/
    public static final String CREATE_PROCEDURAL_CREATIVE = "https://ad.oceanengine.com/open_api/2/creative/procedural_creative/create/";

    /*
     * 修改程序化创意（营销链路）
     **/
    public static final String CREATE_PROCEDURAL_UPDATE = "https://ad.oceanengine.com/open_api/2/creative/procedural_creative/update/";

    /*
     * 修改自定义创意（营销链路）
     **/
    public static final String CREATE_CUSTOM_UPDATE = "https://ad.oceanengine.com/open_api/2/creative/custom_creative/update/";

    /*
     * 自定义报表（体验版成本拉取）
     **/
    public static final String GET_REPORT_CUSTOM = "https://api.oceanengine.com/open_api/v3.0/report/custom/get/";

    /*
     * 广告组数据（旧版成本拉取）
     **/
    public static final String GET_REPORT_CAMPAIGN = "https://ad.oceanengine.com/open_api/2/report/campaign/get/";

}
