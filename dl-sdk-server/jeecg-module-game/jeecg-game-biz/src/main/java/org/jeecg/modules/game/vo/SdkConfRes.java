package org.jeecg.modules.game.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@ApiModel(value="SDK初始化返回对象", description="SDK初始化响应对象")
@Data
public class SdkConfRes {

    @ApiModelProperty(value = "用户id")
    private Integer idAuth;


    @ApiModelProperty(value = "用户id")
    private Integer toutiaoOpen;

    @ApiModelProperty(value = "用户id")
    private String toutiaoChannel;

    @ApiModelProperty(value = "用户id")
    private String toutiaoAid;

}
