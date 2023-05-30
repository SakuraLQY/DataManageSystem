package org.jeecg.modules.count.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @description: 临时数据用来进行计算的
 * @author: chenglin
 * @date: 2023年05月17日 19:10
 */
@Data
public class RetainPayTempBo implements Serializable {
    private Integer payLoyal2 = 0 ;
    private Integer payLoyal3 = 0 ;
    private Integer payLoyal7 = 0 ;
    private Integer payLoyal15 = 0 ;
    private Integer payLoyal30 = 0 ;
    private Integer payLoyal45 = 0 ;
    private Integer payLoyal60 = 0 ;
    private Integer payLoyal90 =0;
    private Integer payUserNumCount2 = 0;
    private Integer payUserNumCount3 = 0;
    private Integer payUserNumCount7 = 0;
    private Integer payUserNumCount15 = 0;
    private Integer payUserNumCount30 = 0;
    private Integer payUserNumCount45 = 0;
    private Integer payUserNumCount60 = 0;
    private Integer payUserNumCount90 = 0;
    private BigDecimal payUserCostCount2 = BigDecimal.ZERO;
    private BigDecimal payUserCostCount3 = BigDecimal.ZERO;
    private BigDecimal payUserCostCount7 = BigDecimal.ZERO;
    private BigDecimal payUserCostCount15 = BigDecimal.ZERO;
    private BigDecimal payUserCostCount30 = BigDecimal.ZERO;
    private BigDecimal payUserCostCount45 = BigDecimal.ZERO;
    private BigDecimal payUserCostCount60 = BigDecimal.ZERO;
    private BigDecimal payUserCostCount90 = BigDecimal.ZERO;
    private Integer countUserNum = 0;
    private Integer firstUserNum = 0;

}
