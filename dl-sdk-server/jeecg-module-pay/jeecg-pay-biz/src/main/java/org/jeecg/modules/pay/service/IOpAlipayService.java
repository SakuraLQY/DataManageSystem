package org.jeecg.modules.pay.service;

import com.alipay.api.AlipayApiException;
import javax.servlet.http.HttpServletRequest;
import org.jeecg.modules.pay.bo.AlipayConf;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.vo.OpUserVo;

/**
 * @Author lili
 * @Description 支付宝
 * @Date 2022-12-26
 **/
public interface IOpAlipayService {

    /**
     * @param alipayConf
     * @return java.lang.String
     * @Author lili
     * @Description 支付宝下单
     * @Date 16:08 2022/12/24
     **/
    String alipayOpen(Integer type, String orderId, OpenDto openDto, OpPayVendor opPayVendor, OpUserVo opUserVo);

    /**
     * @param request
     * @return java.lang.String
     * @Author lili
     * @Description 回调通知
     * @Date 13:51 2022/12/28
     **/
    String backend(HttpServletRequest request) throws AlipayApiException;
}
