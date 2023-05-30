package org.jeecg.common.advert.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * @author chenyw
 * @version V1.0
 * @description:获取成本Dto
 * @date: 2023/2/14 11:22
 **/
@Data
@ApiModel(value = "获取成本dto", description = "获取成本dto")
public class OpCostDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 游戏
     */
    @ApiModelProperty(value = "游戏")
    private List<Integer> gameId;
    /**
     * 子游戏
     */
    @ApiModelProperty(value = "子游戏")
    private List<Integer> subGameId;
    /**
     * 渠道游戏包
     */
    @ApiModelProperty(value = "渠道游戏包")
    private List<Integer> pkgId;
    /**
     * 渠道类型id
     */
    @ApiModelProperty(value = "渠道类型id")
    private List<Integer> channelTypeId;
    /**
     * 渠道id
     */
    @ApiModelProperty(value = "渠道id")
    private List<Integer> channelId;
    /**
     * 渠道子账号id
     */
    @ApiModelProperty(value = "渠道子账号id")
    private List<Integer> channelSubAccountId;
    /**
     * 广告ID
     */
    @ApiModelProperty(value = "广告ID")
    private List<Integer> dealId;
    /**
     * 投放账号ID
     */
    @ApiModelProperty(value = "投放账号id")
    private List<Integer> accountId;
    /**
     * 开始日期
     */
    @ApiModelProperty(value = "开始日期")
    private String regStartTime;
    /**
     * 结束日期
     */
    @ApiModelProperty(value = "结束日期")
    private String regEndTime;

    /**
     * 成本平台
     */
    @ApiModelProperty(value = "成本平台")
    private String costPlatform;

    /**
     * 账号人员
     */
    @ApiModelProperty(value = "账号人员")
    private String createUser;

    /**
     * 归类方式
     */
    @ApiModelProperty(value = "归类方式")
    private String groupType;
}
