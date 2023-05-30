package org.jeecg.modules.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 创建头条站点dto
 * @date: 2023/2/20 15:50
 **/
@Data
@ApiModel(value = "广告基本信息", description = "广告基本信息")
public class PostJrttSiteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告ID")
    private Integer dealId;

    @ApiModelProperty(value = "头条站点ID")
    private Integer siteId;
}
