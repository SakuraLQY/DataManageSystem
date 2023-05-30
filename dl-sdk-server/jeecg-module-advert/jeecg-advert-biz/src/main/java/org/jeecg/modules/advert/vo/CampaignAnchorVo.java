package org.jeecg.modules.advert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 创建广告主播信息
 * @date: 2023/3/8 12:00
 **/
@Data
@ApiModel(value = "星图广告主播信息", description = "星图广告主播信息")
public class CampaignAnchorVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主播ID")
    private Integer anchorId;

    @ApiModelProperty(value = "主播名称")
    private String anchorName;

    @ApiModelProperty(value = "广告ID")
    private Integer dealId;

    @ApiModelProperty(value = "监测链接")
    private String trackUrl;

    @ApiModelProperty(value = "下载链接")
    private String downloadUrl;
}
