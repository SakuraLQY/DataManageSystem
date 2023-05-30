package org.jeecg.modules.advert.api.ks.account.bo;

import java.util.List;
import lombok.Data;

/**
 * @Description: 获取纵横组织下资产账户列表 响应参数
 * @Author: chenyw
 * @Date:   2023-02-08
 * @Version: V1.0
 */
@Data
public class KsAdvertiserSelectResponse {

    List<KsAdvertiserSelectListResponse> list;

}
