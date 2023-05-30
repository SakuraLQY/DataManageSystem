package org.jeecg.modules.advert.api.jrtt.report.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: 广告组成本 列表
 * @Author: chenyw
 * @Date: 2023-02-08
 * @Version: V1.0
 */
@Data
public class CampaignList {

    /**
     * 广告组id，分组条件包含STAT_GROUP_BY_FIELD_ID时，返回具体值
     **/
    @JSONField(name = "campaign_id")
    private Long campaignId;

    /**
     * 展现数据-总花费 表示广告在投放期内的预估花费金额,当天数据可能会有波动，次日稳定
     **/
    @JSONField(name = "cost")
    private BigDecimal cost;

    /**
     * 广告组name，分组条件包含STAT_GROUP_BY_FIELD_ID时，返回具体值
     **/
    @JSONField(name = "campaign_name")
    private String campaignName;

    /**
     * 转化数据-转化数
     **/
    @JSONField(name = "convert")
    private Integer convert;

    /**
     * 展现数据-平均点击单价
     **/
    @JSONField(name = "avg_click_cost")
    private BigDecimal avgClickCost;

    /**
     * 展现数据-展示数
     **/
    @JSONField(name = "show")
    private Integer show;

    /**
     * 数据起始时间，分组条件包含 STAT_GROUP_BY_FIELD_STAT_TIME 时返回，格式为：yyyy-MM-dd HH:mm:ss
     **/
    @JSONField(name = "stat_datetime")
    private String statDatetime;

    /**
     * 转化数据-转化率
     **/
    @JSONField(name = "convert_rate")
    private Double convertRate;

    /**
     * 展现数据-点击率
     **/
    @JSONField(name = "ctr")
    private Double ctr;

    /**
     * 展现数据-平均千次展现费用
     **/
    @JSONField(name = "avg_show_cost")
    private BigDecimal avgShowCost;

    /**
     * 展现数据-点击数
     **/
    @JSONField(name = "click")
    private Integer click;

    /**
     * 转化数据-转化成本
     **/
    @JSONField(name = "convert_cost")
    private BigDecimal convertCost;

    /**
     * 平台
     **/
    @JSONField(name = "platform")
    private String platform;
}
