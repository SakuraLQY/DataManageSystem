package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 创建自定义创意（营销链路）-video_materials  请求参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuCustomVideoMaterialsRequest {

    /**
     * 视频封面图片
     **/
    @JSONField(name = "image_info")
    private XingtuImageInfoRequest  imageInfo  ;

    /**
     * 视频封面图片
     **/
    @JSONField(name = "video_info")
    private XingtuVideoInfoRequest  videoInfo  ;

}
