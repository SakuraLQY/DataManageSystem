package org.jeecg.modules.game.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OpCommodityBo {

    /**商品id*/
    @JSONField(name = "goods_id")
    private String goodsId;
    /**金额*/
    @JSONField(name = "money")
    private BigDecimal money;
    /**货币类型（0/1 元/美元）*/
    @JSONField(name = "currency_type")
    private String currencyType;

}
