package org.jeecg.modules.advert.api.jrtt.material.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.File;
import lombok.Data;

/**
 * @Description: 上传视频 响应参数
 * @Author: lili
 * @Date:   2023-03-09
 * @Version: V1.0
 */
@Data
public class JrttVideoAdResponse {

    /**
     * 视频ID
     **/
    @JSONField(name = "video_id")
    private String videoId ;

    /**
     * 视频大小
     **/
    @JSONField(name = "size")
    private Integer size ;

    /**
     * 视频宽度
     **/
    @JSONField(name = "width")
    private Integer width;

    /**
     * 视频高度
     **/
    @JSONField(name = "height")
    private Integer height;

    /**
     * 视频地址
     **/
    @JSONField(name = "video_url")
    private String videoUrl;

    /**
     * 视频时长
     **/
    @JSONField(name = "duration")
    private Integer duration;

    /**
     * 素材id
     **/
    @JSONField(name = "material_id")
    private Long materialId;

}
