package org.jeecg.modules.count.bo;

import cn.hutool.core.date.DateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: 查询成本
 * @author: chenglin
 * @date: 2023年05月22日 18:48
 */
@Data
public class ReportAccountCostBo implements Serializable {
    private Integer accountId;

    private BigDecimal costMoney;

    private Integer exhibition;

    private Integer download;

    private Date updateTime;

}
