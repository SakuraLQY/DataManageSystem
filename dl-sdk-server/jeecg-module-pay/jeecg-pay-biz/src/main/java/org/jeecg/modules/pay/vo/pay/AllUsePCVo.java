package org.jeecg.modules.pay.vo.pay;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2022/12/24
 **/
@Data
@NoArgsConstructor
public class AllUsePCVo {

    // 订单编号
    private String orderId;
    // 支付url
    private final String payUrl = "";
    // 是否全使用平台币抵扣
    private final boolean allUsePlatformCurrency = true;

    public AllUsePCVo(String orderId) {
        this.orderId = orderId;
    }
}
