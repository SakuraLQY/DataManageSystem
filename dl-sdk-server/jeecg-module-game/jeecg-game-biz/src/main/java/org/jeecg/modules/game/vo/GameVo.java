package org.jeecg.modules.game.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class GameVo {

    @ApiModelProperty(value = "游戏ID")
    private Integer id;

    @ApiModelProperty(value = "游戏名")
    private String gameName;

    @ApiModelProperty(value = "子游戏集合")
    private Map<Integer, GameObjVo> map;

}
