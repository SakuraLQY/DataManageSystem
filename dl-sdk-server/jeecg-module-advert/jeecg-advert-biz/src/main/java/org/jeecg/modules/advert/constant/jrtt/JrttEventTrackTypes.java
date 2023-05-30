
package org.jeecg.modules.advert.constant.jrtt;

/**
 * @Description: 事件回传方式
 * @Author: lili
 * @Date: 2023-02-15
 * @Version: V1.0
 */
public enum JrttEventTrackTypes {

    /**
     * 留存天数
     */
    APPLICATION_API(0, "APPLICATION_API"),

    /**
     * 留存天数
     */
    APPLICATION_SDK(1, "APPLICATION_SDK");

    private final Integer code;
    private final String value;

    private JrttEventTrackTypes(Integer code, String value) {
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
     * 根据code找枚举
     *
     * @param code
     * @return
     */
    public static JrttEventTrackTypes getValue(Integer code) {
        for (JrttEventTrackTypes e : JrttEventTrackTypes.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

}
