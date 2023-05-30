package org.jeecg.modules.pay.constant;

/**
 * @author chenyw
 * @version V1.0
 * @description: 苹果校验订单返回
 * @date: 2022/1/6
 **/
public class IosVerifyReceiptStatus {

    /**
     * 请求成功
     **/
    public static final Integer SUCCESS = 0;

    /**
     * 系统繁忙
     **/
    public static final Integer BUSY = 21005;

    /**
     * 订单属于沙箱环境
     **/
    public static final Integer SAND_BOX = 21007;

}
