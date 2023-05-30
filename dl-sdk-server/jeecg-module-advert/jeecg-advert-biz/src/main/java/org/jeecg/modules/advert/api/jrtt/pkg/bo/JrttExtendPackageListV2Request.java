package org.jeecg.modules.advert.api.jrtt.pkg.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.constant.jrtt.JrttExtendPackageMode;

/**
 * @Description: 创建应用分包请求参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttExtendPackageListV2Request {

    /**
     * 账户id，accout_type类型对应账户ID
     **/
    @JSONField(name = "account_id")
    private Long accountId;

    /**
     * 账户类型 允许值：BP 巨量纵横组织、 AD 广告主账号、STAR 星图
     **/
    @JSONField(name = "account_type")
    private String accountType;

    /**
     * 应用包ID，可通过「查询应用信息」接口查询获取
     **/
    @JSONField(name = "package_id")
    private String packageId;

    /**
     * 页码，默认值为1
     **/
    @JSONField(name = "page")
    private Integer page;

    /**
     * 页面大小，默认值为10，最大不超过100
     **/
    @JSONField(name = "page_size")
    private Integer pageSize;

}
