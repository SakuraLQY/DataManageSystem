package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: TODO
 * @author: chenglin
 * @date: 2023年05月23日 15:22
 */
@Data
public class ComputePayBo implements Serializable {

    /**注册时间**/
    private Date regTime;

    /**注册人数**/
    private Integer regCount;
    /**新增付费人数**/
    private Integer firstPayUser;
    /**新增付费**/
    private BigDecimal firstPay;
}
