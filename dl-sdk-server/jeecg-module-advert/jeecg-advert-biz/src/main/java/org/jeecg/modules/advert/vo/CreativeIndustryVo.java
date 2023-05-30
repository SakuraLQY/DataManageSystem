package org.jeecg.modules.advert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class CreativeIndustryVo {

    @ApiModelProperty(value = "名称")
    private String label;

    @ApiModelProperty(value = "游戏id")
    private Integer value;

    @ApiModelProperty(value = "孩子")
    private List<CreativeIndustryVo> children;

}
