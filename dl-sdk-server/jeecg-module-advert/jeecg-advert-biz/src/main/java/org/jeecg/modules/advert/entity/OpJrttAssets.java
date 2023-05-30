package org.jeecg.modules.advert.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: op_jrtt_assets
 * @Author: jeecg-boot
 * @Date:   2023-02-13
 * @Version: V1.0
 */
@Data
@TableName("op_jrtt_assets")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_jrtt_assets对象", description="op_jrtt_assets")
public class OpJrttAssets implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**头条资产ID*/
	@Excel(name = "头条资产ID", width = 15)
    @ApiModelProperty(value = "头条资产ID")
    private java.lang.Long assetId;
	/**资产名*/
	@Excel(name = "资产名", width = 15)
    @Dict(dictTable = "open_gateway.op_jrtt_assets", dicText = "asset_name", dicCode = "id")
    @ApiModelProperty(value = "资产名")
    private java.lang.String assetName;
	/**游戏*/
	@Excel(name = "游戏", width = 15)
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    @ApiModelProperty(value = "游戏")
    private java.lang.Integer gameId;
	/**子游戏*/
	@Excel(name = "子游戏", width = 15)
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    @ApiModelProperty(value = "子游戏")
    private java.lang.Integer subGameId;
	/**渠道游戏包*/
	@Excel(name = "渠道游戏包", width = 15)
    @Dict(dictTable = "open_gateway.op_pkg", dicText = "pkg_name", dicCode = "id")
    @ApiModelProperty(value = "渠道游戏包")
    private java.lang.Integer pkgId;
	/**投放账号*/
	@Excel(name = "投放账号", width = 15, dictTable = "op_put_account where channel_Id = 4 and state = 1 and level_id = 2 ", dicText = "nick_name", dicCode = "id")
	@Dict(dictTable = "open_gateway.op_put_account where channel_Id = '4' and state = '1' and level_id = '2' ", dicText = "nick_name", dicCode = "id")
    @ApiModelProperty(value = "投放账号")
    private java.lang.Integer accountId;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    @Dict(dictTable = "sys_user", dicText = "username", dicCode = "username")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**渠道ID （4/5  头条/星图）*/
	@Excel(name = "渠道ID （4/5  头条/星图）", width = 15)
    @ApiModelProperty(value = "渠道ID （4/5  头条/星图）")
    private java.lang.Integer channelId;
}
