package org.jeecg.modules.advert.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.count.vo
 * @className: LogDeviceCallbackDataVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 13:50
 */
@Data
public class LogDeviceCallbackDataVo {

    /**
     * 广告id
     */
    @Excel(name = "广告ID",width = 15)
    private String dealId;

    /**
     * 唯一设备ID
     */
    @Excel(name = "唯一设备ID",width = 15)
    private String uniqueId;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间",width = 15)
    private String createTime;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间",width = 15)
    private String updateTime;

    /**
     * 回调参数
     */
    @Excel(name = "回调参数",width = 15)
    private String visitData;
}
