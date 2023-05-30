package org.jeecg.modules.advert.constant.jrtt;

/**
 * @Author lili
 * @Description //深度优化方式-IOS
 * @Date $ 2023-2-15$
 **/
public enum JrttDeepExternalActionConstantIOS {

    /**
     * 付费
     */
    AD_CONVERT_TYPE_PAY("付费", "AD_CONVERT_TYPE_PAY"),
    /**
     * 次留
     */
    AD_CONVERT_TYPE_NEXT_DAY_OPEN("次留", "AD_CONVERT_TYPE_NEXT_DAY_OPEN"),
    /**
     * 广告变现ROI
     */
    AD_CONVERT_TYPE_LT_ROI("广告变现ROI", "AD_CONVERT_TYPE_LT_ROI"),
    /**
     * 付费ROI
     */
    AD_CONVERT_TYPE_PURCHASE_ROI("付费ROI", "AD_CONVERT_TYPE_PURCHASE_ROI");


    private final String chineseName;
    private final String englishName;

    private JrttDeepExternalActionConstantIOS(String chineseName, String englishName) {
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
