package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取已创建事件attribution_configuration 响应参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetEventsAttributionConfigurationResponse {

    /**
     * 事件ID
     **/
    @JSONField(name = "attribution_window")
    private Integer attributionWindow;



}
