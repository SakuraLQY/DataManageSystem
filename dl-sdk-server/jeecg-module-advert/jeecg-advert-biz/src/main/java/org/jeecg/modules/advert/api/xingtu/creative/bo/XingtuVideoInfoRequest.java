package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 创建程序化创意（营销链路）- video_materials-video_info  请求参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuVideoInfoRequest {

    /**
     * 视频ID
     **/
    @JSONField(name = "video_id")
    private String videoId   ;

}
