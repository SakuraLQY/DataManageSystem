package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 添加资产app_asset 请求参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttAddAssetsAppAssetRequest {

    /**
     * 应用名称
     **/
    @JSONField(name = "name")
    private String name;

    /**
     * 应用包名
     **/
    @JSONField(name = "package_name")
    private String packageName;

    /**
     *
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
     * 应用类型
     **/
    @JSONField(name = "app_type")
    private String appType;

}
