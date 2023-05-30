package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: 接受数据库查询的对象
 * @author: chenglin
 * @date: 2023年05月24日 18:52
 */
@Data
public class LtvAnalysisBo implements Serializable {
    /**ID**/
    private Date dateTime;
    /**ID**/
    private Integer id;

    private Integer countUser;
    private BigDecimal ltv1;
    private BigDecimal ltv2;
    private BigDecimal ltv3;
    private BigDecimal ltv4;
    private BigDecimal ltv5;
    private BigDecimal ltv6;
    private BigDecimal ltv7;
    private BigDecimal ltv15;
    private BigDecimal ltv30;
    private BigDecimal ltv45;
    private BigDecimal ltv60;
    private BigDecimal ltv90;
    private BigDecimal ltv120;
    private BigDecimal ltv150;
}
