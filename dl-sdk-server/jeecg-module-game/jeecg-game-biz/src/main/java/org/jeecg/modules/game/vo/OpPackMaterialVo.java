package org.jeecg.modules.game.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: op_pack_material
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class OpPackMaterialVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "游戏ID")
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    private Integer gameId;
    @ApiModelProperty(value = "子游戏ID")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private Integer subGameId;
    @ApiModelProperty(value = "素材名")
    private String materialName;
    @ApiModelProperty(value = "素材类型")
    @Dict(dicCode = "material_type")
    private Integer type;
    @ApiModelProperty(value = "素材格式")
    private java.lang.String format;
    @ApiModelProperty(value = "规格")
    private java.lang.String specs;
    @ApiModelProperty(value = "素材链接")
    private String showUrl;
    @ApiModelProperty(value = "素材备注")
    private String materialDesc;
    @ApiModelProperty(value = "创建用户")
    @Dict(dictTable = "open_gateway.op_pack_material", dicText = "create_by", dicCode = "create_by")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
