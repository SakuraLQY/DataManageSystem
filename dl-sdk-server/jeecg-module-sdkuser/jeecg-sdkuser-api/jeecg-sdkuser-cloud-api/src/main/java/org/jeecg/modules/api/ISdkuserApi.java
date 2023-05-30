package org.jeecg.modules.api;

import org.jeecg.common.constant.ServiceNameConstants;
import org.jeecg.modules.api.factory.SdkuserApiFallbackFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(contextId = "sdkuserRemoteApi", value = ServiceNameConstants.SERVICE_SDKUSER, fallbackFactory = SdkuserApiFallbackFactory.class)
@ConditionalOnMissingClass("org.jeecg.modules.api.service.impl")
public interface ISdkuserApi {

    String hello();

}
