package org.jeecg.modules.count.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: OrderMoneyGroupBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class OrderDateGroupRateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    private String day = "";
    /**
     * 数量
     */
    private Integer count = 0;
    /**
     * 比率
     */
    private BigDecimal rate = BigDecimal.ZERO;

}
