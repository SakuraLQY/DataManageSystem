package org.jeecg.modules.advert.api.jrtt.report.bo;


import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @Description: 获取报表请求参数
 * @Author: chenyw
 * @Date: 2023-04-26
 * @Version: V1.0
 */
@Data
public class JrttReportCustomRequest {


    /**
     * 账户id，accout_type类型对应账户ID
     **/
    @JSONField(name = "advertiser_id")
    private Long advertiserId;

    /**
     * 维度列表
     **/
    @JSONField(name = "dimensions")
    private List<String> dimensions;

    /**
     * 指标列表
     **/
    @JSONField(name = "metrics")
    private List<String> metrics;

    /**
     * 过滤条件
     **/
    @JSONField(name = "filters")
    private List<Object> filters = new ArrayList();

    /**
     * 排序
     **/
    @JSONField(name = "order_by")
    private List<Object> orderBy = new ArrayList();

    /**
     * 页面大小
     **/
    @JSONField(name = "page_size")
    private Integer pageSize;

    /**
     * 开始时间。格式为：yyyy-MM-dd
     **/
    @JSONField(name = "start_time")
    private String startTime;

    /**
     * 结束时间。格式为：yyyy-MM-dd
     **/
    @JSONField(name = "end_time")
    private String endTime;

}
