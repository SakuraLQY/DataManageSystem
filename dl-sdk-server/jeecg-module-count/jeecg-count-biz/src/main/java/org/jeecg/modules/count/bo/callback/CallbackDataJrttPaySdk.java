package org.jeecg.modules.count.bo.callback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 今日头条服务端埋点回调数据
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CallbackDataJrttPaySdk {

    /**
     * 分配的appKey 需要向头条直客询问
     **/
    private String appKey;

    /**
     * ⽤户的唯⼀⾝份标识，需要保证同⼀个⽤户在本app内全局唯⼀ 即与在客户端中user_unique_id上报的⼀致
     **/
    private String userUniqueId;

    /**
     * 安卓参数
     **/
    private String udid;

    /**
     * 包名，务必与真实投放的一直
     **/
    private String appPackage;

    /**
     * 金额
     **/
    private Integer currencyAmount;

}
