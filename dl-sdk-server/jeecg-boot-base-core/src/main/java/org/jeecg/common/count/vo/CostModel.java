package org.jeecg.common.count.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Description: CostModel
 * @Author: jeecg-boot
 * @Date: 2023-04-13
 * @Version: V1.0
 */
@Data
public class CostModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 游戏id
     **/
    private Integer gameId;

    /**
     * 子游戏id
     **/
    private Integer subGameId;

    /**
     * 一级游戏包id
     **/
    private Integer pkgId;

    /**
     * 渠道类型id
     **/
    private Integer channelTypeId;

    /**
     * 渠道id
     **/
    private Integer channelId;

    /**
     * 渠道子账号id
     **/
    private Integer channelSubAccountId;

    /**
     * 成本时间
     **/
    private String costDay;

    /**
     * 成本
     **/
    private BigDecimal costMoney = BigDecimal.ZERO;
}
