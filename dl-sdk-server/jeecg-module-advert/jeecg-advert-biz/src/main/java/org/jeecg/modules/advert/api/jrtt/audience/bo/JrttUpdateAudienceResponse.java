package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 添加定向包 响应参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttUpdateAudienceResponse {

    /**
     * 定向包id
     **/
    @JSONField(name = "audience_package_id")
    private Long audiencePackageId;

}
