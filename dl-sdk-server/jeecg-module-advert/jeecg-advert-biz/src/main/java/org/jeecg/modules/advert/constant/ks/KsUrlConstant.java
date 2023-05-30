package org.jeecg.modules.advert.constant.ks;

/**
 * @Description: 今日头条接口
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
public class KsUrlConstant {

    /*
     * 获取access_token
     **/
    public static final String ACCESS_TOKEN = "https://ad.e.kuaishou.com/rest/openapi/oauth2/authorize/access_token";

    /*
     * 刷新access_token
     **/
    public static final String REFRESH_TOKEN = "https://ad.e.kuaishou.com/rest/openapi/oauth2/authorize/refresh_token";

    /*
     * 上传图片 v2 接口
     **/
    public static final String IMAGE_AD = "https://ad.e.kuaishou.com/rest/openapi/v2/file/ad/image/upload";

    /*
     * 上传视频接口 v2 接口
     **/
    public static final String VIDEO_AD = "https://ad.e.kuaishou.com/rest/openapi/v2/file/ad/video/upload";

    /*
     * IOS监测链接
     **/
    public static final String IOS_TRACK_URL = "https://loggzml.booona.com/log/xingtu.php?adid=__AID__&cid=__CID__&idfa=__IDFA__&mac=__MAC__&os=__OS__&ip=__IP__&time=__TS__&callback_url=__CALLBACK_URL__&tag1=";

    /*
     * 安卓监测链接
     **/
    public static final String ANDROID_TRACK_URL = "https://loggzml.booona.com/log/xingtu.php?adid=__AID__&cid=__CID__&imei=__IMEI__&mac=__MAC__&androidid=__ANDROIDID__&os=__OS__&ip=__IP__&time=__TS__&callback_url=__CALLBACK_URL__&tag1=";

    /*
     * 创建广告计划
     **/
    public static final String CREATE_CAMPAIGN_URL = "https://ad.e.kuaishou.com/rest/openapi/v2/campaign/create";

    /*
     * 修改广告计划预算
     **/
    public static final String MODIFY_BUDGET_URL = "https://ad.e.kuaishou.com/rest/openapi/v2/campaign/update";

    /*
     * 创建快手应用
     **/
    public static final String CREATE_APP_URL="https://ad.e.kuaishou.com/rest/openapi/v1/file/ad/app/create";

    /*
     * 获取快手应用列表
     **/
    public static final String QUERY_APP_LIST_URL="https://ad.e.kuaishou.com/rest/openapi/v2/file/ad/app/list";
}
