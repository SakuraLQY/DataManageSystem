package org.jeecg.modules.pay.test;


import static org.jeecg.common.constant.PayTypeConstant.PAY_TYPE_APPLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javax.xml.crypto.Data;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.constant.BankStatus;
import org.jeecg.common.constant.ChannelConstant;
import org.jeecg.common.constant.GameStatus;
import org.jeecg.common.constant.KafkaKeyConstant;
import org.jeecg.common.constant.KafkaTopicConstant;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpCommodityModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.encryption.RSAUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.service.IOpSubGameService;
import org.jeecg.modules.pay.constant.OpenConstant;
import org.jeecg.modules.pay.dto.IosPayCheckDto;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.entity.OpOrder;
import org.jeecg.modules.pay.entity.OpOrderCount;
import org.jeecg.modules.pay.entity.OpPaySwitch;
import org.jeecg.modules.pay.mapper.OpOrderCountMapper;
import org.jeecg.modules.pay.mapper.OpPaySwitchMapper;
import org.jeecg.modules.pay.service.IIosPayService;
import org.jeecg.modules.pay.service.IOpOrderCountService;
import org.jeecg.modules.pay.service.IOpOrderService;
import org.jeecg.modules.pay.service.impl.OpPaySwitchServiceImpl;
import org.jeecg.modules.pay.util.GenOrderUtils;
import org.jeecg.modules.users.entity.OpUser;
import org.jeecg.modules.users.service.IOpUserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cyw
 * @version V1.0
 * @description: 模拟真实的支付
 * @date: 2022/12/26
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class FullPayTest {

    @Autowired
    private IOpOrderService opOrderService;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void pay() {

        Integer[] userIdArr = {1858199608, 1858199609, 1858199610, 1858199611, 1858199612,
            1858199613, 1858199975, 1858199976, 1858199977, 1858199978};
        List<LocalDateTime> dateList = new ArrayList();
        for (int i = 59; i > 0; i--) {
            LocalDateTime localDate = LocalDateTime.now().minusDays(i);
            dateList.add(localDate);
        }
        for (LocalDateTime locald : dateList) {
            for (Integer userId : userIdArr) {
                OpOrder opOrder = new OpOrder();
                // 添加订单数据
                opOrder.setGameId(2).setSubGameId(22).setPkgId(9)
                    .setOrderId(GenOrderUtils.genOrderId())
                    .setUserId(userId).setMoney(new BigDecimal(6))
                    .setStatus(OpenConstant.CREATE_ORDER_STATUS)
                    .setOpenTime(LocalDateTime.now()).setStatusTime(locald)
                    .setBankType(PayTypeConstant.PAY_TYPE_SDK_ALI).setBankMoneyType("yuan")
                    .setCouponPrice(BigDecimal.ZERO)
                    .setBankMoney(new BigDecimal(6)).setBankOrderId("20230426"+ RandomUtil.randomNumbers(6))
                    .setBankStatus(0).setBankStatusTime(LocalDateTime.now())
                    .setGameOrderId("20230427"+ RandomUtil.randomNumbers(6)).setGameData("1")
                    .setGameStatus(GameStatus.GAME_STATUS_SUCCESS)
                    .setGameStatusTime(LocalDateTime.now())
                    .setGameDeliverRetry(0).setGameDeliverTime(LocalDateTime.now());
                String ExtraData = buildExtraData(16, 1, opOrder.getMoney(),
                    BigDecimal.ZERO, opOrder, locald);
                opOrder.setExtraData1(ExtraData);
                opOrderService.save(opOrder);
                ParsePayDto p = JSONObject.parseObject(ExtraData, ParsePayDto.class);
                kafkaTemplate.send(KafkaTopicConstant.TOPIC_EVENT_PARSE,
                    KafkaKeyConstant.GROUP_SDK_PAY, p);
            }
        }
    }

    private String buildExtraData(Integer dealId, Integer payType, BigDecimal money,
        BigDecimal couponPrice, OpOrder opOrder, LocalDateTime time) {
        ParsePayDto parsePayDto = new ParsePayDto();
        if (oConvertUtils.isNotEmpty(dealId)) {
            Long timestamp = time.toInstant(ZoneOffset.of("+8")).toEpochMilli();
            parsePayDto.setGameId(opOrder.getGameId())
                .setSubGameId(opOrder.getSubGameId())
                .setChannelId(ChannelConstant.JRTT)
                .setChannelTypeId(1)
                .setChannelSubAccountId(7)
                .setDealId(dealId)
                .setPkgId(opOrder.getPkgId())
                .setUniqueId("DEVICE-TEST")
                .setUserId(opOrder.getUserId())
                .setMoney(money).setOrderId(opOrder.getOrderId())
                .setPayType(payType)
                .setOrderType(opOrder.getOrderType())
                .setCouponPrice(couponPrice)
                .setServerId(1)
                .setRoleId("2")
                .setRoleLevel(10)
                .setPayVendorId(1)
                .setClientIp("192.1.168.1")
                .setRegion("福建")
                .setTime(timestamp);
        }
        return JSONObject.toJSONString(parsePayDto);
    }
}
