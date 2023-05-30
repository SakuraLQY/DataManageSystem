package org.jeecg.modules.count.bo.callback;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 今日头条服务端埋点请求接口-header属性字典
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class JrttPaySdkHeader {

    /**
     * 包名，务必与真实投放的包名⼀致
     **/
    @JSONField(name = "app_package")
    private String appPackage;

}
