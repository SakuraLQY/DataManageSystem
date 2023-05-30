package org.jeecg.modules.count.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 封装一个实体类给前端
 * @author: chenglin
 * @date: 2023年05月09日 16:45
 */
@Data
@ApiModel(value = "返回给前端的对象", description = "列表展示对象")
public class GameChargeDto implements Serializable {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "游戏")
    private String gameName;
    @ApiModelProperty(value = "子游戏")
    private String subGameName;
    @ApiModelProperty(value = "注册用户数")
    private Integer registryNumber;
    @ApiModelProperty(value = "充值总额")
    private Integer totalMoney;
    @ApiModelProperty(value = "arpu数")
    private BigDecimal arpu;
}
