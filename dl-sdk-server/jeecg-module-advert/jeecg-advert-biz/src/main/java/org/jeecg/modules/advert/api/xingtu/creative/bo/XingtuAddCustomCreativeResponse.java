package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建自定义创意（营销链路）  响应参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuAddCustomCreativeResponse {

    /**
     * 创意ID列表，若部分失败，则对应项为null
     **/
    @JSONField(name = "creative_ids")
    private List<Long> creativeIds;

    /**
     * 每个创意对应的错误信息
     **/
    @JSONField(name = "errors")
    private List<XingtuAddCustomCreativeErrorsResponse> errors;

}
