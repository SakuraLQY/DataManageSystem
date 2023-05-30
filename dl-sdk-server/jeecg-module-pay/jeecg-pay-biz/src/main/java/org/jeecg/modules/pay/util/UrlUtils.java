package org.jeecg.modules.pay.util;

/**
 * @author xmh
 * @version V1.0
 * @description: url工具类
 * @date: 2023/1/6 15:27
 **/
public class UrlUtils {

    /**
     * @param url:         url
     * @param payVendorId: 支付渠道ID
     * @return String
     * @author xmh
     * @description url加上支付渠道ID
     * @date 2023/1/6 15:31
     */
    public static String addPayVendor(String url, Integer payVendorId) {
        String suffix = "/";
        if (url.endsWith(suffix)) {
            return url + suffix + payVendorId;
        }
        return url + payVendorId;
    }
}
