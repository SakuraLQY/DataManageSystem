package org.jeecg.modules.advert.api.ks.account.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 获取纵横组织下资产账户列表 响应参数
 * @Author: chenyw
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
public class KsAdvertiserSelectListResponse {

    /**
     * 纵横组织id
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 广告组名称
     **/
    @JSONField(name = "advertiser_name")
    private String advertiserName;
}
