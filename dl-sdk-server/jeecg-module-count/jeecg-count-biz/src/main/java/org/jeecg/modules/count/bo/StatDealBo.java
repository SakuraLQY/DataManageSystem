package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @description: TODO
 * @author: chenglin
 * @date: 2023年05月30日 11:01
 */
@Data
public class StatDealBo implements Serializable {
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
    /**注册数*/
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
    /**注册数-按设备数*/
    @ApiModelProperty(value = "有效注册数")
    private Integer validReg;
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
    /** 付费总额 */
    @ApiModelProperty(value = "付费总额")
    private BigDecimal totalMoney ;
}
