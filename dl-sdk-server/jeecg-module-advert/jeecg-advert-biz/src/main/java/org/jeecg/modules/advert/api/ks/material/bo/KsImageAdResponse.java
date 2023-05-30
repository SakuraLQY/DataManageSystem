package org.jeecg.modules.advert.api.ks.material.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 创建图片素材 响应参数
 * @Author: lili
 * @Date: 2023-03-10
 * @Version: V1.0
 */
@Data
public class KsImageAdResponse {

    /**
     * 图片大小
     **/
    @JSONField(name = "size")
    private Long size;

    /**
     * 图片宽度
     **/
    @JSONField(name = "width")
    private Long width;

    /**
     * 图片高度
     **/
    @JSONField(name = "height")
    private Long height;

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
     * 图片 token
     **/
    @JSONField(name = "image_token")
    private String imageToken;

    /**
     * 图片库图片ID
     **/
    @JSONField(name = "pic_id")
    private String picId;
}
