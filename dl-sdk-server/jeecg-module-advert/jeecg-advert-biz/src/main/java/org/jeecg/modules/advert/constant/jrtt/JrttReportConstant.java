package org.jeecg.modules.advert.constant.jrtt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 今日头条 自定义报表 枚举
 * @Author: chenyw
 * @Date: 2023-02-15
 * @Version: V1.0
 */
public class JrttReportConstant {

    /**
     * 维度列表。
     **/
    public static final List<String> CUSTOM_DIMENSIONS = new ArrayList<>(
        Arrays.asList("stat_time_day", "cdp_project_id", "cdp_project_name","platform"));

    /**
     * 指标列表。
     **/
    public static final List<String> CUSTOM_METRICS = new ArrayList<>(
        Arrays.asList("stat_cost", "show_cnt", "download_finish_cnt", "click_cnt"));

    /**
     * 分组条件。
     **/
    public static final List<String> CAMPAIGN_GROUPBY = new ArrayList<>(
        Arrays.asList("STAT_GROUP_BY_FIELD_ID","STAT_GROUP_BY_FIELD_STAT_TIME"));

}
