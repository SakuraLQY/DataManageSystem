package org.jeecg.modules.advert.constant.jrtt;

/**
 * @Author lili
 * @Description //深度优化方式-IOS
 * @Date $ 2023-2-15$
 **/
public enum JrttDeepBidTypeConstantIOS {

    /**
     * 自动优化
     */
    DEEP_BID_PACING("自动优化", "DEEP_BID_PACING"),
    /**
     * 自定义双出价
     */
    DEEP_BID_MIN("自定义双出价", "DEEP_BID_MIN"),
    /**
     * 自动优化
     */
    SMARTBID("自动优化", "SMARTBID"),
    /**
     * 自定义双出价
     */
    AUTO_MIN_SECOND_STAGE("自定义双出价", "AUTO_MIN_SECOND_STAGE"),
    /**
     * ROI系数
     */
    ROI_COEFFICIENT("ROI系数", "ROI_COEFFICIENT"),
    /**
     *  ROI系数——自动优化
     */
    ROI_PACING(" ROI系数——自动优化", "ROI_PACING"),
    /**
     * ROI直投
     */
    ROI_DIRECT_MAIL("ROI直投", "ROI_DIRECT_MAIL"),
    /**
     * 两阶段优化
     */
    MIN_SECOND_STAGE("两阶段优化", "MIN_SECOND_STAGE"),
    /**
     * 动态两阶段
     */
    PACING_SECOND_STAGE("动态两阶段", "PACING_SECOND_STAGE"),
    /**
     * 每次付费出价
     */
    BID_PER_ACTION("每次付费出价", "BID_PER_ACTION"),
    /**
     * ROI三出价
     */
    SOCIAL_ROI("ROI三出价", "SOCIAL_ROI"),
    /**
     * 留存天数
     */
    DEEP_BID_TYPE_RETENTION_DAYS("留存天数", "DEEP_BID_TYPE_RETENTION_DAYS");


    private final String chineseName;
    private final String englishName;

    private JrttDeepBidTypeConstantIOS(String chineseName, String englishName) {
        this.chineseName = chineseName;
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }
    public String getEnglishName() {
        return englishName;
    }

}
