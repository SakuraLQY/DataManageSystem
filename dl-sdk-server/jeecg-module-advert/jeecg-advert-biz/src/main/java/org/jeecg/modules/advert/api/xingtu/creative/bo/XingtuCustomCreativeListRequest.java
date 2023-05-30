package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建自定义创意（营销链路）-creative_list   请求参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuCustomCreativeListRequest {

    /**
     * 素材类型
     **/
    @JSONField(name = "image_mode")
    private String  imageMode  ;

    /**
     * 创意标题素材
     **/
    @JSONField(name = "title_material")
    private XingtuTitleMaterialsRequest titleMaterials ;

    /**
     * 创意视频素材
     **/
    @JSONField(name = "video_material")
    private XingtuCustomVideoMaterialsRequest videoMaterials ;


}
