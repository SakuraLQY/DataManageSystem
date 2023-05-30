package org.jeecg.modules.pay.test;


import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.exception.ErrorCode;
import org.jeecg.common.exception.IdeaRunTimeException;
import org.jeecg.modules.pay.constant.WechatPayConstant;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.entity.OpPayVendor;
import org.jeecg.modules.pay.service.IWechatPayService;
import org.jeecg.modules.pay.util.WechatUtils;
import org.jeecg.modules.vo.OpUserVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/1/6 16:41
 **/
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JeecgSystemApplication.class)
public class WechatTest {

    @Resource
    private IWechatPayService wechatPayService;

    @Test
    public void wechatPay() {
        String orderId = "123";
        OpenDto openDto = new OpenDto();
        openDto.setDesc("desc");
        openDto.setMmm(BigDecimal.valueOf(6.99));
        openDto.setCouponPrice(BigDecimal.valueOf(0.99));
        openDto.setClientIp("127.0.0.1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("app_id", "app_id");
        jsonObject.put("mch_id", "mch_id");
        jsonObject.put("api_key", "api_key");
        jsonObject.put("serial_no", "serial_no");
        jsonObject.put("notify_url", "notify_url");
        jsonObject.put("private_key", "private_key");
        OpPayVendor opPayVendor = new OpPayVendor();
        opPayVendor.setId(1);
        opPayVendor.setPayVendorConf(jsonObject.toJSONString());

        try (MockedStatic<WechatUtils> utilsMockedStatic = Mockito.mockStatic(WechatUtils.class)) {
            // h5 支付
            try {
                wechatPayService.wechatOpen(WechatPayConstant.H5_V3_ORDER, orderId, openDto,
                    opPayVendor,
                    null);
            } catch (IdeaRunTimeException e) {
                Assert.assertEquals(ErrorCode.SEND_WECHAT_FAIL, e.getErrorCode());
            }

            // APP 支付
            try {
                wechatPayService.wechatOpen(WechatPayConstant.APP_ORDER, orderId, openDto,
                    opPayVendor,
                    null);
            } catch (IdeaRunTimeException e) {
                Assert.assertEquals(ErrorCode.SEND_WECHAT_FAIL, e.getErrorCode());
            }
        }
    }
}
