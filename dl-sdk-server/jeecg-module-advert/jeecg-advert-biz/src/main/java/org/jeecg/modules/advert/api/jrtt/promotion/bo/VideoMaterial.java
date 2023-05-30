package org.jeecg.modules.advert.api.jrtt.promotion.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 视频素材信息
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class VideoMaterial {

    /**
     * 素材类型，允许值：CREATIVE_IMAGE_MODE_VIDEO 横版视频、CREATIVE_IMAGE_MODE_VIDEO_VERTICAL 竖版视频
     **/
    @JSONField(name = "image_mode")
    private String imageMode;

    /**
     * 视频ID
     **/
    @JSONField(name = "video_id")
    private String videoId;

    /**
     * 视频封面图片ID
     **/
    @JSONField(name = "video_cover_id")
    private String videoCoverId;

    /**
     * 抖音短视频ID
     **/
    @JSONField(name = "item_id")
    private Integer itemId;

}
