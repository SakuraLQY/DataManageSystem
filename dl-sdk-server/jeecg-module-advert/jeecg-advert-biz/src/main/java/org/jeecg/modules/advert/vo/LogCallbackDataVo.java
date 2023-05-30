package org.jeecg.modules.advert.vo;

import java.util.Date;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @projectName: jeecg-boot-parent
 * @package: org.jeecg.modules.advert.vo
 * @className: LogCallbackDataVo
 * @author: fkh
 * @description: TODO
 * @date: 2023/5/12 16:48
 */
@Data
public class LogCallbackDataVo {

    /**
     * 游戏
     */
    @Excel(name = "游戏",width = 15)
    private String pkgName;

    /**
     * 广告ID
     */
    @Excel(name = "广告ID",width = 15)
    private Integer dealId;

    /**
     * 设备类型
     */
    @Excel(name = "设备类型",width = 15)
    private String uniqueType;

    /**
     * 设备ID
     */
    @Excel(name = "设备ID",width = 15)
    private String uniqueId;

    /**
     * 渠道名
     */
    @Excel(name = "渠道名",width = 15)
    private String channelName;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间",width = 15)
    private Date createTime;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间",width = 15)
    private Date updateTime;

    /**
     * 回调参数
     */
    @Excel(name = "回调参数",width = 15)
    private String visitData;
}
