package org.jeecg.modules.pay.test;


import static org.jeecg.common.constant.PayTypeConstant.PAY_TYPE_APPLE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;

import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.constant.BankStatus;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpCommodityModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.RestUtil;
import org.jeecg.common.util.encryption.RSAUtil;
import org.jeecg.modules.game.entity.OpSubGame;
import org.jeecg.modules.game.service.IOpSubGameService;
import org.jeecg.modules.pay.constant.OpenConstant;
import org.jeecg.common.constant.PayTypeConstant;
import org.jeecg.modules.pay.dto.IosPayCheckDto;
import org.jeecg.modules.pay.entity.OpOrder;
import org.jeecg.modules.pay.entity.OpOrderCount;
import org.jeecg.modules.pay.entity.OpPaySwitch;
import org.jeecg.modules.pay.mapper.OpOrderCountMapper;
import org.jeecg.modules.pay.mapper.OpPaySwitchMapper;
import org.jeecg.modules.pay.service.IIosPayService;
import org.jeecg.modules.pay.service.IOpOrderCountService;
import org.jeecg.modules.pay.service.IOpOrderService;
import org.jeecg.modules.pay.service.impl.OpPaySwitchServiceImpl;
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
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.pay.test
 * @className: PayTest
 * @author: Eric
 * @description: TODO
 * @date: 2023/1/5 10:32
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class PayTest {

    @Autowired
    @InjectMocks
    private OpPaySwitchServiceImpl opPaySwitchService;

    @SpyBean
    OpPaySwitchMapper opPaySwitchMapper;

    @SpyBean
    private OpOrderCountMapper opOrderCountMapper;

    @SpyBean
    private IOpOrderService orderService;
    @SpyBean
    private IOpUserService opUserService;
    @SpyBean
    private IOpSubGameService subGameService;
    @SpyBean
    private IOpOrderCountService orderCountService;
    @SpyBean
    private IIosPayService iosPayService;
    @SpyBean
    private IGameApi gameApi;

    MockedStatic<RestUtil> restUtil;
    MockedStatic<RSAUtil> rsaUtil;
    @Before
    public void before(){
        restUtil = mockStatic(RestUtil.class);
    }

    @After
    public void after(){
        restUtil.close();
    }

    //获取支付列表
    @Test
    public void testGetPayList() {
        OpPaySwitch opPaySwitch;
        List<Integer> defaultPay;
        List<Integer> noDefaultPay;

        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);
        //没有配置
        doReturn(null).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(defaultPay,opPaySwitchService.checkPayMode(1,1, "122", "1.0", "v1.0", new BigDecimal(400)));

        //在黑名单内
        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);
        opPaySwitch = new OpPaySwitch();
        opPaySwitch.setBlackList("122;123");
        opPaySwitch.setDefaultPay(PayTypeConstant.PAY_TYPE_APPLE + "");
        doReturn(opPaySwitch).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(defaultPay, opPaySwitchService
            .checkPayMode(1,1, "122", "1.0", "v1.0", new BigDecimal(400)));


        //没有配置非默认支付
        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);
        opPaySwitch = new OpPaySwitch();
        opPaySwitch.setDefaultPay(PayTypeConstant.PAY_TYPE_APPLE + "");
        doReturn(opPaySwitch).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(defaultPay, opPaySwitchService
            .checkPayMode(1,1, "122", "1.0", "v1.0", new BigDecimal(400)));

        //在白名单
        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);
        noDefaultPay = new LinkedList<>();
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_WEIXIN);
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch = new OpPaySwitch();
        opPaySwitch.setDefaultPay(PAY_TYPE_APPLE + "");
        opPaySwitch.setWhiteList("122;123");
        opPaySwitch.setNoDefaultPay(PayTypeConstant.PAY_TYPE_WEIXIN + "," + PayTypeConstant.PAY_TYPE_H5_ALI);
        doReturn(opPaySwitch).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(noDefaultPay, opPaySwitchService
            .checkPayMode(1,1, "122", "1.0", "v1.0", new BigDecimal(400)));

        //游戏版本和构建版本都匹配
        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);
        noDefaultPay = new LinkedList<>();
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_WEIXIN);
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch = new OpPaySwitch();
        opPaySwitch.setDefaultPay(PAY_TYPE_APPLE + "");
        opPaySwitch.setNoDefaultPay(PayTypeConstant.PAY_TYPE_WEIXIN + "," + PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch.setGameVersion("1.0");
        opPaySwitch.setGameBuild("v1.0");
        doReturn(opPaySwitch).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(defaultPay, opPaySwitchService
            .checkPayMode(1, 1,"122", "1.0", "v1.0", new BigDecimal(400)));


        //不在时间范围内
        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);
        noDefaultPay = new LinkedList<>();
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_WEIXIN);
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch = new OpPaySwitch();
        opPaySwitch.setDefaultPay(PAY_TYPE_APPLE + "");
        opPaySwitch.setNoDefaultPay(PayTypeConstant.PAY_TYPE_WEIXIN + "," + PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch.setRangeTime("23:49:59,23:59:59");
        doReturn(opPaySwitch).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(defaultPay, opPaySwitchService
            .checkPayMode(1, 1,"122", "1.0", "v1.0", new BigDecimal(400)));

        //不在地区范围内
        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);
        noDefaultPay = new LinkedList<>();
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_WEIXIN);
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch = new OpPaySwitch();
        opPaySwitch.setDefaultPay(PAY_TYPE_APPLE + "");
        opPaySwitch.setNoDefaultPay(PayTypeConstant.PAY_TYPE_WEIXIN + "," + PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch.setRegions("福建,北京");
        doReturn(opPaySwitch).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(defaultPay, opPaySwitchService
            .checkPayMode(1, 1,"122", "1.0", "v1.0", new BigDecimal(400)));

        //单笔订单大于配置
        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);
        noDefaultPay = new LinkedList<>();
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_WEIXIN);
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch = new OpPaySwitch();
        opPaySwitch.setDefaultPay(PAY_TYPE_APPLE + "");
        opPaySwitch.setNoDefaultPay(PayTypeConstant.PAY_TYPE_WEIXIN + "," + PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch.setOrderMoney(new BigDecimal(100));
        doReturn(opPaySwitch).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(defaultPay, opPaySwitchService
            .checkPayMode(1,1, "122", "1.0", "v1.0", new BigDecimal(400)));

        //支付次数大于配置
        defaultPay = new LinkedList<>();
        defaultPay.add(PayTypeConstant.PAY_TYPE_APPLE);

        noDefaultPay = new LinkedList<>();
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_WEIXIN);
        noDefaultPay.add(PayTypeConstant.PAY_TYPE_H5_ALI);

        opPaySwitch = new OpPaySwitch();
        opPaySwitch.setDefaultPay(PAY_TYPE_APPLE + "");
        opPaySwitch.setNoDefaultPay(PayTypeConstant.PAY_TYPE_WEIXIN + "," + PayTypeConstant.PAY_TYPE_H5_ALI);
        opPaySwitch.setRechargeTimes(10);
        OpOrderCount orderCount = new OpOrderCount();
        orderCount.setNumber(5);
        doReturn(orderCount).when(opOrderCountMapper).selectOne(any());
        doReturn(opPaySwitch).when(opPaySwitchMapper).selectOne(any());
        Assert.assertEquals(defaultPay, opPaySwitchService
            .checkPayMode(1, 1,"122", "1.0", "v1.0", new BigDecimal(400)));
    }

    //单次发货
    @Test
    public void testDeliverOnce() {
        OpOrder order = new OpOrder();
        order.setId(22);
        order.setGameId(22);
        order.setUserId(1111);
        order.setOrderId("202205261030501410105995127864");
        order.setMoney(new BigDecimal(6));
        order.setStatus(1);
        order.setIsTest(0);
        order.setOpenTime(LocalDateTime.now().minusSeconds(30));
        order.setStatusTime(LocalDateTime.now().minusSeconds(30));
        order.setBankType(5);
        order.setBankMoney(new BigDecimal(6));
        order.setBankOrderId("2022052622001487321417686602");
        order.setBankStatus(1000);
        order.setBankStatusTime(LocalDateTime.now().minusSeconds(20));
        order.setGameOrderId("2#3#1653532249");
        order.setGameData("2$$idea$$1$$idea$$3$$普通充值6$$android$$1653532249");
        order.setGameStatus(-1003);
        order.setGameStatusTime(LocalDateTime.now().minusSeconds(60));
        order.setGameDeliverRetry(1);
        order.setGameDeliverTime(LocalDateTime.now().minusSeconds(60));
        order.setExtraData1(
            "2022-05-26 10:30:50\tpay\t141010000001\t14101\t1\t\tffffffff-bf82-7e1a-ffff-fffffb5ed7d0\tc84792d514bbe87d18c24cd7a0692288\t1\t6.0\t202205261030501410105995127864\t5\t\t1\t0\t1_2\t6");
        order.setOrderType(0);
        order.setCouponPrice(BigDecimal.ZERO);

        doReturn(order).when(orderService).getOne(any());
        OpUser user = new OpUser();
        user.setId(1);
        user.setPlatformCurrency(new BigDecimal(100));

        doReturn(user).when(opUserService).getOne(any());
        doReturn(true).when(opUserService).save(user);

        OpSubGame game = new OpSubGame();
        game.setGameId(22);
        game.setDeliverUrl("dadwda");
        game.setGameKey("dfca47f5d5d1189fc89bdbd3fcc07e50");
        game.setPayPubkey(
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCwDZfIQ6/ibo+iljipffReEoGUNlWkxwCDOFYapezoO/ZOyiub+BMbE6s2MvXt+phza1P+GEJFT4ZiRjXf8RA9A77OguY1pQKlVC4JU5WieGyk01iRmm2Iw5v6uuoay0obOsV8i7xuiHA1EoUjue3R5diXShf8E+2YvbNBwjteVQIDAQAB");
        game.setPayPrikey(
            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALANl8hDr+Juj6KWOKl99F4SgZQ2VaTHAIM4Vhql7Og79k7KK5v4ExsTqzYy9e36mHNrU/4YQkVPhmJGNd/xED0Dvs6C5jWlAqVULglTlaJ4bKTTWJGabYjDm/q66hrLShs6xXyLvG6IcDUShSO57dHl2JdKF/wT7Zi9s0HCO15VAgMBAAECgYAK2DuCTZEXDKJybHh/EFAuP0uWGqa56LxCWQUMzS3DvHXWsqNvDx/2KgcA5rC5gRdE/NaGr23doSFxCkwlSE8sqLjbP6a+bypf35eqYoBPwusLKCBGKH0LjkGrKB95B9e31W87rNJ5MBCzqUc3b4FlN7Sn9vwsxc6S4MfFNcntHQJBAOm0tEn8xuxvaM6mEgtfQNxEwHoOLSDBimSQ8Tku+Gv1i3fwIGgXrIcQVNhsBFaQ5BajNEa71wCdg6oDAM/IcXsCQQDA2PV5RuYB5Kgi45mL5NCh4Bpt/90aSQCikG7KjZf7+jfMBi5qJl/OppllOh2hVCHNUGv6J/iPdE+2PUknxV5vAkA3DyuqgrMa4OSASv/VTgup2Ui/eLeJEA/awYsTPc2pIaBKEXiRDYLID0Dt/ATJoDkcXDbkszxkGLf8RdjbGw4hAkBtLOabiAXC4J5HUi6lQh7S5bpQt9+Gd/nCDp6KUWrPnjJkcMNqGNt4dFy3MBWCNc0FHRhkAKj5HdHazAtUVqpFAkAv1H+a3KS9B/uFc+MouA1Ow4z4QNrTTxLdoJxT6D4HJ9IXmsQN0JqjAVgm+yB/xpNUrxCbt6v2oD+P0u0cceOm");

        doReturn(game).when(subGameService).getOne(any());
        OpSubGameModel opSubGameModel = new OpSubGameModel();
        opSubGameModel.setGameId(22);
        opSubGameModel.setPayPrikey(game.getPayPrikey());
        doReturn(opSubGameModel).when(gameApi).getOpSubGame(any());
        doReturn(true).when(orderCountService).saveOrUpdate(any());
        doReturn(true).when(orderService).updateById(any());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ret", 0);
        restUtil.when(() -> RestUtil.post(anyString(), any(), any(JSONObject.class))).thenReturn(jsonObject);
        orderService.deliverOnce("202205261030501410105995127864");
    }

    /**
     * @author chenyw
     * @description 苹果校验订单
     * @date 11:05 2023/1/7/007
     **/
    @Test
    public void testVerifyReceipt() {
        IosPayCheckDto iosPayCheckDto = new IosPayCheckDto();
        iosPayCheckDto.setSubGameId(1);
        iosPayCheckDto.setDevice("test-device");
        iosPayCheckDto.setMmmType("CNY");
        iosPayCheckDto.setOrderId("202301052309121412030752447402");
        iosPayCheckDto.setPkgVer("com.hy.gsjbcps");
        iosPayCheckDto.setReceipt("test-receipt");
        iosPayCheckDto.setTransactionId("2000000243216423");
        OpOrder order = new OpOrder();
        order.setMoney(new BigDecimal(3));
        order.setStatus(OpenConstant.CREATE_ORDER_STATUS);
        order.setBankStatus(BankStatus.INIT);
        doReturn(order).when(orderService).getOne(any());
        String mockJsonRes = "{"
            + "    \"receipt\": {"
            + "        \"receipt_type\": \"ProductionSandbox\","
            + "        \"adam_id\": 0,"
            + "        \"app_item_id\": 0,"
            + "        \"bundle_id\": \"com.hy.gsjbcps\","
            + "        \"application_version\": \"1\","
            + "        \"download_id\": 0,"
            + "        \"version_external_identifier\": 0,"
            + "        \"receipt_creation_date\": \"2023-01-05 15:09:25 Etc\\/GMT\","
            + "        \"receipt_creation_date_ms\": \"1672931365000\","
            + "        \"receipt_creation_date_pst\": \"2023-01-05 07:09:25 America\\/Los_Angeles\","
            + "        \"request_date\": \"2023-01-05 15:09:30 Etc\\/GMT\","
            + "        \"request_date_ms\": \"1672931370744\","
            + "        \"request_date_pst\": \"2023-01-05 07:09:30 America\\/Los_Angeles\","
            + "        \"original_purchase_date\": \"2013-08-01 07:00:00 Etc\\/GMT\","
            + "        \"original_purchase_date_ms\": \"1375340400000\","
            + "        \"original_purchase_date_pst\": \"2013-08-01 00:00:00 America\\/Los_Angeles\","
            + "        \"original_application_version\": \"1.0\","
            + "        \"in_app\": ["
            + "            {"
            + "                \"quantity\": \"1\","
            + "                \"product_id\": \"com.ninja.hylegend.tier6\","
            + "                \"transaction_id\": \"2000000243216423\","
            + "                \"original_transaction_id\": \"2000000243216423\","
            + "                \"purchase_date\": \"2023-01-05 15:09:25 Etc\\/GMT\","
            + "                \"purchase_date_ms\": \"1672931365000\","
            + "                \"purchase_date_pst\": \"2023-01-05 07:09:25 America\\/Los_Angeles\","
            + "                \"original_purchase_date\": \"2023-01-05 15:09:25 Etc\\/GMT\","
            + "                \"original_purchase_date_ms\": \"1672931365000\","
            + "                \"original_purchase_date_pst\": \"2023-01-05 07:09:25 America\\/Los_Angeles\","
            + "                \"is_trial_period\": \"false\","
            + "                \"in_app_ownership_type\": \"PURCHASED\""
            + "            }"
            + "        ]"
            + "    },"
            + "    \"environment\": \"Sandbox\","
            + "    \"status\": 0"
            + "}";
        JSONObject jsonObject = JSONObject.parseObject(mockJsonRes);
        restUtil.when(() -> RestUtil.post(anyString(), any(), any(JSONObject.class))).thenReturn(jsonObject);
        OpCommodityModel opCommodityModel = new OpCommodityModel();
        opCommodityModel.setMoney(order.getMoney());
        doReturn(opCommodityModel).when(gameApi).getOpCommdityByGoodId(any(),any(),any());
        doReturn(true).when(orderService).updateById(any());
        doNothing().when(orderService).deliverOnce(any());
        iosPayService.verifyReceipt(iosPayCheckDto);
    }
}
