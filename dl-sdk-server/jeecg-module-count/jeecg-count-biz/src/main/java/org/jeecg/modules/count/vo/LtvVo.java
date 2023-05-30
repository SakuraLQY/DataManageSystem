package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 定义返回给前段的数据
 * @author: chenglin
 * @date: 2023年05月15日 16:49
 */
@Data
@ApiModel(value="传给前端对象", description="传给前端对象")
public class LtvVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**日期*/
    @ApiModelProperty(value = "日期")
    private String ltvDate;
    /**游戏*/
    @ApiModelProperty(value = "游戏名")
    private String gameName;
    /**渠道*/
    @ApiModelProperty(value = "渠道名")
    private String channelName;
    /**广告*/
    @ApiModelProperty(value = "广告名")
    private String dealName;
    /**新增注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
    /**剩余的ltv数据*/
    @ApiModelProperty(value = "剩余的LTV")
    private Map<String,BigDecimal>remainLtv;
}
