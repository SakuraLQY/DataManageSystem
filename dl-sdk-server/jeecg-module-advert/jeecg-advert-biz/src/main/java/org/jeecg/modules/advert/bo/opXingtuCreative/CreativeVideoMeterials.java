package org.jeecg.modules.advert.bo.opXingtuCreative;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lili
 * @Description 星图创意中的VideoMeterials
 * @Date 16:30 2023/2/20
 **/
@Data
public class CreativeVideoMeterials {

    /**
     * 图片ID
     **/
    private String imageId  ;

    /**
     * 视频ID
     **/
    private String videoId   ;

    /**
     * 视频素材id
     **/
    private Integer videoMaterialId;

    /**
     * 视频素材帧
     **/
    private Integer videoMaterialFrameRate;

    /**
     * 视频素材封面
     **/
    private String videoMaterialScreenShot;






}
