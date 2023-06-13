package org.jeecg.modules.count.modal;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 合作数据模型类
 * @author: chenglin
 * @date: 2023年06月13日 15:25
 */
@Data
public class StatHourModal implements Serializable {
    private static final long serialVersionUID = 1L;
    /**日期*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String dateTime;
    /**日期*/
    @Excel(name = "广告名称", width = 15)
    @ApiModelProperty(value = "广告名称")
    private String dealName;
    /**游戏名称*/
    @Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private String gameName;
    /**激活数*/
    @Excel(name = "激活数", width = 15)
    @ApiModelProperty(value = "激活数")
    private Integer countActive = 0;
    /**全部激活数*/
    @Excel(name = "全部激活数", width = 15)
    @ApiModelProperty(value = "全部激活数")
    private Integer countAllActive = 0;
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer regCount = 0;
    /**全部注册数*/
    @Excel(name = "全部注册数", width = 15)
    @ApiModelProperty(value = "全部注册数")
    private Integer regAllCount = 0;
    /**有效注册数 注册数/激活数 */
    @Excel(name = "有效注册数", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private Integer validReg = 0;
    /**全部有效注册数 */
    @Excel(name = "全部有效注册数", width = 15)
    @ApiModelProperty(value = "全部有效注册数")
    private Integer AllValidReg = 0;
    /**新增付费人数 */
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser = 0;
    /**全部新增付费人数*/
    @Excel(name = "全部新增付费人数", width = 15)
    @ApiModelProperty(value = "全部新增付费人数")
    private Integer AllFirstPayUser = 0;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstPayMoney = BigDecimal.ZERO;
    /**全部新增付费金额*/
    @Excel(name = "全部新增付费金额", width = 15)
    @ApiModelProperty(value = "全部新增付费金额")
    private BigDecimal AllFirstPayMoney = BigDecimal.ZERO;
    /**新增付费率 */
    @Excel(name = "新增付费率", width = 15)
    @ApiModelProperty(value = "新增付费率")
    private String firstPayRate ;
    /** 活跃人数*/
    @Excel(name = "活跃人数", width = 15)
    @ApiModelProperty(value = "活跃人数")
    private Integer countDau = 0;
    /**全部活跃人数 */
    @Excel(name = "全部活跃人数", width = 15)
    @ApiModelProperty(value = "全部活跃人数")
    private Integer AllCountDau = 0;
    /** 活跃付费人数 */
    @Excel(name = "活跃付费人数", width = 15)
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayUser = 0;
    /**全部活跃付费人数 */
    @Excel(name = "全部活跃付费人数", width = 15)
    @ApiModelProperty(value = "全部活跃付费人数")
    private Integer AllAlivePayUser = 0;
    /** 付费总额 */
    @Excel(name = "付费总额", width = 15)
    @ApiModelProperty(value = "付费总额")
    private BigDecimal totalMoney = BigDecimal.ZERO;
    /** 全部付费总额*/
    @Excel(name = "全部付费总额", width = 15)
    @ApiModelProperty(value = "全部付费总额")
    private BigDecimal AllTotalMoney = BigDecimal.ZERO;
    /** 活跃付费率*/
    @Excel(name = "活跃付费率", width = 15)
    @ApiModelProperty(value = "活跃付费率")
    private String alivePayRate ;
    //计算arpu
    /** ARPPU 活跃付费额/日活 aliveMoney/alivePayuser*/
    @Excel(name = "首日ARPU", width = 15)
    @ApiModelProperty(value = "首日ARPU")
    private BigDecimal firstArpu = BigDecimal.ZERO;
    /** 总付费率 活跃付费人数/DAU*/
    @Excel(name = "全部首日ARPU", width = 15)
    @ApiModelProperty(value = "全部首日ARPU")
    private BigDecimal allFirstArpu = BigDecimal.ZERO;
    /** 次日留存*/
    @Excel(name = "总ARPU", width = 15)
    @ApiModelProperty(value = "总ARPU")
    private BigDecimal totalArpu = BigDecimal.ZERO;
    /** 全部总的arpu*/
    @Excel(name = "全部总ARPU", width = 15)
    @ApiModelProperty(value = "总ARPU")
    private BigDecimal allTotalArpu = BigDecimal.ZERO;


    /** 次日留存*/
    @Excel(name = "3日留存", width = 15)
    @ApiModelProperty(value = "3日留存")
    private String loyal2 ;
    /** 3日留存*/
    @Excel(name = "3日留存", width = 15)
    @ApiModelProperty(value = "3日留存")
    private String loyal3 ;
    /** 7日留存*/
    @Excel(name = "4日留存", width = 15)
    @ApiModelProperty(value = "4日留存")
    private String loyal7 ;
    /** 15日留存*/
    @Excel(name = "5日留存", width = 15)
    @ApiModelProperty(value = "5日留存")
    private String loyal15;
    /** 30日留存*/
    @Excel(name = "6日留存", width = 15)
    @ApiModelProperty(value = "6日留存")
    private String loyal30 ;

    /** ltv1*/
    @Excel(name = "ltv1", width = 15)
    @ApiModelProperty(value = "ltv1")
    private BigDecimal ltv1 = BigDecimal.ZERO;
    /** ltv2*/
    @Excel(name = "ltv2", width = 15)
    @ApiModelProperty(value = "ltv2")
    private BigDecimal ltv2 = BigDecimal.ZERO;
    /** ltv3*/
    @Excel(name = "ltv3", width = 15)
    @ApiModelProperty(value = "ltv3")
    private BigDecimal ltv3 = BigDecimal.ZERO;
    /** ltv7*/
    @Excel(name = "ltv7", width = 15)
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv7 = BigDecimal.ZERO;
    /** ltv30*/
    @Excel(name = "ltv30", width = 15)
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv30 = BigDecimal.ZERO;
    /** ltv60*/
    @Excel(name = "ltv60", width = 15)
    @ApiModelProperty(value = "ltv60")
    private BigDecimal ltv60 = BigDecimal.ZERO;
    /** ltv90*/
    @Excel(name = "ltv90", width = 15)
    @ApiModelProperty(value = "ltv90")
    private BigDecimal ltv90 = BigDecimal.ZERO;
}
