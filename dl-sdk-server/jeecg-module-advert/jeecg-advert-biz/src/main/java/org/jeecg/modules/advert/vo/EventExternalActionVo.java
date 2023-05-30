package org.jeecg.modules.advert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @Author lili
 * @Description
 * @Date 2023-1-18
 **/
@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class EventExternalActionVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事件管理资产id")
    private List<Long> assetIds;
    @ApiModelProperty(value = "优化目标名")
    private String label;
    @ApiModelProperty(value = "优化目标值")
    private String value;


}
