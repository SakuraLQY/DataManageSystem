package org.jeecg.modules.advert.modal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class ChannelObjModal {

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "list")
    private List<ChannelObjModal> list;

}
