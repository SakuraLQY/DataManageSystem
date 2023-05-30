package org.jeecg.modules.advert.dto;

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
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_material
 * @Author: jeecg-boot
 * @Date: 2023-01-12
 * @Version: V1.0
 */
@Data
@ApiModel(value = "前端传来对象", description = "前端传来对象")
public class OpMaterialDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;
    @ApiModelProperty(value = "素材名")
    private String materialName;
    @Dict(dicCode = "material_manager_type")
    @ApiModelProperty(value = "素材类型")
    private Integer type1;
    @Dict(dicCode = "material_img_type")
    @ApiModelProperty(value = "素材归类")
    private Integer type2;
    @Dict(dicCode = "select_upload")
    @ApiModelProperty(value = "是否上传至头条")
    private Integer inUploadJrtt;
    @ApiModelProperty(value = "是否上传成功至头条")
    private Integer jrttStatus;
    @ApiModelProperty(value = "头条账号")
    private Integer jrttAccountId;
    @Dict(dicCode = "select_upload")
    @ApiModelProperty(value = "是否上传至广点通")
    private Integer inUploadGdt;
    @ApiModelProperty(value = "广点通账号")
    private Integer gdtAccountId;
    @Dict(dicCode = "select_upload")
    @ApiModelProperty(value = "是否上传至快手")
    private Integer inUploadKs;
    @ApiModelProperty(value = "快手账号")
    private Integer ksAccountId;
    @ApiModelProperty(value = "素材文件")
    private String file;
    @ApiModelProperty(value = "素材备注")
    private String materialDesc;
    @ApiModelProperty(value = "创建用户")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
