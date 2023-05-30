package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建自定义创意（营销链路）-errors  响应参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuAddCustomCreativeErrorsResponse {

    /**
     * 返回码
     **/
    @JSONField(name = "code")
    private Integer code;

    /**
     * 返回信息
     **/
    @JSONField(name = "message")
    private String message;

}
