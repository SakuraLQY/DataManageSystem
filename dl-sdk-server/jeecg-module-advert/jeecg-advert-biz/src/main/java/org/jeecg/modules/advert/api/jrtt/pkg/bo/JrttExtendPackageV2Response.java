package org.jeecg.modules.advert.api.jrtt.pkg.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.constant.jrtt.JrttExtendPackageMode;

/**
 * @Description: 创建应用分包响应参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttExtendPackageV2Response {

    /**
     * 应用包id
     **/
    @JSONField(name = "package_id")
    private String packageId;

}
