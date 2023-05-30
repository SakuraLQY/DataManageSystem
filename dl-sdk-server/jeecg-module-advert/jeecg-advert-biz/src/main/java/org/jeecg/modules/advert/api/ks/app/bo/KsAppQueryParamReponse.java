package org.jeecg.modules.advert.api.ks.app.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class KsAppQueryParamReponse {

    @JSONField(name = "app_id")
    private Long appId;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "app_version")
    private String appVersion;

    @JSONField(name = "app_name")
    private String appName;

    @JSONField(name = "app_icon_url")
    private String appIconUrl;

    @JSONField(name = "image_token")
    private String imageToken;

    @JSONField(name = "package_name")
    private String packageName;

    @JSONField(name = "platform")
    private Integer platform;

    @JSONField(name = "update_time")
    private Long updateTime;

    @JSONField(name = "use_sdk")
    private Integer useSdk;

    @JSONField(name = "app_privacy_url")
    private String appPrivacyUrl;

    @JSONField(name = "scan_status")
    private Integer scanStatus;

    @JSONField(name = "permission_information")
    private String permissionInformation;

    @JSONField(name = "real_app_version")
    private String realAppVersion;

    @JSONField(name = "package_size")
    private Long packageSize;

    @JSONField(name = "app_detail_image_token")
    private String appDetailImageToken;

    @JSONField(name = "offline_app_stores")
    private String offlineAppStores;
}
