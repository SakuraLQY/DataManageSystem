package org.jeecg.modules.pay.service;

import java.util.Map;
import org.jeecg.common.api.vo.SdkResult;
import org.jeecg.modules.pay.dto.IosPayCheckDto;
import org.jeecg.modules.pay.dto.IosPayMenuDto;
import org.jeecg.modules.pay.dto.OpenDto;
import org.jeecg.modules.pay.vo.IosPayMenuVo;

/**
 * @version V1.0
 * @description: ios pay service
 * @author: chenyw
 * @date: 2023/1/4 16:02
 */
public interface IIosPayService {

    /**
     * @param iosPayMenuDto
     * @param serverName 域名
     * @param port 端口
     * @return org.jeecg.modules.pay.vo.IosPayMenuVo
     * @author chenyw
     * @description 获取ios非默认支付列表
     * @date 14:16 2023/1/5/005
     **/
    IosPayMenuVo iosPayMenu(IosPayMenuDto iosPayMenuDto, String serverName, int port);

    /**
     * @param map 跳转请求
     * @return java.lang.String 跳转页面的html流
     * @author chenyw
     * @description 页面跳转方法
     * @date 13:59 2023/1/6/006
     **/
    String jump(Map<String, String[]> map);

    /**
     * @param iosPayCheckDto
     * @author chenyw
     * @description 校验苹果订单状态并发货
     * @date 10:35 2023/1/7/007
     **/
    void verifyReceipt(IosPayCheckDto iosPayCheckDto);
}
