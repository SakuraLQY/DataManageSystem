package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 添加资产 响应参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttAddAssetsResponse {

    /**
     * 资产ID
     **/
    @JSONField(name = "asset_id")
    private Long assetId;

}
