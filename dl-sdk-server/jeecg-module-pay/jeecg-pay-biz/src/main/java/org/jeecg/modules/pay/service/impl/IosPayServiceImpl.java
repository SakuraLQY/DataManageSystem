package org.jeecg.modules.pay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.BankStatus;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpCommodityModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.Md5Util;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.config.SdkConfig;
import org.jeecg.modules.pay.constant.IosVerifyReceiptStatus;
import org.jeecg.modules.pay.constant.OpenConstant;
import org.jeecg.modules.pay.constant.OrderIsTest;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.modules.pay.dto.IosPayCheckDto;
import org.jeecg.modules.pay.dto.IosPayMenuDto;
import org.jeecg.modules.pay.entity.OpOrder;
import org.jeecg.modules.pay.service.IOpOrderService;
import org.jeecg.modules.pay.service.IIosPayService;
import org.jeecg.modules.pay.vo.IosPayMenuPayVo;
import org.jeecg.modules.pay.vo.IosPayMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author chenyw
 * @version V1.0
 * @description:
 * @date: 2023/1/5
 **/
@Service
@Slf4j
public class IosPayServiceImpl implements IIosPayService {

    @Autowired
    private SdkConfig sdkConfig;
    @Autowired
    private IGameApi gameApi;
    @Autowired
    private IOpOrderService opOrderService;

    // 苹果正式环境校验订单
    public static final String BUY_VERIFY_RECEIPT = "https://buy.itunes.apple.com/verifyReceipt";
    // 苹果沙箱环境校验订单
    public static final String SANDBOX_VERIFY_RECEIPT = "https://sandbox.itunes.apple.com/verifyReceipt";

    @Override
    public IosPayMenuVo iosPayMenu(IosPayMenuDto iosPayMenuDto, String serverName, int port) {
        // TODO 请求参数需要另外的jump_bundle 用于支付成功后 return_url跳转
        OpSubGameModel opSubGameModel = gameApi.getOpSubGame(iosPayMenuDto.getSubGameId());
        if (opSubGameModel == null) {
            throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SUB_GAME);
        }
        IosPayMenuVo iosPayMenuVo = new IosPayMenuVo();
        String path = SpringContextUtils.getApplicationContext().getEnvironment()
            .getProperty("server.servlet.context-path");
        String jumpUrl =
            "http://" + serverName + ":" + port + "/" + path + sdkConfig.getIosJumpUrl();
        List<IosPayMenuPayVo> ztList = new ArrayList<>();
        iosPayMenuVo.setZtList(ztList);
        if (iosPayMenuDto.getZtTypeList().contains(PayTypeConstant.PAY_TYPE_H5_ALI)) {
            IosPayMenuPayVo iosPayMenuPayVo = new IosPayMenuPayVo();
            iosPayMenuPayVo.setIcon(sdkConfig.getAliIcon());
            iosPayMenuPayVo.setName("支付宝");
            iosPayMenuPayVo.setType(PayTypeConstant.PAY_TYPE_H5_ALI);
            String targetUrl =
                "http://" + serverName + ":" + port + "/" + path + sdkConfig.getAliH5OpenUrl();
            String openUrl = createOpenUrl(iosPayMenuDto, jumpUrl, targetUrl,
                opSubGameModel.getGameKey());
            iosPayMenuPayVo.setUrl(openUrl);
            ztList.add(iosPayMenuPayVo);
        }
        if (iosPayMenuDto.getZtTypeList().contains(PayTypeConstant.PAY_TYPE_WEIXIN_WEB)) {
            IosPayMenuPayVo iosPayMenuPayVo = new IosPayMenuPayVo();
            iosPayMenuPayVo.setIcon(sdkConfig.getWxIcon());
            iosPayMenuPayVo.setName("微信");
            iosPayMenuPayVo.setType(PayTypeConstant.PAY_TYPE_WEIXIN_WEB);
            String targetUrl =
                "http://" + serverName + ":" + port + "/" + path + sdkConfig.getWxH5OpenUrl();
            String openUrl = createOpenUrl(iosPayMenuDto, jumpUrl, targetUrl,
                opSubGameModel.getGameKey());
            iosPayMenuPayVo.setUrl(openUrl);
            ztList.add(iosPayMenuPayVo);
        }
        iosPayMenuVo.setMny("¥" + iosPayMenuDto.getMmm());
        iosPayMenuVo.setDesc(iosPayMenuDto.getDesc());
        iosPayMenuVo.setServerId(iosPayMenuDto.getServerId());
        iosPayMenuVo.setRoleName(iosPayMenuDto.getRoleName());
        return iosPayMenuVo;
    }

    @Override
    public String jump(Map<String, String[]> map) {
        String url = map.get("url")[0];
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
        map.forEach((key, value) -> {
            if (!Objects.equals(key, "url") && value != null) {
                uriComponentsBuilder.queryParam(key, value[0]);
            }
        });
        url = uriComponentsBuilder.build().toString();
        String result = "<head>"
            + "<meta http-equiv=\"refresh\" content=\"0; url=" + url + "\" />"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
            + "</head>";
        return result;
    }

    @Override
    public void verifyReceipt(IosPayCheckDto iosPayCheckDto) {
        QueryWrapper<OpOrder> queryWrapper = new QueryWrapper();
        OpOrder opOrder = opOrderService.getOne(
            queryWrapper.lambda().eq(OpOrder::getOrderId, iosPayCheckDto.getOrderId()));
        if (null == opOrder) {
            throw new IdeaRunTimeException(ErrorCode.ORDER_NOT_EXIST);
        }
        if (!OpenConstant.CREATE_ORDER_STATUS.equals(opOrder.getStatus())
            || !BankStatus.INIT.equals(opOrder.getBankStatus())) {
            throw new IdeaRunTimeException(ErrorCode.BANK_STATUS_INVAID);
        }
        OpOrder update = new OpOrder();
        update.setId(opOrder.getId());
        update.setIsTest(OrderIsTest.NO);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("receipt-data", iosPayCheckDto.getReceipt());
        JSONObject response = RestUtil.post(BUY_VERIFY_RECEIPT, null, jsonObject);
        log.info("订单:{} ios校验返回结果:{}", iosPayCheckDto.getOrderId(), response);
        if (Objects.equals(response.get("status"), IosVerifyReceiptStatus.SAND_BOX)) {
            update.setIsTest(OrderIsTest.YES);
            response = RestUtil.post(SANDBOX_VERIFY_RECEIPT, null, jsonObject);
            log.info("沙箱环境订单:{} ios校验返回结果:{}", iosPayCheckDto.getOrderId(), response);
        }
        if (Objects.equals(response.get("status"), IosVerifyReceiptStatus.SUCCESS)) {
            //  成功
        } else if (Objects.equals(response.get("status"), IosVerifyReceiptStatus.BUSY)) {
            throw new IdeaRunTimeException(ErrorCode.IOS_VERIFY_RECEIPT_SERVER_BUSY);
        } else {
            throw new IdeaRunTimeException(ErrorCode.IOS_VERIFY_RECEIPT_ERROR);
        }
        JSONObject receipt = response.getJSONObject("receipt");
        if (receipt == null) {
            throw new IdeaRunTimeException(ErrorCode.IOS_VERIFY_RECEIPT_EMPTY);
        }
        // 订单号
        JSONArray inApp = receipt.getJSONArray("in_app");
        if (inApp == null) {
            throw new IdeaRunTimeException(ErrorCode.IOS_VERIFY_RECEIPT_INAPP_EMPTY);
        }
        for (int i = 0; i < inApp.size(); i++) {
            JSONObject app = inApp.getJSONObject(i);
            String transactionId = app.getString("transaction_id");
            if (Objects.equals(iosPayCheckDto.getTransactionId(), transactionId)) {
                String productId = app.getString("product_id");
                BigDecimal quantity = app.getBigDecimal("quantity");
                // 获取商品
                OpCommodityModel opCommodityModel = gameApi.getOpCommdityByGoodId(iosPayCheckDto.getSubGameId(), iosPayCheckDto.getPkgId(), productId);
                // 查看订单金额是否一致
                BigDecimal bankMoney = opCommodityModel.getMoney().multiply(quantity)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal orderMoney = opOrder.getMoney().setScale(2, BigDecimal.ROUND_HALF_UP);
                if (bankMoney.compareTo(orderMoney) != 0) {
                    log.info("ios订单金额不一致bankMoney:{}, orderMoney:{}", bankMoney, orderMoney);
                    update.setBankStatus(BankStatus.MONEY_NE);
                } else {
                    update.setBankStatus(BankStatus.NORMAL);
                }
                update.setBankOrderId(transactionId);
                update.setBankStatusTime(LocalDateTime.now());
                update.setBankMoney(bankMoney);
                update.setBankMoneyType(iosPayCheckDto.getMmmType());
                update.setIosPkgVer(iosPayCheckDto.getPkgVer());
                opOrderService.updateById(update);
                if (BankStatus.NORMAL.equals(update.getBankStatus())) {
                    log.info("订单:{}状态正常,执行发货", iosPayCheckDto.getOrderId());
                    opOrderService.deliverOnce(iosPayCheckDto.getOrderId());
                }
                break;
            }
        }
    }

    /**
     * @param iosPayMenuDto
     * @param jumpUrl
     * @param targetUrl
     * @param gameKey
     * @return java.lang.String
     * @author chenyw
     * @description 创建跳转open的链接
     * @date 10:41 2023/1/7/007
     **/
    private String createOpenUrl(IosPayMenuDto iosPayMenuDto, String jumpUrl, String targetUrl,
        String gameKey) {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("mmm", iosPayMenuDto.getMmm());
        params.put("desc", iosPayMenuDto.getDesc());
        params.put("userId", iosPayMenuDto.getUserId());
        params.put("subGameId", iosPayMenuDto.getSubGameId());
        params.put("device", iosPayMenuDto.getDevice());
        params.put("orderType", iosPayMenuDto.getOrderType());
        params.put("subGameOrderId", iosPayMenuDto.getSubGameOrderId());
        params.put("userPassword", iosPayMenuDto.getUserPassword());
        params.put("usePlatformCurrency", iosPayMenuDto.getUsePlatformCurrency());
        params.put("couponPrice", iosPayMenuDto.getCouponPrice());
        params.put("serverId", iosPayMenuDto.getServerId());
        params.put("roleId", iosPayMenuDto.getRoleId());
        params.put("roleLevel", iosPayMenuDto.getRoleLevel());
        params.put("subGameData", iosPayMenuDto.getSubGameData());
        params.put("pkgId", iosPayMenuDto.getPkgId());
        String str = "";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(jumpUrl);
        Iterator<Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            if (null != entry.getValue()) {
                String value = "";
                if(entry.getValue()!=null){
                    value = entry.getValue().toString();
                }
                try {
                    // 对所有的参数进行urlencode操作
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    log.error("urlEncode错误:{}", value);
                }
                uriComponentsBuilder.queryParam(entry.getKey(), value);
                str += entry.getValue().toString();
            }
        }
        // 设置签名
        String sign = Md5Util.md5Encode(str + gameKey, "utf-8");
        uriComponentsBuilder.queryParam("sign", sign);
        if (StringUtils.isNotEmpty(iosPayMenuDto.getClientIp())) {
            uriComponentsBuilder.queryParam("clientIp", iosPayMenuDto.getClientIp());
        }
        uriComponentsBuilder.queryParam("url", targetUrl);
        return uriComponentsBuilder.build().toString();
    }

}
