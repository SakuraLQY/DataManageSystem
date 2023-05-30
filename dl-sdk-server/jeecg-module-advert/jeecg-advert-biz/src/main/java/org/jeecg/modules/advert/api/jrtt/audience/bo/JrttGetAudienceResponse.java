
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取定向包  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAudienceResponse {

    /**
     * 定向包信息
     **/
    @JSONField(name = "audience_packages")
    private List<JrttGetAudiencePackagesResponse> audiencePackages;

}
