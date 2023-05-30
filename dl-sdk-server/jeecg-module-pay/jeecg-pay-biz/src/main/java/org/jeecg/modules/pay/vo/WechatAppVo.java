package org.jeecg.modules.pay.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xmh
 * @version V1.0
 * @description: 微信App支付返回参数
 * @date: 2023/1/12 9:31
 **/
@Data
@NoArgsConstructor
public class WechatAppVo {

    public WechatAppVo(boolean allUsePlatformCurrency) {
        this.allUsePlatformCurrency = allUsePlatformCurrency;
    }

    public WechatAppVo(String prepayId) {
        this.prepayId = prepayId;
    }

    /**
     * 预支付交易会话标识
     */
    private String prepayId;

    /**
     * 是否全使用平台币抵扣
     */
    private boolean allUsePlatformCurrency;
}
