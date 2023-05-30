package org.jeecg.modules.system.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.common.constant.RedisConstant;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.common.util.Md5Util;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.modules.users.constant.OperateTypeConstant;
import org.jeecg.modules.users.constant.RegisterLoginTypeConstant;
import org.jeecg.modules.users.dto.SdkLoginDto;
import org.jeecg.modules.users.dto.SdkRegisterDto;
import org.jeecg.modules.users.dto.SdkUpdateDto;
import org.jeecg.modules.users.entity.OpUser;
import org.jeecg.modules.users.entity.OpUserRealnameInfo;
import org.jeecg.modules.users.service.IOpUserOnlineService;
import org.jeecg.modules.users.service.IOpUserRealnameInfoService;
import org.jeecg.modules.users.service.IOpUserService;
import org.jeecg.modules.users.service.impl.SdkUserServiceImpl;
import org.jeecg.modules.users.vo.SdkUserLoginRes;
import org.jeecg.modules.users.vo.SdkUserUpdateRes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;


/**
 * sdk对外接口单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
@SuppressWarnings({"FieldCanBeLocal", "SpringJavaAutowiredMembersInspection"})
@Slf4j
public class SdkUserTest {

    @InjectMocks
    @Autowired
    SdkUserServiceImpl sdkUserServiceImpl;
    @Mock
    private StringRedisTemplate stringRedisTemplate;
    @SpyBean
    private IOpUserService opUserService;
    @SpyBean
    private IOpUserOnlineService opUserOnlineService;
    @Spy
    private IOpUserRealnameInfoService opUserRealnameInfoService;

    @Before
    public void before() {
        // 初始化
        SdkInfo sdkInfo = new SdkInfo();
        OpSubGameModel opSubGameModel = new OpSubGameModel();
        sdkInfo.setIp("0.0.0.0");
        opSubGameModel.setGameId(1);
        opSubGameModel.setGameKey("abcdefg");
        sdkInfo.setOpSubGameModel(opSubGameModel);
        SdkContext.setSdkInfo(sdkInfo);
    }

    @Test
    public void testRegister() {
        SdkRegisterDto sdkRegisterDto = new SdkRegisterDto();
        sdkRegisterDto.setSubGameId(14109).setUserName("test12345").setUserPassword("1234567")
            .setDevice("20C808E3-AD23-7D32-7D01-23BA48A27E61").setDealId(123).setRegisterType(
                RegisterLoginTypeConstant.BY_USER_NAME);
        // mock新增方法
        doReturn(true).when(opUserService).save(any());
        doReturn(null).when(opUserService).getOpUserByUserName(any());
        doReturn(null).when(opUserService).getOpUserByUserPhone(any());
        // 1、验证用户名册
        sdkUserServiceImpl.register(sdkRegisterDto);
        sdkRegisterDto.setRegisterType(RegisterLoginTypeConstant.BY_USER_PHONE)
            .setUserPhone("13338268888").setCode("123456");
        // mock Redis
        ReflectionTestUtils.setField(sdkUserServiceImpl, "stringRedisTemplate",
            stringRedisTemplate);
        ValueOperations valueOperations = mock(ValueOperations.class);
        // mock获取验证码方法
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(
            RedisConstant.getCodeKey(sdkRegisterDto.getUserPhone()))).thenReturn(
            sdkRegisterDto.getCode());
        // 2、验证手机号注册
        sdkUserServiceImpl.register(sdkRegisterDto);
        log.info("验证注册成功");
    }

    @Test
    public void testLogin() {
        SdkInfo sdkInfo = SdkContext.getSdkInfo();
        OpUser opUser = new OpUser();
        opUser.setId(1);
        opUser.setUserType(0);
        opUser.setUserPhone("13626906034");
        opUser.setUserPassword("482a62ce8650b5db3e3e9cb9eec6e544");
        opUser.setSignupDevice("20C808E3-AD23-7D32-7D01-23BA48A27E61");
        opUser.setUserName("c1710267");
        opUser.setUserTagDeal(123);
        opUser.setUserTagSubGame(1234);
        Long time = System.currentTimeMillis() / 1000;
        SdkLoginDto sdkLoginDto = new SdkLoginDto();
        sdkLoginDto.setSubGameId(opUser.getUserTagSubGame());
        sdkLoginDto.setName(opUser.getUserName());
        sdkLoginDto.setDevice(opUser.getSignupDevice());
        sdkLoginDto.setDealId(opUser.getUserTagDeal());
        sdkLoginDto.setTime(time);
        // 模拟令牌
        String str = sdkLoginDto.getSubGameId() + sdkInfo.getOpSubGameModel().getGameKey()
            + opUser.getUserPassword() + sdkLoginDto.getTime();
        String token = Md5Util.md5Encode(str, "utf-8");
        sdkLoginDto.setToken(token);
        // mock 数据库查询方法
        doReturn(opUser).when(opUserService).getOpUserByUserName(any());
        doReturn(opUser).when(opUserService).getOpUserByUserPhone(any());
        doReturn(true).when(opUserService).updateById(any());
        ReflectionTestUtils.setField(sdkUserServiceImpl, "opUserRealnameInfoService",
            opUserRealnameInfoService);
        doReturn(new OpUserRealnameInfo()).when(opUserRealnameInfoService)
            .getOpUserRealnameInfoByUserId(any());
        doReturn(true).when(opUserOnlineService)
            .saveOpUserOnline(any(), any(), any(), any(), any(), any(), any());
        SdkUserLoginRes expectResult = new SdkUserLoginRes();
        expectResult.setUserId(opUser.getId());
        expectResult.setUserPhone(opUser.getUserPhone());
        expectResult.setUserName(opUser.getUserName());
        expectResult.setIsBindPhone(1);
        expectResult.setIsIdAuth(1);
        expectResult.setIdAuthSwitch(0);
        expectResult.setIsAdult(1);
        expectResult.setRemainingTime(0l);
        expectResult.setUserPhone(opUser.getUserPhone());
        expectResult.setUserName(opUser.getUserName());
        // 测试登录
        SdkUserLoginRes sdkUserLoginRes = sdkUserServiceImpl.login(sdkLoginDto);
        sdkUserLoginRes.setSessionId(null); // 不校验session_id
        assertEquals(expectResult, sdkUserLoginRes);
    }

    @Test
    public void testUpdate() {
        String code = "35730";
        OpUser opUser = new OpUser();
        opUser.setId(1);
        opUser.setUserPhone("13626906060");
        opUser.setUserName("c923846758");
        opUser.setUserPassword("9b3e6089232abdf3e153ed2f7f54dc3b");
        // mock 数据库方法
        doReturn(opUser).when(opUserService).getOpUserByUserPhone(any());
        doReturn(opUser).when(opUserService).getOpUserByUserName(any());
        doReturn(true).when(opUserService).updateById(any());
        // mock Redis
        ReflectionTestUtils.setField(sdkUserServiceImpl, "stringRedisTemplate",
            stringRedisTemplate);
        ValueOperations valueOperations = mock(ValueOperations.class);
        // mock获取验证码方法
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(RedisConstant.getCodeKey(opUser.getUserPhone()))).thenReturn(code);
        SdkUpdateDto sdkUpdateDto = new SdkUpdateDto();
        sdkUpdateDto.setSubGameId(14109);
        sdkUpdateDto.setCode("35730");
        sdkUpdateDto.setDevice("0A797742-C3A8-166B-8823-358344643C18");
        sdkUpdateDto.setUserPhone(opUser.getUserPhone());
        sdkUpdateDto.setUserPassword(opUser.getUserPassword());
        sdkUpdateDto.setUserName(opUser.getUserName());
        SdkUserUpdateRes expectResult = new SdkUserUpdateRes();
        expectResult.setUpdateType(OperateTypeConstant.UPDATE_PASSWORD);
        expectResult.setUserName(opUser.getUserName());
        expectResult.setUserPhone(opUser.getUserPhone());
        sdkUpdateDto.setUpdateType(OperateTypeConstant.UPDATE_PASSWORD);
        SdkUserUpdateRes res1 = sdkUserServiceImpl.update(sdkUpdateDto);
        assertEquals(expectResult, res1);
        expectResult.setUpdateType(OperateTypeConstant.RESET_PASSWD);
        sdkUpdateDto.setUpdateType(OperateTypeConstant.RESET_PASSWD);
        SdkUserUpdateRes res2 = sdkUserServiceImpl.update(sdkUpdateDto);
        assertEquals(expectResult, res2);
        expectResult.setUpdateType(OperateTypeConstant.UNBIND_PHONE);
        sdkUpdateDto.setUpdateType(OperateTypeConstant.UNBIND_PHONE);
        SdkUserUpdateRes res3 = sdkUserServiceImpl.update(sdkUpdateDto);
        assertEquals(expectResult, res3);
        // 模拟 绑定手机号数据库不能存在已有手机
        doReturn(null).when(opUserService).getOpUserByUserPhone(any());
        expectResult.setUpdateType(OperateTypeConstant.BIND_PHONE);
        sdkUpdateDto.setUpdateType(OperateTypeConstant.BIND_PHONE);
        SdkUserUpdateRes res4 = sdkUserServiceImpl.update(sdkUpdateDto);
        assertEquals(expectResult, res4);
    }

}
