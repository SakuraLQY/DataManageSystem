package org.jeecg.modules.game.entity;

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
 * @Description: op_pack_material
 * @Author: jeecg-boot
 * @Date:   2023-01-09
 * @Version: V1.0
 */
@Data
@TableName("op_pack_material")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_pack_material对象", description="op_pack_material")
public class OpPackMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**游戏ID*/
    @Excel(name = "游戏ID", width = 15)
    @ApiModelProperty(value = "游戏ID")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private java.lang.Integer gameId;
	/**子游戏ID*/
	@Excel(name = "子游戏ID", width = 15)
    @ApiModelProperty(value = "子游戏ID")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private java.lang.Integer subGameId;
	/**素材名*/
	@Excel(name = "素材名", width = 15)
    @ApiModelProperty(value = "素材名")
    private java.lang.String materialName;
	/**素材类型*/
	@Excel(name = "素材类型", width = 15, dicCode = "material_type")
	@Dict(dicCode = "material_type")
    @ApiModelProperty(value = "素材类型")
    private java.lang.Integer type;
	/**素材格式*/
	@Excel(name = "素材格式", width = 15)
    @ApiModelProperty(value = "素材格式")
    private java.lang.String format;
	@Excel(name = "规格", width = 15)
    @ApiModelProperty(value = "规格")
    private java.lang.String specs;
	/**素材链接*/
	@Excel(name = "素材链接", width = 15)
    @ApiModelProperty(value = "素材链接")
    private java.lang.String showUrl;
	/**素材备注*/
	@Excel(name = "素材备注", width = 15)
    @ApiModelProperty(value = "素材备注")
    private java.lang.String materialDesc;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    @Dict(dictTable = "open_gateway.op_pack_material", dicText = "create_by", dicCode = "create_by")
    private java.lang.String createBy;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
}
