package org.jeecg.modules.count.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @description: 用于接收对象拷贝的对象
 * @author: chenglin
 * @date: 2023年05月12日 13:18
 */
@Data
public class ChannelDetailTempBo implements Serializable {
//    @ApiModelProperty(value = "日期")
//    private Date timeDaily;
    @ApiModelProperty(value = "激活")
    private Integer countActive;
    /**
     * 激活设备数
     */
    @ApiModelProperty(value = "激活设备数")
    private Integer countActiveDev;
    /**
     * 注册数
     */
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    /**
     * 注册设备数
     */
    @ApiModelProperty(value = "注册设备数")
    private Integer countUserDev;
    /**
     * 有效注册数
     */
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser;
    /**
     * 新增付费人数
     */
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser;
    /**
     * 新增付费金额
     */
    @ApiModelProperty(value = "新增付费金额")
    private Integer firstMoney;
    /**
     * 累计付费金额
     */
    @ApiModelProperty(value = "累计付费金额")
    private Integer totalMoney;
    /**
     * DAU
     */
    @ApiModelProperty(value = "DAU")
    private Integer dau;
    /**
     * 活跃付费人数
     */
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayUser;
    /**
     * 活跃付费金额
     */
    @ApiModelProperty(value = "活跃付费金额")
    private Integer aliveMoney;

}
