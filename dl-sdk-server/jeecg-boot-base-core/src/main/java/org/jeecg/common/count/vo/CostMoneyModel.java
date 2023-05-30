package org.jeecg.common.count.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: CostMoneyModel
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class CostMoneyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     **/
    private String id;

    /**
     * 成本
     **/
    private BigDecimal costMoney = BigDecimal.ZERO;
}
