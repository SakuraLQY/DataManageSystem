package org.jeecg.modules.advert.api.jrtt.tool.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttPageInfoResponse;

/**
 * @Description: 获取抖音授权关系响应参数-列表
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class JrttAwemeAuthListListResponse {

    /**
     * 应用包id
     **/
    @JSONField(name = "auth_type")
    private String authType;

    /**
     * 应用包id
     **/
    @JSONField(name = "aweme_id")
    private String awemeId;

    /**
     * 应用包id
     **/
    @JSONField(name = "aweme_name")
    private String awemeName;

    /**
     * 应用包id
     **/
    @JSONField(name = "auth_status")
    private String authStatus;

    /**
     * 应用包id
     **/
    @JSONField(name = "sub_status")
    private String subStatus;

    /**
     * 应用包id
     **/
    @JSONField(name = "start_time")
    private String startTime;

    /**
     * 应用包id
     **/
    @JSONField(name = "end_time")
    private String endTime;

}
