package org.jeecg.modules.game.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.game.bo.OpCommodityBo;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: op_commodity
 * @Author: jeecg-boot
 * @Date:   2022-12-12
 * @Version: V1.0
 */
@Data
@ApiModel(value="返回前端对象", description="返回前端对象")
public class OpCommodityVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
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
    private List<OpCommodityBo> goodsConf;
    /**创建用户*/
    @ApiModelProperty(value = "创建用户")
    private String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
