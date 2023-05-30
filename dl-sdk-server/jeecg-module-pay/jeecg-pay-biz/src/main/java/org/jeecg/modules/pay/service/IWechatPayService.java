package org.jeecg.modules.pay.service;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.modules.pay.bo.WechatCallbackResponse;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.vo.OpUserVo;

/**
 * @version V1.0
 * @description: 微信支付
 * @author: xmh
 * @date: 2023/1/3 15:06
 */
public interface IWechatPayService {

    /**
     * @param type:        微信支付类型
     * @param orderId:     订单ID
     * @param openDto:     订单参数
     * @param opPayVendor: 支付参数
     * @param opUserVo:    用户信息
     * @return JSONObject
     * @author xmh
     * @description 微信统一下单
     * @date 2023/1/4 9:34
     */
    JSONObject wechatOpen(int type, String orderId, OpenDto openDto,
        OpPayVendor opPayVendor, OpUserVo opUserVo);

    /**
     * @param type:        微信支付类型
     * @param orderId:     订单ID
     * @param openDto:     订单参数
     * @param opPayVendor: 支付参数
     * @param opUserVo:    用户信息
     * @return JSONObject
     * @author lili
     * @description 微信统一下单
     * @date 2023/1/4 9:34
     */
    Map<String, String> wechatV2Open(int type, String orderId, OpenDto openDto,
        OpPayVendor opPayVendor, OpUserVo opUserVo);

    /**
     * @param wechatPaySerial:    请求头 Wechatpay-Serial
     * @param wechatPaySignature: 请求头 Wechatpay-Signature
     * @param wechatPayTimestamp: 请求头 Wechatpay-Timestamp
     * @param wechatPayNonce:     请求头 Wechatpay-Nonce
     * @param callback:           请求体
     * @param payVendorId:        支付渠道ID
     * @param response:           response
     * @return WechatCallbackResponse
     * @author xmh
     * @description 微信支付回调
     * @date 2023/1/6 15:18
     */
    WechatCallbackResponse callback(String wechatPaySerial, String wechatPaySignature,
        String wechatPayTimestamp, String wechatPayNonce, String callback, Integer payVendorId,
        HttpServletResponse response);
}
