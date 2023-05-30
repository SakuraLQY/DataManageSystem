package org.jeecg.modules.count.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @Description: SummaryAdvertDto
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给后端对象", description="传给后端对象")
public class SummaryAdvertDto implements Serializable {
    private static final long serialVersionUID = 1L;

	/**游戏*/
    @ApiModelProperty(value = "游戏")
    private List<Integer> gameId;
	/**子游戏*/
    @ApiModelProperty(value = "子游戏")
    private List<Integer> subGameId;
	/**渠道游戏包*/
    @ApiModelProperty(value = "渠道游戏包")
    private List<Integer> pkgId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    private List<Integer> channelTypeId;
    /**渠道id*/
    @ApiModelProperty(value = "渠道id")
    private List<Integer> channelId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    private List<Integer> channelSubAccountId;
	/**广告ID*/
    @ApiModelProperty(value = "广告ID")
    private List<Integer> dealId;
    /**注册开始日期*/
    @ApiModelProperty(value = "开始日期")
    private String regStartTime;
    /**注册结束日期*/
    @ApiModelProperty(value = "结束日期")
    private String regEndTime;
    /**归类方式*/
    @ApiModelProperty(value = "展示方式")
    private Integer groupType;
}
