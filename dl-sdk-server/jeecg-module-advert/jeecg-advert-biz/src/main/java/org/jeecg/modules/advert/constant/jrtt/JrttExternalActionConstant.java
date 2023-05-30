package org.jeecg.modules.advert.constant.jrtt;

/**
 * @Author lili
 * @Description //优化方式-IOS
 * @Date $ 2023-2-15$
 **/
public enum JrttExternalActionConstant {

    /**
     * 激活
     */
    AD_CONVERT_TYPE_ACTIVE("激活",8, "AD_CONVERT_TYPE_ACTIVE"),

    /**
     * 注册
     */
    AD_CONVERT_TYPE_ACTIVE_REGISTER("注册",13, "AD_CONVERT_TYPE_ACTIVE_REGISTER"),

    /**
     * 付费
     */
    AD_CONVERT_TYPE_PAY("付费",14, "AD_CONVERT_TYPE_PAY"),

    /**
     * app内下单
     */
    AD_CONVERT_TYPE_APP_ORDER("app内下单",20, "AD_CONVERT_TYPE_APP_ORDER"),

    /**
     * 关键行为
     */
    AD_CONVERT_TYPE_GAME_ADDICTION("关键行为",25, "AD_CONVERT_TYPE_GAME_ADDICTION"),

    /**
     * 点击量
     */
    AD_CONVERT_TYPE_CLICK_NUM("点击量",-2, "AD_CONVERT_TYPE_CLICK_NUM"),

    /**
     * 展示量
     */
    AD_CONVERT_TYPE_SHOW_OFF_NUM("展示量", -1,"AD_CONVERT_TYPE_SHOW_OFF_NUM"),

    /**
     * 下载完成
     */
    AD_CONVERT_TYPE_DOWNLOAD_FINISH("下载完成",4, "AD_CONVERT_TYPE_DOWNLOAD_FINISH"),

    /**
     * 安装完成
     */
    AD_CONVERT_TYPE_INSTALL_FINISH("安装完成",15, "AD_CONVERT_TYPE_INSTALL_FINISH");



    private final String chineseName;
    private final Integer eventId;
    private final String englishName;

    private JrttExternalActionConstant(String chineseName, Integer eventId, String englishName) {
        this.chineseName = chineseName;
        this.eventId = eventId;
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }
    public Integer getEventId() {
        return eventId;
    }
    public String getEnglishName() {
        return englishName;
    }

    /**
     * 根据eventId找枚举
     *
     * @param eventId
     * @return
     */
    public static JrttExternalActionConstant getByEventId(Integer eventId) {
        for (JrttExternalActionConstant e : JrttExternalActionConstant.values()) {
            if (e.eventId.equals(eventId)) {
                return e;
            }
        }
        return null;
    }

}
