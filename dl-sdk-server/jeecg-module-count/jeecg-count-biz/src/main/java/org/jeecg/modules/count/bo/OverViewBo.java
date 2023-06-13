package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="查询ctDaily表中的数据", description="查询ctDaily表中的数据")
public class OverViewBo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**日期*/
    @ApiModelProperty(value = "日期")
    private String timeDaily;
    /**新增-注册数*/
    @ApiModelProperty(value = "新增-注册数")
    private Integer countUser=0;
    /**首日-付费人数*/
    @ApiModelProperty(value = "首日-付费人数")
    private Integer firstPayuser=0;
    /**新增-活跃用户*/
    @ApiModelProperty(value = "新增-活跃用户")
    private Integer countDau=0;
    /**活跃-付费人数*/
    @ApiModelProperty(value = "活跃-付费人数")
    private Integer alivePayuser=0;
    /**首日-付费金额*/
    @ApiModelProperty(value = "首日-付费金额")
    private BigDecimal firstMoney=BigDecimal.ZERO;
    /**活跃-付费金额*/
    @ApiModelProperty(value = "活跃-付费金额")
    private BigDecimal aliveMoney=BigDecimal.ZERO;
    /**活跃-付费金额-ios*/
    @ApiModelProperty(value = "活跃-付费金额-ios")
    private BigDecimal aliveMoneyIos=BigDecimal.ZERO;
    /**累积-付费人数*/
    @ApiModelProperty(value = "累积-付费人数")
    private Integer totalPayuser=0;
    /**累积-付费金额*/
    @ApiModelProperty(value = "累积-付费金额")
    private BigDecimal totalMoney=BigDecimal.ZERO;
    /**累计付费ios*/
    @ApiModelProperty(value = "累计付费ios")
    private BigDecimal totalMoneyIos=BigDecimal.ZERO;
    /**激活数*/
    @ApiModelProperty(value = "激活数")
    private Integer countActive=0;

}
