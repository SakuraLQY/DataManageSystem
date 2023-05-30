package org.jeecg.modules.game.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "参数详情", description = "参数详情")
public class GameConfModel {

    @ApiModelProperty(value = "游戏key")
    private String gameKey;

    @ApiModelProperty(value = "充值私钥")
    private String payPrikey;

    @ApiModelProperty(value = "充值公钥")
    private String payPubkey;

}
