package org.jeecg.modules.pay.service;

import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.vo.IosPayInfoVo;
import org.jeecg.modules.pay.vo.WechatAppVo;
import org.jeecg.modules.pay.vo.pay.AllUsePCVo;

/**
 * @version V1.0
 * @description: sdk pay service
 * @author: xmh
 * @date: 2022/12/19 16:02
 */
public interface ISdkPayService {

    /**
     * @param openDto: 支付参数
     * @return SdkResult<WechatAppVo>
     * @author xmh
     * @description 微信App下单
     * @date 2023/1/11 16:27
     */
    SdkResult<WechatAppVo> wechatAppPay(OpenDto openDto);

    /**
     * @param openDto:  支付参数
     * @param response: response
     * @author xmh
     * @description 微信h5下单
     * @date 2023/1/11 16:27
     */
    void wechatH5Pay(OpenDto openDto, HttpServletResponse response);

    /**
     * @param openDto
     * @return org.jeecg.common.api.vo.SdkResult<java.lang.String>
     * @Author lili
     * @Description 支付宝h5下单
     * @Date 19:29 2023/4/3
     **/
    void alipayH5Pay(OpenDto openDto, HttpServletResponse response);

    /**
     * @param openDto
     * @author chenyw
     * @description ios支付
     * @date 19:08 2023/4/7/007
     **/
    IosPayInfoVo iosPay(OpenDto openDto);

    /**
     * @param openDto: 支付参数
     * @return SdkResult<AllUsePCVo>
     * @author xmh
     * @description 平台币下单
     * @date 2023/1/11 16:27
     */
    SdkResult<AllUsePCVo> platformCurrencyPay(OpenDto openDto);
}
