package org.jeecg.modules.count.dto;

import java.util.ArrayList;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: OrderPurchaseVolumeDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/4 17:44
 */
@Data
public class OrderPurchaseVolumeDto {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户ID
     */
    @Size(max = 10)
    private Integer userId;

    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 子游戏ID
     */
    private Integer subGameId;

    /**
     * pkgId
     */
    private Integer pkgId;

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
     * 订单创建开始时间
     */
    private String orderStartTime;

    /**
     * 订单创建查询结束时间
     */
    private String orderEndTime;

    /**
     * 用户创建开始时间
     */
    private String userStartTime;

    /**
     * 用户创建查询截止时间
     */
    private String userEndTime;
}
