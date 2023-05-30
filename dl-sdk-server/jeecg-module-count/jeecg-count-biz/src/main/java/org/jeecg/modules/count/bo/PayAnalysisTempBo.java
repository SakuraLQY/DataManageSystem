package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: 用于数据库ct_order表
 * @author: chenglin
 * @date: 2023年05月24日 15:13
 */
@Data
public class PayAnalysisTempBo implements Serializable {

    private Date dateTime;

    private Integer id;

    private Integer payUserNum;

    private BigDecimal payMoney;

}
