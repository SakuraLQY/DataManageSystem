package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: PayOrderVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/27 18:51
 */
@Data
public class PayOrderVo {

    /**
     * 游戏名
     */
    @Excel(name = "游戏名", width = 15)
    private String gameName;

    /**
     * 广告ID
     */
    @Excel(name = "广告ID", width = 15)
    private Integer dealId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID", width = 15)
    private String userId;

    /**
     * 用户名
     */
    @Excel(name = "用户名", width = 15)
    private String userName;

    /**
     * 区服ID
     */
    @Excel(name = "区服ID", width = 15)
    private Integer serverId;

    /**
     * 订单ID
     */
    @Excel(name = "订单ID", width = 15)
    private String orderId;


    /**
     * CP订单
     */
    @Excel(name = "CP订单", width = 15)
    private String gameOrderId;

    /**
     * 渠道订单号
     */
    @Excel(name = "渠道订单号", width = 15)
    private String bankOrderId;

    /**
     * 充值金额
     */
    @Excel(name = "充值金额", width = 15)
    private BigDecimal money;

    /**
     * 充值方式
     */
    @Excel(name = "充值方式", width = 15,dicCode = "pay_type")
    private Integer bankType;

    /**
     * 充值时间
     */
    @Excel(name = "充值时间", width = 15)
    private String openTime;

    /**
     * 充值类型
     */
    @Excel(name = "充值类型", width = 15,dicCode = "order_type")
    private Integer orderType;

    /**
     * 游戏状态
     */
    @Excel(name = "游戏状态", width = 15,dicCode = "game_status")
    private Integer gameStatus;

    /**
     * 银行状态
     */
    @Excel(name = "银行状态", width = 15,dicCode = "bank_status")
    private Integer bankStatus;

    /**
     * 发货重试次数
     */
    @Excel(name = "发货重试次数", width = 15)
    private Integer gameDeliverRetry;

    /**
     * 发货状态
     */
    private Integer sendStatus;

    /**
     * Cp透传参数
     */
    private String gameData;


}
