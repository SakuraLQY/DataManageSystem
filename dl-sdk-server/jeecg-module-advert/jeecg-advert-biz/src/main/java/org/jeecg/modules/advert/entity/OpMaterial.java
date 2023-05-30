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
 * @Description: op_material
 * @Author: jeecg-boot
 * @Date:   2023-01-12
 * @Version: V1.0
 */
@Data
@TableName("op_material")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_material对象", description="op_material")
public class OpMaterial implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**游戏ID*/
	@Excel(name = "游戏ID", width = 15)
	@ApiModelProperty(value = "游戏ID")
	private Integer gameId;
	/**子游戏ID*/
	@Excel(name = "子游戏ID", width = 15)
    @ApiModelProperty(value = "子游戏ID")
    private java.lang.Integer subGameId;
	/**素材名*/
	@Excel(name = "素材名", width = 15)
    @ApiModelProperty(value = "素材名")
    private java.lang.String materialName;
	/**素材类型*/
	@Excel(name = "素材类型", width = 15, dicCode = "material_manager_type")
	@Dict(dicCode = "material_manager_type")
    @ApiModelProperty(value = "素材类型")
    private java.lang.Integer type1;
	/**素材归类*/
	@Excel(name = "素材归类", width = 15, dicCode = "material_img_type")
	@Dict(dicCode = "material_img_type")
    @ApiModelProperty(value = "素材归类")
    private java.lang.Integer type2;
	/**素材格式*/
	@Excel(name = "素材格式", width = 15)
    @ApiModelProperty(value = "素材格式")
    private java.lang.String format;
//	/**素材大小*/
//	@Excel(name = "素材大小", width = 15)
//    @ApiModelProperty(value = "素材大小")
//    private java.lang.String size;
	/**规格*/
	@Excel(name = "规格", width = 15)
    @ApiModelProperty(value = "规格")
    private java.lang.String specs;
	/**素材链接*/
	@Excel(name = "素材链接", width = 15)
    @ApiModelProperty(value = "素材链接")
    private java.lang.String showUrl;
	/**头条素材账号*/
	@Excel(name = "头条素材账号", width = 15, dicCode = "select_upload")
	@Dict(dicCode = "select_upload")
    @ApiModelProperty(value = "头条素材账号")
    private java.lang.Integer jrttCreateAccountId;
	/**头条素材ID*/
	@Excel(name = "头条素材ID", width = 15)
    @ApiModelProperty(value = "头条素材ID")
    private java.lang.String jrttMaterialId;
	/**头条文件ID*/
	@Excel(name = "头条文件ID", width = 15)
    @ApiModelProperty(value = "头条文件ID")
    private java.lang.String jrttFileId;
	/**今日头条素材信息*/
	@Excel(name = "今日头条素材信息", width = 15)
    @ApiModelProperty(value = "今日头条素材信息")
    private java.lang.String jrttMaterialInfo;
	/**今日头条视频封面*/
	@Excel(name = "今日头条视频封面", width = 15)
    @ApiModelProperty(value = "今日头条视频封面")
    private java.lang.String jrttVideoCover;
	/**广点通素材账号*/
	@Excel(name = "广点通素材账号", width = 15, dicCode = "select_upload")
	@Dict(dicCode = "select_upload")
    @ApiModelProperty(value = "广点通素材账号")
    private java.lang.Integer gdtCreateAccountId;
	/**广点通素材ID*/
	@Excel(name = "广点通素材ID", width = 15)
    @ApiModelProperty(value = "广点通素材ID")
    private java.lang.String gdtMaterialId;
	/**广点通文件ID*/
	@Excel(name = "广点通文件ID", width = 15)
    @ApiModelProperty(value = "广点通文件ID")
    private java.lang.String gdtFileId;
	/**广点通素材信息*/
	@Excel(name = "广点通素材信息", width = 15)
    @ApiModelProperty(value = "广点通素材信息")
    private java.lang.String gdtMaterialInfo;
	/**广点通视频封面*/
	@Excel(name = "广点通视频封面", width = 15)
    @ApiModelProperty(value = "广点通视频封面")
    private java.lang.String gdtVideoCover;
	/**快手素材账号*/
	@Excel(name = "快手素材账号", width = 15, dicCode = "select_upload")
	@Dict(dicCode = "select_upload")
    @ApiModelProperty(value = "快手素材账号")
    private java.lang.Integer kuaishouCreateAccountId;
	/**快手素材ID*/
	@Excel(name = "快手素材ID", width = 15)
    @ApiModelProperty(value = "快手素材ID")
    private java.lang.String kuaishouMaterialId;
	/**快手文件ID*/
	@Excel(name = "快手文件ID", width = 15)
    @ApiModelProperty(value = "快手文件ID")
    private java.lang.String kuaishouFileId;
	/**快手素材信息*/
	@Excel(name = "快手素材信息", width = 15)
    @ApiModelProperty(value = "快手素材信息")
    private java.lang.String kuaishouMaterialInfo;
	/**快手视频封面*/
	@Excel(name = "快手视频封面", width = 15)
    @ApiModelProperty(value = "快手视频封面")
    private java.lang.String kuaishouVideoCover;
	/**素材备注*/
	@Excel(name = "素材备注", width = 15)
    @ApiModelProperty(value = "素材备注")
    private java.lang.String materialDesc;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
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
