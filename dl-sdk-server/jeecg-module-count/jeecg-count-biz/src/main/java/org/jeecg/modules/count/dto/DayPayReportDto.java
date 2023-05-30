package org.jeecg.modules.count.dto;

import java.util.ArrayList;
import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: DayPayReportDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/4/26 17:23
 */
@Data
public class DayPayReportDto {

    /**
     * 游戏id
     */
    private ArrayList gameId;

    /**
     * 子游戏ID
     */
    private ArrayList subGameId;

    /**
     * 渠道类型ID
     */
    private ArrayList channelTypeId;

    /**
     * 渠道ID
     */
    private ArrayList channelId;

    /**
     * 渠道子账号ID
     */
    private ArrayList channelSubAccountId;

    /**
     * pkgId
     */
    private ArrayList pkgId;

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
