
package org.jeecg.modules.advert.constant.xingtu;

/**
 * @Description: 星图的广告创意中的词包id
 * @Author: lili
 * @Date: 2023-02-15
 * @Version: V1.0
 */
public enum XingtuCreativeWordId {

    /**
     * {日期}
     */
    DATE_WORD_ID(1727, "{日期}"),

    /**
     * {年龄}
     */
    AGE_WORD_ID(1737, "{年龄}"),

    /**
     * {地点}
     */
    SPLACE_WORD_ID(4, "{地点}"),

    /**
     * {星期}
     */
    WEEK_WORD_ID(1736, "{星期}");

    private final Integer code;
    private final String value;

    private XingtuCreativeWordId(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }
    public String getValue() {
        return value;
    }

    /**
     * 根据value找枚举
     *
     * @param value
     * @return
     */
    public static XingtuCreativeWordId getCode(String value) {
        for (XingtuCreativeWordId e : XingtuCreativeWordId.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }

}
