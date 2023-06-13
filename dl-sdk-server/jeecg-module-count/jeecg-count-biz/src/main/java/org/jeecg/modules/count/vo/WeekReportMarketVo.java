package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: WeekReportVo 数据周报- 大盘同期环比数据
 * @Author: jeecg-boot
 * @Date: 2023-05-19
 * @Version: V1.0
 */
@Data
public class WeekReportMarketVo {

    /**
     * 日期
     **/
    private String day = "";

    /**
     * 消耗
     **/
    private String costMoney = "0";

    /**
     * 新增
     **/
    private String countUser = "0";

    /**
     * DAU
     **/
    private String countDau = "0";

    /**
     * 新增付费
     **/
    private String firstMoney = "0";

    /**
     * 活跃付费
     **/
    private String aliveMoney= "0";

    /**
     * 活跃付费ios
     **/
    private String aliveMoneyIos= "0";

    /**
     * 分成所得
     **/
    private String divideMoney= "0";

}
