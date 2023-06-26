package org.jeecg.modules.count.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 自定义返回给前端的数据
 * @author: chenglin
 * @date: 2023年05月11日 18:56
 */
@Data
public class ChannelDetailVo implements Serializable {

    /**
     * 日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @ApiModelProperty(value = "日期")
    private String timeDaily;
    @ApiModelProperty(value = "子游戏")
    private String gameName;
    /**
     * 子游戏
     */
    @ApiModelProperty(value = "子游戏")
    private String subGameName;
    /**
     * 渠道
     */
    @ApiModelProperty(value = "渠道")
    private String channel;
    /**
     * 激活
     */
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
     * 激活注册率
     */
    @ApiModelProperty(value = "激活注册率")
    private String activeRate;
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
     * 新增付费率
     */
    @ApiModelProperty(value = "新增付费率")
    private String firstPayRate;
    /**
     * 新增ARPU
     */
    @ApiModelProperty(value = "新增ARPU")
    private BigDecimal firstArpu;
    /**
     * 新增ARPPU
     */
    @ApiModelProperty(value = "新增ARPPU")
    private BigDecimal firstArppu;
    /**
     * 老用户付费数
     */
    @ApiModelProperty(value = "老用户付费数 = 活跃用户-新增用户付费数")
    private Integer oldPayUser;
    /**
     * 老用户付费金额
     */
    @ApiModelProperty(value = "老用户付费金额 = 活跃付费-新增付费")
    private BigDecimal oldMoney;
    /**
     * 老用户付费率
     */
    @ApiModelProperty(value = "老用户付费率")
    private String oldPayRate;
    /**
     * 老用户ARPU
     */
    @ApiModelProperty(value = "老用户ARPU")
    private BigDecimal oldArpu;
    /**
     * 老用户ARPPU
     */
    @ApiModelProperty(value = "老用户ARPPU")
    private BigDecimal oldArppu;
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
    /**
     * ARPU
     */
    @ApiModelProperty(value = "ARPU")
    private BigDecimal arpu;
    /**
     * ARPPU
     */
    @ApiModelProperty(value = "ARPPU")
    private BigDecimal arppu;
    /**
     * 总付费率
     */
    @ApiModelProperty(value = "总付费率")
    private String totalPayRate;
    /**
     * 次留
     */
    @ApiModelProperty(value = "次留")
    private String loyal;
    /**
     * 3日留存
     */
    @ApiModelProperty(value = "3日留存")
    private String loyal3;
    /**
     * 4日留存
     */
    @ApiModelProperty(value = "4日留存")
    private String loyal4;
    /**
     * 5日留存
     */
    @ApiModelProperty(value = "5日留存")
    private String loyal5;
    /**
     * 6日留存
     */
    @ApiModelProperty(value = "6日留存")
    private String loyal6;
    /**
     * 7日留存
     */
    @ApiModelProperty(value = "7日留存")
    private String loyal7;
    /**
     * LTV1
     */

    @ApiModelProperty(value = "LTV1")
    private BigDecimal ltv1;
    /**
     * LTV2
     */

    @ApiModelProperty(value = "LTV2")
    private BigDecimal ltv2;
    /**
     * LTV3
     */

    @ApiModelProperty(value = "LTV3")
    private BigDecimal ltv3;
    /**
     * LTV4
     */

    @ApiModelProperty(value = "LTV4")
    private BigDecimal ltv4;
    /**
     * LTV5
     */
    @ApiModelProperty(value = "LTV5")
    private BigDecimal ltv5;
    /**
     * LTV6
     */
    @ApiModelProperty(value = "LTV6")
    private BigDecimal ltv6;
    /**
     * LTV7
     */
    @ApiModelProperty(value = "LTV7")
    private BigDecimal ltv7;
}
