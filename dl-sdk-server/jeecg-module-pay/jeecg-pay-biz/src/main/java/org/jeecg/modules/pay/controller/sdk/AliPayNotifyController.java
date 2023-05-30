package org.jeecg.modules.pay.controller.sdk;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.ResourceUtil;
import org.jeecg.modules.pay.bo.AlipayConf;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.service.IOpAlipayService;
import org.jeecg.modules.pay.service.IOpPayVendorService;
import org.jeecg.modules.pay.util.RequestToMap;
import org.jeecg.modules.vo.OpUserVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lili
 * @Description
 * @Date 2022-12-26
 **/
@RestController
@RequestMapping("pay/ali")
@Slf4j
public class AliPayNotifyController {

    @Resource
    private IOpAlipayService opAlipayService;

    /**
     * @param request
     * @return java.lang.String
     * @Author lili
     * @Description 支付宝回调通知
     * @Date 13:51 2022/12/28
     **/
    @ApiOperation(value = "支付宝回调通知", notes = "支付宝回调通知")
    @PostMapping(value = "/backend")
    public String backend(HttpServletRequest request) throws AlipayApiException {
        return opAlipayService.backend(request);
    }
}


