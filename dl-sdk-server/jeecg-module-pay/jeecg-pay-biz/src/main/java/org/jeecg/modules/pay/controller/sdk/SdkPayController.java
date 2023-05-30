package org.jeecg.modules.pay.controller.sdk;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.service.IOpPaySwitchService;
import org.jeecg.modules.pay.service.ISdkPayService;
import org.jeecg.modules.pay.vo.WechatAppVo;
import org.jeecg.modules.pay.vo.pay.AllUsePCVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2022/12/19
 **/
@Api(tags = "sdk下单模块")
@RestController
@RequestMapping("/sdk/pay")
@Slf4j
public class SdkPayController {

    @Autowired
    private IOpPaySwitchService opPaySwitchService;
    @Resource
    private ISdkPayService sdkPayService;

    @ApiOperation(value = "获取支付列表(安卓)")
    @GetMapping("/getPayList")
    public SdkResult<List<Integer>> getPayList(@RequestParam Integer subGameId,@RequestParam Integer pkgId,
        @RequestParam String userId, @RequestParam(required = false) String version,
        @RequestParam(required = false) String build, BigDecimal mm) {
        List<Integer> payList = opPaySwitchService
            .checkPayMode(subGameId, pkgId, userId, version, build, mm);
        return SdkResult.success(payList);
    }

    @ApiOperation(value = "微信App下单", notes = "微信App下单")
    @GetMapping(value = "/wechat/app/open")
    public SdkResult<WechatAppVo> wechatAppOpen(@Validated OpenDto openDto) {
        return sdkPayService.wechatAppPay(openDto);
    }

    @ApiOperation(value = "支付宝h5下单", notes = "支付宝h5下单")
    @GetMapping(value = "/alipay/h5/open")
    public void alipayH5Open(@Validated OpenDto openDto, HttpServletResponse response) {
        sdkPayService.alipayH5Pay(openDto, response);
    }

    @ApiOperation(value = "微信h5下单", notes = "微信h5下单")
    @GetMapping(value = "/wechat/h5/open")
    public void wechatH5Open(@Validated OpenDto openDto, HttpServletResponse response) {
        sdkPayService.wechatH5Pay(openDto, response);
    }

    @ApiOperation(value = "平台币下单", notes = "平台币下单")
    @GetMapping(value = "/platformcurrency/open")
    public SdkResult<AllUsePCVo> platformCurrencyOpen(@Validated OpenDto openDto) {
        return sdkPayService.platformCurrencyPay(openDto);
    }

}
