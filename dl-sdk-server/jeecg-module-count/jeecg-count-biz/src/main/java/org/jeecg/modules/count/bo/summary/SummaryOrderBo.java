package org.jeecg.modules.count.bo.summary;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: SummaryOrderBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class SummaryOrderBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 用户总数
     */
    private Integer countUser = 0;

    /**
     * 总金额
     */
    private BigDecimal sumMoney = BigDecimal.ZERO;

}
