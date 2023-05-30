package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @description: TODO
 * @author: chenglin
 * @date: 2023年05月29日 10:54
 */
@Data
public class StatHourVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**日期*/
    @ApiModelProperty(value = "日期")
    private String dateTime;
    /**日期*/
    @ApiModelProperty(value = "广告名称")
    private String dealName;
    /**游戏名称*/
    @ApiModelProperty(value = "游戏名称")
    private String gameName;
    /**激活数*/
    @ApiModelProperty(value = "激活数")
    private Integer countActive = 0;
    /**全部激活数*/
    @ApiModelProperty(value = "全部激活数")
    private Integer countAllActive = 0;
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer regCount = 0;
    /**全部注册数*/
    @ApiModelProperty(value = "全部注册数")
    private Integer regAllCount = 0;
    /**有效注册数 注册数/激活数 */
    @ApiModelProperty(value = "有效注册数")
    private Integer validReg = 0;
    /**全部有效注册数 */
    @ApiModelProperty(value = "全部有效注册数")
    private Integer AllValidReg = 0;
    /**新增付费人数 */
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser = 0;
    /**全部新增付费人数*/
    @ApiModelProperty(value = "全部新增付费人数")
    private Integer AllFirstPayUser = 0;
    /**新增付费金额*/
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstPayMoney = BigDecimal.ZERO;
    /**全部新增付费金额*/
    @ApiModelProperty(value = "全部新增付费金额")
    private BigDecimal AllFirstPayMoney = BigDecimal.ZERO;
    /**新增付费率 */
    @ApiModelProperty(value = "新增付费率")
    private String firstPayRate ;
    /** 全部新增付费率*/
    @ApiModelProperty(value = "全部新增付费率")
    private String AllFirstPayRate;
    /** 活跃人数*/
    @ApiModelProperty(value = "活跃人数")
    private Integer countDau = 0;
    /**全部活跃人数 */
    @ApiModelProperty(value = "全部活跃人数")
    private Integer AllCountDau = 0;
    /** 活跃付费人数 */
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayUser = 0;
    /**全部活跃付费人数 */
    @ApiModelProperty(value = "全部活跃付费人数")
    private Integer AllAlivePayUser = 0;
    /** 付费总额 */
    @ApiModelProperty(value = "付费总额")
    private BigDecimal totalMoney = BigDecimal.ZERO;
    /** 全部付费总额*/
    @ApiModelProperty(value = "全部付费总额")
    private BigDecimal AllTotalMoney = BigDecimal.ZERO;
    /** 活跃付费率*/
    @ApiModelProperty(value = "活跃付费率")
    private String alivePayRate ;
    @ApiModelProperty(value = "全部活跃付费率")
    private String AllAlivePayRate;
    //计算arpu
    /** ARPPU 活跃付费额/日活 aliveMoney/alivePayuser*/
    @ApiModelProperty(value = "首日ARPU")
    private BigDecimal firstArpu = BigDecimal.ZERO;
    /** 总付费率 活跃付费人数/DAU*/
    @ApiModelProperty(value = "全部首日ARPU")
    private BigDecimal allFirstArpu = BigDecimal.ZERO;
    /** 次日留存*/
    @ApiModelProperty(value = "总ARPU")
    private BigDecimal totalArpu = BigDecimal.ZERO;
    /** 全部总的arpu*/
    @ApiModelProperty(value = "总ARPU")
    private BigDecimal allTotalArpu = BigDecimal.ZERO;


    /** 次日留存*/
    @ApiModelProperty(value = "3日留存")
    private String loyal2 ;
    /** 3日留存*/
    @ApiModelProperty(value = "3日留存")
    private String loyal3 ;
    /** 7日留存*/
    @ApiModelProperty(value = "4日留存")
    private String loyal7 ;
    /** 15日留存*/
    @ApiModelProperty(value = "5日留存")
    private String loyal15;
    /** 30日留存*/
    @ApiModelProperty(value = "6日留存")
    private String loyal30 ;

    /** ltv1*/
    @ApiModelProperty(value = "ltv1")
    private BigDecimal ltv1 = BigDecimal.ZERO;
    /** ltv2*/
    @ApiModelProperty(value = "ltv2")
    private BigDecimal ltv2 = BigDecimal.ZERO;
    /** ltv3*/
    @ApiModelProperty(value = "ltv3")
    private BigDecimal ltv3 = BigDecimal.ZERO;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv7 = BigDecimal.ZERO;
    /** ltv30*/
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv30 = BigDecimal.ZERO;
    /** ltv60*/
    @ApiModelProperty(value = "ltv60")
    private BigDecimal ltv60 = BigDecimal.ZERO;
    /** ltv90*/
    @ApiModelProperty(value = "ltv90")
    private BigDecimal ltv90 = BigDecimal.ZERO;
}
