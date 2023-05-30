package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: PayOrderTotalVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/4 15:46
 */
@Data
public class PayOrderTotalVo {

    /**
     * 总金额
     */
    private BigDecimal totalMoney;

    /**
     * 总条数
     */
    private String sum;
}
