package org.jeecg.modules.count.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 * @Description: SummaryDto
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给后端对象", description="传给后端对象")
public class SummaryDealInfoDataDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**注册开始日期*/
    @ApiModelProperty(value = "注册开始日期")
    private String regStartTime;
    /**注册结束日期*/
    @ApiModelProperty(value = "注册结束日期")
    private String regEndTime;
    /**广告ID*/
    @Min(value = 1, message = "广告id需要大于0")
    @ApiModelProperty(value = "广告ID")
    private Integer dealId;

}
