package org.jeecg.common.game.vo;

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
public class OpGameModel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**游戏id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "游戏id")
    private Integer id;
	/**游戏类型*/
	@Excel(name = "游戏类型", width = 15, dicCode = "game_type")
	@Dict(dicCode = "game_type")
    @ApiModelProperty(value = "游戏类型")
    private Integer gameTypeId;
	/**游戏名*/
	@Excel(name = "游戏名", width = 15)
    @ApiModelProperty(value = "游戏名")
    private String gameName;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String descs;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
	/**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private String createBy;
}
