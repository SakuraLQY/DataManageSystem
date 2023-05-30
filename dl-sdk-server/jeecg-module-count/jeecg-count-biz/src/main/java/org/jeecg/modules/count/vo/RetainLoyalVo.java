package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Map;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 返回留存的信息给前端
 * @author: chenglin
 * @date: 2023年05月17日 15:41
 */
@Data
public class RetainLoyalVo implements Serializable {
    /**日期*/
    @ApiModelProperty(value = "日期")
    private String dateTime;
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
    /**新增付费数*/
    @Excel(name = "付费数", width = 15)
    @ApiModelProperty(value = "付费数")
    private Integer firstPayUser;
    @ApiModelProperty(value = "推广费用")
    private BigDecimal costPay;
    /**剩余的loyal留存数据*/
    @ApiModelProperty(value = "剩余的留存")
    private Map<String, String> remainLoyal;
    /**剩余的price单价留存数据*/
    @ApiModelProperty(value = "剩余的单价留存")
    private Map<String,BigDecimal> priceLoyal;
}
