package org.jeecg.common.constant.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * online表单枚举 代码生成器用到
 * @author: jeecg-boot
 */
public enum PayTypeEnum {

    /**
     * 支付宝（sdk）
     */
    ZFB(1,"支付宝"),

    /**
     * 支付宝(web)
     */
    ZFB_WEB(2,"支付宝(web)"),

    /**
     * 微信(web)
     */
    WX_WEB(3,"微信(web)"),

    /**
     * 微信（app）
     */
    WX_APP(4,"微信(app)"),

    /**
     * 平台币
     */
    PINGTAIBI(6,"平台币"),

    /**
     * IOS正版支付
     */
    IOS(8,"IOS正版支付"),

    /**
     * 模拟支付
     */
    SIMULATE(9,"模拟支付"),

    /**
     * 微信(native)
     */
    WX_NATIVE(10,"微信(native)"),

    /**
     * 微信(jsapi)
     */
    WX_JSAPI(11,"微信(jsapi)");

    private Integer code;
    private String name;

    /**
     * 构造器
     *
     * @param name 名称
     * @param code 模板编码
     */
    private PayTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据code获取名称
     *
     * @param code
     * @return
     */
    public static String getTemplatePathByConfig(Integer code) {
        return getCgformEnumByConfig(code).name;
    }


    /**
     * 根据code找枚举
     *
     * @param code
     * @return
     */
    public static PayTypeEnum getCgformEnumByConfig(Integer code) {
        for (PayTypeEnum e : PayTypeEnum.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

}
