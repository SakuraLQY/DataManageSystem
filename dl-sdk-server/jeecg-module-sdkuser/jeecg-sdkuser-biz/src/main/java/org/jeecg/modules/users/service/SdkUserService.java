package org.jeecg.modules.users.service;

import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.modules.users.dto.SdkAliveDto;
import org.jeecg.modules.users.dto.SdkLoginDto;
import org.jeecg.modules.users.dto.SdkLogoutDto;
import org.jeecg.modules.users.dto.SdkRegisterDto;
import org.jeecg.modules.users.dto.SdkSendCodeDto;
import org.jeecg.modules.users.dto.SdkUpdateDto;
import org.jeecg.modules.users.dto.SdkVerifyDto;
import org.jeecg.modules.users.vo.SdkUserLoginRes;
import org.jeecg.modules.users.vo.SdkUserRegisterRes;
import org.jeecg.modules.users.vo.SdkUserUpdateRes;
import org.jeecg.modules.users.vo.SdkVerifyRes;

/**
 * @Description: op_user
 * @Author: jeecg-boot
 * @Date: 2022-11-30
 * @Version: V1.0
 */
public interface SdkUserService {

    /**
     * @param sdkRegisterDto
     * @return org.jeecg.modules.users.vo.SdkUserRegisterRes
     * @author chenyw
     * @description sdk注册
     * @date 11:03 2022/12/21/021
     **/
    SdkUserRegisterRes register(SdkRegisterDto sdkRegisterDto);

    /**
     * @param sdkSendCodeDto
     * @author chenyw
     * @description 发送验证码
     * @date 21:31 2022/12/21/021
     **/
    void sendCode(SdkSendCodeDto sdkSendCodeDto);

    /**
     * @param sdkLoginDto
     * @return org.jeecg.modules.users.vo.SdkUserLoginRes
     * @author chenyw
     * @description 登录
     * @date 21:31 2022/12/21/021
     **/
    SdkUserLoginRes login(SdkLoginDto sdkLoginDto);

    /**
     * @param sdkUpdateDto
     * @return org.jeecg.modules.users.vo.SdkUserUpdateRes
     * @author chenyw
     * @description 更新
     * @date 21:31 2022/12/21/021
     **/
    SdkUserUpdateRes update(SdkUpdateDto sdkUpdateDto);

    /**
     * @param sdkAliveDto
     * @author chenyw
     * @description 角色上报
     * @date 21:31 2022/12/21/021
     **/
    void alive(SdkAliveDto sdkAliveDto);

    /**
     * @param sdkLogoutDto
     * @author chenyw
     * @description 登出游戏
     * @date 21:31 2022/12/21/021
     **/
    @Deprecated
    void logoutGame(SdkLogoutDto sdkLogoutDto);

    /**
     * @param sdkVerifyDto
     * @author chenyw
     * @description 客户端登录校验
     * @date 21:32 2022/12/21/021
     **/
    SdkVerifyRes verify(SdkVerifyDto sdkVerifyDto);
}
