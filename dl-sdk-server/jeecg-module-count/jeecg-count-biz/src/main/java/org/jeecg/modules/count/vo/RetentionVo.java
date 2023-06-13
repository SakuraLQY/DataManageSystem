package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: ct_user
 * @Author: jeecg-boot
 * @Date:   2023-04-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class RetentionVo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**ID*/
    @Excel(name = "ID", width = 15)
    @ApiModelProperty(value = "ID")
    private String id;
	/**名称*/
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String name;
	/**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    /**次留*/
    @Excel(name = "次留", width = 15)
    @ApiModelProperty(value = "次留")
    private BigDecimal day2;
    /**3日留存*/
    @Excel(name = "3日留存", width = 15)
    @ApiModelProperty(value = "3日留存")
    private BigDecimal day3;
    /**7日留存*/
    @Excel(name = "7日留存", width = 15)
    @ApiModelProperty(value = "7日留存")
    private BigDecimal day7;
    /**15日留存*/
    @Excel(name = "15日留存", width = 15)
    @ApiModelProperty(value = "15日留存")
    private BigDecimal day15;
    /**30日留存*/
    @Excel(name = "30日留存", width = 15)
    @ApiModelProperty(value = "30日留存")
    private BigDecimal day30;
    /**45日留存*/
    @Excel(name = "45日留存", width = 15)
    @ApiModelProperty(value = "45日留存")
    private BigDecimal day45;
    /**60日留存*/
    @Excel(name = "60日留存", width = 15)
    @ApiModelProperty(value = "60日留存")
    private BigDecimal day60;


}
