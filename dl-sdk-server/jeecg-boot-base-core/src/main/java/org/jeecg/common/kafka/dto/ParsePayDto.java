package org.jeecg.common.kafka.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Description: 解析激活dto
 * @Author: chenyw
 * @Date: 2022-04-06
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ParsePayDto implements Serializable {

    /**
     * 子游戏Id
     */
    private Integer subGameId;

    /**
     *  游戏Id
     */
    private Integer gameId;

    /**
     *  渠道Id
     */
    private Integer channelId;

    /**
     *  渠道类型Id
     */
    private Integer channelTypeId;

    /**
     *  渠道子账号Id
     */
    private Integer channelSubAccountId;

    /**
     * 广告id
     */
    private Integer dealId;

    /**
     * 一级游戏包ID
     */
    private Integer pkgId;

    /**
     * 唯一设备标识
     */
    private String uniqueId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 优惠金额
     */
    private BigDecimal couponPrice;

    /**
     * 区服id
     */
    private Integer serverId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色等级
     */
    private Integer roleLevel;

    /**
     * 支付厂商id
     */
    private Integer payVendorId;

    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 区域
     */
    private String region;

    /**
     * 时间戳(毫秒)
     */
    private Long time;

}
