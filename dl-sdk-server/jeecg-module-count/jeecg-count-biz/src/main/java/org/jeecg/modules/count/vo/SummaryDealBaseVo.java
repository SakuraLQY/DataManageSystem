package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: SummaryDealBaseVo
 * @Author: jeecg-boot
 * @Date:   2023-05-8
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class SummaryDealBaseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**成本*/
    @ApiModelProperty(value = "消耗")
    private BigDecimal costMoney = BigDecimal.ZERO;
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer countUser = 0;
    /**注册单价 成本/新用户数*/
    @ApiModelProperty(value = "注册单价")
    private BigDecimal costCountUser = BigDecimal.ZERO;
    /**首日付费人数 */
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayuser = 0;
    /**首日付费额*/
    @ApiModelProperty(value = "新增付费")
    private BigDecimal firstMoney = BigDecimal.ZERO;
    /**首日付费单价 首日付费成本/首日付费人数 costMoney/firstPayUser */
    @ApiModelProperty(value = "新增付费单价")
    private BigDecimal firstPayuserCostMoney = BigDecimal.ZERO;
    /**arpu 首日付费额/注册数 firstMoney/countUser*/
    @ApiModelProperty(value = "首日arpu")
    private BigDecimal firstArpu = BigDecimal.ZERO;
    /**arppu 首日付费额/首日回款率 firstMoney/firstPayuser */
    @ApiModelProperty(value = "首日arppu")
    private BigDecimal firstArppu = BigDecimal.ZERO;
    /**首日ROI 首日回本/成本 day1/cost */
    @ApiModelProperty(value = "首日ROI")
    private BigDecimal firstRoi = BigDecimal.ZERO;
    /** 累积付费额 */
    @ApiModelProperty(value = "累积付费额")
    private BigDecimal totalMoney = BigDecimal.ZERO;
    /** 活跃付费人数*/
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayuser = 0;
    /** 活跃付费金额*/
    @ApiModelProperty(value = "活跃付费金额")
    private BigDecimal aliveMoney = BigDecimal.ZERO;

}
