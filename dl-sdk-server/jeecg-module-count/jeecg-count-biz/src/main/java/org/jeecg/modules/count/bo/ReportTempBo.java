package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: TODO
 * @author: chenglin
 * @date: 2023年05月23日 13:37
 */
@Data
public class ReportTempBo implements Serializable {
    /**日期**/
    private Date dateTime;
    /**类型**/
    private String type;
    /**金额**/
    private BigDecimal cost;
    /**账号余额**/
    private BigDecimal outCostMoney;
    /**
     游戏+展示+下载拼接
     **/
    private String OtherData;

}
