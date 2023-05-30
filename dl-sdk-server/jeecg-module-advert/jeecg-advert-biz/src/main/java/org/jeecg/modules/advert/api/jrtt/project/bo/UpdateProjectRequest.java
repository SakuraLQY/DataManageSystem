package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.api.jrtt.project.bo
 * @className: UpdateProjectRequest
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/22 15:23
 * @version: 1.0
 */
@Data
public class UpdateProjectRequest {

    @JSONField(name = "advertiser_id")
    private java.lang.Long advertiserId;

    @JSONField(name = "project_id")
    private java.lang.Long projectId;

    @JSONField(name = "name")
    private java.lang.String name;

    @JSONField(name = "audience_extend")
    private java.lang.String audienceExtend;

    @JSONField(name = "keywords")
    private Keyword[] keywords;

    @JSONField(name = "download_mode")
    private java.lang.String downloadMode;

    @JSONField(name = "audience")
    private Audience audience;

    @JSONField(name = "delivery_setting")
    private DeliverySetting deliverySetting;

    @JSONField(name = "track_url_setting")
    private TrackUrlSetting trackUrlSetting;

}
