package org.jeecg.modules.game.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class SubAndOpPackGameVo {

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "名称")
    private String label;

    @ApiModelProperty(value = "游戏id")
    private String value;

    @ApiModelProperty(value = "游戏id")
    private Boolean disabled;

    @ApiModelProperty(value = "孩子")
    private List<SubAndOpPackGameVo> children;

}
