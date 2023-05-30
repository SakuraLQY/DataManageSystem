package org.jeecg.modules.count.vo;

import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: OrderPurchaseVolumeVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/4 17:57
 */
@Data
public class OrderPurchaseVolumeVo {

    /**
     * 游戏名
     */
    @Excel(name = "游戏名", width = 15)
    private String gameName;

    /**
     * 媒体名称
     */
    @Excel(name = "媒体名称", width = 15)
    private String channelName;

    /**
     * 广告名称
     */
    @Excel(name = "广告名称", width = 15)
    private String dealName;

    /**
     * 广告id
     */
    @Excel(name = "广告id", width = 15)
    private String dealId;

    /**
     * 用户名
     */
    @Excel(name = "用户名", width = 15)
    private String userName;

    /**
     * 用户id
     */
    @Excel(name = "用户id", width = 15)
    private String userId;

    /**
     * 充值金额
     */
    @Excel(name = "充值金额", width = 15)
    private BigDecimal money;

    /**
     * 注册时间
     */
    @Excel(name = "注册时间", width = 15)
    private String userCreateTime;

    /**
     * 充值方式
     */
    @Excel(name = "充值方式", width = 15,dicCode = "pay_type")
    private Integer payType;

    /**
     * 充值时间
     */
    @Excel(name = "充值时间", width = 15)
    private String openTime;

    /**
     * 地域+ip
     */
    @Excel(name = "地域+ip", width = 15)
    private String area;

    /**
     * ip
     */
    private String clientIp;

    /**
     * 区域
     */
    private String region;

}
