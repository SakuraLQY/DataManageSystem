package org.jeecg.modules.advert.api.jrtt.promotion.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 错误list
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class PromotionError {

    /**
     * 错误对象类型
     **/
    @JSONField(name = "object_type")
    private String objectType;

    /**
     * 错误信息
     **/
    @JSONField(name = "error_code")
    private String errorCode;

    /**
     * 错误信息
     **/
    @JSONField(name = "error_message")
    private String errorMessage;
}
