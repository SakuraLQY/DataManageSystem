package org.jeecg.modules.game.dto;

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
 * @Description: op_pack_material
 * @Author: jeecg-boot
 * @Date: 2023-01-09
 * @Version: V1.0
 */
@Data
@ApiModel(value = "前端传来对象", description = "前端传来对象")
public class OpPackMaterialDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "游戏ID")
    private Integer gameId;
    @ApiModelProperty(value = "子游戏ID")
    private Integer subGameId;
    @ApiModelProperty(value = "素材名")
    private String materialName;
    @ApiModelProperty(value = "素材类型")
    private Integer type;
    @ApiModelProperty(value = "文件")
    private String file;
    @ApiModelProperty(value = "素材备注")
    private String materialDesc;
    @ApiModelProperty(value = "创建用户")
    private String createBy;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
