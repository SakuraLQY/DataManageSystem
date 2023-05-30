package org.jeecg.modules.count.bo.callback;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 今日头条服务端埋点请求接口-user属性字典
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class JrttPaySdkUser {

    /**
     * ⽤户的唯⼀⾝份标识
     **/
    @JSONField(name = "user_unique_id")
    private String userUniqueId;

    /**
     * 安卓参数
     **/
    @JSONField(name = "udid")
    private String udid;

}
