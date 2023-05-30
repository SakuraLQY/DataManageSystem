package org.jeecg.modules.advert.constant.xingtu;

/**
 * @author xmh
 * @version V1.0
 * @description: 星图接口
 * @date: 2023/3/3 11:44
 **/
public class XingtuUrlConstant {

    /*
     * IOS监测链接
     **/
    public static final String IOS_TRACK_URL = "https://loggzml.booona.com/log/xingtu.php?adid=__AID__&cid=__CID__&idfa=__IDFA__&mac=__MAC__&os=__OS__&ip=__IP__&time=__TS__&callback_url=__CALLBACK_URL__&tag1=";

    /*
     * 安卓监测链接
     **/
    public static final String ANDROID_TRACK_URL = "https://loggzml.booona.com/log/xingtu.php?adid=__AID__&cid=__CID__&imei=__IMEI__&mac=__MAC__&androidid=__ANDROIDID__&os=__OS__&ip=__IP__&time=__TS__&callback_url=__CALLBACK_URL__&tag1=";

    /*
     * 创建广告组
     **/
    public static final String CREATE_CAMPAIGN_URL = "https://ad.oceanengine.com/open_api/2/campaign/create/";
}
