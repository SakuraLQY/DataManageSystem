package org.jeecg.modules.advert.api.ks.material.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.core.io.FileSystemResource;

/**
 * @Description: 上传广告图片
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class KsImageAdRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 图片上传方式
     **/
    @JSONField(name = "upload_type")
    private Integer uploadType;

    /**
     * 图片的md5值
     **/
    @JSONField(name = "signature")
    private String signature;

    /**
     * 图片文件
     **/
    @JSONField(name = "file")
    private FileSystemResource file;

}
