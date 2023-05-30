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
 * @Description: op_commodity
 * @Author: jeecg-boot
 * @Date:   2022-12-12
 * @Version: V1.0
 */
@Data
@TableName("op_commodity")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="op_commodity对象", description="op_commodity")
public class OpCommodity implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    /**子游戏*/
    @Dict(dictTable = "open_gateway.op_sub_game", dicText = "game_name", dicCode = "id")
    @ApiModelProperty(value = "子游戏")
    private Integer subGameId;
    /**渠道游戏包*/
    @Dict(dictTable = "open_gateway.op_pkg", dicText = "pkg_name", dicCode = "id")
    @ApiModelProperty(value = "渠道游戏包")
    private Integer pkgId;
    /**商品ID*/
    @Excel(name = "商品ID", width = 15)
    @ApiModelProperty(value = "商品ID")
    private java.lang.String goodsConf;
    /**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private java.lang.String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;

}
