package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 获取已创建资产App 响应参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetAssetsAppResponse {

    /**
     * 应用资产ID
     **/
    @JSONField(name = "asset_id")
    private Long assetId;

    /**
     * 应用名
     **/
    @JSONField(name = "app_name")
    private String appName;

    /**
     * 应用类型
     **/
    @JSONField(name = "app_type")
    private String appType;

    /**
     * 应用下载链接
     **/
    @JSONField(name = "download_url")
    private String downloadUrl;

    /**
     * 应用ID
     **/
    @JSONField(name = "app_id")
    private Integer appId;

    /**
     * 母包ID
     **/
    @JSONField(name = "package_id")
    private String packageId;

    /**
     * 应用包名
     **/
    @JSONField(name = "package_name")
    private String packageName;

    /**
     * 资产来源
     **/
    @JSONField(name = "role")
    private String role;

    /**
     * 资产名
     **/
    @JSONField(name = "asset_name")
    private String assetName;

}
