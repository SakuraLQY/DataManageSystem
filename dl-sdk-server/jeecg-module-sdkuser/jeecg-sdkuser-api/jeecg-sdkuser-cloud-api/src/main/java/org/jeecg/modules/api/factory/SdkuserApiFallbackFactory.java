package org.jeecg.modules.api.factory;


import org.jeecg.modules.api.ISdkuserApi;
import org.jeecg.modules.api.fallback.SdkuserApiFallback;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Description: SysBaseAPIFallbackFactory
 * @author: jeecg-boot
 */
@Component
public class SdkuserApiFallbackFactory implements FallbackFactory<ISdkuserApi> {

    @Override
    public ISdkuserApi create(Throwable throwable) {
        SdkuserApiFallback fallback = new SdkuserApiFallback();
        fallback.setCause(throwable);
        return fallback;
    }

}