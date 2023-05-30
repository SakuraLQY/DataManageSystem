package org.jeecg.modules.pay.controller.sdk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.common.util.IpUtils;
import org.jeecg.modules.pay.dto.IosPayCheckDto;
import org.jeecg.modules.pay.dto.IosPayListDto;
import org.jeecg.modules.pay.dto.IosPayMenuDto;
import org.jeecg.modules.pay.service.IIosPayService;
import org.jeecg.modules.pay.service.IOpPaySwitchService;
import org.jeecg.modules.pay.vo.IosCheckPayModelVo;
import org.jeecg.modules.pay.vo.IosPayMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyw
 * @version V1.0
 * @description: ios模块
 * @date: 2023/1/4
 **/
@Api(tags = "ios下单模块")
@RestController
@RequestMapping("/sdk/ios")
@Slf4j
public class IosPayController {

    @Resource
    private IIosPayService iosPayService;
    @Autowired
    private IOpPaySwitchService opPaySwitchService;

    @ApiOperation(value = "menu", notes = "ios支付非默认支付列表")
    @GetMapping(value = "/menu")
    public SdkResult<IosPayMenuVo> menu(
        @Validated IosPayMenuDto iosPayMenuDto, HttpServletRequest httpServletRequest) {
        String serverName = httpServletRequest.getServerName();
        int port = httpServletRequest.getServerPort();
        return SdkResult.success(iosPayService.iosPayMenu(iosPayMenuDto, serverName, port));
    }

    @GetMapping(value = "/jump", produces = "text/html")
    public String jump(HttpServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        return iosPayService.jump(map);
    }

    @ApiOperation(value = "verifyReceipt", notes = "ios支付验证")
    @GetMapping(value = "/verifyReceipt")
    public SdkResult verifyReceipt(IosPayCheckDto iosPayCheckDto) {
        iosPayService.verifyReceipt(iosPayCheckDto);
        return SdkResult.success();
    }

    @ApiOperation(value = "获取苹果支付列表")
    @GetMapping("/getZtList")
    public SdkResult<IosCheckPayModelVo> getZtList(@Validated IosPayListDto iosPayListDto,
        HttpServletRequest request) {
        String serverName = request.getServerName();
        int port = request.getServerPort();
        IosCheckPayModelVo iosCheckPayModelVo = opPaySwitchService.checkPayModeIos(iosPayListDto,
            serverName, port);
        return SdkResult.success(iosCheckPayModelVo);
    }
}
