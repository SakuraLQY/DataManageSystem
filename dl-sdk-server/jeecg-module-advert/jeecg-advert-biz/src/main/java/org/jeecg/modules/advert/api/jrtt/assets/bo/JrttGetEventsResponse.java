package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取已创建事件 响应参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetEventsResponse {

    /**
     * 应用数据
     **/
    @JSONField(name = "event_configs")
    private List<JrttGetEventsEventConfigsResponse> eventConfigs;

}
