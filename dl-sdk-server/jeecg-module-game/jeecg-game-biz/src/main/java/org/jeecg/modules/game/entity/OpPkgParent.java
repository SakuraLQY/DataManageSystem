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
 * @Description: op_pkg_parent
 * @Author: jeecg-boot
 * @Date:   2023-01-06
 * @Version: V1.0
 */
@Data
@TableName("op_pkg_parent")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_pkg_parent对象", description="op_pkg_parent")
public class OpPkgParent implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private Integer id;
	/**游戏id*/
	@Excel(name = "游戏id", width = 15)
    @Dict(dictTable = "open_gateway.op_game", dicText = "game_name", dicCode = "id")
    @ApiModelProperty(value = "游戏id")
    private Integer gameId;
	/**子游戏id*/
	@Excel(name = "子游戏id", width = 15)
    @ApiModelProperty(value = "子游戏id")
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    private Integer subGameId;
	/**apk文件名*/
	@Excel(name = "apk文件名", width = 15)
    @ApiModelProperty(value = "apk文件名")
    private String apkName;
	/**游戏名*/
	@Excel(name = "游戏名", width = 15)
    @ApiModelProperty(value = "游戏名")
    private String gameName;
	/**apk包名*/
	@Excel(name = "apk包名", width = 15)
    @ApiModelProperty(value = "apk包名")
    private String packageName;
	/**版本号*/
	@Excel(name = "版本号", width = 15)
    @ApiModelProperty(value = "版本号")
    private String version;
	/**构建版本*/
	@Excel(name = "构建版本", width = 15)
    @ApiModelProperty(value = "构建版本")
    private String versionCode;
    /**母包状态*/
    @Excel(name = "母包状态", width = 15)
    @ApiModelProperty(value = "母包状态")
    private Integer state;
	/**母包备注*/
	@Excel(name = "母包备注", width = 15)
    @ApiModelProperty(value = "母包备注")
    private String parentDesc;
	/**创建用户*/
	@Excel(name = "创建用户", width = 15)
    @ApiModelProperty(value = "创建用户")
    private String creatUser;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**包体更新时间*/
	@Excel(name = "包体更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "包体更新时间")
    private Date pkgUpdateTime;
}
