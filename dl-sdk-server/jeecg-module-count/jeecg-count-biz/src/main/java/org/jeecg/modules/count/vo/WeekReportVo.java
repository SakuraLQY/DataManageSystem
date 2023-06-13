package org.jeecg.modules.count.vo;

import java.util.List;
import lombok.Data;

/**
 * @Description: WeekReportVo 数据周报VO
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Data
public class WeekReportVo {

    /**
     * 数据报表
     **/
    List<WeekReportDataVo> data;

    /**
     * 大盘同期环比
     **/
    List<WeekReportMarketVo> market;

    /**
     *  环比详细数据
     **/
    List<WeekReportDetailVo> detail;

}
