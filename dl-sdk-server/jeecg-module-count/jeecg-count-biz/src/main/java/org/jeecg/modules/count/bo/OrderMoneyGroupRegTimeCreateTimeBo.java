package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @Description: OrderMoneyGroupRegTimeCreateTimeBo
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class OrderMoneyGroupRegTimeCreateTimeBo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 金额
     **/
    private BigDecimal money;

    /**
     * 用户创建时间
     **/
    private Date userCreateTime;

    /**
     * 创建时间
     **/
    private Date createTime;

}
