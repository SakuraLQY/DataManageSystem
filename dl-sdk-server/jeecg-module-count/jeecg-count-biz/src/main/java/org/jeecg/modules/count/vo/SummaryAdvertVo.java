package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: SummaryVo
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="广告数据明细", description="广告数据明细")
public class SummaryAdvertVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**日期*/
    @ApiModelProperty(value = "日期")
    private String day;
	/**游戏*/
    @ApiModelProperty(value = "广告id")
    private String dealId;
	/**渠道游戏包名*/
    @ApiModelProperty(value = "渠道游戏包名")
    private String pkgName;
    /**广告名*/
    @ApiModelProperty(value = "广告名")
    private String dealName;
    /**渠道名*/
    @ApiModelProperty(value = "渠道名")
    private String channelName;
    /**激活数*/
    @ApiModelProperty(value = "激活数")
    private Integer countActive = 0;
    /**激活设备数*/
    @ApiModelProperty(value = "激活设备数")
    private Integer countActiveDev = 0;
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer countUser = 0;
    /**注册设备数*/
    @ApiModelProperty(value = "注册设备数")
    private Integer countUserDev = 0;
    /**激活注册率 注册数/激活数 */
    @ApiModelProperty(value = "激活注册率")
    private BigDecimal countActiveUserRate = BigDecimal.ZERO;
    /**有效注册数*/
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser = 0;
    /**有效注册率 有效注册数/注册数 */
    @ApiModelProperty(value = "有效注册率")
    private BigDecimal countValidUserCountUserRate = BigDecimal.ZERO;
    /**新增付费人数 */
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayuser = 0;
    /**新增付费额*/
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstMoney = BigDecimal.ZERO;
    /**新增付费率 新增付费人数/注册数*/
    @ApiModelProperty(value = "新增付费率")
    private BigDecimal firstMoneyFirstUserRate = BigDecimal.ZERO;
    /**arpu 首日付费额/注册数 firstMoney/countUser*/
    @ApiModelProperty(value = "新增ARPU")
    private BigDecimal firstArpu = BigDecimal.ZERO;
    /**arppu 首日付费额/首日回款率 firstMoney/firstPayuser */
    @ApiModelProperty(value = "新增ARRPU")
    private BigDecimal firstArppu = BigDecimal.ZERO;
    /** 老用户付费数*/
    @ApiModelProperty(value = "老用户付费数")
    private Integer oldUserPayuser = 0;
    /** 老用户付费金额*/
    @ApiModelProperty(value = "老用户付费金额")
    private BigDecimal oldUserMoney = BigDecimal.ZERO;
    /** 老用户付费率 当日老用户付费人数 / 当日老用户总数 (alivePayUser-firstPayUser)/(countDau-countUser)*100*/
    @ApiModelProperty(value = "老用户付费率")
    private BigDecimal oldUserPayRate = BigDecimal.ZERO;
    /** 老用户arpu (aliveMoney-firstMoney)/(countDau-countUser) */
    @ApiModelProperty(value = "老用户Arpu")
    private BigDecimal oldUserArpu = BigDecimal.ZERO;
    /** 老用户arppu (aliveMoney-firstMoney)/(alivePayuser-firstPayuser) */
    @ApiModelProperty(value = "老用户Arppu")
    private BigDecimal oldUserArppu = BigDecimal.ZERO;
    /** 累积付费额 */
    @ApiModelProperty(value = "累积付金费额")
    private BigDecimal totalMoney = BigDecimal.ZERO;
    /** DAU 日活*/
    @ApiModelProperty(value = "DAU")
    private Integer countDau = 0;
    /** 活跃付费人数*/
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayuser = 0;
    /** 活跃付费金额*/
    @ApiModelProperty(value = "活跃付费金额")
    private BigDecimal aliveMoney = BigDecimal.ZERO;
    /** ARPU 活跃付费额/日活 aliveMoney/countDau*/
    @ApiModelProperty(value = "ARPU")
    private BigDecimal arpu = BigDecimal.ZERO;
    /** ARPPU 活跃付费额/日活 aliveMoney/alivePayuser*/
    @ApiModelProperty(value = "ARPPU")
    private BigDecimal arppu = BigDecimal.ZERO;
    /** 总付费率 活跃付费人数/DAU*/
    @ApiModelProperty(value = "总付费率")
    private BigDecimal alivePayuserDauRate = BigDecimal.ZERO;
    /** 次日留存*/
    @ApiModelProperty(value = "次留")
    private BigDecimal retention2 = BigDecimal.ZERO;
    /** 3日留存*/
    @ApiModelProperty(value = "3日留存")
    private BigDecimal retention3 = BigDecimal.ZERO;
    /** 4日留存*/
    @ApiModelProperty(value = "4日留存")
    private BigDecimal retention4 = BigDecimal.ZERO;
    /** 5日留存*/
    @ApiModelProperty(value = "5日留存")
    private BigDecimal retention5 = BigDecimal.ZERO;
    /** 6日留存*/
    @ApiModelProperty(value = "6日留存")
    private BigDecimal retention6 = BigDecimal.ZERO;
    /** 7日留存*/
    @ApiModelProperty(value = "7日留存")
    private BigDecimal retention7 = BigDecimal.ZERO;
    /** ltv1*/
    @ApiModelProperty(value = "ltv1")
    private BigDecimal ltv1 = BigDecimal.ZERO;
    /** ltv2*/
    @ApiModelProperty(value = "ltv2")
    private BigDecimal ltv2 = BigDecimal.ZERO;
    /** ltv3*/
    @ApiModelProperty(value = "ltv3")
    private BigDecimal ltv3 = BigDecimal.ZERO;
    /** ltv4*/
    @ApiModelProperty(value = "ltv4")
    private BigDecimal ltv4 = BigDecimal.ZERO;
    /** ltv5*/
    @ApiModelProperty(value = "ltv5")
    private BigDecimal ltv5 = BigDecimal.ZERO;
    /** ltv6*/
    @ApiModelProperty(value = "ltv6")
    private BigDecimal ltv6 = BigDecimal.ZERO;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv7 = BigDecimal.ZERO;
}
