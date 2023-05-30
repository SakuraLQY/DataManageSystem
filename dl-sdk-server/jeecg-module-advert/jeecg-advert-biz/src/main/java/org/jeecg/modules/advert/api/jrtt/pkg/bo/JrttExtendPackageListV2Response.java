package org.jeecg.modules.advert.api.jrtt.pkg.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttPageInfoResponse;

/**
 * @Description: 查询应用分包列表响应参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttExtendPackageListV2Response {

    /**
     * 应用包id
     **/
    @JSONField(name = "list")
    private List<JrttExtendPackageListV2ListResponse> list;

    /**
     * 分页信息
     **/
    @JSONField(name = "page_info")
    private JrttPageInfoResponse pageInfo;

}
