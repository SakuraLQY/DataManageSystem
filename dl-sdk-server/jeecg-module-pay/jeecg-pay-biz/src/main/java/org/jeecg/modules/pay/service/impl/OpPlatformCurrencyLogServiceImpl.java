package org.jeecg.modules.pay.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.util.Date;
import org.jeecg.modules.pay.constant.OpenConstant;
import org.jeecg.modules.pay.entity.OpPlatformCurrencyLog;
import org.jeecg.modules.pay.mapper.OpPlatformCurrencyLogMapper;
import org.jeecg.modules.pay.service.IOpPlatformCurrencyLogService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: op_platform_currency_log
 * @Author: jeecg-boot
 * @Date: 2022-12-21
 * @Version: V1.0
 */
@Service
@DS("open_gateway")
public class OpPlatformCurrencyLogServiceImpl extends
    ServiceImpl<OpPlatformCurrencyLogMapper, OpPlatformCurrencyLog> implements
    IOpPlatformCurrencyLogService {

    @Override
    public boolean updateStatus(String orderId, int status) {
        UpdateWrapper<OpPlatformCurrencyLog> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orderId);
        wrapper.set("status", status);
        if (status == OpenConstant.PC_USE) {
            wrapper.set("use_time", new Date());
        } else if (status == OpenConstant.PC_RETURN) {
            wrapper.set("return_time", new Date());
        }
        return update(wrapper);
    }
}
