package org.jeecg.modules.game.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class GameObjVo {

    @ApiModelProperty(value = "游戏ID")
    private Integer id;

    @ApiModelProperty(value = "游戏名")
    private String gameName;

    @ApiModelProperty(value = "子游戏列表")
    private List<GameObjVo> list;

}
