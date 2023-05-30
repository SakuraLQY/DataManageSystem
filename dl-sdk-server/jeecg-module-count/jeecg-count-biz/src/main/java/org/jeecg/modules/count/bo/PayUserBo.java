package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @description: 查询用户id，充值金额-针对新增付费用户数据的
 * @author: chenglin
 * @date: 2023年05月17日 17:49
 */
@Data
public class PayUserBo implements Serializable {
    private Integer userId;

    private BigDecimal payMoney;
}
