package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: UserOrderVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/5 10:30
 */
@Data
public class UserOrderVo {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 广告id
     */
    private Integer dealId;

    /**
     * 用户ip
     */
    private String clientIp;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 充值时间
     */
    private String openTime;

    /**
     * 充值金额
     */
    private BigDecimal money;

}
