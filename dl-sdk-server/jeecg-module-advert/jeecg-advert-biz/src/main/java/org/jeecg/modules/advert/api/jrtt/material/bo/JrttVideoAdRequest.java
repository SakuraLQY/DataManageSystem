package org.jeecg.modules.advert.api.jrtt.material.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.io.File;
import lombok.Data;
import org.apache.http.entity.mime.content.FileBody;
import org.springframework.core.io.FileSystemResource;

/**
 * @Description: 上传视频 请求参数
 * @Author: lili
 * @Date:   2023-03-09
 * @Version: V1.0
 */
@Data
public class JrttVideoAdRequest {

    /**
     * 广告主ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId ;

    /**
     * 图片的md5值
     **/
    @JSONField(name = "upload_type")
    private String uploadType;

    /**
     * 图片的md5值
     **/
    @JSONField(name = "video_signature")
    private String videoSignature;

    /**
     * 图片文件
     **/
    @JSONField(name = "video_file")
    private FileSystemResource videoFile;

    /**
     * 素材的文件名
     **/
    @JSONField(name = "filename")
    private String filename;

}
