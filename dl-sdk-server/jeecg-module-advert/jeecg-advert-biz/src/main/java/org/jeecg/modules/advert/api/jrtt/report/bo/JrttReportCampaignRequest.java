package org.jeecg.modules.advert.api.jrtt.report.bo;


import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取广告组数据 请求参数
 * @Author: chenyw
 * @Date: 2023-04-26
 * @Version: V1.0
 */
@Data
public class JrttReportCampaignRequest {


    /**
     * 账户id，accout_type类型对应账户ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 起始日期
     **/
    @JSONField(name = "start_date")
    private String startDate;

    /**
     * 结束日期
     **/
    @JSONField(name = "end_date")
    private String endDate;

    /**
     * 页面大小
     **/
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     * 汇总条件
     **/
    @JSONField(name = "group_by")
    private List<String> groupBy;

}
