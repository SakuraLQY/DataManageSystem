package org.jeecg.modules.count.dto;

import java.util.ArrayList;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: PayOrderDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/27 17:04
 */
@Data
public class PayOrderDto {

    /**
     * SDK订单
     */
    @Size(max = 64)
    private String orderId;

    /**
     * CP订单
     */
    @Size(max = 128)
    private String gameOrderId;

    /**
     * 渠道订单号
     */
    @Size(max = 64)
    private String bankOrderId;

    /**
     * 用户ID
     */
    @Size(max = 10)
    private Integer userId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 充值类型
     */
    private Integer orderType;

    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 子游戏ID
     */
    private Integer subGameId;

    /**
     * 广告Id
     */
    private ArrayList dealId;

    /**
     * 渠道类型ID
     */
    private Integer channelTypeId;

    /**
     * 渠道ID
     */
    private Integer channelId;

    /**
     * 渠道子账号ID
     */
    private Integer channelSubAccountId;

    /**
     * pkgId
     */
    private Integer pkgId;

    /**
     * 充值方式
     */
    private Integer bankType;

    /**
     * 手机系统
     */
    private Integer phoneOs;

    /**
     * 支付渠道
     */
    private Integer payVendorId;

    /**
     * 订单查询开始时间
     */
    private String orderStartTime;

    /**
     * 订单查询结束时间
     */
    private String orderEndTime;

    /**
     * 发货状态
     */
    private Integer sendStatus;
}
