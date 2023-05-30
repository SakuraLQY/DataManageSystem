package org.jeecg.modules.count.dto;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: SupplementaryOrderDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/4 13:59
 */
@Data
public class SupplementaryOrderDto {

    /**
     * 发货状态
     */
    private String sendStatus;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 校验密码
     */
    private String validatePassword;

    /**
     * 银行状态
     */
    private String bankStatus;

    /**
     * 游戏状态
     */
    private String gameStatus;
}
