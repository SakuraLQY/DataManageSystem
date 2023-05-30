package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 接受转换数据的数据库查询
 * @author: chenglin
 * @date: 2023年05月24日 11:18
 */
@Data
public class ConversionBo implements Serializable {
    /**ID**/
    private Date dateTime;
    /**ID**/
    private Integer id;
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
    /**首日付费*/
    private Integer firstPayUser;
    /**活跃人数*/
    private Integer countDau;

}
