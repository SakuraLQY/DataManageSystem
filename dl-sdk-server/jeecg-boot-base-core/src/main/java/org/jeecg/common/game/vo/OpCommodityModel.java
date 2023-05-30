package org.jeecg.common.game.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="游戏内购信息", description="游戏内购信息")
public class OpCommodityModel {
    private static final long serialVersionUID = 1L;

    /**商品ID*/
    private java.lang.String goodsId;
    /**游戏商品金额*/
    private BigDecimal money;
    /**币种*/
    private java.lang.String currencyType;
}
