package org.jeecg.modules.api.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;;
import org.jeecg.modules.api.ISdkuserApi;


/**
 * 进入fallback的方法 检查是否token未设置
 * @author: jeecg-boot
 */
@Slf4j
public class SdkuserApiFallback implements ISdkuserApi {

    @Setter
    private Throwable cause;

    @Override
    public String hello() {
        return null;
    }

}
