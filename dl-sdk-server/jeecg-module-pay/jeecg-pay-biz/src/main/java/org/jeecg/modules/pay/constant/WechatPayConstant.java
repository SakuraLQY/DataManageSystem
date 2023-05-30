package org.jeecg.modules.pay.constant;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/1/3 10:25
 **/
public class WechatPayConstant {

    // 货币类型
    public static final String CURRENCY_CNY = "CNY";
    // h5 v2 api 下单
    public static final String H5_V2_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // h5 v3 api 下单
    public static final String H5_V3_API = "https://api.mch.weixin.qq.com/v3/pay/transactions/h5";
    // app api 下单
    public static final String APP_API = "https://api.mch.weixin.qq.com/v3/pay/transactions/app";
    // js api 下单
    public static final String JS_API = "https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi";
    // native api 下单
    public static final String NATIVE_API = "https://api.mch.weixin.qq.com/v3/pay/transactions/native";
    // 商户订单号查询
    public static final String QUERY_ORDER = "https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/";
    // 下单成功返回码
    public static final int SUCCESS_CODE = 200;
    // h5 V2下单
    public static final int H5_V2_ORDER = 1;
    // h5 V3下单
    public static final int H5_V3_ORDER = 2;
    // app 下单
    public static final int APP_ORDER = 3;
    // js 下单
    public static final int JS_ORDER = 4;
    // native 下单
    public static final int NATIVE_ORDER = 5;
    // 支付成功回调通知类型
    public static final String CALLBACK_SUCCESS = "TRANSACTION.SUCCESS";
    // 交易状态 成功
    public static final String TRADE_STATE_SUCCESS = "SUCCESS";
    // 请求方式 - GET
    public static final Integer REQUEST_GET = 1;
    // 请求方式 - GET
    public static final String POST = "POST";
    // 请求方式 - POST
    public static final Integer REQUEST_POST = 2;
    // 渠道配置 JSON KEY 商户号
    public static final String VENDOR_KEY_MCHID = "mch_id";
    // 渠道配置 JSON KEY 微信版本
    public static final String VENDOR_KEY_VERSION = "version";
    // 渠道配置 JSON KEY 微信版本
    public static final String VERSION_V2 = "2";
    // 渠道配置 JSON KEY 微信版本
    public static final String VERSION_V3 = "3";
    // 渠道配置 JSON KEY 应用ID
    public static final String VENDOR_KEY_APPID = "app_id";
    // 渠道配置 JSON KEY API V3密钥
    public static final String VENDOR_KEY_APIKEY = "api_key";
    // 渠道配置 JSON KEY 商户API证书序列号
    public static final String VENDOR_KEY_SERIAL_NO = "serial_no";
    // 渠道配置 JSON KEY 商户私钥
    public static final String VENDOR_KEY_PRIVATE_KEY = "private_key";
    // 渠道配置 JSON KEY 回调地址
    public static final String VENDOR_KEY_NOTIFY_URL = "notify_url";
    // redissonLock key
    public static final String REDIS_LOCK_KEY_WECHAT = "wechatLockKey";
    // redissonLock 上锁后自动释放锁时间
    public static final int expireSeconds = 6000;
    // redissonLock 上锁后自动释放锁时间
    public static final String  TRADE_H5_V2_TYPE = "MWEB";
}
