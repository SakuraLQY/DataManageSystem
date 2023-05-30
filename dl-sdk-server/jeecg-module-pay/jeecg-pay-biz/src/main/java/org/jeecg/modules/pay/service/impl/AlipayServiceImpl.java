package org.jeecg.modules.pay.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.base.CaseFormat;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.constant.BankStatus;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.handler.IFillRuleHandler;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.pay.bo.AlipayBizContentConf;
import org.jeecg.modules.pay.bo.AlipayConf;
import org.jeecg.modules.pay.constant.AlipayConstant;
import org.jeecg.modules.pay.constant.OpenConstant;
import org.jeecg.modules.pay.constant.WechatPayConstant;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.entity.OpOrder;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.mapper.OpOrderMapper;
import org.jeecg.modules.pay.service.IOpAlipayService;
import org.jeecg.modules.pay.service.IOpOrderService;
import org.jeecg.modules.pay.service.IOpPlatformCurrencyLogService;
import org.jeecg.modules.pay.util.MoneyUtils;
import org.jeecg.modules.pay.util.RequestToMap;
import org.jeecg.modules.vo.OpUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

/**
 * @Author lili
 * @Description
 * @Date 2022-12-26
 **/
@Service
@Slf4j
@DS("open_gateway")
public class AlipayServiceImpl implements IOpAlipayService {

    @Resource
    private IOpOrderService opOrderService;
    @Resource
    private IOpPlatformCurrencyLogService platformCurrencyLogService;

    // 应用ID
    private String appId = null;
    // 合作者
    private String sellerId = null;
    // 应用邮箱
    private String sellerEmail = null;
    // API 密钥
    private String sellerPrikey = null;
    // API 支付宝公钥
    private String aliPublic = null;
    // 回调地址
    private String notifyUrl = null;

    private void loadVendorConf(OpPayVendor opPayVendor) {
        JSONObject vendorConf = JSONObject.parseObject(opPayVendor.getPayVendorConf());
        if (vendorConf.isEmpty()) {
            throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
        }

        appId = vendorConf.getString(AlipayConstant.VENDOR_KEY_APPID);
        sellerId = vendorConf.getString(AlipayConstant.VENDOR_KEY_SELLER_ID);
        sellerEmail = vendorConf.getString(AlipayConstant.VENDOR_KEY_SELLER_EMAIL);
        sellerPrikey = vendorConf.getString(AlipayConstant.VENDOR_KEY_SELLER_PRIKEY);
        aliPublic = vendorConf.getString(AlipayConstant.VENDOR_KEY_SELLER_PUBLIC);
        notifyUrl = vendorConf.getString(AlipayConstant.VENDOR_KEY_NOTIFY_URL);

        if (oConvertUtils.isEmpty(appId) || oConvertUtils.isEmpty(sellerPrikey)
            || oConvertUtils.isEmpty(
            aliPublic) || oConvertUtils.isEmpty(notifyUrl)) {
            throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
        }
    }

    @Override
    @Transactional
    public String alipayOpen(Integer type, String orderId, OpenDto openDto, OpPayVendor opPayVendor,
        OpUserVo opUserVo) {
        // 加载支付参数
        loadVendorConf(opPayVendor);
        AlipayConf alipayConf = new AlipayConf();
        alipayConf.setAppId(appId);
        alipayConf.setSellerId(sellerId);
        alipayConf.setMethod("alipay.trade.wap.pay");
        alipayConf.setCharset(AlipayConstant.CHARSET);
        alipayConf.setSignType(AlipayConstant.SIGN_TYPE);
        alipayConf.setTimestamp(DateUtils.formatDateTime());
        alipayConf.setVersion(AlipayConstant.VERSION);
        alipayConf.setNotifyUrl(notifyUrl);
        AlipayBizContentConf bizContent = new AlipayBizContentConf();
        bizContent.setOutTradeNo(orderId);
        bizContent.setSubject(openDto.getDesc());
        bizContent.setTotalAmount(String.format("%.2f",
            openDto.getMmm().subtract(openDto.getCouponPrice().divide(new BigDecimal(100)))));
        bizContent.setProductCode("QUICK_WAP_WAY");
        bizContent.setTimeoutExpress(AlipayConstant.timeoutExpress);
        bizContent.setTimeExpire(DateUtils.formatTime(new Date().getTime() + 1000 * 60 * 2));
        alipayConf.setBizContent(bizContent);

        switch (type) {
            case AlipayConstant.H5_ORDER:
                return sendRequest(type, alipayConf);
            default:
                throw new IdeaRunTimeException(ErrorCode.WRONG_ALIPAY_ORDER);
        }
    }

    private String sendRequest(Integer type, AlipayConf alipayConf) {
        String form = "";
        if (type == AlipayConstant.H5_ORDER) {
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstant.H5_API,
                appId, sellerPrikey, AlipayConstant.FORMAT,
                AlipayConstant.CHARSET, aliPublic, AlipayConstant.SIGN_TYPE);
            AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
            AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
            model.setOutTradeNo(alipayConf.getBizContent().getOutTradeNo());
            model.setTotalAmount(alipayConf.getBizContent().getTotalAmount());
            model.setProductCode("QUICK_WAP_WAY");
            model.setSellerId(alipayConf.getSellerId());
            model.setSubject(alipayConf.getBizContent().getSubject());
//            model.setQuitUrl("");
            model.setTimeoutExpress(AlipayConstant.timeoutExpress);
//            model.setStoreId(alipayConf.getGameId());
            model.setTimeExpire(DateUtils.formatTime(new Date().getTime() + 1000 * 60 * 2));
            request.setBizModel(model);
            request.setNotifyUrl(notifyUrl);
            request.setReturnUrl(AlipayConstant.RETURN_URL);
            try {
                form = alipayClient.pageExecute(request, "GET").getBody(); //调用SDK生成表单

            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }

        return form;
    }

    @Override
    @Transactional
    public String backend(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = RequestToMap.convertRequestParamsToMap(
            request); // 将异步通知中收到的待验证所有参数都存放到map中

        if (params.get("notify_id").isEmpty() || params.get("app_id").isEmpty() || params.get(
            "out_trade_no").isEmpty() || params.get(
            "seller_id").isEmpty() || params.get("trade_no").isEmpty() || params.get("trade_status")
            .isEmpty() || params.get("total_amount")
            .isEmpty()
            || params.get("sign").isEmpty() || params.get("sign_type").isEmpty()) {
            log.error("回调通知参数有空值");
            throw new IdeaRunTimeException(ErrorCode.CONF_EXIST_EMPTY);
        }
        boolean verify = false;
        if (params.get("sign_type").equals(AlipayConstant.SIGN_TYPE)) {
            verify = AlipaySignature.rsaCheckV1(params, aliPublic, AlipayConstant.CHARSET,
                AlipayConstant.SIGN_TYPE);
        }
        if (!verify) {
            log.error("验签失败");
            return "failure";
        }
        //通过out_trade_no取得订单记录
        OpOrder opOrder = opOrderService.getOrderByOrderId(params.get("out_trade_no"));
        //判断订单是否为空
        if (oConvertUtils.isEmpty(opOrder)) {
            log.error("订单不存在");
            throw new IdeaRunTimeException(ErrorCode.ORDER_IS_EMPTY);
        }
        //订单不为空，判断订单状态和银行状态
        if (1 != opOrder.getStatus() || (opOrder.getBankStatus() < BankStatus.INIT
            || opOrder.getBankStatus() >= BankStatus.NORMAL)) {
            throw new IdeaRunTimeException(ErrorCode.ORDER_STATUS_ERROR);
        }
        //验证 app_id 是否为该商家本身
        if (!appId.equals(params.get("app_id"))) {
            throw new IdeaRunTimeException(ErrorCode.EXCEPTION_NOTIFICATION);
        }
        Integer bankStatus = 1000;
        if (!params.get("seller_id").equals(sellerId)) {
            bankStatus = -1;
            throw new IdeaRunTimeException(ErrorCode.EXCEPTION_NOTIFICATION);
        } else {
            if (new BigDecimal(params.get("total_amount")).compareTo(opOrder.getMoney()) == 0) {
                if ("WAIT_BUYER_PAY".equals(params.get("trade_status"))) {
                    bankStatus = 1;
                    throw new IdeaRunTimeException(ErrorCode.EXCEPTION_NOTIFICATION);
                } else {
                    if ("TRADE_SUCCESS".equals(params.get("trade_status"))
                        && "TRADE_FINISHED".equals(params.get("trade_status"))) {
                        bankStatus = -3;
                        throw new IdeaRunTimeException(ErrorCode.EXCEPTION_NOTIFICATION);
                    }
                }
            } else {
                bankStatus = -2;
                throw new IdeaRunTimeException(ErrorCode.EXCEPTION_NOTIFICATION);
            }
        }

        //更改订单状态
        if (!opOrderService.updateOrderByPay(opOrder.getOrderId(), params.get("trade_no"),
            bankStatus,
            opOrder.getMoney())) {
            throw new IdeaRunTimeException(ErrorCode.UPDATE_ORDER_ERROR);
        }
        BigDecimal couponPrice = opOrder.getCouponPrice();
        // 判断是否需要更新平台币记录
        if (bankStatus >= BankStatus.NORMAL && couponPrice.compareTo(BigDecimal.ZERO) > 0) {
            platformCurrencyLogService.updateStatus(opOrder.getOrderId(), OpenConstant.PC_USE);
        }
        // 发货
        if (opOrder.getBankStatus() < BankStatus.NORMAL
            && bankStatus >= BankStatus.NORMAL) {
            // 异步发货
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    opOrderService.deliverOnce(opOrder.getOrderId());
                } catch (Exception e) {
                    log.error("支付宝支付发货异常{}", e.getMessage());
                }
            });
        }

        return "success";
    }
}
