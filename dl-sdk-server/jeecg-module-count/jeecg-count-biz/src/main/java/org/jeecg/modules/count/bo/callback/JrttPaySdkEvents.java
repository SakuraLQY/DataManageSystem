package org.jeecg.modules.count.bo.callback;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 今日头条服务端埋点请求接口-event列表，每个元素为⼀个事件
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class JrttPaySdkEvents {

    /**
     * 事件名
     **/
    @JSONField(name = "event")
    private String event;

    /**
     * 事件参数
     **/
    @JSONField(name = "params")
    private String params;

    /**
     * 时间戳 毫秒
     **/
    @JSONField(name = "local_time_ms")
    private Long localTimeMs;

}
