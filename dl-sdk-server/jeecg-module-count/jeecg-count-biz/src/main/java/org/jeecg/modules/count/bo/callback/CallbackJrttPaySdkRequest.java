package org.jeecg.modules.count.bo.callback;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 今日头条服务端埋点请求接口
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class CallbackJrttPaySdkRequest {

    /**
     * user属性字典
     **/
    @JSONField(name = "user")
    JrttPaySdkUser user;

    /**
     * header属性字典
     **/
    @JSONField(name = "header")
    JrttPaySdkHeader header;

    /**
     * events属性字典
     **/
    @JSONField(name = "events")
    List<JrttPaySdkEvents> events;
}
