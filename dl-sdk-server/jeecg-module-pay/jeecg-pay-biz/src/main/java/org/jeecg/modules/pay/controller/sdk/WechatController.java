package org.jeecg.modules.pay.controller.sdk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.pay.bo.WechatCallbackResponse;
import org.jeecg.modules.pay.service.IWechatPayService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/1/4 17:37
 **/
@Api(tags="wechat")
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    @Resource
    private IWechatPayService wechatPayService;

    @ApiOperation(value = "微信支付回调", notes ="微信支付回调")
    @PostMapping(value = "/callback/{payVendorId}")
    public WechatCallbackResponse wechatCallback(@RequestHeader("Wechatpay-Serial") String wechatPaySerial,
        @RequestHeader("Wechatpay-Signature") String wechatPaySignature,
        @RequestHeader("Wechatpay-Timestamp") String wechatPayTimestamp,
        @RequestHeader("Wechatpay-Nonce") String wechatPayNonce,
        @RequestBody String callback,
        @PathVariable Integer payVendorId,
        HttpServletResponse response){

        return wechatPayService.callback(wechatPaySerial, wechatPaySignature, wechatPayTimestamp, wechatPayNonce, callback, payVendorId, response);
    }
}
