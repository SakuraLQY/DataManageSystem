package org.jeecg.modules.game.controller.sdk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.common.util.IpUtils;
import org.jeecg.modules.game.dto.SdkConfDto;
import org.jeecg.modules.game.dto.SdkIosConfDto;
import org.jeecg.modules.game.dto.SdkStartDataDto;
import org.jeecg.modules.game.service.SdkGameService;
import org.jeecg.modules.game.vo.PrivacyAndUserProtocolVo;
import org.jeecg.modules.game.vo.SdkConfRes;
import org.jeecg.modules.game.vo.SdkIosConfRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lili
 * @Description sdk游戏
 * @Date 2023-03-29
 **/
@Api(tags = "sdkGame")
@RestController
@RequestMapping("/sdk/game")
@Slf4j
public class SdkGameController {

    @Resource
    private SdkGameService sdkGameService;

    @ApiOperation(value = "conf", notes = "安卓初始化")
    @GetMapping(value = "/conf")
    public SdkResult<SdkConfRes> conf(SdkConfDto sdkConfDto) {
        SdkConfRes sdkConfRes = sdkGameService.conf(sdkConfDto);
        return SdkResult.success(sdkConfRes);
    }

    @ApiOperation(value = "iosConf", notes = "ios初始化")
    @GetMapping(value = "/iosConf")
    public SdkResult<SdkIosConfRes> iosConf(SdkIosConfDto sdkIosConfDto, HttpServletRequest request) {
        if (StringUtils.isEmpty(sdkIosConfDto.getClientIp())) {
            sdkIosConfDto.setClientIp(IpUtils.getIpAddr(request));
        }
        SdkIosConfRes sdkIosConfRes = sdkGameService.iosConf(sdkIosConfDto);
        return SdkResult.success(sdkIosConfRes);
    }

    @ApiOperation(value = "start", notes = "激活")
    @GetMapping(value = "/start")
    public SdkResult<SdkConfRes> start(SdkStartDataDto sdkEventDto, HttpServletRequest request) {
        if (StringUtils.isEmpty(sdkEventDto.getClientIp())) {
            sdkEventDto.setClientIp(IpUtils.getIpAddr(request));
        }
        sdkGameService.start(sdkEventDto);
        return SdkResult.success(null);
    }

    @ApiOperation(value = "privacy", notes = "获取隐私政策,用户协议")
    @GetMapping(value = "/privacyAndUserProtocol")
    public SdkResult<PrivacyAndUserProtocolVo> getPrivacy(@RequestParam("pkgId") Integer pkgId) {
        return SdkResult.success(sdkGameService.getPrivacyAndUserProtocol(pkgId));
    }

}
