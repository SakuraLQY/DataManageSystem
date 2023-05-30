package org.jeecg.modules.advert.modal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import lombok.Data;

@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class ChannelModal {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "子游戏集合")
    private Map<Integer, ChannelObjModal> map;

}
