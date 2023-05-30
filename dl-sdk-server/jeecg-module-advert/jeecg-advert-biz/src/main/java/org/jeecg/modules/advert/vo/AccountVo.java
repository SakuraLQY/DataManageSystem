package org.jeecg.modules.advert.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author lili
 * @Description
 * @Date 2023-1-18
 **/
@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class AccountVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "账号")
    private java.lang.String account;
    @ApiModelProperty(value = "账号昵称")
    private java.lang.String nickName;
    @ApiModelProperty(value = "渠道账号id")
    private java.lang.String advertiserId;

}
