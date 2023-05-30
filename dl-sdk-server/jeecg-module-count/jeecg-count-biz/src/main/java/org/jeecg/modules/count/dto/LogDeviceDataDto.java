package org.jeecg.modules.count.dto;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: LogManageDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 10:05
 */
@Data
public class LogDeviceDataDto {

    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 子游戏ID
     */
    private Integer subGameId;

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
     * 唯一ID
     */
    private String uniqueId;

    /**
     * 创建查询开始时间
     */
    private String startCreateTime;

    /**
     * 创建查询结束时间
     */
    private String endCreateTime;
}
