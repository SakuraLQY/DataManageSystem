package org.jeecg.modules.pay.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.jeecg.modules.pay.api.IPayBaseAPI;
import org.jeecg.modules.pay.entity.OpVendor;
import org.jeecg.modules.pay.service.IOpOrderService;
import org.jeecg.modules.pay.service.IOpVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2022/12/19
 **/
@Service
public class PayBaseAPIImpl implements IPayBaseAPI {

    @Resource
    private IOpVendorService opVendorService;

    @Autowired
    private IOpOrderService iOpOrderService;

    @Override
    public List<OpVendor> getAllVendor() {
        return opVendorService.list();
    }

    @Override
    public Boolean updateOrderStatus(String orderId, Integer bankStatus){
        Boolean result = iOpOrderService.updateOrderStatus(orderId, bankStatus);

        return result;
    }

    @Override
    public void deliverOnce(String orderId){
        iOpOrderService.deliverOnce(orderId);
    }
}
