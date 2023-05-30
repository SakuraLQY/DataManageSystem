package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 存放我对应的其他信息
 * @author: chenglin
 * @date: 2023年05月10日 17:54
 */
@Data
public class LaunchTotalBo<T> implements Serializable {
    //查询剩余部分的数据{name,count_active,count_user,count_dau,count_valid_user,first_money,first_payuser,total_money}

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "id")
    private T ID;
    @ApiModelProperty(value = "日期")
    private Date daily;
    @ApiModelProperty(value = "激活数")
    private Integer countActive;
    @ApiModelProperty(value = "激活设备数")
    private Integer countActiveDev;
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser;
    @ApiModelProperty(value = "活跃数DAU")
    private Integer countDau;
    @ApiModelProperty(value = "首日付费金额")
    private BigDecimal firstMoney;
    @ApiModelProperty(value = "首日付费人数")
    private Integer firstPayUser;
    @ApiModelProperty(value = "总收入")
    private Integer totalMoney;
}
