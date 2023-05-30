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
 * @Description: op_game
 * @Author: jeecg-boot
 * @Date:   2022-12-08
 * @Version: V1.0
 */
@Data
@TableName("op_game")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_game对象", description="op_game")
public class OpGame implements Serializable {
    private static final long serialVersionUID = 1L;

	/**游戏id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "游戏id")
    private java.lang.Integer id;
	/**游戏类型*/
	@Excel(name = "游戏类型", width = 15, dicCode = "game_type")
	@Dict(dicCode = "game_type")
    @ApiModelProperty(value = "游戏类型")
    private java.lang.Integer gameTypeId;
	/**游戏名*/
	@Excel(name = "游戏名", width = 15)
    @ApiModelProperty(value = "游戏名")
    private java.lang.String gameName;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String descs;
	/**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
}
