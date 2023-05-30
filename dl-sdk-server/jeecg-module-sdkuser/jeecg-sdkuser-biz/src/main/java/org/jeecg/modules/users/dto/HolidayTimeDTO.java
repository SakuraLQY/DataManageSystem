package org.jeecg.modules.users.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "前端返回对象", description = "前端返回对象")
public class HolidayTimeDTO {

    @ApiModelProperty(value = "开始时间")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
