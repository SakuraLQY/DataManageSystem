package org.jeecg.modules.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.exception.ParseException;
import com.wechat.pay.contrib.apache.httpclient.exception.ValidationException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jeecg.common.constant.BankStatus;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.modules.pay.bo.WechatCallbackResponse;
import org.jeecg.modules.pay.bo.WechatOrderRequest;
import org.jeecg.modules.pay.bo.WechatOrderV2Request;
import org.jeecg.modules.pay.bo.wx.Amount;
import org.jeecg.modules.pay.bo.wx.H5Info;
import org.jeecg.modules.pay.bo.wx.Payer;
import org.jeecg.modules.pay.bo.wx.SceneInfo;
import org.jeecg.modules.pay.constant.OpenConstant;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.modules.pay.constant.WechatPayConstant;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.entity.OpOrder;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.service.IOpOrderService;
import org.jeecg.modules.pay.service.IOpPayVendorService;
import org.jeecg.modules.pay.service.IOpPlatformCurrencyLogService;
import org.jeecg.modules.pay.service.IOpVendorService;
import org.jeecg.modules.pay.service.IWechatPayService;
import org.jeecg.modules.pay.util.MoneyUtils;
import org.jeecg.modules.pay.util.UrlUtils;
import org.jeecg.modules.pay.util.WechatUtils;
import org.jeecg.modules.pay.vo.OpVendorVo;
import org.jeecg.modules.vo.OpUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.jeecg.boot.starter.lock.client.RedissonLockClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xmh
 * @version V1.0
 * @description: 微信支付
 * @date: 2023/1/1
 **/
@Service
@Slf4j
@DS("open_gateway")
public class WechatPayServiceImpl implements IWechatPayService {

    @Resource
    private IOpOrderService opOrderService;
    @Resource
    private IOpPayVendorService opPayVendorService;
    @Resource
    private IOpVendorService vendorService;
    @Resource
    private IOpPlatformCurrencyLogService platformCurrencyLogService;
    @Resource
    private IGameApi gameApi;

    // wechat http client
    private CloseableHttpClient wechatHttpClient = null;
    // 商户号
    private String mchId = null;
    // 微信应用ID
    private String appId = null;
    // API V2密钥
    private String apiV2Key = null;
    // API V3密钥
    private String apiV3Key = null;
    // 商户API证书序列号
    private String mchSerialNo = null;
    // 回调地址
    private String notifyUrl = null;
    // 商户私钥字符串
    private String mchPrivateKeyString = null;
    private WXPayConfig config;
    @Autowired
    RedissonLockClient redissonLock;
    // 读取轮询查单配置
    @Value("#{'${list-wechat-seconds}'.split(',')}")
    private List<Integer> list;
    // 订单号
    private String orderId = null;
    // 交易状态
    private String callbackTradeState = null;
    // 订单金额
    private Integer callbackMoney = null;
    // 微信支付订单号
    private String transactionId = null;


    /**
     * @param opPayVendor: 支付参数
     * @author xmh
     * @description 加载支付参数
     * @date 2023/1/6 9:25
     */
    private void loadVendorConf(OpPayVendor opPayVendor, int type) {
        JSONObject vendorConf = JSONObject.parseObject(opPayVendor.getPayVendorConf());
        if (vendorConf.isEmpty()) {
            throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
        }

        appId = vendorConf.getString(WechatPayConstant.VENDOR_KEY_APPID);
        mchId = vendorConf.getString(WechatPayConstant.VENDOR_KEY_MCHID);
        apiV3Key = vendorConf.getString(WechatPayConstant.VENDOR_KEY_APIKEY);
        apiV2Key = vendorConf.getString(WechatPayConstant.VENDOR_KEY_APIKEY);
        mchSerialNo = vendorConf.getString(WechatPayConstant.VENDOR_KEY_SERIAL_NO);
        notifyUrl = vendorConf.getString(WechatPayConstant.VENDOR_KEY_NOTIFY_URL);
        mchPrivateKeyString = vendorConf.getString(WechatPayConstant.VENDOR_KEY_PRIVATE_KEY);
        if (type != WechatPayConstant.H5_V2_ORDER) {
            if (oConvertUtils.isEmpty(appId) || oConvertUtils.isEmpty(mchId)
                || oConvertUtils.isEmpty(
                apiV3Key) || oConvertUtils.isEmpty(mchSerialNo)
                || oConvertUtils.isEmpty(notifyUrl)
                || oConvertUtils.isEmpty(mchPrivateKeyString)) {
                throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
            }
        } else {
            if (oConvertUtils.isEmpty(appId) || oConvertUtils.isEmpty(mchId)
                || oConvertUtils.isEmpty(
                apiV2Key) || oConvertUtils.isEmpty(notifyUrl)
                || oConvertUtils.isEmpty(mchPrivateKeyString)) {
                throw new IdeaRunTimeException(ErrorCode.EMPTY_VENDOR);
            }
        }

    }

    @Override
    @Transactional
    public Map<String, String> wechatV2Open(int type, String orderId, OpenDto openDto,
        OpPayVendor opPayVendor,
        OpUserVo opUserVo) {
        // 加载支付参数
        loadVendorConf(opPayVendor, type);
        // 创建订单对象
        WechatOrderV2Request wechatOrderV2Request = new WechatOrderV2Request();
        // 支付金额等于金额 - 优惠金额
        int money = MoneyUtils.changeY2F(openDto.getMmm().subtract(openDto.getCouponPrice()));
        wechatOrderV2Request.setOutTradeNo(orderId);
        wechatOrderV2Request.setAppId(appId);
        wechatOrderV2Request.setMchId(mchId);
        wechatOrderV2Request.setBody(openDto.getDesc());
        wechatOrderV2Request.setNonceStr(WechatUtils.getRandomStringByLength(32));
        wechatOrderV2Request.setTotalFee(money);
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        wechatOrderV2Request.setSpbillCreateIp(sdkInfo.getIp());
        wechatOrderV2Request.setNotifyUrl(notifyUrl);
        wechatOrderV2Request.setTradeType(WechatPayConstant.TRADE_H5_V2_TYPE);
        try {
            JSONObject jsonObject = JSONObject.parseObject(
                JSONObject.toJSONString(wechatOrderV2Request));
            String preStr = RestUtil.asUrlVariables(WechatUtils.getAloneKeys(jsonObject));
            preStr += "&key=" + apiV2Key;
            String sign = WXPayUtil.MD5(preStr).toUpperCase();
            wechatOrderV2Request.setSign(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String params = WechatUtils.jsonToXml(JSONObject.toJSONString(wechatOrderV2Request));

        switch (type) {
            case WechatPayConstant.H5_V2_ORDER:
                return prepare(WechatPayConstant.REQUEST_POST, WechatPayConstant.H5_V2_API,
                    params);
            default:
                throw new IdeaRunTimeException(ErrorCode.WRONG_WECHAT_ORDER);
        }
    }

    @Override
    @Transactional
    public JSONObject wechatOpen(int type, String orderId, OpenDto openDto,
        OpPayVendor opPayVendor,
        OpUserVo opUserVo) {
        // 加载支付参数
        loadVendorConf(opPayVendor, type);
        // 创建订单对象
        WechatOrderRequest wechatOrderVo = new WechatOrderRequest();
        // 支付金额等于金额 - 优惠金额
        int money = MoneyUtils.changeY2F(openDto.getMmm().subtract(openDto.getCouponPrice()));
        // 构建 http client
        wechatHttpClient = WechatUtils.buildHttpClient(mchId, mchSerialNo, mchPrivateKeyString,
            apiV3Key);
        wechatOrderVo.setOutTradeNo(orderId);
        wechatOrderVo.setAppId(appId);
        wechatOrderVo.setMchId(mchId);
        wechatOrderVo.setDescription(openDto.getDesc());
        // 回调地址加上支付渠道ID
        wechatOrderVo.setNotifyUrl(UrlUtils.addPayVendor(notifyUrl, opPayVendor.getId()));

        wechatOrderVo.setAmount(new Amount(money, WechatPayConstant.CURRENCY_CNY));

        // 针对不同类型处理
        if (type == WechatPayConstant.H5_V3_ORDER) {
            wechatOrderVo.setSceneInfo(
                new SceneInfo(openDto.getClientIp(), H5Info.wapH5Info()));
        }
        if (type == WechatPayConstant.JS_ORDER) {
            // 获取 openId
            if (oConvertUtils.isEmpty(opUserVo.getExtendedField())) {
                throw new IdeaRunTimeException(ErrorCode.PLEASE_USE_ALI_PAY);
            }
            String openId = opUserVo.getExtendedField();
            // TODO appId为微信公众号ID
            wechatOrderVo.setAppId("wx0cb88cc94f6646f3");
            wechatOrderVo.setPayer(new Payer(openId));
        }

        switch (type) {
            case WechatPayConstant.H5_V3_ORDER:
                return sendRequest(WechatPayConstant.REQUEST_POST, WechatPayConstant.H5_V3_API,
                    JSONObject.toJSONString(wechatOrderVo));
            case WechatPayConstant.APP_ORDER:
                return sendRequest(WechatPayConstant.REQUEST_POST, WechatPayConstant.APP_API,
                    JSONObject.toJSONString(wechatOrderVo));
            case WechatPayConstant.JS_ORDER:
                return sendRequest(WechatPayConstant.REQUEST_POST, WechatPayConstant.JS_API,
                    JSONObject.toJSONString(wechatOrderVo));
            case WechatPayConstant.NATIVE_ORDER:
                return sendRequest(WechatPayConstant.REQUEST_POST, WechatPayConstant.NATIVE_API,
                    JSONObject.toJSONString(wechatOrderVo));
            default:
                throw new IdeaRunTimeException(ErrorCode.WRONG_WECHAT_ORDER);
        }
    }

    @Override
    @Transactional
    public WechatCallbackResponse callback(String wechatPaySerial, String wechatPaySignature,
        String wechatPayTimestamp, String wechatPayNonce, String callback, Integer payVendorId,
        HttpServletResponse response) {
        // 加载支付参数
        OpPayVendor opPayVendor = opPayVendorService.getById(payVendorId);
        try {
            loadVendorConf(opPayVendor, 3);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return WechatCallbackResponse.fail("pay vendor conf empty");
        }

        // 构建 request, 传入必要参数
        NotificationRequest request = new NotificationRequest.Builder().withSerialNumber(
                wechatPaySerial)
            .withNonce(wechatPayNonce)
            .withTimestamp(wechatPayTimestamp)
            .withSignature(wechatPaySignature)
            .withBody(callback)
            .build();
        // 加载商户私钥
        PrivateKey mchPrivateKey = WechatUtils.loadMchPrivateKey(mchPrivateKeyString);
        // 获取验签器
        Verifier verifier = WechatUtils.getVerifier(mchId, mchSerialNo, apiV3Key, mchPrivateKey);
        NotificationHandler handler = new NotificationHandler(verifier, apiV3Key.getBytes(
            StandardCharsets.UTF_8));
        // 验签和解析请求体
        Notification notification;
        try {
            notification = handler.parse(request);
        } catch (ValidationException | ParseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return WechatCallbackResponse.fail("parse notification error");
        }

        String eventType = notification.getEventType();
        // 支付不成功
        if (!WechatPayConstant.CALLBACK_SUCCESS.equals(eventType)) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return WechatCallbackResponse.fail("pay error");
        }

        // 解析返回数据
        JSONObject decryptData = JSONObject.parseObject(notification.getDecryptData());
        try {
            parseResponseInfo(decryptData);
        } catch (IdeaRunTimeException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return WechatCallbackResponse.fail("input empty");
        }

        // 加锁
        if (redissonLock.tryLock(WechatPayConstant.REDIS_LOCK_KEY_WECHAT, -1,
            WechatPayConstant.expireSeconds)) {
            try {
                // 获取订单信息
                OpOrder opOrder = opOrderService.getOrderByOrderId(orderId);
                if (oConvertUtils.isEmpty(opOrder)) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return WechatCallbackResponse.fail("order not existed");
                }
                // 判断订单状态
                if (!OpenConstant.CREATE_ORDER_STATUS.equals(opOrder.getStatus()) || (
                    opOrder.getBankStatus() < 0
                        || opOrder.getBankStatus() >= BankStatus.NORMAL)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    return WechatCallbackResponse.success("order has been processed");
                }
                checkAndDeliverOrder(opOrder);

                response.setStatus(HttpServletResponse.SC_OK);
                return WechatCallbackResponse.success();
            } finally {
                // 释放锁
                redissonLock.unlock(WechatPayConstant.REDIS_LOCK_KEY_WECHAT);
            }
        }

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return WechatCallbackResponse.fail("error");
    }

    @Transactional
    @Scheduled(cron = "0/20 * * * * ?")
    public void quartzQueryOrder() {
        // 建立map用来存储渠道配置
        Map<Integer, OpPayVendor> gameVendor = new HashMap<>();
        for (Integer timeSeconds : list) {
            LambdaQueryWrapper<OpOrder> wrapper = new LambdaQueryWrapper<>();
            // 支付方式为微信支付
            wrapper.in(OpOrder::getBankType, PayTypeConstant.WECAHT_PAY);
            // 订单状态为初始状态, 未收到回调
            wrapper.eq(OpOrder::getBankStatus, BankStatus.INIT);
            // 时间间隔
            wrapper.apply("UNIX_TIMESTAMP() - UNIX_TIMESTAMP(open_time) <= " + timeSeconds);
            // 查询订单
            List<OpOrder> orders = opOrderService.list(wrapper);
            if (orders.size() == 0) {
                continue;
            }
            for (OpOrder order : orders) {
                try {
                    Integer subGameId = order.getGameId();
                    OpPayVendor opPayVendor = gameVendor.get(subGameId);
                    // 判断是否有该渠道配置
                    if (oConvertUtils.isEmpty(opPayVendor)) {
                        OpSubGameModel opSubGameModel = gameApi.getOpSubGame(subGameId);
                        OpVendorVo opVendorVo = vendorService.getOpVendorVoById(
                            opSubGameModel.getVendorId());
                        // 读取微信渠道配置并保存
                        opPayVendor = opVendorVo.getWxPayVendorData();
                        gameVendor.put(subGameId, opPayVendor);
                    }

                    loadVendorConf(opPayVendor, 3);
                    // 构建 http client
                    wechatHttpClient = WechatUtils.buildHttpClient(mchId, mchSerialNo,
                        mchPrivateKeyString,
                        apiV3Key);
                    // 发送请求
                    JSONObject data = sendRequest(WechatPayConstant.REQUEST_GET,
                        WechatPayConstant.QUERY_ORDER.concat(order.getOrderId()).concat("?mchid=")
                            .concat(mchId), null);
                    // 解析返回数据
                    parseResponseInfo(data);
                    // 检查和发货
                    checkAndDeliverOrder(order);
                } catch (IdeaRunTimeException e) {
                    log.error("微信支付查询订单异常, 订单号:{}, 异常码:{}", order.getOrderId(), e.getErrorCode());
                }
            }
        }
    }

    /**
     * @param type
     * @param url
     * @param jsonString
     * @return com.alibaba.fastjson.JSONObject
     * @Author lili
     * @Description 微信V2预支付
     * @Date 15:12 2023/4/13
     **/
    private Map<String, String> prepare(Integer type, String url, String jsonString) {
        //调用统一下单接口，并接受返回的结果
        String result = httpRequest(url, WechatPayConstant.POST, jsonString);
        //将返回的xml解析成Map
        Map<String, String> resMap = WechatUtils.doXMLParse(result);
        //判断结果
        if (!"SUCCESS".equals(resMap.get("return_code")) || !"SUCCESS".equals(
            resMap.get("result_code"))) {
            log.error("微信请求返回失败, 返回结果:{}, 请求地址:{}", resMap, url);
            throw new IdeaRunTimeException(ErrorCode.WEIXIN_PREPARE_ERROR);
        }
        if (!appId.equals(resMap.get("appid")) || !mchId.equals(resMap.get("mch_id"))
            || oConvertUtils.isEmpty(resMap.get("prepay_id"))
            || !WechatPayConstant.TRADE_H5_V2_TYPE.equals(resMap.get("trade_type"))) {
            throw new IdeaRunTimeException(ErrorCode.WEIXIN_PREPARE_CONF_ERROR);
        }

        return resMap;
    }

    /**
     * http 请求
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式
     * @param outputStr     参数
     * @return
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        //创建sslContext
        StringBuffer stringBuffer = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            //往服务器端写内容
            if (outputStr != null) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream ins = conn.getInputStream();
            InputStreamReader insr = new InputStreamReader(ins, "utf-8");
            BufferedReader br = new BufferedReader(insr);
            stringBuffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }


    /**
     * @param type:       请求类型
     * @param url:        请求地址
     * @param jsonString: 请求参数
     * @return JSONObject
     * @author xmh
     * @description 发送请求V3
     * @date 2023/1/7 14:02
     */
    private JSONObject sendRequest(Integer type, String url, String jsonString) {
        CloseableHttpResponse response = null;
        try {
            // get 方式
            if (type.equals(WechatPayConstant.REQUEST_GET)) {
                HttpGet httpGet = new HttpGet(url);
                httpGet.addHeader("Accept", "application/json");
                response = wechatHttpClient.execute(httpGet);
            } else {
                // 参数
                StringEntity entity = new StringEntity(jsonString, StandardCharsets.UTF_8);
                entity.setContentType("application/json");

                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Accept", "application/json");
                httpPost.addHeader("Content-type", "application/json; charset=utf-8");

                httpPost.setEntity(entity);
                response = wechatHttpClient.execute(httpPost);
            }
            // 获得当前的响应体
            String bodyAsString = EntityUtils.toString(response.getEntity());
            // 获得响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != WechatPayConstant.SUCCESS_CODE) {
                log.error("微信请求返回失败, 响应码:{}, 返回结果:{}, 请求地址:{}", statusCode, bodyAsString, url);
                throw new IdeaRunTimeException(ErrorCode.WECHAT_ORDER_ERROR);
            }

            return JSONObject.parseObject(bodyAsString);
        } catch (IdeaRunTimeException e) {
            throw new IdeaRunTimeException(e.getErrorCode());
        } catch (Exception e) {
            throw new IdeaRunTimeException(ErrorCode.SEND_WECHAT_FAIL);
        } finally {
            try {
                // 释放资源
                if (wechatHttpClient != null) {
                    wechatHttpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param data: 返回对象
     * @author xmh
     * @description 解析微信返回对象
     * @date 2023/1/7 15:31
     */
    private void parseResponseInfo(JSONObject data) {
        // 回调应用ID
        String callbackAppId = data.getString("appid");
        // 回调商户号
        String callbackMchId = data.getString("mchid");
        // 订单号
        orderId = data.getString("out_trade_no");
        // 交易状态
        callbackTradeState = data.getString("trade_state");
        // 订单金额
        JSONObject amount = data.getJSONObject("amount");
        if (oConvertUtils.isEmpty(amount)) {
            throw new IdeaRunTimeException(ErrorCode.PARSE_RESPONSE_FAIL);
        }
        callbackMoney = amount.getInteger("total");
        // 微信支付订单号
        transactionId = data.getString("transaction_id");

        if (oConvertUtils.isEmpty(callbackAppId) || oConvertUtils.isEmpty(callbackMchId)
            || oConvertUtils.isEmpty(orderId) || oConvertUtils.isEmpty(callbackTradeState)
            || oConvertUtils.isEmpty(callbackMoney) || oConvertUtils.isEmpty(transactionId)) {
            throw new IdeaRunTimeException(ErrorCode.PARSE_RESPONSE_FAIL);
        }
    }

    /**
     * @param opOrder: 订单对象
     * @author xmh
     * @description 对订单检查和发货
     * @date 2023/1/7 15:37
     */
    private void checkAndDeliverOrder(OpOrder opOrder) {
        // 设置订单初始状态
        int bankStatus = BankStatus.NORMAL;
        BigDecimal couponPrice = opOrder.getCouponPrice();
        BigDecimal orderMoney = opOrder.getMoney().subtract(couponPrice);
        // 金额不相等
        if (!MoneyUtils.changeY2F(orderMoney).equals(callbackMoney)) {
            bankStatus = BankStatus.MONEY_NE;
        } else {
            // 交易状态未成功
            if (!WechatPayConstant.TRADE_STATE_SUCCESS.equals(callbackTradeState)) {
                bankStatus = BankStatus.TRADE_FAIL;
            }
        }
        // 更新订单状态
        opOrderService.updateOrderByPay(orderId, transactionId, bankStatus,
            MoneyUtils.changeF2Y(callbackMoney));
        // 判断是否需要更新平台币记录
        if (bankStatus >= BankStatus.NORMAL && couponPrice.compareTo(BigDecimal.ZERO) > 0) {
            platformCurrencyLogService.updateStatus(orderId, OpenConstant.PC_USE);
        }

        // 发货
        if (opOrder.getBankStatus() < BankStatus.NORMAL
            && bankStatus >= BankStatus.NORMAL) {
            // 异步发货
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    opOrderService.deliverOnce(orderId);
                } catch (Exception e) {
                    log.error("微信支付发货异常{}", e.getMessage());
                }
            });
        }
    }
}
