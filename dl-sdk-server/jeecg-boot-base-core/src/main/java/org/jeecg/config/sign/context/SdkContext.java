package org.jeecg.config.sign.context;

import org.jeecg.common.exception.JeecgBootException;

public class SdkContext {

    private static final ThreadLocal<SdkInfo> SDK_INFO = new ThreadLocal<>();

    public SdkContext() {
    }

    public static SdkInfo getSdkInfo() {
        return SDK_INFO.get();
    }

    public static void setSdkInfo(SdkInfo sdkInfo) {
        SDK_INFO.set(sdkInfo);
    }

    public static void remove() {
        SDK_INFO.remove();
    }
}
