package org.jeecg.modules.advert.dto;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: LogCallbackDataDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 16:25
 */
@Data
public class LogCallbackDataDto {


    /**
     * pkgID
     */
    private Integer pkgId;

    /**
     * 设备ID
     */
    private String uniqueId;

    /**
     * 广告id
     */
    private Integer dealId;

    /**
     * 记录表
     */
    private Integer tableName;

    /**
     * 设备类型
     */
    private String uniqueType;

    /**
     * 渠道ID
     */
    private Integer channelId;

    /**’
     * 创建时间
     */
    private String startCreateTime;

    /**
     * 查询创建时间结束时间
     */
    private String endCreateTime;
}
