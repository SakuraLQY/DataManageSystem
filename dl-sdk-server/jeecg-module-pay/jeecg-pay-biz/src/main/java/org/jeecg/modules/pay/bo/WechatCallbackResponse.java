package org.jeecg.modules.pay.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author xmh
 * @version V1.0
 * @description: 微信回调返回对象
 * @date: 2023/1/5 16:20
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "微信回调返回对象", description = "微信回调返回对象")
public class WechatCallbackResponse {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回状态码")
    private String code;

    @ApiModelProperty(value = "返回信息")
    private String message;

    /**
     * @return WechatCallbackResponse
     * @author xmh
     * @description 成功返回对象
     * @date 2023/1/5 16:24
     */
    public static WechatCallbackResponse success() {
        WechatCallbackResponse response = new WechatCallbackResponse();
        response.setCode("SUCCESS");
        response.setMessage("success");
        return response;
    }

    /**
     * @param message: 返回信息
     * @return WechatCallbackResponse
     * @author xmh
     * @description 成功返回对象
     * @date 2023/1/5 16:24
     */
    public static WechatCallbackResponse success(String message) {
        WechatCallbackResponse response = new WechatCallbackResponse();
        response.setCode("SUCCESS");
        response.setMessage(message);
        return response;
    }

    /**
     * @param message: 返回信息
     * @return WechatCallbackResponse
     * @author xmh
     * @description 失败返回对象
     * @date 2023/1/5 16:24
     */
    public static WechatCallbackResponse fail(String message) {
        WechatCallbackResponse response = new WechatCallbackResponse();
        response.setCode("FAIL");
        response.setMessage(message);
        return response;
    }
}
