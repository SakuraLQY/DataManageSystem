package org.jeecg.modules.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/3/2 18:25
 **/
@Data
@ApiModel(value = "星图广告基本信息", description = "星图广告基本信息")
public class OpXingtuDealDto extends OpDealDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主播")
    private Integer anchorPlanId;

    @ApiModelProperty(value = "安卓下载链接")
    private String androidUrl;

    @ApiModelProperty(value = "IOS下载链接")
    private String iosUrl;
}
