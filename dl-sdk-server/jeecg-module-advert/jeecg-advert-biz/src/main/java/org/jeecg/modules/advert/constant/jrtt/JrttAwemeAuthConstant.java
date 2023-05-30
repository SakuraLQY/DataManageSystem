package org.jeecg.modules.advert.constant.jrtt;

/**
 * @Description: 抖音授权常量
 * @Author: chenyw
 * @Date: 2023-02-15
 * @Version: V1.0
 */
public class JrttAwemeAuthConstant {

    // 授权类型
    public static class Type{
        /**
         * 抖音号授权
         **/
        public static final String AWEME_ACCOUNT = "AWEME_ACCOUNT";

        /**
         * 单视频授权
         **/
        public static final String VIDEO_ITEM = "VIDEO_ITEM";
    }

    // 授权状态
    public static class Status{
        /**
         * 授权中
         **/
        public static final String AUTHRIZED = "AUTHRIZED";

        /**
         * 待授权确认
         **/
        public static final String AUTHRIZING = "AUTHRIZING";


        /**
         * 授权失效
         **/
        public static final String INVALID = "INVALID";
    }

}

