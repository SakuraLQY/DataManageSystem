package org.jeecg.modules.advert.api.jrtt.tool.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttPageInfoResponse;

/**
 * @Description: 获取抖音授权关系响应参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttAwemeAuthListResponse {

    /**
     * 列表内容
     **/
    @JSONField(name = "list")
    private List<JrttAwemeAuthListListResponse> list;

    /**
     * 分页参数
     **/
    @JSONField(name = "page_info")
    private JrttPageInfoResponse jrttPageInfoResponse;

}
