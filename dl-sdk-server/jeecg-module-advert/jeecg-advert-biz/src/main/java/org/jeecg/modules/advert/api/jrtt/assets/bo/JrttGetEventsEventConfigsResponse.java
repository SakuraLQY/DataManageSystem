package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取已创建事件event_configs 响应参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetEventsEventConfigsResponse {

    /**
     * 事件ID
     **/
    @JSONField(name = "event_id")
    private Integer eventId;

    /**
     * 事件类型
     **/
    @JSONField(name = "event_type")
    private String eventType;

    /**
     * 事件中文名称
     **/
    @JSONField(name = "event_cn_name")
    private String eventCnName;

    /**
     * 事件回传方式列表
     **/
    @JSONField(name = "track_types")
    private List<String> trackTypes;

    /**
     * 激活免联调状态
     **/
    @JSONField(name = "debugging_status")
    private String debuggingStatus;
}
