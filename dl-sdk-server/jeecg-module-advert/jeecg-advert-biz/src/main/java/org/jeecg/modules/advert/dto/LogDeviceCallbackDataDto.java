package org.jeecg.modules.advert.dto;

import lombok.Data;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.dto
 * @className: LogDeviceCallbackDataDto
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 13:45
 */
@Data
public class LogDeviceCallbackDataDto {

    /**
     * 广告id
     */
    private String dealId;

    /**
     * 唯一设备ID
     */
    private String uniqueId;

    /**
     * 记录表名
     */
    private Integer tableName;

    /**
     * 创建日期
     */
    private String startCreateTime;

    /**
     * 查询创建日期结束时间
     */
    private String endCreateTime;
}
