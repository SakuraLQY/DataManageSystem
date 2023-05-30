package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: 获取账号投放成本
 * @author: chenglin
 * @date: 2023年05月23日 11:53
 */
@Data
public class ReportBillCostBo implements Serializable {
    private Integer gameId;

    private Integer subGameId;

    private String costDesc;

    private Date dateTime;

    private BigDecimal money;

    private Integer exhibition;

    private Integer download;
}
