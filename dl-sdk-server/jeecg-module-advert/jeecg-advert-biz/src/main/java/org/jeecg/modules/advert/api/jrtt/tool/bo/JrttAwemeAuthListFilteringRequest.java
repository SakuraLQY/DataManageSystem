package org.jeecg.modules.advert.api.jrtt.tool.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取抖音授权关系请求参数 - 筛选条件
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttAwemeAuthListFilteringRequest {

    /**
     * 授权类型，可选值:AWEME_ACCOUNT: 抖音号授权、VIDEO_ITEM: 单视频授权
     **/
    @JSONField(name = "auth_type")
    private List<String> authType;

}
