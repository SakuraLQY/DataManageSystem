package org.jeecg.modules.pay.constant;

import java.util.List;

/**
 * @Author lili
 * @Description 支付宝参数
 * @Date 2022-12-24
 **/
public class AlipayConstant {

    //支付宝网关地址
    public static final String H5_API = "https://openapi.alipay.com/gateway.do";
    // h5 下单
    public static final int H5_ORDER = 1;
    // app 下单
    public static final int APP_ORDER = 2;
    //参数返回格式
    public static final String FORMAT = "JSON";
    //编码集
    public static final String CHARSET = "UTF8";
    //签名算法类型
    public static final String SIGN_TYPE = "RSA2";
    //版本
    public static final String VERSION = "1.0";
    //同步通知地址
    public static final String RETURN_URL = "";
    // 超时
    public static final String timeoutExpress = "2m";
    //用户付款中途退出返回商户网站的地址
    public static final String QUIT_URL = "";
    // 渠道配置 JSON KEY 应用ID
    public static final String VENDOR_KEY_APPID = "app_id";
    // 渠道配置 JSON KEY API 合作者
    public static final String VENDOR_KEY_SELLER_ID = "seller_id";
    // 渠道配置 JSON KEY 邮箱
    public static final String VENDOR_KEY_SELLER_EMAIL = "seller_email";
    // 渠道配置 JSON KEY 公钥
    public static final String VENDOR_KEY_SELLER_PUBLIC = "alipay_pubkey2";
    // 渠道配置 JSON KEY 商户私钥
    public static final String VENDOR_KEY_SELLER_PRIKEY = "seller_prikey";
    // 渠道配置 JSON KEY 回调地址
    public static final String VENDOR_KEY_NOTIFY_URL = "notify_url";
//    public static final List<AlipayField> WAP_NOTIFY;





}
