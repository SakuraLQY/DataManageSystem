package org.jeecg.modules.advert.api.jrtt.material.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.File;
import java.nio.file.FileSystem;
import java.util.List;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.tool.bo.JrttAwemeAuthListFilteringRequest;
import org.springframework.core.io.FileSystemResource;

/**
 * @Description: 上传广告图片
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttImageAdRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 图片上传方式
     **/
    @JSONField(name = "upload_type")
    private String uploadType;


    /**
     * 图片的md5值
     **/
    @JSONField(name = "image_signature")
    private String imageSignature;

    /**
     * 图片文件
     **/
    @JSONField(name = "image_file")
    private FileSystemResource imageFile;

    /**
     * 素材的文件名
     **/
    @JSONField(name = "filename")
    private String filename;
}
