package org.jeecg.modules.count.vo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @description: 合作商数据单位返回给前端的数据
 * @author: chenglin
 * @date: 2023年05月30日 10:29
 */
@Data
public class StatDealVo implements Serializable {
    /**日期*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String dateTime;
    /**广告名称*/
    @Excel(name = "广告名称", width = 15)
    @ApiModelProperty(value = "广告名称")
    private String dealName;
    /**游戏名称*/
    @Excel(name = "游戏名称", width = 15)
    @ApiModelProperty(value = "游戏名称")
    private String gameName;
    /**激活数*/
    @Excel(name = "激活数", width = 15)
    @ApiModelProperty(value = "激活数")
    private Integer countActive;
    /**注册数*/
    @Excel(name = "注册数", width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer regCount;
    /**有效注册数*/
    @Excel(name = "有效注册数", width = 15)
    @ApiModelProperty(value = "有效注册数")
    private Integer validReg;
    /**新增付费人数*/
    @Excel(name = "新增付费人数", width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser;
    /**新增付费金额*/
    @Excel(name = "新增付费金额", width = 15)
    @ApiModelProperty(value = "新增付费金额")
    private BigDecimal firstPayMoney;
    /**活跃人数*/
    @Excel(name = "活跃人数", width = 15)
    @ApiModelProperty(value = "活跃人数")
    private Integer countDau;
    /**活跃付费人数*/
    @Excel(name = "活跃付费人数", width = 15)
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayUser;
    /**付费总额*/
    @Excel(name = "付费总额", width = 15)
    @ApiModelProperty(value = "付费总额")
    private BigDecimal totalMoney;
}
