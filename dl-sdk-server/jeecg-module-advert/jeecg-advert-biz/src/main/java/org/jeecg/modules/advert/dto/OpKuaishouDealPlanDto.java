package org.jeecg.modules.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/3/9 18:59
 **/
@Data
@ApiModel(value = "星图广告基本信息", description = "星图广告基本信息")
public class OpKuaishouDealPlanDto extends OpDealDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告计划ID")
    private String campaignId;

    @ApiModelProperty(value = "计划类型")
    private Integer type;

    @ApiModelProperty(value = "预算类型")
    private Integer budgetMode;

    @ApiModelProperty(value = "出价类型")
    private Integer bidType;

    @ApiModelProperty(value = "预算")
    private Long budget;

    @ApiModelProperty(value = "分日预算")
    private String budgetWeek;
}
