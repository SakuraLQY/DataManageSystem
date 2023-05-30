package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.net.InetAddress;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: TODO
 * @author: chenglin
 * @date: 2023年05月24日 10:56
 */
@Data
public class PublishConversionVo implements Serializable {
    /**ID*/
    @Excel(name = "ID", width = 15)
    @ApiModelProperty(value = "ID")
    private String typeId;
    /**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
    /**激活*/
    @Excel(name = "激活", width = 15)
    @ApiModelProperty(value = "激活")
    private Integer active;
    /**激活设备数*/
    @Excel(name = "激活设备数", width = 15)
    @ApiModelProperty(value = "激活设备数")
    private Integer activeDev;
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
    /**注册设备数*/
    @Excel(name = "注册设备数", width = 15)
    @ApiModelProperty(value = "注册设备数")
    private Integer regCountDev;
    /**激活注册率*/
    @Excel(name = "激活注册率", width = 15)
    @ApiModelProperty(value = "激活注册率")
    private String activeRegRate;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser;
    /**有效注册数*/
    @Excel(name = "有效注册数", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private Integer validUser;
}
