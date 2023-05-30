package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="所有符合要求的成本", description="所有符合要求的成本")
public class RoiCostVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**成本日期*/
    @ApiModelProperty(value = "costDay")
    private String costDay;
	/**成本金额*/
    @ApiModelProperty(value = "costMoney")
    private BigDecimal costMoney;
    /**游戏*/
    @ApiModelProperty(value = "游戏")
    private Integer gameId;
    /**子游戏*/
    @ApiModelProperty(value = "子游戏")
    private Integer subGameId;
    /**渠道游戏包*/
    @ApiModelProperty(value = "渠道游戏包")
    private Integer pkgId;
    /**渠道ID*/
    @ApiModelProperty(value = "渠道ID")
    private Integer channelId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private Integer channelTypeId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private Integer channelSubAccountId;

}
