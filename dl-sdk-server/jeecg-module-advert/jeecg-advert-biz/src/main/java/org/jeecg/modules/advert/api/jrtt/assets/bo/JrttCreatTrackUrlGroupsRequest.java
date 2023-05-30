package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 监测链接组信息 请求参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttCreatTrackUrlGroupsRequest {

    /**
     * 点击（监测链接）
     **/
    @JSONField(name = "action_track_url")
    private String actionTrackUrl;

    /**
     * 展示（监测链接）
     **/
    @JSONField(name = "track_url")
    private String trackUrl;

    /**
     * 视频播放（监测链接）
     **/
    @JSONField(name = "video_play_track_url")
    private String videoPlayTrackUrl;

    /**
     * 视频播完（监测链接）
     **/
    @JSONField(name = "video_play_done_track_url")
    private String videoPlayDoneTrackUrl;

    /**
     * 视频有效播放（监测链接）
     **/
    @JSONField(name = "video_play_effective_track_url")
    private String videoPlayEffectiveTrackUrl;

    /**
     * 监测链接组名称
     **/
    @JSONField(name = "track_url_group_name")
    private String trackUrlGroupName;

}
