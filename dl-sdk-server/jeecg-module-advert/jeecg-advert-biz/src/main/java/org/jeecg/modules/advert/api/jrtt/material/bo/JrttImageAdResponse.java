package org.jeecg.modules.advert.api.jrtt.material.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 创建图片素材 响应参数
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttImageAdResponse {

    /**
     * 图片id
     **/
    @JSONField(name = "id")
    private String id;

    /**
     * 图片大小
     **/
    @JSONField(name = "size")
    private String size;

    /**
     * 图片宽度
     **/
    @JSONField(name = "width")
    private String width;

    /**
     * 图片高度
     **/
    @JSONField(name = "height")
    private String height;

    /**
     * 图片预览地址
     **/
    @JSONField(name = "url")
    private String url;

    /**
     * 图片格式
     **/
    @JSONField(name = "format")
    private String format;

    /**
     * 图片md5
     **/
    @JSONField(name = "signature")
    private String signature;

    /**
     * 素材id
     **/
    @JSONField(name = "material_id")
    private String materialId;
}
