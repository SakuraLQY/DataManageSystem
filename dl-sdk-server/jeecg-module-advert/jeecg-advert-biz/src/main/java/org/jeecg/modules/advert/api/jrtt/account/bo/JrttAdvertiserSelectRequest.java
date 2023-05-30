package org.jeecg.modules.advert.api.jrtt.account.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 获取纵横组织下资产账户列表 请求参数
 * @Author: chenyw
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttAdvertiserSelectRequest {

    /**
     * 广告组id
     **/
    @JSONField(name = "advertiser_id")
    private String advertiserId;

}
