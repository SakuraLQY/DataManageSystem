package org.jeecg.modules.count.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @description: 接受数据报表前端的参数
 * @author: chenglin
 * @date: 2023年05月22日 16:10
 */
@Data
@ApiModel(value="传给后端对象", description="传给后端对象")
public class ReportAccountDto implements Serializable {
    private static final long serialVersionUID = 1L;
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
    /**渠道ID*/
    @ApiModelProperty(value = "渠道ID")
    @Dict(dictTable = "open_gateway.op_channel", dicText = "channel_name", dicCode = "id")
    private List<Integer> channelId;
    /**用户ID*/
    @ApiModelProperty(value = "用户ID")
    private String createUser;
    /**渠道类型id*/
    @ApiModelProperty(value = "渠道类型id")
    @Dict(dictTable = "open_gateway.op_channel_type", dicText = "type_name", dicCode = "id")
    private List<Integer> channelTypeId;
    /**渠道子账号id*/
    @ApiModelProperty(value = "渠道子账号id")
    @Dict(dictTable = "open_gateway.op_channel_sub_account", dicText = "sub_account_name", dicCode = "id")
    private List<Integer> channelSubAccountId;
    /**投放账号id**/
    /**投放账号*/
    @Dict(dictTable = "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2'", dicText = "nick_name", dicCode = "id")
    @ApiModelProperty(value = "投放账号")
    private List<Integer> accountId;
}
