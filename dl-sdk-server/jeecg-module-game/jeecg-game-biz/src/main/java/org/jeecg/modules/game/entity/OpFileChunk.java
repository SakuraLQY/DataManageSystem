package org.jeecg.modules.game.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: op_file_chunk
 * @Author: jeecg-boot
 * @Date:   2023-01-18
 * @Version: V1.0
 */
@Data
@TableName("op_file_chunk")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_file_chunk对象", description="op_file_chunk")
public class OpFileChunk implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
	/**文件名*/
	@Excel(name = "文件名", width = 15)
    @ApiModelProperty(value = "文件名")
    private java.lang.String fileName;
	/**当前分片，从1开始*/
	@Excel(name = "当前分片，从1开始", width = 15)
    @ApiModelProperty(value = "当前分片，从1开始")
    private java.lang.Integer chunkNumber;
	/**分片大小*/
	@Excel(name = "分片大小", width = 15)
    @ApiModelProperty(value = "分片大小")
    private java.lang.Double chunkSize;
	/**当前分片大小*/
	@Excel(name = "当前分片大小", width = 15)
    @ApiModelProperty(value = "当前分片大小")
    private java.lang.Double currentChunkSize;
	/**文件总大小*/
	@Excel(name = "文件总大小", width = 15)
    @ApiModelProperty(value = "文件总大小")
    private java.lang.Double totalSize;
	/**总分片数*/
	@Excel(name = "总分片数", width = 15)
    @ApiModelProperty(value = "总分片数")
    private java.lang.Integer totalChunk;
	/**文件标识*/
	@Excel(name = "md5文件标识", width = 15)
    @ApiModelProperty(value = "md5文件标识")
    private java.lang.String identifier;
	/**真实路径*/
	@Excel(name = "真实路径", width = 15)
    @ApiModelProperty(value = "真实路径")
    private java.lang.String relativePath;
	/**createtime*/
	@Excel(name = "createtime", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createtime")
    private java.util.Date createtime;
	/**updatetime*/
	@Excel(name = "updatetime", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updatetime")
    private java.util.Date updatetime;

    @ApiModelProperty(value = "gameId")
    private Integer gameId;

    @ApiModelProperty(value = "subGameId")
    private Integer subGameId;

    @Transient
    @TableField(exist = false)
    private MultipartFile file;
}
