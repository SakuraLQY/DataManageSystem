package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: 成本查询数据
 * @author: chenglin
 * @date: 2023年05月17日 17:05
 */
@Data
public class RetainCostBo implements Serializable {

    private Date dateTime;

    private BigDecimal costMoney;
}
