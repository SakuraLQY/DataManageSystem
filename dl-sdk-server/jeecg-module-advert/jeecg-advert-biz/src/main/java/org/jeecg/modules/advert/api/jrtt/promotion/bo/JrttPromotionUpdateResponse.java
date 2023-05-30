package org.jeecg.modules.advert.api.jrtt.promotion.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 修改广告响应参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttPromotionUpdateResponse {

    /**
     * 广告ID
     **/
    @JSONField(name = "promotion_id")
    private Long promotionId;

    /**
     * 错误list
     **/
    @JSONField(name = "error_list")
    private List<PromotionError> errorList;

}
