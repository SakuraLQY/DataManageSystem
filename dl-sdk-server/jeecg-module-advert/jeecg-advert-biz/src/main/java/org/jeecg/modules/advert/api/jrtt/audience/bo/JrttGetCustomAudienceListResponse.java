
package org.jeecg.modules.advert.api.jrtt.audience.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @Description: 人群包列表  响应参数
 * @Author: lili
 * @Date: 2023-02-13
 * @Version: V1.0
 */
@Data
public class JrttGetCustomAudienceListResponse {

    /**
     * 人群包ID
     **/
    @JSONField(name = "custom_audience_id")
    @JsonProperty("custom_audience_id")
    private Long customAudienceId;

    /**
     * 人群包是否删除
     **/
    @JSONField(name = "isdel")
    @JsonProperty("isdel")
    private Integer isdel;

    /**
     * 数据源ID
     **/
    @JSONField(name = "data_source_id")
    @JsonProperty("data_source_id")
    private String dataSourceId;

    /**
     * 人群包名称
     **/
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

    /**
     * 人群包来源
     **/
    @JSONField(name = "source")
    @JsonProperty("source")
    private String source;

    /**
     * 人群包状态
     **/
    @JSONField(name = "status")
    @JsonProperty("status")
    private Integer status;

    /**
     * 人群包可投放状态
     **/
    @JSONField(name = "delivery_status")
    @JsonProperty("delivery_status")
    private String deliveryStatus;

    /**
     * 人群包覆盖人群数目
     **/
    @JSONField(name = "cover_num")
    @JsonProperty("cover_num")
    private Integer coverNum;

    /**
     * 上传数据源包含的人群数目
     **/
    @JSONField(name = "upload_num")
    @JsonProperty("upload_num")
    private Integer uploadNum;

    /**
     * 人群包标签
     **/
    @JSONField(name = "tag")
    @JsonProperty("tag")
    private String tag;

}
