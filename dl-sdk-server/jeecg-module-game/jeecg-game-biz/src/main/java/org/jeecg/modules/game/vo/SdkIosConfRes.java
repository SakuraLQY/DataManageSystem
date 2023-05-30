package org.jeecg.modules.game.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeecg.modules.game.bo.OpCommodityBo;

@Data
@Accessors(chain = true)
@ApiModel(value="SDK初始化", description="SDK-ios初始化返回值")
public class SdkIosConfRes {

    @ApiModelProperty(value = "广告id")
    private Integer dealId;

    @ApiModelProperty(value = "苹果商品列表")
    private List<OpCommodityBo> iosProductList;

    @ApiModelProperty(value = "首次激活 1为首次激活 0为2二次激活")
    private Integer firstActive;

    @ApiModelProperty(value = "游戏版本")
    private Integer gameVer = 0;

    @ApiModelProperty(value = "活动状态")
    private Integer activityState = 0;

}
