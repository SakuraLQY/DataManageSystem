package org.jeecg.modules.advert.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_jrtt_assets
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="op_jrtt_assets对象", description="op_jrtt_assets")
public class OpJrttAssetsVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
    @ApiModelProperty(value = "id")
    private Integer id;
	/**头条资产ID*/
    @ApiModelProperty(value = "头条资产ID")
    private Long assetId;
	/**资产名*/
    @ApiModelProperty(value = "资产名")
    private String assetName;
	/**游戏*/
    @ApiModelProperty(value = "游戏")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private Integer gameId;
	/**子游戏*/
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    @ApiModelProperty(value = "子游戏")
    private Integer subGameId;
	/**渠道游戏包*/
    @Dict(dictTable = "open_gateway.op_pkg", dicText = "pkg_name", dicCode = "id")
    @ApiModelProperty(value = "渠道游戏包")
    private Integer pkgId;
	/**投放账号*/
	@Dict(dictTable = "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2' ", dicText = "nick_name", dicCode = "id")
    @ApiModelProperty(value = "投放账号")
    private Integer accountId;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    @Dict(dictTable = "sys_user", dicText = "username", dicCode = "username")
    private String createBy;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**渠道ID （4/5  头条/星图）*/
    @ApiModelProperty(value = "渠道ID （4/5  头条/星图）")
    private Integer channelId;
    /**游戏类型*/
    @ApiModelProperty(value = "游戏类型 （ 0/1 安卓/IOS）")
    private Integer gameType;
}
