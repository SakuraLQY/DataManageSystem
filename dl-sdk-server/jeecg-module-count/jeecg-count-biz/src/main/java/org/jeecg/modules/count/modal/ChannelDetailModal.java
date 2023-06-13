package org.jeecg.modules.count.modal;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description: 渠道明细模型类
 * @author: chenglin
 * @date: 2023年06月13日 14:41
 */
@Data
public class ChannelDetailModal implements Serializable {
    /**
     * 日期
     */
    @Excel(name = "日期", width = 15,format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @ApiModelProperty(value = "日期")
    private Date timeDaily;
    @Excel(name = "游戏",width = 15)
    @ApiModelProperty(value = "子游戏")
    private String gameName;
    /**
     * 子游戏
     */
    @Excel(name = "子游戏名",width = 15)
    @ApiModelProperty(value = "子游戏")
    private String subGameName;
    /**
     * 渠道
     */
    @Excel(name = "渠道",width = 15)
    @ApiModelProperty(value = "渠道")
    private String channel;
    /**
     * 激活
     */
    @Excel(name = "激活",width = 15)
    @ApiModelProperty(value = "激活")
    private Integer countActive;
    /**
     * 激活设备数
     */
    @Excel(name = "激活设备数",width = 15)
    @ApiModelProperty(value = "激活设备数")
    private Integer countActiveDev;
    /**
     * 注册数
     */
    @Excel(name = "注册数",width = 15)
    @ApiModelProperty(value = "注册数")
    private Integer countUser;
    /**
     * 注册设备数
     */
    @Excel(name = "注册设备数",width = 15)
    @ApiModelProperty(value = "注册设备数")
    private Integer countUserDev;
    /**
     * 激活注册率
     */
    @Excel(name = "激活注册率",width = 15)
    @ApiModelProperty(value = "激活注册率")
    private String activeRate;
    /**
     * 有效注册数
     */
    @Excel(name = "有效注册数",width = 15)
    @ApiModelProperty(value = "有效注册数")
    private Integer countValidUser;
    /**
     * 新增付费人数
     */
    @Excel(name = "新增付费人数",width = 15)
    @ApiModelProperty(value = "新增付费人数")
    private Integer firstPayUser;
    /**
     * 新增付费金额
     */
    @Excel(name = "新增ARPU",width = 15)
    @ApiModelProperty(value = "新增ARPU")
    private Integer firstMoney;
    /**
     * 新增付费率
     */
    @Excel(name = "新增ARPU",width = 15)
    @ApiModelProperty(value = "新增ARPU")
    private String firstPayRate;
    /**
     * 新增ARPU
     */
    @Excel(name = "新增ARPU",width = 15)
    @ApiModelProperty(value = "新增ARPU")
    private BigDecimal firstArpu;
    /**
     * 新增ARPPU
     */
    @Excel(name = "新增ARPPU",width = 15)
    @ApiModelProperty(value = "新增ARPPU")
    private BigDecimal firstArppu;
    /**
     * 老用户付费数
     */
    @Excel(name = "老用户付费数",width = 15)
    @ApiModelProperty(value = "老用户付费数 = 活跃用户-新增用户付费数")
    private Integer oldPayUser;
    /**
     * 老用户付费金额
     */
    @Excel(name = "老用户付费金额",width = 15)
    @ApiModelProperty(value = "老用户付费金额 = 活跃付费-新增付费")
    private BigDecimal oldMoney;
    /**
     * 老用户付费率
     */
    @Excel(name = "老用户付费率",width = 15)
    @ApiModelProperty(value = "老用户付费率")
    private String oldPayRate;
    /**
     * 老用户ARPU
     */
    @Excel(name = "老用户ARPU",width = 15)
    @ApiModelProperty(value = "老用户ARPU")
    private BigDecimal oldArpu;
    /**
     * 老用户ARPPU
     */
    @Excel(name = "老用户ARPPU",width = 15)
    @ApiModelProperty(value = "老用户ARPPU")
    private BigDecimal oldArppu;
    /**
     * 累计付费金额
     */
    @Excel(name = "累计付费金额",width = 15)
    @ApiModelProperty(value = "累计付费金额")
    private Integer totalMoney;
    /**
     * DAU
     */
    @Excel(name = "DAU",width = 15)
    @ApiModelProperty(value = "DAU")
    private Integer dau;
    /**
     * 活跃付费人数
     */
    @Excel(name = "活跃付费人数",width = 15)
    @ApiModelProperty(value = "活跃付费人数")
    private Integer alivePayUser;
    /**
     * 活跃付费金额
     */
    @Excel(name = "活跃付费金额",width = 15)
    @ApiModelProperty(value = "活跃付费金额")
    private Integer aliveMoney;
    /**
     * ARPU
     */
    @Excel(name = "ARPU",width = 15)
    @ApiModelProperty(value = "ARPU")
    private BigDecimal arpu;
    /**
     * ARPPU
     */
    @Excel(name = "ARPPU",width = 15)
    @ApiModelProperty(value = "ARPPU")
    private BigDecimal arppu;
    /**
     * 总付费率
     */
    @Excel(name = "总付费率",width = 15)
    @ApiModelProperty(value = "总付费率")
    private String totalPayRate;
    /**
     * 次留
     */
    @Excel(name = "次留",width = 15)
    @ApiModelProperty(value = "次留")
    private Integer loyal;
    /**
     * 3日留存
     */
    @Excel(name = "3日留存",width = 15)
    @ApiModelProperty(value = "3日留存")
    private Integer loyal3;
    /**
     * 4日留存
     */
    @Excel(name = "4日留存",width = 15)
    @ApiModelProperty(value = "4日留存")
    private Integer loyal4;
    /**
     * 5日留存
     */
    @Excel(name = "5日留存",width = 15)
    @ApiModelProperty(value = "5日留存")
    private Integer loyal5;
    /**
     * 6日留存
     */
    @Excel(name = "6日留存",width = 15)
    @ApiModelProperty(value = "6日留存")
    private Integer loyal6;
    /**
     * 7日留存
     */
    @Excel(name = "7日留存",width = 15)
    @ApiModelProperty(value = "7日留存")
    private Integer loyal7;
    /**
     * LTV1
     */
    @Excel(name = "LTV1",width = 15)
    @ApiModelProperty(value = "LTV1")
    private Integer ltv1;
    /**
     * LTV2
     */
    @Excel(name = "LTV2",width = 15)
    @ApiModelProperty(value = "LTV2")
    private Integer ltv2;
    /**
     * LTV3
     */
    @Excel(name = "LTV3",width = 15)
    @ApiModelProperty(value = "LTV3")
    private Integer ltv3;
    /**
     * LTV4
     */
    @Excel(name = "LTV4",width = 15)
    @ApiModelProperty(value = "LTV4")
    private Integer ltv4;
    /**
     * LTV5
     */
    @Excel(name = "LTV5",width = 15)
    @ApiModelProperty(value = "LTV5")
    private Integer ltv5;
    /**
     * LTV6
     */
    @Excel(name = "LTV6",width = 15)
    @ApiModelProperty(value = "LTV6")
    private Integer ltv6;
    /**
     * LTV7
     */
    @Excel(name = "LTV7",width = 15)
    @ApiModelProperty(value = "LTV7")
    private Integer ltv7;
}
