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
 * @Description: op_material
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
@Data
@ApiModel(value = "返回前端对象", description = "返回前端对象")
public class OpMaterialVo implements Serializable {

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
    @Dict(dicCode = "material_manager_type")
    @ApiModelProperty(value = "素材类型")
    private Integer type1;
    @Dict(dicCode = "material_img_type")
    @ApiModelProperty(value = "素材归类")
    private Integer type2;
    @ApiModelProperty(value = "素材链接")
    private String showUrl;
    @ApiModelProperty(value = "头条素材账号")
    @Dict(dictTable = "open_gateway.op_put_account", dicText = "nick_name", dicCode = "id")
    private String jrttCreateAccountId;
    @ApiModelProperty(value = "头条素材ID")
    private String jrttMaterialId;
    @ApiModelProperty(value = "头条文件ID")
    private String jrttFileId;
    @ApiModelProperty(value = "广点通素材账号")
    @Dict(dictTable = "open_gateway.op_put_account", dicText = "nick_name", dicCode = "id")
    private String gdtCreateAccountId;
    @ApiModelProperty(value = "广点通素材ID")
    private String gdtMaterialId;
    @ApiModelProperty(value = "广点通文件ID")
    private String gdtFileId;
    @ApiModelProperty(value = "快手素材账号")
    @Dict(dictTable = "open_gateway.op_put_account", dicText = "nick_name", dicCode = "id")
    private String kuaishouCreateAccountId;
    @ApiModelProperty(value = "快手素材ID")
    private String kuaishouMaterialId;
    @ApiModelProperty(value = "快手文件ID")
    private String kuaishouFileId;
    @ApiModelProperty(value = "素材备注")
    private String materialDesc;
    @Dict(dictTable = "open_gateway.op_material", dicText = "create_by", dicCode = "create_by")
    private String createBy;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**头条上传状态*/
    @ApiModelProperty(value = "头条上传状态")
    private java.lang.Integer jrttStatus;
    /**广点通上传状态*/
    @ApiModelProperty(value = "广点通上传状态")
    private java.lang.Integer gdtStatus;
    /**快手上传状态*/
    @ApiModelProperty(value = "上传状态")
    private java.lang.Integer ksStatus;
}
