package org.jeecg.modules.advert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xmh
 * @version V1.0
 * @description:
 * @date: 2023/3/7 14:51
 **/
@Data
@AllArgsConstructor
@ApiModel(value = "通用message返回信息", description = "通用message返回信息")
public class MessageVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
