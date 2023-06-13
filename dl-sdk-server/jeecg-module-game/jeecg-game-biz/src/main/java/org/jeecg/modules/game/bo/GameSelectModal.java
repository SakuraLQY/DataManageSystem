package org.jeecg.modules.game.bo;

import com.alibaba.fastjson.annotation.JSONField;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class GameSelectModal {

    /**类型（多选，单选）*/
    @JSONField(name = "type")
    private Integer type;
    /**渠道*/
    @JSONField(name = "channelId")
    private List<Integer> channelId;


}
