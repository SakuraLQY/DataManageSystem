package org.jeecg.modules.count.bo;

import java.util.List;
import lombok.Data;

/**
 * @Description: ReportConfigWeek 周报配置
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
public class ReportConfigWeek {

    /**
     * 一级分组
     **/
    private String firstGroup;

    /**
     * 一级分组的内容
     **/
    List<ReportConfigWeekData> data;
}
