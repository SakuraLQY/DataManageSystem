package org.jeecg.modules.count.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 接受前段传来的对象
 * @author: chenglin
 * @date: 2023年05月15日 16:58
 */
@Data
@ApiModel(value="传给后端对象", description="传给后端对象")
public class LtvDto implements Serializable {
    /**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
    /**游戏*/
    @ApiModelProperty(value = "游戏")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private List<Integer> gameId;
    /**子游戏*/
    @ApiModelProperty(value = "子游戏")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private List<Integer> subGameId;
    /**渠道游戏包*/
    @ApiModelProperty(value = "渠道游戏包")
    @Dict(dictTable = "open_gateway.op_pkg", dicText = "pkg_name", dicCode = "id")
    private List<Integer> pkgId;
    @ApiModelProperty(value = "渠道游戏包")
    private List<String> pkgIdList;
    /**广告ID*/
    @ApiModelProperty(value = "广告ID")
    @Dict(dictTable = "open_gateway.op_deal", dicText = "deal_name", dicCode = "id")
    private List<Integer> dealId;
    /**广告计划ID*/
    @ApiModelProperty(value = "广告计划ID")
    private List<Integer> planId;
    /**用户ID*/
    @ApiModelProperty(value = "用户ID")
    private String createUser;
    /**渠道ID*/
    @ApiModelProperty(value = "渠道ID")
    @Dict(dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    private List<Integer> channelId;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    @Dict(dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
    private List<Integer> channelTypeId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    @Dict(dictTable = "open_gateway.op_channel_sub_account", dicText = "sub_account_name", dicCode = "id")
    private List<Integer> channelSubAccountId;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
