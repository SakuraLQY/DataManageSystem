package org.jeecg.modules.users.controller.sdk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.modules.users.dto.SdkAliveDto;
import org.jeecg.modules.users.dto.SdkIdAuthDto;
import org.jeecg.modules.users.dto.SdkLoginDto;
import org.jeecg.modules.users.dto.SdkLogoutDto;
import org.jeecg.modules.users.dto.SdkRegisterDto;
import org.jeecg.modules.users.dto.SdkSendCodeDto;
import org.jeecg.modules.users.dto.SdkUpdateDto;
import org.jeecg.modules.users.dto.SdkVerifyDto;
import org.jeecg.modules.users.service.IOpUserService;
import org.jeecg.modules.users.vo.SdkUserIdAuthRes;
import org.jeecg.modules.users.vo.SdkUserLoginRes;
import org.jeecg.modules.users.vo.SdkUserRegisterRes;
import org.jeecg.modules.users.service.SdkUserService;
import org.jeecg.modules.users.vo.SdkUserUpdateRes;
import org.jeecg.modules.users.vo.SdkVerifyRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@Api(tags = "sdk请求用户模块")
@RestController
@RequestMapping("/sdk/user")
@Slf4j
public class SdkUserController {

    @Autowired
    private SdkUserService sdkUserService;
    @Resource
    private IOpUserService opUserService;

    @ApiOperation(value = "register", notes = "用户注册")
    @GetMapping(value = "/register")
    public SdkResult<SdkUserRegisterRes> register(SdkRegisterDto sdkRegisterDto) {
        SdkUserRegisterRes sdkUserRegisterRes = sdkUserService.register(sdkRegisterDto);
        return SdkResult.success(sdkUserRegisterRes);
    }

    @ApiOperation(value = "sendCode", notes = "发送短信验证码")
    @GetMapping(value = "/send_code")
    public SdkResult sendCode(SdkSendCodeDto sendCodeDto) {
        sdkUserService.sendCode(sendCodeDto);
        return SdkResult.success();
    }

    @ApiOperation(value = "login", notes = "登录")
    @GetMapping(value = "/login")
    public SdkResult<SdkUserLoginRes> login(@Validated SdkLoginDto sdkLoginDto) {
        SdkUserLoginRes sdkUserLoginRes = sdkUserService.login(sdkLoginDto);
        return SdkResult.success(sdkUserLoginRes);
    }

    @ApiOperation(value = "logout_game", notes = "登出")
    @GetMapping(value = "/logout_game")
    public SdkResult logoutGame(SdkLogoutDto sdkLogoutDto) {
        sdkUserService.logoutGame(sdkLogoutDto);
        return SdkResult.success();
    }

    @ApiOperation(value = "update", notes = "找回密码、修改密码、绑定手机、解绑手机")
    @GetMapping(value = "/update")
    public SdkResult<SdkUserUpdateRes> update(SdkUpdateDto sdkUpdateDto) {
        SdkUserUpdateRes sdkUserUpdateRes = sdkUserService.update(sdkUpdateDto);
        return SdkResult.success(sdkUserUpdateRes);
    }

    @ApiOperation(value = "alive", notes = "角色上报接口")
    @GetMapping(value = "/alive")
    public SdkResult<ArrayList> alive(SdkAliveDto sdkAliveDto) {
        sdkUserService.alive(sdkAliveDto);
        return SdkResult.success(new ArrayList());
    }

    @ApiOperation(value = "verify", notes = "cp验证用户信息方法")
    @GetMapping(value = "/verify")
    public SdkResult<SdkVerifyRes> verify(SdkVerifyDto sdkVerifyDto) {
        SdkVerifyRes sdkVerifyRes = sdkUserService.verify(sdkVerifyDto);
        return SdkResult.success(sdkVerifyRes);
    }

    @ApiOperation(value = "id_auth", notes = "实名验证")
    @GetMapping(value = "/id_auth")
    public SdkResult<SdkUserIdAuthRes> doIdAuth(SdkIdAuthDto sdkIdAuthDto) {
        SdkUserIdAuthRes sdkUserIdAuthRes = opUserService.idAuth(sdkIdAuthDto);
        return SdkResult.success(sdkUserIdAuthRes);
    }

}
