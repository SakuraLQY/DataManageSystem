package org.jeecg.modules.advert.api.ks.material.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description: 上传视频 响应参数
 * @Author: lili
 * @Date:   2023-03-09
 * @Version: V1.0
 */
@Data
public class KsVideoAdResponse {

    /**
     * 视频ID
     **/
    @JSONField(name = "photo_id")
    private String photoId ;

    /**
     * 视频 md5 值
     **/
    @JSONField(name = "signature")
    private String signature ;

}
