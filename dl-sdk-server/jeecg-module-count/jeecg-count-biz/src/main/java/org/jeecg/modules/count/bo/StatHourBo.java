package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: 接受数据库的对象
 * @author: chenglin
 * @date: 2023年05月29日 14:08
 */
@Data
public class StatHourBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**日期*/
    @ApiModelProperty(value = "日期")
    private Date dateTime;
    /**日期*/
    @ApiModelProperty(value = "广告名称")
    private Integer dealId;
    /**游戏名称*/
    @ApiModelProperty(value = "游戏名称")
    private Integer gameId;
    /**激活数*/
    @ApiModelProperty(value = "激活数")
    private Integer countActive ;
    /**全部激活数*/
    @ApiModelProperty(value = "全部激活数")
    private Integer countAllActive;
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
    /**注册数-按设备数*/
    @ApiModelProperty(value = "注册数")
    private Integer countUserDev;
    /**有效注册数 注册数/激活数 */
    @ApiModelProperty(value = "有效注册数")
    private Integer validReg ;
    /**全部有效注册数 */
    @ApiModelProperty(value = "全部有效注册数")
    private Integer AllValidReg;
    /**新增付费人数 */
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser;
    /**新增付费金额*/
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstPayMoney ;

    /** 活跃人数*/
    @ApiModelProperty(value = "活跃人数")
    private Integer countDau ;
    /** 活跃付费人数 */
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayUser ;
    /**全部活跃付费人数 */
    @ApiModelProperty(value = "全部活跃付费人数")
    private Integer AllAlivePayUser ;
    /** 付费总额 */
    @ApiModelProperty(value = "付费总额")
    private BigDecimal totalMoney ;

    /** 次日留存*/
    @ApiModelProperty(value = "3日留存")
    private BigDecimal loyal2 ;
    /** 3日留存*/
    @ApiModelProperty(value = "3日留存")
    private BigDecimal loyal3 ;
    /** 7日留存*/
    @ApiModelProperty(value = "4日留存")
    private BigDecimal loyal7;
    /** 15日留存*/
    @ApiModelProperty(value = "5日留存")
    private BigDecimal loyal15 ;
    /** 30日留存*/
    @ApiModelProperty(value = "6日留存")
    private BigDecimal loyal30 ;

    /** ltv1*/
    @ApiModelProperty(value = "ltv1")
    private BigDecimal ltv1 ;
    /** ltv2*/
    @ApiModelProperty(value = "ltv2")
    private BigDecimal ltv2 ;
    /** ltv3*/
    @ApiModelProperty(value = "ltv4")
    private BigDecimal ltv3 ;
    /** ltv3*/
    @ApiModelProperty(value = "ltv5")
    private BigDecimal ltv4 ;
    /** ltv3*/
    @ApiModelProperty(value = "ltv6")
    private BigDecimal ltv5 ;
    /** ltv3*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv6 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv7 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv8 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv9 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv10 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv11 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv12 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv13 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv14 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv15 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv16;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv17;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv18 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv19 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv20;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv21;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv22;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv23;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv24;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv25 ;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv26;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv27;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv28;
    /** ltv7*/
    @ApiModelProperty(value = "ltv7")
    private BigDecimal ltv29;
    /** ltv30*/
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv30 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv31 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv32 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv33 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv34 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv35 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv36 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv37 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv38 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv39 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv40 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv41 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv42 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv43 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv44 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv45 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv46 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv47 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv48 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv49 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv50 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv51 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv52 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv53 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv54 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv55 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv56 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv57 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv58 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv59 ;
    /** ltv60*/
    @ApiModelProperty(value = "ltv60")
    private BigDecimal ltv60 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv61 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv62 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv63 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv64 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv65 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv66 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv67 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv68 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv69 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv70 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv71 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv72 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv73 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv74 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv75 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv76 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv77 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv78 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv79 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv80 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv81 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv82 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv83 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv84 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv85 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv86 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv87 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv88 ;
    @ApiModelProperty(value = "ltv30")
    private BigDecimal ltv89 ;
    /** ltv90*/
    @ApiModelProperty(value = "ltv90")
    private BigDecimal ltv90 ;
}
