package org.jeecg.modules.advert.api.jrtt.project.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.bo.jrtt.project
 * @className: TrackUrlSetting
 * @author: Eric
 * @description: TODO
 * @date: 2023/2/17 14:30
 * @version: 1.0
 */
@Data
public class TrackUrlSetting {

    @JSONField(name = "track_url_type")
    private String trackUrlType;

    @JSONField(name = "action_track_url")
    private String actionTrackUrl;

}
