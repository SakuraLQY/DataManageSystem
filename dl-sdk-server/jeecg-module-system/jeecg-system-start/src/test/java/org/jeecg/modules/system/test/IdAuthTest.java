package org.jeecg.modules.system.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.constant.IsConstant;
import org.jeecg.common.game.vo.OpSubGameModel;
import org.jeecg.config.sign.context.SdkContext;
import org.jeecg.config.sign.context.SdkInfo;
import org.jeecg.modules.users.dto.SdkIdAuthDto;
import org.jeecg.modules.users.entity.OpUserOnline;
import org.jeecg.modules.users.entity.OpUserRealnameInfo;
import org.jeecg.modules.users.mapper.OpHolidayConfigMapper;
import org.jeecg.modules.users.mapper.OpUserOnlineMapper;
import org.jeecg.modules.users.entity.OpUserRealnamedGame;
import org.jeecg.modules.users.mapper.OpUserRealnameInfoMapper;
import org.jeecg.modules.users.mapper.OpUserRealnamedGameMapper;
import org.jeecg.modules.users.service.IOpUserOnlineService;
import org.jeecg.modules.users.service.IOpUserRealnameInfoService;
import org.jeecg.modules.users.service.IOpUserService;
import org.jeecg.modules.users.vo.SdkUserIdAuthRes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 功能描述
 *
 * @author: xmh
 * @date: 2022/12/7 9:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class IdAuthTest {

    @Resource
    private IOpUserService opUserService;
    @Spy
    private IOpUserRealnameInfoService opUserRealnameInfoService;
    @MockBean
    private OpUserRealnameInfoMapper opUserRealnameInfoMapper;
    @MockBean
    private OpUserRealnamedGameMapper opUserRealnamedGameMapper;

    //    @InjectMocks
    @SpyBean
    private IOpUserOnlineService opUserOnlineService;
    @SpyBean
    private OpUserOnlineMapper opUserOnlineMapper;

    @SpyBean
    private OpHolidayConfigMapper holidayConfigMapper;

    @Before
    public void before() {
        // 初始化
        SdkInfo sdkInfo = new SdkInfo();
        OpSubGameModel opSubGameModel = new OpSubGameModel();
        opSubGameModel.setIdAuthApi(1);
        opSubGameModel.setOfficialAntiIndulgeSwitch(1);
        opSubGameModel.setOfficialBizId("1199006685");
        opSubGameModel.setOfficialAntiIndulgeSwitch(1);
        sdkInfo.setOpSubGameModel(opSubGameModel);
        SdkContext.setSdkInfo(sdkInfo);
    }

    // 游客上报
    @Test
    public void test() {
        when(opUserOnlineMapper.delete(any())).thenReturn(99);
        List<String> bizIds = new ArrayList<>();
        bizIds.add("1199006685");

        doReturn(true).when(holidayConfigMapper).exists(any());
        doReturn(bizIds).when(opUserOnlineMapper).selectObjs(any());
        LinkedList<OpUserOnline> opUserOnlines = new LinkedList<>();
        OpUserOnline opUserOnline = new OpUserOnline();
        opUserOnline.setId(1);
        opUserOnline.setSubGameId(10011);
        opUserOnline.setLoginTime(LocalDateTime.now());
        opUserOnline.setBizId("1199006685");
        opUserOnline.setSi("c5beb640775d116b7deb674bc9088b91");
        opUserOnline.setDi("ee9239d6c8dc470ff9979d04e7a677ad");
        opUserOnline.setState(0);
        opUserOnline.setRetryTimes(0);
        opUserOnlines.add(opUserOnline);
        doReturn(opUserOnlines).when(opUserOnlineService).list(any());
        opUserOnlineService.report();
    }

    // 身份证被占用，本账户未实名 -> 假实名
    @Test
    public void Test() {
        SdkIdAuthDto sdkIdAuthDto = new SdkIdAuthDto();
        sdkIdAuthDto.setSubGameId(8);
        sdkIdAuthDto.setUserId(9);
        sdkIdAuthDto.setIdNumber("35058319990919314X");
        sdkIdAuthDto.setRealName("林晓丽");
        // 身份证在本游戏中未被绑定
        LambdaQueryWrapper<OpUserRealnamedGame> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpUserRealnamedGame::getSubGameId, sdkIdAuthDto.getSubGameId());
        wrapper.eq(OpUserRealnamedGame::getRealNumber, sdkIdAuthDto.getIdNumber());
        when(opUserRealnamedGameMapper.selectOne(wrapper)).thenReturn(null);
        // 身份证被绑定
        OpUserRealnameInfo realnameInfo = new OpUserRealnameInfo();
        realnameInfo.setUserId(sdkIdAuthDto.getUserId());
        when(opUserRealnameInfoMapper.selectOne(any())).thenReturn(realnameInfo);
        // 本账户未实名
        ReflectionTestUtils.setField(opUserService, "opUserRealnameInfoService",
            opUserRealnameInfoService);
        doReturn(null).when(opUserRealnameInfoService).getOpUserRealnameInfoByUserId(any());
        // 插入或更新数据
        when(opUserRealnameInfoMapper.insert(any())).thenReturn(1);
        when(opUserRealnameInfoMapper.updateById(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.insert(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.updateById(any())).thenReturn(1);
        SdkUserIdAuthRes expectRes = new SdkUserIdAuthRes();
        expectRes.setUserId(sdkIdAuthDto.getUserId());
        expectRes.setIsAdult(IsConstant.YES);
        expectRes.setRemainingTime(0L);
        SdkUserIdAuthRes sdkUserIdAuthRes = opUserService.idAuth(sdkIdAuthDto);
        assertEquals(expectRes, sdkUserIdAuthRes);
    }

    // 身份证被占用，本账户假实名 -> 同步到 game
    @Test
    public void Test1() {
        SdkIdAuthDto sdkIdAuthDto = new SdkIdAuthDto();
        sdkIdAuthDto.setSubGameId(4);
        sdkIdAuthDto.setUserId(9);
        sdkIdAuthDto.setIdNumber("350624199804083038");
        sdkIdAuthDto.setRealName("徐茂华");
        // 身份证在本游戏中未被绑定
        LambdaQueryWrapper<OpUserRealnamedGame> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpUserRealnamedGame::getSubGameId, sdkIdAuthDto.getSubGameId());
        wrapper.eq(OpUserRealnamedGame::getRealNumber, sdkIdAuthDto.getIdNumber());
        when(opUserRealnamedGameMapper.selectOne(wrapper)).thenReturn(null);
        // 身份证被绑定
        OpUserRealnameInfo realnameInfo = new OpUserRealnameInfo();
        realnameInfo.setUserId(sdkIdAuthDto.getUserId());
        when(opUserRealnameInfoMapper.selectOne(any())).thenReturn(realnameInfo);
        // 本账户假实名
        OpUserRealnameInfo user = new OpUserRealnameInfo();
        user.setUserId(sdkIdAuthDto.getUserId());
        user.setRealNumber(sdkIdAuthDto.getIdNumber());
        ReflectionTestUtils.setField(opUserService, "opUserRealnameInfoService",
            opUserRealnameInfoService);
        doReturn(user).when(opUserRealnameInfoService).getOpUserRealnameInfoByUserId(any());
        // 插入或更新数据
        when(opUserRealnameInfoMapper.insert(any())).thenReturn(1);
        when(opUserRealnameInfoMapper.updateById(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.insert(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.updateById(any())).thenReturn(1);

        SdkUserIdAuthRes expectRes = new SdkUserIdAuthRes();
        expectRes.setUserId(sdkIdAuthDto.getUserId());
        expectRes.setIsAdult(IsConstant.YES);
        expectRes.setRemainingTime(0L);
        SdkUserIdAuthRes sdkUserIdAuthRes = opUserService.idAuth(sdkIdAuthDto);
        assertEquals(expectRes, sdkUserIdAuthRes);
    }

    // 身份证被占用，本账户真实名 -> 同步到 game
    @Test
    public void Test2() {
        SdkIdAuthDto sdkIdAuthDto = new SdkIdAuthDto();
        sdkIdAuthDto.setSubGameId(4);
        sdkIdAuthDto.setUserId(9);
        sdkIdAuthDto.setIdNumber("350624199804083038");
        sdkIdAuthDto.setRealName("徐茂华");
        // 身份证在本游戏中未被绑定
        LambdaQueryWrapper<OpUserRealnamedGame> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpUserRealnamedGame::getSubGameId, sdkIdAuthDto.getSubGameId());
        wrapper.eq(OpUserRealnamedGame::getRealNumber, sdkIdAuthDto.getIdNumber());
        when(opUserRealnamedGameMapper.selectOne(wrapper)).thenReturn(null);
        // 身份证被绑定
        OpUserRealnameInfo realnameInfo = new OpUserRealnameInfo();
        realnameInfo.setUserId(sdkIdAuthDto.getUserId());
        when(opUserRealnameInfoMapper.selectOne(any())).thenReturn(realnameInfo);
        // 本账户真实名
        OpUserRealnameInfo user = new OpUserRealnameInfo();
        user.setUserId(sdkIdAuthDto.getUserId());
        user.setRealNumber(sdkIdAuthDto.getIdNumber());
        user.setRealPi("1111");
        ReflectionTestUtils.setField(opUserService, "opUserRealnameInfoService",
            opUserRealnameInfoService);
        doReturn(user).when(opUserRealnameInfoService).getOpUserRealnameInfoByUserId(any());
        // 插入或更新数据
        when(opUserRealnameInfoMapper.insert(any())).thenReturn(1);
        when(opUserRealnameInfoMapper.updateById(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.insert(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.updateById(any())).thenReturn(1);

        SdkUserIdAuthRes expectRes = new SdkUserIdAuthRes();
        expectRes.setUserId(sdkIdAuthDto.getUserId());
        expectRes.setIsAdult(IsConstant.YES);
        expectRes.setRemainingTime(0L);
        SdkUserIdAuthRes sdkUserIdAuthRes = opUserService.idAuth(sdkIdAuthDto);
        assertEquals(expectRes, sdkUserIdAuthRes);
    }

    // 身份证未被占用，本账户真实名 -> 更新实名信息
    @Test
    public void Test3() {
        SdkIdAuthDto sdkIdAuthDto = new SdkIdAuthDto();
        sdkIdAuthDto.setSubGameId(4);
        sdkIdAuthDto.setUserId(9);
        sdkIdAuthDto.setIdNumber("350624199804083038");
        sdkIdAuthDto.setRealName("徐茂华");
        // 身份证在本游戏中未被绑定
        LambdaQueryWrapper<OpUserRealnamedGame> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpUserRealnamedGame::getSubGameId, sdkIdAuthDto.getSubGameId());
        wrapper.eq(OpUserRealnamedGame::getRealNumber, sdkIdAuthDto.getIdNumber());
        when(opUserRealnamedGameMapper.selectOne(wrapper)).thenReturn(null);
        // 身份证未被绑定
        when(opUserRealnameInfoMapper.selectOne(any())).thenReturn(null);
        // 本账户真实名
        OpUserRealnameInfo user = new OpUserRealnameInfo();
        user.setUserId(sdkIdAuthDto.getUserId());
        user.setRealNumber(sdkIdAuthDto.getIdNumber());
        user.setRealPi("1111");
        ReflectionTestUtils.setField(opUserService, "opUserRealnameInfoService",
            opUserRealnameInfoService);
        doReturn(user).when(opUserRealnameInfoService).getOpUserRealnameInfoByUserId(any());
        // 插入或更新数据
        when(opUserRealnameInfoMapper.insert(any())).thenReturn(1);
        when(opUserRealnameInfoMapper.updateById(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.insert(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.updateById(any())).thenReturn(1);

        SdkUserIdAuthRes expectRes = new SdkUserIdAuthRes();
        expectRes.setUserId(sdkIdAuthDto.getUserId());
        expectRes.setIsAdult(IsConstant.YES);
        expectRes.setRemainingTime(0L);
        SdkUserIdAuthRes sdkUserIdAuthRes = opUserService.idAuth(sdkIdAuthDto);
        assertEquals(expectRes, sdkUserIdAuthRes);
    }

    // 身份证未被占用，本账户未实名 -> 真实名，同步到game
    @Test
    public void Test4() {
        SdkIdAuthDto sdkIdAuthDto = new SdkIdAuthDto();
        sdkIdAuthDto.setSubGameId(4);
        sdkIdAuthDto.setUserId(9);
        sdkIdAuthDto.setIdNumber("350624199804083038");
        sdkIdAuthDto.setRealName("徐茂华");
        // 身份证在本游戏中未被绑定
        LambdaQueryWrapper<OpUserRealnamedGame> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OpUserRealnamedGame::getSubGameId, sdkIdAuthDto.getSubGameId());
        wrapper.eq(OpUserRealnamedGame::getRealNumber, sdkIdAuthDto.getIdNumber());
        when(opUserRealnamedGameMapper.selectOne(wrapper)).thenReturn(null);
        // 身份证未被绑定
        when(opUserRealnameInfoMapper.selectOne(any())).thenReturn(null);
        // 本账户未实名
        ReflectionTestUtils.setField(opUserService, "opUserRealnameInfoService",
            opUserRealnameInfoService);
        doReturn(null).when(opUserRealnameInfoService).getOpUserRealnameInfoByUserId(any());
        // 插入或更新数据
        when(opUserRealnameInfoMapper.insert(any())).thenReturn(1);
        when(opUserRealnameInfoMapper.updateById(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.insert(any())).thenReturn(1);
        when(opUserRealnamedGameMapper.updateById(any())).thenReturn(1);

        SdkUserIdAuthRes expectRes = new SdkUserIdAuthRes();
        expectRes.setUserId(sdkIdAuthDto.getUserId());
        expectRes.setIsAdult(IsConstant.YES);
        expectRes.setRemainingTime(0L);
        SdkUserIdAuthRes sdkUserIdAuthRes = opUserService.idAuth(sdkIdAuthDto);
        assertEquals(expectRes, sdkUserIdAuthRes);
    }
}
