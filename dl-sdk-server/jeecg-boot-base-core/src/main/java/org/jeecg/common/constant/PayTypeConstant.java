package org.jeecg.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author xmh
 * @version V1.0
 * @description: 支付类型常量类
 * @date: 2022/12/21
 **/
public class PayTypeConstant {

    // 支付宝（sdk）
    public static final Integer PAY_TYPE_SDK_ALI = 1;
    // 支付宝（网页）
    public static final Integer PAY_TYPE_H5_ALI = 2;
    // 微信 web
    public static final Integer PAY_TYPE_WEIXIN_WEB = 3;
    // 微信支付 app
    public static final Integer PAY_TYPE_WEIXIN = 4;
    // 平台币支付
    public static final Integer PAY_TYPE_PC = 6;
    // 苹果支付
    public static final Integer PAY_TYPE_APPLE = 8;
    // 模拟支付
    public static final Integer PAY_TYPE_SIMULATE = 9;
    // 微信 native
    public static final Integer PAY_TYPE_WEIXIN_NATIVE = 10;
    // 微信 JS
    public static final Integer PAY_TYPE_WEIXIN_JS = 11;
    // 不允许使用平台币抵扣
    public static final Integer[] CANNOT_USE_PC = new Integer[]{PAY_TYPE_H5_ALI, PAY_TYPE_WEIXIN_WEB,
        PAY_TYPE_WEIXIN_JS, PAY_TYPE_WEIXIN_NATIVE};
    // 属于微信支付的方式
    public static final List<Integer> WECAHT_PAY = Arrays.asList(PAY_TYPE_WEIXIN, PAY_TYPE_WEIXIN_WEB, PAY_TYPE_WEIXIN_JS, PAY_TYPE_WEIXIN_NATIVE);
    // 属于支付宝的支付方式
    public static final List<Integer> ALI_PAY = Arrays.asList(PAY_TYPE_SDK_ALI, PAY_TYPE_H5_ALI);

}
