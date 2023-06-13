package org.jeecg.modules.count.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.advert.dto.JrttVisitDto;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.SubGameTypeConstant;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.bo.PkgChannelConfJrtt;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.common.util.CountUtil;
import org.jeecg.common.util.RestUtil;
import org.jeecg.modules.count.bo.callback.CallbackDataJrtt;
import org.jeecg.modules.count.bo.callback.CallbackDataJrttPaySdk;
import org.jeecg.modules.count.bo.callback.CallbackJrttPaySdkRequest;
import org.jeecg.modules.count.bo.callback.JrttPaySdkEvents;
import org.jeecg.modules.count.bo.callback.JrttPaySdkHeader;
import org.jeecg.modules.count.bo.callback.JrttPaySdkUser;
import org.jeecg.modules.count.constant.CallBackChannelConstant;
import org.jeecg.modules.count.constant.CallBackEventType;
import org.jeecg.modules.count.constant.CallBackOperationType;
import org.jeecg.modules.count.constant.CallBackState;
import org.jeecg.modules.count.constant.CallBackUrlConstant;
import org.jeecg.modules.count.dto.CallbackOperationDto;
import org.jeecg.modules.count.dto.CtCallbackDto;
import org.jeecg.modules.count.entity.CtCallback;
import org.jeecg.modules.count.mapper.CtCallbackMapper;
import org.jeecg.modules.count.service.ICtCallbackService;
import org.jeecg.modules.count.vo.CtCallbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: ct_callback
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Slf4j
@Service
@DS("open_countly")
public class CtCallbackServiceImpl extends ServiceImpl<CtCallbackMapper, CtCallback> implements
    ICtCallbackService {

    @Autowired
    private IGameApi iGameApi;
    @Autowired
    private IAdvertApi iAdvertApi;

    @Override
    public void parsePayCallback(ParsePayDto parsePayDto) {
        OpSubGameModel opSubGameModel = iGameApi.getOpSubGame(parsePayDto.getSubGameId());
        if (SubGameTypeConstant.IOS.equals(opSubGameModel.getGameType())) {
            String visitData = iAdvertApi.getUnique(parsePayDto.getDealId(),
                parsePayDto.getUniqueId(),
                null, null, null, parsePayDto.getClientIp());
            if (StringUtils.isEmpty(visitData)) {
                log.error("向头条推送支付信息时查询visitData为空,订单号:{}", parsePayDto.getOrderId());
                return;
            }
            // 渠道判断
            if (ChannelConstant.JRTT.equals(parsePayDto.getChannelId())) {
                // 星图和头条
                pushToJrtt(CallBackEventType.JRTT_PAY, visitData, parsePayDto.getMoney());
            }
        } else {
            // 渠道判断
            if (ChannelConstant.JRTT.equals(parsePayDto.getChannelId())
                || ChannelConstant.XING_TU.equals(parsePayDto.getChannelId())) {
                OpPkgModel opPkgModel = iGameApi.getOpPkgById(parsePayDto.getPkgId());
                PkgChannelConfJrtt pkgChannelConfJrtt = opPkgModel.getJrttConf();
                String userUniqueId = parsePayDto.getDealId() + "" + parsePayDto.getUserId();
                pushToJrttPaySdk(userUniqueId, pkgChannelConfJrtt, parsePayDto.getUniqueId(),
                    parsePayDto.getMoney());
            }
        }
    }

    @Override
    public void parseStartCallback(ParseStartDto parseStartDto) {
        OpSubGameModel opSubGameModel = iGameApi.getOpSubGame(parseStartDto.getSubGameId());
        if (SubGameTypeConstant.IOS.equals(opSubGameModel.getGameType())) {
            String visitData = iAdvertApi.getUnique(parseStartDto.getDealId(),
                parseStartDto.getUniqueId(), parseStartDto.getDeviceId(),
                parseStartDto.getSerialId(), parseStartDto.getAndroidId(),
                parseStartDto.getClientIp());
            // 渠道判断
            if (ChannelConstant.JRTT.equals(parseStartDto.getChannelId())
                || ChannelConstant.XING_TU.equals(parseStartDto.getChannelId())) {
                // 星图和头条
                pushToJrtt(CallBackEventType.JRTT_START, visitData, null);
            }
        }
    }

    @Override
    public void parseRegisterCallback(ParseLoginDto parseLoginDto) {
        OpSubGameModel opSubGameModel = iGameApi.getOpSubGame(parseLoginDto.getSubGameId());
        if (SubGameTypeConstant.IOS.equals(opSubGameModel.getGameType())) {
            String visitData = iAdvertApi.getUnique(parseLoginDto.getDealId(),
                parseLoginDto.getUniqueId(), null, null, null, null);
            // 渠道判断
            if (ChannelConstant.JRTT.equals(parseLoginDto.getChannelId())
                || ChannelConstant.XING_TU.equals(parseLoginDto.getChannelId())) {
                pushToJrtt(CallBackEventType.JRTT_REGISTER, visitData, null);
            }
        }
    }

    @Override
    public IPage<CtCallbackVo> getDeviceCallbackData(Page page, CtCallbackDto ctCallbackDto) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(ctCallbackDto.getGameId()), "a.game_id",
            ctCallbackDto.getGameId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(ctCallbackDto.getSubGameId()), "a.sub_game_id",
            ctCallbackDto.getSubGameId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(ctCallbackDto.getPkgId()), "a.pkg_id",
            ctCallbackDto.getPkgId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(ctCallbackDto.getChannelId()), "a.channel_id",
            ctCallbackDto.getChannelId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(ctCallbackDto.getChannelSubAccountId()),
            "a.channel_sub_account_id", ctCallbackDto.getChannelSubAccountId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(ctCallbackDto.getCallbackState()),
            "a.callback_state", ctCallbackDto.getCallbackState());
        queryWrapper.eq(ObjectUtils.isNotEmpty(ctCallbackDto.getDealId()), "a.deal_id",
            ctCallbackDto.getDealId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(ctCallbackDto.getChannelTypeId()),
            "c.type_id", ctCallbackDto.getChannelTypeId());
        queryWrapper.ge(ObjectUtils.isNotEmpty(ctCallbackDto.getStartTime()), "a.create_time",
            ctCallbackDto.getStartTime());
        queryWrapper.le(ObjectUtils.isNotEmpty(ctCallbackDto.getEndTime()), "a.create_time",
            ctCallbackDto.getEndTime());
        List<CtCallbackVo> ctCallbackVoList = baseMapper.getCtCallbackPage(page, queryWrapper);
        return page.setRecords(ctCallbackVoList);
    }

    @Override
    public void callbackOperation(CallbackOperationDto callbackOperationDto) {
        CtCallback ctCallback = getById(callbackOperationDto.getId());
        if (ctCallback == null) {
            throw new JeecgBootException("未找到回调数据");
        }

        if (ChannelConstant.JRTT.equals(ctCallback.getChannelId())
            || ChannelConstant.XING_TU.equals(ctCallback.getChannelId())) {
            String callbackData = ctCallback.getCallbackData();
            CallbackDataJrtt callBackDataJrtt = JSONObject.parseObject(callbackData,
                CallbackDataJrtt.class);
            String[] arr = callBackDataJrtt.getUrl().split("&event_type");
            if(arr.length == 0){
                throw new JeecgBootException("回调url为空");
            }
            String url = arr[0];
            if (CallBackOperationType.START.equals(callbackOperationDto.getOperationType())) {
                CtCallback push = getCtCallback(CallBackEventType.JRTT_START, null, url);
                handleJrtt(push);
            } else if (CallBackOperationType.REGISTER.equals(callbackOperationDto.getOperationType())) {
                CtCallback push = getCtCallback(CallBackEventType.JRTT_REGISTER, null, url);
                handleJrtt(push);
            } else if (CallBackOperationType.PAY.equals(callbackOperationDto.getOperationType())) {
                // 金额默认为6
                CtCallback push = getCtCallback(CallBackEventType.JRTT_SDK_PAY, BigDecimal.valueOf(6), url);
                handleJrtt(push);
            }
        }
    }

    /**
     * @param eventType
     * @param visitData
     * @param money
     * @author chenyw
     * @description 头条API上报
     * @date 20:34 2023/4/24/024
     **/
    private void pushToJrtt(Integer eventType, String visitData, BigDecimal money) {
        JrttVisitDto jrttVisitDto = JSONObject.parseObject(visitData, JrttVisitDto.class);
        String callbackUrl = jrttVisitDto.getCallbackUrl();
        CtCallback insert = getCtCallback(eventType, money, callbackUrl);
        save(insert);
    }

    /**
     * @param eventType
     * @param money
     * @param callbackUrl
     * @return org.jeecg.modules.count.entity.CtCallback
     * @author chenyw
     * @description 获取callback对象
     * @date 19:52 2023/5/15/015
     **/
    private CtCallback getCtCallback(Integer eventType, BigDecimal money, String callbackUrl) {
        String url = callbackUrl + "&event_type=" + eventType;
        if (CallBackEventType.JRTT_PAY.equals(eventType)) {
            // 支付加上金额
            money = money.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP);
            String props = "{\"pay_amount:\":" + money + "}";
            try {
                props = URLEncoder.encode(props, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("URLEncoder失败:", e);
            }
            url = url + "&props=" + props;
        }
        CallbackDataJrtt callBackDataJrtt = new CallbackDataJrtt(url);
        CtCallback insert = new CtCallback();
        insert.setCallbackData(JSONObject.toJSONString(callBackDataJrtt));
        insert.setCallbackChannel(CallBackChannelConstant.JRTT);
        insert.setEventType(eventType);
        insert.setCallbackNum(0);
        insert.setCallbackState(CallBackState.WAIT);
        return insert;
    }

    /**
     * @param userUniqueId
     * @param pkgChannelConfJrtt
     * @param udid
     * @param money
     * @author chenyw
     * @description 头条SDK上报
     * @date 20:34 2023/4/24/024
     **/
    private void pushToJrttPaySdk(String userUniqueId, PkgChannelConfJrtt pkgChannelConfJrtt,
        String udid, BigDecimal money) {
        if (pkgChannelConfJrtt == null) {
            //  渠道参数缺失 返回
            return;
        }
        if (StringUtils.isEmpty(pkgChannelConfJrtt.getPackageName()) || StringUtils.isEmpty(
            pkgChannelConfJrtt.getAppKey())) {
            // 参数不存在，则不走服务端埋点接口上报
            return;
        }
        Integer currencyAmount = money.setScale(0, BigDecimal.ROUND_DOWN).intValue();
        CallbackDataJrttPaySdk callBackDataJrttPaySdk = new CallbackDataJrttPaySdk(
            pkgChannelConfJrtt.getAppKey(),
            userUniqueId, udid, pkgChannelConfJrtt.getPackageName(), currencyAmount);
        CtCallback insert = new CtCallback();
        insert.setCallbackChannel(CallBackChannelConstant.JRTT_SDK_PAY);
        insert.setEventType(CallBackEventType.JRTT_SDK_PAY);
        insert.setCallbackData(JSONObject.toJSONString(callBackDataJrttPaySdk));
        insert.setCallbackNum(0);
        insert.setCallbackState(CallBackState.WAIT);
        save(insert);
    }

    /**
     * @author chenyw
     * @description 定时请求callback
     * @date 13:48 2023/4/25/025
     **/
    @Scheduled(cron = "0/10 * * * * ?")
    public void handleCallback() {
        // 最大重试次数
        int MAX_RETRY_NUM = 3;
        List<CtCallback> ctCallbackList = list(
            new LambdaQueryWrapper<CtCallback>().eq(CtCallback::getCallbackState,
                CallBackState.WAIT).lt(CtCallback::getCallbackNum, MAX_RETRY_NUM));
        for (CtCallback ctCallback : ctCallbackList) {
            Integer result = CallBackState.FAIL;
            // 不同渠道不同推送方法
            if (CallBackChannelConstant.JRTT.equals(ctCallback.getCallbackChannel())) {
                result = handleJrtt(ctCallback);
            } else if (CallBackChannelConstant.JRTT_SDK_PAY.equals(
                ctCallback.getCallbackChannel())) {
                result = handleJrttSdkPay(ctCallback);
            }
            ctCallback.setCallbackNum(CountUtil.increaseInt(ctCallback.getCallbackNum()));
            ctCallback.setCallbackState(result);
            updateById(ctCallback);
        }
    }

    /**
     * @param ctCallback
     * @return java.lang.Integer
     * @author chenyw
     * @description 推送到头条
     * @date 10:14 2023/4/25/025
     **/
    private Integer handleJrtt(CtCallback ctCallback) {
        log.info("推送至头条:{}", ctCallback.getCallbackData());
        try {
            String callbackData = ctCallback.getCallbackData();
            CallbackDataJrtt callBackDataJrtt = JSONObject.parseObject(callbackData,
                CallbackDataJrtt.class);
            JSONObject jsonObject = RestUtil.get(callBackDataJrtt.getUrl());
            log.info("推送至头条成功,响应:{}", jsonObject);
        } catch (Exception e) {
            log.error("推送至头条失败", e);
            return CallBackState.FAIL;
        }
        return CallBackState.SUCCESS;
    }

    /**
     * @param ctCallback
     * @return java.lang.Integer
     * @author chenyw
     * @description 推送到头条 sdk特殊上报 支付
     * @date 10:14 2023/4/25/025
     **/
    private Integer handleJrttSdkPay(CtCallback ctCallback) {
        log.info("推送至头条(服务端埋点):{}", ctCallback.getCallbackData());
        try {
            String callbackData = ctCallback.getCallbackData();
            CallbackDataJrttPaySdk callBackDataJrtt = JSONObject.parseObject(callbackData,
                CallbackDataJrttPaySdk.class);
            CallbackJrttPaySdkRequest callbackJrttPaySdkRequest = new CallbackJrttPaySdkRequest();
            JrttPaySdkUser jrttPaySdkUser = new JrttPaySdkUser();
            jrttPaySdkUser.setUdid(callBackDataJrtt.getUdid());
            jrttPaySdkUser.setUserUniqueId(callBackDataJrtt.getUserUniqueId());
            callbackJrttPaySdkRequest.setUser(jrttPaySdkUser);
            JrttPaySdkHeader jrttPaySdkHeader = new JrttPaySdkHeader();
            jrttPaySdkHeader.setAppPackage(callBackDataJrtt.getAppPackage());
            callbackJrttPaySdkRequest.setHeader(jrttPaySdkHeader);
            JrttPaySdkEvents jrttPaySdkEvents = new JrttPaySdkEvents();
            jrttPaySdkEvents.setEvent("purchase");
            jrttPaySdkEvents.setParams(
                "{\"currency\":\"rmb\",\"is_success\":\"yes\",\"is_server\":\"yes\",\"currency_amount\":"
                    + callBackDataJrtt.getCurrencyAmount() + "}");
            jrttPaySdkEvents.setLocalTimeMs(System.currentTimeMillis());
            List<JrttPaySdkEvents> jrttPaySdkEventsList = new ArrayList();
            jrttPaySdkEventsList.add(jrttPaySdkEvents);
            callbackJrttPaySdkRequest.setEvents(jrttPaySdkEventsList);
            JSONObject param = (JSONObject) JSONObject.toJSON(callbackJrttPaySdkRequest);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-MCS-AppKey", callBackDataJrtt.getAppKey());
            JSONObject res = RestUtil.post(CallBackUrlConstant.JRTT_PAY_SDK_URL, headers, null,
                param);
            log.info("推送至头条成功,响应:{}", res);
        } catch (Exception e) {
            log.error("推送至头条失败", e);
            return CallBackState.FAIL;
        }
        return CallBackState.SUCCESS;
    }

}
