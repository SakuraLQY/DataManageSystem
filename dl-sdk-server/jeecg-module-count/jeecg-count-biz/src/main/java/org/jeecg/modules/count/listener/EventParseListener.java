package org.jeecg.modules.count.listener;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jeecg.common.constant.KafkaKeyConstant;
import org.jeecg.common.constant.KafkaTopicConstant;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParsePayDto;
import org.jeecg.common.kafka.dto.ParseRegisterDto;
import org.jeecg.common.kafka.dto.ParseStartDto;
import org.jeecg.modules.count.entity.CtDevice;
import org.jeecg.modules.count.entity.CtOrder;
import org.jeecg.modules.count.entity.CtRole;
import org.jeecg.modules.count.entity.CtUser;
import org.jeecg.modules.count.service.ICtCallbackService;
import org.jeecg.modules.count.service.ICtDailyService;
import org.jeecg.modules.count.service.ICtDeviceService;
import org.jeecg.modules.count.service.ICtHourService;
import org.jeecg.modules.count.service.ICtOrderService;
import org.jeecg.modules.count.service.ICtRoleService;
import org.jeecg.modules.count.service.ICtUserService;
import org.jeecg.modules.count.service.ILgLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @Description: kafka监听对象-事件解析
 * @Author: jeecg-boot
 * @Date: 2022-12-12
 * @Version: V1.0
 */
@Service
@Slf4j
public class EventParseListener {

    @Autowired
    private ICtDeviceService ctDeviceService;

    @Autowired
    private ICtDailyService ctDailyService;

    @Autowired
    private ICtHourService ctHourService;

    @Autowired
    private ICtUserService ctUserService;

    @Autowired
    private ICtRoleService ctRoleService;

    @Autowired
    private ICtOrderService ctOrderService;

    @Autowired
    private ILgLoginService lgLoginService;

    @Autowired
    private ICtCallbackService ctCallbackService;

    @KafkaListener(topics = KafkaTopicConstant.TOPIC_EVENT_PARSE)
    public void onMessage(ConsumerRecord<String, Object> record) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (KafkaKeyConstant.GROUP_SDK_START.equals(record.key())) {
            ParseStartDto parseStartDto = objectMapper.convertValue(record.value(),
                ParseStartDto.class);
            parseStart(parseStartDto);
        } else if (KafkaKeyConstant.GROUP_SDK_LOGIN.equals(record.key())) {
            ParseLoginDto parseLoginDto = objectMapper.convertValue(record.value(),
                ParseLoginDto.class);
            parseLogin(parseLoginDto);
        } else if (KafkaKeyConstant.GROUP_SDK_ALIVE.equals(record.key())) {
            ParseAliveDto parseAliveDto = objectMapper.convertValue(record.value(),
                ParseAliveDto.class);
            parseAlive(parseAliveDto);
        } else if (KafkaKeyConstant.GROUP_SDK_REGISTER.equals(record.key())) {
            ParseRegisterDto parseRegisterDto = objectMapper.convertValue(record.value(),
                ParseRegisterDto.class);
            parseRegister(parseRegisterDto);
        } else if (KafkaKeyConstant.GROUP_SDK_PAY.equals(record.key())) {
            ParsePayDto parsePayDto = objectMapper.convertValue(record.value(), ParsePayDto.class);
            parsePay(parsePayDto);
        }
    }

    /**
     * @param parseStartDto
     * @author chenyw
     * @description 解析激活
     * @date 13:56 2023/4/6/006
     **/
    public void parseStart(ParseStartDto parseStartDto) {

        CtDevice ctDevice = ctDeviceService.searchStartDevice(parseStartDto);

        ctDailyService.updateStartDaily(parseStartDto, ctDevice);

        ctHourService.updateStartHour(parseStartDto, ctDevice);

        if(ctDevice.getStartupTime() == null ){
            // 第一次start的设备 上报激活
            ctCallbackService.parseStartCallback(parseStartDto);
        }

            //TODO 快手活跃数据回传

        //TODO 控量操作
    }

    /**
     * @param parseLoginDto
     * @author chenyw
     * @description 解析登录
     * @date 13:56 2023/4/6/006
     **/
    public void parseLogin(ParseLoginDto parseLoginDto) {
        //获取设备,更新设备中的登录时间
        CtDevice resultDevice = ctDeviceService.searchLoginDevice(parseLoginDto);

        //获取用户
        CtUser resultUser = ctUserService.searchLoginUser(parseLoginDto);

        ctDailyService.updateLoginDaily(parseLoginDto, resultDevice, resultUser);
        if (ObjectUtils.isEmpty(resultUser.getLoginTime())) {

            if (ObjectUtils.isEmpty(resultDevice.getLoginTime())) {
                // TODO 上传
                ctCallbackService.parseRegisterCallback(parseLoginDto);
            }

            // TODO 回传
        } else {
            ctHourService.updateLogin(parseLoginDto, resultUser);
            ctUserService.updateMask(parseLoginDto, resultUser);
        }
        // TODO 控量操作
        // TODO 记录登录日志
        lgLoginService.insertLoginLog(parseLoginDto,resultUser,resultDevice);
    }

    /**
     * @param parseRegisterDto
     * @author chenyw
     * @description 解析注册
     * @date 17:32 2023/4/23/023
     **/
    public void parseRegister(ParseRegisterDto parseRegisterDto) {

        ctDeviceService.searchRegisterDevice(parseRegisterDto);
    }

    /**
     * @param parseAliveDto
     * @author chenyw
     * @description 解析上报
     * @date 17:32 2023/4/23/023
     **/
    public void parseAlive(ParseAliveDto parseAliveDto) {

        CtDevice ctDevice = ctDeviceService.searchAliveDevice(parseAliveDto);
        CtUser ctUser = ctUserService.searchAliveUser(parseAliveDto);

        ctDailyService.updateAliveDaily(parseAliveDto, ctDevice, ctUser);
        if (ObjectUtils.isEmpty(parseAliveDto.getRoleId()) || ObjectUtils.isEmpty(
            parseAliveDto.getServerId()) || ObjectUtils.isEmpty(parseAliveDto.getUserId())) {
            return;
        }

        ctRoleService.searchAliveRole(parseAliveDto);
    }

    /**
     * @param parsePayDto
     * @author chenyw
     * @description 解析支付
     * @date 17:32 2023/4/23/023
     **/
    public void parsePay(ParsePayDto parsePayDto) {
        // 1、查询是否已经统计过订单 统计过不记录
        CtOrder ctOrder = ctOrderService.getCtOrder(parsePayDto.getDealId(),
            parsePayDto.getOrderId());
        if (ctOrder != null) {
            // 如果已存在订单记录则不记录
            log.info("解析支付日志,订单已存在:{}", ctOrder.getOrderId());
            return;
        }
        // 2、更新设备信息
        CtDevice ctDevice = ctDeviceService.parsePayDevice(parsePayDto);
        // 3、更新用户信息
        CtUser ctUser = ctUserService.parsePayUser(parsePayDto);
        // 4、更新角色信息
        CtRole ctRole = ctRoleService.parsePayRole(parsePayDto);
        // 5、更新统计信息
        ctDailyService.parsePayDaily(parsePayDto, ctDevice, ctUser);
        // 6、更新订单信息
        ctOrderService.parsePayOrder(parsePayDto, ctRole, ctDevice, ctUser);
        // 7、推送至callback表
        ctCallbackService.parsePayCallback(parsePayDto);
    }
}
