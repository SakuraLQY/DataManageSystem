package org.jeecg.modules.advert.api.jrtt.assets.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.List;
import lombok.Data;

/**
 * @Description: 创建监测链接组 请求参数
 * @Author: lili
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttCreatTrackUrlRequest {

    /**
     * 广告主id
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 资产ID
     **/
    @JSONField(name = "assets_id")
    private Long assetsId;

    /**
     * 应用下载链接
     **/
    @JSONField(name = "download_url")
    private String downloadUrl;

    /**
     * 监测链接组信息
     **/
    @JSONField(name = "track_url_groups")
    private List<JrttCreatTrackUrlGroupsRequest> trackUrlGroups;

}
