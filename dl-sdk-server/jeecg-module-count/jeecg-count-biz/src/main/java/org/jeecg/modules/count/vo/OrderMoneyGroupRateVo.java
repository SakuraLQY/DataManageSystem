package org.jeecg.modules.count.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: OrderMoneyGroupBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class OrderMoneyGroupRateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 金额
     */
    @Excel(name = "面额", width = 15)
    private BigDecimal money = BigDecimal.ZERO;
    /**
     * 数量
     */
    @Excel(name = "数量", width = 15)
    private Integer count = 0;
    /**
     * 比率
     */
    @Excel(name = "比率", width = 15)
    private BigDecimal rate = BigDecimal.ZERO;

}
