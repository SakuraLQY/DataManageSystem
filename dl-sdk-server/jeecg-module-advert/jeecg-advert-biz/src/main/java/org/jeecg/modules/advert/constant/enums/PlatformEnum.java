package org.jeecg.modules.advert.constant.enums;

import org.jeecg.common.constant.enums.MessageTypeEnum;

/**
 * 平台枚举
 * @date 2023-04-27
 * @author: jeecg-boot
 */
public enum PlatformEnum {

    /**
     * 头条体验版-消耗拉取 android类型
     */
    JRTT_CUSTOM_REPORT_ANDROID("Android", 1),

    /**
     * 头条体验版-消耗拉取 ios类型
     */
    JRTT_CUSTOM_REPORT_IOS("IOS", 2),

    /**
     * 头条旧版-消耗拉取 android类型
     */
    JRTT_CAMPAIGN_REPORT_ANDROID("android", 1),

    /**
     * 头条旧版-消耗拉取 android类型
     */
    JRTT_CAMPAIGN_REPORT_IOS("ios", 2),

    /**
     * 其他类型
     */
    OTHER("other", 3);

    /**
     * 类型
     */
    String code;

    /**
     * 1 android 2 ios 3其他
     */
    Integer value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * 构造器
     *
     * @param code  类型
     * @param value 值
     */
    PlatformEnum(String code, Integer value) {
        this.code = code;
        this.value = value;
    }


    /**
     * 根据type获取枚举
     *
     * @param type
     * @return
     */
    public static PlatformEnum valueOfCode(String code) {
        for (PlatformEnum e : PlatformEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return OTHER;
    }
}
