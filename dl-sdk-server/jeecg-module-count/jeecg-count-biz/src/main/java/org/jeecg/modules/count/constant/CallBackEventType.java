package org.jeecg.modules.count.constant;

/**
 * @Description: ct_callback回调事件类型
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
public class CallBackEventType {

    /**
     * 今日头条-激活
     **/
    public static final Integer JRTT_START = 0;

    /**
     * 今日头条-注册
     **/
    public static final Integer JRTT_REGISTER = 1;

    /**
     * 今日头条-支付
     **/
    public static final Integer JRTT_PAY = 2;

    /**
     * 今日头条-支付-sdk回传
     **/
    public static final Integer JRTT_SDK_PAY = 99;

}
