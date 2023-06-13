package org.jeecg.modules.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.jeecg.common.constant.BankStatus;
import org.jeecg.common.constant.GameStatus;
import org.jeecg.common.constant.KafkaKeyConstant;
import org.jeecg.common.constant.KafkaTopicConstant;
import org.jeecg.common.constant.OrderType;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.encryption.RSAUtil;
import org.jeecg.modules.api.ISdkuserApi;
import org.jeecg.modules.pay.constant.OrderIsTest;
import org.jeecg.modules.pay.entity.OpOrder;
import org.jeecg.modules.pay.entity.OpOrderCount;
import org.jeecg.modules.pay.mapper.OpOrderMapper;
import org.jeecg.modules.pay.service.IOpOrderCountService;
import org.jeecg.modules.pay.service.IOpOrderService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: op_order
 * @Author: jeecg-boot
 * @Date: 2022-12-21
 * @Version: V1.0
 */
@Service
@Slf4j
@DS("open_gateway")
public class OpOrderServiceImpl extends ServiceImpl<OpOrderMapper, OpOrder> implements
    IOpOrderService {

    @Value("${order.retryTimes}")
    private Integer retryTimes;

    @Autowired
    private ISdkuserApi sdkuserApi;

    @Autowired
    private IGameApi gameApi;

    @Autowired
    private IOpOrderCountService opOrderCountService;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public OpOrder getPkgIdOrder(Integer pkgId, String gameOrderId) {
        LambdaQueryWrapper<OpOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpOrder::getPkgId, pkgId);
        wrapper.eq(OpOrder::getGameOrderId, gameOrderId);
        return getOne(wrapper);
    }

    @Override
    public boolean updateOrderStatus(String orderId, int bankStatus) {
        UpdateWrapper<OpOrder> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_id", orderId);
        wrapper.set("bank_status", bankStatus);
        wrapper.set("bank_status_time", new Date());

        return update(wrapper);
    }

    @Override
    public OpOrder getOrderByOrderId(String orderId) {
        LambdaQueryWrapper<OpOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpOrder::getOrderId, orderId);
        return getOne(wrapper);
    }

    @Override
    public boolean updateOrderByPay(String orderId, String transactionId, int bankStatus,
        BigDecimal bankMoney) {
        UpdateWrapper<OpOrder> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("order_id", orderId);
        updateWrapper.set("bank_order_id", transactionId);
        updateWrapper.set("bank_status", bankStatus);
        updateWrapper.set("bank_money", bankMoney);
        updateWrapper.set("bank_status_time", LocalDateTime.now());
        return update(updateWrapper);
    }


    /**
     * @return void
     * @author czb
     * @description 定时器，订单重试方法
     * @date 2022/12/24 11:28
     */
    @Scheduled(cron = "0/15 * * * * ?")
    public void handleOrder() {
        List<OpOrder> orders = list(new LambdaQueryWrapper<OpOrder>()
            //银行状态正常
            .ge(OpOrder::getBankStatus, BankStatus.NORMAL)
            //发货状态没成功的
            .lt(OpOrder::getGameStatus, GameStatus.GAME_STATUS_SUCCESS)
            //距离上次更新银行状态时间超过10秒
            .le(OpOrder::getBankStatusTime, LocalDateTime.now().minusSeconds(10))
            //重试次数
            .le(OpOrder::getGameDeliverRetry, retryTimes)
            //上次发货时间到现在超过时间间隔 时间间隔公式=重试次数的平方（单位秒，最大为3600秒（即一个小时））
            .last("and now() - game_deliver_time > if(POW(game_deliver_retry,2) > 3600, 3600, POW(game_deliver_retry, 2))")
        );
        if (orders.size() == 0) {
            return;
        }
        LinkedList<OpOrder> opOrders = new LinkedList<>();
        LinkedList<OpOrderCount> opOrderCounts = new LinkedList<>();
        for (OpOrder order : orders) {
            //发货
            Integer gameStatus = doDeliverOnce(order);
            //更新用户总消费表，有记录加上此次订单消费金额，没有创建新纪录，如果支付失败gameStatus < 1000,则不更新或者不创建新纪录
            OpOrderCount opOrderCount = updateOrderCount(gameStatus, order);
            if (opOrderCount != null) {
                opOrderCounts.add(opOrderCount);
            }
            //更新订单信息
            updateOrder(order, gameStatus);
            opOrders.add(order);
        }
        //数据库操作
        ((IOpOrderService) AopContext.currentProxy()).updateDBBatch(opOrders, opOrderCounts);
    }



    /**
     * @param orderId: 订单id
     * @return void
     * @author czb
     * @description 单笔订单发货
     * @date 2022/12/22 14:34
     */
    @Override
    public void deliverOnce(String orderId) {
        OpOrder order = getOne(new LambdaQueryWrapper<OpOrder>().eq(OpOrder::getOrderId, orderId));
        //订单不存在
        if (null == order) {
            throw new IdeaRunTimeException(ErrorCode.ORDER_NOT_EXIST);
        }
        //银行状态非法
        if (order.getBankStatus() < BankStatus.NORMAL) {
            throw new IdeaRunTimeException(ErrorCode.BANK_STATUS_INVAID);
        }
        //发货
        Integer gameStatus = doDeliverOnce(order);

        OpOrderCount opOrderCount = updateOrderCount(gameStatus, order);
        //更新订单信息
        updateOrder(order,gameStatus);
        //数据库操作，保存信息
        ((IOpOrderService) AopContext.currentProxy()).updateDB(order, opOrderCount);
    }

    /**
     * @param order: 订单
     * @return Integer 发货返回状态
     * @author czb
     * @description 单次发货
     * @date 2022/12/22 16:07
     */
    private Integer doDeliverOnce(OpOrder order) {
        Integer orderType = order.getOrderType();

        int gameStatus = GameStatus.GAME_STATUS_SUCCESS;
        //充值平台币
        if (orderType == OrderType.PLATFORM_CURRENCY) {
            if (sdkuserApi.updateUserPC(order.getUserId(), order.getMoney().multiply(new BigDecimal(100)).intValue(), true)) {
                gameStatus = GameStatus.GAME_STATUS_PLATFORM_CURRENCY_FAIL;
                //todo 打印失败日志，订单id，充值类型，充值金额，充值玩家账号
            }
        } else { //游戏充值
            OpSubGameModel opSubGameModel = gameApi.getOpSubGame(order.getSubGameId());
            //请求参数，字典序排序
            TreeMap<String, Object> dictMap = new TreeMap<>();
            dictMap.put("app_id", opSubGameModel.getId());
            dictMap.put("user_id", order.getUserId());
            dictMap.put("order_id", order.getOrderId());
            dictMap.put("pay_type", order.getBankType());
            dictMap.put("money", order.getMoney());
            dictMap.put("app_order_id", order.getGameOrderId());
            dictMap.put("app_data", order.getGameData());
            dictMap.put("is_test", order.getIsTest());
            dictMap.put("time", System.currentTimeMillis() / 1000);
            StringBuilder toSignStr = new StringBuilder();
            for (Entry<String, Object> entry : dictMap.entrySet()) {
                toSignStr.append(entry.getValue().toString());
            }
            toSignStr.append(opSubGameModel.getGameKey());
            //签名
            String sign = RSAUtil.sign(toSignStr.toString(), opSubGameModel.getPayPrikey());
            try {
                sign = URLEncoder.encode(sign, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("urlEncode失败");
            }
            dictMap.put("sign", sign);
            JSONObject jsonObject = new JSONObject();
            for (Entry<String, Object> entry : dictMap.entrySet()) {
                jsonObject.put(entry.getKey(), entry.getValue());
            }
            log.info("发货请求:{}", jsonObject);
            JSONObject result = RestUtil.post(opSubGameModel.getDeliverUrl(), jsonObject, null);
            log.info("发货结果:{}", result);
            if (null == result) {
                gameStatus = GameStatus.GAME_STATUS_RESULT_NULL;
            } else {
                Integer ret = result.getInteger("ret");
                if (null == ret) {
                    gameStatus = GameStatus.GAME_STATUS_RET_CODE_NULL;
                } else {
                    if (ret != 0) {
                        gameStatus = GameStatus.GAME_STATUS_RESULT_FAIL;
                    }
                }
            }
        }
        if(GameStatus.GAME_STATUS_SUCCESS.equals(gameStatus)){
            // 订单发货成功
            ParsePayDto parsePayDto = JSONObject.parseObject(order.getExtraData1(), ParsePayDto.class);
            if(OrderIsTest.NO.equals(order.getIsTest())){
                kafkaTemplate.send(KafkaTopicConstant.TOPIC_EVENT_PARSE,
                    KafkaKeyConstant.GROUP_SDK_PAY, parsePayDto);
            }
        }
        return gameStatus;
    }

    /**
     * @param gameStatus: 发货返回状态
     * @param order:      订单
     * @return OpOrderCount
     * @author czb
     * @description 更新用户总消费表，有记录加上此次订单消费金额，没有创建新纪录，如果支付失败gameStatus < 1000,则不更新或者不创建新纪录
     * @date 2022/12/22 16:06
     */
    private OpOrderCount updateOrderCount(Integer gameStatus, OpOrder order) {
        //更新用户总计表，充值次数、累计金额
        if (gameStatus >= GameStatus.GAME_STATUS_SUCCESS) {
            if (null != order.getExtraData1()) {
                log.info("event: {}，is_test: {}", order.getExtraData1(), order.getIsTest());
            }
            OpOrderCount orderCount = opOrderCountService
                .getOne(new LambdaQueryWrapper<OpOrderCount>()
                    .eq(OpOrderCount::getGameId, order.getGameId())
                    .eq(OpOrderCount::getUserId, order.getUserId()));
            if (orderCount != null) {
                orderCount.setTotalMoney(orderCount.getTotalMoney().add(order.getMoney()));
                orderCount.setNumber(orderCount.getNumber() + 1);
                return orderCount;
            } else {
                OpOrderCount opOrderCount = new OpOrderCount();
                opOrderCount.setGameId(order.getGameId());
                opOrderCount.setUserId(order.getUserId());
                opOrderCount.setNumber(1);
                opOrderCount.setTotalMoney(order.getMoney());
                return opOrderCount;
            }
        }
        return null;
    }

    /**
     * @param order: 订单
     * @param gameStatus: 发货状态
     * @return void
     * @author czb
     * @description
     * @date 2022/12/24 15:53
     */
    private void updateOrder(OpOrder order, Integer gameStatus) {
        order.setGameStatus(gameStatus);
        order.setGameStatusTime(LocalDateTime.now());
        order.setGameDeliverRetry(order.getGameDeliverRetry() + 1);
        order.setGameDeliverTime(LocalDateTime.now());
    }

    /**
     * @param order:订单
     * @param opOrderCount: 订单统计
     * @return void
     * @author czb
     * @description 单次更新数据库操作
     * @date 2022/12/24 11:24
     */
    @Transactional
    public void updateDB(OpOrder order, OpOrderCount opOrderCount) {
        if (opOrderCount != null) {
            opOrderCountService.saveOrUpdate(opOrderCount);
        }
        updateById(order);
    }

    /**
     * @param opOrders:      订单
     * @param opOrderCounts: 订单统计
     * @return void
     * @author czb
     * @description 批量更新数据库
     * @date 2022/12/24 11:21
     */
    @Transactional
    public void updateDBBatch(LinkedList<OpOrder> opOrders,
        LinkedList<OpOrderCount> opOrderCounts) {
        updateBatchById(opOrders);
        if (opOrderCounts.size() != 0) {
            opOrderCountService.saveOrUpdateBatch(opOrderCounts);
        }
    }

}
