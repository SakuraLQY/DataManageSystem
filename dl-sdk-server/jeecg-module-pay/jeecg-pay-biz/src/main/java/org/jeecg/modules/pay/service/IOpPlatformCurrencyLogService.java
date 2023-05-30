package org.jeecg.modules.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.pay.entity.OpPlatformCurrencyLog;

/**
 * @Description: op_platform_currency_log
 * @Author: jeecg-boot
 * @Date: 2022-12-21
 * @Version: V1.0
 */
public interface IOpPlatformCurrencyLogService extends IService<OpPlatformCurrencyLog> {

    /**
     * @param orderId: 订单ID
     * @param status:  状态
     * @return boolean
     * @author xmh
     * @description 更新平台币状态
     * @date 2022/12/24 16:15
     */
    boolean updateStatus(String orderId, int status);
}
