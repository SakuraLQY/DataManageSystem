package org.jeecg.modules.advert.api.ks.material.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.core.io.FileSystemResource;

/**
 * @Description: 上传视频 请求参数
 * @Author: lili
 * @Date:   2023-03-09
 * @Version: V1.0
 */
@Data
public class KsVideoAdRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 视频的md5值
     **/
    @JSONField(name = "signature")
    private String signature;

    /**
     * 视频文件
     **/
    @JSONField(name = "file")
    private FileSystemResource file;

    /**
     * 视频名称
     **/
    @JSONField(name = "photo_name")
    private String photoName;

}
