package org.jeecg.modules.advert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description: 创建并发布头条站点返回对象
 * @date: 2023/2/20 18:58
 **/
@Data
@AllArgsConstructor
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class JrttSiteVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
