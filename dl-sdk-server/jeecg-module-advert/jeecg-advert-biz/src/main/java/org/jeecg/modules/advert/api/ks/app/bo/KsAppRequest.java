package org.jeecg.modules.advert.api.ks.app.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.core.io.FileSystemResource;

@Data
public class KsAppRequest {

    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    @JSONField(name = "file")
    private FileSystemResource file;

    @JSONField(name = "app_version")
    private String appVersion;

    @JSONField(name = "app_name")
    private String appName;

    @JSONField(name = "image_token")
    private String imageToken;

    @JSONField(name = "package_name")
    private String packageName;

    @JSONField(name = "platform")
    private Integer platform;

    @JSONField(name = "url")
    private String url;

    @JSONField(name = "app_privacy_url")
    private String appPrivacyUrl;



}
