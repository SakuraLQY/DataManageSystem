package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: SummaryVo
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class DetailVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**日期*/
    @ApiModelProperty(value = "日期")
    private String day;
    /**游戏名*/
    @ApiModelProperty(value = "游戏名")
    private String gameName;
    /**渠道名*/
    @ApiModelProperty(value = "渠道名")
    private String channelName;
    /**广告名*/
    @ApiModelProperty(value = "广告名")
    private String dealName;
    /**激活数*/
    @ApiModelProperty(value = "激活数")
    private Integer countActive = 0;
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer countUser = 0;
    /**有效注册数*/
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser = 0;
    /**新增付费人数 */
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayuser = 0;
    /**新增付费金额*/
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstMoney = BigDecimal.ZERO;
    /**新增付费率 新增付费人数/*/
    @ApiModelProperty(value = "新增付费率")
    private BigDecimal firstMoneyRate = BigDecimal.ZERO;
    /**arpu 首日付费额/注册数 firstMoney/countUser*/
    @ApiModelProperty(value = "新增ARPU")
    private BigDecimal firstArpu = BigDecimal.ZERO;
    /**arpu 首日付费额/注册数 firstMoney/countUser*/
    @ApiModelProperty(value = "新增ARPPU")
    private BigDecimal firstArppu = BigDecimal.ZERO;
    /** 老用户付费率 当日老用户付费人数 / 当日老用户总数 (alivePayUser-firstPayUser)/(countDau-countUser)*100*/
    @ApiModelProperty(value = "老用户付费率")
    private BigDecimal oldUserPayRate = BigDecimal.ZERO;
    /** 老用户arpu (aliveMoney-firstMoney)/(countDau-countUser) */
    @ApiModelProperty(value = "老用户ARPU")
    private BigDecimal oldUserArpu = BigDecimal.ZERO;
    /** 老用户arppu (aliveMoney-firstMoney)/(alivePayuser-firstPayuser) */
    @ApiModelProperty(value = "老用户ARPPU")
    private BigDecimal oldUserArppu = BigDecimal.ZERO;
    /** 累计付费金额*/
    @ApiModelProperty(value = "累计付费金额")
    private Integer totalMoney = 0;
    /** DAU 日活*/
    @ApiModelProperty(value = "DAU")
    private Integer countDau = 0;
    /** 活跃付费人数*/
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayuser = 0;
    /** 活跃付费金额*/
    @ApiModelProperty(value = "活跃付费金额")
    private BigDecimal aliveMoney = BigDecimal.ZERO;
    /** 活跃付费率=活跃付费人数/dau alivePayuser/countDau */
    @ApiModelProperty(value = "活跃付费率")
    private BigDecimal aliveMoneyRate = BigDecimal.ZERO;
    /** ARPU=活跃付费金额/dau aliveMoney/countDau */
    @ApiModelProperty(value = "ARPU")
    private BigDecimal arpu = BigDecimal.ZERO;
    /** ARPPU= 活跃付费金额/活跃付费人数 aliveMoney/alivePayUser */
    @ApiModelProperty(value = "ARPPU")
    private BigDecimal arppu = BigDecimal.ZERO;
    /** 推广费用 */
    @ApiModelProperty(value = "推广费用")
    private BigDecimal costMoney = BigDecimal.ZERO;
    /**sdk分成所得*/
    @Excel(name = "sdk分成所得", width = 15)
    @ApiModelProperty(value = "sdk分成所得")
    private BigDecimal sdkShare;
}
