package org.jeecg.modules.advert.api.jrtt.report.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttPageInfoResponse;

/**
 * @Description: 获取报表响应参数-列表
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class CustomnRows {

    /**
     * 维度列表
     **/
    @JSONField(name = "dimensions")
    private Dimensions dimensions;

    /**
     * 指标列表
     **/
    @JSONField(name = "metrics")
    private Metrics metrics;

}
