package org.jeecg.modules.users.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.advert.vo.OpDealModel;
import org.jeecg.common.constant.KafkaKeyConstant;
import org.jeecg.common.constant.KafkaTopicConstant;
import org.jeecg.common.constant.PkgParentConstant;
import org.jeecg.common.constant.SwitchConstant;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.common.game.api.IAdvertApi;
import org.jeecg.common.game.api.IGameApi;
import org.jeecg.common.game.vo.OpGameModel;
import org.jeecg.common.game.vo.OpPkgModel;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.kafka.dto.ParseAliveDto;
import org.jeecg.common.kafka.dto.ParseLoginDto;
import org.jeecg.common.kafka.dto.ParseRegisterDto;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.DySmsEnum;
import org.jeecg.common.util.DySmsHelper;
import org.jeecg.common.util.Md5Util;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.SdkConfig;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.modules.users.bo.CheckAdultData;
import org.jeecg.modules.users.bo.SessionInfo;
import org.jeecg.modules.users.constant.CloseTypeConstant;
import org.jeecg.modules.users.constant.LimitCrossLoginConstant;
import org.jeecg.modules.users.constant.OnlineBtConstant;
import org.jeecg.modules.users.constant.OperateTypeConstant;
import org.jeecg.modules.users.constant.RegisterLimitSwitchConstant;
import org.jeecg.modules.users.constant.RegisterLoginTypeConstant;
import org.jeecg.modules.users.constant.RuleTypeConstant;
import org.jeecg.modules.users.constant.UserTypeConstant;
import org.jeecg.modules.users.dto.SdkAliveDto;
import org.jeecg.modules.users.dto.SdkLoginDto;
import org.jeecg.modules.users.dto.SdkLogoutDto;
import org.jeecg.modules.users.dto.SdkRegisterDto;
import org.jeecg.modules.users.dto.SdkSendCodeDto;
import org.jeecg.modules.users.dto.SdkUpdateDto;
import org.jeecg.modules.users.dto.SdkVerifyDto;
import org.jeecg.modules.users.entity.OpRegisterLoginSwitch;
import org.jeecg.modules.users.entity.OpRole;
import org.jeecg.modules.users.entity.OpUser;
import org.jeecg.modules.users.entity.OpUserOnline;
import org.jeecg.modules.users.entity.OpUserRealnameInfo;
import org.jeecg.modules.users.entity.OpUserRealnamedGame;
import org.jeecg.modules.users.mapper.OpUserOnlineMapper;
import org.jeecg.modules.users.service.IOpRegisterLoginSwitchService;
import org.jeecg.modules.users.service.IOpUserOnlineService;
import org.jeecg.modules.users.service.IOpUserRealnameInfoService;
import org.jeecg.modules.users.service.IOpUserRealnamedGameService;
import org.jeecg.modules.users.service.IOpUserService;
import org.jeecg.modules.users.service.SdkUserService;
import org.jeecg.modules.users.util.RedisUtils;
import org.jeecg.modules.users.util.RegularUtils;
import org.jeecg.modules.users.vo.SdkUserLoginRes;
import org.jeecg.modules.users.vo.SdkUserRegisterRes;
import org.jeecg.modules.users.vo.SdkUserUpdateRes;
import org.jeecg.modules.users.vo.SdkVerifyRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@DS("open_gateway")
public class SdkUserServiceImpl implements SdkUserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IOpUserService opUserService;
    @Autowired
    private IOpUserOnlineService opUserOnlineService;
    @Autowired
    private OpUserOnlineMapper opUserOnlineMapper;
    @Autowired
    private IOpUserRealnameInfoService opUserRealnameInfoService;
    @Autowired
    private IOpRegisterLoginSwitchService opRegisterLoginSwitchService;
    @Autowired
    private IOpUserRealnamedGameService opUserRealnamedGameService;
    @Autowired
    private SdkConfig sdkConfig;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private IAdvertApi advertApi;
    @Autowired
    private IGameApi gameApi;

    // 单日 注册设备最大限制
    private final static Integer REGISTER_LIMIT_DEVICE = 10;
    // 单日 注册ip最大限制
    private final static Integer REGISTER_LIMIT_IP = 10;
    // 注册限制过期时间(天)
    private final static Integer REGISTER_LIMIT_TIME_OUT = 1;
    // 验证码 十分钟内获取最大限制
    private final static Integer CODE_LIMIT = 5;
    // 验证码限制过期时间(分钟)
    private final static Integer CODE_LIMIT_TIME_OUT = 10;
    // 验证码超时时间(分钟)
    private final static Integer CODE_TIME_OUT = 3;
    // session过期时间
    private final static Integer SESSION_TIME_OUT = 6000;

    @Override
    public SdkUserRegisterRes register(SdkRegisterDto sdkRegisterDto) {
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        SdkUserRegisterRes sdkUserRegisterRes = new SdkUserRegisterRes();
        log.info("sdk注册参数:{}", sdkRegisterDto);
        Date date = new Date();
        String dateStr = DateUtils.yyyyMMdd.get().format(date);
        // 1、校验注册
        verifyRegister(sdkRegisterDto, sdkInfo, dateStr);
        // 2、注册
        OpUser insert = new OpUser();
        insert.setUserName(sdkRegisterDto.getUserName())
            .setUserPassword(sdkRegisterDto.getUserPassword())
            .setUserType(UserTypeConstant.USER_TYPE_SDK).setUserTagDeal(sdkRegisterDto.getDealId())
            .setUserTagSubGame(sdkRegisterDto.getSubGameId())
            .setUserTagPkg(sdkRegisterDto.getPkgId()).setSignupIp(sdkInfo.getIp())
            .setSignupSource(sdkRegisterDto.getSubGameId().toString())
            .setSignupTime(date).setUserPhone(sdkRegisterDto.getUserPhone());
        opUserService.save(insert);
        sdkUserRegisterRes.setUserId(insert.getId());
        // 3、新增注册限制（同一个ip、设备号的注册限制）
        addRegisterLimit(sdkRegisterDto.getDevice(), sdkInfo.getIp(), dateStr);
        OpDealModel opDeal = advertApi.getOpDeal(sdkRegisterDto.getDealId());
        ParseRegisterDto parseRegisterDto = new ParseRegisterDto();
        parseRegisterDto.setChannelId(opDeal.getChannelId() == null ? 0 : opDeal.getChannelId());
        parseRegisterDto.setChannelSubAccountId(
            opDeal.getChannelSubAccountId() == null ? 0 : opDeal.getChannelSubAccountId());
        parseRegisterDto.setChannelTypeId(
            opDeal.getChannelTypeId() == null ? 0 : opDeal.getChannelTypeId());
        parseRegisterDto.setGameId(sdkInfo.getOpSubGameModel().getGameId());
        parseRegisterDto.setDealId(sdkRegisterDto.getDealId());
        parseRegisterDto.setPkgId(sdkRegisterDto.getPkgId());
        parseRegisterDto.setSubGameId(sdkRegisterDto.getSubGameId());

        //转为毫秒为单位的时间戳
        long time = System.currentTimeMillis();
        parseRegisterDto.setTime(time);
        parseRegisterDto.setUniqueId(sdkRegisterDto.getDevice());
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_EVENT_PARSE,
            KafkaKeyConstant.GROUP_SDK_REGISTER, parseRegisterDto);
        return sdkUserRegisterRes;
    }

    @Override
    public void sendCode(SdkSendCodeDto sdkSendCodeDto) {
        // 1、校验
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        log.info("sdk 发送短信验证码参数:{}", sdkSendCodeDto);
        verifySendCode(sdkSendCodeDto, sdkInfo);
        // 2、生成验证码
        String code = RandomUtil.randomNumbers(5);
        JSONObject obj = new JSONObject();
        obj.put("code", code);
        boolean codeResult = false;
        try {
            codeResult = DySmsHelper.sendSms(sdkSendCodeDto.getUserPhone(), obj,
                DySmsEnum.SDK_REGISTER_TEMPLATE_CODE);
        } catch (ClientException e) {
            log.error("短信接口未配置，请联系管理员");
            throw new IdeaRunTimeException(ErrorCode.SMS_SEND_FAILED);
        }
        if (codeResult) {
            // 将短信验证码放到缓存里
            stringRedisTemplate.opsForValue()
                .set(RedisUtils.getCodeKey(sdkSendCodeDto.getUserPhone()), code, CODE_TIME_OUT,
                    TimeUnit.DAYS);
        } else {
            throw new IdeaRunTimeException(ErrorCode.SMS_SEND_FAILED);
        }
        // 3、新增验证码获取限制
        addSendCodeLimit(sdkSendCodeDto.getDevice(), sdkInfo.getIp(), sdkInfo.getIp());
    }

    @Override
    public SdkUserLoginRes login(SdkLoginDto sdkLoginDto) {
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        log.info("sdk 登录参数:{}", sdkLoginDto);
        // 1、获取用户信息 先根据用户名查 再根据手机号查
        OpUser opUser = opUserService.getOpUserByUserName(sdkLoginDto.getName());
        if (null == opUser) {
            opUser = opUserService.getOpUserByUserPhone(sdkLoginDto.getName());
        }
        // 2、校验用户信息
        verifyLogin(sdkLoginDto, sdkInfo, opUser);
        // 3、更新用户登录数据
        OpUser update = new OpUser();
        update.setId(opUser.getId()).setSigninIp(sdkInfo.getIp())
            .setSigninDevice(sdkLoginDto.getDevice())
            .setSigninSource(sdkLoginDto.getSubGameId().toString()).setSigninTime(new Date());
        opUserService.updateOpUserById(update);
        // 4、获取session用于回调
        SessionInfo sessionInfo = new SessionInfo();
        Long time = System.currentTimeMillis() / 1000;
        String str =
            sdkLoginDto.getSubGameId() + sdkLoginDto.getPkgId() + opUser.getId() + time
                + sdkConfig.getLoginSessionSalt()
                + sdkInfo.getOpSubGameModel().getGameKey();
        String sign = Md5Util.md5Encode(str, "utf-8");
        sessionInfo.setSubGameId(sdkLoginDto.getSubGameId()).setPkgId(sdkLoginDto.getPkgId())
            .setUserId(opUser.getId()).setTime(time)
            .setSign(sign);
        String sessionId = Base64.getEncoder()
            .encodeToString(JSONObject.toJSONString(sessionInfo).getBytes());
        //5、 查询实名信息
        OpUserRealnameInfo opUserRealnamedGame = opUserRealnameInfoService.getOpUserRealnameInfoByUserId(
            opUser.getId());
        // 实名认证开关
        CheckAdultData checkAdultData = new CheckAdultData();
        // 如果已实名 上报中宣部
        if (opUserRealnamedGame != null) {
            if (PkgParentConstant.DEFAULT_PKG_ID.equals(sdkLoginDto.getPkgId())) {
                if (SwitchConstant.OPEN.equals(sdkInfo.getOpPkgModel().getIdAuthSwitch())
                    && SwitchConstant.OPEN.equals(sdkInfo.getOpPkgModel().getAntiIndulgeSwitch())) {
                    // 上报中宣部
                    opUserOnlineService.saveOpUserOnline(sdkLoginDto.getSubGameId(),
                        sdkLoginDto.getPkgId(), opUser.getId(),
                        OnlineBtConstant.ON_LINE,
                        sdkInfo.getOpPkgModel().getOfficialBizId(), sdkLoginDto.getDevice(),
                        opUserRealnamedGame.getRealPi());
                }
            } else if (SwitchConstant.OPEN.equals(sdkInfo.getOpSubGameModel().getIdAuthSwitch())
                && SwitchConstant.OPEN.equals(sdkInfo.getOpSubGameModel().getAntiIndulgeSwitch())) {
                // 上报中宣部
                opUserOnlineService.saveOpUserOnline(sdkLoginDto.getSubGameId(),
                    sdkLoginDto.getPkgId(), opUser.getId(),
                    OnlineBtConstant.ON_LINE,
                    sdkInfo.getOpSubGameModel().getOfficialBizId(), sdkLoginDto.getDevice(),
                    opUserRealnamedGame.getRealPi());
            }

            checkAdultData = opUserOnlineService.checkAdult(opUserRealnamedGame.getRealNumber());
        }
        SdkUserLoginRes result = new SdkUserLoginRes();
        result.setUserId(opUser.getId());
        result.setUserPhone(opUser.getUserPhone());
        result.setUserName(opUser.getUserName());
        result.setSessionId(sessionId);
        result.setIsBindPhone(StringUtils.isNotBlank(opUser.getUserPhone()) ? SwitchConstant.OPEN
            : SwitchConstant.CLOSE);
        result.setIsIdAuth(
            opUserRealnamedGame != null ? SwitchConstant.OPEN : SwitchConstant.CLOSE);
        result.setIsAdult(checkAdultData.getIsAdult());
        result.setRemainingTime(checkAdultData.getRemainingTime());
        if(PkgParentConstant.DEFAULT_PKG_ID.equals(sdkLoginDto.getPkgId())){
            result.setAntiIndulgeSwitch(sdkInfo.getOpSubGameModel().getAntiIndulgeSwitch());
            result.setIsPhonePop(sdkInfo.getOpSubGameModel().getPhoneSwitch());
            result.setIdAuthSwitch(sdkInfo.getOpSubGameModel().getIdAuthSwitch());
        }else{
            result.setAntiIndulgeSwitch(sdkInfo.getOpPkgModel().getAntiIndulgeSwitch());
            result.setIsPhonePop(sdkInfo.getOpPkgModel().getPhoneSwitch());
            result.setIdAuthSwitch(sdkInfo.getOpPkgModel().getIdAuthSwitch());
        }
        // 登录成功 向队列发送数据
        OpDealModel opDeal = advertApi.getOpDeal(sdkLoginDto.getDealId());
        ParseLoginDto parseLoginDto = new ParseLoginDto();
        parseLoginDto.setChannelId(opDeal.getChannelId() == null ? 0 : opDeal.getChannelId());
        parseLoginDto.setChannelSubAccountId(
            opDeal.getChannelSubAccountId() == null ? 0 : opDeal.getChannelSubAccountId());
        parseLoginDto.setChannelTypeId(
            opDeal.getChannelTypeId() == null ? 0 : opDeal.getChannelTypeId());
        parseLoginDto.setGameId(sdkInfo.getOpSubGameModel().getGameId());
        parseLoginDto.setUniqueId(sdkLoginDto.getDevice());
        parseLoginDto.setDealId(sdkLoginDto.getDealId());
        parseLoginDto.setClientIp(sdkInfo.getIp());
        parseLoginDto.setTime(System.currentTimeMillis());
        parseLoginDto.setSubGameId(sdkLoginDto.getSubGameId());
        parseLoginDto.setUserId(opUser.getId());
        parseLoginDto.setPkgId(sdkLoginDto.getPkgId());
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_EVENT_PARSE, KafkaKeyConstant.GROUP_SDK_LOGIN,
            parseLoginDto);
        return result;
    }

    @Override
    public SdkUserUpdateRes update(SdkUpdateDto sdkUpdateDto) {
        log.info("sdk update参数: {}", sdkUpdateDto);
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        // 1、获取用户信息
        OpUser opUser;
        if (OperateTypeConstant.UPDATE_PASSWORD.equals(sdkUpdateDto.getUpdateType())
            || OperateTypeConstant.BIND_PHONE.equals(sdkUpdateDto.getUpdateType())) {
            opUser = opUserService.getOpUserByUserName(sdkUpdateDto.getUserName());
        } else {
            opUser = opUserService.getOpUserByUserPhone(sdkUpdateDto.getUserPhone());
        }
        // 2、校验
        verifyUpdate(sdkUpdateDto, opUser, sdkInfo);
        // 3、更新用户信息
        OpUser update = new OpUser();
        update.setId(opUser.getId());
        if (OperateTypeConstant.UPDATE_PASSWORD.equals(sdkUpdateDto.getUpdateType())
            || OperateTypeConstant.RESET_PASSWD.equals(sdkUpdateDto.getUpdateType())) {
            update.setUserPassword(sdkUpdateDto.getUserPassword());
        } else if (OperateTypeConstant.BIND_PHONE.equals(sdkUpdateDto.getUpdateType())) {
            update.setUserPhone(sdkUpdateDto.getUserPhone());
        } else {
            update.setUserPhone("");
        }
        opUserService.updateOpUserById(update);
        SdkUserUpdateRes result = new SdkUserUpdateRes();
        result.setUpdateType(sdkUpdateDto.getUpdateType());
        result.setUserPhone(sdkUpdateDto.getUserPhone());
        result.setUserName(opUser.getUserName());
        return result;
    }

    @Override
    public void logoutGame(SdkLogoutDto sdkLogoutDto) {
        // 上报登出
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        OpUserRealnameInfo opUserRealnamedGame = opUserRealnameInfoService.getOpUserRealnameInfoByUserId(
            sdkLogoutDto.getUserId());
        if (null != opUserRealnamedGame) {
            if (oConvertUtils.isNotEmpty(sdkInfo.getOpPkgModel())) {
                if (SwitchConstant.OPEN.equals(
                    sdkInfo.getOpPkgModel().getAntiIndulgeSwitch())) {
                    opUserOnlineService.saveOpUserOnline(sdkLogoutDto.getSubGameId(),
                        sdkLogoutDto.getPkgId(),
                        sdkLogoutDto.getUserId(),
                        OnlineBtConstant.OFF_LINE, sdkInfo.getOpPkgModel().getOfficialBizId(),
                        sdkLogoutDto.getDevice(), opUserRealnamedGame.getRealPi());
                }
            } else {
                if (SwitchConstant.OPEN.equals(
                    sdkInfo.getOpSubGameModel().getAntiIndulgeSwitch())) {
                    opUserOnlineService.saveOpUserOnline(sdkLogoutDto.getSubGameId(),
                        sdkLogoutDto.getPkgId(),
                        sdkLogoutDto.getUserId(),
                        OnlineBtConstant.OFF_LINE, sdkInfo.getOpSubGameModel().getOfficialBizId(),
                        sdkLogoutDto.getDevice(), opUserRealnamedGame.getRealPi());
                }
            }
        }
    }

    @Override
    public void alive(SdkAliveDto sdkAliveDto) {
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        if(null == sdkAliveDto.getPkgId()){
            // 如果pkgId为空不执行
            return;
        }
        // 2、入库
        //开启实名验证
        QueryWrapper<OpUserOnline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", sdkAliveDto.getUserId())
            .eq("sub_game_id", sdkAliveDto.getSubGameId())
            .eq("pkg_id", sdkAliveDto.getPkgId());
        List<OpUserOnline> list = opUserOnlineService.list(queryWrapper);
        if (oConvertUtils.isNotEmpty(sdkInfo.getOpPkgModel())) {
            if (SwitchConstant.OPEN.equals(sdkInfo.getOpPkgModel().getOfficialAntiIndulgeSwitch())
                && oConvertUtils.isNotEmpty(sdkInfo.getOpPkgModel().getOfficialBizId())) {
                if (list != null && !list.isEmpty()) {
                    opUserOnlineMapper.updateLogoutTime(sdkAliveDto.getUserId(),
                        sdkAliveDto.getSubGameId(), sdkAliveDto.getPkgId(),
                        LocalDateTime.now().plusSeconds(400));
                }
            }
        } else if (
            SwitchConstant.OPEN.equals(sdkInfo.getOpSubGameModel().getOfficialAntiIndulgeSwitch())
                && oConvertUtils.isNotEmpty(sdkInfo.getOpSubGameModel().getOfficialBizId())) {
            if (list != null && !list.isEmpty()) {
                opUserOnlineMapper.updateLogoutTime(sdkAliveDto.getUserId(),
                    sdkAliveDto.getSubGameId(),
                    sdkAliveDto.getPkgId(),
                    LocalDateTime.now().plusSeconds(400));
            }
        }
        OpSubGameModel opSubGameModel = gameApi.getOpSubGame(sdkAliveDto.getSubGameId());
        OpDealModel opDeal = advertApi.getOpDeal(sdkAliveDto.getDealId());
        ParseAliveDto parseAliveDto = new ParseAliveDto();
        parseAliveDto.setChannelId(opDeal.getChannelId() == null ? 0 : opDeal.getChannelId());
        parseAliveDto.setChannelSubAccountId(
            opDeal.getChannelSubAccountId() == null ? 0 : opDeal.getChannelSubAccountId());
        parseAliveDto.setChannelTypeId(
            opDeal.getChannelTypeId() == null ? 0 : opDeal.getChannelTypeId());
        parseAliveDto.setGameId(opSubGameModel.getGameId());
        parseAliveDto.setUniqueId(sdkAliveDto.getDevice());
        parseAliveDto.setDealId(sdkAliveDto.getDealId());
        parseAliveDto.setClientIp(sdkInfo.getIp());
        parseAliveDto.setRoleName(sdkAliveDto.getRoleName());
        parseAliveDto.setRoleId(sdkAliveDto.getRoleId());
        parseAliveDto.setRoleLevel(sdkAliveDto.getRoleLevel());
        parseAliveDto.setServerId(sdkAliveDto.getServerId());
        parseAliveDto.setServerName(sdkAliveDto.getServerName());
        parseAliveDto.setTime(System.currentTimeMillis());
        parseAliveDto.setSubGameId(sdkAliveDto.getSubGameId());
        parseAliveDto.setUserId(sdkAliveDto.getUserId());
        parseAliveDto.setPkgId(sdkAliveDto.getPkgId());
        kafkaTemplate.send(KafkaTopicConstant.TOPIC_EVENT_PARSE, KafkaKeyConstant.GROUP_SDK_ALIVE,
            parseAliveDto);
    }

    @Override
    public SdkVerifyRes verify(SdkVerifyDto sdkVerifyDto) {
        log.info("verify参数:{}", sdkVerifyDto);
        String sessionStr = new String(Base64.getDecoder().decode(sdkVerifyDto.getSessionId()));
        SessionInfo session = JSONObject.parseObject(sessionStr, SessionInfo.class);
        if (null == session) {
            throw new IdeaRunTimeException(ErrorCode.SDK_USER_EMPTY_SESSION);
        }
        if (!Objects.equals(sdkVerifyDto.getSubGameId(), session.getSubGameId())) {
            throw new IdeaRunTimeException(ErrorCode.SDK_USER_INVALID_APP_ID);
        }
        if (!Objects.equals(sdkVerifyDto.getUserId(), session.getUserId())) {
            throw new IdeaRunTimeException(ErrorCode.SDK_USER_ID_ERROR);
        }
        OpUser opUser = opUserService.getOpUserById(sdkVerifyDto.getUserId());
        if (null == opUser) {
            throw new IdeaRunTimeException(ErrorCode.SDK_USER_DOES_NOT_EXIST);
        }
        // session有效期为10分钟
        if (session.getTime() > System.currentTimeMillis() / 1000 + SESSION_TIME_OUT) {
            throw new IdeaRunTimeException(ErrorCode.SDK_USER_SESSION_TIMEOUT);
        }
        // 查询身份证号
        OpUserRealnamedGame opUserRealnamedGame = opUserRealnamedGameService.getOpUserRealnamedGameByUserIdAndPkgId(
            sdkVerifyDto.getUserId(), sdkVerifyDto.getPkgId());
        SdkVerifyRes sdkVerifyRes = new SdkVerifyRes();
        sdkVerifyRes.setRealNumber(opUserRealnamedGame.getRealNumber());
        sdkVerifyRes.setUserPhone(opUser.getUserPhone());
        return sdkVerifyRes;
    }

    /**
     * @param sdkUpdateDto
     * @param opUser
     * @author chenyw
     * @description 校验更新
     * @date 20:31 2022/12/21/021
     **/
    private void verifyUpdate(SdkUpdateDto sdkUpdateDto, OpUser opUser, SdkInfo sdkInfo) {
        if (OperateTypeConstant.UPDATE_PASSWORD.equals(sdkUpdateDto.getUpdateType())) {
            if (null == opUser) {
                throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SDK_USER);
            }
            if (sdkUpdateDto.getUserPassword() == null
                || sdkUpdateDto.getUserPassword().length() != 32) {
                throw new IdeaRunTimeException(ErrorCode.PASSWORD_ILLEGAL);
            }
            // 校验密码
            verifyToken(sdkUpdateDto.getSubGameId(), sdkInfo.getOpSubGameModel().getGameKey(),
                opUser.getUserPassword(), sdkUpdateDto.getTime(), sdkUpdateDto.getToken());
        } else if (OperateTypeConstant.RESET_PASSWD.equals(sdkUpdateDto.getUpdateType())) {
            if (null == opUser) {
                throw new IdeaRunTimeException(ErrorCode.SDK_USER_NO_BIND_PHONE);
            }
            if (StringUtils.isEmpty(sdkUpdateDto.getCode())) {
                throw new IdeaRunTimeException(ErrorCode.SMS_CODE_EMPTY);
            }
            if (!RegularUtils.checkPhone(sdkUpdateDto.getUserPhone())) {
                throw new IdeaRunTimeException(ErrorCode.PHONE_ILLEGAL);
            }
            if (sdkUpdateDto.getUserPassword() == null
                || sdkUpdateDto.getUserPassword().length() != 32) {
                throw new IdeaRunTimeException(ErrorCode.PASSWORD_ILLEGAL);
            }
            verifySmsCode(sdkUpdateDto.getUserPhone(), sdkUpdateDto.getCode());
        } else if (OperateTypeConstant.BIND_PHONE.equals(sdkUpdateDto.getUpdateType())) {
            if (null == opUser) {
                throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SDK_USER);
            }
            if (StringUtils.isEmpty(sdkUpdateDto.getCode())) {
                throw new IdeaRunTimeException(ErrorCode.SMS_CODE_EMPTY);
            }
            if (!RegularUtils.checkPhone(sdkUpdateDto.getUserPhone())) {
                throw new IdeaRunTimeException(ErrorCode.PHONE_ILLEGAL);
            }
            OpUser phoneUser = opUserService.getOpUserByUserPhone(sdkUpdateDto.getUserPhone());
            if (null != phoneUser) {
                throw new IdeaRunTimeException(ErrorCode.SDK_USER_PHONE_ALREADY_EXIT);
            }
            verifySmsCode(sdkUpdateDto.getUserPhone(), sdkUpdateDto.getCode());
        } else if (OperateTypeConstant.UNBIND_PHONE.equals(sdkUpdateDto.getUpdateType())) {
            if (null == opUser) {
                throw new IdeaRunTimeException(ErrorCode.SDK_USER_NO_BIND_PHONE);
            }
            if (StringUtils.isEmpty(sdkUpdateDto.getCode())) {
                throw new IdeaRunTimeException(ErrorCode.SMS_CODE_EMPTY);
            }
            if (!RegularUtils.checkPhone(sdkUpdateDto.getUserPhone())) {
                throw new IdeaRunTimeException(ErrorCode.PHONE_ILLEGAL);
            }
            verifySmsCode(sdkUpdateDto.getUserPhone(), sdkUpdateDto.getCode());
        } else {
            // 未知的更新类型
            throw new IdeaRunTimeException(ErrorCode.SDK_UPDATE_UNKNOWN_TYPE);
        }
    }

    /**
     * @param sdkLoginDto
     * @param sdkInfo
     * @param opUser
     * @author chenyw
     * @description 校验登录
     * @date 14:56 2022/12/21/021
     **/
    private void verifyLogin(SdkLoginDto sdkLoginDto, SdkInfo sdkInfo, OpUser opUser) {
        if (null == opUser) {
            throw new IdeaRunTimeException(ErrorCode.NO_FOUND_SDK_USER);
        }
        // 校验密码
        verifyToken(sdkLoginDto.getSubGameId(), sdkInfo.getOpSubGameModel().getGameKey(),
            opUser.getUserPassword(), sdkLoginDto.getTime(), sdkLoginDto.getToken());
        // 校验平台类型
        if (!UserTypeConstant.USER_TYPE_SDK.equals(opUser.getUserType())) {
            throw new IdeaRunTimeException(ErrorCode.SDK_TYPE_FAULT);
        }
        // 验证 loginSwitch
        List<OpRegisterLoginSwitch> opRegisterLoginSwitchList = opRegisterLoginSwitchService.getAllSwitchList();
        for (OpRegisterLoginSwitch opRegisterLoginSwitch : opRegisterLoginSwitchList) {
            if ((RuleTypeConstant.GAME.equals(opRegisterLoginSwitch.getRuleType())
                && Objects.equals(sdkInfo.getOpSubGameModel().getGameId(),
                opRegisterLoginSwitch.getRuleId())) ||
                (RuleTypeConstant.SUB_GAME.equals(opRegisterLoginSwitch.getRuleType())
                    && Objects.equals(sdkLoginDto.getSubGameId(),
                    opRegisterLoginSwitch.getRuleId())) ||
                (RuleTypeConstant.DEAL.equals(opRegisterLoginSwitch.getRuleType())
                    && Objects.equals(sdkLoginDto.getDealId(), opRegisterLoginSwitch.getRuleId()))
            ) {
                if (CloseTypeConstant.ALL_CLOSE.equals(opRegisterLoginSwitch.getLoginCloseType())) {
                    throw new IdeaRunTimeException(ErrorCode.GAME_IS_CLOSED);
                } else if (CloseTypeConstant.CONDITION_CLOSE.equals(
                    opRegisterLoginSwitch.getLoginCloseType())) {
                    if (StringUtils.isNotEmpty(opRegisterLoginSwitch.getLoginLimitIp())) {
                        String[] loginLimitIpArr = opRegisterLoginSwitch.getLoginLimitIp()
                            .split("\\r?\\n");
                        if (Arrays.asList(loginLimitIpArr).contains(sdkInfo.getIp())) {
                            throw new IdeaRunTimeException(ErrorCode.GAME_IS_CLOSED);
                        }
                    } else if (StringUtils.isNotEmpty(
                        opRegisterLoginSwitch.getLoginLimitDevice())) {
                        String[] loginLimitDeviceArr = opRegisterLoginSwitch.getLoginLimitDevice()
                            .split("\\r?\\n");
                        if (Arrays.asList(loginLimitDeviceArr).contains(sdkLoginDto.getDevice())) {
                            throw new IdeaRunTimeException(ErrorCode.GAME_IS_CLOSED);
                        }
                    } else if (StringUtils.isNotEmpty(
                        opRegisterLoginSwitch.getLoginLimitUserId())) {
                        String[] loginLimitUserIdArr = opRegisterLoginSwitch.getLoginLimitUserId()
                            .split("\\r?\\n");
                        if (Arrays.asList(loginLimitUserIdArr)
                            .contains(opUser.getId().toString())) {
                            throw new IdeaRunTimeException(ErrorCode.GAME_IS_CLOSED);
                        }
                    }
                }
                if (LimitCrossLoginConstant.OPEN.equals(
                    opRegisterLoginSwitch.getLimitCrossLoginSwitch()) && !Objects.equals(
                    opUser.getUserTagSubGame(), sdkLoginDto.getSubGameId())) {
                    throw new IdeaRunTimeException(ErrorCode.LIMIT_CROSS_LOGIN);
                }
            }
        }
    }

    /**
     * @param sendCodeDto
     * @param sdkInfo
     * @author chenyw
     * @description 校验发送短信验证码
     * @date 20:55 2022/12/21/021
     **/
    private void verifySendCode(SdkSendCodeDto sendCodeDto, SdkInfo sdkInfo) {
        if (!RegularUtils.checkPhone(sendCodeDto.getUserPhone())) {
            throw new IdeaRunTimeException(ErrorCode.PHONE_ILLEGAL);
        }
        String deviceNumber = stringRedisTemplate.opsForValue()
            .get(RedisUtils.getCodeLimitDevice(sendCodeDto.getDevice()));
        String ipNumber = stringRedisTemplate.opsForValue()
            .get(RedisUtils.getCodeLimitIp(sdkInfo.getIp()));
        if (StringUtils.isNotEmpty(deviceNumber) && Integer.parseInt(deviceNumber) > CODE_LIMIT) {
            log.info("设备:{}获取验证码频繁", sendCodeDto.getDevice());
            throw new IdeaRunTimeException(ErrorCode.LIMIT_GET_CODE);
        }
        if (StringUtils.isNotEmpty(ipNumber) && Integer.parseInt(ipNumber) > CODE_LIMIT) {
            log.info("ip:{}获取验证码频繁", sdkInfo.getIp());
            throw new IdeaRunTimeException(ErrorCode.LIMIT_GET_CODE);
        }
    }

    /**
     * @param sdkRegisterDto
     * @param sdkInfo
     * @author chenyw
     * @description 注册校验
     * @date 11:14 2022/12/21/021
     **/
    private void verifyRegister(SdkRegisterDto sdkRegisterDto, SdkInfo sdkInfo, String dateStr) {
        // 注册设备和ip限制查询
        String deviceNumber = stringRedisTemplate.opsForValue()
            .get(RedisUtils.getRegisterLimitDeviceKey(sdkRegisterDto.getDevice(), dateStr));
        String ipNumber = stringRedisTemplate.opsForValue()
            .get(RedisUtils.getRegisterLimitIpKey(sdkInfo.getIp(), dateStr));
        if (StringUtils.isNotEmpty(ipNumber) && Integer.parseInt(ipNumber) > REGISTER_LIMIT_IP) {
            throw new IdeaRunTimeException(ErrorCode.LIMIT_REGISTER);
        }
        if (StringUtils.isNotEmpty(deviceNumber)
            && Integer.parseInt(deviceNumber) > REGISTER_LIMIT_DEVICE) {
            throw new IdeaRunTimeException(ErrorCode.LIMIT_REGISTER);
        }
        if (RegisterLoginTypeConstant.BY_USER_NAME.equals(sdkRegisterDto.getRegisterType())) {
            // 校验用户名密码
            if (!RegularUtils.checkUserName(sdkRegisterDto.getUserName())) {
                throw new IdeaRunTimeException(ErrorCode.USER_NAME_ILLEGAL);
            }
        } else {
            if (StringUtils.isEmpty(sdkRegisterDto.getUserPhone())) {
                throw new IdeaRunTimeException(ErrorCode.PHONE_CANT_BE_EMPTY);
            }
            if (!RegularUtils.checkPhone(sdkRegisterDto.getUserPhone())) {
                throw new IdeaRunTimeException(ErrorCode.PHONE_ILLEGAL);
            }
            // 校验手机验证码
            verifySmsCode(sdkRegisterDto.getUserPhone(), sdkRegisterDto.getCode());
        }
        // 校验密码
        if (!RegularUtils.checkPassword(sdkRegisterDto.getUserPassword())) {
            throw new IdeaRunTimeException(ErrorCode.PASSWD_ILLEGAL);
        }
        // 验证手机号是否已经存在
        if (StringUtils.isNotEmpty(sdkRegisterDto.getUserPhone())) {
            if (opUserService.getOpUserByUserPhone(sdkRegisterDto.getUserPhone()) != null) {
                throw new IdeaRunTimeException(ErrorCode.SDK_USER_PHONE_ALREADY_EXIT);
            }
        }
        // 验证用户名是否已经存在
        if (opUserService.getOpUserByUserName(sdkRegisterDto.getUserName()) != null) {
            throw new IdeaRunTimeException(ErrorCode.SDK_USER_ALREADY_EXIT);
        }
        // 验证 register_switch
        List<OpRegisterLoginSwitch> opRegisterLoginSwitchList = opRegisterLoginSwitchService.getAllSwitchList();
        for (OpRegisterLoginSwitch opRegisterLoginSwitch : opRegisterLoginSwitchList) {
            if ((RuleTypeConstant.GAME.equals(opRegisterLoginSwitch.getRuleType())
                && Objects.equals(sdkInfo.getOpSubGameModel().getGameId(),
                opRegisterLoginSwitch.getRuleId())) ||
                (RuleTypeConstant.SUB_GAME.equals(opRegisterLoginSwitch.getRuleType())
                    && Objects.equals(sdkRegisterDto.getSubGameId(),
                    opRegisterLoginSwitch.getRuleId())) ||
                (RuleTypeConstant.DEAL.equals(opRegisterLoginSwitch.getRuleType())
                    && Objects.equals(sdkRegisterDto.getDealId(),
                    opRegisterLoginSwitch.getRuleId()))
            ) {
                if (RegisterLoginTypeConstant.BY_USER_PHONE.equals(sdkRegisterDto.getRegisterType())
                    && RegisterLimitSwitchConstant.CLOSE_FAST.equals(
                    opRegisterLoginSwitch.getRegisterLimitSwitch())) {
                    // 不限制手机注册
                    continue;
                } else if (
                    RegisterLoginTypeConstant.BY_USER_NAME.equals(sdkRegisterDto.getRegisterType())
                        && RegisterLimitSwitchConstant.CLOSE_PHONE.equals(
                        opRegisterLoginSwitch.getRegisterLimitSwitch())) {
                    // 不限制快速注册
                    continue;
                }
                if (CloseTypeConstant.ALL_CLOSE.equals(
                    opRegisterLoginSwitch.getRegisterCloseType())) {
                    throw new IdeaRunTimeException(ErrorCode.GAME_IS_CLOSED);
                } else if (CloseTypeConstant.CONDITION_CLOSE.equals(
                    opRegisterLoginSwitch.getRegisterCloseType())) {
                    if (StringUtils.isNotEmpty(opRegisterLoginSwitch.getRegisterLimitIp())) {
                        // 根据ip限制
                        String[] registerLimitIpArr = opRegisterLoginSwitch.getRegisterLimitIp()
                            .split("\\r?\\n");
                        if (Arrays.asList(registerLimitIpArr).contains(sdkInfo.getIp())) {
                            throw new IdeaRunTimeException(ErrorCode.GAME_IS_CLOSED);
                        }
                    } else if (StringUtils.isNotEmpty(
                        opRegisterLoginSwitch.getRegisterLimitDevice())) {
                        // 根据设备号限制
                        String[] registerLimitDeviceArr = opRegisterLoginSwitch.getRegisterLimitDevice()
                            .split("\\r?\\n");
                        if (Arrays.asList(registerLimitDeviceArr)
                            .contains(sdkRegisterDto.getDevice())) {
                            throw new IdeaRunTimeException(ErrorCode.GAME_IS_CLOSED);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param phone 手机号
     * @param code  验证码
     * @author chenyw
     * @description 校验手机验证码
     * @date 15:05 2022/12/22/022
     **/
    private void verifySmsCode(String phone, String code) {
        // 校验手机验证码
        String realCode = stringRedisTemplate.opsForValue()
            .get(RedisUtils.getCodeKey(phone));
        if (!Objects.equals(realCode, code)) {
            throw new IdeaRunTimeException(ErrorCode.SMS_CODE_ILLEGAL);
        }
    }

    /**
     * @param subGameId   子游戏id
     * @param gameKey     游戏key
     * @param password    密码
     * @param time        时间
     * @param expectToken 期望token
     * @author chenyw
     * @description 校验密码
     * @date 15:26 2022/12/22/022
     **/
    private void verifyToken(Integer subGameId, String gameKey, String password, Long time,
        String expectToken) {
        // 校验密码
        String str = subGameId + gameKey + password + time;
        String token = Md5Util.md5Encode(str, "utf-8");
        if (!Objects.equals(token, expectToken)) {
            throw new IdeaRunTimeException(ErrorCode.PASSWORD_ERROR);
        }
    }

    /**
     * @param device
     * @param userName
     * @param ip
     * @return
     * @author chenyw
     * @description 增加注册ip设备号限制
     * @date 18:07 2022/12/15/015
     **/
    private void addRegisterLimit(String device, String userName, String ip) {
        String deviceKey = RedisUtils.getCodeLimitDevice(device);
        String userNameKey = RedisUtils.getCodeLimitUsername(userName);
        String ipKey = RedisUtils.getCodeLimitIp(ip);
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(deviceKey))) {
            stringRedisTemplate.opsForValue().increment(deviceKey, 1);
        } else {
            stringRedisTemplate.opsForValue()
                .set(deviceKey, "1", CODE_LIMIT_TIME_OUT, TimeUnit.MINUTES);
        }
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(userNameKey))) {
            stringRedisTemplate.opsForValue().increment(userNameKey, 1);
        } else {
            stringRedisTemplate.opsForValue()
                .set(userNameKey, "1", CODE_LIMIT_TIME_OUT, TimeUnit.MINUTES);
        }
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(ipKey))) {
            stringRedisTemplate.opsForValue().increment(ipKey, 1);
        } else {
            stringRedisTemplate.opsForValue()
                .set(ipKey, "1", CODE_LIMIT_TIME_OUT, TimeUnit.MINUTES);
        }
    }

    /**
     * addSendCodeLimit.
     *
     * @description 新增验证码获取限制
     * @author chenyw
     * @date 2022/12/13/013 15:49
     * @param: device
     * @param: ip
     * @param: dateStr
     */
    private void addSendCodeLimit(String device, String ip, String dateStr) {
        String deviceKey = RedisUtils.getRegisterLimitDeviceKey(device, dateStr);
        String ipKey = RedisUtils.getRegisterLimitIpKey(ip, dateStr);
        if (stringRedisTemplate.hasKey(deviceKey)) {
            stringRedisTemplate.opsForValue().increment(deviceKey, 1);
        } else {
            stringRedisTemplate.opsForValue()
                .set(deviceKey, "1", REGISTER_LIMIT_TIME_OUT, TimeUnit.DAYS);
        }
        if (stringRedisTemplate.hasKey(ipKey)) {
            stringRedisTemplate.opsForValue().increment(ipKey, 1);
        } else {
            stringRedisTemplate.opsForValue()
                .set(ipKey, "1", REGISTER_LIMIT_TIME_OUT, TimeUnit.DAYS);
        }
    }
}
