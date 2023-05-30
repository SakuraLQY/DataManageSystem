package org.jeecg.modules.advert.api.jrtt.report.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.jeecg.modules.advert.api.jrtt.base.bo.JrttPageInfoResponse;

/**
 * @Description: 维度数据
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class Dimensions {

    /**
     * 项目id
     **/
    @JSONField(name = "cdp_project_id")
    private Long cdpProjectId;

    /**
     * 项目名
     **/
    @JSONField(name = "cdp_project_name")
    private String cdpProjectName;

    /**
     * 时间
     **/
    @JSONField(name = "stat_time_day")
    private String statTimeDay;

    /**
     * 平台
     **/
    @JSONField(name = "platform")
    private String platform;

}
