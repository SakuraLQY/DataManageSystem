package org.jeecg.modules.advert.api.xingtu.creative.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建程序化创意（营销链路）-Creative  请求参数
 * @Author: lili
 * @Date: 2023-03-22
 * @Version: V1.0
 */
@Data
public class XingtuCreativeRequest {

    /**
     * 创意标题素材
     **/
    @JSONField(name = "title_materials")
    private List<XingtuTitleMaterialsRequest> titleMaterials ;

    /**
     * 创意视频素材
     **/
    @JSONField(name = "video_materials")
    private List<XingtuVideoMaterialsRequest> videoMaterials ;


}
