package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取行业列表  响应参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuGetIndustryResponse {

    /**
     * 行业列表
     **/
    @JSONField(name = "list")
    private List<XingtuGetIndustryListResponse> list;

}
